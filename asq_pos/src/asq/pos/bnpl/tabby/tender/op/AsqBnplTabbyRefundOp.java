package asq.pos.bnpl.tabby.tender.op;

import javax.inject.Inject;
import javax.inject.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyPaymentObj;
import asq.pos.bnpl.tabby.tender.service.AsqSubmitBnplTabbyServiceRequest;
import asq.pos.bnpl.tabby.tender.service.AsqSubmitBnplTabbyServiceResponse;
import asq.pos.bnpl.tabby.tender.service.IAsqBnplTabbyServices;
import asq.pos.bnpl.tabby.tender.service.IAsqSubmitBnplTabbyServiceRequest;
import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyServiceRequest;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyServiceResponse;
import asq.pos.loyalty.neqaty.tender.service.IAsqNeqatyService;
import asq.pos.loyalty.neqaty.tender.service.IAsqNeqatyServiceRequest;
import asq.pos.loyalty.neqaty.tender.service.NeqatyMethod;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trn.IPosTransaction;
import dtv.xst.dao.ttr.ITenderLineItem;

/**
 * @author RA20221457
 *
 */
public class AsqBnplTabbyRefundOp extends Operation {

	private static final Logger LOG = LogManager.getLogger(AsqBnplTabbyRefundOp.class);

	private String tenderType;

	@Inject
	protected Provider<IAsqBnplTabbyServices> asqBnplTabbyServices;

	@Override
	public IOpResponse handleOpExec(IXstEvent paramIXstEvent) {
		IPosTransaction trans = (IPosTransaction) this._transactionScope.getTransaction();
		return tabbyVoidRefundTrx(trans);
	}

	private IOpResponse tabbyVoidRefundTrx(IPosTransaction orgTranx) {
		IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest = new AsqSubmitBnplTabbyServiceRequest();
		AsqSubmitBnplTabbyServiceResponse asqSubmitBnplTabbyServiceResponse;
		AsqBnplTabbyPaymentObj payment = new AsqBnplTabbyPaymentObj();
		
		 Boolean paymentStatus = _transactionScope.getValue(AsqValueKeys.ASQ_TABBY_PAYMENT_SUCCESS);
		if (paymentStatus) {
			LOG.debug("Tabby refund service call starts here: ");
			payment.setId(orgTranx.getProperty("ASQ_TABBY_PAYMENT_ID").getStringValue());
			asqSubmitBnplTabbyServiceRequest.setPayment(payment);
			asqSubmitBnplTabbyServiceRequest.setAmount(orgTranx.getAmountTendered().toString());
			asqSubmitBnplTabbyServiceResponse = refundPayment(asqSubmitBnplTabbyServiceRequest);
			LOG.debug("Tabby refund service Ends here: ");
		} else {
			LOG.debug("Tabby cancel session service Ends here: ");
			asqSubmitBnplTabbyServiceRequest.setId(orgTranx.getProperty("ASQ_TABBY_PAYMENT_ID").getStringValue());
			asqSubmitBnplTabbyServiceResponse = cancelSession(asqSubmitBnplTabbyServiceRequest);
			_transactionScope.clearValue(AsqValueKeys.ASQ_TABBY_PAYMENT_SUCCESS);
			LOG.debug("Tabby cancel session service Ends here: ");
		}
		return HELPER.completeResponse();
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

	public AsqSubmitBnplTabbyServiceResponse refundPayment(
			IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest) {
		AsqSubmitBnplTabbyServiceResponse response = new AsqSubmitBnplTabbyServiceResponse();
		try {
			response = (AsqSubmitBnplTabbyServiceResponse) asqBnplTabbyServices.get()
					.refundPayment(asqSubmitBnplTabbyServiceRequest);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}
	
	public AsqSubmitBnplTabbyServiceResponse cancelSession(IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest) {
		AsqSubmitBnplTabbyServiceResponse response = new AsqSubmitBnplTabbyServiceResponse();
		try {
			 response = (AsqSubmitBnplTabbyServiceResponse) asqBnplTabbyServices.get().cancelSession(asqSubmitBnplTabbyServiceRequest);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}
}
