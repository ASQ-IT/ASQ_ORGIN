package asq.pos.loyalty.stc.tender.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.oracle.shaded.fasterxml.jackson.core.JsonProcessingException;
import asq.pos.loyalty.stc.tender.AsqStcHelper;
import dtv.service.req.IServiceResponse;
import dtv.servicex.RetryServiceType;
import dtv.servicex.ServiceType;
import dtv.servicex.impl.AbstractJaxRsHandler;

public class AsqSTCloyaltyServiceHandler extends AbstractJaxRsHandler<IAsqSTCLoyaltyServiceRequest, IServiceResponse> {
	
	private static final Logger LOG = LogManager.getLogger(AsqSTCloyaltyServiceHandler.class);
	
	/**
	 * This class handles STC API service calls
	 * 
	 * @param request object
	 * @return response result
	 */

	@Inject
	AsqStcHelper asqStcHelper;

	/**
	 * This method converts API response to JSON object for Earn API
	 * 
	 * @param request object
	 * @return response result
	 */
	
	@Override
	public IServiceResponse handleService(IAsqSTCLoyaltyServiceRequest argServiceRequest,
			ServiceType<IAsqSTCLoyaltyServiceRequest, IServiceResponse> argServiceType) {
		HttpResponse<String> rawResponse = null;
		try {
			LOG.info("STC API HandleService Method starts here:");
			String secretToken = System.getProperty("asq.stc.auth.secrettoken");
			String authUsernamePassword = System.getProperty("asq.stc.auth.username") + ":"
					+ System.getProperty("asq.stc.auth.password");
			String authToken = Base64.getEncoder().encodeToString(authUsernamePassword.getBytes());
			LOG.info("STC API AuthToken for request: :" +authToken);
			String globalId = argServiceRequest.getGlobalId();
			argServiceRequest.setGlobalId(null);
			HttpClient httpClient = HttpClient.newHttpClient();
			java.net.http.HttpRequest.Builder builder = HttpRequest.newBuilder();
			builder.header("Content-Type", "application/json").header("Authorization", "Basic " + authToken)
					.header("X-Secret-Token", secretToken).header("GlobalId", globalId);
			builder.uri(URI.create(getEndpointAddress() + getServicePath()));
			LOG.info("STC API Posting request starts here:");
			builder.POST(HttpRequest.BodyPublishers.ofString(asqStcHelper.convertTojson(argServiceRequest)));
			HttpRequest httpRequest = builder.build();
			rawResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
			LOG.info("STC API returning response :" +rawResponse);
			checkForExceptions(rawResponse);
			System.out.println();
			return asqStcHelper.convertJSONToPojo(rawResponse.body(), AsqSTCLoyaltyServiceResponse.class);
		} catch (Exception ex) {
			LOG.error("WE have recieved a exception in STC we service call ", ex);
			if (200 != rawResponse.statusCode()) {
				try {
					return asqStcHelper.convertJSONToPojo(rawResponse.body(), AsqSTCLoyaltyServiceResponse.class);
				} catch (JsonProcessingException e) {
					AsqSTCLoyaltyServiceResponse arg = new AsqSTCLoyaltyServiceResponse();
					AsqSTCErrorDesc errDesc = new AsqSTCErrorDesc();
					errDesc.setCode(String.valueOf(rawResponse.statusCode()));
					errDesc.setDescription("Response Mapping Failed in AsqSTCloyaltyServiceHandler");
					arg.setErrors(new AsqSTCErrorDesc[] { errDesc });
					return arg;
				}
			}
		}
		return null;
	}

	public IServiceResponse callWebBased(IAsqSTCLoyaltyServiceRequest argServiceRequest,
			ServiceType<IAsqSTCLoyaltyServiceRequest, IServiceResponse> argServiceType) {
		Response rawResponse = null;
		Builder requestBuilder = null;
		try {
			String secretToken = System.getProperty("asq.stc.auth.secrettoken");
			String authUsernamePassword = System.getProperty("asq.stc.auth.username") + ":"
					+ System.getProperty("asq.stc.auth.password");
			String authToken = Base64.getEncoder().encodeToString(authUsernamePassword.getBytes());
			String globalId = argServiceRequest.getGlobalId();
			argServiceRequest.setGlobalId(null);
			requestBuilder = getBaseWebTarget().request().header("Content-Type", "application/json")
					.header("Accept-Language", "en").header("Accept-Version", "V2")
					.header("Authorization", "Basic " + authToken).header("X-Secret-Token", secretToken)
					.header("GlobalId", globalId);
			rawResponse = requestBuilder.post(Entity.json(asqStcHelper.convertTojson(argServiceRequest)));
			checkForExceptions(rawResponse);
			System.out.println("rawResponse" + rawResponse);
		} catch (Exception ex) {
			LOG.error("WE have recieved a exception in STC we service call ", ex);
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