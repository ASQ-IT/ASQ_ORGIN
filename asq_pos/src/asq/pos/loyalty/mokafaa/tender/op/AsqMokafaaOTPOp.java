package asq.pos.loyalty.mokafaa.tender.op;

import java.math.BigDecimal;

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
import dtv.i18n.IFormattable;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.framework.op.OpState;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.op.IOpState;
import dtv.pos.iframework.validation.IValidationResultList;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trn.IPosTransaction;
import asq.pos.zatca.AsqZatcaConstant;

public class AsqMokafaaOTPOp extends AbstractFormOp<AsqMokafaaTenderOTPEditModel> {

	private static final Logger LOG = LogManager.getLogger(AsqMokafaaOTPOp.class);
	  protected final IOpState SHOWING_ERROR_PROMPT = new OpState("SHOWING_ERROR_PROMPT");
	/**
	 * This class extends the Xstore Standard form class to handle all actions
	 * related to OTP field
	 */

	@Inject
	protected Provider<IAsqMokafaaLoyaltyTenderService> _asqMokafaaLoyalityTenderService;

	@Inject
	AsqMokafaaHelper asqMokafaaHelper;

	@Override
	protected AsqMokafaaTenderOTPEditModel createModel() {
		return new AsqMokafaaTenderOTPEditModel();
	}

	@Override
	protected String getFormKey() {
		return "ASQ_MOKAFAA_OTP";
	}

	/**
	 * This method handles the DataActions after submitting OTP request
	 * 
	 * @throws Database persistent Exception
	 * @param IRetailTransaction originalPosTrx, String globalID, Date requestDate
	 * @return generated globalID
	 */

	@Override
	protected IOpResponse handleDataAction(IXstDataAction argAction) {

		String amount = null;
		try {
			if (XstDataActionKey.ACCEPT.equals(argAction.getActionKey())) {
				AsqMokafaaTenderOTPEditModel model = getModel();
				if (getOpState() == SHOWING_ERROR_PROMPT) {

					model.revertChanges();

					setOpState(null);
					return null;
				}
				IValidationResultList results = validateForm(model);
				if (!results.isValid()) {
					return super.handleDataAction(argAction);
				}
				
//				ITenderLineItem tenderLine = getScopedValue(ValueKeys.CURRENT_TENDER_LINE);
//				String tender = tenderLine.getTenderId();
				if ((model.getMokafaaOTP() != null) && (model.getMokafaaRedeemPoints() != null)
						&& (!model.getMokafaaRedeemPoints().equals(""))) {
					LOG.debug("Process of STC Redemption points Tender starts here :" + model.getMokafaaOTP());
					IPosTransaction trans = _transactionScope.getTransaction();
					BigDecimal redemptionValueBigDec = new BigDecimal(model.getMokafaaRedeemPoints());
					if (trans.getTotal().compareTo(BigDecimal.ZERO) == 0) {
						return errorResponse("_asqSTCEnteredZeroPointValueMessage");
						
					} else if (trans.getTotal().compareTo(redemptionValueBigDec) > 0) {
						LOG.info("Calculated Redemption amount has been set here to transaction :"
								+ trans.getTotal().compareTo(redemptionValueBigDec));
						//trans.setTotal(trans.getTotal().subtract(redemptionValueBigDec));
					} else if (trans.getTotal().compareTo(redemptionValueBigDec) < 0) {
						return errorResponse("_asqSTCEnteredPointsValueMessage");
						
					} else if (redemptionValueBigDec.compareTo(BigDecimal.ZERO) == 0) {
						return errorResponse("_asqSTCEnteredZeroPointValueMessage");

					}
					
					amount = redemptionValueBigDec.toString();
					setScopedValue(AsqValueKeys.ASQ_MOKAFAA_AMOUNT, redemptionValueBigDec);
				}
				String otp = model.getMokafaaOTP();
				setScopedValue(AsqValueKeys.ASQ_STC_OTP, otp);
				return redeemPoints(otp, amount);

			}
		} catch (Exception exception) {
			LOG.error("Exception from STC_OTP form in Handling Data Action :" + exception);
			return technicalErrorScreen("Exception from STC_OTP form in Handling Data Action :" + exception);

		}
		return HELPER.completeResponse();
	}

	private IOpResponse errorResponse(String string) {
		setOpState(SHOWING_ERROR_PROMPT);
		IFormattable[] args = new IFormattable[1];
		args[0] = _formattables.getSimpleFormattable(string);
		LOG.info("Mokafaa API::::: " + string);
		return HELPER.getPromptResponse("GENERAL_REEDEM_ERROR", args);
		
	}

