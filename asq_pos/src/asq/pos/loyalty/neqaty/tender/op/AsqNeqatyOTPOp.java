/**
 * 
 */
package asq.pos.loyalty.neqaty.tender.op;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyHelper;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyServiceRequest;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyServiceResponse;
import asq.pos.loyalty.neqaty.tender.service.IAsqNeqatyService;
import asq.pos.loyalty.neqaty.tender.service.IAsqNeqatyServiceRequest;
import asq.pos.loyalty.neqaty.tender.service.NeqatyMethod;
import dtv.i18n.IFormattable;
import dtv.pos.common.OpChainKey;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.ttr.ITenderLineItem;

/**
 * @author RA20221457
 *
 */
public class AsqNeqatyOTPOp extends AbstractFormOp<AsqNeqatyOTPEditModel> {

	private static final Logger LOG = LogManager.getLogger(AsqNeqatyOTPOp.class);

	private String custMobileNumber = "";

	@Inject
	protected Provider<IAsqNeqatyService> asqNeqatyService;

	@Inject
	AsqNeqatyHelper asqNeqatyHelper;

	/**
	 * This class extends the Xstore Standard form class to handle all actions
	 * related to Mobile number field
	 */

	@Override
	protected AsqNeqatyOTPEditModel createModel() {
		return new AsqNeqatyOTPEditModel();
	}

	@Override
	protected String getFormKey() {
		return "ASQ_NEQATY_OTP";
	}

	@Override
	protected IOpResponse handleDataAction(IXstDataAction argAction) {

		try {
			if (XstDataActionKey.ACCEPT.equals(argAction.getActionKey())) {
				AsqNeqatyOTPEditModel model = getModel();
//				ITenderLineItem tenderLine = getScopedValue(ValueKeys.CURRENT_TENDER_LINE);
//				String tender = tenderLine.getTenderId();
				if (model.getNeqatyOTP() == null) {
					return super.handleDataAction(argAction);
				}
				String otp = model.getNeqatyOTP();
				setScopedValue(AsqValueKeys.ASQ_STC_OTP, otp);
				String custMobileNmbr = getScopedValue(AsqValueKeys.ASQ_MOBILE_NUMBER);
				return submitOTP(otp, custMobileNmbr);

			}
		} catch (Exception exception) {
			LOG.error("Exception from STC_OTP form in Handling Data Action :" + exception);
			return technicalErrorScreen("Exception from STC_OTP form in Handling Data Action :" + exception);

		}
		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_TENDER_STC"));
	}

	private IOpResponse submitOTP(String otp, String custMobileNmbr) {
		IAsqNeqatyServiceRequest request = new AsqNeqatyServiceRequest();
		request.setAuthenticationKey(System.getProperty("asq.neqaty.auth.key"));
		request.setMsisdn(custMobileNumber);
//		take reference from previous auth method call
		if(null!=getScopedValue(AsqValueKeys.ASQ_NEQATY_TRANS_REFERENCE)) {
			request.setTransactionReference(getScopedValue(AsqValueKeys.ASQ_NEQATY_TRANS_REFERENCE));
		}
		request.setOtp(otp);
		request.setTid(0);
		request.setMethod(NeqatyMethod.CONFIRM);
		AsqNeqatyServiceResponse response= (AsqNeqatyServiceResponse) asqNeqatyService.get().callNeqatyService(request);
		return validateResponse(response);
	}

	private IOpResponse validateResponse(AsqNeqatyServiceResponse response) {
		if (null != response && 0 != response.getResultCode()) {
			return handleServiceError(response);
		} else if (null == response) {
			return technicalErrorScreen("Service has null response");
		}
		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_TENDER_NEQATY"));
	}

	public IOpResponse handleServiceError(AsqNeqatyServiceResponse asqServiceResponse) {
		IFormattable[] args = new IFormattable[2];
		args[0] = _formattables.getSimpleFormattable(String.valueOf(asqServiceResponse.getResultCode()));
		args[1] = _formattables.getSimpleFormattable(asqServiceResponse.getResultDescription());
		String errorConstant = asqNeqatyHelper.mapError(asqServiceResponse.getResultCode());
		LOG.info("Error From Neqaty Inquire OTP Operation : " + asqServiceResponse.getResultCode() + " - "
				+ asqServiceResponse.getResultDescription());
		LOG.info("Error Message Generated By Xstore based on Neqaty Inquire OTP Operation Response: " + errorConstant);
		return HELPER.getPromptResponse(errorConstant, args);
	}
	
	private IOpResponse technicalErrorScreen(String message) {
		IFormattable[] args = new IFormattable[2];
		args[1] = _formattables.getSimpleFormattable(message);
		LOG.info("Neqaty Inquire OTP Operation::::: "+message);
		return HELPER.getPromptResponse("ASQ_NEQATY_TECHNICAL_ERROR", args);
	}

}
