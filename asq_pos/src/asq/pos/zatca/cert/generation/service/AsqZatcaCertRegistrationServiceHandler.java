package asq.pos.zatca.cert.generation.service;

import java.util.Base64;
import java.util.Properties;

import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.zatca.cert.generation.AsqZatcaConstant;
import asq.pos.zatca.cert.generation.AsqZatcaErrorDesc;
import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import dtv.service.req.IServiceResponse;
import dtv.servicex.ServiceType;
import dtv.servicex.impl.AbstractJaxRsHandler;

public class AsqZatcaCertRegistrationServiceHandler extends AbstractJaxRsHandler<AsqSubmitZatcaCertServiceRequest, IServiceResponse> {

	private static final Logger LOG = LogManager.getLogger(AsqZatcaCertRegistrationServiceHandler.class);

	@Inject
	AsqZatcaHelper asqZatcaHelper;

	@Override
	public IServiceResponse handleService(AsqSubmitZatcaCertServiceRequest argServiceRequest, ServiceType<AsqSubmitZatcaCertServiceRequest, IServiceResponse> argServiceType) {
		Response rawResponse = null;
		Builder requestBuilder = null;
		Properties csidProperties = AsqZatcaHelper.getCSIDProperties();
		try {
			if (AsqZatcaConstant.ZATCA_CERT_GEN_SRV.equalsIgnoreCase(argServiceType.getServiceHandlerId())) {
				requestBuilder = this.getBaseWebTarget().request().header("Content-Type", "application/json").header(AsqZatcaIntegrationConstants.ZATCA_OTP, argServiceRequest.getOtp())
						.header("Accept-Version", "V2");
				argServiceRequest.setOtp(null);
				rawResponse = requestBuilder.post(Entity.json(asqZatcaHelper.convertTojson(argServiceRequest)));
			} else if (AsqZatcaConstant.ZATCA_CERT_CSIDS_SRV.equalsIgnoreCase(argServiceType.getServiceHandlerId())) {
				String authStr = csidProperties.getProperty("binarySecurityToken") + ":" + csidProperties.getProperty("secret");
				String encodedString = Base64.getEncoder().encodeToString(authStr.getBytes());
				requestBuilder = this.getBaseWebTarget().request().header("Content-Type", "application/json")
						.header(AsqZatcaIntegrationConstants.Authorization, AsqZatcaIntegrationConstants.Basic + encodedString).header("Accept-Version", "V2");
				rawResponse = requestBuilder.post(Entity.json(asqZatcaHelper.convertTojson(argServiceRequest)));
			} else if (AsqZatcaConstant.ZATCA_CERT_GEN_INVOICE_SRV.equalsIgnoreCase(argServiceType.getServiceHandlerId())) {
				String authStr = csidProperties.getProperty("binarySecurityToken") + ":" + csidProperties.getProperty("secret");
				String encodedString = Base64.getEncoder().encodeToString(authStr.getBytes());
				requestBuilder = this.getBaseWebTarget().request().header("Content-Type", "application/json").header("Accept-Language", "en")
						.header(AsqZatcaIntegrationConstants.Authorization, AsqZatcaIntegrationConstants.Basic + encodedString).header("Accept-Version", "V2");
				rawResponse = requestBuilder.post(Entity.json(asqZatcaHelper.convertTojson(argServiceRequest)));
				System.out.println(argServiceRequest.getInvoice());
			}
			checkForExceptions(rawResponse);
			return asqZatcaHelper.convertJSONToPojo(rawResponse.readEntity(String.class), AsqSubmitZatcaCertServiceResponse.class);
		} catch (Exception ex) {
			LOG.error("We have reieved exception in calling of webservice ", rawResponse);
			if (null != rawResponse.getStatusInfo()) {
				AsqSubmitZatcaCertServiceResponse arg = new AsqSubmitZatcaCertServiceResponse();
				AsqZatcaErrorDesc errDesc = new AsqZatcaErrorDesc();
				errDesc.setCode(String.valueOf(rawResponse.getStatusInfo().getStatusCode()));
				errDesc.setMessage(rawResponse.getStatusInfo().getReasonPhrase());
				arg.setErrors(errDesc);
				return arg;
			}
		} finally {
			if (rawResponse != null) {
				rawResponse.close();
			}
		}
		return null;
	}
}
