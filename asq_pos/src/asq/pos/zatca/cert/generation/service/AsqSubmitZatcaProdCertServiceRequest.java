/**
 *
 */
package asq.pos.zatca.cert.generation.service;

import dtv.servicex.impl.req.ServiceRequest;

/**
 * @author RA20221457
 *
 */
public class AsqSubmitZatcaProdCertServiceRequest extends ServiceRequest implements IAsqSubmitZatcaProdCertServiceRequest {

	private String compliance_request_id;

	@Override
	public String getCompliance_request_id() {
		return compliance_request_id;
	}

	@Override
	public void setCompliance_request_id(String compliance_request_id) {
		this.compliance_request_id = compliance_request_id;
	}
}
