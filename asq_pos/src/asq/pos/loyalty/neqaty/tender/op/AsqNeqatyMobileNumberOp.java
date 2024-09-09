package asq.pos.loyalty.neqaty.tender.op;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyHelper;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyServiceRequest;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyServiceResponse;
import asq.pos.loyalty.neqaty.tender.service.IAsqNeqatyService;
import asq.pos.loyalty.neqaty.tender.service.IAsqNeqatyServiceRequest;
import asq.pos.loyalty.neqaty.tender.service.NeqatyMethod;
import dtv.i18n.IFormattable;
import dtv.pos.common.OpChainKey;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.crm.IParty;
import dtv.xst.dao.trl.IRetailTransaction;

/**
 * @author RA20221457
 *
 */
public class AsqNeqatyMobileNumberOp extends AbstractFormOp<AsqNeqatyMobileNumberEditModel> {

	private static final Logger LOG = LogManager.getLogger(AsqNeqatyMobileNumberOp.class);

	private String custMobileNumber = "";

	@Inject
	protected Provider<IAsqNeqatyService> asqNeqatyService;

	@Inject
	AsqNeqatyHelper asqNeqatyHelper;

	/**
	 * This class extends the Xstore Standard form class to handle all actions
	 * related to Mobile number field
	 */

	@Override
	protected AsqNeqatyMobileNumberEditModel createModel() {
		return new AsqNeqatyMobileNumberEditModel();
	}

	@Override
	protected String getFormKey() {
		return "ASQ_CAP_CUST_MOBILE_NO";
	}

	@Override
	protected IOpResponse handleInitialState() {
		AsqNeqatyMobileNumberEditModel editModel = getModel();
		try {
			IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
			if (trans != null && trans.getCustomerParty() != null)// Transactions Typecode condition to be included
			{
				LOG.info("Neqaty API Mobile number form execution Customer is Linked to Transaction:");
				custMobileNumber = trans.getCustomerParty().getTelephoneInformation().get(0).getTelephoneNumber();
				editModel.setCustMobileNumber(custMobileNumber);
				setScopedValue(AsqValueKeys.ASQ_NEQATY_MOBILE, editModel.getCustMobileNumber());
				return super.handleInitialState();
			} else {

			}
		} catch (Exception ex) {
			LOG.info("Neqaty API Mobile number form execution exception customer is not available in the transaction and Trans object is null:");
		}
		return super.handleInitialState();
	}

	@Override
	protected IOpResponse handleDataAction(IXstDataAction argAction) {
		try {
			if (XstDataActionKey.ACCEPT.equals(argAction.getActionKey())) {
				IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
				AsqNeqatyMobileNumberEditModel editModel = this.getModel();
				custMobileNumber = editModel.getCustMobileNumber();
				LOG.debug("Neqaty API Mobile number captured :" + custMobileNumber);
				if (custMobileNumber != null && !custMobileNumber.equals("")) {
					LOG.debug("Process of Neqaty tender starts here");
					if (null != trans.getCustomerParty() && !(this.getScopedValue(AsqValueKeys.ASQ_NEQATY_MOBILE).equals(custMobileNumber))) {
						IParty info = trans.getCustomerParty();
						info.setTelephone1(custMobileNumber);//
						LOG.debug("Neqaty API setting updated customer mobile number to transaction, this will be udpated once the transaciton is completed");
					}
				} else if (custMobileNumber == null) {
					LOG.debug("Neqaty Inquire OTP Operation customer mobile number field is null :");
					return super.handleDataAction(argAction);
				}
				this.setScopedValue(AsqValueKeys.ASQ_MOBILE_NUMBER, custMobileNumber);
				return inqueryWithOTP();

			}
		} catch (Exception exception) {
			LOG.error("Exception from Neqaty form in Handling Data Action :" + exception);
		}
		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_NEQATY_REDEEM_OPTION"));

	}

	private IOpResponse inqueryWithOTP() {
		LOG.debug("Neqaty Inquire OTP Operation service call starts here: ");
		IAsqNeqatyServiceRequest request = new AsqNeqatyServiceRequest();
		request.setAuthenticationKey(System.getProperty("asq.neqaty.auth.key"));
		request.setOperationType("Inquire");
		request.setMsisdn(custMobileNumber);
		request.setTid(0);

		request.setMethod(NeqatyMethod.AUTHORIZE);

		// return
		// this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_NEQATY_REDEEM_OPTION"));
		AsqNeqatyServiceResponse response = (AsqNeqatyServiceResponse) asqNeqatyService.get().redeemNeqityPoint(request);
		LOG.debug("Neqaty Inquire OTP Operation service response here: ");
		return validateResponse(response);
	}

	private IOpResponse validateResponse(AsqNeqatyServiceResponse response) {
		if (null != response && 0 != response.getResultCode()) {
			return handleServiceError(response);
		} else if (null == response) {
			return technicalErrorScreen("Service has null response");
		}
		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_NEQATY_REDEEM_OPTION"));
	}

	public IOpResponse handleServiceError(AsqNeqatyServiceResponse asqServiceResponse) {
		IFormattable[] args = new IFormattable[2];
		args[0] = _formattables.getSimpleFormattable(String.valueOf(asqServiceResponse.getResultCode()));
		args[1] = _formattables.getSimpleFormattable(asqServiceResponse.getResultDescription());
		String errorConstant = asqNeqatyHelper.mapError(asqServiceResponse.getResultCode());
		LOG.info("Error From Neqaty Inquire OTP Operation : " + asqServiceResponse.getResultCode() + " - " + asqServiceResponse.getResultDescription());
		LOG.info("Error Message Generated By Xstore based on Neqaty Inquire OTP Operation Response: " + errorConstant);
		return HELPER.getPromptResponse(errorConstant, args);
	}

	private IOpResponse technicalErrorScreen(String message) {
		IFormattable[] args = new IFormattable[2];
		args[1] = _formattables.getSimpleFormattable(message);
		LOG.info("Neqaty Inquire OTP Operation::::: " + message);
		return HELPER.getPromptResponse("ASQ_NEQATY_TECHNICAL_ERROR", args);
	}

}
