package asq.pos.loyalty.stc.tender.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

public class AsqSTCServiceResponseError {
	
	/**
	 * This class has all error attributes POJO's
	 */

	@JsonProperty("code")
	private String code;
	@JsonProperty("description")
	private String description;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
