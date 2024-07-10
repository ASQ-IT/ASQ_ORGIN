package asq.pos.zatca.cert.generation.service;

import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.oracle.shaded.fasterxml.jackson.core.JsonProcessingException;
import com.oracle.shaded.fasterxml.jackson.databind.ObjectMapper;

import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import dtv.service.req.IServiceResponse;
import dtv.servicex.RetryServiceType;
import dtv.servicex.ServiceType;
import dtv.servicex.impl.AbstractJaxRsHandler;
import dtv.servicex.impl.req.ServiceResponse;
import javax.ws.rs.client.Invocation.Builder;

public class AsqZatcaCertRegistrationServiceHandler extends AbstractJaxRsHandler<AsqSubmitZatcaCertServiceRequest, IServiceResponse> {
	
	@Inject
	AsqZatcaHelper asqZatcaHelper;

	@Override
	public IServiceResponse handleService(AsqSubmitZatcaCertServiceRequest argServiceRequest,
			ServiceType<AsqSubmitZatcaCertServiceRequest, IServiceResponse> argServiceType) {

		Response rawResponse = null;
		ServiceResponse submitResponse = null;
		try {
			try {
				Builder requestBuilder = this.getBaseWebTarget().request().header("Accept-Version", "V2")
					        .header(AsqZatcaIntegrationConstants.ZATCA_OTP,argServiceRequest.getOtp());
				
				argServiceRequest.setOtp(null);
				rawResponse = requestBuilder.post(Entity.json(asqZatcaHelper.convertTojson(argServiceRequest)));
				checkForExceptions(rawResponse);
				submitResponse = (ServiceResponse) rawResponse.readEntity(ServiceResponse.class);
			} catch (Exception ex) {
				RetryServiceType retryType = new RetryServiceType(argServiceType.getServiceHandlerId());
				String retryRequest = argServiceRequest.toString();
				queueForRetry(retryType, retryRequest);
			}
			return (IServiceResponse) submitResponse;
		} finally {
			if (rawResponse != null)
				rawResponse.close();
		}
	}
	
	public IServiceResponse handleResponse(String argResponse) {
		asqZatcaHelper.convertJSONToPojo(argResponse,);
	}
}
