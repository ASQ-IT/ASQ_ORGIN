package asq.pos.zatca.invoice.models;

public class InvoiceICV {
	
	private Integer icv;
	private String invoiceId;
	private String hash;
	private String invoiceDate;
	
	@Override
	public String toString() {
		return "InvoiceICV [icv=" + icv + ", invoiceId=" + invoiceId + ", hash=" + hash + ", invoiceDate=" + invoiceDate
				+ "]";
	}

	public InvoiceICV() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Integer getIcv() {
		return icv;
	}
	public void setIcv(Integer icv) {
		this.icv = icv;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	

}
