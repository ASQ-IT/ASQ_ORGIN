package asq.pos.zatca.prod.cert.registration.op;

import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceRequest;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceResponse;
import asq.pos.zatca.cert.generation.service.IAsqSubmitZatcaCertServiceRequest;
import asq.pos.zatca.cert.generation.service.IAsqZatcaCertRegistrationServices;
import dtv.i18n.IFormattable;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;

public class AsqZatcaProdCertRegistrationOp extends Operation {

	private static final Logger logger = LogManager.getLogger(AsqZatcaProdCertRegistrationOp.class);

	@Inject
	private AsqZatcaHelper asqZatcaHelper;

	@Inject
	protected Provider<IAsqZatcaCertRegistrationServices> zatcaService;

	@Override
	public boolean isOperationApplicable() {
		return !isComplete();
	}

	@Override
	public IOpResponse handleOpExec(IXstEvent arg0) {
		try {
			logger.debug("We have started the process of getting the production certificate ");
			AsqSubmitZatcaCertServiceResponse response = generateProductionCertPCSID();
			if (response != null) {
				asqZatcaHelper.mapRequiredValuesToPropertiesFile(response);
				asqZatcaHelper.generateCSIDFile(response.getBinarySecurityToken());
				return HELPER.getCompletePromptResponse("ASQ_ZATCA_PROD_CERT_NOTIFIY");
			}
		} catch (Exception exception) {
			logger.error("We have recieved exception in getting the prod certificate :" + exception);
			IFormattable[] args = new IFormattable[2];
			args[0] = _formattables.getSimpleFormattable("System Error");
			args[1] = _formattables.getSimpleFormattable(exception.getLocalizedMessage());
			return HELPER.getPromptResponse("ASQ_ZATCA_REGISTOR_ERROR", args);
		}
		return HELPER.completeResponse();
	}

	public AsqSubmitZatcaCertServiceResponse generateProductionCertPCSID() throws IOException {
		Properties csidProperties = asqZatcaHelper.getCSIDProperties();
		if ("true".equalsIgnoreCase(csidProperties.getProperty("isComplianceCheck"))) {
			IAsqSubmitZatcaCertServiceRequest productionRequest = new AsqSubmitZatcaCertServiceRequest();
			productionRequest.setCompliance_request_id(csidProperties.getProperty("complianceRequestID"));
			return (AsqSubmitZatcaCertServiceResponse) zatcaService.get().submitCSIDSForRegistration(productionRequest);
		}
		return null;
	}

}
