package asq.pos.loyalty.neqaty.tender.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

public class AsqNeqatyErrorDesc {

	/**
	 * This class helps to implement all the error description for STC API
	 */

	@JsonProperty("code")
	private int code;
	@JsonProperty("description")
	private String description;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
