package asq.pos.register.sale.zatca;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqValueKeys;
import asq.pos.zatca.AsqZatcaConstant;
import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import asq.pos.zatca.database.helper.AsqZatcaDatabaseHelper;
import asq.pos.zatca.database.helper.AsqZatcaInvoiceHashQueryResult;
import asq.pos.zatca.invoice.generation.op.AsqZatcaInvoiceGenerationHelper;
import asq.pos.zatca.invoice.generation.op.SmartHubUtil;
import asq.pos.zatca.invoice.generation.utils.ASQException;
import asq.pos.zatca.invoice.models.AdditionalDocumentReference;
import asq.pos.zatca.invoice.models.HashQRData;
import asq.pos.zatca.invoice.models.SignatureData;
import dtv.pos.common.LocationFactory;
import dtv.pos.common.TransactionType;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.register.returns.ReturnManager;
import dtv.pos.register.type.LineItemType;
import dtv.pos.tender.TenderHelper;
import dtv.util.StringUtils;
import dtv.util.sequence.SequenceFactory;
import dtv.xst.cleandto.tnd.Tender;
import dtv.xst.dao.crm.IParty;
import dtv.xst.dao.loc.IRetailLocation;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trl.IRetailTransactionLineItem;
import dtv.xst.dao.trl.impl.SaleReturnLineItemModel;
import dtv.xst.dao.trl.impl.TaxLineItemModel;
import dtv.xst.dao.ttr.impl.TenderLineItemModel;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AllowanceChargeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.SupplierPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxSubtotalType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DocumentCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InvoiceTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IssueDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IssueTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NoteType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ProfileIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.UUIDType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;

public class AsqZatcaSaleQrCodeGenerationOp extends Operation {

	private static final Logger logger = LogManager.getLogger(AsqZatcaSaleQrCodeGenerationOp.class);

	@Inject
	private AsqZatcaInvoiceGenerationHelper asqZatcaInvoiceGenerationHelper;

	@Inject
	private AsqZatcaHelper asqZatcaHelper;

	@Inject
	private ReturnManager retunManager;

	@Inject
	private LocationFactory locFactory;

	@Inject
	private AsqZatcaDatabaseHelper asqZatcaDatabaseHelper;

	@Inject
	TenderHelper tenderHelper;

	@Override
	public IOpResponse handleOpExec(IXstEvent paramIXstEvent) {
		IRetailTransaction currentSaleTrans = _transactionScope.getTransaction(TransactionType.RETAIL_SALE);
		try {
			String generatedQRcode = genZatacaInvoiceAndQRcode(currentSaleTrans);
			_transactionScope.setValue(AsqValueKeys.ASQ_ZATCA_QR_CODE, generatedQRcode);
		} catch (Exception e) {
			logger.error("We have recieved Exception in genearting Zatca invoice and QR code for the current transaction  ", e);
		}
		return HELPER.completeResponse();
	}

