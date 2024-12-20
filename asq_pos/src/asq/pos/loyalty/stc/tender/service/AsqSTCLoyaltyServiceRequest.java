package asq.pos.loyalty.stc.tender.service;

import dtv.servicex.impl.req.ServiceRequest;

public class AsqSTCLoyaltyServiceRequest extends ServiceRequest implements IAsqSTCLoyaltyServiceRequest{
	
	/**
	 * This class has all request attribute POJO's
	 */
	
	long   Msisdn;
	String BranchId;
	String TerminalId;
	String RequestDate;
	Integer PIN;
	Integer Amount;
	String transactionId;
	String globalId;
	String RefRequestId;
	String RefRequestDate;
	
	public String getRefRequestId() {
		return RefRequestId;
	}
	public void setRefRequestId(String refRequestId) {
		RefRequestId = refRequestId;
	}
	public String getRefRequestDate() {
		return RefRequestDate;
	}
	public void setRefRequestDate(String refRequestDate) {
		RefRequestDate = refRequestDate;
	}
	public String getGlobalId() {
		return globalId;
	}
	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public long getMsisdn() {
		return Msisdn;
	}
	public void setMsisdn(long msisdn) {
		Msisdn = msisdn;
	}
	public String getBranchId() {
		return BranchId;
	}
	public void setBranchId(String branchId) {
		BranchId = branchId;
	}
	public String getTerminalId() {
		return TerminalId;
	}
	public void setTerminalId(String terminalId) {
		TerminalId = terminalId;
	}
	public String getRequestDate() {
		return RequestDate;
	}
	public void setRequestDate(String requestDate) {
		RequestDate = requestDate;
	}
	public Integer getPIN() {
		return PIN;
	}
	public void setPIN(Integer pIN) {
		PIN = pIN;
	}
	public Integer getAmount() {
		return Amount;
	}
	public void setAmount(Integer amount) {
		Amount = amount;
	}
}
