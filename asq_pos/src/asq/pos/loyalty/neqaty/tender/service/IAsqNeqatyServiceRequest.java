package asq.pos.loyalty.neqaty.tender.service;

import dtv.service.req.IServiceRequest;

public interface IAsqNeqatyServiceRequest extends IServiceRequest {

	public String getMsisdn();
	public void setMsisdn(String msisdn);
	public String getAuthenticationKey();
	public void setAuthenticationKey(String authenticationKey);
	public int getTid();
	public void setTid(int tid);
	public int getToken();
	public void setToken(int token);
	public String getOperationType();
	public void setOperationType(String operationType);
	public double getAmount();
	public void setAmount(double amount);
	public double getRedeemPoints();
	public void setRedeemPoints(double redeemPoints);
	public int getRedeemCode();
	public void setRedeemCode(int redeemCode);
	public String getTransactionReference();
	public void setTransactionReference(String transactionReference);
	public NeqatyMethod getMethod();
	public void setMethod(NeqatyMethod method);
	public String getOtp();
	public void setOtp(String otp);

}
