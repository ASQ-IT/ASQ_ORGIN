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
	public void setRequestDate(String date);
	public Integer getPIN();
	public void setPIN(Integer pIN);
	public Integer getAmount();
	public void setAmount(Integer amount);
	public String getTransactionId();
	public void setTransactionId(String transactionId);
	public String getGlobalId();
	public void setGlobalId(String globalId);
	public String getRefRequestId();
	public void setRefRequestId(String refRequestId);
	public String getRefRequestDate();
	public void setRefRequestDate(String refRequestDate);
}
