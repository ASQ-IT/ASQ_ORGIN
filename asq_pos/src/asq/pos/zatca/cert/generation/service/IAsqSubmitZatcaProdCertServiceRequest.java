package asq.pos.zatca.cert.generation.service;

import dtv.service.req.IServiceRequest;

public interface IAsqSubmitZatcaProdCertServiceRequest extends IServiceRequest {
	
	public String getCompliance_request_id();

	public void setCompliance_request_id(String compliance_request_id);


}
