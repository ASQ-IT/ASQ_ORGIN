/**
 * 
 */
package asq.pos.inventory.ship.op;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqConfigurationMgr;
import asq.pos.common.AsqValueKeys;
import asq.pos.inventory.AsqBinTransferOTPFormOp;
import asq.pos.loyalty.neqaty.tender.op.AsqNeqatyOTPEditModel;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.framework.op.OpState;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.op.IOpState;
import dtv.pos.iframework.validation.IValidationResultList;

/**
 * @author SA20547171
 *
 */
public class AsqStoreTransferOTPFormOp extends AbstractFormOp<AsqNeqatyOTPEditModel> {

	private static final Logger LOG = LogManager.getLogger(AsqStoreTransferOTPFormOp.class);

	protected final IOpState SHOWING_ERROR_PROMPT = new OpState("SHOWING_ERROR_PROMPT");

	@Inject
	private AsqConfigurationMgr _sysConfig;

	@Override
	protected AsqNeqatyOTPEditModel createModel() {
		return new AsqNeqatyOTPEditModel("_asqCaptureShipOTPTitle", "_asqCaptureShipOTPDescription");
	}

	@Override
	protected String getFormKey() {
		return "ASQ_RETURN_OTP";
	}

	@Override
	protected IOpResponse handleDataAction(IXstDataAction argAction) {
		AsqNeqatyOTPEditModel model = getModel();
		IValidationResultList results = validateForm(model);
		try {
			if (XstDataActionKey.ACCEPT.equals(argAction.getActionKey())) {

				if (!results.isValid()) {
					return super.handleDataAction(argAction);
				}
			
				int enteredOTP = Integer.parseInt(model.getNeqatyOTP());
				
				boolean emailReturnOtp = getScopedValue(AsqValueKeys.ASQ_RETURN_OFFLINE);
				
				if(Boolean.TRUE.equals(emailReturnOtp) &&  enteredOTP == _sysConfig.getReturnOfflineEmailOTP() ) {
					return HELPER.completeResponse();
				}


				int actualOTP = getScopedValue(asq.pos.common.AsqValueKeys.ASQ_STORE_TRANSFER_OTP);

				if (enteredOTP == actualOTP ) {
					return HELPER.completeResponse();
				} else {
					setOpState(SHOWING_ERROR_PROMPT);
					return HELPER.getPromptResponse("ASQ_STORE_OTP_ERROR");
				}
			}
		} catch (Exception exception) {
			LOG.error("Exception from Store Transfer OTP form in Handling Data Action :" + exception);
		}
		return super.handleDataAction(argAction);
	}

	@Override
	protected IOpResponse handleAfterDataAction(IXstEvent argEvent) {
		if (getOpState() == SHOWING_ERROR_PROMPT) {
			setOpState(null);
		}
		return super.handleAfterDataAction(argEvent);
	}

}
