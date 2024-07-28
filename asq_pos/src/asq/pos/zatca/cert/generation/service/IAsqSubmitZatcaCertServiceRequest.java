package asq.pos.zatca.cert.generation.service;

import dtv.service.req.IServiceRequest;

/**
 * 
 * @author AT20315667
 *
 */
public interface IAsqSubmitZatcaCertServiceRequest extends IServiceRequest {

	public String getOtp();

	public void setOtp(String otp);

	public String getRequestJSON();

	public void setRequestJSON(String requestJSON);

	public String getCsr();

	public void setCsr(String csr);

	public String getCompliance_request_id();

	public void setCompliance_request_id(String compliance_request_id);
}
