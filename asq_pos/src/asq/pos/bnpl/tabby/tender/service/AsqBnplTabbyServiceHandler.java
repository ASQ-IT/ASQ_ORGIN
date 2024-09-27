package asq.pos.bnpl.tabby.tender.service;

import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import asq.pos.zatca.cert.generation.AsqZatcaIntegrationConstants;
import dtv.service.req.IServiceResponse;
import dtv.servicex.RetryServiceType;
import dtv.servicex.ServiceType;
import dtv.servicex.impl.AbstractJaxRsHandler;

public class AsqBnplTabbyServiceHandler extends AbstractJaxRsHandler<AsqSubmitBnplTabbyServiceRequest, IServiceResponse>{

	@Inject
	AsqZatcaHelper asqZatcaHelper;

	@Override
	public IServiceResponse handleService(AsqSubmitBnplTabbyServiceRequest argServiceRequest,
			ServiceType<AsqSubmitBnplTabbyServiceRequest, IServiceResponse> argServiceType) {
		Response rawResponse = null;
		try {
			try {
				if(argServiceType.getServiceHandlerId().equalsIgnoreCase("BNPL_TABBY_CREATE_SESSION_SRV")) {
					Builder requestBuilder = this.getBaseWebTarget().request(MediaType.APPLICATION_JSON)
							.header("Content-Type", "application/json")
							.header(AsqZatcaIntegrationConstants.Authorization, System.getProperty("asq.bnpl.tender.tabby.public.key"))			
							.header("Accept-Version", "V2");
					rawResponse = requestBuilder.post(Entity.json(asqZatcaHelper.convertTojson(argServiceRequest)));
				
				}else if(argServiceType.getServiceHandlerId().equalsIgnoreCase("BNPL_TABBY_NOTIFICATION_SRV")) {
					Builder requestBuilder = this.getBaseWebTarget().resolveTemplate("session_id", argServiceRequest.getId())
							.request()						
							.header("Content-Type", "application/json")
							.header(AsqZatcaIntegrationConstants.Authorization, System.getProperty("asq.bnpl.tender.tabby.secret.key"))
							.header("Accept-Version", "V2");
					argServiceRequest.setId(null);
					rawResponse = requestBuilder.post(null);
				
				}else if(argServiceType.getServiceHandlerId().equalsIgnoreCase("BNPL_TABBY_RETRIEVE_PAYMENT_SRV")) {
					Builder requestBuilder = this.getBaseWebTarget().resolveTemplate("payment_id", argServiceRequest.getPayment().getId())
							.request()
							.header("Content-Type", "application/json")
							.header(AsqZatcaIntegrationConstants.Authorization, System.getProperty("asq.bnpl.tender.tabby.secret.key"))
							.header("Accept-Version", "V2");
					argServiceRequest.getPayment().setId(null);
					rawResponse = requestBuilder.get();
				}else if(argServiceType.getServiceHandlerId().equalsIgnoreCase("BNPL_TABBY_REFUND_PAYMENT_SRV")) {
					Builder requestBuilder = this.getBaseWebTarget().resolveTemplate("payment_id", argServiceRequest.getPayment().getId())
							.request()
							.header("Content-Type", "application/json")
							.header(AsqZatcaIntegrationConstants.Authorization, System.getProperty("asq.bnpl.tender.tabby.secret.key"))
							.header("Accept-Version", "V2");
					argServiceRequest.getPayment().setId(null);
					rawResponse = requestBuilder.post(Entity.json(asqZatcaHelper.convertTojson(argServiceRequest)));
					System.out.println(rawResponse.getMetadata()); 
				}else {
					Builder requestBuilder = this.getBaseWebTarget().resolveTemplate("session_id", argServiceRequest.getId())
							.request()
							.header("Content-Type", "application/json")
							.header(AsqZatcaIntegrationConstants.Authorization, System.getProperty("asq.bnpl.tender.tabby.secret.key"))
							.header("Accept-Version", "V2");
					argServiceRequest.setId(null);
					rawResponse = requestBuilder.post(Entity.json(asqZatcaHelper.convertTojson(argServiceRequest)));
				}
				
				checkForExceptions(rawResponse);
			} catch (Exception ex) {
				RetryServiceType retryType = new RetryServiceType(argServiceType.getServiceHandlerId());
				String retryRequest = argServiceRequest.toString();
				queueForRetry(retryType, retryRequest);
			}
			return handleResponse(rawResponse);
		}
		finally {
			if (rawResponse != null)
				rawResponse.close();
		}
	}
	
	private IServiceResponse  handleResponse(Response argRawResponse) {
		if(argRawResponse.getStatus()==200){  
		try {  
			return   (IServiceResponse) asqZatcaHelper.convertJSONToPojo(argRawResponse.readEntity(String.class),AsqSubmitBnplTabbyServiceResponse.class);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		}else {
			try {  
				AsqSubmitBnplTabbyServiceResponse asqSubmitBnplTabbyServiceResponse = new AsqSubmitBnplTabbyServiceResponse();
				AsqBnplTabbyErrorDesc errorResponse = asqZatcaHelper.convertJSONToPojo(argRawResponse.readEntity(String.class),AsqBnplTabbyErrorDesc.class);
				asqSubmitBnplTabbyServiceResponse.setError(errorResponse);
				return (IServiceResponse)asqSubmitBnplTabbyServiceResponse;
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}


}
