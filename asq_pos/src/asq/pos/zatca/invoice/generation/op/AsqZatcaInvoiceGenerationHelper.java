package asq.pos.zatca.invoice.generation.op;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.etsi.uri._01903.v1_3.CertIDListType;
import org.etsi.uri._01903.v1_3.CertIDType;
import org.etsi.uri._01903.v1_3.DigestAlgAndValueType;
import org.etsi.uri._01903.v1_3.QualifyingPropertiesType;
import org.etsi.uri._01903.v1_3.SignedPropertiesType;
import org.etsi.uri._01903.v1_3.SignedSignaturePropertiesType;
import org.w3._2000._09.xmldsig_.DigestMethodType;
import org.w3._2000._09.xmldsig_.X509IssuerSerialType;
import org.xml.sax.SAXException;

import asq.pos.zatca.AsqZatcaConstant;
import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceRequest;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceResponse;
import asq.pos.zatca.database.helper.AsqZatcaDatabaseHelper;
import asq.pos.zatca.invoice.generation.utils.ASQException;
import asq.pos.zatca.invoice.generation.utils.InvoiceHash;
import asq.pos.zatca.invoice.models.AdditionalDocumentReference;
import asq.pos.zatca.invoice.models.HashQRData;
import asq.pos.zatca.invoice.models.InvoiceData;
import asq.pos.zatca.invoice.models.ItemAllowanceCharges;
import asq.pos.zatca.invoice.models.LineItems;
import asq.pos.zatca.invoice.models.OutboundInvoice;
import asq.pos.zatca.invoice.models.SignatureData;
import asq.pos.zatca.invoice.models.TaxSubtotal;
import dtv.pos.customer.ICustomerHelper;
import dtv.util.StringUtils;
import dtv.util.sequence.SequenceFactory;
import dtv.xst.dao.crm.IParty;
import dtv.xst.dao.crm.IPartyLocaleInformation;
import dtv.xst.dao.loc.IRetailLocation;
import dtv.xst.dao.trl.impl.SaleReturnLineItemModel;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AddressType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AllowanceChargeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AttachmentType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.BillingReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ContactType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CountryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.DeliveryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.DocumentReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.InvoiceLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ItemType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.MonetaryTotalType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyIdentificationType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyLegalEntityType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyTaxSchemeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PaymentMeansType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PriceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.SignatureType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxCategoryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxSchemeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxSubtotalType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxTotalType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ActualDeliveryDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AdditionalStreetNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AllowanceChargeReasonType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AllowanceTotalAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BaseAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BuildingNumberType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ChargeIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CityNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CitySubdivisionNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CompanyIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CountrySubentityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DistrictType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EmbeddedDocumentBinaryObjectType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IdentificationCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InstructionNoteType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InvoicedQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LineExtensionAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MultiplierFactorNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PayableAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PayableRoundingAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaymentMeansCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PercentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PlotIdentificationType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PostalZoneType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PrepaidAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PriceAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RegistrationNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RoundingAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SignatureMethodType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.StreetNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxExclusiveAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxExemptionReasonCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxExemptionReasonType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxInclusiveAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxableAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TelephoneType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.UUIDType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ExtensionContentType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ExtensionURIType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;
import oasis.names.specification.ubl.schema.xsd.commonsignaturecomponents_2.UBLDocumentSignaturesType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import oasis.names.specification.ubl.schema.xsd.signatureaggregatecomponents_2.SignatureInformationType;
import oasis.names.specification.ubl.schema.xsd.signaturebasiccomponents_2.ReferencedSignatureIDType;

public class AsqZatcaInvoiceGenerationHelper {

	@Inject
	private AsqZatcaHelper asqZatcaHelper;

	@Inject
	private AsqZatcaDatabaseHelper asqZatcaDatabaseHelper;

	@Inject
	ICustomerHelper customerHelper;

	private static final Logger logger = LogManager.getLogger(AsqZatcaInvoiceGenerationHelper.class);
	final String QR = "QR";
	final String EmptyString = "";
	final String SourceSystem = "POS";
	String invoiceOutboundFolder = "";
	private String transactionDateTime;
	private String uuid;

	/**
	 * Removing and replacing the Unicode and backslash character from the input
	 * invoice JSON
	 * 
	 * @param argInputInvoice
	 * @return
	 * @throws ASQException
	 */
	private String removetheUnexpectedElementsFromInvoice(String argInputInvoice) throws ASQException {
		String invoiceJson = null;
		if (null == argInputInvoice) {
			throw new ASQException("Invoice Cannot be null or empty ** input Json ** " + argInputInvoice);
		}
		invoiceJson = argInputInvoice.replaceAll(AsqZatcaConstant.uniCodeChartoFind, AsqZatcaConstant.uniCodeChartoReplace);
		invoiceJson = invoiceJson.replaceAll(AsqZatcaConstant.backSlashChartoFind, AsqZatcaConstant.backSlashChartoReplace);
		;
		logger.info("*******InvoiceRequest After Replacing UniCode :" + invoiceJson);
		return invoiceJson;
	}

	/**
	 * This method creates a new out bound JSON invoice after validating the
	 * required fields and throws exception if empty
	 * 
	 * @param argInputInvoiceData
	 * @return
	 * @throws ASQException
	 * @throws ParseException
	 */
	private OutboundInvoice creatingOutBoundInvoiceJSON(InvoiceData argInputInvoiceData) throws ASQException, ParseException {
		logger.info("Start the Proccess of creating OutBoundInvoice");
		OutboundInvoice outGoingInvoice = new OutboundInvoice();
		if (null == argInputInvoiceData) {
			throw new ASQException("Invoice Cannot be null or empty ** input Json ** " + argInputInvoiceData);
		}
		if (argInputInvoiceData.getStoreId() == null || argInputInvoiceData.getStoreId().isEmpty()) {
			throw new ASQException("StoreID Cannot be null or empty");
		}
		if (argInputInvoiceData.getSellerName() == null || argInputInvoiceData.getSellerName().isEmpty()) {
			throw new ASQException("SellerName Cannot be null or empty");
		}
		if (argInputInvoiceData.getSellerVATRegNumber() == null || argInputInvoiceData.getSellerVATRegNumber().isEmpty()) {
			throw new ASQException("SellerVATRegNumber Cannot be null or empty");
		}
		outGoingInvoice.setStoreID(argInputInvoiceData.getStoreId());
		if (argInputInvoiceData.getRegisterId() == null || argInputInvoiceData.getRegisterId().isEmpty()) {
			throw new ASQException("RegisterId Cannot be null or empty");
		}
		outGoingInvoice.setRegisterID(argInputInvoiceData.getRegisterId());
		if (argInputInvoiceData.getIrn() == null || argInputInvoiceData.getIrn().isEmpty()) {
			throw new ASQException("Irn Cannot be null or empty");
		}
		outGoingInvoice.setInvoiceNumber(argInputInvoiceData.getIrn());
		if (argInputInvoiceData.getInvoiceIssueDate() == null || argInputInvoiceData.getInvoiceIssueDate().isEmpty()) {
			throw new ASQException("InvoiceIssueDate Cannot be null or empty");
		}
		if (argInputInvoiceData.getInvoiceIssueTime() == null || argInputInvoiceData.getInvoiceIssueTime().isEmpty()) {
			throw new ASQException("getInvoiceIssueTime Cannot be null or empty");
		}
		outGoingInvoice.setBusinessDate(argInputInvoiceData.getInvoiceIssueDate());

		transactionDateTime = SmartHubUtil.dateAndTimeFormatChange(argInputInvoiceData.getInvoiceIssueDate(), argInputInvoiceData.getInvoiceIssueTime());
		outGoingInvoice.setTransactionDateTime(transactionDateTime);
		return outGoingInvoice;
	}

