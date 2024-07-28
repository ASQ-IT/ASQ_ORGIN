package asq.pos.zatca.cert.generation.op;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oracle.shaded.fasterxml.jackson.core.JsonProcessingException;
import com.oracle.shaded.fasterxml.jackson.databind.JsonMappingException;

import asq.pos.common.AsqValueKeys;
import asq.pos.zatca.cert.generation.AsqZatcaErrorDesc;
import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceRequest;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceResponse;
import asq.pos.zatca.cert.generation.service.IAsqSubmitZatcaCertServiceRequest;
import asq.pos.zatca.cert.generation.service.IAsqZatcaCertRegistrationServices;
import asq.pos.zatca.integration.zatca.util.POSUtil;
import dtv.i18n.IFormattable;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.iframework.action.IXstActionKey;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;

public class AsqZatcaCertRegistrationOp extends AbstractFormOp<AsqZatcaCertRegistrationEditModel> {

	private static final Logger LOG = LogManager.getLogger(AsqZatcaCertRegistrationOp.class);

	@Inject
	private AsqZatcaHelper asqZatcaHelper;

	@Inject
	protected Provider<IAsqZatcaCertRegistrationServices> zatcaService;

	@Override
	protected String getFormKey() {
		return "ASQ_CAPTURE_OTP";
	}

	@Override
	protected AsqZatcaCertRegistrationEditModel createModel() {
		return new AsqZatcaCertRegistrationEditModel();
	}

	@Override
	protected IOpResponse handleBeforeDataAction(IXstEvent argAction) {
		return super.handleBeforeDataAction(argAction);
	}

	@Override
	protected IOpResponse handleFormResponse(IXstEvent argEvent) {
		return HELPER.completeResponse();
	}

	@Override
	protected IOpResponse handleInitialState() {
		return super.handleInitialState();
	}

	@Override
	protected IOpResponse handleDataAction(IXstDataAction argAction) {
		AsqSubmitZatcaCertServiceResponse asqSubmitZatcaCertServiceResponse;
		try {
			IXstActionKey actionKey = argAction.getActionKey();
			if (actionKey == XstDataActionKey.ACCEPT) {
				LOG.debug("Process of registering the till to Zataca Starts");
				AsqZatcaCertRegistrationEditModel model = getModel();
				setScopedValue(AsqValueKeys.ZATCA_OTP, model.getCaptureOTP());
				if (asqZatcaHelper.getCertOnBoarding(model.getCaptureOTP())) {
					asqSubmitZatcaCertServiceResponse = generateCCSID(model.getCaptureOTP());// compliance
					if (asqSubmitZatcaCertServiceResponse.getErrors() == null) {
						asqZatcaHelper.mapRequiredValuesToPropertiesFile(asqSubmitZatcaCertServiceResponse);
						asqZatcaHelper.generateCSIDFile(asqSubmitZatcaCertServiceResponse.getBinarySecurityToken());// Certificate Created
						asqZatcaHelper.getJKSCert();// Making it as JKS
					} else {
						LOG.error("Response from Zatca have error or Null");
						return handleServiceError(asqSubmitZatcaCertServiceResponse);
					}
				}
			}
		} catch (Exception ex) {
			LOG.error("Recieve error in the generating zatca certificate", ex);
			return HELPER.getPromptResponse("ASQ_ZATCA_REGISTOR_ERROR");
		}
		return HELPER.completeResponse();
	}

	public IOpResponse handleServiceError(AsqSubmitZatcaCertServiceResponse asqServiceResponse) throws JsonMappingException, JsonProcessingException {
		IFormattable[] args = new IFormattable[2];
		if (null == asqServiceResponse) {
			args[1] = _formattables.getSimpleFormattable("Service has null response");
		} else {
			AsqZatcaErrorDesc error = asqServiceResponse.getErrors();
			args[0] = _formattables.getSimpleFormattable(error.getCode());
			args[1] = _formattables.getSimpleFormattable(error.getMessage());
		}
		return HELPER.getPromptResponse("ASQ_ZATCA_REGISTOR_ERROR", args);
	}

	public AsqSubmitZatcaCertServiceResponse generateCCSID(String otp) throws URISyntaxException, IOException {
		IAsqSubmitZatcaCertServiceRequest asqSubmitZatcaCertServiceRequest = new AsqSubmitZatcaCertServiceRequest();
		asqSubmitZatcaCertServiceRequest.setOtp(otp);
		asqSubmitZatcaCertServiceRequest.setCsr(POSUtil.encodeFileToBase64(asqZatcaHelper.getZatcaCISRFile()));
		return (AsqSubmitZatcaCertServiceResponse) zatcaService.get().submitCertForRegistration(asqSubmitZatcaCertServiceRequest);
	}

	/*
	 * public AsqSubmitZatcaCertServiceResponse generatePCSID() throws IOException {
	 * 
	 * Properties csidProperties = AsqZatcaHelper.getCSIDProperties();
	 * AsqSubmitZatcaCertServiceResponse response = new
	 * AsqSubmitZatcaCertServiceResponse(); if
	 * ("true".equalsIgnoreCase(csidProperties.getProperty("isComplianceCheck"))) {
	 * // ZATCA Req IAsqSubmitZatcaCertServiceRequest productionRequest = new
	 * AsqSubmitZatcaCertServiceRequest();
	 * productionRequest.setCompliance_request_id(csidProperties.getProperty(
	 * "complianceRequestID")); response = (AsqSubmitZatcaCertServiceResponse)
	 * zatcaService.get().submitCSIDSForRegistration(productionRequest); if
	 * (response != null) { try {
	 * asqZatcaHelper.mapRequiredValuesToPropertiesFile(response); try {
	 * asqZatcaHelper.generateCSIDFile(response.getBinarySecurityToken()); } catch
	 * (IOException e) { e.printStackTrace(); } } catch (URISyntaxException e) {
	 * e.printStackTrace(); } } } return response; }
	 */
}
