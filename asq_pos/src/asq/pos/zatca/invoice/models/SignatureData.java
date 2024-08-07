package asq.pos.zatca.invoice.models;

import javax.xml.datatype.XMLGregorianCalendar;

public class SignatureData {
	
	private byte[] signatureValue;
//	private byte[] x509Certificate;
	private byte[] digestValueInvoiceSignedData;
	private byte[] digestValueXadesSignedProperties;
	private XMLGregorianCalendar csidCertSigningTime;
	private String csidCertIssuerName;
	private String csidCertSerialNumber;
	private Integer ICV;
	private String x509Certificate;
	
	

	public String getX509Certificate() {
		return x509Certificate;
	}
	public void setX509Certificate(String x509Certificate) {
		this.x509Certificate = x509Certificate;
	}
	public Integer getICV() {
		return ICV;
	}
	public void setICV(Integer iCV) {
		ICV = iCV;
	}
	public byte[] getSignatureValue() {
		return signatureValue;
	}
	public void setSignatureValue(byte[] signatureValue) {
		this.signatureValue = signatureValue;
	}
//	public byte[] getX509Certificate() {
//		return x509Certificate;
//	}
//	public void setX509Certificate(byte[] x509Certificate) {
//		this.x509Certificate = x509Certificate;
//	}
	public byte[] getDigestValueInvoiceSignedData() {
		return digestValueInvoiceSignedData;
	}
	public void setDigestValueInvoiceSignedData(byte[] digestValueInvoiceSignedData) {
		this.digestValueInvoiceSignedData = digestValueInvoiceSignedData;
	}
	public byte[] getDigestValueXadesSignedProperties() {
		return digestValueXadesSignedProperties;
	}
	public void setDigestValueXadesSignedProperties(byte[] digestValueXadesSignedProperties) {
		this.digestValueXadesSignedProperties = digestValueXadesSignedProperties;
	}
	public XMLGregorianCalendar getCsidCertSigningTime() {
		return csidCertSigningTime;
	}
	public void setCsidCertSigningTime(XMLGregorianCalendar csidCertSigningTime) {
		this.csidCertSigningTime = csidCertSigningTime;
	}
	public String getCsidCertIssuerName() {
		return csidCertIssuerName;
	}
	public void setCsidCertIssuerName(String csidCertIssuerName) {
		this.csidCertIssuerName = csidCertIssuerName;
	}
	public String getCsidCertSerialNumber() {
		return csidCertSerialNumber;
	}
	public void setCsidCertSerialNumber(String csidCertSerialNumber) {
		this.csidCertSerialNumber = csidCertSerialNumber;
	}
	
	
	
	
	
}
