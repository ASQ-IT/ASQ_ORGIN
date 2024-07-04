package asq.pos.loyality.stc.tender.service;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import dtv.service.req.IServiceResponse;
import dtv.servicex.RetryServiceType;
import dtv.servicex.ServiceType;
import dtv.servicex.impl.AbstractJaxRsHandler;
import dtv.servicex.impl.req.ServiceResponse;

public class AsqSTCloyalityServiceHandler
		extends AbstractJaxRsHandler<IAsqSTCLoyalityServiceRequest, IServiceResponse> {

	@Override
	public IServiceResponse handleService(IAsqSTCLoyalityServiceRequest argServiceRequest,
			ServiceType<IAsqSTCLoyalityServiceRequest, IServiceResponse> argServiceType) {
		// Here we write the calling code
		Response rawResponse = null;
		ServiceResponse submitResponse = null;
		try {
			try {
				WebTarget target = getBaseWebTarget();
				target.getConfiguration().getProperty("secertToken");
				
				rawResponse = target.request().post(Entity.json(argServiceRequest));
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

}
