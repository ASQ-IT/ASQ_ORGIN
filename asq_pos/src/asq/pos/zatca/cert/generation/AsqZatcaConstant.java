package asq.pos.zatca.cert.generation;

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
}
