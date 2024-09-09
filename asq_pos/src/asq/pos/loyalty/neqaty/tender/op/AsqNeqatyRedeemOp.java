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
import dtv.xst.dao.trn.IPosTransaction;
import dtv.xst.dao.ttr.ITenderLineItem;

/**
 * @author RA20221457
 *
 */
public class AsqNeqatyRedeemOp extends AbstractFormOp<AsqNeqatyRedeemEditModel> {

	private static final Logger LOG = LogManager.getLogger(AsqNeqatyRedeemOp.class);

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
	protected AsqNeqatyRedeemEditModel createModel() {
		return new AsqNeqatyRedeemEditModel();
	}

	@Override
	protected String getFormKey() {
		return "ASQ_NEQATY_REDEEM_OPTION";
	}

	@Override
	protected IOpResponse handleDataAction(IXstDataAction argAction) {
		
		try {
			if (XstDataActionKey.ACCEPT.equals(argAction.getActionKey())) {
				AsqNeqatyRedeemEditModel model = getModel();
				if ((model.getNeqatyRedeemPoints() != null)
						&& (!model.getNeqatyRedeemPoints().equals(""))) {
					LOG.debug("Process of Neqaty Redemption points Tender starts here :");
					IPosTransaction trans = (IPosTransaction) this._transactionScope.getTransaction();
				}
				String custMobileNmbr = getScopedValue(AsqValueKeys.ASQ_MOBILE_NUMBER);
				return redeemPoints(custMobileNmbr, model.getNeqatyRedeemPoints());

			}
		} catch (Exception exception) {
			LOG.error("Exception from STC_OTP form in Handling Data Action :" + exception);
			return technicalErrorScreen("Exception from STC_OTP form in Handling Data Action :" + exception);

		}
		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_NEQATY_OTP"));
	}

	private IOpResponse redeemPoints(String custMobileNmbr, String neqatyRedeemPoints) {
		IAsqNeqatyServiceRequest request = new AsqNeqatyServiceRequest();
		request.setAuthenticationKey(System.getProperty("asq.neqaty.auth.key"));
		request.setOperationType("Redeem-OTP");
		request.setMsisdn(custMobileNumber);
		
		///// 3 value need to be passed to Neqaty to redeem
		request.setAmount(0);//transaction amount
		request.setRedeemCode(0);//customer selected redeem option code from inquire response
		request.setRedeemPoints(0);//redeem points from the selected option
		
		request.setTid(0);
		request.setMethod(NeqatyMethod.AUTHORIZE);
//		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_NEQATY_OTP"));
		AsqNeqatyServiceResponse response= (AsqNeqatyServiceResponse) asqNeqatyService.get().redeemNeqityPoint(request);
		return validateResponse(response);
	}

	private IOpResponse validateResponse(AsqNeqatyServiceResponse response) {
		if (null != response && 0 != response.getResultCode()) {
			return handleServiceError(response);
		} else if (null == response) {
			return technicalErrorScreen("Service has null response");
		}
		setScopedValue(AsqValueKeys.ASQ_NEQATY_TRANS_REFERENCE, response.getTransactionReference());
		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_NEQATY_OTP"));
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
