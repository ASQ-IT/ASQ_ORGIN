package asq.pos.zatca.cert.generation;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsqZatcaValidationResults {

	@JsonProperty("infoMessages")
	private AsqZatcaInfoMessages[] infoMessages;

	@JsonProperty("warningMessages")
	private AsqZatcaWarningMessages[] warningMessages;

	@JsonProperty("errorMessages")
	private AsqZatcaErrorMessages[] errorMessages;

	@JsonProperty("status")
	private String status;

	public AsqZatcaInfoMessages[] getInfoMessages() {
		return infoMessages;
	}

	public void setInfoMessages(AsqZatcaInfoMessages[] infoMessages) {
		this.infoMessages = infoMessages;
	}

	public AsqZatcaWarningMessages[] getWarningMessages() {
		return warningMessages;
	}

	public void setWarningMessages(AsqZatcaWarningMessages[] warningMessages) {
		this.warningMessages = warningMessages;
	}

	public AsqZatcaErrorMessages[] getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(AsqZatcaErrorMessages[] errorMessages) {
		this.errorMessages = errorMessages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
