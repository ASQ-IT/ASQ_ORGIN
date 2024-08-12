package asq.pos.loyalty.stc.earn.op;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

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
import dtv.pos.common.OpChainKey;
import dtv.pos.common.TransactionType;
import dtv.pos.framework.action.XstDataAction;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trl.IRetailTransaction;

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

		IRetailTransaction txn = this._transactionScope.getTransaction(TransactionType.RETAIL_SALE);
		boolean isCustomerPresent = false;
		String custMobileNmbr = "";
		boolean isCustomerRequired = AsqConfigurationMgr.getSTCCustomerAvailable();
		LOG.info("STC Earn API customer availability parameter : " + isCustomerRequired);
		int pointsForCalculation = AsqConfigurationMgr.getSTCPointsCalculation();
		LOG.info("STC Earn API Point calculation value parameter : " + pointsForCalculation);
		BigDecimal valueForCalculation = new BigDecimal(pointsForCalculation);
		if (null != txn && (txn.getCustomerParty() != null)) {
			isCustomerPresent = true;
		}
		if (isCustomerPresent && isCustomerRequired && !isReturnTransaction(txn)) {
			if (txn.getSubtotal().compareTo(valueForCalculation) == 1) {
				Byte calcltdSTCPntsForEarnAPI = (txn.getSubtotal().divide(valueForCalculation)).byteValue();
				LOG.info("STC Points calculated Earn Service API call Starts here : " + calcltdSTCPntsForEarnAPI);
				if (getScopedValue(AsqValueKeys.ASQ_STC_MOBILE) != null) {
					custMobileNmbr = getScopedValue(AsqValueKeys.ASQ_STC_MOBILE);
				}
				AsqSTCLoyaltyServiceResponse response = new AsqSTCLoyaltyServiceResponse();
				IAsqSTCLoyaltyServiceRequest request = new AsqSTCLoyaltyServiceRequest();
				LOG.info("STC request preparation for Earn API Starts here : ");
				request.setMsisdn(Long.parseLong(custMobileNmbr.trim()));
				request.setBranchId(System.getProperty("asq.stc.branchid"));
				request.setTerminalId(System.getProperty("asq.stc.terminalid"));
				request.setRequestDate(DateTime.now().toString());
				request.setPIN(null);
				request.setAmount(calcltdSTCPntsForEarnAPI.intValue());
				LOG.info("STC request preparation for Earn API call Ends here : ");
				LOG.info("Sending request to Earn API : " + request);
				response = (AsqSTCLoyaltyServiceResponse) _asqSTCLoyalityTenderService.get().earnReward(request);
				LOG.info("Returning request from Earn API: " + response);
				if (null != response && null != response.getErrors() && 0 != response.getErrors().length) {
					return handleServiceError(response);
				} else if (null == response) {
					IFormattable[] args = new IFormattable[2];
					args[1] = _formattables.getSimpleFormattable("Service has null response");
					LOG.info("STC Earn Reward API: Service has null response");
					return HELPER.getPromptResponse("ASQ_STC_TECHNICAL_ERROR", args);
				}
			} else {
				return this.HELPER.completeResponse();
			}
		} else if ((txn.getSubtotal().compareTo(valueForCalculation) == 1) && (!isCustomerPresent)) {
			if (paramIXstEvent != null) {
				XstDataAction key = (XstDataAction) (paramIXstEvent);
				if (XstDataActionKey.NO.equals(key.getActionKey())) {
					LOG.info("STC Earn Reward API Customer Mobile Number Screen Cashier selected NO to complete the transaction");
					return this.HELPER.completeResponse();
				} else if (paramIXstEvent != null) {
					if (XstDataActionKey.YES.equals(key.getActionKey())) {
						LOG.info("STC Earn Reward API Cashier selected YES to link customer to the transaction");
						return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("CUST_ASSOCIATION"));
					}
				}
			}
			return HELPER.getPromptResponse("ASQ_STC_CUSTOMER_UNAVAILABLE", _formattables.getSimpleFormattable(AsqZatcaConstant.CUSTOMER_UNAVAILABLE));
		} else {
			return this.HELPER.completeResponse();
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
}
