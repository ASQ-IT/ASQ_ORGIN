package asq.pos.bnpl.tamara.tender.service;

import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oracle.shaded.fasterxml.jackson.core.JsonProcessingException;
import com.oracle.shaded.fasterxml.jackson.databind.JsonMappingException;

import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyErrorDesc;
import asq.pos.bnpl.tabby.tender.service.AsqSubmitBnplTabbyServiceResponse;
import asq.pos.bnpl.tamara.tender.op.AsqBnplTamaraTenderOp;
import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import asq.pos.zatca.cert.generation.AsqZatcaIntegrationConstants;
import dtv.service.req.IServiceResponse;
import dtv.servicex.RetryServiceType;
import dtv.servicex.ServiceType;
import dtv.servicex.impl.AbstractJaxRsHandler;

public class AsqBnplTamaraServiceHandler extends AbstractJaxRsHandler<AsqSubmitBnplTamraServiceRequest, IServiceResponse> {
	
	private static final Logger LOG = LogManager.getLogger(AsqBnplTamaraServiceHandler.class);

	@Inject
	AsqZatcaHelper asqZatcaHelper;

	@Override
	public IServiceResponse handleService(AsqSubmitBnplTamraServiceRequest argServiceRequest, ServiceType<AsqSubmitBnplTamraServiceRequest, IServiceResponse> argServiceType) {
		Response rawResponse = null;
		try {
			try {
				if (argServiceType.getServiceHandlerId().equalsIgnoreCase("BNPL_TAMARA_CREATE_SESSION_SRV")) {
					Builder requestBuilder = this.getBaseWebTarget().request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
							.header(AsqZatcaIntegrationConstants.Authorization, System.getProperty("asq.bnpl.tender.tamara.token")).header("Accept-Version", "V2");
					rawResponse = requestBuilder.post(Entity.json(asqZatcaHelper.convertTojson(argServiceRequest)));
				} else if (argServiceType.getServiceHandlerId().equalsIgnoreCase("BNPL_TAMARA_ORDER_DETAIL_SRV")) {
					Builder requestBuilder = this.getBaseWebTarget().resolveTemplate("order_id", argServiceRequest.getOrder_id()).request().header("Content-Type", "application/json")
							.header(AsqZatcaIntegrationConstants.Authorization, System.getProperty("asq.bnpl.tender.tamara.token")).header("Accept-Version", "V2");
					rawResponse = requestBuilder.get();
				} else if (argServiceType.getServiceHandlerId().equalsIgnoreCase("BNPL_TAMARA_REFUND_SRV")) {
					Builder requestBuilder = this.getBaseWebTarget().resolveTemplate("orderId", argServiceRequest.getOrder_id()).request().header("Content-Type", "application/json")
							.header(AsqZatcaIntegrationConstants.Authorization, System.getProperty("asq.bnpl.tender.tamara.token")).header("Accept-Version", "V2");
					argServiceRequest.setOrder_id(null);
					rawResponse = requestBuilder.post(Entity.json(asqZatcaHelper.convertTojson(argServiceRequest)));
				} else {
					Builder requestBuilder = this.getBaseWebTarget().resolveTemplate("checkoutId", argServiceRequest.getCheckout_id()).request().header("Content-Type", "application/json")
							.header(AsqZatcaIntegrationConstants.Authorization, System.getProperty("asq.bnpl.tender.tamara.token")).header("Accept-Version", "V2");
					argServiceRequest.setCheckout_id(null);
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
				return (IServiceResponse) asqZatcaHelper.convertJSONToPojo(argRawResponse.readEntity(String.class),
						AsqSubmitBnplTabbyServiceResponse.class);
			} catch (Exception e) {
				LOG.error("Tamara Service Exception occured");
				return null;
			}
		} else {
			try {
				AsqSubmitBnplTamraServiceResponse asqSubmitBnplTamaraServiceResponse = new AsqSubmitBnplTamraServiceResponse();
				AsqTamaraErrorDesc errorResponse = asqZatcaHelper.convertJSONToPojo(argRawResponse.readEntity(String.class), AsqTamaraErrorDesc.class);
				asqSubmitBnplTamaraServiceResponse.setErrors(errorResponse);
				int errorCode = argRawResponse.getStatus();
				String errorStatus = Integer.toString(errorCode);
				asqSubmitBnplTamaraServiceResponse.setStatus(errorStatus);
				return (IServiceResponse) asqSubmitBnplTamaraServiceResponse;
			} catch (Exception e) {
				LOG.error("Tamara Service Exception occured during convertJSONToPojo");
				return null;
			}
		}
	}

}
