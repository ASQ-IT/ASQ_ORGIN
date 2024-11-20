/**
 * 
 */
package asq.pos.loyalty.neqaty.tender.op;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyHelper;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyServiceRequest;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyServiceResponse;
import asq.pos.loyalty.neqaty.tender.service.AsqValueKeys;
import asq.pos.loyalty.neqaty.tender.service.IAsqNeqatyService;
import asq.pos.loyalty.neqaty.tender.service.IAsqNeqatyServiceRequest;
import asq.pos.loyalty.neqaty.tender.service.NeqatyMethod;
import dtv.i18n.IFormattable;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trn.IPosTransaction;
import dtv.xst.dao.ttr.ITenderLineItem;

/**
 * @author SA20547171 Void tender for Neqaty
 */
public class AsqNeqatyEarnRefundOp extends Operation {

	private static final Logger LOG = LogManager.getLogger(AsqNeqatyEarnRefundOp.class);

	private String tenderType;

	@Inject
	protected Provider<IAsqNeqatyService> asqNeqatyService;
	
	@Inject
	AsqNeqatyHelper asqNeqatyHelper;

	@Override
	public IOpResponse handleOpExec(IXstEvent paramIXstEvent) {
		String custMobileNumber = getScopedValue(AsqValueKeys.ASQ_NEQATY_MOBILE);
		return refundPoints(custMobileNumber);
	}

	private IOpResponse refundPoints(String custMobileNumber) {
		LOG.debug("Neqaty Inquire OTP Operation service call starts here: ");
		IAsqNeqatyServiceRequest request = new AsqNeqatyServiceRequest();
		request.setAuthenticationKey(System.getProperty("asq.neqaty.auth.key"));
		request.setOperationType("EarnRefund");
		request.setMsisdn(custMobileNumber);
		request.setTid(0);
		request.setMethod(NeqatyMethod.AUTHORIZE);
		request.setTransactionReference(custMobileNumber);
		AsqNeqatyServiceResponse response = (AsqNeqatyServiceResponse) asqNeqatyService.get()
				.callNeqatyService(request);
		LOG.debug("Neqaty Inquire OTP Operation service response here: ");
		if (null != response && 0 != response.getResultCode()) {
			return handleServiceError(response);
		} else if (null == response) {
			return technicalErrorScreen("Service has null response");
		}
		return HELPER.completeResponse();
	}

	public IOpResponse handleServiceError(AsqNeqatyServiceResponse asqServiceResponse) {
		IFormattable[] args = new IFormattable[2];
		args[0] = _formattables.getSimpleFormattable(String.valueOf(asqServiceResponse.getResultCode()));
		args[1] = _formattables.getSimpleFormattable(asqServiceResponse.getResultDescription());
		String errorConstant = asqNeqatyHelper.mapError(asqServiceResponse.getResultCode());
		LOG.info("Error From Neqaty Earn Refund OTP Operation : " + asqServiceResponse.getResultCode() + " - "
				+ asqServiceResponse.getResultDescription());
		LOG.info("Error Message Generated By Xstore based on Neqaty Earn Refund OTP Operation Response: " + errorConstant);
		return HELPER.getPromptResponse(errorConstant, args);
	}

	private IOpResponse technicalErrorScreen(String message) {
		IFormattable[] args = new IFormattable[2];
		args[1] = _formattables.getSimpleFormattable(message);
		LOG.info("Neqaty Earn Refund OTP Operation::::: " + message);
		return HELPER.getPromptResponse("ASQ_NEQATY_TECHNICAL_ERROR", args);
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
		return (tenderLine.getVoid() && 	tenderLine.getTenderId().equalsIgnoreCase(tenderType));
	
	}

}