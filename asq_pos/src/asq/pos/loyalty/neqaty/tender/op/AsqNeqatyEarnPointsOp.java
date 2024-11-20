package asq.pos.loyalty.neqaty.tender.op;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqConfigurationMgr;
import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyHelper;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyServiceRequest;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyServiceResponse;
import asq.pos.loyalty.neqaty.tender.service.IAsqNeqatyService;
import asq.pos.loyalty.neqaty.tender.service.IAsqNeqatyServiceRequest;
import asq.pos.loyalty.neqaty.tender.service.NeqatyMethod;
import dtv.i18n.IFormattable;
import dtv.pos.common.TransactionType;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trl.IRetailTransaction;

public class AsqNeqatyEarnPointsOp extends Operation {

	private static final Logger LOG = LogManager.getLogger(AsqNeqatyEarnPointsOp.class);

	/**
	 * This class checks if customer is already attached to sale transaction. If
	 * Yes, and the trx total is greater than 100SAR, STC points added to the
	 * customer account. If customer not available, prompt cashier with an option to
	 * add customer or complete the trx without adding points.
	 */

	@Inject
	protected Provider<IAsqNeqatyService> asqNeqatyService;

	@Inject
	AsqNeqatyHelper asqNeqatyHelper;

	/**
	 * This method handles the customer availability parameter check and points
	 * calculation for Earn API
	 * 
	 * @param
	 * @return
	 */

	@SuppressWarnings("unused")
	@Override
	public IOpResponse handleOpExec(IXstEvent paramIXstEvent) {

		IRetailTransaction txn = _transactionScope.getTransaction(TransactionType.RETAIL_SALE);
		boolean isCustomerPresent = false;
		String custMobileNmbr = "";
		boolean isCustomerRequired = AsqConfigurationMgr.getSTCCustomerAvailable();
		LOG.info("Neqaty Earn API customer availability parameter : " + isCustomerRequired);
		int pointsForCalculation = AsqConfigurationMgr.getSTCPointsCalculation();
		LOG.info("Neqaty Earn API Point calculation value parameter : " + pointsForCalculation);
		BigDecimal valueForCalculation = new BigDecimal(pointsForCalculation);
		
		if (null != txn && (txn.getCustomerParty() != null)) {
			isCustomerPresent = true;//Check transaction customer
		}
		if (isCustomerPresent && isCustomerRequired && !isReturnTransaction(txn) && txn.getSubtotal().compareTo(valueForCalculation) > 0) {
				Byte calcltdNeqatyPntsForEarnAPI = (txn.getSubtotal().divide(valueForCalculation)).byteValue();
				LOG.info("Neqaty Points calculated Earn Service API call Starts here : " + calcltdNeqatyPntsForEarnAPI);
				if ( _transactionScope.getValue(AsqValueKeys.ASQ_NEQATY_MOBILE)!=null) {
					custMobileNmbr =  _transactionScope.getValue(AsqValueKeys.ASQ_NEQATY_MOBILE);
				}
				return earnPoints(custMobileNmbr, calcltdNeqatyPntsForEarnAPI.doubleValue());
			}
			/*
				 * else { return this.HELPER.completeResponse(); }
				 */
		 
		/*
			 * else if ((txn.getSubtotal().compareTo(valueForCalculation) == 1) &&
			 * (!isCustomerPresent)) { if (paramIXstEvent != null) { XstDataAction key =
			 * (XstDataAction) (paramIXstEvent); if
			 * (XstDataActionKey.NO.equals(key.getActionKey())) { LOG.info(
			 * "Neqaty Earn Reward API Customer Mobile Number Screen Cashier selected NO to complete the transaction"
			 * ); return this.HELPER.completeResponse(); } else if (paramIXstEvent != null)
			 * { if (XstDataActionKey.YES.equals(key.getActionKey())) { LOG.
			 * info("Neqaty Earn Reward API Cashier selected YES to link customer to the transaction"
			 * ); return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf(
			 * "CUST_ASSOCIATION")); } } } return
			 * HELPER.getPromptResponse("ASQ_Neqaty_CUSTOMER_UNAVAILABLE",
			 * _formattables.getSimpleFormattable(AsqZatcaConstant.CUSTOMER_UNAVAILABLE)); }
			 */
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

	private IOpResponse earnPoints(String custMobileNmbr, double amount) {
		IAsqNeqatyServiceRequest request = new AsqNeqatyServiceRequest();
		request.setAuthenticationKey(System.getProperty("asq.neqaty.auth.key"));
		request.setTid(0);
		request.setMethod(NeqatyMethod.AUTHORIZE);
		request.setOperationType("Earn");

		request.setMsisdn(custMobileNmbr);
		request.setAmount(amount);// transaction amount

		AsqNeqatyServiceResponse response = (AsqNeqatyServiceResponse) asqNeqatyService.get()
				.callNeqatyService(request);
		if (null != response && 0 == response.getResultCode()) {
			String transactionReference = response.getTransactionReference();
			_transactionScope.setValue(AsqValueKeys.ASQ_NEQATY_TRANS_REFERENCE_EARN, transactionReference);
			response = confirmEarnPointsTransaction(request, transactionReference);
		}
		return validateResponse(response);
	}

	private AsqNeqatyServiceResponse confirmEarnPointsTransaction(IAsqNeqatyServiceRequest request,
			String transactionReference) {
		request.setTransactionReference(transactionReference);
		request.setMethod(NeqatyMethod.CONFIRM);

		request.setAmount(0);
		request.setOperationType(null);
		return (AsqNeqatyServiceResponse) asqNeqatyService.get().callNeqatyService(request);
	}

	private IOpResponse validateResponse(AsqNeqatyServiceResponse response) {
		if (null != response && 0 != response.getResultCode()) {
			return handleServiceError(response);
		} else if (null == response) {
			return technicalErrorScreen("Service has null response");
		}
		return HELPER.completeResponse();
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
		LOG.info("Neqaty Inquire OTP Operation::::: " + message);
		return HELPER.getPromptResponse("ASQ_NEQATY_TECHNICAL_ERROR", args);
	}
}
