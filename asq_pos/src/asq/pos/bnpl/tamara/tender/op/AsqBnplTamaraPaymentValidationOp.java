package asq.pos.bnpl.tamara.tender.op;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceRequest;
import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceResponse;
import asq.pos.bnpl.tamara.tender.service.IAsqBnplTamaraServices;
import asq.pos.bnpl.tamara.tender.service.IAsqSubmitBnplTamraServiceRequest;
import asq.pos.common.AsqValueKeys;
import dtv.i18n.IFormattable;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.security.StationState;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trn.IPosTransaction;
import dtv.xst.dao.ttr.ITenderLineItem;

/**
 * @author RA20221457
 *
 */
public class AsqBnplTamaraPaymentValidationOp extends Operation {
	private static final Logger LOG = LogManager.getLogger(AsqBnplTamaraPaymentValidationOp.class);

	@Inject
	protected Provider<IAsqBnplTamaraServices> tamaraService;

	@Override
	public IOpResponse handleOpExec(IXstEvent paramIXstEvent) {
		Boolean custConfrmtn = false;
		IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
		String orderID = _transactionScope.getValue(AsqValueKeys.TAMARA_RESPONSE);
		if (getScopedValue(AsqValueKeys.ASQ_TAMARA_CUSTOMER_PAYMENT_CONFIRMATION)!= null) {
			return requestPreparerGetOrderDetails(orderID, trans);
		} else {
			try {
				Thread.sleep(10000);
				setScopedValue(AsqValueKeys.ASQ_TAMARA_CUSTOMER_PAYMENT_CONFIRMATION, true);
			} catch (InterruptedException e) {
				LOG.error("Exception during confirmation sleep cycle :" + e);
			}
			return HELPER.getPromptResponse("ASQ_TAMARA_PAYMENT_WAIT");
		}
	}

	@Override
	public boolean isOperationApplicable() {
		if (_transactionScope.getValue(AsqValueKeys.TAMARA_RESPONSE) != null) {
			return true;
		}
		return false;
	}

	private IOpResponse requestPreparerGetOrderDetails(String response, IPosTransaction trans) {
		AsqSubmitBnplTamraServiceResponse asqSubmitBnplTamraServiceResponse = new AsqSubmitBnplTamraServiceResponse();
		IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest = new AsqSubmitBnplTamraServiceRequest();
		asqSubmitBnplTamraServiceRequest.setOrder_id(response);
		asqSubmitBnplTamraServiceResponse = getOrderDetails(asqSubmitBnplTamraServiceRequest);
		LOG.info("Successful Execution of GETOrderDetails API i.e., payment completed");
		return validateGetOrderDetailsResponse(asqSubmitBnplTamraServiceResponse);
	}

	private IOpResponse validateGetOrderDetailsResponse(AsqSubmitBnplTamraServiceResponse response) {
		if (null != response && null != response.getErrors()) {
			return handleServiceError(response);

		} else if (null != _transactionScope.getValue(AsqValueKeys.ASQ_TAMARA_PAYMENT_SUCCESS)) {
			LOG.info("Successful Execution of GETOrderDetails API calling payment successful prompt");
			return this.HELPER.getPromptResponse("ASQ_TAMARA_PAYMENT_SUCCESSFULL");
		} else if (null != _transactionScope.getValue(AsqValueKeys.ASQ_TAMARA_PAYMENT_EXPIRED)) {
			LOG.info("GETOrderDetails API calling payment expired/timeout prompt");
			return this.HELPER.getPromptResponse("ASQ_TAMARA_PAYMENT_LINK_EXPIRED");
		}
		return HELPER.completeCurrentChainResponse();
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
		LOG.info("TAMARA REDEEM API::::: " + message);
		return HELPER.getPromptResponse("ASQ_TAMARA_TECHNICAL_ERROR", args);
	}

	/**
	 * This method handles the TAMARA service errors
	 *
	 * @param asqServiceResponse
	 * @return Error Prompts
	 */

	public IOpResponse handleServiceError(AsqSubmitBnplTamraServiceResponse asqServiceResponse) {
		IFormattable[] args = new IFormattable[2];
		LOG.info("Error From TAMARA API::::: " + args[0]);
		LOG.info("Error Message Generated By Xstore based on TAMARA API Response::::: " + asqServiceResponse.getMessage());
		return HELPER.getPromptResponse(asqServiceResponse.getMessage(), args);
	}

	public AsqSubmitBnplTamraServiceResponse getOrderDetails(IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest) {
		boolean success = false;
		AsqSubmitBnplTamraServiceResponse response = new AsqSubmitBnplTamraServiceResponse();
		while (!success) {
			try {
				LOG.info("Calling getOrderDetails API to validate payment status");
				response = (AsqSubmitBnplTamraServiceResponse) tamaraService.get().getOrderDetails(asqSubmitBnplTamraServiceRequest);
				if (response != null && response.getStatus() != null) {
					LOG.info("TAMARA Response status :" + response.getStatus());
					if (response.getStatus().equalsIgnoreCase("fully_captured")) {
						LOG.info("Successfully completed the payment with status fully_captured");
						success = true;
						_transactionScope.setValue(AsqValueKeys.ASQ_TAMARA_PAYMENT_SUCCESS, success);
					} else if (response.getStatus().equalsIgnoreCase("expired")) {
						LOG.info("Payment link has expired");
						success = true;
						_transactionScope.setValue(AsqValueKeys.ASQ_TAMARA_PAYMENT_EXPIRED, success);
					}
				}
				if (!success) {
					Thread.sleep(10000);
				}
			} catch (Exception ex) {
				LOG.debug("Exception during calling getOrderDetails method to check payment status");
			}
		}
		return response;
	}
}
