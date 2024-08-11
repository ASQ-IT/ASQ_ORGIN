package asq.pos.loyalty.stc.tender.op;

import java.math.BigDecimal;
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
import dtv.xst.dao.trn.IPosTransaction;

public class AsqSTCTenderOp extends AbstractFormOp<AsqSTCTenderOTPEditModel> {

	private static final Logger LOG = LogManager.getLogger(AsqSTCTenderOp.class);

	/**
	 * This class extends the Xstore Standard form class to handle all actions
	 * related to OTP field
	 */

	@Inject
	protected Provider<IAsqSTCLoyaltyTenderService> _asqSTCLoyalityTenderService;

	@Inject
	AsqStcHelper asqStcHelper;

	@Override
	protected AsqSTCTenderOTPEditModel createModel() {
		return new AsqSTCTenderOTPEditModel();
	}

	@Override
	protected String getFormKey() {
		return "ASQ_STC_OTP";
	}

	/**
	 * This method handles the DataActions after submitting OTP request
	 * 
	 * @throws Database persistent Exception
	 * @param IRetailTransaction originalPosTrx, String globalID, Date requestDate
	 * @return generated globalID
	 */

	@Override
	protected IOpResponse handleDataAction(IXstDataAction argAction) {

		String custMobileNmbr = "";
		Integer amount = null;
		try {
			if (XstDataActionKey.ACCEPT.equals(argAction.getActionKey())) {
				AsqSTCTenderOTPEditModel model = getModel();
				if ((model.getStcOTP() != null) && (model.getStcRedeemPoints() != null)
						&& (!model.getStcRedeemPoints().equals(""))) {
					LOG.debug("Process of STC Redemption points Tender starts here :" + model.getStcOTP());
					IPosTransaction trans = (IPosTransaction) this._transactionScope.getTransaction();
					BigDecimal redemptionValueBigDec = new BigDecimal(model.getStcRedeemPoints());
					if (trans.getTotal().compareTo(BigDecimal.ZERO) == 0) {
						return HELPER.getErrorResponse(
								_formattables.getSimpleFormattable("_asqSTCEnteredZeroPointValueMessage"));
					} else if (trans.getTotal().compareTo(redemptionValueBigDec) == 1) {
						LOG.info("Calculated Redemption amount has been set here to transaction :"
								+ trans.getTotal().compareTo(redemptionValueBigDec));
						trans.setTotal(trans.getTotal().subtract(redemptionValueBigDec));
					} else if (trans.getTotal().compareTo(redemptionValueBigDec) == -1) {
						return HELPER.getErrorResponse(
								_formattables.getSimpleFormattable("_asqSTCEnteredPointsValueMessage"));
					} else if (redemptionValueBigDec.compareTo(BigDecimal.ZERO) == 0) {
						return HELPER.getErrorResponse(
								_formattables.getSimpleFormattable("_asqSTCEnteredZeroPointValueMessage"));
					}
				} else if (model.getStcOTP() == null) {
					return super.handleDataAction(argAction);
				}
				AsqSTCLoyaltyServiceResponse response = new AsqSTCLoyaltyServiceResponse();
				String otp = model.getStcOTP();
				setScopedValue(AsqValueKeys.ASQ_STC_OTP, otp);
				IAsqSTCLoyaltyServiceRequest request = new AsqSTCLoyaltyServiceRequest();
				request.setMsisdn(Long.parseLong(custMobileNmbr.trim()));
				request.setBranchId(System.getProperty("asq.stc.branchid"));
				request.setTerminalId(System.getProperty("asq.stc.terminalid"));
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				Date requestDate = DateTime.now().toDate();
				request.setRequestDate(formatter.format(requestDate));
				String globalID = asqStcHelper.generateGlobalId();
				// asqStcHelper.saveSTCResponseToDB(trans,globalID,requestDate);
				request.setPIN(Integer.parseInt(otp));
				request.setAmount(amount);
				response = (AsqSTCLoyaltyServiceResponse) _asqSTCLoyalityTenderService.get().submitOTPRequest(request);
				if (null != response && null != response.getErrors() && 0 != response.getErrors().length) {
					return handleServiceError(response);
				} else if (null == response) {
					IFormattable[] args = new IFormattable[2];
					args[1] = _formattables.getSimpleFormattable("Service has null response");
					LOG.info("STC REDEEM API::::: Service has null response");
					return HELPER.getPromptResponse("ASQ_STC_TECHNICAL_ERROR", args);
				}

			}
		}

		catch (Exception exception) {
			LOG.error("Exception from STC_OTP form in Handling Data Action :" + exception);
		}
		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_TENDER_STC"));
	}

	/**
	 * This method handles form validation of mobile number field
	 * 
	 * @param argModel
	 * @return validationResultList
	 */

	protected IValidationResultList validateForm(AsqSTCTenderOTPEditModel argModel) {
		ValidationResultList validationResultList = new ValidationResultList();
		if (StringUtils.isEmpty(argModel.getStcOTP()) && argModel.getStcOTP() == null) {
			IValidationResult idResult = SimpleValidationResult.getFailed("_asqOTPFieldExceptionMessage");
			validationResultList.add(idResult);
		}
		return (IValidationResultList) validationResultList;
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
		LOG.info("Error From STC REDEEM API::::: " + error.getCode() + " - " + error.getDescription());
		LOG.info("Error Message Generated By Xstore based on STC OTP API Response::::: " + errorConstant);
		return HELPER.getPromptResponse(errorConstant, args);
	}
}
