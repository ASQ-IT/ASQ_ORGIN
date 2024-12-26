package asq.pos.loyalty.stc.earn.op;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqConfigurationMgr;
import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.stc.tender.AsqStcHelper;
import asq.pos.loyalty.stc.tender.service.AsqSTCErrorDesc;
import asq.pos.loyalty.stc.tender.service.AsqSTCLoyaltyServiceRequest;
import asq.pos.loyalty.stc.tender.service.AsqSTCLoyaltyServiceResponse;
import asq.pos.loyalty.stc.tender.service.IAsqSTCLoyaltyServiceRequest;
import asq.pos.loyalty.stc.tender.service.IAsqSTCLoyaltyTenderService;
import asq.pos.zatca.AsqZatcaConstant;
import dtv.i18n.IFormattable;
import dtv.pos.common.TransactionType;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trl.IRetailTransactionLineItem;
import dtv.xst.dao.ttr.ITenderLineItem;

public class AsqSTCEarnPointsOp extends Operation {

	private static final Logger LOG = LogManager.getLogger(AsqSTCEarnPointsOp.class);

	/**
	 * This class checks if customer is already attached to sale transaction. If
	 * Yes, and the trx total is greater than 100SAR, STC points added to the
	 * customer account. If customer not available, prompt cashier with an option to
	 * add customer or complete the trx without adding points.
	 */

	@Inject
	protected Provider<IAsqSTCLoyaltyTenderService> _asqSTCLoyalityTenderService;

	@Inject
	AsqStcHelper asqStcHelper;

	/**
	 * This method handles the customer availability parameter check and points
	 * calculation for Earn API
	 *
	 * @param
	 * @return
	 */

	@Override
	public IOpResponse handleOpExec(IXstEvent paramIXstEvent) {

		IRetailTransaction txn = _transactionScope.getTransaction(TransactionType.RETAIL_SALE);
		boolean isCustomerPresent = false;
		String custMobileNmbr = "";
		boolean isCustomerRequired = AsqConfigurationMgr.getSTCCustomerAvailable();
		LOG.info("STC Earn API customer availability parameter : " + isCustomerRequired);
		BigDecimal earnAmount = BigDecimal.ZERO;
		if (txn.getAmountTendered() != null && txn.getAmountTendered().compareTo(BigDecimal.ZERO) > 0) {
			earnAmount = txn.getSubtotal();
			for (IRetailTransactionLineItem tenderLineItem : txn.getTenderLineItems()) {
				if (((ITenderLineItem) tenderLineItem).getTenderId().equalsIgnoreCase("STC") && !((ITenderLineItem) tenderLineItem).getVoid()) {
					earnAmount = txn.getSubtotal().subtract(((ITenderLineItem) tenderLineItem).getAmount());
				}
			}
		}
		if (txn.getCustomerParty() != null) {
			custMobileNmbr = txn.getCustomerParty().getTelephone3();
			isCustomerPresent = true;// Check transaction customer
		}
		if (isCustomerPresent && isCustomerRequired && !isReturnTransaction(txn) && earnAmount.compareTo(BigDecimal.ZERO) > 0) {
			LOG.info("STC Points Earn Service API call Starts here : ");
			if (_transactionScope.getValue(AsqValueKeys.ASQ_MOBILE_NUMBER) != null) {
				custMobileNmbr = _transactionScope.getValue(AsqValueKeys.ASQ_MOBILE_NUMBER);
			}
			return earnPoints(custMobileNmbr, earnAmount.intValue(), txn);
		} else if (isCustomerPresent && isCustomerRequired && isReturnTransaction(txn) && earnAmount.compareTo(BigDecimal.ZERO) <= 0) {
			earnAmount = txn.getSubtotal().abs();
			IRetailTransaction rtrans = _transactionScope.getValue(ValueKeys.CURRENT_ORIGINAL_TRANSACTION);
			if (rtrans != null && rtrans.getStringProperty(AsqZatcaConstant.ASQ_STC_SUCCESS_REDEEM_RESPONSE) != null && custMobileNmbr != null) {
				for (IRetailTransactionLineItem tenderLineItem : rtrans.getTenderLineItems()) {
					if (((ITenderLineItem) tenderLineItem).getTenderId().equalsIgnoreCase("STC") && !((ITenderLineItem) tenderLineItem).getVoid()) {
						earnAmount = rtrans.getSubtotal().subtract(((ITenderLineItem) tenderLineItem).getAmount());
					}
				}
				if (txn.getSubtotal().abs().compareTo(earnAmount) > 0) {
					LOG.info("STC Points RedeemRefund during return Service API call Starts here : ");

					return stcRefundRedeem(custMobileNmbr, rtrans.getStringProperty(AsqZatcaConstant.ASQ_STC_SUCCESS_REDEEM_RESPONSE), earnAmount.intValue());
				} else {
					LOG.info("STC Points RedeemRefund during return Service API call Starts here : ");
					return stcRefundRedeem(custMobileNmbr, rtrans.getStringProperty(AsqZatcaConstant.ASQ_STC_SUCCESS_REDEEM_RESPONSE), txn.getSubtotal().intValue());
				}
			}
		}
		return this.HELPER.completeResponse();
	}

