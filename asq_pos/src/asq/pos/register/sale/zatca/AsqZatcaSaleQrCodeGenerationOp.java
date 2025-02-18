package asq.pos.register.sale.zatca;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqValueKeys;
import asq.pos.register.sale.AsqHelper;
import asq.pos.zatca.AsqZatcaConstant;
import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import asq.pos.zatca.database.helper.AsqZatcaDatabaseHelper;
import asq.pos.zatca.database.helper.AsqZatcaInvoiceHashQueryResult;
import asq.pos.zatca.invoice.generation.op.AsqZatcaInvoiceGenerationHelper;
import asq.pos.zatca.invoice.generation.op.SmartHubUtil;
import asq.pos.zatca.invoice.generation.utils.ASQException;
import asq.pos.zatca.invoice.models.AdditionalDocumentReference;
import asq.pos.zatca.invoice.models.ItemAllowanceCharges;
import asq.pos.zatca.invoice.models.SignatureData;
import dtv.i18n.FormattableFactory;
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
import dtv.xst.dao.trl.IRetailPriceModifier;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trl.IRetailTransactionLineItem;
import dtv.xst.dao.trl.impl.SaleReturnLineItemModel;
import dtv.xst.dao.trl.impl.TaxLineItemModel;
import dtv.xst.dao.trn.PosTransactionId;
import dtv.xst.dao.ttr.impl.TenderLineItemModel;
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
	protected AsqHelper asqHelper;

	@Inject
	private AsqZatcaHelper asqZatcaHelper;

	@Inject
	private ReturnManager retunManager;

	@Inject
	private AsqZatcaDatabaseHelper asqZatcaDatabaseHelper;

	@Inject
	TenderHelper tenderHelper;

	@Inject
	FormattableFactory formattableFactory;

	@Override
	public IOpResponse handleOpExec(IXstEvent paramIXstEvent) {
		IRetailTransaction currentSaleTrans = _transactionScope.getTransaction(TransactionType.RETAIL_SALE);
		try {
			if (null != currentSaleTrans && !(AsqZatcaConstant.ASQ_ZATCA_RAIN_CHECK.equalsIgnoreCase(currentSaleTrans.getTransactionTypeCode()))) {
				String generatedQRcode = genZatacaInvoiceAndQRcode(currentSaleTrans);
				_transactionScope.setValue(AsqValueKeys.ASQ_ZATCA_QR_CODE, generatedQRcode);
			}
		} catch (Exception e) {
			logger.error("We have recieved Exception in genearting Zatca invoice and QR code for the current transaction  ", e);
		}
		return HELPER.completeResponse();
	}

	public String genZatacaInvoiceAndQRcode(IRetailTransaction currentSaleTrans) throws ASQException, Exception {
		logger.info("*******InvoiceRequest Unicode Value After Converting to Object :" + currentSaleTrans);

		InvoiceType zatcaInvoiceObj = new InvoiceType();

		GregorianCalendar gregorianCalendarIssueDate = new GregorianCalendar();
		gregorianCalendarIssueDate.setTime(currentSaleTrans.getCreateDate());

		// invoiceData.getInvoiceIssueDate()
		XMLGregorianCalendar invoiceIssueDate = asqZatcaHelper.getZatcaIssueDate(gregorianCalendarIssueDate);

		// invoiceData.getInvoiceIssueTime()
		XMLGregorianCalendar invoiceIssueTimeStamp = asqZatcaHelper.getZatcaIssueTime(gregorianCalendarIssueDate);

		// invoiceData.getPayableAmount
		String payableAmount = String.valueOf(asqZatcaHelper.getAbsoluteValue(currentSaleTrans.getAmountTendered()));

		// invoiceData.getAdditionalDocumentReference().get(i).getUUID()
		String xmlUUID = String.valueOf(currentSaleTrans.getObjectIdAsString());
		xmlUUID = xmlUUID.replace(AsqZatcaConstant.ASQ_UIID_CHAR_TOREP, AsqZatcaConstant.ASQ_UIID_CHAR_BEREP);

		// invoiceData.getIrn();
		String xmlIrnValue = asqZatcaHelper.getTransactionUIID(currentSaleTrans);

		// vatTotal
		String vatTotal = String.valueOf(asqZatcaHelper.getAbsoluteValue(currentSaleTrans.getTaxAmount()));

		Long nextICV = SequenceFactory.getNextLongValue(AsqZatcaConstant.ASQ_ZATCA_SEQ);

		oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac = asqZatcaHelper
				.getZatcaOjectFactory(oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory.class);
		oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc = asqZatcaHelper.getZatcaOjectFactory(oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory.class);

		poulateXMLInvoiceType(currentSaleTrans, zatcaInvoiceObj, cbc, cac, xmlIrnValue, xmlUUID, invoiceIssueDate, invoiceIssueTimeStamp, nextICV);

		String intialTransInvoice = asqZatcaHelper.generateInvoiceXML(zatcaInvoiceObj);

		// Getting the first hash for signature
		intialTransInvoice = SmartHubUtil.removeNewlineAndWhiteSpaces(intialTransInvoice);
		logger.debug("**********Initial XML Generated : " + intialTransInvoice);
		String hashedXML = asqZatcaInvoiceGenerationHelper.getInvoiceHash(intialTransInvoice);// generating hash
		logger.debug("**********Initial Invoice Hash : " + hashedXML);

		// Generating Signed Hash
		logger.debug("**********KeyStore Fetching from Properties - Path -{} -- Key- {} : ", AsqZatcaConstant.certificateFilePath, AsqZatcaConstant.keySecret);
		KeyStore ks = SmartHubUtil.getKeyStore(AsqZatcaConstant.certificateFilePath, AsqZatcaConstant.keySecret);
		byte[] signatureECDA = SmartHubUtil.sigData(ks, AsqZatcaConstant.keyAlg, AsqZatcaConstant.keySecret, AsqZatcaConstant.sigAlg, hashedXML);

		X509Certificate certificate = asqZatcaInvoiceGenerationHelper.getZatcaCertificate();

		SignatureData signatureData = asqZatcaInvoiceGenerationHelper.getSignatureData(intialTransInvoice, nextICV, currentSaleTrans.getCreateDate(), certificate, signatureECDA);
		UBLExtensionsType ublExtensionsType = asqZatcaHelper.getZatcaOjectFactory(oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ObjectFactory.class).createUBLExtensionsType();
		ublExtensionsType.getUBLExtension()
				.add(asqZatcaInvoiceGenerationHelper.getUBLExtension(AsqZatcaConstant.zatcaXMLExtenUri, AsqZatcaConstant.zatcaXMLSignInfoId, AsqZatcaConstant.zatcaXMLRefSignID,
						new String[] { AsqZatcaConstant.zatcaXMLTransAlgo, AsqZatcaConstant.zatcaXMLTransAlgo, AsqZatcaConstant.zatcaXMLTransAlgo, AsqZatcaConstant.zatcaXMLTransAlgo },
						new String[] { AsqZatcaConstant.zatcaXMLTagUBL, AsqZatcaConstant.zatcaXMLTagSignature, AsqZatcaConstant.zatcaXMLAddDoc, StringUtils.EMPTY }, cbc, signatureData));
		zatcaInvoiceObj.getSignature().add(asqZatcaInvoiceGenerationHelper.setSignatureType(AsqZatcaConstant.zatcaXMLRefSignID, AsqZatcaConstant.zatcaXMLExtenUri, cbc, cac));
		zatcaInvoiceObj.setUBLExtensions(ublExtensionsType);

		logger.debug(" ---------------------------Generate QR Starts---------------------- ");
		zatcaInvoiceObj.getAdditionalDocumentReference().add(asqZatcaInvoiceGenerationHelper.setDocumentReferenceType(AsqZatcaConstant.ASQ_QR_CODE, StringUtils.EMPTY, cbc, cac, AsqZatcaConstant.ASQ_ZATCA_QR_CODE_VALUE));

		String invoiceXML = SmartHubUtil.generateCompleteSignedInvoiceXML(zatcaInvoiceObj);
		invoiceXML = asqZatcaInvoiceGenerationHelper.generateFinalXML(invoiceXML, signatureData);
		// Generating final hash Value
		hashedXML = asqZatcaInvoiceGenerationHelper.getInvoiceHash(invoiceXML);

		Base64 base64 = new Base64();
		String signedHash = base64.encodeBase64String(signatureECDA);

		String qrCode = SmartHubUtil.generateQRCode(AsqZatcaConstant.companyLegalName, AsqZatcaConstant.companyVatNumber, invoiceIssueTimeStamp, invoiceIssueDate, payableAmount, vatTotal, hashedXML, signedHash,
				certificate.getPublicKey().getEncoded(), certificate.getSignature());

		logger.debug("*******QRCode Generated: {} ************", qrCode);
		if (null == qrCode) {
			logger.error("QR Code Generation Failed: QR Code is null or empty");
			throw new ASQException("QR Code Generation Failed: QR Code is null or empty");
		}
		invoiceXML = invoiceXML.replace(AsqZatcaConstant.ASQ_ZATCA_QR_CODE_VALUE_BYTE, qrCode);
		logger.debug(" ---------------------------Generate QR End---------------------- ");

		// saving the invoice into staging table
		asqZatcaDatabaseHelper.saveInvoiceInStaging(xmlIrnValue, _stationState.getCurrentBusinessDate(), Long.valueOf(_stationState.getWorkstationId()), currentSaleTrans.getTransactionSequence(),
				base64.encodeToString(invoiceXML.getBytes()), qrCode, xmlUUID, hashedXML, nextICV);

		// return the receipt QRcode
		return qrCode;
	}

	private void poulateXMLInvoiceType(IRetailTransaction currentSaleTrans, InvoiceType argZatcaInvoiceObj, oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc,
			oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac, String xmlIrnValue, String xmlUUID, XMLGregorianCalendar invoiceIssueDate, XMLGregorianCalendar invoiceIssueTime,
			Long nextICV) throws ParseException, DatatypeConfigurationException {

		// setting Profile ID
		ProfileIDType profileID = cbc.createProfileIDType();
		profileID.setValue(AsqZatcaConstant.ASQ_ZATCA_REPORTING);
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
		if (retunManager != null && null != retunManager.getCurrentReturnType()) {
			// This flow is for return need to set
			if (null != retunManager.getCurrentOrigTransactionId()) {
				PosTransactionId orgTrans = retunManager.getCurrentOrigTransactionId();
				argZatcaInvoiceObj.getBillingReference().add(asqZatcaInvoiceGenerationHelper.setBillingReference(String.valueOf(orgTrans.getTransactionSequence())));
			} else {
				logger.info("OriginalInvoiceNumbers is null or empty in the invoice Request");
			}
			reasonCode = AsqZatcaConstant.ZATCA_RETURN_REASON_CODE;
			invoiceTypeCodeType.setValue(AsqZatcaConstant.ZATCA_RETURN_CODE);
		}
		// B2C = 02 is for Business to Customer
		invoiceTypeCodeType.setName(AsqZatcaConstant.ZATCA_BUSINESS_TO_CUSTOMER);
		invoiceTypeCodeType.setValue(AsqZatcaConstant.ZATCA_SALE_CODE);
		argZatcaInvoiceObj.setInvoiceTypeCode(invoiceTypeCodeType);

		// Setting the tender currency
		// In case of Foreign currency it should be Foreign currency code
		DocumentCurrencyCodeType documentCurrencyCodeType = cbc.createDocumentCurrencyCodeType();
		documentCurrencyCodeType.setValue(AsqZatcaConstant.zatcaCurrencyID);
		argZatcaInvoiceObj.setDocumentCurrencyCode(documentCurrencyCodeType);

		// Setting the tax currency
		TaxCurrencyCodeType taxCurrencyCodeType = cbc.createTaxCurrencyCodeType();
		taxCurrencyCodeType.setValue(AsqZatcaConstant.zatcaCurrencyID);
		argZatcaInvoiceObj.setTaxCurrencyCode(taxCurrencyCodeType);

		// setting transaction Date
		IssueDateType issueDateType = cbc.createIssueDateType();
		issueDateType.setValue(invoiceIssueDate);
		argZatcaInvoiceObj.setIssueDate(issueDateType);

		// Setting transaction Issue time
		IssueTimeType issueTimeType = cbc.createIssueTimeType();
		issueTimeType.setValue(invoiceIssueTime);
		argZatcaInvoiceObj.setIssueTime(issueTimeType);

		// setting Supplier
		SupplierPartyType supplierPartyType = cac.createSupplierPartyType();
		supplierPartyType.setParty(asqZatcaInvoiceGenerationHelper.setPartyType(cbc, cac));
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
			notes.setValue(String.valueOf(asqZatcaHelper.getAbsoluteValue(currentSaleTrans.getTotal())));
			argZatcaInvoiceObj.getNote().add(notes);
		}

		// retrieving hash value
		ArrayList<AdditionalDocumentReference> docList = new ArrayList<AdditionalDocumentReference>();
		try {
			List<AsqZatcaInvoiceHashQueryResult> invoiceHashQryResult = asqZatcaDatabaseHelper.getPrevInvoiceHashFromDB();

			if (invoiceHashQryResult != null && !invoiceHashQryResult.isEmpty()) {
				for (AsqZatcaInvoiceHashQueryResult queryResult : invoiceHashQryResult) {
					logger.debug("Fetching data from previousInvoiceHash from DB:" + invoiceHashQryResult.toString());
					AdditionalDocumentReference addDocPIH = new AdditionalDocumentReference(AsqZatcaConstant.zatcaPreviousInvoiceHash, StringUtils.EMPTY, StringUtils.EMPTY);
					addDocPIH.setEmbeddedDocumentBinaryObject(String.valueOf(queryResult.get(AsqZatcaConstant.zatcaInvoiceHash)));// invoice hash to be taken from constant file
					docList.add(addDocPIH);

					docList.add(new AdditionalDocumentReference(AsqZatcaConstant.zatcaICV, String.valueOf(nextICV), StringUtils.EMPTY));
				}
			} else {
				logger.debug("PreviousHashFromDb is empty:" + invoiceHashQryResult);
			}
		} catch (Exception exception) {
			logger.error("Returned null from getPreviousInvoiceHashFromDB method:" + exception);
		}

		for (AdditionalDocumentReference docRef : docList) {
			if (!AsqZatcaConstant.ASQ_QR_CODE.equalsIgnoreCase(docRef.getId())) {
				argZatcaInvoiceObj.getAdditionalDocumentReference().add(asqZatcaInvoiceGenerationHelper.setDocumentReferenceType(docRef.getId(), docRef.getUUID(), cbc, cac, docRef.getEmbeddedDocumentBinaryObject()));
			}
		}

		// It will we same as issue date for sale and for return it will be actual issue
		// date of Org transaction
		argZatcaInvoiceObj.getDelivery().add(asqZatcaInvoiceGenerationHelper.setDeliveryType(invoiceIssueDate, invoiceIssueDate, cbc, cac));

		List<IRetailTransactionLineItem> tenderLine = currentSaleTrans.getLineItemsByTypeCode(LineItemType.TENDER.getName());
		String tenderType = AsqZatcaConstant.ZATCA_TENDER_CASH_CODE;
		for (IRetailTransactionLineItem tenders : tenderLine) {
			if (tenders instanceof TenderLineItemModel) {
				TenderLineItemModel tenderModel = (TenderLineItemModel) tenders;
				Tender tender = tenderHelper.getTender(tenderModel.getTenderId(), Long.valueOf(_stationState.getWorkstationId()), null);
				if (null != tender && null != tender.getTenderType()) {
					if (AsqZatcaConstant.ZATCA_TENDER_TYPE_CODE_CURRENCY.equalsIgnoreCase(tender.getTenderType().getTenderTypecode())) {
						tenderType = AsqZatcaConstant.ZATCA_TENDER_CASH_CODE;
						// This tag will only populate when foreign currency is used
						BigDecimal calculated = null;
						if (!AsqZatcaConstant.zatcaCurrencyID.equalsIgnoreCase(tender.getCurrencyId())) {
							// Atul Need to set Foreign currency amount in SAR
							calculated = currentSaleTrans.getTaxAmount();
						}
						argZatcaInvoiceObj.getTaxTotal()
								.add(asqZatcaInvoiceGenerationHelper.mapTotalTaxAmoutTag(String.valueOf(asqZatcaHelper.getAbsoluteValue(currentSaleTrans.getTaxAmount())), AsqZatcaConstant.zatcaCurrencyID, cbc, cac));
					} else if (AsqZatcaConstant.ZATCA_TENDER_TYPE_CODE_CREDIT.equalsIgnoreCase(tender.getTenderType().getTenderTypecode())) {
						tenderType = AsqZatcaConstant.ZATCA_TENDER_CREDIT_CODE;
					} else if (AsqZatcaConstant.ZATCA_TENDER_TYPE_CODE_DEBIT.equalsIgnoreCase(tender.getTenderType().getTenderTypecode())) {
						tenderType = AsqZatcaConstant.ZATCA_TENDER_DEBIT_CODE;
					} else if (AsqZatcaConstant.ZATCA_TENDER_TYPE_CODE_PAYBYLINK.equalsIgnoreCase(tender.getTenderType().getTenderTypecode())) {
						tenderType = AsqZatcaConstant.ZATCA_TENDER_PAYLINK_CODE;
					}
				}
				argZatcaInvoiceObj.getPaymentMeans().add(asqZatcaInvoiceGenerationHelper.setPaymentMeans(tenderType, reasonCode, cbc, cac));
			}
		}

		List<TaxSubtotalType> taxSubtotalTypes = new ArrayList<>();
		BigDecimal taxableValue = null;
		BigDecimal taxPerc = null;
		String taxCategory = null;
		List<IRetailTransactionLineItem> taxLines = currentSaleTrans.getLineItemsByTypeCode(LineItemType.TAX.getName());
		for (IRetailTransactionLineItem taxLine : taxLines) {
			if (taxLine instanceof TaxLineItemModel) {
				TaxLineItemModel taxLineModel = (TaxLineItemModel) taxLine;
				if (!taxLineModel.getVoid()) {
					// get Taxable Amount
					taxableValue = asqZatcaHelper.getAbsoluteValue(taxLineModel.getTaxableAmount()).subtract(asqZatcaHelper.getAbsoluteValue(taxLineModel.getTaxAmount()), MathContext.DECIMAL64);
					taxableValue = taxableValue.setScale(2, asqHelper.getSystemRoundingMode());
					taxPerc = taxLineModel.getTaxPercentage().setScale(2, asqHelper.getSystemRoundingMode());
					taxCategory = taxLineModel.getTaxGroupId();
					taxSubtotalTypes.add(asqZatcaInvoiceGenerationHelper.setTaxSubtotalType(taxLineModel.getCurrencyId(), asqZatcaHelper.getAbsoluteValue(taxLineModel.getTaxAmount()), taxableValue,
							asqZatcaInvoiceGenerationHelper.setTaxCategoryType(new String[] { AsqZatcaConstant.ZATCA_TAXCATEGORY_SCHEMEID }, new String[] { AsqZatcaConstant.ZATCA_SCHEME_AGENCYID },
									new String[] { AsqZatcaConstant.ZATCA_TAXCATEGORY_ID_VAL }, new BigDecimal[] { asqZatcaHelper.getFormatttedBigDecimalValue(taxPerc) },
									new String[] { AsqZatcaConstant.ZATCA_TAXSCHEME_SCHEMEID }, new String[] { taxLineModel.getTaxGroupId() }, new String[] { AsqZatcaConstant.ZATCA_SCHEME_AGENCYID },
									// taxSubTotal.getTaxExemptionReasonCode(),
									// taxSubTotal.getTaxExemptionReason(),
									null, null, cbc, cac),
							cbc, cac));
				}
			}
		}
		argZatcaInvoiceObj.getTaxTotal()
				.add(asqZatcaInvoiceGenerationHelper.setTaxTotalType(AsqZatcaConstant.zatcaCurrencyID, asqZatcaHelper.getAbsoluteValue(currentSaleTrans.getTaxAmount()), null, taxSubtotalTypes, cbc, cac));

		// Need to set AllowanceTotalAmount for transaction level discount
		BigDecimal discountAmount = new BigDecimal(0);
		argZatcaInvoiceObj
				.setLegalMonetaryTotal(asqZatcaInvoiceGenerationHelper.setMonetaryTotalType(AsqZatcaConstant.zatcaCurrencyID, taxableValue, taxableValue, asqZatcaHelper.getAbsoluteValue(currentSaleTrans.getTotal()),
						String.valueOf(discountAmount), String.valueOf(new BigDecimal(0)), asqZatcaHelper.getAbsoluteValue(currentSaleTrans.getTotal()), String.valueOf(new BigDecimal(0)), cbc, cac));

		for (IRetailTransactionLineItem lineItem : currentSaleTrans.getSaleLineItems()) {
			if (lineItem instanceof SaleReturnLineItemModel) {
				// need to check on allowance charges
				SaleReturnLineItemModel salelineItem = (SaleReturnLineItemModel) lineItem;
				BigDecimal priceAmount = salelineItem.getNetAmount();
				if (!salelineItem.getVoid()) {
					BigDecimal lineExtensionAmount = null;
					List<ItemAllowanceCharges> listAllowanceChargeType = new ArrayList<ItemAllowanceCharges>();
					if (salelineItem.getDiscounted() || (null != salelineItem.getRetailPriceModifiers() && salelineItem.getRetailPriceModifiers().size() > 0)) {
						List<IRetailPriceModifier> priceModelList = salelineItem.getRetailPriceModifiers();
						String discountPercent = "";
						BigDecimal discountPrice = null;
						for (IRetailPriceModifier priceModel : priceModelList) {
							// Zatca discount calculation handling starts
							// need to check with multiple discount scenario
							discountPrice = priceModel.getAmount();
							discountAmount = discountPrice.divide(taxPerc.add(new BigDecimal(1), MathContext.DECIMAL64), 2, asqHelper.getSystemRoundingMode());
							discountPercent = String.valueOf(asqZatcaHelper.getDiscountPercentage(priceAmount, discountAmount));

							priceAmount = salelineItem.getPreDealAmount();
							priceAmount = priceAmount.divide(taxPerc, 2, asqHelper.getSystemRoundingMode());
							// Zatca discount calculation handling ends
						}
						// extra check for rounding
						lineExtensionAmount = priceAmount.subtract(discountAmount, MathContext.DECIMAL64);
						if (lineExtensionAmount.compareTo(salelineItem.getPreDealAmount()) != 1) {
							discountAmount = discountPrice.divide(taxPerc.add(new BigDecimal(1), MathContext.DECIMAL64), 2, RoundingMode.DOWN);
							lineExtensionAmount = priceAmount.subtract(discountAmount, MathContext.DECIMAL64);
						}
						lineExtensionAmount = lineExtensionAmount.multiply(salelineItem.getQuantity());

						lineExtensionAmount = asqZatcaHelper.getAbsoluteValue(lineExtensionAmount);
						priceAmount = asqZatcaHelper.getAbsoluteValue(priceAmount);
						discountAmount = asqZatcaHelper.getAbsoluteValue(discountAmount);

						listAllowanceChargeType.add(new ItemAllowanceCharges(AsqZatcaConstant.ASQ_FALSE, AsqZatcaConstant.ASQ_DISCOUNT, String.valueOf(discountAmount), discountPercent, AsqZatcaConstant.zatcaCurrencyID,
								String.valueOf(priceAmount)));
					}
					argZatcaInvoiceObj.getInvoiceLine().add(asqZatcaInvoiceGenerationHelper.setInvoiceLineType(salelineItem, listAllowanceChargeType, cbc, cac, priceAmount, taxPerc, taxCategory, lineExtensionAmount));
				}
			}
		}
	}
}
