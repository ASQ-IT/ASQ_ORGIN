package asq.pos.zatca.integration.data;

public class ComplianceResponse {

	private String requestID;
	private String dispositionMessage;
	private String binarySecurityToken;
	private String secret;
	private String errors;
	
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



	public String toString() {
		return "OutboundResponse [requestID=" + requestID + ", dispositionMessage=" + dispositionMessage
				+ ", binarySecurityToken=" + binarySecurityToken + ", secret=" + secret + ", errors=" + errors + "]";
	}
	
}
