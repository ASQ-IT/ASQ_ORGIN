package asq.pos.loyalty.stc.tender.op;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import asq.pos.zatca.AsqZatcaConstant;
import dtv.data2.access.DataFactory;
import dtv.i18n.IFormattable;
import dtv.pos.common.OpChainKey;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.framework.validation.ValidationResultList;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.validation.IValidationResult;
import dtv.pos.iframework.validation.IValidationResultList;
import dtv.pos.iframework.validation.SimpleValidationResult;
import dtv.util.StringUtils;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trn.IPosTransaction;
import dtv.xst.dao.trn.IPosTransactionProperty;
import dtv.xst.dao.ttr.ITenderLineItem;

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

		Integer amount = null;
		try {
			if (XstDataActionKey.ACCEPT.equals(argAction.getActionKey())) {
				AsqSTCTenderOTPEditModel model = getModel();
				ITenderLineItem tenderLine = getScopedValue(ValueKeys.CURRENT_TENDER_LINE);
				if ((model.getStcOTP() != null) && (model.getStcRedeemPoints() != null) && (!model.getStcRedeemPoints().equals(""))) {
					LOG.debug("Process of STC Redemption points Tender starts here :" + model.getStcOTP());
					IPosTransaction trans = (IPosTransaction) this._transactionScope.getTransaction();
					BigDecimal redemptionValueBigDec = new BigDecimal(model.getStcRedeemPoints());
					BigDecimal  trxTotal = trans.getSubtotal();//excluding tax
					BigDecimal roundedAmount = trxTotal.setScale(0, RoundingMode.HALF_UP);
					if (trans.getTotal().compareTo(BigDecimal.ZERO) == 0) {
						return HELPER.getErrorResponse(_formattables.getSimpleFormattable("_asqSTCEnteredZeroPointValueMessage"));
					} else if (trxTotal.compareTo(redemptionValueBigDec) == 1) {
						LOG.info("Calculated Redemption amount has been set here to transaction :"+ roundedAmount.compareTo(redemptionValueBigDec));
						roundedAmount = trxTotal.subtract(redemptionValueBigDec);
						trans.setAmountTendered(roundedAmount);
					} else if (trxTotal.compareTo(redemptionValueBigDec) == -1) {
						return HELPER.getErrorResponse(_formattables.getSimpleFormattable("_asqSTCEnteredPointsValueMessage"));
					} else if (redemptionValueBigDec.compareTo(BigDecimal.ZERO) == 0) {
						return HELPER.getErrorResponse(_formattables.getSimpleFormattable("_asqSTCEnteredZeroPointValueMessage"));
					}
					amount = roundedAmount.intValue();
				} else if (model.getStcOTP() == null) {
					return super.handleDataAction(argAction);
				}
				String otp = model.getStcOTP();
				setScopedValue(AsqValueKeys.ASQ_STC_OTP, otp);
				String custMobileNmbr = getScopedValue(AsqValueKeys.ASQ_MOBILE_NUMBER);
				return requestPreparerForRedeemPoints(otp, custMobileNmbr, amount);
			}
		} catch (Exception exception) {
			LOG.error("Exception from STC_OTP form in Handling Data Action :" + exception);
			return technicalErrorScreen("Exception from STC_OTP form in Handling Data Action :" + exception);
		}
		LOG.info("Action key is not equal to ACCEPT, rolling back to Sale Screen :" +argAction.getActionKey());
		return this.HELPER.getOpChainRollBackRequest() ;
	}
	
	/**
	 * This method checks whether customer is linked to transaction or not
	 * 
	 * @param
	 * @return
	 */

	protected IOpResponse handleInitialState() {
		AsqSTCTenderOTPEditModel editModel = getModel();
		try {
			IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
			if (trans != null)
			{
				BigDecimal roundedAmount = trans.getSubtotal();
				editModel.setStcRedeemPoints(roundedAmount.toString());
				return super.handleInitialState();
		} 
		}catch (Exception ex) {
			LOG.info(
					"STC OTP API amount auto populate exception and Trans object is null:");
		}
		return super.handleInitialState();
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

	private IOpResponse requestPreparerForRedeemPoints(String otp, String custMobileNmbr, int amount) {

		IAsqSTCLoyaltyServiceRequest request = new AsqSTCLoyaltyServiceRequest();
		request.setMsisdn(Long.parseLong(custMobileNmbr.trim()));
		request.setBranchId(System.getProperty("asq.stc.branchid"));
		request.setTerminalId(System.getProperty("asq.stc.terminalid"));
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date requestDate = DateTime.now().toDate();
		request.setRequestDate(formatter.format(requestDate));
		request.setPIN(Integer.parseInt(otp));
		request.setAmount(amount);
		String globalID = asqStcHelper.generateGlobalId();
		request.setGlobalId(globalID);
		AsqSTCLoyaltyServiceResponse response = (AsqSTCLoyaltyServiceResponse) _asqSTCLoyalityTenderService.get()
				.submitOTPRequest(request);
		return validateResponseAndStoreDataInDB(response, requestDate, globalID, null);
	}

	private IOpResponse validateResponseAndStoreDataInDB(AsqSTCLoyaltyServiceResponse response, Date requestDate,
			String globalID, String earnPoints) {
		if (null != response && null != response.getErrors() && 0 != response.getErrors().length) {
			return handleServiceError(response);
		} else if (null == response) {
			return technicalErrorScreen("STC REDEEM API::::: Service has null response");
		}
		IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
		LOG.info("STC REDEEM API saving response to DB started");
		saveSTCResponseToDB(trans, globalID, requestDate, earnPoints);
		LOG.info("STC REDEEM API saving response to DB successfull");
		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_TENDER_STC"));
	}
	
	/**
	 * This method saves the response of STC API like GlobalID and Request Date to
	 * TRN_TRNS_P table for audit purpose
	 * 
	 * @throws 
	 * @param IRetailTransaction originalPosTrx, String globalID, Date requestDate
	 * @return
	 */
	
	public void saveSTCResponseToDB(IRetailTransaction originalPosTrx, String globalID, Date requestDate,
			String earnPoints) {
		IPosTransactionProperty newTrxProps = DataFactory.createObject(IPosTransactionProperty.class);
		newTrxProps.setType("STRING");
		newTrxProps.setPropertyValue(globalID);
		newTrxProps.setDateValue(requestDate);
		if (earnPoints != null) {
			BigDecimal earnPntsDecimal = new BigDecimal(earnPoints);
			newTrxProps.setDecimalValue(earnPntsDecimal);
			originalPosTrx.addPosTransactionProperty(newTrxProps);
			newTrxProps.setPropertyCode(AsqZatcaConstant.STC_SUCCESS_EARN_RESPONSE);
		}
		else {
			newTrxProps.setPropertyCode(AsqZatcaConstant.STC_SUCCESS_REDEEM_RESPONSE);
			originalPosTrx.addPosTransactionProperty(newTrxProps);
			System.out.println();
		}
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
		LOG.info("STC REDEEM API::::: " + message);
		return HELPER.getPromptResponse("ASQ_STC_TECHNICAL_ERROR", args);
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
