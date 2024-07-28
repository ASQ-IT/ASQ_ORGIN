package asq.pos.zatca.cert.generation.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;

import dtv.servicex.impl.req.ServiceRequest;

/**
 * @author RA20221457
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsqSubmitZatcaCertServiceRequest extends ServiceRequest implements IAsqSubmitZatcaCertServiceRequest {

	private String csr;
	private String otp;
	private String compliance_request_id;
	private String invoiceHash;
	private String uuid;
	private String invoice;

	private String requestJSON;

	@Override
	public String getOtp() {
		return otp;
	}

	@Override
	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Override
	public String getRequestJSON() {
		return requestJSON;
	}

	@Override
	public void setRequestJSON(String requestJSON) {
		this.requestJSON = requestJSON;
	}

	@Override
	public String getCsr() {
		return csr;
	}

	@Override
	public void setCsr(String csr) {
		this.csr = csr;
	}

	@Override
	public String getCompliance_request_id() {
		return compliance_request_id;
	}

	@Override
	public void setCompliance_request_id(String compliance_request_id) {
		this.compliance_request_id = compliance_request_id;
	}

	public String getInvoiceHash() {
		return invoiceHash;
	}

	public void setInvoiceHash(String invoiceHash) {
		this.invoiceHash = invoiceHash;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
}
