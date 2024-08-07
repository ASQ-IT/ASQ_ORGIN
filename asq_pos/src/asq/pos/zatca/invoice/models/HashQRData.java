package asq.pos.zatca.invoice.models;

public class HashQRData {
	
	private String QR;
	private String invoiceHash;
	private String certificate;
	private boolean isCertificateExpired=false;
	private String expirationDate;
	
	
	
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public boolean isCertificateExpired() {
		return isCertificateExpired;
	}
	public void setCertificateExpired(boolean isCertificateExpired) {
		this.isCertificateExpired = isCertificateExpired;
	}
	public String getQR() {
		return QR;
	}
	public void setQR(String qR) {
		QR = qR;
	}
	public String getInvoiceHash() {
		return invoiceHash;
	}
	public void setInvoiceHash(String invoiceHash) {
		this.invoiceHash = invoiceHash;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	
	

}
