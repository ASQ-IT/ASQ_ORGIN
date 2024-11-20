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
	private String username;
	private String invoice;
	private String uuid;
	private String password;

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

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}
}
