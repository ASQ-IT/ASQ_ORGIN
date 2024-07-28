package asq.pos.zatca.cert.generation.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

import asq.pos.zatca.cert.generation.AsqZatcaErrorDesc;
import dtv.service.req.IServiceResponse;

/**
 * @author RA20221457
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqSubmitZatcaCertServiceResponse implements IServiceResponse {

	@JsonProperty("requestID")
	private String requestID;
	@JsonProperty("dispositionMessage")
	private String dispositionMessage;
	@JsonProperty("binarySecurityToken")
	private String binarySecurityToken;
	@JsonProperty("secret")
	private String secret;
	private AsqZatcaErrorDesc errors;

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getDispositionMessage() {
		return dispositionMessage;
	}

	public void setDispositionMessage(String dispositionMessage) {
		this.dispositionMessage = dispositionMessage;
	}

	public String getBinarySecurityToken() {
		return binarySecurityToken;
	}

	public void setBinarySecurityToken(String binarySecurityToken) {
		this.binarySecurityToken = binarySecurityToken;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public AsqZatcaErrorDesc getErrors() {
		return errors;
	}

	public void setErrors(AsqZatcaErrorDesc errors) {
		this.errors = errors;
	}

}
