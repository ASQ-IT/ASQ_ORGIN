/**
 * 
 */
package asq.pos.bnpl.tamara.tender.op;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyPaymentObj;
import asq.pos.bnpl.tabby.tender.service.AsqSubmitBnplTabbyServiceRequest;
import asq.pos.bnpl.tabby.tender.service.AsqSubmitBnplTabbyServiceResponse;
import asq.pos.bnpl.tabby.tender.service.IAsqSubmitBnplTabbyServiceRequest;
import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraAmountObj;
import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraItemObj;
import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceRequest;
import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceResponse;
import asq.pos.bnpl.tamara.tender.service.AsqTamaraErrorDesc;
import asq.pos.bnpl.tamara.tender.service.IAsqBnplTamaraServices;
import asq.pos.bnpl.tamara.tender.service.IAsqSubmitBnplTamraServiceRequest;
import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.stc.tender.AsqStcHelper;
import asq.pos.zatca.AsqZatcaConstant;
import dtv.i18n.IFormattable;
import dtv.pos.common.OpChainKey;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.action.XstDataAction;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trl.IRetailTransactionLineItem;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import dtv.xst.dao.trn.IPosTransaction;
import dtv.xst.dao.ttr.ITenderLineItem;
import dtv.pos.framework.action.XstDefaultAction;

/**
 * @author RA20221457
 *
 */
public class AsqBnplTamaraRefundOp extends Operation {

	private static final Logger LOG = LogManager.getLogger(AsqBnplTamaraRefundOp.class);
	
	private String tenderType;

	@Inject
	AsqStcHelper asqStcHelper;
	
	@Inject
	protected Provider<IAsqBnplTamaraServices> tamaraService;

	@Override
	public IOpResponse handleOpExec(IXstEvent paramIXstEvent) {
		IPosTransaction trans = (IPosTransaction) this._transactionScope.getTransaction();
		return tamaraVoidRefund(trans);
	}

	@SuppressWarnings("unused")
	private IOpResponse tamaraVoidRefund(IPosTransaction orgTranx) {
		IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamaraServiceRequest = new AsqSubmitBnplTamraServiceRequest();
		AsqSubmitBnplTamraServiceResponse asqSubmitBnplTamaraServiceResponse;
		AsqBnplTamaraAmountObj payment = new AsqBnplTamaraAmountObj();
		
		 Boolean paymentStatus = _transactionScope.getValue(AsqValueKeys.ASQ_TAMARA_PAYMENT_SUCCESS);
		if (paymentStatus) {
			LOG.debug("Tamara refund service call starts here: ");
			AsqSubmitBnplTamraServiceResponse response = requestPreparerForRefundService(orgTranx);
			if(response.getStatus().equalsIgnoreCase("fully_refunded")) {
				return this.HELPER.getCompletePromptResponse("ASQ_VOID_SUCCESSFULL");
			}
			else if (null != response && null != response.getErrors()) {
				return handleServiceError(response);
			} else if (null == response) {
				return technicalErrorScreen("TAMARA API::::: Service has null response");
			}
			LOG.debug("Tamara refund service Ends here: ");
		} else {
			LOG.debug("Tamara cancel session service Ends here: ");
			AsqSubmitBnplTamraServiceResponse response = requestPreparerForCancelSessionService(orgTranx);
			LOG.debug("Tamara cancel session service Ends here: ");
		}
		return HELPER.completeResponse();
}
	
	private AsqSubmitBnplTamraServiceResponse requestPreparerForRefundService(IPosTransaction trans) {

		AsqSubmitBnplTamraServiceResponse asqSubmitBnplTamraServiceResponse;
		IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest = new AsqSubmitBnplTamraServiceRequest();
		asqSubmitBnplTamraServiceRequest.setOrder_id(this._transactionScope.getValue(AsqValueKeys.ASQ_TAMARA_ORDERID));
		asqSubmitBnplTamraServiceRequest.setComment(AsqZatcaConstant.ASQ_TAMARA_TRANSACTION_COMMENT);
		AsqBnplTamaraAmountObj asqBnplTamaraAmountObj = new AsqBnplTamaraAmountObj();
		asqBnplTamaraAmountObj.setAmount(trans.getAmountTendered());
		//asqBnplTamaraAmountObj.setCurrency("SAR");
		asqSubmitBnplTamraServiceRequest.setTotal_amount(asqBnplTamaraAmountObj);
		//asqBnplTamaraAmountObj.setCurrency(trans.getRetailTransactionLineItems().get(0).getCurrencyId());
		return asqSubmitBnplTamraServiceResponse = simplifiedRefunds(asqSubmitBnplTamraServiceRequest);
	}

	private AsqSubmitBnplTamraServiceResponse requestPreparerForCancelSessionService(IPosTransaction trans) {

		AsqSubmitBnplTamraServiceResponse asqSubmitBnplTamraServiceResponse;
		String orderID = this._transactionScope.getValue(AsqValueKeys.ASQ_TAMARA_ORDERID);
		String checkoutID = this._transactionScope.getValue(AsqValueKeys.ASQ_TAMARA_CHECKOUTID);
		IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest = new AsqSubmitBnplTamraServiceRequest();
		asqSubmitBnplTamraServiceRequest.setOrder_id(orderID);
		asqSubmitBnplTamraServiceRequest.setCheckout_id(checkoutID);
		asqSubmitBnplTamraServiceRequest.setStore_code(AsqZatcaConstant.ASQ_TAMARA_STORE_CODE_DEFAULT);
		return asqSubmitBnplTamraServiceResponse = cancelSession(asqSubmitBnplTamraServiceRequest);
	}

	public AsqSubmitBnplTamraServiceResponse simplifiedRefunds(
			IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest) {
		AsqSubmitBnplTamraServiceResponse response = new AsqSubmitBnplTamraServiceResponse();
		try {
			response = (AsqSubmitBnplTamraServiceResponse) tamaraService.get()
					.simplifiedRefunds(asqSubmitBnplTamraServiceRequest);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	public AsqSubmitBnplTamraServiceResponse cancelSession(
			IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest) {
		AsqSubmitBnplTamraServiceResponse response = new AsqSubmitBnplTamraServiceResponse();
		try {
			response = (AsqSubmitBnplTamraServiceResponse) tamaraService.get()
					.cancelSession(asqSubmitBnplTamraServiceRequest);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
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
		IFormattable[] args = new IFormattable[1];
		AsqTamaraErrorDesc error = asqServiceResponse.getErrors();
		args[0] = _formattables.getSimpleFormattable(error.getError());
		String errorConstant = asqStcHelper.mapTamaraErrors(asqServiceResponse.getStatus());
		LOG.info("Error From TAMARA API::::: " + args[0]);
		LOG.info("Error Message Generated By Xstore based on TAMARA API Response::::: " + error.getMessage());
		return HELPER.getPromptResponse(errorConstant, args);
	}

}
