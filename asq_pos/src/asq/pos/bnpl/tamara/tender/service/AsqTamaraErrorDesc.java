package asq.pos.bnpl.tamara.tender.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqTamaraErrorDesc {
	
	/**
	 * This class helps to implement all the error description for TAMARA API
	 */
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("error_code")
	private String error_code;
	
	@JsonProperty("error")
	private String error;
	
	@JsonProperty("message")
	private String message;

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
