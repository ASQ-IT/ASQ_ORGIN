package asq.pos.loyalty.neqaty.tender.service;

import dtv.servicex.impl.req.ServiceRequest;

public class AsqNeqatyServiceRequest extends ServiceRequest implements IAsqNeqatyServiceRequest {

	/**
	 * This class has all request attribute POJO's
	 */

	String Msisdn;
	String authenticationKey;
	int tid;
	int token;
	String operationType;
	double amount;
	double redeemPoints;
	int redeemCode;
	String transactionReference;
	String otp;
	NeqatyMethod method;
	
	public NeqatyMethod getMethod() {
		return method;
	}
	public void setMethod(NeqatyMethod method) {
		this.method = method;
	}
	public String getMsisdn() {
		return Msisdn;
	}
	public void setMsisdn(String msisdn) {
		Msisdn = msisdn;
	}
	public String getAuthenticationKey() {
		return authenticationKey;
	}
	public void setAuthenticationKey(String authenticationKey) {
		this.authenticationKey = authenticationKey;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public int getToken() {
		return token;
	}
	public void setToken(int token) {
		this.token = token;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getRedeemPoints() {
		return redeemPoints;
	}
	public void setRedeemPoints(double redeemPoints) {
		this.redeemPoints = redeemPoints;
	}
	public int getRedeemCode() {
		return redeemCode;
	}
	public void setRedeemCode(int redeemCode) {
		this.redeemCode = redeemCode;
	}
	public String getTransactionReference() {
		return transactionReference;
	}
	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}

}
