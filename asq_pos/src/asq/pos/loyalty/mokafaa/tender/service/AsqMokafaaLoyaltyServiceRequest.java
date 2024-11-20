package asq.pos.loyalty.mokafaa.tender.service;

import dtv.servicex.impl.req.ServiceRequest;

public class AsqMokafaaLoyaltyServiceRequest extends ServiceRequest implements IAsqMokafaaLoyaltyServiceRequest{
	
	/**
	 * This class has all request attribute POJO's
	 */
	private String serviceRequest;
	private String mobile;
	private String currency;
	private String lang;
	private String loyaltyCICNo;
	private String OTPValue;
	private String OTPToken;
	private String amount;
	private String language;
	private Long transactionID;
	
	private String authToken;
	
	
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getLoyaltyCICNo() {
		return loyaltyCICNo;
	}
	public void setLoyaltyCICNo(String loyaltyCICNo) {
		this.loyaltyCICNo = loyaltyCICNo;
	}
	public String getOTPValue() {
		return OTPValue;
	}
	public void setOTPValue(String oTPValue) {
		OTPValue = oTPValue;
	}
	public String getOTPToken() {
		return OTPToken;
	}
	public void setOTPToken(String oTPToken) {
		OTPToken = oTPToken;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getServiceRequest() {
		return serviceRequest;
	}
	public void setServiceRequest(String serviceRequest) {
		this.serviceRequest = serviceRequest;
	}
	public Long getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(Long transactionID) {
		this.transactionID = transactionID;
	}
	
	
}
