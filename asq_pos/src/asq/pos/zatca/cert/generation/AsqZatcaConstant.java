package asq.pos.zatca.cert.generation;

import dtv.util.Base64;

public interface AsqZatcaConstant {

	String uniCodeChartoFind = "\\\\u";
	String uniCodeChartoReplace = "\\u";
	String backSlashChartoFind = "\\\\\"";
	String backSlashChartoReplace = "\"";

	String certificateFilePath = System.getProperty("asq.pos.invoice.certificateFilePath");
	String keySecret = new String(Base64.byteArrayToBase64(System.getProperty("asq.pos.invoice.keySecret").getBytes()));
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
