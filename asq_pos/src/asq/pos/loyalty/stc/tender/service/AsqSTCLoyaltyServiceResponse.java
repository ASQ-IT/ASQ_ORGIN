package asq.pos.loyalty.stc.tender.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

import dtv.service.req.IServiceResponse;
import dtv.servicex.impl.req.ServiceRequest;

public class AsqSTCLoyaltyServiceResponse extends ServiceRequest implements IServiceResponse {

	@JsonProperty("code")
	private String code;
	@JsonProperty("description")
	private String description;
	@JsonProperty("errors")
	private AsqSTCServiceResponseError[] errors;

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

	public AsqSTCServiceResponseError[] getErrors() {
		return errors;
	}

	public void setErrors(AsqSTCServiceResponseError[] errors) {
		this.errors = errors;
	}
}
