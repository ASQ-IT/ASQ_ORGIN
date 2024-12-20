package asq.pos.loyalty.neqaty.tender.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;
import dtv.service.req.IServiceResponse;
import dtv.servicex.impl.req.ServiceRequest;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsqNeqatyLoyaltyServiceResponse extends ServiceRequest implements IServiceResponse{
	
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
	private AsqNeqatyErrorDesc [] errors;
	
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
	public AsqNeqatyErrorDesc[] getErrors() {
		return errors;
	}
	public void setErrors(AsqNeqatyErrorDesc[] errors) {
		this.errors = errors;
	}
	
	
}
