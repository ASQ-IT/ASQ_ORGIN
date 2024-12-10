/**
 * 
 */
package asq.pos.loyalty.mokafaa.tender.op;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.mokafaa.tender.service.AsqMokafaaHelper;
import asq.pos.loyalty.mokafaa.tender.service.AsqMokafaaLoyaltyServiceRequest;
import asq.pos.loyalty.mokafaa.tender.service.AsqMokafaaLoyaltyServiceResponse;
import asq.pos.loyalty.mokafaa.tender.service.IAsqMokafaaLoyaltyServiceRequest;
import asq.pos.loyalty.mokafaa.tender.service.IAsqMokafaaLoyaltyTenderService;
import dtv.i18n.IFormattable;
import dtv.pos.common.OpChainKey;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.framework.op.OpState;
import dtv.pos.framework.scope.TransactionScope;
import dtv.pos.framework.validation.ValidationResultList;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.op.IOpState;
import dtv.pos.iframework.validation.IValidationResult;
import dtv.pos.iframework.validation.IValidationResultList;
import dtv.pos.iframework.validation.SimpleValidationResult;
import dtv.util.StringUtils;
import dtv.xst.dao.crm.IParty;
import dtv.xst.dao.crm.IPartyTelephone;
import dtv.xst.dao.trl.IRetailTransaction;

/**
 * @author RA20221457
 *
 */

public class AsqMokafaaMobileNumberOp extends AbstractFormOp<AsqMokafaaMobileNumberEditModel> {

	private static final Logger LOG = LogManager.getLogger(AsqMokafaaMobileNumberOp.class);
	
	/**
	 * This class extends the Xstore Standard form class to handle all actions
	 * related to Mobile number field & Mokafaa API calls
	 */

	@Inject
	protected Provider<IAsqMokafaaLoyaltyTenderService> _asqMokafaaLoyalityTenderService;

	@Inject
	AsqMokafaaHelper asqMokafaaHelper;

	private String custMobileNumber = "";
	private String loyalityCicNo = null;
	private boolean isAuthCallFailed = false;

	@Override
	protected AsqMokafaaMobileNumberEditModel createModel() {
		return new AsqMokafaaMobileNumberEditModel();
	}

	@Override
	protected String getFormKey() {
		return "ASQ_MOKAFAA_CUST_MOBILE_NO";
	}

	/**
	 * This method checks whether customer is linked to transaction or not
	 * 
	 * @param
	 * @return
	 */

	@Override
	protected IOpResponse handleInitialState() {
		AsqMokafaaMobileNumberEditModel editModel = getModel();
		try {
			IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
			if (trans != null && trans.getCustomerParty() != null)// Transactions Typecode condition to be included
			{
				LOG.info("Mokafaa API Mobile number form execution Customer is Linked to Transaction:");
				List<IPartyTelephone> custMobileType = trans.getCustomerParty().getTelephoneInformation();
			
				
			for(IPartyTelephone custMobile:custMobileType) {
				if(custMobile.getTelephoneType().equalsIgnoreCase("MOBILE")) {
					custMobileNumber = custMobile.getTelephoneNumber();
					break;
				}
			}
			//	custMobileNumber = trans.getCustomerParty().getTelephoneInformation().get(i).getTelephoneNumber();
				editModel.setCustMobileNumber(custMobileNumber);
				setScopedValue(AsqValueKeys.ASQ_MOBILE_NUMBER, editModel.getCustMobileNumber());
				return super.handleInitialState();
			} 
		} catch (Exception ex) {
			LOG.info(
					"Mokafaa API Mobile number form execution exception customer is not available in the transaction and Trans object is null:");
		}
		return super.handleInitialState();
	}

	/**
	 * This method handles the data operation after submitting the mobile number
	 * 
	 * @param
	 * @return
	 */

	@Override
	protected IOpResponse handleDataAction(IXstDataAction argAction) {
		if (XstDataActionKey.ACCEPT.equals(argAction.getActionKey())) {
			AsqMokafaaMobileNumberEditModel editModel = getModel();
			IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
			
			custMobileNumber = editModel.getCustMobileNumber();
			LOG.debug("Mokafaa API Mobile number captured :" + custMobileNumber);
			if (custMobileNumber != null && !custMobileNumber.equals("")) {
				LOG.info("Process of Mokafaa tender starts here");
				setScopedValue(AsqValueKeys.ASQ_MOBILE_NUMBER, custMobileNumber);
				if (null != trans.getCustomerParty()
						&& !(this.getScopedValue(AsqValueKeys.ASQ_MOBILE_NUMBER).equals(custMobileNumber))) {
					IParty info = trans.getCustomerParty();
					info.setTelephone3(custMobileNumber);
					LOG.info(
							"Mokafaa API setting updated customer mobile number to transaction, this will be udpated once the transaciton is completed");
				}
				
			} else if (null != editModel.getLoyaltyCICNo() && !StringUtils.isEmpty(editModel.getLoyaltyCICNo())) {
				loyalityCicNo = editModel.getLoyaltyCICNo();
				setScopedValue(AsqValueKeys.ASQ_MOKAFAA_LOYALITY_CIC_NO, loyalityCicNo);
				custMobileNumber = null;
			} else {
				LOG.debug("Mokafaa API customer mobile number / Loyality CIC number field is null :");
				return super.handleDataAction(argAction);
			}
			LOG.info("Mokafaa API Trigger OTP service call starts here: ");
			return triggerOTP();

		}
		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_MOKAFAA_OTP"));
	}

