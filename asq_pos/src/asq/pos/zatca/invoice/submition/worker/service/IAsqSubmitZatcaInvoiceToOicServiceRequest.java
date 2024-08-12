package asq.pos.zatca.invoice.submition.worker.service;

import java.util.Date;

import dtv.service.req.IServiceRequest;

public interface IAsqSubmitZatcaInvoiceToOicServiceRequest extends IServiceRequest {

	public String getVatRegNo();

	public void setVatRegNo(String vatRegNo);

	public String getStoreNumber();

	public void setStoreNumber(String storeNumber);

	public String getTransactionNo();

	public void setTransactionNo(String transactionNo);

	public Date getBusinessDate();

	public void setBusinessDate(Date businessDate);

	public Date getCreationDate();

	public void setCreationDate(Date creationDate);

	public String getTillId();

	public void setTillId(String tillId);

	public String getInvoiceHash();

	public void setInvoiceHash(String invoiceHash);

	public String getUsername();

	public void setUsername(String username);

	public String getInvoice();

	public void setInvoice(String invoice);

	public String getUuid();

	public void setUuid(String uuid);

	public String getPassword();

	public void setPassword(String password);
}
