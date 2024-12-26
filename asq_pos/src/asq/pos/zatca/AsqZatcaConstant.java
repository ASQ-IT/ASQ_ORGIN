package asq.pos.zatca;

public interface AsqZatcaConstant {

	String ZATCA_CERT_GEN_SRV = "ZATCA_CERT_GEN_SRV";
	String ZATCA_CERT_GEN_INVOICE_SRV = "ZATCA_CERT_GEN_INVOICE_SRV";
	String ZATCA_CERT_CSIDS_SRV = "ZATCA_CERT_CSIDS_SRV";

	String uniCodeChartoFind = "\\\\u";
	String uniCodeChartoReplace = "\\u";
	String backSlashChartoFind = "\\\\\"";
	String backSlashChartoReplace = "\"";

	String certificateFilePath = System.getProperty("asq.pos.invoice.certificateFilePath");
	String keySecret = System.getProperty("asq.pos.invoice.keySecret");
	String keyAlg = System.getProperty("asq.pos.invoice.keyAlg");
	String sigAlg = System.getProperty("asq.pos.invoice.sigAlg");
	String commonDateFormat = System.getProperty("asq.pos.invoice.dateFormat");
	String commonTimeFormat = System.getProperty("asq.pos.invoice.timeFormat");
	String signingTimeFormat = System.getProperty("asq.pos.invoice.signingTimeFormat");
	String csidCertificateFilePath = System.getProperty("asq.pos.invoice.csidCertificateFilePath");
	// invoiceOutFileName = System.getProperty("asq.pos.invoice.resourceFolder") +
	// System.getProperty("asq.pos.invoice.invoiceFileName");
	String outboundFolder = System.getProperty("asq.pos.invoice.outboundFolder").concat(System.getProperty("asq.pos.invoice.invoiceFileName"));
	String UBLDocumentSignaturesTag = System.getProperty("asq.pos.invoice.UBLDocumentSignaturesTag");
	String UBLDocumentSignaturesWithNamespace = System.getProperty("asq.pos.invoice.UBLDocumentSignaturesWithNamespace");
	String SignatureTag = System.getProperty("asq.pos.invoice.SignatureTag");
	String SignatureWithNamespace = System.getProperty("asq.pos.invoice.SignatureWithNamespace");
	String QualifyingPropertiesTag = System.getProperty("asq.pos.invoice.QualifyingPropertiesTag");
	String QualifyingPropertiesWithNamespace = System.getProperty("asq.pos.invoice.QualifyingPropertiesWithNamespace");

	String ZATCA_STATUS_NEW = "NEW";
	String ZATCA_STATUS_SUBMITTED = "SUBMITTED";

	// STC Integration API constants
	String CUSTOMER_UNAVAILABLE = "Customer not linked to the transaction";
	String STC_EARN_SUCCESS_CODE = "SUCCESS";
	String STC_SUCCESS_EARN_RESPONSE = "STC_SUCCESS_EARN_RESPONSE";
	String ASQ_STC_SUCCESS_REDEEM_RESPONSE = "STC_SUCCESS_REDEEM_RESPONSE";
	String STC_SUCCESS_REFUND_REDEEM_RESPONSE = "STC_SUCCESS_REFUND_REDEEM_RESPONSE";
	String ASQ_CERTIFICATE_EXPIRY = "No trusted certificate found";
	String ASQ_TAMARA_TRANSACTION_COMMENT = "ASQ_TAMARA_TRANSACTION";
	String ASQ_TAMARA_STORE_TYPE_DEFAULT = "IN_STORE";
	String ASQ_TAMARA_STORE_CODE_DEFAULT = "STORE_A";
	String ASQ_TABBY_MERCHANT_CODE_DEFAULT = "ASQAAB1";
	String ZATCA_SALE_CODE = "388";
	String ZATCA_RETURN_CODE = "381";
	String ZATCA_BUSINESS_TO_CUSTOMER = "0200000";

	/**
	 * Reason Code : Their are 5 reason - Cancellation or suspension of the supplies
	 * after its occurrence either wholly or partially - In case of essential change
	 * or amendment in the supply, which leads to the change of the VAT due-
	 * Amendment of the supply value which is pre-agreed upon between the supplier
	 * and consumer - In case of goods or services refund. - In case of change in
	 * Seller's or Buyer's information
	 */
	String ZATCA_RETURN_REASON_CODE = "In case of goods or services refund. (عند ترجيع السلع أو الخدمات)";

	String ZATCA_TENDER_CASH_CODE = "10";
	String ZATCA_TENDER_CREDIT_CODE = "54";
	String ZATCA_TENDER_DEBIT_CODE = "55";
	String ZATCA_TENDER_PAYLINK_CODE = "68";

	String ZATCA_TAXCATEGORY_SCHEMEID = "UN/ECE 5305";
	String ZATCA_TAXSCHEME_SCHEMEID = "UN/ECE 5153";
	String ZATCA_SCHEME_AGENCYID = "6";

	String ZATCA_TAXCATEGORY_ID_VAL = "S";
	String ZATCA_TAXSCHEME_ID_VAL = "VAT";
	String ZATCA_CRN_ID_VAL = "CRN";

	// These value will remain constant
	String companyLegalName = System.getProperty("asq.zatca.company.legalenity.name");
	String companyVatNumber = System.getProperty("asq.zatca.company.vat.reg.number");
	String companyCRNNumber = System.getProperty("asq.zatca.company.crn.number");

	String companyAddStreetName = System.getProperty("asq.zatca.company.street.name");
	String companyAddBuildingNumber = System.getProperty("asq.zatca.company.building.number");
	String companyAddPlotNumber = System.getProperty("asq.zatca.company.plot.number");
	String companyAddCitySubdivision = System.getProperty("asq.zatca.company.sub.division");
	String companyAddCityName = System.getProperty("asq.zatca.company.city.name");
	String companyAddPostalZone = System.getProperty("asq.zatca.company.postal.zone");
	String companyAddCountrySubentity = System.getProperty("asq.zatca.company.country.subentity");
	String companyAddCountry = System.getProperty("asq.zatca.company.country");

	String ASQ_ZATCA_SECRET_KEY = "secret";
	String ASQ_ZATCA_TOKEN_KEY = "binarySecurityToken";
	String ASQ_ZATCA_OIC_DUP_STATUS = "DUPLICATE";
	String ASQ_ZATCA_OIC_ERR_STATUS = "ERROR";
	String ASQ_ZATCA_OIC_WAR_STATUS = "WARNING";

	String ASQ_QR_CODE = "QR";
	String ASQ_UIID_CHAR_TOREP = "::";
	String ASQ_UIID_CHAR_BEREP = "-";

	String ASQ_ZATCA_SEQ = "ASQ_ZATCA_SEQ";
	String ASQ_ZATCA_REPORTING = "reporting:1.0";

	String ASQ_FALSE = "false";
	String ASQ_DISCOUNT = "Discount";
	String ASQ_PLANET_TAX_ID = "PLANET_TAX_ID";
	String ASQ_ZATCA_RAIN_CHECK = "RAIN_CHECK";

	String ASQ_NEQATY_TRX_ID = "ASQ_NEQATY_TRX_ID";
	String ASQ_MOKAFA_TRX_ID = "ASQ_MOKAFA_TRX_ID";
	String ASQ_NEQATY_EARN_TRN="ASQ_NEQATY_EARN_TRN";
}
