package asq.pos.loyalty.stc.tender.service;

import dtv.service.req.IServiceRequest;

public interface IAsqSTCLoyaltyServiceRequest extends IServiceRequest {

	public long getMsisdn();
	public void setMsisdn(long msisdn);
	public String getBranchId();
	public void setBranchId(String branchId);
	public String getTerminalId();
	public void setTerminalId(String terminalId);
	public String getRequestDate();
	public void setRequestDate(String dateTime);
	public Integer getPIN();
	public void setPIN(Integer pIN);
	public Integer getAmount();
	public void setAmount(Integer amount);
	public String getTransactionId();
	public void setTransactionId(String transactionId);
	
}