	/**
	 * This method handles form validation of mobile number field
	 * 
	 * @param argModel
	 * @return validationResultList
	 */

	/*
	 * @Override protected IValidationResultList
	 * validateForm(AsqMokafaaTenderOTPEditModel argModel) { ValidationResultList
	 * validationResultList = new ValidationResultList(); if
	 * (StringUtils.isEmpty(argModel.getMokafaaOTP()) && argModel.getMokafaaOTP() ==
	 * null) { IValidationResult idResult =
	 * SimpleValidationResult.getFailed("_asqOTPFieldExceptionMessage");
	 * validationResultList.add(idResult); } return validationResultList; }
	 */

	private IOpResponse redeemPoints(String otp, String amount) {

		IAsqMokafaaLoyaltyServiceRequest request = new AsqMokafaaLoyaltyServiceRequest();
		request.setServiceRequest(null);
		request.setOTPValue(otp);
		request.setAmount(amount);
		request.setOTPToken(_transactionScope.getValue(AsqValueKeys.ASQ_MOKAFAA_OTP_TOKEN));
		request.setLanguage(System.getProperty("asq.mokafaa.redeem..language"));
		request.setAuthToken(_transactionScope.getValue(AsqValueKeys.ASQ_MOKAFAA_AUTH_TOKEN));
		
		LOG.info("Mokafaa API trigger OTP request is prepared :" + request);
		AsqMokafaaLoyaltyServiceResponse response = (AsqMokafaaLoyaltyServiceResponse) _asqMokafaaLoyalityTenderService.get()
				.submitOTPRequest(request);
		LOG.info("Mokafaa API Trigger OTP returns service response here: ");
//
		return validateResponse(response);
	}

	private IOpResponse validateResponse(AsqMokafaaLoyaltyServiceResponse response) {
		if (null != response) {
			if (!(("200").equals(response.getStatus())) && null != response.getErrorCode()) {
				return handleServiceError(response);
			} else if (null != response.getHttpCode()) {
				return unauthorizedError(response);
			}
//			LOG.info("Mokafaa API saving response to DB started");
//			asqStcHelper.saveSTCResponseToDB(trans, globalID, requestDate, earnPoints);
//			LOG.info("Mokafaa API saving response to DB successfull");
			if (null != response.getTransactionID()) {
				_transactionScope.setValue(AsqValueKeys.ASQ_MOKAFAA_REDEEM_TRANSACTION_ID, response.getTransactionID());
			}
			IRetailTransaction trans = (IRetailTransaction) _transactionScope.getTransaction();
			saveMokafaResponseToDB(trans, _transactionScope.getValue(AsqValueKeys.ASQ_MOKAFAA_REDEEM_TRANSACTION_ID));
			//trans.setTotal(trans.getTotal().subtract(getScopedValue(AsqValueKeys.ASQ_MOKAFAA_AMOUNT)));
			return HELPER.getPromptResponse("ASQ_MOKAFAA_REDEEM_SUCCESS");
		} else {
			return technicalErrorScreen("Service has null response");
		}
	}
	
	private void saveMokafaResponseToDB(IRetailTransaction trans, long value) {
		trans.setDecimalProperty(AsqZatcaConstant.ASQ_MOKAFA_TRX_ID, new BigDecimal(value));
	}
	

	private IOpResponse unauthorizedError(AsqMokafaaLoyaltyServiceResponse response) {
		IFormattable[] args = new IFormattable[2];
		args[0] = _formattables.getSimpleFormattable(response.getHttpCode());
		args[1] = _formattables.getSimpleFormattable(response.getError_description());
		setOpState(SHOWING_ERROR_PROMPT);
		return HELPER.getPromptResponse("GENERAL_API_ERROR", args);
	}

	/**
	 * This method handles the Trigger OTP API call service errors
	 * 
	 * @param asqServiceResponse
	 * @return Error Prompts
	 */

	public IOpResponse handleServiceError(AsqMokafaaLoyaltyServiceResponse asqServiceResponse) {
		IFormattable[] args = new IFormattable[2];
		args[0] = _formattables.getSimpleFormattable(asqServiceResponse.getErrorCode());
		args[1] = _formattables.getSimpleFormattable(asqServiceResponse.getMessage());
		String errorConstant = asqMokafaaHelper.mapError(asqServiceResponse.getErrorCode());
		LOG.info("Error From Mokafaa OTP API::::: " + asqServiceResponse.getErrorCode() + " - "
				+ asqServiceResponse.getMessage());
		LOG.info("Error Message Generated By Xstore based on Mokafaa API Response::::: " + errorConstant);
		setOpState(SHOWING_ERROR_PROMPT);
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
}
