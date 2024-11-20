package asq.pos.loyalty.mokafaa.tender.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

import dtv.service.req.IServiceResponse;
import dtv.servicex.impl.req.ServiceRequest;

public class AsqMokafaaLoyaltyServiceResponse extends ServiceRequest implements IServiceResponse{
	
	/**
	 * This class has all response attributes POJO's
	 */
	
	@JsonProperty("message")
	private String message;
	@JsonProperty("status")
	private String status;
	@JsonProperty("requestID")
	private String requestID;
	@JsonProperty("errorCode")
	private String errorCode;
	
	//customer-authorization
	@JsonProperty("otp")
	private OTP otp;
	
	///oauth2/token
	@JsonProperty("token_type")
	private String token_type;
	@JsonProperty("access_token")
	private String access_token;
	@JsonProperty("scope")
	private String scope;
	@JsonProperty("expires_in")
	private String expires_in;
	@JsonProperty("consented_on")
	private String consented_on;
	
	//otp-validation
	@JsonProperty("merchant")
	private String merchant;
	@JsonProperty("transactionDate")
	private String transactionDate;
	@JsonProperty("transactionID")
	private Long transactionID;
	@JsonProperty("transactionType")
	private String transactionType;
	@JsonProperty("pointsAmount")
	private Long pointsAmount;
	
	@JsonProperty("error")
	private String error;
	@JsonProperty("error_description")
	private String error_description;
	
	@JsonProperty("httpCode")
	private String httpCode;
	@JsonProperty("httpMessage")
	private String httpMessage;
	@JsonProperty("moreInformation")
	private String moreInformation;
	
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public OTP getOtp() {
		return otp;
	}
	public void setOtp(OTP otp) {
		this.otp = otp;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getConsented_on() {
		return consented_on;
	}
	public void setConsented_on(String consented_on) {
		this.consented_on = consented_on;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Long getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(Long transactionID) {
		this.transactionID = transactionID;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Long getPointsAmount() {
		return pointsAmount;
	}
	public void setPointsAmount(Long pointsAmount) {
		this.pointsAmount = pointsAmount;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getError_description() {
		return error_description;
	}
	public void setError_description(String error_description) {
		this.error_description = error_description;
	}
	public String getHttpCode() {
		return httpCode;
	}
	public void setHttpCode(String httpCode) {
		this.httpCode = httpCode;
	}
	public String getHttpMessage() {
		return httpMessage;
	}
	public void setHttpMessage(String httpMessage) {
		this.httpMessage = httpMessage;
	}
	public String getMoreInformation() {
		return moreInformation;
	}
	public void setMoreInformation(String moreInformation) {
		this.moreInformation = moreInformation;
	}		
}
