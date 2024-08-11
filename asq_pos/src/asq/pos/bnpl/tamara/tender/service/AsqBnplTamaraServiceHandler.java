package asq.pos.bnpl.tamara.tender.service;

import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import asq.pos.zatca.cert.generation.AsqZatcaIntegrationConstants;
import dtv.service.req.IServiceResponse;
import dtv.servicex.RetryServiceType;
import dtv.servicex.ServiceType;
import dtv.servicex.impl.AbstractJaxRsHandler;

public class AsqBnplTamaraServiceHandler extends AbstractJaxRsHandler<AsqSubmitBnplTamraServiceRequest, IServiceResponse> {

	@Inject
	AsqZatcaHelper asqZatcaHelper;

	@Override
	public IServiceResponse handleService(AsqSubmitBnplTamraServiceRequest argServiceRequest, ServiceType<AsqSubmitBnplTamraServiceRequest, IServiceResponse> argServiceType) {
		Response rawResponse = null;
		try {
			Builder requestBuilder = this.getBaseWebTarget().request().header("Content-Type", "application/json")
					.header(AsqZatcaIntegrationConstants.Authorization, System.getProperty("asq.bnpl.tender.tamara.token")).header("Accept-Version", "V2");
			rawResponse = requestBuilder.post(Entity.json(asqZatcaHelper.convertTojson(argServiceRequest)));
			checkForExceptions(rawResponse);
		} catch (Exception ex) {
			RetryServiceType retryType = new RetryServiceType(argServiceType.getServiceHandlerId());
			String retryRequest = argServiceRequest.toString();
			queueForRetry(retryType, retryRequest);
		} finally {
			if (rawResponse != null)
				rawResponse.close();
		}
		return (IServiceResponse) rawResponse;
	}

}
