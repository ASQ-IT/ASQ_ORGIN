package asq.pos.zatca.invoice.models;

public class OnboardingResponse {
	
	//private String responseCode;
	//private String errorCode;
//	private String error;
	//private String csid;
	//private int statusCode;
	
	private String requestID;
	private String dispositionMessage;
	private String binarySecurityToken;
	private String secret;
	private String errors;
	
	
	

	@Override
	public String toString() {
		return "OutboundResponse [requestID=" + requestID + ", dispositionMessage=" + dispositionMessage
				+ ", binarySecurityToken=" + binarySecurityToken + ", secret=" + secret + ", errors=" + errors + "]";
	}
	
//	public String toString() {
//		return "OutboundResponse [responseCode=" + responseCode + ", errorCode=" + errorCode + ", error=" + error
//				+ ", csid=" + csid + "]";
//	}
//	public OnboardingResponse(String responseCode, String errorCode, String error, String csid) {
//		super();
//		this.responseCode = responseCode;
//		this.errorCode = errorCode;
//		this.error = error;
//		this.csid = csid;
//	}
	public OnboardingResponse(String requestID, String dispositionMessage, String binarySecurityToken, String secret) {
		super();
		//this.statusCode = statusCode;
		this.requestID = requestID;
		this.dispositionMessage = dispositionMessage;
		this.binarySecurityToken = binarySecurityToken;
		this.secret = secret;
		this.errors = errors;
	}
//	public int getStatusCode() {
//		return statusCode;
//	}
//
//	public void setStatusCode(int statusCode) {
//		this.statusCode = statusCode;
//	}

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
	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

//	public String getResponseCode() {
//		return responseCode;
//	}
//	public void setResponseCode(String responseCode) {
//		this.responseCode = responseCode;
//	}
//	public String getErrorCode() {
//		return errorCode;
//	}
//	public void setErrorCode(String errorCode) {
//		this.errorCode = errorCode;
//	}
//	public String getError() {
//		return error;
//	}
//	public void setError(String error) {
//		this.error = error;
//	}
//	public String getCsid() {
//		return csid;
//	}
//	public void setCsid(String csid) {
//		this.csid = csid;
//	}
	
	

}
