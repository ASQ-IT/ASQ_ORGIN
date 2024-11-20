package asq.pos.loyalty.mokafaa.tender.service;

import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oracle.shaded.fasterxml.jackson.core.JsonProcessingException;
import com.oracle.shaded.fasterxml.jackson.databind.JsonMappingException;

import dtv.service.req.IServiceResponse;
import dtv.servicex.RetryServiceType;
import dtv.servicex.ServiceType;
import dtv.servicex.impl.AbstractJaxRsHandler;

public class AsqMokafaaloyaltyServiceHandler extends AbstractJaxRsHandler<AsqMokafaaLoyaltyServiceRequest, IServiceResponse> {
	
	private static final Logger LOG = LogManager.getLogger(AsqMokafaaloyaltyServiceHandler.class);
	
	/**
	 * This class handles Mokafa'a API service calls
	 * 
	 * @param request object
	 * @return response result
	 */

	@Inject
	AsqMokafaaHelper asqMokafaaHelper;

	/**
	 * This method converts API response to JSON object for Earn API
	 * 
	 * @param request object
	 * @return response result
	 */
	
	@Override
	public IServiceResponse handleService(AsqMokafaaLoyaltyServiceRequest argServiceRequest,
			ServiceType<AsqMokafaaLoyaltyServiceRequest, IServiceResponse> argServiceType) {
		Response rawResponse = null;
		try {
			try {
				if (null != argServiceRequest && null != argServiceRequest.getServiceRequest()
						&& "AUTH".equalsIgnoreCase(argServiceRequest.getServiceRequest())) {
					Builder requestBuilder = this.getBaseWebTarget().request(MediaType.APPLICATION_JSON)
							.header("Content-Type", "application/x-www-form-urlencoded");
					rawResponse = requestBuilder.post(Entity.form(getFormForAUthToken()));

				} else {

					LOG.info("Mokafa'a API HandleService Method starts here:");
					String authToken = argServiceRequest.getAuthToken();
					argServiceRequest.setAuthToken(null);
					LOG.info("Mokafa'a API AuthToken for request: :" + authToken);
					String merchantToken = System.getProperty("asq.mokafaa.merchantToken");
					LOG.info("Mokafa'a API Posting request starts here:");
					Builder requestBuilder = this.getBaseWebTarget().request(MediaType.APPLICATION_JSON)
							.header("Content-Type", "application/json").header("Authorization", "Bearer " + authToken)
							.header("merchantToken", "Bearer "+merchantToken);
					rawResponse = requestBuilder.post(Entity.json(asqMokafaaHelper.convertTojson(argServiceRequest)));
				}
				LOG.info("Mokafa'a API returning response :" + rawResponse);
				checkForExceptions(rawResponse);
			} catch (Exception ex) {
				RetryServiceType retryType = new RetryServiceType(argServiceType.getServiceHandlerId());
				String retryRequest = argServiceRequest.toString();
				queueForRetry(retryType, retryRequest);
			}
			return handleResponse(rawResponse);
			// return (IServiceResponse) rawResponse;
		} finally {
			if (rawResponse != null)
				rawResponse.close();
		}
		// return (IServiceResponse) rawResponse;
	}

	private Form getFormForAUthToken() {
		Form form = new Form().param("grant_type", System.getProperty("asq.mokafaa.auth.granttype"))
				.param("scope", System.getProperty("asq.mokafaa.auth.scope"))
				.param("client_id", System.getProperty("asq.mokafaa.auth.clientid"))
				.param("client_secret", System.getProperty("asq.mokafaa.auth.clientsecret"));
		return form;
	}
	
	private IServiceResponse handleResponse(Response argRawResponse) {
		AsqMokafaaLoyaltyServiceResponse response = null;
		if (null != argRawResponse) {
			try {
				response = asqMokafaaHelper.convertJSONToPojo(argRawResponse.readEntity(String.class),
						AsqMokafaaLoyaltyServiceResponse.class);
			} catch (JsonMappingException e) {
				response = new AsqMokafaaLoyaltyServiceResponse();
				response.setErrorCode("-1");
				response.setMessage("Error in Mapping Response to Response class in AsqMokafaaloyaltyServiceHandler");
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				response = new AsqMokafaaLoyaltyServiceResponse();
				response.setErrorCode("-1");
				response.setMessage("JsonProcessingException in AsqMokafaaloyaltyServiceHandler");
				e.printStackTrace();
			}
		} else {
			response = new AsqMokafaaLoyaltyServiceResponse();
			response.setErrorCode("-1");
			response.setMessage("Issue with calling Mokafaa API. Response is null in AsqMokafaaloyaltyServiceHandler");
		}
		return response;
	}
	
}