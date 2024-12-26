/**
 * 
 */
package asq.pos.bnpl.tabby.tender.op;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.python.antlr.PythonParser.return_stmt_return;

import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyDetailsObj;
import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyErrorDesc;
import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyPaymentObj;
import asq.pos.bnpl.tabby.tender.service.AsqSubmitBnplTabbyServiceRequest;
import asq.pos.bnpl.tabby.tender.service.AsqSubmitBnplTabbyServiceResponse;
import asq.pos.bnpl.tabby.tender.service.IAsqBnplTabbyServices;
import asq.pos.bnpl.tabby.tender.service.IAsqSubmitBnplTabbyServiceRequest;
import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceResponse;
import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.stc.tender.AsqStcHelper;
import dtv.i18n.IFormattable;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trl.IRetailTransaction;

/**
 * @author RA20221457
 *
 */

public class AsqBnplTabbyPaymentValidationOp extends Operation{
	private static final Logger LOG = LogManager.getLogger(AsqBnplTabbyPaymentValidationOp.class);
	
	@Inject
	protected Provider<IAsqBnplTabbyServices> tabbyService;
	
	@Inject
	AsqStcHelper asqStcHelper;

	@Override
	public IOpResponse handleOpExec(IXstEvent paramIXstEvent) {
		LOG.info("Calling GetNotification API");
		IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
		String sessionID=_transactionScope.getValue(AsqValueKeys.TABBY_RESPONSE);
		return requestPreparerGetNotificationService(sessionID, trans);
	}
	
	private IOpResponse requestPreparerGetNotificationService(String sessionID, IRetailTransaction trans) {

		AsqSubmitBnplTabbyServiceResponse asqSubmitBnplTabbyServiceResponse = new AsqSubmitBnplTabbyServiceResponse();
		IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest = new AsqSubmitBnplTabbyServiceRequest();
			asqSubmitBnplTabbyServiceRequest.setId(sessionID);
			LOG.info("Calling notificationRequest API to cofirm whether customer has received SMS or not");
			asqSubmitBnplTabbyServiceResponse = notificationRequest(asqSubmitBnplTabbyServiceRequest);
			LOG.info("Returned notificationRequest API to cofirm whether customer has received SMS or not");
			return validateNotificationServiceResponse(asqSubmitBnplTabbyServiceResponse, trans);
	}

	@SuppressWarnings("null")
	private IOpResponse validateNotificationServiceResponse(AsqSubmitBnplTabbyServiceResponse response,
			IRetailTransaction trans) {
		if (null != response && null != response.getError()) {
			return handleServiceError(response);
		} else if (response.getStatus().equalsIgnoreCase("ok")) {
			LOG.info("Calling requestPreparerRetrievePayment API to check the payment status");
			if (getScopedValue(AsqValueKeys.ASQ_TAMARA_CUSTOMER_PAYMENT_CONFIRMATION) != null) {
				return (IOpResponse) requestPreparerRetrievePayment(response, trans);
			} else {
				try {
					Thread.sleep(10000);
					setScopedValue(AsqValueKeys.ASQ_TAMARA_CUSTOMER_PAYMENT_CONFIRMATION, true);
				} catch (InterruptedException e) {
					LOG.error("Exception during confirmation sleep cycle :" + e);
				}
				return HELPER.getCompletePromptResponse("ASQ_TABBY_PAYMENT_WAIT");
			}
		}
		return HELPER.completeCurrentChainResponse();
	}