	public String genZatacaInvoiceAndQRcode(IRetailTransaction currentSaleTrans) throws ASQException, Exception {
		logger.info("*******InvoiceRequest Unicode Value After Converting to Object :" + currentSaleTrans);

		InvoiceType zatcaInvoiceObj = new InvoiceType();

		AdditionalDocumentReference addDocQR = new AdditionalDocumentReference("QR", StringUtils.EMPTY, StringUtils.EMPTY);

		IRetailLocation loc = locFactory.getStoreById(currentSaleTrans.getRetailLocationId());

		String sellerName = String.valueOf(loc.getStringProperty("ASQ_SELLER_NAME"));

		// invoiceData.getSellerVATRegNumber()
		String sellerVATRegNumber = String.valueOf(loc.getStringProperty("ASQ_VAT_NUMBER"));

		// invoiceData.getInvoiceIssueTime()
		String invoiceIssueTime = asqZatcaHelper.getIssueTime(currentSaleTrans.getCreateDate());

		// invoiceData.getInvoiceIssueDate()
		String invoiceIssueDate = asqZatcaHelper.getIssueDate(currentSaleTrans.getCreateDate());

		// invoiceData.getPayableAmount
		String payableAmount = currentSaleTrans.getAmountTendered().toString();

		// invoiceData.getAdditionalDocumentReference().get(i).getUUID()
		String xmlUUID = String.valueOf(currentSaleTrans.getObjectIdAsString());

		// invoiceData.getIrn();
		String xmlIrnValue = asqZatcaHelper.getTransactionUIID(currentSaleTrans);

		// vatTotal
		String vatTotal = String.valueOf(currentSaleTrans.getTaxAmount());

		SignatureData signatureData = new SignatureData();

		Long nextICV = SequenceFactory.getNextLongValue("ASQ_ZATCA_SEQ");

		oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac = asqZatcaHelper
				.getZatcaOjectFactory(oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory.class);
		oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc = asqZatcaHelper
				.getZatcaOjectFactory(oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory.class);

		poulateXMLInvoiceType(currentSaleTrans, loc, zatcaInvoiceObj, cbc, cac, addDocQR, xmlIrnValue, xmlUUID, invoiceIssueDate, invoiceIssueTime, nextICV);

		String intialTransInvoice = asqZatcaHelper.generateInvoiceXML(zatcaInvoiceObj);

		logger.debug(" ---------------------------Generate QR Starts---------------------- ");
		HashQRData data = asqZatcaInvoiceGenerationHelper.getHashAndQR(sellerName, sellerVATRegNumber, invoiceIssueTime, invoiceIssueDate, payableAmount, vatTotal, intialTransInvoice,
				AsqZatcaConstant.keySecret, AsqZatcaConstant.keyAlg, addDocQR, xmlUUID, xmlIrnValue, signatureData, nextICV);

		if (data.isCertificateExpired()) {
			logger.error("*******Certificate Expired************");
			logger.error("*******Certificate Valid Up to: {} ************", data.getExpirationDate());
			SmartHubUtil.generateCertificateExpiredErrorResponse(data.getExpirationDate());
		}
		logger.debug(" ---------------------------Generate QR End---------------------- ");
		logger.debug("*******QRCode Generated: {} ************", data.getQR());
		if (null == data || null == data.getQR() || data.getQR().isEmpty()) {
			logger.error("QR Code Generation Failed: QR Code is null or empty");
			throw new ASQException("QR Code Generation Failed: QR Code is null or empty");
		} else if (null == data || null == data.getCertificate() || data.getCertificate().isEmpty()) {
			logger.error("Certificate Fetching Failed: Issue with Certificate Reading");
			throw new ASQException("Certificate Fetching Failed: Issue with Certificate Reading");
		}

		zatcaInvoiceObj.getAdditionalDocumentReference().add(asqZatcaInvoiceGenerationHelper.setDocumentReferenceType("QR", StringUtils.EMPTY, cbc, cac, data.getQR()));
		zatcaInvoiceObj.getSignature()
				.add(asqZatcaInvoiceGenerationHelper.setSignatureType(System.getProperty("asq.pos.invoice.referencedSignatureID"), System.getProperty("asq.pos.invoice.extensionURI"), cbc, cac));

		UBLExtensionsType ublExtensionsType = asqZatcaHelper.getZatcaOjectFactory(oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ObjectFactory.class).createUBLExtensionsType();
		ublExtensionsType.getUBLExtension()
				.add(asqZatcaInvoiceGenerationHelper.getUBLExtension(System.getProperty("asq.pos.invoice.extensionURI"), System.getProperty("asq.pos.invoice.signatureInformationID"),
						System.getProperty("asq.pos.invoice.referencedSignatureID"),
						new String[] { System.getProperty("asq.pos.invoice.transformsAlgorithm"), System.getProperty("asq.pos.invoice.transformsAlgorithm"),
								System.getProperty("asq.pos.invoice.transformsAlgorithm"), System.getProperty("asq.pos.invoice.transformsAlgorithm") },
						// createTransformTypeXPath
						new String[] { System.getProperty("asq.pos.invoice.xpathTagUBLExtensions"), System.getProperty("asq.pos.invoice.xpathTagSignature"),
								System.getProperty("asq.pos.invoice.xpathTagAdditionalDocRef"), StringUtils.EMPTY },
						cbc, signatureData));

		zatcaInvoiceObj.setUBLExtensions(ublExtensionsType);
		String invoiceXML = asqZatcaInvoiceGenerationHelper.generateFinalXML(zatcaInvoiceObj, data.getQR(), signatureData);

		Base64 base64 = new Base64();

		// saving the invoice into staging table
		asqZatcaDatabaseHelper.saveInvoiceInStaging(xmlIrnValue, _stationState.getCurrentBusinessDate(), Long.valueOf(_stationState.getWorkstationId()), currentSaleTrans.getTransactionSequence(),
				base64.encodeToString(invoiceXML.getBytes()), data.getQR(), xmlUUID, data.getInvoiceHash(), nextICV);

		// return the receipt QRcode
		return data.getQR();
	}

