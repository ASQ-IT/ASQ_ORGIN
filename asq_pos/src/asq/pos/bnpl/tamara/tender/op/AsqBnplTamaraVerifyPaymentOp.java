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
import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraAmountObj;
import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraItemObj;
import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceRequest;
import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceResponse;
import asq.pos.bnpl.tamara.tender.service.IAsqBnplTamaraServices;
import asq.pos.bnpl.tamara.tender.service.IAsqSubmitBnplTamraServiceRequest;
import asq.pos.common.AsqValueKeys;
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
import dtv.xst.dao.trn.IPosTransactionProperty;
import dtv.xst.dao.ttr.ITenderLineItem;
import dtv.pos.framework.action.XstDefaultAction;

/**
 * @author RA20221457
 *
 */
public class AsqBnplTamaraVerifyPaymentOp extends Operation {

	private static final Logger LOG = LogManager.getLogger(AsqBnplTamaraVerifyPaymentOp.class);

	@Inject
	protected Provider<IAsqBnplTamaraServices> tamaraService;

	@Override
	public IOpResponse handleOpExec(IXstEvent paramIXstEvent) {
		IPosTransaction trans = (IPosTransaction) this._transactionScope.getTransaction();
		if (paramIXstEvent instanceof XstDataAction && ((XstDataAction) paramIXstEvent).getActionName() != null
				&& ((XstDataAction) paramIXstEvent).getActionName().toString().equals("Yes")) {
			AsqSubmitBnplTamraServiceResponse response = requestPreparerForCancelSessionService(trans);// API cancel// user want to// void
			return HELPER.getPromptResponse("ASQ_TAMARA_CANCEL_SESSION");
		} else if (paramIXstEvent instanceof XstDataAction && ((XstDataAction) paramIXstEvent).getActionName() != null
				&& ((XstDataAction) paramIXstEvent).getActionName().toString().equals("Yes")) {
			AsqSubmitBnplTamraServiceResponse response = requestPreparerForRefundService(trans);
			response.getMessage();
			return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("VOID_REFUND_TENDER"));
		} else if (paramIXstEvent instanceof XstDataAction && ((XstDataAction) paramIXstEvent).getActionName() != null
				&& ((XstDataAction) paramIXstEvent).getActionName().toString().equals("No")) {
			return HELPER.completeResponse();
		} else {
			List<ITenderLineItem> tenderLineItemList = trans.getLineItems(ITenderLineItem.class);
			String tenderId = tenderLineItemList.get(0).getTenderId();
			if (tenderId.equals("TAMARA")) { // need to check payment status false here //scope value
				return HELPER.getPromptResponse("ASQ_TAMARA_CANCEL_SESSION");
			} else if (tenderId.equals("TAMARA")) { // payment status true here
				return HELPER.getPromptResponse("ASQ_TAMARA_REFUND_INITIATE");
			}
		}
		return HELPER.completeResponse();
	}

	private AsqSubmitBnplTamraServiceResponse requestPreparerForRefundService(IPosTransaction trans) {

		AsqSubmitBnplTamraServiceResponse asqSubmitBnplTamraServiceResponse;
		IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest = new AsqSubmitBnplTamraServiceRequest();
		String orderID = this._transactionScope.getValue(AsqValueKeys.ASQ_TAMARA_ORDERID);
		asqSubmitBnplTamraServiceRequest.setComment(AsqZatcaConstant.ASQ_TAMARA_TRANSACTION_COMMENT);
		AsqBnplTamaraAmountObj asqBnplTamaraAmountObj = new AsqBnplTamaraAmountObj();
		asqBnplTamaraAmountObj.setAmount(trans.getAmountTendered());
		 asqBnplTamaraAmountObj.setCurrency("SAR");// need to correct this
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
}
