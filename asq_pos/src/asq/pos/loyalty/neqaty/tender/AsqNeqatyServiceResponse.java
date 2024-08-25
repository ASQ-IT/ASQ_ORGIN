package asq.pos.loyalty.neqaty.tender;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

import asq.pos.loyalty.stc.tender.service.AsqSTCErrorDesc;
import dtv.service.req.IServiceResponse;
import dtv.servicex.impl.req.ServiceRequest;

public class AsqNeqatyServiceResponse extends ServiceRequest implements IServiceResponse {

	/**
	 * This class has all response attributes POJO's
	 */

	@JsonProperty("code")
	private String code;
	@JsonProperty("description")
	private String description;
	@JsonProperty("points")
	private String points;
	@JsonProperty("errors")
	private AsqSTCErrorDesc[] errors;

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

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public AsqSTCErrorDesc[] getErrors() {
		return errors;
	}

	public void setErrors(AsqSTCErrorDesc[] errors) {
		this.errors = errors;
	}

}
