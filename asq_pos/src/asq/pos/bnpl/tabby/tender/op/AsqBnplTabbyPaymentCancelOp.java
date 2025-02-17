package asq.pos.bnpl.tabby.tender.op;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.bnpl.tabby.tender.service.AsqSubmitBnplTabbyServiceRequest;
import asq.pos.bnpl.tabby.tender.service.AsqSubmitBnplTabbyServiceResponse;
import asq.pos.bnpl.tabby.tender.service.IAsqBnplTabbyServices;
import asq.pos.bnpl.tabby.tender.service.IAsqSubmitBnplTabbyServiceRequest;
import asq.pos.common.AsqValueKeys;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trn.IPosTransaction;

/**
 * @author RA20221457
 *
 */
public class AsqBnplTabbyPaymentCancelOp extends Operation {
	private static final Logger LOG = LogManager.getLogger(AsqBnplTabbyPaymentCancelOp.class);

	@Inject
	protected Provider<IAsqBnplTabbyServices> asqBnplTabbyServices;

	@Override
	public IOpResponse handleOpExec(IXstEvent paramIXstEvent) {
		IPosTransaction trans = this._transactionScope.getTransaction();
		return tabbyPaymentCancel(trans);
	}

	private IOpResponse tabbyPaymentCancel(IPosTransaction orgTranx) {
		IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest = new AsqSubmitBnplTabbyServiceRequest();
		LOG.debug("Tabby cancel session service Starts here: ");
		asqSubmitBnplTabbyServiceRequest.setId(orgTranx.getProperty("ASQ_TABBY_PAYMENT_ID").getStringValue());
		AsqSubmitBnplTabbyServiceResponse asqSubmitBnplTabbyServiceResponse = cancelSession(asqSubmitBnplTabbyServiceRequest);
		_transactionScope.clearValue(AsqValueKeys.ASQ_TABBY_PAYMENT_SUCCESS);
		LOG.debug("Tabby cancel session service Ends here: ");
		return HELPER.completeResponse();
	}

	public AsqSubmitBnplTabbyServiceResponse cancelSession(IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest) {
		AsqSubmitBnplTabbyServiceResponse response = new AsqSubmitBnplTabbyServiceResponse();
		try {
			response = (AsqSubmitBnplTabbyServiceResponse) asqBnplTabbyServices.get().cancelSession(asqSubmitBnplTabbyServiceRequest);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

}
