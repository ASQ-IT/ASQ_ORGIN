package asq.pos.bnpl.tabby.tender.op;

import javax.inject.Inject;
import javax.inject.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyErrorDesc;
import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyPaymentObj;
import asq.pos.bnpl.tabby.tender.service.AsqSubmitBnplTabbyServiceRequest;
import asq.pos.bnpl.tabby.tender.service.AsqSubmitBnplTabbyServiceResponse;
import asq.pos.bnpl.tabby.tender.service.IAsqBnplTabbyServices;
import asq.pos.bnpl.tabby.tender.service.IAsqSubmitBnplTabbyServiceRequest;
import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceResponse;
import asq.pos.bnpl.tamara.tender.service.AsqTamaraErrorDesc;
import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyServiceRequest;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyServiceResponse;
import asq.pos.loyalty.neqaty.tender.service.IAsqNeqatyService;
import asq.pos.loyalty.neqaty.tender.service.IAsqNeqatyServiceRequest;
import asq.pos.loyalty.neqaty.tender.service.NeqatyMethod;
import asq.pos.loyalty.stc.tender.AsqStcHelper;
import dtv.i18n.IFormattable;
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
	AsqStcHelper asqStcHelper;

	@Inject
	protected Provider<IAsqBnplTabbyServices> asqBnplTabbyServices;

	@Override
	public IOpResponse handleOpExec(IXstEvent paramIXstEvent) {
		IPosTransaction trans = (IPosTransaction) this._transactionScope.getTransaction();
		return tabbyVoidRefundTrx(trans);
	}

	@SuppressWarnings("unused")
	private IOpResponse tabbyVoidRefundTrx(IPosTransaction orgTranx) {
		IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest = new AsqSubmitBnplTabbyServiceRequest();
		AsqSubmitBnplTabbyServiceResponse response;
		AsqBnplTabbyPaymentObj payment = new AsqBnplTabbyPaymentObj();
		
		 Boolean paymentStatus = _transactionScope.getValue(AsqValueKeys.ASQ_TABBY_PAYMENT_SUCCESS);
		if (paymentStatus) {
			LOG.debug("Tabby refund service call starts here: ");
			payment.setId(orgTranx.getProperty("ASQ_TABBY_PAYMENT_ID").getStringValue());
			asqSubmitBnplTabbyServiceRequest.setPayment(payment);
			asqSubmitBnplTabbyServiceRequest.setAmount(orgTranx.getAmountTendered().toString());
			response = refundPayment(asqSubmitBnplTabbyServiceRequest);
			if(response.getStatus().equalsIgnoreCase("CLOSED")) {
				return this.HELPER.getCompletePromptResponse("ASQ_VOID_SUCCESSFULL");
			}
			else if (null != response && null != response.getError()) {
				return handleServiceError(response);
			} else if (null == response) {
				return technicalErrorScreen("TABBY API::::: Service has null response");
			}
			LOG.debug("Tabby refund service Ends here: ");
		} 
		 else {
			LOG.debug("Tabby cancel session service Ends here: ");
			asqSubmitBnplTabbyServiceRequest.setId(orgTranx.getProperty("ASQ_TABBY_PAYMENT_ID").getStringValue());
			response = cancelSession(asqSubmitBnplTabbyServiceRequest);
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
	 * This method handles the TAMARA service errors
	 * 
	 * @param asqServiceResponse
	 * @return Error Prompts
	 */

	public IOpResponse handleServiceError(AsqSubmitBnplTabbyServiceResponse asqServiceResponse) {
		IFormattable[] args = new IFormattable[1];
		AsqBnplTabbyErrorDesc error = asqServiceResponse.getError();
		args[0] = _formattables.getSimpleFormattable(error.getError());
		String errorConstant = asqStcHelper.mapTabbyErrors(asqServiceResponse.getStatus());
		LOG.info("Error From TAMARA API::::: " + args[0]);
		LOG.info("Error Message Generated By Xstore based on TABBY API Response::::: " + error.getDescription());
		return HELPER.getPromptResponse(errorConstant, args);
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
