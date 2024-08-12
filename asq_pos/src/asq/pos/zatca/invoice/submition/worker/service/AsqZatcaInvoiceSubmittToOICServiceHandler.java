package asq.pos.zatca.invoice.submition.worker.service;

import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.zatca.cert.generation.AsqZatcaErrorDesc;
import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceResponse;
import dtv.service.req.IServiceResponse;
import dtv.servicex.ServiceType;
import dtv.servicex.impl.AbstractJaxRsHandler;

public class AsqZatcaInvoiceSubmittToOICServiceHandler extends AbstractJaxRsHandler<AsqSubmitZatcaInvoiceToOicServiceRequest, IServiceResponse> {

	private static final Logger LOG = LogManager.getLogger(AsqZatcaInvoiceSubmittToOICServiceHandler.class);

	@Inject
	AsqZatcaHelper asqZatcaHelper;

	@Override
	public IServiceResponse handleService(AsqSubmitZatcaInvoiceToOicServiceRequest argServiceRequest, ServiceType<AsqSubmitZatcaInvoiceToOicServiceRequest, IServiceResponse> argServiceType) {
		Response rawResponse = null;
		Builder requestBuilder = getZatcaBaseRequestHeader();
		try {
			rawResponse = requestBuilder.post(Entity.json(asqZatcaHelper.convertTojson(argServiceRequest)));
			checkForExceptions(rawResponse);
			return asqZatcaHelper.convertJSONToPojo(rawResponse.readEntity(String.class), AsqSubmitZatcaCertServiceResponse.class);
		} catch (Exception ex) {
			LOG.error("We have reieved exception in calling of Zatca webservice ", ex);
			AsqSubmitZatcaCertServiceResponse arg = new AsqSubmitZatcaCertServiceResponse();
			AsqZatcaErrorDesc errDesc = new AsqZatcaErrorDesc();
			if (null != rawResponse.getStatusInfo()) {
				errDesc.setCode(String.valueOf(rawResponse.getStatusInfo().getStatusCode()));
				errDesc.setMessage(rawResponse.getStatusInfo().getReasonPhrase());
			} else {
				errDesc.setCode(String.valueOf(ex.hashCode()));
				errDesc.setMessage(ex.getMessage());
			}
			arg.setErrors(errDesc);
			return arg;
		} finally {
			if (rawResponse != null) {
				rawResponse.close();
			}
		}
	}

	Builder getZatcaBaseRequestHeader() {
		return this.getBaseWebTarget().request().header("Content-Type", "application/json").header("Accept-Version", "V2");
	}
}