	/**
	 * 
	 * @param invoiceData
	 * @param invoiceXmlString
	 * @return
	 * @throws Exception
	 * @throws ASQException
	 */
	public AsqSubmitZatcaCertServiceResponse generateSampleRegInvoice(InvoiceType invoiceData, String invoiceXmlString) throws ASQException, Exception {
		logger.info("*******InvoiceRequest Unicode Value After Converting to Object :" + invoiceData.getSellerSupplierParty());

		AdditionalDocumentReference addDocQR = new AdditionalDocumentReference("QR", StringUtils.EMPTY, StringUtils.EMPTY);

		String sellerName = invoiceData.getAccountingSupplierParty().getParty().getPartyLegalEntity().get(0).getRegistrationName().getValue();

		// invoiceData.getSellerVATRegNumber()
		String sellerVATRegNumber = invoiceData.getAccountingSupplierParty().getParty().getPartyTaxScheme().get(0).getCompanyID().getValue();

		// invoiceData.getInvoiceIssueTime()
		String invoiceIssueTime = invoiceData.getIssueTime().getValue().toString();

		// invoiceData.getInvoiceIssueDate()
		String invoiceIssueDate = invoiceData.getIssueDate().getValue().toString();

		// invoiceData.getPayableAmount
		String payableAmount = invoiceData.getNote().get(0).getValue();

		// invoiceData.getAdditionalDocumentReference().get(i).getUUID()
		String xmlUUID = invoiceData.getUUID().getValue();

		// vatTotal
		String vatTotal = String.valueOf(invoiceData.getTaxTotal().get(0).getTaxAmount().getValue());

		// invoiceData.getIrn();
		String xmlIrnValue = invoiceData.getID().getValue();

		Long nextICV = SequenceFactory.getNextLongValue("ASQ_ZATCA_SEQ");

		SignatureData signatureData = new SignatureData();

		logger.debug(" ---------------------------Generate QR Starts---------------------- ");
		HashQRData data = getHashAndQR(sellerName, sellerVATRegNumber, invoiceIssueTime, invoiceIssueDate, payableAmount, vatTotal, invoiceXmlString, AsqZatcaConstant.keySecret,
				AsqZatcaConstant.keyAlg, addDocQR, xmlUUID, xmlIrnValue, signatureData, nextICV);

		if (data.isCertificateExpired()) {
			logger.error("*******Certificate Expired************");
			logger.error("*******Certificate Valid Up to: {} ************", data.getExpirationDate());
			return SmartHubUtil.generateCertificateExpiredErrorResponse(data.getExpirationDate());
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
		oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac = asqZatcaHelper
				.getZatcaOjectFactory(oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory.class);
		oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc = asqZatcaHelper
				.getZatcaOjectFactory(oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory.class);

		invoiceData.getAdditionalDocumentReference().add(setDocumentReferenceType(QR, EmptyString, cbc, cac, data.getQR()));
		invoiceData.getSignature().add(setSignatureType(System.getProperty("asq.pos.invoice.referencedSignatureID"), System.getProperty("asq.pos.invoice.extensionURI"), cbc, cac));

		UBLExtensionsType ublExtensionsType = asqZatcaHelper.getZatcaOjectFactory(oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ObjectFactory.class).createUBLExtensionsType();
		ublExtensionsType.getUBLExtension()
				.add(getUBLExtension(System.getProperty("asq.pos.invoice.extensionURI"), System.getProperty("asq.pos.invoice.signatureInformationID"),
						System.getProperty("asq.pos.invoice.referencedSignatureID"),
						new String[] { System.getProperty("asq.pos.invoice.transformsAlgorithm"), System.getProperty("asq.pos.invoice.transformsAlgorithm"),
								System.getProperty("asq.pos.invoice.transformsAlgorithm"), System.getProperty("asq.pos.invoice.transformsAlgorithm") },
						// createTransformTypeXPath
						new String[] { System.getProperty("asq.pos.invoice.xpathTagUBLExtensions"), System.getProperty("asq.pos.invoice.xpathTagSignature"),
								System.getProperty("asq.pos.invoice.xpathTagAdditionalDocRef"), EmptyString },
						cbc, signatureData));

		invoiceData.setUBLExtensions(ublExtensionsType);
		String invoiceXML = generateFinalXML(invoiceData, data.getQR(), signatureData);

		Base64 base64 = new Base64();
		AsqSubmitZatcaCertServiceRequest oi = new AsqSubmitZatcaCertServiceRequest();
		oi.setInvoiceHash(data.getInvoiceHash());
		oi.setUuid(xmlUUID);
		oi.setInvoice(base64.encodeToString(invoiceXML.getBytes()));
		SmartHubUtil.writeInvoiceJSON(invoiceData.getID().getValue(), invoiceIssueDate, invoiceIssueTime, oi);
		return new AsqSubmitZatcaCertServiceResponse();
	}

	/**
	 * 
	 * @param id
	 * @param uuid
	 * @param cbc
	 * @param cac
	 * @param attachments
	 * @return
	 */
	public DocumentReferenceType setDocumentReferenceType(String id, String uuid, oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc,
			oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac, String... attachments) {
		Base64 base64 = new Base64();
		DocumentReferenceType documentReferenceType = cac.createDocumentReferenceType();
		IDType idType = cbc.createIDType();
		idType.setValue(id);
		UUIDType uuidType = cbc.createUUIDType();
		uuidType.setValue(uuid);
		AttachmentType attachmentType = cac.createAttachmentType();
		EmbeddedDocumentBinaryObjectType embeddedDocumentBinaryObject = cbc.createEmbeddedDocumentBinaryObjectType();

		documentReferenceType.setID(idType);
		if (id.equalsIgnoreCase("ICV")) {
			documentReferenceType.setUUID(uuidType);
		}
		for (String attachment : attachments) {
			embeddedDocumentBinaryObject.setMimeCode("text/plain");
			if (id.equalsIgnoreCase("PIH")) {
				embeddedDocumentBinaryObject.setValue(base64.decode(attachment));
			} else if (id.equalsIgnoreCase("QR")) {
				embeddedDocumentBinaryObject.setValue("QR".getBytes());
			} else {
				embeddedDocumentBinaryObject.setValue(attachment.getBytes());
			}
			attachmentType.setEmbeddedDocumentBinaryObject(embeddedDocumentBinaryObject);
			if (!id.equalsIgnoreCase("ICV")) {
				documentReferenceType.setAttachment(attachmentType);
			}
		}
		return documentReferenceType;
	}

	/**
	 * 
	 * @param id
	 * @param signMethod
	 * @param cbc
	 * @param cac
	 * @return
	 */
	public SignatureType setSignatureType(String id, String signMethod, oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc,
			oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {
		SignatureType signatureType = cac.createSignatureType();
		SignatureMethodType signatureMethodType = cbc.createSignatureMethodType();
		IDType idType = cbc.createIDType();
		idType.setValue(id);
		signatureMethodType.setValue(signMethod);
		signatureType.setID(idType);
		signatureType.setSignatureMethod(signatureMethodType);
		return signatureType;
	}

	/**
	 * In this method we setup the store address and it zatca information
	 * 
	 * @param IRetailLocation
	 * @param cbc
	 * @param cac
	 * @return
	 */
	public PartyType setPartyType(IRetailLocation loc, oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc,
			oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {

		PartyType partyType = cac.createPartyType();

		// setting store party PartyIdentification
		PartyIdentificationType partyIdentificationType = cac.createPartyIdentificationType();
		IDType idType = cbc.createIDType();
		idType.setSchemeID("CRN");
		idType.setValue(System.getProperty("asq.zatca.company.crn.number"));
		partyIdentificationType.setID(idType);
		partyType.getPartyIdentification().add(partyIdentificationType);

		// setting the store address
		AddressType addressType = cac.createAddressType();
		StreetNameType streetNameType = cbc.createStreetNameType();
		streetNameType.setValue(loc.getAddress1());
		addressType.setStreetName(streetNameType);

		/*
		 * if (loc.getAddress2() != null) { AdditionalStreetNameType
		 * additionalStreetNameType = cbc.createAdditionalStreetNameType();
		 * additionalStreetNameType.setValue(loc.getAddress2()); }
		 */

		BuildingNumberType buildingNumberType = cbc.createBuildingNumberType();
		buildingNumberType.setValue(loc.getAddress2());
		addressType.setBuildingNumber(buildingNumberType);

		PlotIdentificationType plotIdentificationType = cbc.createPlotIdentificationType();
		plotIdentificationType.setValue(loc.getAddress3());
		addressType.setPlotIdentification(plotIdentificationType);

		CitySubdivisionNameType citySubdivisionName = cbc.createCitySubdivisionNameType();
		citySubdivisionName.setValue(loc.getAddress4());
		addressType.setCitySubdivisionName(citySubdivisionName);

		CityNameType cityNameType = cbc.createCityNameType();
		cityNameType.setValue(loc.getCity());
		addressType.setCityName(cityNameType);

		PostalZoneType postalZoneType = cbc.createPostalZoneType();
		postalZoneType.setValue(loc.getPostalCode());
		addressType.setPostalZone(postalZoneType);

		CountrySubentityType countrySubentityType = cbc.createCountrySubentityType();
		countrySubentityType.setValue(loc.getDistrict());
		addressType.setCountrySubentity(countrySubentityType);

		CountryType countryType = cac.createCountryType();
		IdentificationCodeType identificationCodeType = cbc.createIdentificationCodeType();
		identificationCodeType.setValue(loc.getCountry());
		countryType.setIdentificationCode(identificationCodeType);
		addressType.setCountry(countryType);

		partyType.setPostalAddress(addressType);

		// setting PartyTaxSchemeType starts
		PartyTaxSchemeType partyTaxSchemeType = cac.createPartyTaxSchemeType();

		CompanyIDType companyIDType = cbc.createCompanyIDType();
		companyIDType.setValue(System.getProperty("asq.zatca.company.vat.reg.number"));
		partyTaxSchemeType.setCompanyID(companyIDType);

		TaxSchemeType taxSchemeType = cac.createTaxSchemeType();
		IDType taxIdType = cbc.createIDType();
		taxIdType.setValue("VAT");
		taxSchemeType.setID(taxIdType);
		partyTaxSchemeType.setTaxScheme(taxSchemeType);

		partyType.getPartyTaxScheme().add(partyTaxSchemeType);
		// setting PartyTaxSchemeType ends

		// Setting Zatca Customer Reg Name
		PartyLegalEntityType partyLegalEntityType = cac.createPartyLegalEntityType();
		RegistrationNameType registrationNameType = cbc.createRegistrationNameType();
		registrationNameType.setValue(System.getProperty("asq.zatca.company.legalenity.name"));
		partyLegalEntityType.setRegistrationName(registrationNameType);
		partyType.getPartyLegalEntity().add(partyLegalEntityType);

		return partyType;
	}

	/**
	 * 
	 * @param id
	 * @param schemaType
	 * @param streetName
	 * @param additionalStreetName
	 * @param buildingNumber
	 * @param plotIdentification
	 * @param citySubdivision
	 * @param city
	 * @param postalZone
	 * @param district
	 * @param state
	 * @param countryCode
	 * @param taxScheme
	 * @param buyerName
	 * @param cbc
	 * @param cac
	 * @return
	 */
	public PartyType setCustomerShippingAddress(IParty party, oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc,
			oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {

		PartyType partyType = cac.createPartyType();

		PartyIdentificationType partyIdentificationType = cac.createPartyIdentificationType();
		IDType idType = cbc.createIDType();
		idType.setSchemeID("CRN");
		idType.setValue(party.getCustomerId());
		partyIdentificationType.setID(idType);
		partyType.getPartyIdentification().add(partyIdentificationType);

		IPartyLocaleInformation custAddress = party.getLocaleInformation().get(0);
		AddressType addressType = cac.createAddressType();

		if (null != custAddress.getAddress1()) {
			StreetNameType streetNameType = cbc.createStreetNameType();
			streetNameType.setValue(custAddress.getAddress1());
			addressType.setStreetName(streetNameType);
		}
		if (null != custAddress.getAddress2()) {
			AdditionalStreetNameType additionalStreetNameType = cbc.createAdditionalStreetNameType();
			additionalStreetNameType.setValue(custAddress.getAddress2());
			addressType.setAdditionalStreetName(additionalStreetNameType);
		}

		if (null != custAddress.getApartment()) {
			BuildingNumberType buildingNumberType = cbc.createBuildingNumberType();
			buildingNumberType.setValue(custAddress.getApartment());
			addressType.setBuildingNumber(buildingNumberType);
		}

		if (null != custAddress.getAddress3()) {
			PlotIdentificationType plotIdentificationType = cbc.createPlotIdentificationType();
			plotIdentificationType.setValue(custAddress.getAddress3());
			addressType.setPlotIdentification(plotIdentificationType);
		}

		if (null != custAddress.getCity()) {
			CitySubdivisionNameType citySubdivisionName = cbc.createCitySubdivisionNameType();
			citySubdivisionName.setValue(custAddress.getCity());
			addressType.setCitySubdivisionName(citySubdivisionName);
		}

		if (null != custAddress.getCity()) {
			CityNameType cityNameType = cbc.createCityNameType();
			cityNameType.setValue(custAddress.getCity());
			addressType.setCityName(cityNameType);
		}

		if (null != custAddress.getPostalCode()) {
			PostalZoneType postalZoneType = cbc.createPostalZoneType();
			postalZoneType.setValue(custAddress.getPostalCode());
			addressType.setPostalZone(postalZoneType);
		}

		if (null != custAddress.getState()) {
			DistrictType districtType = cbc.createDistrictType();
			districtType.setValue(custAddress.getState());
			addressType.setDistrict(districtType);
		}

		if (null != custAddress.getState()) {
			CountrySubentityType countrySubentityType = cbc.createCountrySubentityType();
			countrySubentityType.setValue(custAddress.getState());
			addressType.setCountrySubentity(countrySubentityType);
		}

		if (null != custAddress.getCountry()) {
			CountryType countryType = cac.createCountryType();
			IdentificationCodeType identificationCodeType = cbc.createIdentificationCodeType();
			identificationCodeType.setValue(custAddress.getCountry());
			countryType.setIdentificationCode(identificationCodeType);
			addressType.setCountry(countryType);
		}

		PartyTaxSchemeType partyTaxSchemeType = cac.createPartyTaxSchemeType();
		TaxSchemeType taxSchemeType = cac.createTaxSchemeType();
		IDType taxIdType = cbc.createIDType();
		taxIdType.setValue("VAT");
		taxSchemeType.setID(taxIdType);
		partyTaxSchemeType.setTaxScheme(taxSchemeType);
		partyType.getPartyTaxScheme().add(partyTaxSchemeType);

		partyType.setPostalAddress(addressType);

		if (null != party.getFirstName()) {
			PartyLegalEntityType partyLegalEntityType = cac.createPartyLegalEntityType();
			RegistrationNameType registrationName = cbc.createRegistrationNameType();
			registrationName.setValue(party.getFirstName());
			partyLegalEntityType.setRegistrationName(registrationName);
			partyType.getPartyLegalEntity().add(partyLegalEntityType);
		}
		return partyType;
	}

	/**
	 * 
	 * @param actualDeliveryDate
	 * @param latestDeliveryDate
	 * @param cbc
	 * @param cac
	 * @throws DatatypeConfigurationException
	 * @throws ParseException
	 * @return deliveryType
	 */

	public DeliveryType setDeliveryType(XMLGregorianCalendar actualDeliveryDate, XMLGregorianCalendar latestDeliveryDate,
			oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc, oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac)
			throws DatatypeConfigurationException, ParseException {
		DeliveryType deliveryType = cac.createDeliveryType();
		if (null != actualDeliveryDate) {
			ActualDeliveryDateType actualDeliveryDateType = cbc.createActualDeliveryDateType();
			actualDeliveryDateType.setValue(actualDeliveryDate);
			deliveryType.setActualDeliveryDate(actualDeliveryDateType);
		} else if (null != latestDeliveryDate) {
			ActualDeliveryDateType actualDeliveryDateType = cbc.createActualDeliveryDateType();
			actualDeliveryDateType.setValue(latestDeliveryDate);
			deliveryType.setActualDeliveryDate(actualDeliveryDateType);
		}
		return deliveryType;
	}

	/**
	 * 
	 * @param PaymentMeansCode
	 * @param creditDebitReason
	 * @param cbc
	 * @param cac
	 * @return
	 */
	public PaymentMeansType setPaymentMeans(String PaymentMeansCode, String creditDebitReason, oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc,
			oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {
		PaymentMeansType paymentMeansType = cac.createPaymentMeansType();
		PaymentMeansCodeType paymentMeansCodeType = cbc.createPaymentMeansCodeType();
		paymentMeansCodeType.setValue(PaymentMeansCode);
		paymentMeansType.setPaymentMeansCode(paymentMeansCodeType);

		if (null != creditDebitReason && !creditDebitReason.isEmpty()) {
			InstructionNoteType instructionNote = cbc.createInstructionNoteType();
			instructionNote.setValue(creditDebitReason);
			paymentMeansType.getInstructionNote().add(instructionNote);
		} else {
			logger.info("creditDebitReason is null or empty in the invoice Request");
		}
		return paymentMeansType;
	}

	/**
	 * 
	 * @param chargeIndicator
	 * @param allowanceChargeReason
	 * @param currencyID
	 * @param amount
	 * @param taxCategoryId
	 * @param categorySchemeAgencyID
	 * @param schemeAgencyID
	 * @param taxCategorySchemeID
	 * @param taxSchemeID
	 * @param Percent
	 * @param taxSchemeId
	 * @param cbc
	 * @param cac
	 * @return
	 */
	public AllowanceChargeType setAllowanceChargeType(String chargeIndicator, String allowanceChargeReason, String currencyID, String amount, String taxCategoryId, String categorySchemeAgencyID,
			String schemeAgencyID, String taxCategorySchemeID, String taxSchemeID, String Percent, String taxSchemeId,
			oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc, oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {
		AllowanceChargeType allowanceChargeType = cac.createAllowanceChargeType();
		TaxCategoryType taxCategoryType = cac.createTaxCategoryType();
		TaxSchemeType taxSchemeType = cac.createTaxSchemeType();
		ChargeIndicatorType chargeIndicatorType = cbc.createChargeIndicatorType();
		AllowanceChargeReasonType allowanceChargeReasonType = cbc.createAllowanceChargeReasonType();
		AmountType amountType = cbc.createAmountType();
		IDType taxIdType = cbc.createIDType();
		PercentType PercentType = cbc.createPercentType();
		IDType taxSchemeIdType = cbc.createIDType();
		chargeIndicatorType.setValue(Boolean.parseBoolean(chargeIndicator));
		allowanceChargeReasonType.setValue(allowanceChargeReason);
		amountType.setCurrencyID(currencyID);
		amountType.setValue(new BigDecimal(amount));
		taxIdType.setValue(taxCategoryId);
		taxIdType.setSchemeAgencyID(categorySchemeAgencyID);
		taxIdType.setSchemeID(taxCategorySchemeID);
		taxCategoryType.setID(taxIdType);
		taxSchemeIdType.setValue(taxSchemeId);
		taxSchemeIdType.setSchemeAgencyID(schemeAgencyID);
		taxSchemeIdType.setSchemeID(taxSchemeID);
		taxSchemeType.setID(taxSchemeIdType);
		PercentType.setValue(BigDecimal.valueOf(Long.valueOf(Percent)));
		taxCategoryType.setPercent(PercentType);
		taxCategoryType.setTaxScheme(taxSchemeType);
		allowanceChargeType.setChargeIndicator(chargeIndicatorType);
		allowanceChargeType.getAllowanceChargeReason().add(allowanceChargeReasonType);
		allowanceChargeType.setAmount(amountType);
		allowanceChargeType.getTaxCategory().add(taxCategoryType);

		return allowanceChargeType;
	}

	/**
	 * 
	 * @param currencyID
	 * @param taxAmount
	 * @param roundingAmount
	 * @param taxSubtotalTypes
	 * @param cbc
	 * @param cac
	 * @return
	 */
	public TaxTotalType setTaxTotalType(String currencyID, BigDecimal taxAmount, BigDecimal roundingAmount, List<TaxSubtotalType> taxSubtotalTypes,
			oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc, oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {

		TaxTotalType taxTotalType = cac.createTaxTotalType();
		TaxAmountType taxAmountType = cbc.createTaxAmountType();
		RoundingAmountType roundingAmountType = cbc.createRoundingAmountType();

		taxAmountType.setValue(taxAmount);
		taxAmountType.setCurrencyID(currencyID);
		taxTotalType.setTaxAmount(taxAmountType);

		if (taxSubtotalTypes != null) {
			for (TaxSubtotalType taxSubtotalType : taxSubtotalTypes) {
				taxTotalType.getTaxSubtotal().add(taxSubtotalType);
			}
		}

		if (roundingAmount != null) {
			roundingAmountType.setValue(roundingAmount);
			roundingAmountType.setCurrencyID(currencyID);
			taxTotalType.setRoundingAmount(roundingAmountType);
		}

		return taxTotalType;
	}

	/**
	 * 
	 * @param taxCategorySchemeID
	 * @param taxCategorySchemeAgencyID
	 * @param taxCategoryID
	 * @param taxPercent
	 * @param taxSchemeID
	 * @param taxSchemeValue
	 * @param taxSchemeAgencyID
	 * @param taxExemptionReasonCode
	 * @param taxExemptionReason
	 * @param cbc
	 * @param cac
	 * @return
	 */
	public TaxCategoryType setTaxCategoryType(String taxCategorySchemeID[], String taxCategorySchemeAgencyID[], String taxCategoryID[], BigDecimal taxPercent[], String taxSchemeID[],
			String taxSchemeValue[], String taxSchemeAgencyID[], String taxExemptionReasonCode, String taxExemptionReason,
			oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc, oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {

		TaxCategoryType taxCategoryType = cac.createTaxCategoryType();
		for (int i = 0; i < taxCategorySchemeID.length; i++) {
			IDType idType = cbc.createIDType();
			PercentType percentType = cbc.createPercentType();

			idType.setValue(taxCategoryID[i]);
			idType.setSchemeAgencyID(taxCategorySchemeAgencyID[i]);
			idType.setSchemeID(taxCategorySchemeID[i]);
			percentType.setValue(taxPercent[i]);
			taxCategoryType.setID(idType);
			taxCategoryType.setPercent(percentType);

			for (int j = 0; j < taxSchemeID.length; j++) {
				TaxSchemeType taxSchemeType = cac.createTaxSchemeType();
				IDType schemeIdType = cbc.createIDType();
				schemeIdType.setValue(taxSchemeValue[i]);
				schemeIdType.setSchemeAgencyID(taxSchemeAgencyID[i]);
				schemeIdType.setSchemeID(taxSchemeID[i]);

				taxSchemeType.setID(schemeIdType);
				taxCategoryType.setTaxScheme(taxSchemeType);
			}

		}

		if (null != taxExemptionReasonCode && !taxExemptionReasonCode.isEmpty()) {
			TaxExemptionReasonCodeType taxExceptionCodeType = cbc.createTaxExemptionReasonCodeType();
			taxExceptionCodeType.setValue(taxExemptionReasonCode);
			taxCategoryType.setTaxExemptionReasonCode(taxExceptionCodeType);
		}

		if (null != taxExemptionReason && !taxExemptionReason.isEmpty()) {
			TaxExemptionReasonType taxExceptionReasonType = cbc.createTaxExemptionReasonType();
			taxExceptionReasonType.setValue(taxExemptionReason);
			taxCategoryType.getTaxExemptionReason().add(taxExceptionReasonType);
		}
		return taxCategoryType;
	}

	/**
	 * 
	 * @param currencyID
	 * @param taxAmount
	 * @param taxableAmount
	 * @param taxCategoryType
	 * @param cbc
	 * @param cac
	 * @return
	 */
	public TaxSubtotalType setTaxSubtotalType(String currencyID, BigDecimal taxAmount, BigDecimal taxableAmount, TaxCategoryType taxCategoryType,
			oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc, oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {

		TaxAmountType taxAmountType = cbc.createTaxAmountType();
		TaxSubtotalType taxSubtotalType = cac.createTaxSubtotalType();
		TaxableAmountType taxableAmountType = cbc.createTaxableAmountType();

		taxableAmountType.setCurrencyID(currencyID);
		taxableAmountType.setValue(taxableAmount);
		taxAmountType.setCurrencyID(currencyID);
		taxAmountType.setValue(taxAmount);

		taxSubtotalType.setTaxableAmount(taxableAmountType);
		taxSubtotalType.setTaxAmount(taxAmountType);
		taxSubtotalType.setTaxCategory(taxCategoryType);

		return taxSubtotalType;
	}

	/**
	 * 
	 * @param currencyID
	 * @param lineExtensionAmount
	 * @param taxExclusiveAmount
	 * @param taxInclusiveAmount
	 * @param allowanceTotalAmount
	 * @param prepaidAmount
	 * @param payableAmount
	 * @param payableRoundingAmount
	 * @param cbc
	 * @param cac
	 * @return
	 */
	public MonetaryTotalType setMonetaryTotalType(String currencyID, BigDecimal lineExtensionAmount, BigDecimal taxExclusiveAmount, BigDecimal taxInclusiveAmount, String allowanceTotalAmount,
			String prepaidAmount, BigDecimal payableAmount, String payableRoundingAmount, oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc,
			oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {

		MonetaryTotalType monetaryTotalType = cac.createMonetaryTotalType();
		LineExtensionAmountType lineExtensionAmountType = cbc.createLineExtensionAmountType();
		TaxExclusiveAmountType taxExclusiveAmountType = cbc.createTaxExclusiveAmountType();
		TaxInclusiveAmountType taxInclusiveAmountType = cbc.createTaxInclusiveAmountType();
		AllowanceTotalAmountType allowanceTotalAmountType = cbc.createAllowanceTotalAmountType();
		PrepaidAmountType prepaidAmountType = cbc.createPrepaidAmountType();
		PayableAmountType payableAmountType = cbc.createPayableAmountType();
		PayableRoundingAmountType payableRoundingAmountType = cbc.createPayableRoundingAmountType();

		lineExtensionAmountType.setCurrencyID(currencyID);
		taxExclusiveAmountType.setCurrencyID(currencyID);
		taxInclusiveAmountType.setCurrencyID(currencyID);
		allowanceTotalAmountType.setCurrencyID(currencyID);
		prepaidAmountType.setCurrencyID(currencyID);
		payableAmountType.setCurrencyID(currencyID);
		payableRoundingAmountType.setCurrencyID(currencyID);

		lineExtensionAmountType.setValue(lineExtensionAmount);
		taxExclusiveAmountType.setValue(taxExclusiveAmount);
		taxInclusiveAmountType.setValue(taxInclusiveAmount);
		allowanceTotalAmountType.setValue(new BigDecimal(allowanceTotalAmount));
		prepaidAmountType.setValue(new BigDecimal(prepaidAmount));
		payableAmountType.setValue(payableAmount);

		if (null != payableRoundingAmount && !payableRoundingAmount.isEmpty()) {
			payableRoundingAmountType.setValue(new BigDecimal(payableRoundingAmount));
			monetaryTotalType.setPayableRoundingAmount(payableRoundingAmountType);
		}
		monetaryTotalType.setLineExtensionAmount(lineExtensionAmountType);
		monetaryTotalType.setTaxExclusiveAmount(taxExclusiveAmountType);
		monetaryTotalType.setTaxInclusiveAmount(taxInclusiveAmountType);
		monetaryTotalType.setAllowanceTotalAmount(allowanceTotalAmountType);
		monetaryTotalType.setPrepaidAmount(prepaidAmountType);
		monetaryTotalType.setPayableAmount(payableAmountType);

		return monetaryTotalType;
	}

	/**
	 * 
	 * @param chargeIndicator
	 * @param allowanceChargeReason
	 * @param amount
	 * @param baseAmount
	 * @param currencyID
	 * @param multiplierNumeric
	 * @param cbc
	 * @param cac
	 * @return
	 */
	public AllowanceChargeType setAllowanceChargeType(String chargeIndicator, String allowanceChargeReason, BigDecimal amount, BigDecimal baseAmount, String currencyID, BigDecimal multiplierNumeric,
			oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc, oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {

		AllowanceChargeType allowanceChargeType = cac.createAllowanceChargeType();
		ChargeIndicatorType chargeIndicatorType = cbc.createChargeIndicatorType();
		AllowanceChargeReasonType allowanceChargeReasonType = cbc.createAllowanceChargeReasonType();
		AmountType amountType = cbc.createAmountType();
		BaseAmountType baseAmountType = new BaseAmountType();
		MultiplierFactorNumericType multiplierFactorNumeric = new MultiplierFactorNumericType();

		if (null != allowanceChargeReason) {
			allowanceChargeReasonType.setValue(allowanceChargeReason);
		}

		if (null != chargeIndicator) {
			chargeIndicatorType.setValue(Boolean.valueOf(chargeIndicator));
			allowanceChargeType.setChargeIndicator(chargeIndicatorType);
		}

		if (null != currencyID) {
			amountType.setCurrencyID(currencyID);
			baseAmountType.setCurrencyID(currencyID);
		}
		if (null != amount) {
			amountType.setValue(amount);
			allowanceChargeType.setAmount(amountType);
		}
		if (null != baseAmount) {
			baseAmountType.setValue(baseAmount);
			allowanceChargeType.setBaseAmount(baseAmountType);
		}

		if (null != multiplierNumeric) {
			multiplierFactorNumeric.setValue(multiplierNumeric);
			allowanceChargeType.setMultiplierFactorNumeric(multiplierFactorNumeric);
		}

		allowanceChargeType.getAllowanceChargeReason().add(allowanceChargeReasonType);

		return allowanceChargeType;
	}

	/**
	 * 
	 * @param currencyID
	 * @param priceAmount
	 * @param listAllowanceChargeType
	 * @param cbc
	 * @param cac
	 * @return
	 */
	public PriceType setPriceType(String currencyID, BigDecimal priceAmount, List<AllowanceChargeType> listAllowanceChargeType,
			oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc, oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {
		PriceType priceType = cac.createPriceType();
		PriceAmountType priceAmountType = cbc.createPriceAmountType();

		priceAmountType.setCurrencyID(currencyID);
		priceAmountType.setValue(priceAmount);
		priceType.setPriceAmount(priceAmountType);
		if (null != listAllowanceChargeType) {
			priceType.getAllowanceCharge().addAll(listAllowanceChargeType);
		}
		return priceType;
	}

	/**
	 * 
	 * @param taxSchemeValue
	 * @param taxSchemeAgencyID
	 * @param cbc
	 * @param cac
	 * @return
	 */

	public TaxSchemeType setTaxSchemeType(String taxSchemeValue, String taxSchemeAgencyID, String taxSchemeID, oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc,
			oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {
		TaxSchemeType taxSchemeType = cac.createTaxSchemeType();
		IDType schemeIdType = cbc.createIDType();
		schemeIdType.setValue(taxSchemeValue);
		if (taxSchemeAgencyID.isEmpty()) {
			schemeIdType.setSchemeAgencyID(taxSchemeAgencyID);
		}
		if (taxSchemeID.isEmpty()) {
			schemeIdType.setSchemeID(taxSchemeID);
		}
		taxSchemeType.setID(schemeIdType);

		return taxSchemeType;
	}

	/**
	 * 
	 * @param classifiedTaxCategoryID
	 * @param itemName
	 * @param percentValue
	 * @param taxSchemeType
	 * @param cbc
	 * @param cac
	 * @return
	 */
	public ItemType setItemType(String classifiedTaxCategoryID, String itemName, BigDecimal percentValue, TaxSchemeType taxSchemeType,
			oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc, oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {

		ItemType itemType = cac.createItemType();
		TaxCategoryType taxCategoryType = cac.createTaxCategoryType();
		PercentType precentType = cbc.createPercentType();
		NameType nameType = cbc.createNameType();

		nameType.setValue(itemName);
		precentType.setValue(percentValue);
		IDType id = cbc.createIDType();
		id.setValue(classifiedTaxCategoryID);

		taxCategoryType.setID(id);
		taxCategoryType.setPercent(precentType);
		taxCategoryType.setTaxScheme(taxSchemeType);

		itemType.setName(nameType);
		itemType.getClassifiedTaxCategory().add(taxCategoryType);

		return itemType;
	}

	/**
	 * 
	 * @param lineItem
	 * @param listAllowanceChargeType
	 * @param cbc
	 * @param cac
	 * @return
	 */
	public InvoiceLineType setInvoiceLineType(SaleReturnLineItemModel lineItem, oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc,
			oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {

		InvoiceLineType invoiceLineType = cac.createInvoiceLineType();
		InvoicedQuantityType invoicedQuantityType = cbc.createInvoicedQuantityType();
		LineExtensionAmountType lineExtensionAmountType = cbc.createLineExtensionAmountType();

		IDType invoiceLineTypeID = cbc.createIDType();
		invoiceLineTypeID.setValue(String.valueOf(lineItem.getLineItemSequence()));
		invoicedQuantityType.setUnitCode("PCE");
		invoicedQuantityType.setValue(lineItem.getQuantity());
		lineExtensionAmountType.setCurrencyID(lineItem.getCurrencyId());
		lineExtensionAmountType.setValue(lineItem.getGrossAmount());

		invoiceLineType.setID(invoiceLineTypeID);
		invoiceLineType.setInvoicedQuantity(invoicedQuantityType);
		invoiceLineType.setLineExtensionAmount(lineExtensionAmountType);

		if (lineItem.getDiscounted()) {
			// mapAllowanceCharges(invoiceLineType, lineItem, cbc, cac);
		}

		if (null != lineItem.getCurrencyId() && null != lineItem.getTaxModifiers()) {
			invoiceLineType.getTaxTotal().add(setTaxTotalType(lineItem.getCurrencyId(), lineItem.getTaxModifiers().get(0).getRawTaxAmount(), new BigDecimal(0), null, cbc, cac));
		}

		invoiceLineType.setItem(setItemType("S", lineItem.getItemDescription(), asqZatcaHelper.getFormatttedBigDecimalValue(lineItem.getTaxModifiers().get(0).getRawTaxPercentage()),
				setTaxSchemeType(lineItem.getTaxModifiers().get(0).getTaxGroupId(), AsqZatcaConstant.ZATCA_SCHEME_AGENCYID, AsqZatcaConstant.ZATCA_TAXSCHEME_SCHEMEID, cbc, cac), cbc, cac));

		invoiceLineType.setPrice(setPriceType(lineItem.getCurrencyId(), lineItem.getNetAmount(), null, cbc, cac));

		// if (null != lineItem.getNotes() && lineItem.getNotes().length > 0) {
		// for (String note : lineItem.getNotes()) {
		// NoteType noteType = cbc.createNoteType();
		// noteType.setValue(note);
		// invoiceLineType.getNote().add(noteType);
		// }
		// }

		return invoiceLineType;
	}

	/**
	 * 
	 * @param invoiceLineType
	 * @param lineItem
	 * @param cac
	 */
	private void mapAllowanceCharges(InvoiceLineType invoiceLineType, LineItems lineItem, oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc,
			oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {
		if (null != lineItem.getItemLineAllowanceCharges()) {
			for (ItemAllowanceCharges allowanceCharge : lineItem.getItemLineAllowanceCharges()) {
				AllowanceChargeType allowanceChargeType = cac.createAllowanceChargeType();
				mapChargeIndicatore(allowanceChargeType, allowanceCharge, cbc);
				mapAllowanceChargeReason(allowanceChargeType, allowanceCharge, cbc);
				mapAmount(allowanceChargeType, allowanceCharge, cbc);
				mapBaseAmount(allowanceChargeType, allowanceCharge);
				mapMultiplierFactorNumeric(allowanceChargeType, allowanceCharge);
				if (null != allowanceChargeType) {
					invoiceLineType.getAllowanceCharge().add(allowanceChargeType);
				}
			}
		}
	}

	/**
	 * 
	 * @param allowanceChargeType
	 * @param allowanceCharge
	 */

	private void mapMultiplierFactorNumeric(AllowanceChargeType allowanceChargeType, ItemAllowanceCharges allowanceCharge) {
		MultiplierFactorNumericType multiplierFactorNumeric = new MultiplierFactorNumericType();
		if (null != allowanceCharge.getItemMultiplierFactorNummeric()) {
			multiplierFactorNumeric.setValue(new BigDecimal(allowanceCharge.getItemMultiplierFactorNummeric()));
			allowanceChargeType.setMultiplierFactorNumeric(multiplierFactorNumeric);
		}
	}

	/**
	 * 
	 * @param allowanceChargeType
	 * @param allowanceCharge
	 */

	private void mapBaseAmount(AllowanceChargeType allowanceChargeType, ItemAllowanceCharges allowanceCharge) {
		BaseAmountType baseAmount = new BaseAmountType();
		if (null != allowanceCharge.getItemBaseAmount()) {
			baseAmount.setCurrencyID(allowanceCharge.getItemAllowanceChargeAmountCurrencyID());
			baseAmount.setValue(new BigDecimal(allowanceCharge.getItemBaseAmount()));
			allowanceChargeType.setBaseAmount(baseAmount);
		}

	}

	/**
	 * 
	 * @param allowanceChargeType
	 * @param allowanceCharge
	 * @param cbc
	 */
	private void mapAmount(AllowanceChargeType allowanceChargeType, ItemAllowanceCharges allowanceCharge, oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc) {
		AmountType amountType = cbc.createAmountType();
		if (null != allowanceCharge.getItemAllowanceChargeAmount()) {
			amountType.setCurrencyID(allowanceCharge.getItemAllowanceChargeAmountCurrencyID());
			amountType.setValue(new BigDecimal(allowanceCharge.getItemAllowanceChargeAmount()));
			allowanceChargeType.setAmount(amountType);
		}
	}

	/**
	 * 
	 * @param allowanceChargeType
	 * @param allowanceCharge
	 * @param cbc
	 */
	private void mapAllowanceChargeReason(AllowanceChargeType allowanceChargeType, ItemAllowanceCharges allowanceCharge,
			oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc) {
		AllowanceChargeReasonType allowanceChargeReasonType = cbc.createAllowanceChargeReasonType();
		if (null != allowanceCharge.getItemAllowanceChargeReason()) {
			allowanceChargeReasonType.setValue(allowanceCharge.getItemAllowanceChargeReason());
			allowanceChargeType.getAllowanceChargeReason().add(allowanceChargeReasonType);
		}
	}

	/**
	 * 
	 * @param allowanceChargeType
	 * @param allowanceCharge
	 * @param cbc
	 */
	private void mapChargeIndicatore(AllowanceChargeType allowanceChargeType, ItemAllowanceCharges allowanceCharge,
			oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc) {
		ChargeIndicatorType chargeIndicatorType = cbc.createChargeIndicatorType();
		if (null != allowanceCharge.getItemAllowanceChargeIndicator()) {
			chargeIndicatorType.setValue(Boolean.valueOf(allowanceCharge.getItemAllowanceChargeIndicator()));
			allowanceChargeType.setChargeIndicator(chargeIndicatorType);
		}
	}

	/**
	 * 
	 * @param sellerName
	 * @param vatNumber
	 * @param invoiceTimeStamp
	 * @param invoiceDate
	 * @param invoiceTotal
	 * @param vatTotal
	 * @param xmlData
	 * @param keySecret
	 * @param keyAlias
	 * @param invoiceData
	 * @param addDocQR
	 * @return
	 * @throws ASQException
	 * @throws Exception
	 */
	public HashQRData getHashAndQR(String sellerName, String vatNumber, String invoiceIssueTimeStamp, String invoiceIssueDate, String invoiceTotal, String vatTotal, String xmlData, String keySecret,
			String keyAlias, AdditionalDocumentReference addDocQR, String xmlUUID, String xmlIrnValue, SignatureData signatureData, Long nextICV) throws ASQException, Exception {

		Base64 base64 = new Base64();
		String xml = SmartHubUtil.removeNewlineAndWhiteSpaces(xmlData);
		logger.debug("**********Initial XML Generated : " + xml);
		String hashedXML = new InvoiceHash().getInvoiceHash(xml);// generating hash
		logger.debug("**********Invoice Hash : " + hashedXML);
		logger.debug("**********KeyStore Fetching from Properties - Path -{} -- Key- {} : ", AsqZatcaConstant.certificateFilePath, keySecret);
		KeyStore ks = SmartHubUtil.getKeyStore(AsqZatcaConstant.certificateFilePath, keySecret);

		X509Certificate x509 = SmartHubUtil.readCSIDFile(AsqZatcaConstant.csidCertificateFilePath);
		if (isCertificateExpired(x509)) {
			HashQRData data = new HashQRData();
			data.setCertificateExpired(true);
			data.setExpirationDate(x509.getNotAfter().toString());
			return data;
		}
		byte[] signedHashAsBytes = SmartHubUtil.sigData(ks, AsqZatcaConstant.keyAlg, keySecret, AsqZatcaConstant.sigAlg, hashedXML);
		String signedHash = base64.encodeBase64String(signedHashAsBytes);
		byte[] csidSignature = x509.getSignature();
		byte[] publicKeyString = x509.getPublicKey().getEncoded();
		String csidCertificate = SmartHubUtil.getReadCSIDCertificateString(AsqZatcaConstant.csidCertificateFilePath);
		csidCertificate = SmartHubUtil.removeHeaderAndFooter(csidCertificate);

		XMLGregorianCalendar signingTime = SmartHubUtil.signingTimeConversion(invoiceIssueDate, invoiceIssueTimeStamp);

		String qrCode = SmartHubUtil.generateQRCode(sellerName, vatNumber, invoiceIssueTimeStamp, invoiceIssueDate, invoiceTotal, vatTotal, hashedXML, signedHash, publicKeyString, csidSignature);
		addDocQR.setEmbeddedDocumentBinaryObject(qrCode);

		// saving the hash value in the database
		// AsqZatcaInvoiceHashModel model =
		// asqZatcaDatabaseHelper.saveInvoiceHash(xmlIrnValue, xmlUUID, hashedXML,
		// signingTime.toXMLFormat());

		signatureData.setICV(nextICV.intValue());
		signatureData.setSignatureValue(signedHashAsBytes);
		signatureData.setDigestValueInvoiceSignedData(hashedXML.getBytes());
		signatureData.setX509Certificate(csidCertificate);
		signatureData.setCsidCertSigningTime(signingTime);// take from invoice json
		String issuername = x509.getIssuerX500Principal().getName().replaceAll(",", ", ");
		signatureData.setCsidCertIssuerName(issuername);
		signatureData.setCsidCertSerialNumber(x509.getSerialNumber().toString());

		HashQRData data = new HashQRData();
		data.setQR(qrCode);
		data.setInvoiceHash(hashedXML);
		data.setCertificate(csidCertificate);
		return data;
	}

	private boolean isCertificateExpired(X509Certificate x509) throws ParseException {
		// if(x509)
		Date expirationDate = x509.getNotAfter();
		Date currentDate = new Date();
		return expirationDate.before(currentDate);
	}

	/**
	 * 
	 * @param extensionURI
	 * @param signatureInformationID
	 * @param referencedSignatureID
	 * @param transformsAlgorithm
	 * @param xpath
	 * @param signatureValue
	 * @param cbc
	 * @param signatureData
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws ParseException
	 * @throws DatatypeConfigurationException
	 * @throws InvalidCanonicalizerException
	 * @throws CanonicalizationException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 * @throws SAXException
	 */
	public UBLExtensionType getUBLExtension(String extensionURI, String signatureInformationID, String referencedSignatureID, String[] transformsAlgorithm, String[] xpath,
			oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc, SignatureData signatureData) throws JAXBException, IOException, NoSuchAlgorithmException,
			ParseException, DatatypeConfigurationException, InvalidCanonicalizerException, CanonicalizationException, ParserConfigurationException, TransformerException, SAXException {

		oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ObjectFactory ext = asqZatcaHelper
				.getZatcaOjectFactory(oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ObjectFactory.class);
		oasis.names.specification.ubl.schema.xsd.signatureaggregatecomponents_2.ObjectFactory sac = asqZatcaHelper
				.getZatcaOjectFactory(oasis.names.specification.ubl.schema.xsd.signatureaggregatecomponents_2.ObjectFactory.class);
		oasis.names.specification.ubl.schema.xsd.commonsignaturecomponents_2.ObjectFactory sig = asqZatcaHelper
				.getZatcaOjectFactory(oasis.names.specification.ubl.schema.xsd.commonsignaturecomponents_2.ObjectFactory.class);
		oasis.names.specification.ubl.schema.xsd.signaturebasiccomponents_2.ObjectFactory sbc = asqZatcaHelper
				.getZatcaOjectFactory(oasis.names.specification.ubl.schema.xsd.signaturebasiccomponents_2.ObjectFactory.class);
		org.w3._2000._09.xmldsig_.ObjectFactory ds = asqZatcaHelper.getZatcaOjectFactory(org.w3._2000._09.xmldsig_.ObjectFactory.class);
		org.etsi.uri._01903.v1_3.ObjectFactory xades = asqZatcaHelper.getZatcaOjectFactory(org.etsi.uri._01903.v1_3.ObjectFactory.class);

		UBLExtensionType ublExtensionType = ext.createUBLExtensionType();
		ExtensionContentType extensionContentType = ext.createExtensionContentType();
		UBLDocumentSignaturesType ublDocumentSignaturesType = sig.createUBLDocumentSignaturesType();
		SignatureInformationType signatureInformationType = sac.createSignatureInformationType();
		ReferencedSignatureIDType referencedSignatureIDType = sbc.createReferencedSignatureIDType();
		org.w3._2000._09.xmldsig_.SignatureType signatureType = ds.createSignatureType();
		org.w3._2000._09.xmldsig_.SignedInfoType signedInfoType = ds.createSignedInfoType();
		org.w3._2000._09.xmldsig_.CanonicalizationMethodType canonicalizationMethodType = ds.createCanonicalizationMethodType();
		org.w3._2000._09.xmldsig_.SignatureMethodType signatureMethodType = ds.createSignatureMethodType();
		org.w3._2000._09.xmldsig_.ReferenceType referenceType01 = ds.createReferenceType();
		org.w3._2000._09.xmldsig_.ReferenceType referenceType02 = ds.createReferenceType();
		org.w3._2000._09.xmldsig_.TransformsType transformsType = ds.createTransformsType();
		org.w3._2000._09.xmldsig_.SignatureValueType signatureValueType = ds.createSignatureValueType();
		org.w3._2000._09.xmldsig_.KeyInfoType keyInfoType = ds.createKeyInfoType();
		org.w3._2000._09.xmldsig_.X509DataType x509DataType = ds.createX509DataType();
		org.w3._2000._09.xmldsig_.X509IssuerSerialType x509IssuerSerialType = ds.createX509IssuerSerialType();

		ExtensionURIType extensionURIType = new ExtensionURIType();
		extensionURIType.setValue(extensionURI);
		ublDocumentSignaturesType.getSignatureInformation().add(signatureInformationType);
		extensionContentType.setAny(sig.createUBLDocumentSignatures(ublDocumentSignaturesType));
		ublExtensionType.setExtensionURI(extensionURIType);
		ublExtensionType.setExtensionContent(extensionContentType);
		IDType idType = cbc.createIDType();
		idType.setValue(signatureInformationID);
		signatureInformationType.setID(idType);
		referencedSignatureIDType.setValue(referencedSignatureID);
		signatureInformationType.setReferencedSignatureID(referencedSignatureIDType);
		signatureInformationType.setSignature(signatureType);
		canonicalizationMethodType.setAlgorithm(System.getProperty("asq.pos.invoice.canonicalizationMethod_Algorithm"));
		signatureMethodType.setAlgorithm(System.getProperty("asq.pos.invoice.signatureMethod_Algorithm"));
		referenceType01.setId("invoiceSignedData");
		referenceType01.setURI(EmptyString);
		for (int i = 0; i < transformsAlgorithm.length; i++) {
			org.w3._2000._09.xmldsig_.TransformType transformType = ds.createTransformType();
			transformType.setAlgorithm(transformsAlgorithm[i]);
			transformType.getContent().add(ds.createTransformTypeXPath(xpath[i]));
			transformsType.getTransform().add(transformType);
		}
		referenceType01.setTransforms(transformsType);
		DigestMethodType digestMethodType01 = ds.createDigestMethodType();
		digestMethodType01.setAlgorithm(System.getProperty("asq.pos.invoice.digestMethod_Algorithm"));
		referenceType01.setDigestMethod(digestMethodType01);
		referenceType01.setDigestValue(signatureData.getDigestValueInvoiceSignedData());
		referenceType02.setType(System.getProperty("asq.pos.invoice.referenceSignedProperties"));
		referenceType02.setURI("#xadesSignedProperties");
		DigestMethodType digestMethodType02 = ds.createDigestMethodType();
		digestMethodType02.setAlgorithm(System.getProperty("asq.pos.invoice.digestMethod_Algorithm"));
		referenceType02.setDigestMethod(digestMethodType02);
		signedInfoType.setCanonicalizationMethod(canonicalizationMethodType);
		signedInfoType.setSignatureMethod(signatureMethodType);
		signedInfoType.getReference().add(referenceType01);
		signedInfoType.getReference().add(referenceType02);
		signatureValueType.setValue(signatureData.getSignatureValue());
		ds.createX509Data(x509DataType);
		x509DataType.getX509IssuerSerialOrX509SKIOrX509SubjectName().add(ds.createX509DataTypeX509Certificate("CERT".getBytes()));
		keyInfoType.getContent().add(ds.createX509Data(x509DataType));
		signatureType.setId("signature");
		signatureType.setSignedInfo(signedInfoType);
		signatureType.setSignatureValue(signatureValueType);
		signatureType.setKeyInfo(keyInfoType);
		org.w3._2000._09.xmldsig_.ObjectType objectType = ds.createObjectType();
		// ATUL
		QualifyingPropertiesType qualifyingPropertiesType = xades.createQualifyingPropertiesType();
		qualifyingPropertiesType.setTarget("");
		// ATUL
		SignedPropertiesType signedPropertiesType = xades.createSignedPropertiesType();
		SignedSignaturePropertiesType signedSignaturePropertiesType = xades.createSignedSignaturePropertiesType();
		CertIDListType certIDListType = xades.createCertIDListType();
		CertIDType certIDType = xades.createCertIDType();
		DigestAlgAndValueType digestAlgAndValueType = xades.createDigestAlgAndValueType();
		DigestMethodType digestMethodType = ds.createDigestMethodType();
		X509IssuerSerialType x509IssuerSerialType01 = ds.createX509IssuerSerialType();
		digestMethodType.setAlgorithm(System.getProperty("asq.pos.invoice.sha256Algorithm"));
		digestAlgAndValueType.setDigestMethod(digestMethodType);
		digestAlgAndValueType.setDigestValue("HASHCERT".getBytes());
		x509IssuerSerialType01.setX509IssuerName(signatureData.getCsidCertIssuerName());
		x509IssuerSerialType01.setX509SerialNumber(new BigInteger(signatureData.getCsidCertSerialNumber()));
		certIDType.setCertDigest(digestAlgAndValueType);
		certIDType.setIssuerSerial(x509IssuerSerialType01);
		certIDListType.getCert().add(certIDType);
		signedSignaturePropertiesType.setSigningTime(signatureData.getCsidCertSigningTime());
		signedSignaturePropertiesType.setSigningCertificate(certIDListType);
		signedPropertiesType.setId(System.getProperty("asq.pos.invoice.signedPropertiesID"));
		signedPropertiesType.setSignedSignatureProperties(signedSignaturePropertiesType);
		Base64 base64 = new Base64();
		signatureData.setDigestValueXadesSignedProperties("ReplaceSignature".getBytes());
		referenceType02.setDigestValue(signatureData.getDigestValueXadesSignedProperties());

		qualifyingPropertiesType.setSignedProperties(signedPropertiesType);
		// ATUL
		objectType.getContent().add(xades.createQualifyingProperties(qualifyingPropertiesType));
		// objectType.getContent().add(qualifyingPropertiesType);
		signatureType.getObject().add(objectType);

		return ublExtensionType;
	}

	/**
	 * 
	 * @param customerPartyType
	 * @param buyerPhoneNumber
	 * @param cbc
	 * @param cac
	 * @return
	 */

	public void mapBuyerPhoneNumber(CustomerPartyType customerPartyType, String buyerPhoneNumber, oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc,
			oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {
		if (null != buyerPhoneNumber && !buyerPhoneNumber.isEmpty()) {
			ContactType buyerContact = cac.createContactType();
			TelephoneType telephone = cbc.createTelephoneType();
			telephone.setValue(buyerPhoneNumber);
			buyerContact.setTelephone(telephone);
			customerPartyType.setBuyerContact(buyerContact);
		}
	}

	/**
	 * 
	 * @param totalTaxValue
	 * @param currencyId
	 * @param cbc
	 * @param cac
	 * @return
	 */

	public TaxTotalType mapTotalTaxAmoutTag(String totalTaxValue, String currencyId, oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc,
			oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {
		TaxTotalType taxTotalType = cac.createTaxTotalType();
		TaxAmountType taxAmountType = cbc.createTaxAmountType();
		taxAmountType.setValue(new BigDecimal(totalTaxValue));
		taxAmountType.setCurrencyID(currencyId);
		taxTotalType.setTaxAmount(taxAmountType);
		return taxTotalType;
	}

	/**
	 * 
	 * @param taxSubTotals
	 * @param cbc
	 * @param cac
	 * @return
	 */

	public List<TaxSubtotalType> mapTaxSubtotal(List<TaxSubtotal> taxSubTotals, oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory cbc,
			oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac) {
		List<TaxSubtotalType> taxSubtotalTypes = new ArrayList<>();
		for (TaxSubtotal taxSubTotal : taxSubTotals) {
			taxSubtotalTypes.add(setTaxSubtotalType(taxSubTotal.getCurrency(), new BigDecimal(taxSubTotal.getTaxAmount()), new BigDecimal(taxSubTotal.getTaxableAmount()),
					setTaxCategoryType(new String[] { taxSubTotal.getTaxCategorySchemeID() }, new String[] { taxSubTotal.getTaxCategorySchemeAgencyID() },
							new String[] { taxSubTotal.getTaxCategoryID() }, new BigDecimal[] { new BigDecimal(taxSubTotal.getTaxCategoryPercent()) },
							new String[] { taxSubTotal.getTaxCategoryTaxSchemeSchemeID() }, new String[] { taxSubTotal.getTaxCategoryTaxSchemeID() },
							new String[] { taxSubTotal.getTaxCategoryTaxSchemeSchemeAgencyID() }, taxSubTotal.getTaxExemptionReasonCode(), taxSubTotal.getTaxExemptionReason(), cbc, cac),
					cbc, cac));
		}
		return taxSubtotalTypes;
	}

	/**
	 * 
	 * @param originalInvoiceNumbers
	 * @return
	 */
	public BillingReferenceType setBillingReference(String originalInvoiceNumbers) {
		oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory cac = new oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory();

		BillingReferenceType billingReferenceType = cac.createBillingReferenceType();
		DocumentReferenceType documentReferenceType = cac.createDocumentReferenceType();
		IDType id = new IDType();
		id.setValue(originalInvoiceNumbers);
		documentReferenceType.setID(id);
		billingReferenceType.setInvoiceDocumentReference(documentReferenceType);
		return billingReferenceType;
	}

	/**
	 * 
	 * @param in
	 * @param argQRCode
	 * @param signatureData
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws JAXBException
	 * @return invoiceXML
	 */

	public String generateFinalXML(InvoiceType in, String argQRCode, SignatureData signatureData) throws NoSuchAlgorithmException, IOException, JAXBException {
		String invoiceXML = SmartHubUtil.generateCompleteSignedInvoiceXML(in);
		logger.debug("Before Invoice Generated: " + invoiceXML);
		invoiceXML = invoiceXML.replace(AsqZatcaConstant.UBLDocumentSignaturesTag, AsqZatcaConstant.UBLDocumentSignaturesWithNamespace);
		invoiceXML = invoiceXML.replace(AsqZatcaConstant.SignatureTag, AsqZatcaConstant.SignatureWithNamespace);
		invoiceXML = invoiceXML.replace(AsqZatcaConstant.QualifyingPropertiesTag, AsqZatcaConstant.QualifyingPropertiesWithNamespace);
		invoiceXML = invoiceXML.replace("UVI=", argQRCode);
		invoiceXML = invoiceXML.replace("Q0VSVA==", signatureData.getX509Certificate());
		invoiceXML = invoiceXML.replace("SEFTSENFUlQ=", SmartHubUtil.hashCertificate(AsqZatcaConstant.csidCertificateFilePath));
		invoiceXML = invoiceXML.replace("UmVwbGFjZVNpZ25hdHVyZQ==", SmartHubUtil.hashSignatureProperties(getSignaturePropertyHashingString(signatureData)));
		invoiceXML = SmartHubUtil.removeSigAttributes(invoiceXML);
		invoiceXML = SmartHubUtil.removeNewlineAndWhiteSpaces(invoiceXML);
		logger.debug("After Invoice Generated: " + invoiceXML);
		return invoiceXML;
	}

	/**
	 * 
	 * @param signatureData
	 * @param argQRCode
	 * @param signatureData
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @return signaturePropertyHashingString
	 */

	private String getSignaturePropertyHashingString(SignatureData signatureData) throws NoSuchAlgorithmException, IOException {
		String signaturePropertyHashingString = "<xades:SignedProperties xmlns:xades=\"http://uri.etsi.org/01903/v1.3.2#\" Id=\"xadesSignedProperties\"><xades:SignedSignatureProperties><xades:SigningTime>SITV</xades:SigningTime><xades:SigningCertificate><xades:Cert><xades:CertDigest><ds:DigestMethod xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><ds:DigestValue xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">CERTV</ds:DigestValue></xades:CertDigest><xades:IssuerSerial><ds:X509IssuerName xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">ISSUEV</ds:X509IssuerName><ds:X509SerialNumber xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">SNOV</ds:X509SerialNumber></xades:IssuerSerial></xades:Cert></xades:SigningCertificate></xades:SignedSignatureProperties></xades:SignedProperties>";
		signaturePropertyHashingString = signaturePropertyHashingString.replace("SITV", signatureData.getCsidCertSigningTime().toString());
		signaturePropertyHashingString = signaturePropertyHashingString.replace("CERTV", SmartHubUtil.hashCertificate(AsqZatcaConstant.csidCertificateFilePath));
		signaturePropertyHashingString = signaturePropertyHashingString.replace("ISSUEV", signatureData.getCsidCertIssuerName());
		signaturePropertyHashingString = signaturePropertyHashingString.replace("SNOV", signatureData.getCsidCertSerialNumber());
		return signaturePropertyHashingString;
	}

	/**
	 * This method converts the sample invoice XML to InvoiceType object
	 * 
	 * @param argSampleInvoice
	 * @return
	 * @throws JAXBException
	 */
	public InvoiceType getInvoiceOject(File argSampleInvoice) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(new oasis.names.specification.ubl.schema.xsd.invoice_2.ObjectFactory().getClass().getPackage().getName() + ":"
				+ new oasis.names.specification.ubl.schema.xsd.commonsignaturecomponents_2.ObjectFactory().getClass().getPackage().getName() + ":"
				+ new org.etsi.uri._01903.v1_3.ObjectFactory().getClass().getPackage().getName());
		Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
		JAXBElement<InvoiceType> obj = jaxbUnMarshaller.unmarshal(new StreamSource(argSampleInvoice), InvoiceType.class);
		return obj.getValue();
	}

	/**
	 * This method help in having the file from a location with filtered file types
	 * 
	 * @param argLocation
	 * @param argFileFilters
	 * @return
	 */
	public File[] getFilesFromLocation(String argLocation, String argFileFilters) {
		File signedInvoiceFolder = new File(argLocation);
		return signedInvoiceFolder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith(argFileFilters)) {
					return true;
				}
				return false;
			}
		});
	}
}
