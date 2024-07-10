package asq.pos.zatca.cert.generation.op;

import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.iframework.action.IXstActionKey;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.action.IXstDataActionKey;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Provider;

import asq.pos.AsqValueKeys;
import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceRequest;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceResponse;
import asq.pos.zatca.cert.generation.service.IAsqSubmitZatcaCertServiceRequest;
import asq.pos.zatca.cert.generation.service.IAsqZatcaCertRegistrationServices;
import asq.pos.zatca.integration.zatca.util.POSUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
					asqSubmitZatcaCertServiceResponse = generateCCSID(otp);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return HELPER.completeResponse();
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}
	
}
