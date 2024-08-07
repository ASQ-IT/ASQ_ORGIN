package asq.pos.zatca.invoice.models;

public class InvoiceInput {

	private String storeID;
	private String registerID;
	private String invoiceNumber;
	private String businessDate;
	private String transactionDateTime;
	private InvoiceData invoice;

	public String getStoreID() {
		return storeID;
	}

	public void setStoreID(String storeID) {
		this.storeID = storeID;
	}

	public String getRegisterID() {
		return registerID;
	}

	public void setRegisterID(String registerID) {
		this.registerID = registerID;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	public String getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public InvoiceData getInvoice() {
		return invoice;
	}

	public void setInvoice(InvoiceData invoice) {
		this.invoice = invoice;
	}

}
