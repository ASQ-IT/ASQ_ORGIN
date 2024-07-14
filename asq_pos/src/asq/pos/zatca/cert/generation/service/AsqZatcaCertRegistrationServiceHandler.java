package asq.pos.zatca.cert.generation.service;

import java.util.Base64;
import java.util.Properties;

import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import dtv.service.req.IServiceResponse;
import dtv.servicex.RetryServiceType;
import dtv.servicex.ServiceType;
import dtv.servicex.impl.AbstractJaxRsHandler;

public class AsqZatcaCertRegistrationServiceHandler extends AbstractJaxRsHandler<AsqSubmitZatcaCertServiceRequest, IServiceResponse> {
	
	@Inject
	AsqZatcaHelper asqZatcaHelper;

	@Override
	public IServiceResponse handleService(AsqSubmitZatcaCertServiceRequest argServiceRequest,
			ServiceType<AsqSubmitZatcaCertServiceRequest, IServiceResponse> argServiceType) {
		Response rawResponse = null;
		Builder requestBuilder = null;
		Properties csidProperties = AsqZatcaHelper.getCSIDProperties();
		try {
			try {
				if(argServiceRequest.getOtp()!=null) {
					String otp = argServiceRequest.getOtp();
					 requestBuilder = this.getBaseWebTarget().request().header("Content-Type", "application/json")
						        .header(AsqZatcaIntegrationConstants.ZATCA_OTP,otp)
						        .header("Accept-Version", "V2");
					argServiceRequest.setOtp(null);
					rawResponse = requestBuilder.post(Entity.json(asqZatcaHelper.convertTojson(argServiceRequest)));
					checkForExceptions(rawResponse);
				}
				else if(argServiceRequest.getCompliance_request_id()!=null) {
					String authStr = csidProperties.getProperty("binarySecurityToken") + ":" + csidProperties.getProperty("secret");
					String encodedString = Base64.getEncoder().encodeToString(authStr.getBytes());
					requestBuilder = this.getBaseWebTarget().request().header("Content-Type", "application/json")
							.header(AsqZatcaIntegrationConstants.Authorization, AsqZatcaIntegrationConstants.Basic+encodedString)
			                .header("Accept-Version", "V2");
					rawResponse = requestBuilder.post(Entity.json(asqZatcaHelper.convertTojson(argServiceRequest)));
					checkForExceptions(rawResponse);
				}
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
	
	private String encodeToString(byte[] bytes) {
		return null;
	}

	private IServiceResponse  handleResponse(Response argRawResponse) {
		if(argRawResponse.getStatus()==200){    
			return   (IServiceResponse) asqZatcaHelper.convertJSONToPojo(argRawResponse.readEntity(String.class),AsqSubmitZatcaCertServiceResponse.class);
		}
		return null;
	}
}

