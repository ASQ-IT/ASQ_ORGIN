package asq.pos.zatca.invoice.submition.worker.service;

import dtv.servicex.impl.req.ServiceRequest;

public class AsqSubmitZatcaInvoiceToOicServiceRequest extends ServiceRequest implements IAsqSubmitZatcaInvoiceToOicServiceRequest {

	private String vatRegNo;
	private String storeNumber;
	private String transactionNo;
	private String businessDate;
	private String creationDate;
	private String tillId;
	private String invoiceHash;
	private String Username;
	private String invoice;
	private String uuid;
	private String Password;

	@Override
	public String getVatRegNo() {
		return vatRegNo;
	}

	@Override
	public void setVatRegNo(String vatRegNo) {
		this.vatRegNo = vatRegNo;
	}

	@Override
	public String getStoreNumber() {
		return storeNumber;
	}

	@Override
	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	@Override
	public String getTransactionNo() {
		return transactionNo;
	}

	@Override
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	@Override
	public String getTillId() {
		return tillId;
	}

	@Override
	public void setTillId(String tillId) {
		this.tillId = tillId;
	}

	@Override
	public String getInvoiceHash() {
		return invoiceHash;
	}

	@Override
	public void setInvoiceHash(String invoiceHash) {
		this.invoiceHash = invoiceHash;
	}

	@Override
	public String getUsername() {
		return Username;
	}

	@Override
	public void setUsername(String username) {
		Username = username;
	}

	@Override
	public String getInvoice() {
		return invoice;
	}

	@Override
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	@Override
	public String getUuid() {
		return uuid;
	}

	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String getPassword() {
		return Password;
	}

	@Override
	public void setPassword(String password) {
		Password = password;
	}

	@Override
	public String getBusinessDate() {
		return businessDate;
	}

	@Override
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	@Override
	public String getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

}
