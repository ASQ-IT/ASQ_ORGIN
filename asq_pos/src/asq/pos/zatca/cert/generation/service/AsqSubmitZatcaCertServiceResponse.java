package asq.pos.zatca.cert.generation.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

import asq.pos.zatca.cert.generation.AsqZatcaErrorDesc;
import asq.pos.zatca.cert.generation.AsqZatcaValidationResults;
import dtv.service.req.IServiceResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsqSubmitZatcaCertServiceResponse implements IServiceResponse {

	@JsonProperty("requestID")
	private String requestID;

	@JsonProperty("dispositionMessage")
	private String dispositionMessage;

	@JsonProperty("binarySecurityToken")
	private String binarySecurityToken;

	@JsonProperty("secret")
	private String secret;

	@JsonProperty("errors")
	private AsqZatcaErrorDesc errors;

	@JsonProperty("validationResults")
	private AsqZatcaValidationResults validationResults;

	@JsonProperty("reportingStatus")
	private String reportingStatus;

	@JsonProperty("clearanceStatus")
	private String clearanceStatus;

	@JsonProperty("qrSellertStatus")
	private String qrSellertStatus;

	@JsonProperty("qrBuyertStatus")
	private String qrBuyertStatus;

	@JsonProperty("code")
	private String code;

	@JsonProperty("message")
	private String message;

	@JsonProperty("timestamp")
	private String timestamp;

	@JsonProperty("error")
	private String error;

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getDispositionMessage() {
		return dispositionMessage;
	}

	public void setDispositionMessage(String dispositionMessage) {
		this.dispositionMessage = dispositionMessage;
	}

	public String getBinarySecurityToken() {
		return binarySecurityToken;
	}

	public void setBinarySecurityToken(String binarySecurityToken) {
		this.binarySecurityToken = binarySecurityToken;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public AsqZatcaErrorDesc getErrors() {
		return errors;
	}

	public void setErrors(AsqZatcaErrorDesc errors) {
		this.errors = errors;
	}

	public AsqZatcaValidationResults getValidationResults() {
		return validationResults;
	}

	public void setValidationResults(AsqZatcaValidationResults validationResults) {
		this.validationResults = validationResults;
	}

	public String getReportingStatus() {
		return reportingStatus;
	}

	public void setReportingStatus(String reportingStatus) {
		this.reportingStatus = reportingStatus;
	}

	public String getClearanceStatus() {
		return clearanceStatus;
	}

	public void setClearanceStatus(String clearanceStatus) {
		this.clearanceStatus = clearanceStatus;
	}

	public String getQrSellertStatus() {
		return qrSellertStatus;
	}

	public void setQrSellertStatus(String qrSellertStatus) {
		this.qrSellertStatus = qrSellertStatus;
	}

	public String getQrBuyertStatus() {
		return qrBuyertStatus;
	}

	public void setQrBuyertStatus(String qrBuyertStatus) {
		this.qrBuyertStatus = qrBuyertStatus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