	/**
	 * Check for return transaction Do not add points for customer for return or
	 * exchange transaction
	 *
	 * @param trn
	 * @return boolean
	 */
	public boolean isReturnTransaction(IRetailTransaction trn) {

		if (null != trn && BigDecimal.ZERO.compareTo(trn.getTotal()) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Method handles all service errors returned from Earn API
	 *
	 * @param asqServiceResponse
	 * @return ErrorPrompt
	 */

	public IOpResponse handleServiceError(AsqSTCLoyaltyServiceResponse asqServiceResponse) {
		IFormattable[] args = new IFormattable[2];
		AsqSTCErrorDesc error = asqServiceResponse.getErrors()[0];
		args[0] = _formattables.getSimpleFormattable(error.getCode());
		args[1] = _formattables.getSimpleFormattable(error.getDescription());
		String errorConstant = asqStcHelper.mapError(error.getCode());
		LOG.info("Error From STC Earn Reward API: " + error.getCode() + " - " + error.getDescription());
		LOG.info("Error Message Generated By Xstore based on STC Earn Reward Response: " + errorConstant);
		return HELPER.getPromptResponse(errorConstant, args);
	}

	private IOpResponse earnPoints(String custMobileNmbr, int amount, IRetailTransaction txn) {
		IAsqSTCLoyaltyServiceRequest request = new AsqSTCLoyaltyServiceRequest();
		LOG.info("STC request preparation for Earn API Starts here : ");
		if (null != _transactionScope.getValue(AsqValueKeys.ASQ_MOBILE_NUMBER)) {
			custMobileNmbr = _transactionScope.getValue(AsqValueKeys.ASQ_MOBILE_NUMBER);
		}
		request.setMsisdn(Long.parseLong(custMobileNmbr.trim()));
		request.setBranchId(System.getProperty("asq.stc.branchid"));
		request.setTerminalId(System.getProperty("asq.stc.terminalid"));
		ZoneId ksaZone = ZoneId.of("Asia/Riyadh");
		ZonedDateTime ksaDateTime = ZonedDateTime.now(ksaZone);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		String requestDate = ksaDateTime.format(formatter);
		request.setRequestDate(requestDate);
		request.setPIN(null);
		request.setAmount(amount);
		String globalID = asqStcHelper.generateGlobalId();
		// asqStcHelper.saveSTCResponseToDB(trans, globalID, requestDate);
		request.setGlobalId(globalID);
		LOG.info("Sending request to Earn API: " + request.toString());
		AsqSTCLoyaltyServiceResponse response = (AsqSTCLoyaltyServiceResponse) _asqSTCLoyalityTenderService.get().earnReward(request);
		LOG.info("STC API Earn Reward Response :" + response);
		return validateEarnResponse(response);
	}

	private IOpResponse validateEarnResponse(AsqSTCLoyaltyServiceResponse response) {
		if (null != response && null != response.getErrors() && 0 != response.getErrors().length) {
			return handleServiceError(response);
		} else if (null == response) {
			return technicalErrorScreen("STC REDEEM API::::: Service has null response");
		}
		if (response.getDescription().equalsIgnoreCase("success")) {
			return HELPER.getCompletePromptResponse("ASQ_STC_SUCCESSFULL_EARN_REWARD");
		}
		return this.HELPER.completeResponse();
	}

	/**
	 * This method return Technical Error
	 *
	 * @param argModel
	 * @return Error
	 */

	private IOpResponse technicalErrorScreen(String message) {
		IFormattable[] args = new IFormattable[2];
		args[1] = _formattables.getSimpleFormattable(message);
		LOG.info("STC REDEEM API::::: " + message);
		return HELPER.getPromptResponse("ASQ_STC_TECHNICAL_ERROR", args);
	}

	/**
	 * This method implements the refund service API call by preparing the request
	 * attributes
	 *
	 * @param trans
	 * @return refund redeem submission to STC Service Handler
	 */

	@SuppressWarnings("unused")
	private IOpResponse stcRefundRedeem(String custMobileNumber, String transEarnReference, int amount) {

		IAsqSTCLoyaltyServiceRequest request = new AsqSTCLoyaltyServiceRequest();
		String requestDate = asqStcHelper.getCurrentDateTime();
		LOG.info("Request Date :" + requestDate);
		request.setRequestDate(requestDate);
		request.setMsisdn(Long.parseLong(custMobileNumber.trim()));
		request.setBranchId(System.getProperty("asq.stc.branchid"));
		request.setTerminalId(System.getProperty("asq.stc.terminalid"));
		request.setRefRequestId(_transactionScope.getValue(AsqValueKeys.ASQ_STC_REF_REQUEST_ID));
		request.setRefRequestDate(_transactionScope.getValue(AsqValueKeys.ASQ_STC_REF_REQUEST_DATE));
		request.setPIN(null);
		request.setAmount(null);
		LOG.info("STC API Generate GlobalID method calling from ASQSTCHelper");
		String globalID = asqStcHelper.generateGlobalId();
		LOG.info("STC API Generate GlobalID generated:" + globalID);
		request.setGlobalId(globalID);
		LOG.info("STC API trigger OTP request is prepared :" + request);
		AsqSTCLoyaltyServiceResponse response = (AsqSTCLoyaltyServiceResponse) _asqSTCLoyalityTenderService.get().refundRedeem(request);
		if (response.getDescription().equalsIgnoreCase("Success")) {
			return this.HELPER.getCompletePromptResponse("ASQ_VOID_SUCCESSFULL");
		}
		if (null != response && null != response.getErrors()) {
			return handleServiceError(response);
		} else if (null == response) {
			return technicalErrorScreen("TAMARA API::::: Service has null response");
		}
		LOG.debug("STC refund service Ends here: ");

		return HELPER.completeResponse();
	}

	@Override
	public boolean isOperationApplicable() {
		return _transactionScope.getValue(AsqValueKeys.ASQ_LOYALTY) && AsqConfigurationMgr.getSTCLoyaltyEarnEnable();
	}
}
