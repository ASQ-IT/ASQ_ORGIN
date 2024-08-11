/**
 * 
 */
package asq.pos.loyalty.stc.tender.op;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Inject;
import javax.inject.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
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
import dtv.xst.dao.trl.IRetailTransaction;

/**
 * @author RA20221457
 *
 */

public class AsqSTCMobileNumberOp extends AbstractFormOp<AsqSTCMobileNumberEditModel> {

	private static final Logger LOG = LogManager.getLogger(AsqSTCMobileNumberOp.class);

	/**
	 * This class extends the Xstore Standard form class to handle all actions
	 * related to Mobile number field
	 */

	@Inject
	protected Provider<IAsqSTCLoyaltyTenderService> _asqSTCLoyalityTenderService;

	@Inject
	AsqStcHelper asqStcHelper;
	private String custMobileNumber = "";
	private int i = 0;

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
			if (trans != null && trans.getCustomerParty() != null)// Transactions Typecode condition to be included
			{
				LOG.info("STC API Mobile number form execution Customer is Linked to Transaction:");
				custMobileNumber = trans.getCustomerParty().getTelephoneInformation().get(i).getTelephoneNumber();
				editModel.setCustMobileNumber(custMobileNumber);
				setScopedValue(AsqValueKeys.ASQ_STC_MOBILE, editModel.getCustMobileNumber());
				return super.handleInitialState();
			} else {

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
			custMobileNumber = editModel.getCustMobileNumber();
			LOG.debug("STC API Mobile number captured :" + custMobileNumber);
			if (custMobileNumber != null && !custMobileNumber.equals("")) {
				LOG.debug("Process of STC tender starts here");
				if (!(this.getScopedValue(AsqValueKeys.ASQ_STC_MOBILE).equals(custMobileNumber))) {
					IParty info = trans.getCustomerParty();
					info.setTelephone1(custMobileNumber);//
					LOG.debug(
							"STC API setting updated customer mobile number to transaction, this will be udpated once the transaciton is completed");
				}
			} else if (custMobileNumber == null) {
				LOG.debug("STC API customer mobile number field is null :");
				return super.handleDataAction(argAction);
			}
			LOG.debug("STC API Trigger OTP service call starts here: ");
			AsqSTCLoyaltyServiceResponse response = triggerOTP(trans);
			LOG.debug("STC API Trigger OTP returns service response here: ");
			if (null != response && null != response.getErrors() && 0 != response.getErrors().length) {
				return handleServiceError(response);
			} else if (null == response) {
				IFormattable[] args = new IFormattable[2];
				args[1] = _formattables.getSimpleFormattable("Service has null response");
				LOG.info("STC OTP API::::: Service has null response");
				return HELPER.getPromptResponse("ASQ_STC_TECHNICAL_ERROR", args);
			}

		}
		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_STC_OTP"));
	}

	/**
	 * This method handles form validation of mobile number field
	 * 
	 * @param argModel
	 * @return validationResultList
	 */

	protected IValidationResultList validateForm(AsqSTCMobileNumberEditModel argModel) {
		ValidationResultList validationResultList = new ValidationResultList();
		if (StringUtils.isEmpty(argModel.getCustMobileNumber()) && argModel.getCustMobileNumber() == null) {
			IValidationResult idResult = SimpleValidationResult.getFailed("_asqMobileNumberFieldExceptionMessage");
			validationResultList.add(idResult);
		}
		return (IValidationResultList) validationResultList;
	}

	/**
	 * This method implements the Trigger OTP service API call by preparing the
	 * request attributes
	 * 
	 * @param trans
	 * @return triggerOTPRequest submission to STC Service Handler
	 */

	private AsqSTCLoyaltyServiceResponse triggerOTP(IRetailTransaction trans) {

		IAsqSTCLoyaltyServiceRequest request = new AsqSTCLoyaltyServiceRequest();

		/*
		 * //Converting SystemDate & Time to Saudi locale DateTime dateTimeUtc = new
		 * DateTime( DateTimeZone.UTC ); DateTimeZone timeZone = DateTimeZone.forID(
		 * "Asia/Riyadh" ); DateTime dateTimeRiyadh = dateTimeUtc.withZone( timeZone );
		 * DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		 * 
		 * Date requestDate = dateTimeRiyadh.toDate();
		 */

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date requestDate = DateTime.now().toDate();
		String globalID = asqStcHelper.generateGlobalId();
		asqStcHelper.saveSTCResponseToDB(trans, globalID, requestDate);
		request.setMsisdn(Long.parseLong(custMobileNumber.trim()));
		request.setBranchId(System.getProperty("asq.stc.branchid"));
		request.setTerminalId(System.getProperty("asq.stc.terminalid"));
		request.setRequestDate(formatter.format(requestDate));
		request.setPIN(null);
		request.setAmount(null);
		request.setGlobalId(globalID);
		return (AsqSTCLoyaltyServiceResponse) _asqSTCLoyalityTenderService.get().triggerOTPRequest(request);
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

}
