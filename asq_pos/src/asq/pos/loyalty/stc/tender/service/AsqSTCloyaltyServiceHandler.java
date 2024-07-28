package asq.pos.loyalty.stc.tender.service;

import java.util.Base64;

import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import asq.pos.loyalty.stc.tender.AsqStcHelper;
import dtv.service.req.IServiceResponse;
import dtv.servicex.RetryServiceType;
import dtv.servicex.ServiceType;
import dtv.servicex.impl.AbstractJaxRsHandler;
import dtv.servicex.impl.req.ServiceResponse;

public class AsqSTCloyaltyServiceHandler extends AbstractJaxRsHandler<IAsqSTCLoyaltyServiceRequest, IServiceResponse> {

	@Inject
	AsqStcHelper asqStcHelper;

	@Override
	public IServiceResponse handleService(IAsqSTCLoyaltyServiceRequest argServiceRequest,
			ServiceType<IAsqSTCLoyaltyServiceRequest, IServiceResponse> argServiceType) {
		// Here we write the calling code
		Response rawResponse = null;
		Builder requestBuilder = null;
		ServiceResponse submitResponse = null;
		try {
			try {
				String secretToken = System.getProperty("asq.stc.auth.secrettoken");
				String authUsernamePassword = System.getProperty("asq.stc.auth.username") + ":"
						+ System.getProperty("asq.stc.auth.password");
				String authToken = Base64.getEncoder().encodeToString(authUsernamePassword.getBytes());
				String transactionId = argServiceRequest.getTransactionId();
				argServiceRequest.setTransactionId(null);

				requestBuilder = this.getBaseWebTarget().request().header("Content-Type", "application/json")
						.header("Accept-Language", "en").header("Accept-Version", "V2")
						.header("Authorization", "Basic " + authToken).header("X-Secret-Token", secretToken)
						.header("GlobalId", transactionId);
				rawResponse = requestBuilder.post(Entity.json(asqStcHelper.convertTojson(argServiceRequest)));
				checkForExceptions(rawResponse);

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

}