	private void poulateXMLInvoiceType(IRetailTransaction currentSaleTrans, IRetailLocation loc, InvoiceType argZatcaInvoiceObj,
			oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc, oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac,
			AdditionalDocumentReference addDocQR, String xmlIrnValue, String xmlUUID, String invoiceIssueDate, String invoiceIssueTime, Long nextICV)
			throws ParseException, DatatypeConfigurationException {

		// setting Profile ID
		ProfileIDType profileID = cbc.createProfileIDType();
		profileID.setValue("reporting:1.0");
		argZatcaInvoiceObj.setProfileID(profileID);

		// Setting the ID
		IDType idType = cbc.createIDType();
		idType.setValue(xmlIrnValue);
		argZatcaInvoiceObj.setID(idType);

		// Setting the UUID
		UUIDType uuidType = cbc.createUUIDType();
		uuidType.setValue(xmlUUID);
		argZatcaInvoiceObj.setUUID(uuidType);

		// Setting the invoice type
		InvoiceTypeCodeType invoiceTypeCodeType = cbc.createInvoiceTypeCodeType();
		// setting return sale parameters
		String reasonCode = StringUtils.EMPTY;
		if (retunManager != null && null != retunManager.getReturnItemId()) {
			reasonCode = AsqZatcaConstant.ZATCA_RETURN_REASON_CODE;
			invoiceTypeCodeType.setValue(AsqZatcaConstant.ZATCA_RETURN_CODE);
		}
		// B2C = 02 is for Business to Customer
		invoiceTypeCodeType.setName(AsqZatcaConstant.ZATCA_BUSINESS_TO_CUSTOMER);
		invoiceTypeCodeType.setValue(AsqZatcaConstant.ZATCA_SALE_CODE);
		argZatcaInvoiceObj.setInvoiceTypeCode(invoiceTypeCodeType);

		// Setting the tender currency
		DocumentCurrencyCodeType documentCurrencyCodeType = cbc.createDocumentCurrencyCodeType();
		documentCurrencyCodeType.setValue(System.getProperty("dtv.location.CurrencyId"));
		argZatcaInvoiceObj.setDocumentCurrencyCode(documentCurrencyCodeType);

		// Setting the tax currency
		TaxCurrencyCodeType taxCurrencyCodeType = cbc.createTaxCurrencyCodeType();
		taxCurrencyCodeType.setValue(System.getProperty("dtv.location.CurrencyId"));
		argZatcaInvoiceObj.setTaxCurrencyCode(taxCurrencyCodeType);

		// setting transaction Date
		IssueDateType issueDateType = cbc.createIssueDateType();
		GregorianCalendar gregorianCalendarIssueDate = new GregorianCalendar();
		gregorianCalendarIssueDate.setTime(currentSaleTrans.getCreateDate());

		XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(gregorianCalendarIssueDate.get(Calendar.YEAR), gregorianCalendarIssueDate.get(Calendar.MONTH) + 1,
				gregorianCalendarIssueDate.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
		issueDateType.setValue(date);
		argZatcaInvoiceObj.setIssueDate(issueDateType);

		// Setting transaction Issue time
		IssueTimeType issueTimeType = cbc.createIssueTimeType();
		XMLGregorianCalendar time = DatatypeFactory.newInstance().newXMLGregorianCalendarTime(gregorianCalendarIssueDate.get(Calendar.HOUR), gregorianCalendarIssueDate.get(Calendar.MINUTE),
				gregorianCalendarIssueDate.get(Calendar.SECOND), DatatypeConstants.FIELD_UNDEFINED);
		issueTimeType.setValue(time);
		argZatcaInvoiceObj.setIssueTime(issueTimeType);

		// setting Supplier
		SupplierPartyType supplierPartyType = cac.createSupplierPartyType();
		supplierPartyType.setParty(asqZatcaInvoiceGenerationHelper.setPartyType(loc, cbc, cac));
		argZatcaInvoiceObj.setAccountingSupplierParty(supplierPartyType);

		// Setting customer info (Optional)
		CustomerPartyType customerPartyType = cac.createCustomerPartyType();
		IParty party = currentSaleTrans.getCustomerParty();
		if (null != party) {
			customerPartyType.setParty(asqZatcaInvoiceGenerationHelper.setCustomerShippingAddress(party, cbc, cac));
			argZatcaInvoiceObj.setAccountingCustomerParty(customerPartyType);
			// setting customer mobile number
			asqZatcaInvoiceGenerationHelper.mapBuyerPhoneNumber(customerPartyType, party.getTelephone1(), cbc, cac);
		}

		// DocumentLevel Notes // tender total //not a mandatory
		if (null != currentSaleTrans.getTotal()) {
			NoteType notes = cbc.createNoteType();
			notes.setValue(String.valueOf(currentSaleTrans.getTotal()));
			argZatcaInvoiceObj.getNote().add(notes);
		}

		// retrieving hash value
		ArrayList<AdditionalDocumentReference> docList = new ArrayList<AdditionalDocumentReference>();
		try {
			List<AsqZatcaInvoiceHashQueryResult> invoiceHashQryResult = asqZatcaDatabaseHelper.getPrevInvoiceHashFromDB();

			if (invoiceHashQryResult != null && !invoiceHashQryResult.isEmpty()) {
				for (AsqZatcaInvoiceHashQueryResult queryResult : invoiceHashQryResult) {
					logger.debug("Fetching data from previousInvoiceHash from DB:" + invoiceHashQryResult.toString());
					AdditionalDocumentReference addDocPIH = new AdditionalDocumentReference("PIH", StringUtils.EMPTY, StringUtils.EMPTY);
					addDocPIH.setEmbeddedDocumentBinaryObject(String.valueOf(queryResult.get("InvoiceHash")));// invoice hash to be taken from constant file
					docList.add(addDocPIH);

					docList.add(new AdditionalDocumentReference("ICV", String.valueOf(nextICV), StringUtils.EMPTY));
					docList.add(addDocQR);
				}
			} else {
				logger.debug("PreviousHashFromDb is empty:" + invoiceHashQryResult.toString());
			}
		} catch (Exception exception) {
			logger.error("Returned null from getPreviousInvoiceHashFromDB method:" + exception);
		}

		for (AdditionalDocumentReference docRef : docList) {
			if (!"QR".equalsIgnoreCase(docRef.getId())) {
				argZatcaInvoiceObj.getAdditionalDocumentReference()
						.add(asqZatcaInvoiceGenerationHelper.setDocumentReferenceType(docRef.getId(), docRef.getUUID(), cbc, cac, docRef.getEmbeddedDocumentBinaryObject()));
			}
		}

		// It will we same as issue date for sale and for return it will be actual issue
		// date of Org transaction
		argZatcaInvoiceObj.getDelivery().add(asqZatcaInvoiceGenerationHelper.setDeliveryType(date, date, cbc, cac));

		List<IRetailTransactionLineItem> tenderLine = currentSaleTrans.getLineItemsByTypeCode(LineItemType.TENDER.getName());
		String tenderType = AsqZatcaConstant.ZATCA_TENDER_CASH_CODE;
		for (IRetailTransactionLineItem tenders : tenderLine) {
			if (tenders instanceof TenderLineItemModel) {
				TenderLineItemModel tenderModel = (TenderLineItemModel) tenders;
				Tender tender = tenderHelper.getTender(tenderModel.getTenderId(), Long.valueOf(_stationState.getWorkstationId()), null);
				if (null != tender && tender.getTenderType().getTenderTypecode().equalsIgnoreCase("CURRENCY")) {
					tenderType = AsqZatcaConstant.ZATCA_TENDER_CASH_CODE;
					// This tag will only populate when foreign currency is used
					if (!System.getProperty("dtv.location.CurrencyId").equalsIgnoreCase(tender.getCurrencyId())) {
						argZatcaInvoiceObj.getTaxTotal().add(asqZatcaInvoiceGenerationHelper.mapTotalTaxAmoutTag(String.valueOf(currentSaleTrans.getTaxAmount()), "SAR", cbc, cac));
					}
				} else if ("CREDIT_CARD".equalsIgnoreCase(tender.getTenderType().getTenderTypecode())) {
					tenderType = AsqZatcaConstant.ZATCA_TENDER_CREDIT_CODE;
				} else if ("DEBIT_CARD".equalsIgnoreCase(tender.getTenderType().getTenderTypecode())) {
					tenderType = AsqZatcaConstant.ZATCA_TENDER_DEBIT_CODE;
				} else if ("PAY_BY_LINK".equalsIgnoreCase(tender.getTenderType().getTenderTypecode())) {
					tenderType = AsqZatcaConstant.ZATCA_TENDER_PAYLINK_CODE;
				}
				argZatcaInvoiceObj.getPaymentMeans().add(asqZatcaInvoiceGenerationHelper.setPaymentMeans(tenderType, reasonCode, cbc, cac));
			}
		}

		List<TaxSubtotalType> taxSubtotalTypes = new ArrayList<>();
		BigDecimal taxableValue = null;
		List<IRetailTransactionLineItem> taxLines = currentSaleTrans.getLineItemsByTypeCode(LineItemType.TAX.getName());
		for (IRetailTransactionLineItem taxLine : taxLines) {
			if (taxLine instanceof TaxLineItemModel) {
				TaxLineItemModel taxLineModel = (TaxLineItemModel) taxLine;
				// get Taxable Amount
				taxableValue = taxLineModel.getTaxableAmount().subtract(taxLineModel.getTaxAmount());
				taxSubtotalTypes.add(asqZatcaInvoiceGenerationHelper.setTaxSubtotalType(taxLineModel.getCurrencyId(), taxLineModel.getTaxAmount(), taxableValue,
						asqZatcaInvoiceGenerationHelper.setTaxCategoryType(new String[] { AsqZatcaConstant.ZATCA_TAXCATEGORY_SCHEMEID }, new String[] { AsqZatcaConstant.ZATCA_SCHEME_AGENCYID },
								new String[] { "S" }, new BigDecimal[] { asqZatcaHelper.getFormatttedBigDecimalValue(taxLineModel.getTaxPercentage()) },
								new String[] { AsqZatcaConstant.ZATCA_TAXSCHEME_SCHEMEID }, new String[] { taxLineModel.getTaxGroupId() }, new String[] { AsqZatcaConstant.ZATCA_SCHEME_AGENCYID },
								// taxSubTotal.getTaxExemptionReasonCode(),
								// taxSubTotal.getTaxExemptionReason(),
								null, null, cbc, cac),
						cbc, cac));
			}
		}
		argZatcaInvoiceObj.getTaxTotal()
				.add(asqZatcaInvoiceGenerationHelper.setTaxTotalType(System.getProperty("dtv.location.CurrencyId"), currentSaleTrans.getTaxAmount(), null, taxSubtotalTypes, cbc, cac));

		// Need to set AllowanceTotalAmount for transaction level discount
		argZatcaInvoiceObj.setLegalMonetaryTotal(asqZatcaInvoiceGenerationHelper.setMonetaryTotalType(System.getProperty("dtv.location.CurrencyId"), taxableValue, taxableValue,
				currentSaleTrans.getTotal(), String.valueOf(new BigDecimal(0)), String.valueOf(new BigDecimal(0)), currentSaleTrans.getTotal(), String.valueOf(new BigDecimal(0)), cbc, cac));

		for (IRetailTransactionLineItem lineItem : currentSaleTrans.getSaleLineItems()) {
			if (lineItem instanceof SaleReturnLineItemModel) {
				// need to check on allowance charges
				SaleReturnLineItemModel salelineItem = (SaleReturnLineItemModel) lineItem;
				List<AllowanceChargeType> listAllowanceChargeType = new ArrayList<AllowanceChargeType>();
				if (null != lineItem && salelineItem.getDiscounted()) {
					listAllowanceChargeType.add(asqZatcaInvoiceGenerationHelper.setAllowanceChargeType("false", "Discount", salelineItem.getPreDealAmount(), salelineItem.getUnitPrice(),
							System.getProperty("dtv.location.CurrencyId"), salelineItem.getQuantity(), cbc, cac));
				}
				argZatcaInvoiceObj.getInvoiceLine().add(asqZatcaInvoiceGenerationHelper.setInvoiceLineType(salelineItem, cbc, cac));
			}
		}
		// This flow is for return need to set
		// if (null != invoiceData.getOriginalInvoiceNumbers() &&
		// !invoiceData.getOriginalInvoiceNumbers().isEmpty()) {
		// in.getBillingReference().add(asqZatcaInvoiceGenerationHelper.setBillingReference(invoiceData.getOriginalInvoiceNumbers()));
		// } else {
		// logger.info("OriginalInvoiceNumbers is null or empty in the invoice
		// Request");
		// }
	}
}
