/**
 * 
 */
package asq.pos.bnpl.tamara.tender.op;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraAmountObj;
import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceRequest;
import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceResponse;
import asq.pos.bnpl.tamara.tender.service.IAsqBnplTamaraServices;
import asq.pos.bnpl.tamara.tender.service.IAsqSubmitBnplTamraServiceRequest;
import asq.pos.common.AsqValueKeys;
import asq.pos.zatca.AsqZatcaConstant;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trn.IPosTransaction;

/**
 * @author RA20221457
 *
 */
public class AsqBnplTamaraPaymentCancelOp extends Operation{
	private static final Logger LOG = LogManager.getLogger(AsqBnplTamaraPaymentCancelOp.class);
	
	@Inject
	protected Provider<IAsqBnplTamaraServices> tamaraService;

	@Override
	public IOpResponse handleOpExec(IXstEvent paramIXstEvent) {
		IPosTransaction trans = (IPosTransaction) this._transactionScope.getTransaction();
		return tamaraPaymentCancel(trans);
	}
	
	private IOpResponse tamaraPaymentCancel(IPosTransaction orgTranx) {
		IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamaraServiceRequest = new AsqSubmitBnplTamraServiceRequest();
		AsqSubmitBnplTamraServiceResponse asqSubmitBnplTamaraServiceResponse;
		AsqBnplTamaraAmountObj payment = new AsqBnplTamaraAmountObj();
			LOG.debug("Tamara cancel session service Starts here: ");
			AsqSubmitBnplTamraServiceResponse response = requestPreparerForCancelSessionService(orgTranx);
			LOG.debug("Tamara cancel session service Ends here: ");
		
		return HELPER.completeResponse();
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
