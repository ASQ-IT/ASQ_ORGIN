package asq.pos.planet.vat.claim.service;

import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.oracle.shaded.fasterxml.jackson.core.JsonProcessingException;
import com.oracle.shaded.fasterxml.jackson.databind.JsonMappingException;

import asq.pos.zatca.cert.generation.AsqZatcaErrorDesc;
import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import asq.pos.zatca.cert.generation.AsqZatcaIntegrationConstants;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceResponse;
import dtv.service.req.IServiceResponse;
import dtv.servicex.RetryServiceType;
import dtv.servicex.ServiceType;
import dtv.servicex.impl.AbstractJaxRsHandler;

public class AsqPlanetVatClaimServiceHandler
		extends AbstractJaxRsHandler<AsqPlanetVatClaimServiceRequest, IServiceResponse> {

	@Inject
	AsqZatcaHelper asqZatcaHelper;

	@Override
	public IServiceResponse handleService(AsqPlanetVatClaimServiceRequest argServiceRequest,
			ServiceType<AsqPlanetVatClaimServiceRequest, IServiceResponse> argServiceType) {
		Response rawResponse = null;
		try {
			try {
				if (argServiceType.getServiceHandlerId().equalsIgnoreCase("PLANET_AUTH_TOKEN_SRV")) {
					Form form = new Form();

					form.param("client_id", System.getProperty("asq.pos.planet.auth.client.id"));
					form.param("client_secret", System.getProperty("asq.pos.planet.auth.client.secret"));
					form.param("grant_type", System.getProperty("asq.pos.planet.auth.grant.type"));

					Builder requestBuilder = this.getBaseWebTarget().request(MediaType.APPLICATION_JSON)
							.header("Content-Type", "application/json").header("Accept-Version", "V2");
					rawResponse = requestBuilder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
				} else if (argServiceType.getServiceHandlerId().equalsIgnoreCase("PLANET_VAT_CLAIM_REG_SRV")
						|| argServiceType.getServiceHandlerId().equalsIgnoreCase("PLANET_VAT_CLAIM_CANCEL_SRV")) {
					Builder requestBuilder = this.getBaseWebTarget().request(MediaType.APPLICATION_JSON)
							.header("Content-Type", "application/json")
							.header(AsqZatcaIntegrationConstants.Authorization,
									"Bearer " + argServiceRequest.getToken())
							.header("Accept-Version", "V2");
					argServiceRequest.setToken(null);
					rawResponse = requestBuilder.post(Entity.json(asqZatcaHelper.convertTojson(argServiceRequest)));

				}

				checkForExceptions(rawResponse);
			} catch (Exception ex) {
				RetryServiceType retryType = new RetryServiceType(argServiceType.getServiceHandlerId());
				String retryRequest = argServiceRequest.toString();
				queueForRetry(retryType, retryRequest);
			}
			return handleResponse(rawResponse);
		} finally {
			if (rawResponse != null)
				rawResponse.close();
		}
	}

	private IServiceResponse handleResponse(Response argRawResponse) {
		if (argRawResponse.getStatus() == 200) {
			try {
				return asqZatcaHelper.convertJSONToPojo(argRawResponse.readEntity(String.class),
						AsqPlanetVatClaimServiceResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
			else {

				try {  
					AsqPlanetVatClaimServiceResponse asqPlanetVatClaimServiceResponse = new AsqPlanetVatClaimServiceResponse();
					AsqPlanetVatClaimErrorDesc errorResponse = asqZatcaHelper.convertJSONToPojo(argRawResponse.readEntity(String.class),AsqPlanetVatClaimErrorDesc.class);
					asqPlanetVatClaimServiceResponse.setError(errorResponse);
					return asqPlanetVatClaimServiceResponse;
				}catch(Exception e) {
					e.printStackTrace();
					return null;
				}
			
			}
	}

	private String getPlanetAuthToken() throws JsonMappingException, JsonProcessingException {
		Response rawResponse = null;

		Form form = new Form();

		form.param("client_id", System.getProperty("asq.pos.planet.auth.client.id"));
		form.param("client_secret", System.getProperty("asq.pos.planet.auth.client.secret"));
		form.param("grant_type", System.getProperty("asq.pos.planet.auth.grant.type"));

		Builder requestBuilder = this.getBaseWebTarget().request(MediaType.APPLICATION_JSON)
				.header("Content-Type", "application/json").header("Accept-Version", "V2");
		rawResponse = requestBuilder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		AsqPlanetVatClaimServiceResponse asqPlanetVatClaimServiceResponse = asqZatcaHelper
				.convertJSONToPojo(rawResponse.readEntity(String.class), AsqPlanetVatClaimServiceResponse.class);
		return asqPlanetVatClaimServiceResponse.getAccess_token();
	}
}