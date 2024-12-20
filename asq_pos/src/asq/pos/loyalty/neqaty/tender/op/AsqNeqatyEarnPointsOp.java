package asq.pos.loyalty.neqaty.tender.op;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqConfigurationMgr;
import asq.pos.common.AsqValueKeys;

import dtv.xst.dao.ttr.ITenderLineItem;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyHelper;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyServiceRequest;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyServiceResponse;
import asq.pos.loyalty.neqaty.tender.service.IAsqNeqatyService;
import asq.pos.loyalty.neqaty.tender.service.IAsqNeqatyServiceRequest;
import asq.pos.loyalty.neqaty.tender.service.NeqatyMethod;
import asq.pos.zatca.AsqZatcaConstant;
import dtv.i18n.IFormattable;
import dtv.pos.common.TransactionType;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.op.Operation;
import dtv.pos.framework.scope.ValueKey;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trl.IRetailTransactionLineItem;
import dtv.xst.dao.trn.IPosTransaction;

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

	@Override
	public IOpResponse handleOpExec(IXstEvent paramIXstEvent) {

		IRetailTransaction txn = _transactionScope.getTransaction(TransactionType.RETAIL_SALE);
		boolean isCustomerPresent = false;
		String custMobileNmbr = "";
		boolean isCustomerRequired = AsqConfigurationMgr.getSTCCustomerAvailable();
		LOG.info("Neqaty Earn API customer availability parameter : " + isCustomerRequired);
		/*
		 * int pointsForCalculation = AsqConfigurationMgr.getSTCPointsCalculation();
		 * LOG.info("Neqaty Earn API Point calculation value parameter : " +
		 * pointsForCalculation); BigDecimal valueForCalculation = new
		 * BigDecimal(pointsForCalculation);
		 */
		BigDecimal earnAmount = BigDecimal.ZERO;
		if (txn.getAmountTendered() != null && txn.getAmountTendered().compareTo(BigDecimal.ZERO) > 0) {
			earnAmount = txn.getSubtotal();
			for (IRetailTransactionLineItem tenderLineItem : txn.getTenderLineItems()) {
				if (((ITenderLineItem) tenderLineItem).getTenderId().equalsIgnoreCase("NEQATI")
						&& !((ITenderLineItem) tenderLineItem).getVoid()) {
					earnAmount = txn.getSubtotal().subtract(((ITenderLineItem) tenderLineItem).getAmount());
				}
			}
		}
		if (txn.getCustomerParty() != null) {
			custMobileNmbr = txn.getCustomerParty().getTelephone3();
			isCustomerPresent = true;// Check transaction customer
		}

		if (isCustomerPresent && isCustomerRequired && !isReturnTransaction(txn)
				&& earnAmount.compareTo(BigDecimal.ZERO) > 0) {

			LOG.info("Neqaty Points calculated Earn Service API call Starts here : ");
			if (_transactionScope.getValue(AsqValueKeys.ASQ_NEQATY_MOBILE) != null) {
				custMobileNmbr = _transactionScope.getValue(AsqValueKeys.ASQ_NEQATY_MOBILE);
			}
			return earnPoints(custMobileNmbr, earnAmount.doubleValue(), txn);
		}

		else if (isCustomerPresent && isCustomerRequired && isReturnTransaction(txn)
				&& earnAmount.compareTo(BigDecimal.ZERO) <= 0) {
			IRetailTransaction rtrans = _transactionScope.getValue(ValueKeys.CURRENT_ORIGINAL_TRANSACTION);
			if (rtrans != null && rtrans.getStringProperty(AsqZatcaConstant.ASQ_NEQATY_EARN_TRN) != null) {
				for (IRetailTransactionLineItem tenderLineItem : rtrans.getTenderLineItems()) {
					if (((ITenderLineItem) tenderLineItem).getTenderId().equalsIgnoreCase("NEQATI")
							&& !((ITenderLineItem) tenderLineItem).getVoid()) {
						earnAmount = rtrans.getSubtotal().subtract(((ITenderLineItem) tenderLineItem).getAmount());
					}
				}
				if (txn.getSubtotal().abs().compareTo(earnAmount) > 0) {
					LOG.info("Neqaty Points calculated Earn Service API call Starts here : ");
					return refundEarnPoints(custMobileNmbr, rtrans.getStringProperty(AsqZatcaConstant.ASQ_NEQATY_EARN_TRN), earnAmount.abs().doubleValue());
				} else {
					LOG.info("Neqaty Points calculated Earn Service API call Starts here : ");
					return refundEarnPoints(custMobileNmbr, rtrans.getStringProperty(AsqZatcaConstant.ASQ_NEQATY_EARN_TRN), txn.getSubtotal().abs().doubleValue());
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

		return null != trn && BigDecimal.ZERO.compareTo(trn.getTotal()) > 0;

	}

	/**
	 * Method handles all service errors returned from Earn API
	 * 
	 * @param txn
	 * 
	 * @param asqServiceResponse
	 * @return ErrorPrompt
	 */

	private IOpResponse earnPoints(String custMobileNmbr, double amount, IRetailTransaction txn) {
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
			txn.setStringProperty(AsqZatcaConstant.ASQ_NEQATY_EARN_TRN, transactionReference);
			response = confirmEarnPointsTransaction(request, transactionReference);
		}
		return validateResponse(response);
	}

	/**
	 * Method handles all service errors returned from Earn API
	 * 
	 * @param transEarnReference
	 * 
	 * @param asqServiceResponse
	 * @return ErrorPrompt
	 */

	private IOpResponse refundEarnPoints(String custMobileNmbr, String transEarnReference, double amount) {
		IAsqNeqatyServiceRequest request = new AsqNeqatyServiceRequest();
		request.setAuthenticationKey(System.getProperty("asq.neqaty.auth.key"));
		request.setTid(0);
		request.setAmount(amount);
		request.setMethod(NeqatyMethod.AUTHORIZE);
		request.setOperationType("EarnRefund");

		request.setMsisdn(custMobileNmbr);
		request.setTransactionReference(transEarnReference);// transactionreference from the earn auth call response
		AsqNeqatyServiceResponse response = (AsqNeqatyServiceResponse) asqNeqatyService.get()
				.callNeqatyService(request);
		if (null != response && 0 == response.getResultCode()) {
			String transactionReference = response.getTransactionReference();
			// setScopedValue(AsqValueKeys.ASQ_NEQATY_TRANS_REFERENCE,transactionReference);
			response = confirmEarnPointsTransaction(request, transactionReference);
		}
		return validateResponse(response);
	}

	private AsqNeqatyServiceResponse confirmEarnPointsTransaction(IAsqNeqatyServiceRequest request,
			String transactionReference) {
		request.setTransactionReference(transactionReference);
		request.setMethod(NeqatyMethod.CONFIRM);
		request.setOperationType(null);
		// request.setAmount(0);
		return (AsqNeqatyServiceResponse) asqNeqatyService.get().callNeqatyService(request);
	}

	private IOpResponse validateResponse(AsqNeqatyServiceResponse response) {
		/*
		 * if (null != response && 0 != response.getResultCode()) { return
		 * handleServiceError(response); }
		 */
		if (null == response) {
			return technicalErrorScreen("Service has null response");
		}
		return HELPER.completeResponse();
	}

	/*
	 * public IOpResponse handleServiceError(AsqNeqatyServiceResponse
	 * asqServiceResponse) { IFormattable[] args = new IFormattable[2]; args[0] =
	 * _formattables.getSimpleFormattable(String.valueOf(asqServiceResponse.
	 * getResultCode())); args[1] =
	 * _formattables.getSimpleFormattable(asqServiceResponse.getResultDescription())
	 * ; String errorConstant =
	 * asqNeqatyHelper.mapError(asqServiceResponse.getResultCode());
	 * LOG.info("Error From Neqaty Inquire OTP Operation : " +
	 * asqServiceResponse.getResultCode() + " - " +
	 * asqServiceResponse.getResultDescription()); LOG.
	 * info("Error Message Generated By Xstore based on Neqaty Inquire OTP Operation Response: "
	 * + errorConstant); return HELPER.getPromptResponse(errorConstant, args); }
	 */

	private IOpResponse technicalErrorScreen(String message) {
		IFormattable[] args = new IFormattable[2];
		args[1] = _formattables.getSimpleFormattable(message);
		LOG.info("Neqaty Earn Operation::::: " + message);
		return HELPER.getPromptResponse("ASQ_NEQATY_EARN_ERROR", args);
	}
}