	private IOpResponse requestPreparerRetrievePayment(AsqSubmitBnplTabbyServiceResponse response, IRetailTransaction trans) {

		AsqSubmitBnplTabbyServiceResponse asqSubmitBnplTabbyServiceResponse = new AsqSubmitBnplTabbyServiceResponse();
		IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest = new AsqSubmitBnplTabbyServiceRequest();
		AsqBnplTabbyPaymentObj payment = new AsqBnplTabbyPaymentObj();
		 AsqBnplTabbyDetailsObj asqBnplTabbyDetailsObj = new AsqBnplTabbyDetailsObj();
		if (response.getError() == null) {
	        asqBnplTabbyDetailsObj.setPhone(_transactionScope.getValue(AsqValueKeys.ASQ_MOBILE_NUMBER));
	        asqBnplTabbyDetailsObj.setReference_id(Long.toString(trans.getTransactionSequence()));
	        payment.setAmount(trans.getTotal().toString());
	        payment.setCurrency(trans.getRetailTransactionLineItems().get(0).getCurrencyId());
	        //payment.setCurrency("SAR");
	        payment.setBuyer(asqBnplTabbyDetailsObj);
	        payment.setOrder(asqBnplTabbyDetailsObj);
			payment.setId(this._transactionScope.getValue(AsqValueKeys.ASQ_TABBY_PMNT_ID));
			asqSubmitBnplTabbyServiceRequest.setPayment(payment);

			LOG.info("Calling requestPreparerRetrievePayment API to check the payment status");
			asqSubmitBnplTabbyServiceResponse = retrievePayment(asqSubmitBnplTabbyServiceRequest);
			LOG.info("Returned from requestPreparerRetrievePayment API to check the payment status");
		} else if (null != response && null != response.getError()) {
			return handleServiceError(response);
		}
		return validateRetrievePaymentResponse(asqSubmitBnplTabbyServiceRequest);
	}

	 
		private IOpResponse validateRetrievePaymentResponse(IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest) {
			
			AsqSubmitBnplTabbyServiceResponse response = new AsqSubmitBnplTabbyServiceResponse();
			if (null != response && null != response.getError()) {
				return handleServiceError(response);
			} else if (null != _transactionScope.getValue(AsqValueKeys.ASQ_TABBY_PAYMENT_SUCCESS)) {
				LOG.info("Successful Execution of Retrieve API calling payment successful prompt");
				return this.HELPER.getCompletePromptResponse("ASQ_TABBY_PAYMENT_SUCCESSFULL");
			}
			else if (null != _transactionScope.getValue(AsqValueKeys.ASQ_TABBY_PAYMENT_EXPIRED)) {
				LOG.info("Retrieve API calling payment expired/timeout prompt");
				return this.HELPER.getCompletePromptResponse("ASQ_TABBY_PAYMENT_EXPIRED");
			}
			return HELPER.completeCurrentChainResponse();
		}
	
	public AsqSubmitBnplTabbyServiceResponse notificationRequest(
			IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest) {
		AsqSubmitBnplTabbyServiceResponse response = new AsqSubmitBnplTabbyServiceResponse();
		try {
			LOG.info("Calling notificationRequest API");
			response = (AsqSubmitBnplTabbyServiceResponse) tabbyService.get()
					.notificationRequest(asqSubmitBnplTabbyServiceRequest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	public AsqSubmitBnplTabbyServiceResponse retrievePayment(
			IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest) {
		AsqSubmitBnplTabbyServiceResponse response = new AsqSubmitBnplTabbyServiceResponse();
		boolean success = false;
		while (!success) {
			try {
			LOG.info("Calling getOrderDetails API to validate payment status");
				response = (AsqSubmitBnplTabbyServiceResponse) tabbyService.get().retrievePayment(asqSubmitBnplTabbyServiceRequest);
				if (response != null && response.getStatus() != null) {
					LOG.info("TABBY Response status :" + response.getStatus());
					System.out.println("TABBY Response status :" + response.getStatus());
					if (response.getStatus().equalsIgnoreCase("AUTHORIZED")||response.getStatus().equalsIgnoreCase("CLOSED")) {
						LOG.info("Successfully completed the payment");
						success = true;
						_transactionScope.setValue(AsqValueKeys.ASQ_TABBY_PAYMENT_SUCCESS, success);
					} else if (response.getStatus().equalsIgnoreCase("EXPIRED")) {
						LOG.info("Payment link has expired");
						success = true;
						_transactionScope.setValue(AsqValueKeys.ASQ_TABBY_PAYMENT_EXPIRED, success);
					}
				}
				if (!success) {
					Thread.sleep(50000);
				}
			} catch (Exception ex) {
				LOG.debug("Exception during calling getOrderDetails method to check payment status");
			}
		}
		return response;
	}

	public AsqSubmitBnplTabbyServiceResponse refundPayment(
			IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest) {
		AsqSubmitBnplTabbyServiceResponse response = new AsqSubmitBnplTabbyServiceResponse();
		try {
			LOG.info("Calling refundPayment API");
			response = (AsqSubmitBnplTabbyServiceResponse) tabbyService.get()
					.refundPayment(asqSubmitBnplTabbyServiceRequest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
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
		LOG.info("TABBY REDEEM API::::: " + message);
		return HELPER.getPromptResponse("ASQ_TABBY_TECHNICAL_ERROR", args);
	}

	/**
	 * This method handles the TABBY service errors
	 * 
	 * @param asqServiceResponse
	 * @return Error Prompts
	 */

	public IOpResponse handleServiceError(AsqSubmitBnplTabbyServiceResponse asqServiceResponse) {
		IFormattable[] args = new IFormattable[2];
		AsqBnplTabbyErrorDesc error = asqServiceResponse.getError();
		args[0] = _formattables.getSimpleFormattable(error.getErrorType());
		args[1] = _formattables.getSimpleFormattable(error.getError());
		String errorConstant = asqStcHelper.mapTabbyErrors(asqServiceResponse.getStatus());
		LOG.info("Error From TABBY API::::: " + args[0] + " - " + args[1]);
		LOG.info("Error Message Generated By Xstore based on TABBY API Response::::: " + error.getErrorType());
		return HELPER.getPromptResponse(errorConstant, args);
	}

}