	/**
	 * This method handles form validation of mobile number field
	 * 
	 * @param argModel
	 * @return validationResultList
	 * @Override
	 */

	@Override
	protected IValidationResultList validateForm(AsqMokafaaMobileNumberEditModel argModel) {
		ValidationResultList validationResultList = new ValidationResultList();
		if (StringUtils.isEmpty(argModel.getCustMobileNumber()) && argModel.getCustMobileNumber() == null) {
			IValidationResult idResult = SimpleValidationResult.getFailed("_asqMobileNumberFieldExceptionMessage");
			validationResultList.add(idResult);
		}
		return validationResultList;
	}

	/**
	 * This method implements the Trigger OTP service API call by preparing the
	 * request attributes
	 * 
	 * @param trans
	 * @return triggerOTPRequest submission to STC Service Handler
	 */

	private IOpResponse triggerOTP() {

//		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_MOKAFAA_OTP"));
		String authToken = getAuthToken();
		if (isAuthCallFailed) {
			return HELPER.getPromptResponse("GENERAL_API_ERROR", null);
		}
          _transactionScope.setValue(AsqValueKeys.ASQ_MOKAFAA_AUTH_TOKEN, authToken);
		IAsqMokafaaLoyaltyServiceRequest request = new AsqMokafaaLoyaltyServiceRequest();
		request.setServiceRequest(null);
		if (null != custMobileNumber) {
			request.setMobile(custMobileNumber);
		} else {
			request.setLoyaltyCICNo(loyalityCicNo);
		}
		request.setCurrency(System.getProperty("asq.mokafaa.redeem.currency"));
		request.setLang(System.getProperty("asq.mokafaa.redeem..language"));
		request.setAuthToken(authToken);

		LOG.info("Mokafaa API trigger OTP request is prepared :" + request);
		AsqMokafaaLoyaltyServiceResponse response = (AsqMokafaaLoyaltyServiceResponse) _asqMokafaaLoyalityTenderService
				.get().triggerOTPRequest(request);
		LOG.info("Mokafaa API Trigger OTP returns service response here: ");
//
		return validateResponse(response);
	}

	private String getAuthToken() {
		IAsqMokafaaLoyaltyServiceRequest request = new AsqMokafaaLoyaltyServiceRequest();

		request.setServiceRequest("AUTH");
		isAuthCallFailed = true;

		LOG.info("Mokafaa API auth token request is prepared :" + request);
		AsqMokafaaLoyaltyServiceResponse response = (AsqMokafaaLoyaltyServiceResponse) _asqMokafaaLoyalityTenderService
				.get().getAuthTokenCall(request);
		LOG.info("Mokafaa API auth token returns service response here: ");
//
		if (null != response && null != response.getAccess_token()) {
			isAuthCallFailed = false;
			return response.getAccess_token();
		} else if (null != response && null != response.getError()) {
			LOG.info("Mokafaa API auth token Generation Failed: {}", response.getError_description());
			
		}
		return null;
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

	private IOpResponse validateResponse(AsqMokafaaLoyaltyServiceResponse response) {
		if (null != response) {
			if (!(("200").equals(response.getStatus())) && null != response.getErrorCode()) {
				return handleServiceError(response);
			} else if (null != response.getHttpCode()) {
				return unauthorizedError(response);
			}
//			LOG.info("Mokafaa API saving response to DB started");
//			asqStcHelper.saveSTCResponseToDB(trans, globalID, requestDate, earnPoints);
//			LOG.info("Mokafaa API saving response to DB successfull");
			if (null != response.getOtp() && null != response.getOtp().getOtp_token()) {
				_transactionScope.setValue(AsqValueKeys.ASQ_MOKAFAA_OTP_TOKEN, response.getOtp().getOtp_token());
			}
			return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_MOKAFAA_OTP"));
		} else {
			return technicalErrorScreen("Service has null response");
		}
	}

	private IOpResponse unauthorizedError(AsqMokafaaLoyaltyServiceResponse response) {
		IFormattable[] args = new IFormattable[2];
		args[0] = _formattables.getSimpleFormattable(response.getHttpCode());
		args[1] = _formattables.getSimpleFormattable(response.getError_description());
		return HELPER.getPromptResponse("GENERAL_API_ERROR", args);
	}

	/**
	 * This method handles the Trigger OTP API call service errors
	 * 
	 * @param asqServiceResponse
	 * @return Error Prompts
	 */

	public IOpResponse handleServiceError(AsqMokafaaLoyaltyServiceResponse asqServiceResponse) {
//		if(("210").equals(asqServiceResponse.getErrorCode())) {
//			return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_MOKAFAA_OTP"));
//		}
		IFormattable[] args = new IFormattable[2];
		args[0] = _formattables.getSimpleFormattable(asqServiceResponse.getErrorCode());
		args[1] = _formattables.getSimpleFormattable(asqServiceResponse.getMessage());
		String errorConstant = asqMokafaaHelper.mapError(asqServiceResponse.getErrorCode());
		LOG.info("Error From Mokafaa OTP API::::: " + asqServiceResponse.getErrorCode() + " - "
				+ asqServiceResponse.getMessage());
		LOG.info("Error Message Generated By Xstore based on Mokafaa API Response::::: " + errorConstant);
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
		LOG.info("Mokafaa API::::: " + message);
		return HELPER.getPromptResponse("GENERAL_API_ERROR", args);
	}

}
