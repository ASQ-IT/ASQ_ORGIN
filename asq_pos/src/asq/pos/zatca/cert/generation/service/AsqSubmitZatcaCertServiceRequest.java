/**
 * 
 */
package asq.pos.zatca.cert.generation.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;

import dtv.servicex.impl.req.ServiceRequest;

/**
 * @author RA20221457
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsqSubmitZatcaCertServiceRequest extends ServiceRequest implements IAsqSubmitZatcaCertServiceRequest{
	
	private String csr;
	private String otp;
	
	private String requestJSON;
	
	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	public String getRequestJSON() {
		return requestJSON;
	}

	public void setRequestJSON(String requestJSON) {
		this.requestJSON = requestJSON;
	}

	public String getCsr() {
		return csr;
	}

	public void setCsr(String csr) {
		this.csr = csr;
	}

}
