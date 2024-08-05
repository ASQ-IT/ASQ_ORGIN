package asq.pos.zatca.invoice.generation.op;

import dtv.servicex.impl.req.ServiceRequest;

public class AsqSubmitZatcaInvoiceRequest extends ServiceRequest implements IAsqSubmitZatcaInvoiceServiceRequest{
	
	private String invoiceHash;
	private String uuid;
	private String invoice;
	
	public AsqSubmitZatcaInvoiceRequest() {
		super();
	}
	public AsqSubmitZatcaInvoiceRequest(String invoiceHash, String uUID,String invoice) {
		super();
		this.invoiceHash = invoiceHash;
		uuid = uUID;
		this.invoice = invoice;
	}
	@Override
	public String toString() {
		return "AsqZatcaInvoiceRequest [invoiceHash=" + invoiceHash + ", UUID=" + uuid + ", invoice=" + invoice + "]";
	}
	public String getInvoiceHash() {
		return invoiceHash;
	}
	public void setInvoiceHash(String invoiceHash) {
		this.invoiceHash = invoiceHash;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	
	
}
