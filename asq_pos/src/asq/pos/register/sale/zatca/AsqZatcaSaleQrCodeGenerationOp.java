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
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceRequest;
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
import dtv.util.StringUtils;
import dtv.xst.dao.crm.IParty;
import dtv.xst.dao.loc.IRetailLocation;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trl.IRetailTransactionLineItem;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.SupplierPartyType;
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
	private LocationFactory locFactory;

	@Inject
	private AsqZatcaDatabaseHelper asqZatcaDatabaseHelper;

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

		oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac = asqZatcaHelper
				.getZatcaOjectFactory(oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory.class);
		oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc = asqZatcaHelper
				.getZatcaOjectFactory(oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory.class);

		poulateXMLInvoiceType(currentSaleTrans, loc, zatcaInvoiceObj, cbc, cac, addDocQR, xmlIrnValue, xmlUUID, invoiceIssueDate, invoiceIssueTime);

		String intialTransInvoice = asqZatcaHelper.generateInvoiceXML(zatcaInvoiceObj);

		logger.debug(" ---------------------------Generate QR Starts---------------------- ");
		HashQRData data = asqZatcaInvoiceGenerationHelper.getHashAndQR(sellerName, sellerVATRegNumber, invoiceIssueTime, invoiceIssueDate, payableAmount, vatTotal, intialTransInvoice,
				AsqZatcaConstant.keySecret, AsqZatcaConstant.keyAlg, addDocQR, xmlUUID, xmlIrnValue, signatureData);

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

		AsqSubmitZatcaCertServiceRequest zatcaTransReq = new AsqSubmitZatcaCertServiceRequest();
		zatcaTransReq.setInvoiceHash(data.getInvoiceHash());
		zatcaTransReq.setUuid(xmlUUID);
		zatcaTransReq.setInvoice(base64.encodeToString(invoiceXML.getBytes()));

		// saving the invoice into staging table
		String tranZatcaJson = asqZatcaHelper.convertTojson(zatcaTransReq);
		asqZatcaDatabaseHelper.saveInvoiceInStaging(xmlIrnValue, _stationState.getCurrentBusinessDate(), Long.valueOf(_stationState.getWorkstationId()), currentSaleTrans.getTransactionSequence(),
				tranZatcaJson, data.getQR(), xmlUUID);

		// return the receipt QRcode
		return data.getQR();
	}

	private void poulateXMLInvoiceType(IRetailTransaction currentSaleTrans, IRetailLocation loc, InvoiceType argZatcaInvoiceObj,
			oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc, oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac,
			AdditionalDocumentReference addDocQR, String xmlIrnValue, String xmlUUID, String invoiceIssueDate, String invoiceIssueTime) throws ParseException, DatatypeConfigurationException {

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
		// B2C 02
		// B2B 01
		invoiceTypeCodeType.setName("0200000");
		invoiceTypeCodeType.setValue("388");
		// return 381
		// sale 388
		argZatcaInvoiceObj.setInvoiceTypeCode(invoiceTypeCodeType);

		// Setting the tender currency
		DocumentCurrencyCodeType documentCurrencyCodeType = cbc.createDocumentCurrencyCodeType();
		documentCurrencyCodeType.setValue("SAR");
		argZatcaInvoiceObj.setDocumentCurrencyCode(documentCurrencyCodeType);

		// Setting the tax currency
		TaxCurrencyCodeType taxCurrencyCodeType = cbc.createTaxCurrencyCodeType();
		taxCurrencyCodeType.setValue("SAR");
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

		// Setting Supplier
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
					addDocPIH.setEmbeddedDocumentBinaryObject(queryResult.get("InvoiceHash").toString());// invoice hash to be taken from constant file
					docList.add(addDocPIH);

					docList.add(new AdditionalDocumentReference("ICV", String.valueOf(queryResult.getIcv() + 1), StringUtils.EMPTY));
					docList.add(addDocQR);

				}
			} else {
				logger.debug("PreviousHashFromDb is empty:" + invoiceHashQryResult.toString());
			}
		} catch (Exception exception) {
			logger.error("Returned null from getPreviousInvoiceHashFromDB method:" + exception);
		}

		// This need to be set
		for (AdditionalDocumentReference docRef : docList) {
			if (!"QR".equalsIgnoreCase(docRef.getId())) {
				argZatcaInvoiceObj.getAdditionalDocumentReference()
						.add(asqZatcaInvoiceGenerationHelper.setDocumentReferenceType(docRef.getId(), docRef.getUUID(), cbc, cac, docRef.getEmbeddedDocumentBinaryObject()));
			}
		}

		// It will we same as issue date for sale and for return it will be actual issue
		// date of Org transaction
		argZatcaInvoiceObj.getDelivery().add(asqZatcaInvoiceGenerationHelper.setDeliveryType(invoiceIssueDate, invoiceIssueDate, cbc, cac));

		// 55 Debit card
		// 54 credit card
		// 10 for cash
		// 68 online Payment

		// Reason Code : It will be only for return . Their are 5 reason
		// - Cancellation or suspension of the supplies after its occurrence either
		// wholly or partially (تم إلغاء أو وقف التوريد بعد حدوثه أو اعتباره كلياً أو
		// جزئياً)
		// - In case of essential change or amendment in the supply, which leads to the
		// change of the VAT due (وجود تغيير أو تعديل جوهري في طبيعة التوريد بحيث يؤدي
		// الى تغيير الضريبة المستحقة)
		// - Amendment of the supply value which is pre-agreed upon between the supplier
		// and consumer (تم الاتفاق على تعديل قيمة التوريد مسبقاً)
		// - In case of goods or services refund. (عند ترجيع السلع أو الخدمات)
		// - In case of change in Seller's or Buyer's information (عند التعديل على
		// بيانات المورد أو المشتري)
		argZatcaInvoiceObj.getPaymentMeans().add(asqZatcaInvoiceGenerationHelper.setPaymentMeans("10", "Resaon Code", cbc, cac));

		// mapping TaxTotal nnennenne
		double totalTaxvalue = 0.0;
		// argZatcaInvoiceObj.getTaxTotal().add(asqZatcaInvoiceGenerationHelper.setTaxTotalType("SAR",
		// currentSaleTrans.getTaxAmount(), null,
		// asqZatcaInvoiceGenerationHelper.mapTaxSubtotal(currentSaleTrans.getSubtotal(),
		// cbc, cac), cbc, cac));

		// set tax total and currency IT WILL CAHNGE FOR frg CURRENCY
		argZatcaInvoiceObj.getTaxTotal().add(asqZatcaInvoiceGenerationHelper.mapTotalTaxAmoutTag(String.valueOf(currentSaleTrans.getTaxAmount()), "SAR", cbc, cac));

		// setting the transaction Amount
		argZatcaInvoiceObj.setLegalMonetaryTotal(asqZatcaInvoiceGenerationHelper.setMonetaryTotalType("SAR", String.valueOf(currentSaleTrans.getSubtotal()),
				String.valueOf(currentSaleTrans.getSubtotal()), String.valueOf(currentSaleTrans.getTotal()), String.valueOf(new BigDecimal(0)), String.valueOf(new BigDecimal(0)),
				String.valueOf(currentSaleTrans.getTotal()), String.valueOf(currentSaleTrans.getTotal()), cbc, cac));

		for (IRetailTransactionLineItem lineItem : currentSaleTrans.getSaleLineItems()) {
			/*
			 * need to check on allowance charges List<AllowanceChargeType>
			 * listAllowanceChargeType = new ArrayList<AllowanceChargeType>(); if (null !=
			 * lineItem.getItemAllowanceCharges()) { for (ItemAllowanceCharges
			 * itemAllowanceCharge : lineItem.getItemAllowanceCharges()) {
			 * listAllowanceChargeType.add(asqZatcaInvoiceGenerationHelper.
			 * setAllowanceChargeType(new
			 * String().valueOf(itemAllowanceCharge.isItemAllowanceChargeIndicator()),
			 * itemAllowanceCharge.getItemAllowanceChargeReason(), new
			 * BigDecimal(itemAllowanceCharge.getItemAllowanceChargeAmount()), new
			 * BigDecimal(itemAllowanceCharge.getItemBaseAmount()),
			 * lineItem.getItemPriceAmountCurrencyID(),
			 * itemAllowanceCharge.getItemMultiplierFactorNummeric(), cbc, cac)); } }
			 */
			argZatcaInvoiceObj.getInvoiceLine().add(asqZatcaInvoiceGenerationHelper.setInvoiceLineType(lineItem, cbc, cac));
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
