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

import asq.pos.loyalty.stc.tender.AsqStcHelper;
import dtv.service.req.IServiceResponse;
import dtv.servicex.RetryServiceType;
import dtv.servicex.ServiceType;
import dtv.servicex.impl.AbstractJaxRsHandler;

public class AsqSTCloyaltyServiceHandler extends AbstractJaxRsHandler<IAsqSTCLoyaltyServiceRequest, IServiceResponse> {

	@Inject
	AsqStcHelper asqStcHelper;

	private static final Logger logger = LogManager.getLogger(AsqSTCloyaltyServiceHandler.class);

	@Override
	public IServiceResponse handleService(IAsqSTCLoyaltyServiceRequest argServiceRequest, ServiceType<IAsqSTCLoyaltyServiceRequest, IServiceResponse> argServiceType) {
		HttpResponse<String> rawResponse = null;
		try {
			String secretToken = System.getProperty("asq.stc.auth.secrettoken");
			String authUsernamePassword = System.getProperty("asq.stc.auth.username") + ":" + System.getProperty("asq.stc.auth.password");
			String authToken = Base64.getEncoder().encodeToString(authUsernamePassword.getBytes());
			String transactionId = argServiceRequest.getTransactionId();
			argServiceRequest.setTransactionId(null);

			HttpClient httpClient = HttpClient.newHttpClient();

			java.net.http.HttpRequest.Builder builder = HttpRequest.newBuilder();

			builder.header("Content-Type", "application/json").header("Authorization", "Basic " + authToken).header("X-Secret-Token", secretToken).header("GlobalId", transactionId);

			builder.uri(URI.create(getEndpointAddress() + getServicePath()));

			builder.POST(HttpRequest.BodyPublishers.ofString(asqStcHelper.convertTojson(argServiceRequest)));

			HttpRequest httpRequest = builder.build();

			rawResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
			checkForExceptions(rawResponse);
			return asqStcHelper.convertJSONToPojo(rawResponse.body(), AsqSTCLoyaltyServiceResponse.class);
		} catch (Exception ex) {
			logger.error("WE have recieved a exception in STC we service call ", ex);
			if (200 != rawResponse.statusCode()) {
				AsqSTCLoyaltyServiceResponse arg = new AsqSTCLoyaltyServiceResponse();
				AsqSTCServiceResponseError errDesc = new AsqSTCServiceResponseError();
				errDesc.setCode(String.valueOf(rawResponse.statusCode()));
				errDesc.setDescription(rawResponse.toString());
				arg.setErrors(new AsqSTCServiceResponseError[] { errDesc });
				return arg;
			}
		}
		return null;
	}

	public IServiceResponse callWebBased(IAsqSTCLoyaltyServiceRequest argServiceRequest, ServiceType<IAsqSTCLoyaltyServiceRequest, IServiceResponse> argServiceType) {
		Response rawResponse = null;
		Builder requestBuilder = null;
		try {
			String secretToken = System.getProperty("asq.stc.auth.secrettoken");
			String authUsernamePassword = System.getProperty("asq.stc.auth.username") + ":" + System.getProperty("asq.stc.auth.password");
			String authToken = Base64.getEncoder().encodeToString(authUsernamePassword.getBytes());
			String transactionId = argServiceRequest.getTransactionId();
			argServiceRequest.setTransactionId(null);

			requestBuilder = getBaseWebTarget().request().header("Content-Type", "application/json").header("Accept-Language", "en").header("Accept-Version", "V2")
					.header("Authorization", "Basic " + authToken).header("X-Secret-Token", secretToken).header("GlobalId", transactionId);
			rawResponse = requestBuilder.post(Entity.json(asqStcHelper.convertTojson(argServiceRequest)));
			checkForExceptions(rawResponse);
			System.out.println("rawResponse" + rawResponse);
		} catch (Exception ex) {
			logger.error("WE have recieved a exception in STC we service call ", ex);
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
