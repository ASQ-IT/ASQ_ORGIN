package asq.pos.zatca.cert.generation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties
public class AsqZatcaWarningMessages {

	@JsonProperty("type")
	private String type;

	@JsonProperty("code")
	private String code;

	@JsonProperty("category")
	private String category;

	@JsonProperty("message")
	private String message;

	@JsonProperty("status")
	private String status;

	public String getType() {
		return type;
	}

	public String getCode() {
		return code;
	}

	public String getCategory() {
		return category;
	}

	public String getMessage() {
		return message;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
