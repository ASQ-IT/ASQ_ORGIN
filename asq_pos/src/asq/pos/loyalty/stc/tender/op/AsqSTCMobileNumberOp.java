/**
 * 
 */
package asq.pos.loyalty.stc.tender.op;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import asq.pos.bnpl.tamara.tender.op.AsqBnplTamaraEditModel;
import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.stc.tender.AsqStcHelper;
import asq.pos.loyalty.stc.tender.service.AsqSTCErrorDesc;
import asq.pos.loyalty.stc.tender.service.AsqSTCLoyaltyServiceRequest;
import asq.pos.loyalty.stc.tender.service.AsqSTCLoyaltyServiceResponse;
import asq.pos.loyalty.stc.tender.service.IAsqSTCLoyaltyServiceRequest;
import asq.pos.loyalty.stc.tender.service.IAsqSTCLoyaltyTenderService;
import dtv.i18n.IFormattable;
import dtv.pos.common.OpChainKey;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.framework.validation.ValidationResultList;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.validation.IValidationResult;
import dtv.pos.iframework.validation.IValidationResultList;
import dtv.pos.iframework.validation.SimpleValidationResult;
import dtv.util.StringUtils;
import dtv.xst.dao.crm.IParty;
import dtv.xst.dao.crm.impl.PartyModel;
import dtv.xst.dao.trl.IRetailTransaction;

/**
 * @author RA20221457
 *
 */

public class AsqSTCMobileNumberOp extends AbstractFormOp<AsqSTCMobileNumberEditModel> {

	private static final Logger LOG = LogManager.getLogger(AsqSTCMobileNumberOp.class);

	/**
	 * This class extends the Xstore Standard form class to handle all actions
	 * related to Mobile number field & STC API calls
	 */

	@Inject
	protected Provider<IAsqSTCLoyaltyTenderService> _asqSTCLoyalityTenderService;

	@Inject
	AsqStcHelper asqStcHelper;
	private String custMobileNumber = "";

	@Override
	protected AsqSTCMobileNumberEditModel createModel() {
		return new AsqSTCMobileNumberEditModel();
	}

	@Override
	protected String getFormKey() {
		return "ASQ_CAP_CUST_MOBILE_NO";
	}

	/**
	 * This method checks whether customer is linked to transaction or not
	 * 
	 * @param
	 * @return
	 */
	protected IOpResponse handleInitialState() {
		AsqSTCMobileNumberEditModel editModel = getModel();
		try {
			IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
			if (trans != null && trans.getCustomerParty() != null && trans.getTransactionTypeCode().equalsIgnoreCase("RETAIL_SALE")){
				LOG.info("STC API Mobile number form execution Customer is Linked to Transaction:");
				IParty argParty = trans.getCustomerParty();
				custMobileNumber = argParty.getTelephone3();
				editModel.setCustMobileNumber(custMobileNumber);
				_transactionScope.setValue(AsqValueKeys.ASQ_MOBILE_NUMBER, custMobileNumber);
				return super.handleInitialState();
			}
		} catch (Exception ex) {
			LOG.info(
					"STC API Mobile number form execution exception customer is not available in the transaction and Trans object is null:");
		}
		return super.handleInitialState();
	}

	/**
	 * This method handles the data operation after submitting the mobile number
	 * 
	 * @param
	 * @return
	 */
	protected IOpResponse handleDataAction(IXstDataAction argAction) {
		if (XstDataActionKey.ACCEPT.equals(argAction.getActionKey())) {
			IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
			AsqSTCMobileNumberEditModel editModel = this.getModel();
			IValidationResultList results = validateForm(editModel);
			if (!results.isValid()) {
				return super.handleDataAction(argAction);
			}
			if (editModel.getCustMobileNumber() != null) {
				custMobileNumber = editModel.getCustMobileNumber();
				_transactionScope.setValue(AsqValueKeys.ASQ_MOBILE_NUMBER, custMobileNumber);
				LOG.debug("STC API Mobile number captured :" + custMobileNumber);
			}
			if (custMobileNumber != null && !custMobileNumber.equals("")) {
				LOG.info("Process of STC tender starts here");
				if (null != trans.getCustomerParty() && !(this._transactionScope
						.getValue(AsqValueKeys.ASQ_MOBILE_NUMBER).equals(custMobileNumber))) {
					IParty info = trans.getCustomerParty();
					info.setTelephone3(custMobileNumber);
					_transactionScope.setValue(AsqValueKeys.ASQ_MOBILE_NUMBER, custMobileNumber);
					LOG.info(
							"STC API setting updated customer mobile number to transaction, this will be udpated once the transaciton is completed");
				}
			} else if (custMobileNumber == null || custMobileNumber.equals("")) {
				LOG.debug("STC API customer mobile number field is null :");
				return super.handleDataAction(argAction);
			}
			LOG.info("STC API Trigger OTP service call starts here: ");
			return requestPreparerForTriggerOTP(trans);

		}
		LOG.info("Action key is not equal to ACCEPT, rolling back to Sale Screen :" + argAction.getActionKey());
		return this.HELPER.getOpChainRollBackRequest();
	}

	/**
	 * This method implements the Trigger OTP service API call by preparing the
	 * request attributes
	 * 
	 * @param trans
	 * @return triggerOTPRequest submission to STC Service Handler
	 */
	private IOpResponse requestPreparerForTriggerOTP(IRetailTransaction trans) {

		IAsqSTCLoyaltyServiceRequest request = new AsqSTCLoyaltyServiceRequest();
		ZoneId ksaZone = ZoneId.of("Asia/Riyadh");
		ZonedDateTime ksaDateTime = ZonedDateTime.now(ksaZone);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		String requestDate = ksaDateTime.format(formatter);
		request.setMsisdn(Long.parseLong(custMobileNumber.trim()));
		request.setBranchId(System.getProperty("asq.stc.branchid"));
		request.setTerminalId(System.getProperty("asq.stc.terminalid"));
		request.setRequestDate(requestDate);
		LOG.info("Request Date :" + requestDate);
		request.setPIN(null);
		request.setAmount(null);
		LOG.info("STC API Generate GlobalID method calling from ASQSTCHelper");
		String globalID = asqStcHelper.generateGlobalId();
		LOG.info("STC API Generate GlobalID generated:" + globalID);
		request.setGlobalId(globalID);
		LOG.info("STC API trigger OTP request is prepared :" + request);
		AsqSTCLoyaltyServiceResponse response = (AsqSTCLoyaltyServiceResponse) _asqSTCLoyalityTenderService.get()
				.triggerOTPRequest(request);
		LOG.info("STC API Trigger OTP returns service response here: ");
		return validateResponseAndStoreDataInDB(response, trans, requestDate, globalID, null);
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
	private IOpResponse validateResponseAndStoreDataInDB(AsqSTCLoyaltyServiceResponse response,
			IRetailTransaction trans, String requestDate, String globalID, String earnPoints) {
		if (null != response && null != response.getErrors() && 0 != response.getErrors().length) {
			return handleServiceError(response);
		} else if (null == response) {
			return technicalErrorScreen("Service has null response");
		}
		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_STC_OTP"));
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
		LOG.info("Error Message Generated By Xstore based on STC OTP API Response::::: " + errorConstant);
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
}
