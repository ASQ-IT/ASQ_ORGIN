package asq.pos.bnpl.tamara.tender.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

public class AsqTamaraErrorDesc {
	
	/**
	 * This class helps to implement all the error description for TAMARA API
	 */

	@JsonProperty("error_code")
	private String error_code;

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
}
