/**
 *
 */
package asq.pos.loyalty.stc.tender.op;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.stc.tender.AsqStcHelper;
import asq.pos.loyalty.stc.tender.service.AsqSTCErrorDesc;
import asq.pos.loyalty.stc.tender.service.AsqSTCLoyaltyServiceRequest;
import asq.pos.loyalty.stc.tender.service.AsqSTCLoyaltyServiceResponse;
import asq.pos.loyalty.stc.tender.service.IAsqSTCLoyaltyServiceRequest;
import asq.pos.loyalty.stc.tender.service.IAsqSTCLoyaltyTenderService;
import asq.pos.zatca.AsqZatcaConstant;
import dtv.data2.access.DataFactory;
import dtv.i18n.IFormattable;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trn.IPosTransactionProperty;
import dtv.xst.dao.ttr.ITenderLineItem;

/**
 * @author RA20221457
 *
 */

public class AsqSTCRefundRedeemOp extends Operation {

	private static final Logger LOG = LogManager.getLogger(AsqSTCRefundRedeemOp.class);

	/**
	 * This class extends the Xstore Standard form class to handle all actions
	 * related to Mobile number field & STC API calls
	 */

	@Inject
	protected Provider<IAsqSTCLoyaltyTenderService> _asqSTCLoyalityTenderService;

	@Inject
	AsqStcHelper asqStcHelper;
	private String custMobileNumber = "";
	private String tenderType;
	Boolean isRefundRedeem = false;

	/**
	 * This method handles the data operation after submitting the mobile number
	 *
	 * @param
	 * @return
	 */

	@Override
	public IOpResponse handleOpExec(IXstEvent arg0) {
		IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
		custMobileNumber = _transactionScope.getValue(AsqValueKeys.ASQ_MOBILE_NUMBER);
		return stcVoidRefundRedeem(custMobileNumber);
	}

	/**
	 * This method implements the refund service API call by preparing the request
	 * attributes
	 *
	 * @param trans
	 * @return refund redeem submission to STC Service Handler
	 */

	@SuppressWarnings("unused")
	private IOpResponse stcVoidRefundRedeem(String custMobileNumber) {

		IAsqSTCLoyaltyServiceRequest request = new AsqSTCLoyaltyServiceRequest();
		String requestDate = asqStcHelper.getCurrentDateTime();
		LOG.info("Request Date :" + requestDate);
		request.setRequestDate(requestDate);
		request.setMsisdn(Long.parseLong(custMobileNumber.trim()));
		request.setBranchId(System.getProperty("asq.stc.branchid"));
		request.setTerminalId(System.getProperty("asq.stc.terminalid"));
		request.setRefRequestId(_transactionScope.getValue(AsqValueKeys.ASQ_STC_REF_REQUEST_ID));
		request.setRefRequestDate(_transactionScope.getValue(AsqValueKeys.ASQ_STC_REF_REQUEST_DATE));
		request.setPIN(null);
		request.setAmount(null);
		LOG.info("STC API Generate GlobalID method calling from ASQSTCHelper");
		String globalID = asqStcHelper.generateGlobalId();
		LOG.info("STC API Generate GlobalID generated:" + globalID);
		request.setGlobalId(globalID);
		LOG.info("STC API trigger OTP request is prepared :" + request);
		AsqSTCLoyaltyServiceResponse response = (AsqSTCLoyaltyServiceResponse) _asqSTCLoyalityTenderService.get().refundRedeem(request);
		if (null != response) {
			if ("Success".equalsIgnoreCase(response.getDescription())) {
				return this.HELPER.getCompletePromptResponse("ASQ_VOID_SUCCESSFULL");
			}
			if (null != response.getErrors()) {
				return handleServiceError(response);
			} else {
				return technicalErrorScreen("TAMARA API::::: Service has null response");
			}
		}
		LOG.debug("STC refund service Ends here: ");

		return HELPER.completeResponse();
	}

	/**
	 * This method handles the OTP API response
	 *
	 * @param request
	 * @param requestDate
	 *
	 * @param asqServiceResponse
	 * @return Error Prompts
	 */

	private IOpResponse validateResponseAndStoreDataInDB(AsqSTCLoyaltyServiceResponse response, String requestDate, String globalID) {
		IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
		if (null != response && null != response.getErrors() && 0 != response.getErrors().length) {
			return handleServiceError(response);
		} else if (null == response) {
			return technicalErrorScreen("Service has null response");
		}
		if (response.getDescription().equalsIgnoreCase("SUCCESS")) {
			isRefundRedeem = true;
			return this.HELPER.getCompletePromptResponse("ASQ_STC_SUCCESSFULL_REDEEM_REFUND");
		}
		return HELPER.completeResponse();
	}

	/**
	 * This method handles the Trigger OTP API call service errors
	 *
	 * @param asqServiceResponse
	 * @return Error Prompts
	 */

	public IOpResponse handleServiceError(AsqSTCLoyaltyServiceResponse asqServiceResponse) {
		IFormattable[] args = new IFormattable[2];
		AsqSTCErrorDesc error = asqServiceResponse.getErrors()[0];
		args[0] = _formattables.getSimpleFormattable(error.getCode());
		args[1] = _formattables.getSimpleFormattable(error.getDescription());
		String errorConstant = asqStcHelper.mapError(error.getCode());
		LOG.info("Error From STC OTP API::::: " + error.getCode() + " - " + error.getDescription());
		LOG.info("Error Message Generated By Xstore based on STC Refund API Response::::: " + errorConstant);
		return HELPER.getPromptResponse(errorConstant, args);
	}

	/**
	 * This method return technical error screen
	 *
	 * @param asqServiceResponse
	 * @return Error Prompts
	 */

	private IOpResponse technicalErrorScreen(String message) {
		IFormattable[] args = new IFormattable[2];
		args[1] = _formattables.getSimpleFormattable(message);
		LOG.info("STC OTP API::::: " + message);
		return HELPER.getPromptResponse("ASQ_STC_TECHNICAL_ERROR", args);
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

	public void saveSTCRedeemResponseToDB(String globalID, String date) {
		IPosTransactionProperty newTrxProps = DataFactory.createObject(IPosTransactionProperty.class);
		IRetailTransaction originalPosTrx = (IRetailTransaction) this._transactionScope.getTransaction();
		newTrxProps.setType("STRING");
		newTrxProps.setPropertyValue(globalID);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date requestDate = null;
		try {
			requestDate = format.parse(date);
		} catch (ParseException e) {
			LOG.error("Exception while updating Trans_P Date conversion");
		}
		newTrxProps.setDateValue(requestDate);
		newTrxProps.setPropertyCode(AsqZatcaConstant.STC_SUCCESS_REFUND_REDEEM_RESPONSE);
		originalPosTrx.addPosTransactionProperty(newTrxProps);

	}
}
