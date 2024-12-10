/**
 * 
 */
package asq.pos.inventory;

import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import asq.pos.common.AsqConfigurationMgr;
import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.neqaty.tender.op.AsqNeqatyOTPEditModel;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.framework.op.OpState;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.op.IOpState;
import dtv.pos.iframework.validation.IValidationResultList;
import dtv.xst.dao.inv.IInventoryDocument;
import dtv.xst.dao.inv.IInventoryDocumentLineItem;
import dtv.xst.dao.inv.IInventoryLocation;
import dtv.xst.dao.inv.IInventoryLocationBucket;
import dtv.xst.dao.trn.IPosTransaction;

/**
 * @author RA20221457
 *
 */
public class AsqBinTransferOTPFormOp extends AbstractFormOp<AsqNeqatyOTPEditModel> {

	private static final Logger LOG = LogManager.getLogger(AsqBinTransferOTPFormOp.class);

	protected final IOpState SHOWING_ERROR_PROMPT = new OpState("SHOWING_ERROR_PROMPT");

	@Inject
	private AsqConfigurationMgr _sysConfig;

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
		AsqNeqatyOTPEditModel model = getModel();
		IValidationResultList results = validateForm(model);
		try {
			if (!results.isValid()) {
				return super.handleDataAction(argAction);
			}
			int enteredOTP = Integer.parseInt(model.getNeqatyOTP());
			boolean emailReturnOtp = getScopedValue(AsqValueKeys.ASQ_RETURN_OFFLINE);
			if (Boolean.TRUE.equals(emailReturnOtp) && enteredOTP == _sysConfig.getBinTransferOfflineEmailOTP()) {
				return HELPER.completeResponse();
			}
			int actualOTP = getScopedValue(AsqValueKeys.ASQ_BIN_TRANSFER_OTP);
			if (enteredOTP == actualOTP) {
				return HELPER.getPromptResponse("ASQ_BIN_TRANSFER_SUCCESS");
				//return HELPER.completeResponse();
			} else {
				setOpState(SHOWING_ERROR_PROMPT);
				return HELPER.getPromptResponse("ASQ_BIN_TRANSFER_OTP_ERROR");
			}

		} catch (Exception exception) {
			LOG.error("Exception from Bin Transfer OTP form in Handling Data Action :" + exception);
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
