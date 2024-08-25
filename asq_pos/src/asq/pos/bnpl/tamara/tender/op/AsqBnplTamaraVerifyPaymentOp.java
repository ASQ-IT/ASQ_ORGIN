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
import dtv.i18n.IFormattable;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trl.IRetailTransactionLineItem;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import dtv.xst.dao.trn.IPosTransaction;
import dtv.xst.dao.trn.IPosTransactionProperty;
import dtv.xst.dao.ttr.ITenderLineItem;

/**
 * @author RA20221457
 *
 */
public class AsqBnplTamaraVerifyPaymentOp extends Operation {

	private static final Logger LOG = LogManager.getLogger(AsqBnplTamaraVerifyPaymentOp.class);

	@Inject
	protected Provider<IAsqBnplTamaraServices> tamaraService;

	@Override
	public IOpResponse handleOpExec(IXstEvent arg0) {

		IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
		if (getScopedValue(AsqValueKeys.ASQ_TAMARA_PAYMENT_SUCCESS)) {
			List<ITenderLineItem> tenderLineItemList = trans.getLineItems(ITenderLineItem.class);
			tenderLineItemList.get(0).getTenderId().equals(tenderLineItemList);
			return HELPER.getPromptResponse("ASQ_TAMARA_REFUND_INITIATE");
		} else {
			return HELPER.getPromptResponse("ASQ_TAMARA_CANCEL_SESSION");
		}
	}

	public IOpResponse handlePromptResponse(IXstEvent argVar1) {

		IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
		if (argVar1.getName().equalsIgnoreCase("REFUND")) {
			AsqSubmitBnplTamraServiceResponse response = requestPreparerForRefundService(trans);
			//respoonse is OK. 
			//handle response db update if required
			return this.HELPER.completeResponse();
			
		} else if (argVar1.getName().equalsIgnoreCase("CANCEL")) {
			AsqSubmitBnplTamraServiceResponse response = requestPreparerForCancelSessionService(trans);
			//handle response db update if required
			//response is yes continue to void the transaction
		}
		return this.HELPER.completeResponse();
	}

	private AsqSubmitBnplTamraServiceResponse requestPreparerForRefundService(IRetailTransaction trans) {

		AsqSubmitBnplTamraServiceResponse asqSubmitBnplTamraServiceResponse;
		IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest = new AsqSubmitBnplTamraServiceRequest();
		IPosTransactionProperty newTrxProps = null;
		String orderID = (String) newTrxProps.getPropertyValue();// need to check null condition
		// asqSubmitBnplTamraServiceRequest.setOrder_id("1ba09aa0-1f11-464f-a13e-506c5f951cea");
		asqSubmitBnplTamraServiceRequest.setOrder_id("orderID");
		asqSubmitBnplTamraServiceRequest.setComment("testing");
		AsqBnplTamaraAmountObj asqBnplTamaraAmountObj = new AsqBnplTamaraAmountObj();
		asqBnplTamaraAmountObj.setAmount(trans.getAmountTendered());
		// asqBnplTamaraAmountObj.setCurrency("SAR");
		asqBnplTamaraAmountObj.setCurrency(trans.getRetailTransactionLineItems().get(0).getCurrencyId());
		return asqSubmitBnplTamraServiceResponse = simplifiedRefunds(asqSubmitBnplTamraServiceRequest);
	}

	private AsqSubmitBnplTamraServiceResponse requestPreparerForCancelSessionService(IRetailTransaction trans) {

		AsqSubmitBnplTamraServiceResponse asqSubmitBnplTamraServiceResponse;
		IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest = new AsqSubmitBnplTamraServiceRequest();
		asqSubmitBnplTamraServiceRequest.setCheckout_id("eaa11dc3-8ef0-41dc-acca-13b003a0a11c");// need to get from trx
																								// property
		asqSubmitBnplTamraServiceRequest.setOrder_id("1ba09aa0-1f11-464f-a13e-506c5f951cea");// need to get from trx
																								// property
		asqSubmitBnplTamraServiceRequest.setStore_code("Store A");
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
