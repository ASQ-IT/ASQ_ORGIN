package asq.pos.zatca.invoice.generation.op;

import dtv.service.req.IServiceRequest;

/**
 * 
 * @author AT20315667
 *
 */
public interface IAsqSubmitZatcaInvoiceServiceRequest extends IServiceRequest {
	

	public String getInvoiceHash();
	
	public void setInvoiceHash(String invoiceHash);
	
	public String getUuid();
	
	public void setUuid(String uuid);
	
	public String getInvoice();
	
	public void setInvoice(String invoice);
	
	
}
