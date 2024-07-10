package asq.pos.zatca.cert.generation.op;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqValueKeys;
import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceRequest;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceResponse;
import asq.pos.zatca.cert.generation.service.IAsqSubmitZatcaCertServiceRequest;
import asq.pos.zatca.cert.generation.service.IAsqZatcaCertRegistrationServices;
import asq.pos.zatca.integration.zatca.util.POSUtil;
import dtv.pos.common.OpChainKey;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.iframework.action.IXstActionKey;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.action.IXstDataActionKey;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;

public class AsqZatcaCertRegistrationOp extends AbstractFormOp<AsqZatcaCertRegistrationEditModel> {
	
	private static final Logger LOG = LogManager.getLogger(AsqZatcaCertRegistrationOp.class);
	
	@Inject
	private AsqZatcaHelper asqZatcaHelper;
	
	@Inject
	protected Provider<IAsqZatcaCertRegistrationServices> zatcaService;

	public static final IXstDataActionKey ACCEPT = XstDataActionKey.valueOf("ACCEPT");

	protected String getFormKey() {
		return "CAPTURE_OTP";
	}

	protected IOpResponse handleBeforeDataAction(IXstEvent argAction) {
		return super.handleBeforeDataAction(argAction);
	}

	protected IOpResponse handleFormResponse(IXstEvent argEvent) {
		return this.HELPER.completeResponse();
	}

	protected IOpResponse handleInitialState() {
		return super.handleInitialState();
	}

	protected IOpResponse handleDataAction(IXstDataAction argAction) {
		AsqSubmitZatcaCertServiceResponse asqSubmitZatcaCertServiceResponse;
		try {
			IXstActionKey actionKey = argAction.getActionKey();
			if (actionKey == ACCEPT) {
				AsqZatcaCertRegistrationEditModel model = (AsqZatcaCertRegistrationEditModel) getModel();
				String otp = model.getCaptureOTP();
				setScopedValue(AsqValueKeys.ZATCA_OTP, otp);
				
				if(asqZatcaHelper.getCertOnBoarding(otp)) {
					asqSubmitZatcaCertServiceResponse = generateCCSID(otp);//compliance
					//files should be manually placed//	invoice should be located at one place		
					
					//generateinvoice//read sample invoice //.json/read first file and call generate invoice
					//after generating the compliance JKS certificate flow will return here.
					
				//	asqSubmitZatcaCertServiceResponse = generatePCSID();
				//	asqZatcaHelper.getProdCertOnBoarding();// Generates production CSID
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//return HELPER.completeResponse();
		 return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_ZATCA_INVOICE_GENERATION"));
	}

	@Override
	protected AsqZatcaCertRegistrationEditModel createModel() {
		return new AsqZatcaCertRegistrationEditModel();
	}

	public AsqSubmitZatcaCertServiceResponse generateCCSID(String otp) {
		AsqSubmitZatcaCertServiceResponse response = new AsqSubmitZatcaCertServiceResponse();
		try {
			IAsqSubmitZatcaCertServiceRequest asqSubmitZatcaCertServiceRequest = new AsqSubmitZatcaCertServiceRequest();
			asqSubmitZatcaCertServiceRequest.setOtp(otp);
			asqSubmitZatcaCertServiceRequest.setCsr(POSUtil.encodeFileToBase64(asqZatcaHelper.getZatcaCISRFile()));
			response = (AsqSubmitZatcaCertServiceResponse) zatcaService.get().submitCertForRegistration(asqSubmitZatcaCertServiceRequest);
			
			if(response!= null) {
				try {
					asqZatcaHelper.mapRequiredValuesToPropertiesFile(response);
					if(null!=response.getBinarySecurityToken()) {
						asqZatcaHelper.generateCSIDFile(response.getBinarySecurityToken());//Certificate Created
					    asqZatcaHelper.getJKSCert(otp);//Making it as JKS
					}
					else {
						System.out.println("csid.properties file does not contain Binary Security Token");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}
	
	public AsqSubmitZatcaCertServiceResponse generatePCSID() {

		Properties csidProperties = AsqZatcaHelper.getCSIDProperties();
		AsqSubmitZatcaCertServiceResponse response = new AsqSubmitZatcaCertServiceResponse();
		if ("true".equalsIgnoreCase(csidProperties.getProperty("isComplianceCheck"))) {
			// ZATCA Req
			IAsqSubmitZatcaCertServiceRequest productionRequest = new AsqSubmitZatcaCertServiceRequest();
			productionRequest.setCompliance_request_id(csidProperties.getProperty("complianceRequestID"));
			response = (AsqSubmitZatcaCertServiceResponse) zatcaService.get().submitCSIDSForRegistration(productionRequest);
			if(response!= null) {
				try {
					asqZatcaHelper.mapRequiredValuesToPropertiesFile(response);
					try {
						asqZatcaHelper.generateCSIDFile(response.getBinarySecurityToken());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}  catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}
}
