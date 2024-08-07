package asq.pos.zatca.invoice.models;

public class OutboundInvoice {
	
	private String StoreID;
	private String RegisterID;
	private String InvoiceNumber;
	private String trnNumber;
	private String BusinessDate;
	private String TransactionDateTime;
	private String ICV;
	private String InvoiceHash;
	private String uuid;
	private String SourceSystem;
	private String BinarySecurityToken;
	private String Secret;
	private String IsComplianceCheck;
	private AdditionalTags AdditionalTags;
	private String Invoice;
	
	public OutboundInvoice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OutboundInvoice(String storeID, String registerID, String invoiceNumber, String businessDate,
			String transactionDateTime, String iCV, String invoiceHash, String uUID,String sourceSystem, String invoice) {
		super();
		this.StoreID = storeID;
		this.RegisterID = registerID;
		this.InvoiceNumber = invoiceNumber;
		this.BusinessDate = businessDate;
		this.TransactionDateTime = transactionDateTime;
		ICV = iCV;
		this.InvoiceHash = invoiceHash;
		uuid = uUID;
		this.SourceSystem = sourceSystem;
		this.Invoice = invoice;
	}
	@Override
	public String toString() {
		return "OutboundInvoice [storeID=" + StoreID + ", registerID=" + RegisterID + ", invoiceNumber=" + InvoiceNumber
				+ ", businessDate=" + BusinessDate + ", transactionDateTime=" + TransactionDateTime + ", ICV=" + ICV
				+ ", invoiceHash=" + InvoiceHash + ", UUID=" + uuid + ", sourceSystem=" + SourceSystem + ", invoice=" + Invoice + "]";
	}
	
	
	
	public AdditionalTags getAdditionalTags() {
		return AdditionalTags;
	}
	public void setAdditionalTags(AdditionalTags additionalTags) {
		AdditionalTags = additionalTags;
	}
	public String getStoreID() {
		return StoreID;
	}
	public void setStoreID(String storeID) {
		StoreID = storeID;
	}
	public String getRegisterID() {
		return RegisterID;
	}
	public void setRegisterID(String registerID) {
		RegisterID = registerID;
	}
	public String getInvoiceNumber() {
		return InvoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		InvoiceNumber = invoiceNumber;
	}
	public String getBusinessDate() {
		return BusinessDate;
	}
	public void setBusinessDate(String businessDate) {
		BusinessDate = businessDate;
	}
	public String getTransactionDateTime() {
		return TransactionDateTime;
	}
	public void setTransactionDateTime(String transactionDateTime) {
		TransactionDateTime = transactionDateTime;
	}
	
	public String getICV() {
		return ICV;
	}
	public void setICV(String iCV) {
		ICV = iCV;
	}
	public String getInvoiceHash() {
		return InvoiceHash;
	}
	public void setInvoiceHash(String invoiceHash) {
		InvoiceHash = invoiceHash;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getSourceSystem() {
		return SourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		SourceSystem = sourceSystem;
	}
	public String getBinarySecurityToken() {
		return BinarySecurityToken;
	}
	public void setBinarySecurityToken(String binarySecurityToken) {
		BinarySecurityToken = binarySecurityToken;
	}
	public String getSecret() {
		return Secret;
	}
	public void setSecret(String secret) {
		Secret = secret;
	}
	public String getIsComplianceCheck() {
		return IsComplianceCheck;
	}
	public void setIsComplianceCheck(String isComplianceCheck) {
		IsComplianceCheck = isComplianceCheck;
	}
	public String getInvoice() {
		return Invoice;
	}
	public void setInvoice(String invoice) {
		Invoice = invoice;
	}
	public String getTrnNumber() {
		return trnNumber;
	}
	public void setTrnNumber(String trnNumber) {
		this.trnNumber = trnNumber;
	}

}
