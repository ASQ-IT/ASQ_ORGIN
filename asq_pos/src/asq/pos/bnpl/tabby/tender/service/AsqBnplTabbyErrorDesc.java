package asq.pos.bnpl.tabby.tender.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqBnplTabbyErrorDesc {
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("errorType")
	private String errorType;
	
	@JsonProperty("error")
	private String error;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	

}
