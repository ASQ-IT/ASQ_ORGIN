/**
 * 
 */
package asq.pos.loyalty.mokafaa.tender.op;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.mokafaa.tender.service.AsqMokafaaHelper;
import asq.pos.loyalty.mokafaa.tender.service.AsqMokafaaLoyaltyServiceRequest;
import asq.pos.loyalty.mokafaa.tender.service.AsqMokafaaLoyaltyServiceResponse;
import asq.pos.loyalty.mokafaa.tender.service.IAsqMokafaaLoyaltyServiceRequest;
import asq.pos.loyalty.mokafaa.tender.service.IAsqMokafaaLoyaltyTenderService;
import asq.pos.zatca.AsqZatcaConstant;
import dtv.i18n.IFormattable;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.OpState;
import dtv.pos.framework.op.Operation;
import dtv.pos.framework.validation.ValidationResultList;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.op.IOpState;
import dtv.pos.iframework.validation.IValidationResult;
import dtv.pos.iframework.validation.IValidationResultList;
import dtv.pos.iframework.validation.SimpleValidationResult;
import dtv.service.ServiceException;
import dtv.util.StringUtils;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.ttr.ITenderLineItem;

/**
 * @author RA20221457
 *
 */

public class AsqMokafaaRefundOp extends Operation {

	private static final Logger LOG = LogManager.getLogger(AsqMokafaaRefundOp.class);
	private String tenderType;
	  protected final IOpState SHOWING_ERROR_PROMPT = new OpState("SHOWING_ERROR_PROMPT");

	/**
	 * This class extends the Xstore Standard form class to handle all actions
	 * related to Mobile number field & Mokafaa API calls
	 */

	@Inject
	protected Provider<IAsqMokafaaLoyaltyTenderService> _asqMokafaaLoyalityTenderService;

	@Inject
	AsqMokafaaHelper asqMokafaaHelper;

	/**
	 * This method checks whether customer is linked to transaction or not
	 * 
	 * @param
	 * @return
	 */

	/**
	 * This method handles the data operation after submitting the mobile number
	 * 
	 * @param
	 * @return
	 */

	@Override
	public IOpResponse handleOpExec(IXstEvent argAction) {
		LOG.info("Mokafaa API Trigger OTP service call starts here: ");
		try {
			if(argAction!=null && argAction.getName().equalsIgnoreCase("CONTINUE")) {
				return HELPER.completeResponse();
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
			
		return refundRedeem();
	}


	/**
	 * This method implements the Trigger OTP service API call by preparing the
	 * request attributes
	 * 
	 * @param trans
	 * @return triggerOTPRequest submission to STC Service Handler
	 */

	private IOpResponse refundRedeem() {

		IAsqMokafaaLoyaltyServiceRequest request = new AsqMokafaaLoyaltyServiceRequest();
		request.setServiceRequest(null);
		request.setTransactionID(_transactionScope.getValue(AsqValueKeys.ASQ_MOKAFAA_REDEEM_TRANSACTION_ID));
		request.setAuthToken(_transactionScope.getValue(AsqValueKeys.ASQ_MOKAFAA_AUTH_TOKEN));

		LOG.info("Mokafaa API refundRedeem OTP request is prepared :" + request);
		AsqMokafaaLoyaltyServiceResponse response = (AsqMokafaaLoyaltyServiceResponse) _asqMokafaaLoyalityTenderService
				.get().refundRedeem(request);
		LOG.info("Mokafaa API refundRedeem OTP returns service response here: ");
//
		return validateResponse(response);
	}

	/**
	 * This method handles the OTP API response
	 * 
	 * @param request
	 * @param requestDate
	 * 
	 * @param asqServiceResponse
	 * @return Error Prompts
	 */

	private IOpResponse validateResponse(AsqMokafaaLoyaltyServiceResponse response) {
		if (null != response) {
			if (!(("200").equals(response.getStatus())) && null != response.getErrorCode()) {
				return handleServiceError(response);
			} else if (null != response.getHttpCode()) {
				return unauthorizedError(response);
			}
			return HELPER.getPromptResponse("ASQ_MOKAFAA_REDEEM_REFUND_SUCCESS");
		} else {
			return technicalErrorScreen("Service has null response");
		}
	}

	private IOpResponse unauthorizedError(AsqMokafaaLoyaltyServiceResponse response) {
		IFormattable[] args = new IFormattable[2];
		args[0] = _formattables.getSimpleFormattable(response.getHttpCode());
		args[1] = _formattables.getSimpleFormattable(response.getError_description());
		return HELPER.getPromptResponse("GENERAL_API_ERROR", args);
	}

	/**
	 * This method handles the Trigger OTP API call service errors
	 * 
	 * @param asqServiceResponse
	 * @return Error Prompts
	 */

	public IOpResponse handleServiceError(AsqMokafaaLoyaltyServiceResponse asqServiceResponse) {
//		if(("210").equals(asqServiceResponse.getErrorCode())) {
//			return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_MOKAFAA_OTP"));
//		}
		IFormattable[] args = new IFormattable[2];
		args[0] = _formattables.getSimpleFormattable(asqServiceResponse.getErrorCode());
		args[1] = _formattables.getSimpleFormattable(asqServiceResponse.getMessage());
		String errorConstant = asqMokafaaHelper.mapError(asqServiceResponse.getErrorCode());
		LOG.info("Error From Mokafaa OTP API::::: " + asqServiceResponse.getErrorCode() + " - "
				+ asqServiceResponse.getMessage());
		LOG.info("Error Message Generated By Xstore based on Mokafaa API Response::::: " + errorConstant);
		return HELPER.getPromptResponse(errorConstant, args);
	}

	/**
	 * This method return technical error screen
	 * 
	 * @param asqServiceResponse
	 * @return Error Prompts
	 */

	private IOpResponse technicalErrorScreen(String message) {
		IFormattable[] args = new IFormattable[2];
		args[1] = _formattables.getSimpleFormattable(message);
		LOG.info("Mokafaa API::::: " + message);
		return HELPER.getPromptResponse("GENERAL_API_ERROR", args);
	}

	@Override
	public void setParameter(String argName, String argValue) {
		if ("TenderType".equalsIgnoreCase(argName)) {
			tenderType = argValue;
		}
		super.setParameter(argName, argValue);
	}

	@Override
	public boolean isOperationApplicable() {

		ITenderLineItem tenderLine = getScopedValue(ValueKeys.CURRENT_TENDER_LINE);
		return (!tenderLine.getVoid() && tenderLine.getTenderId().equalsIgnoreCase(tenderType));
	}

}
