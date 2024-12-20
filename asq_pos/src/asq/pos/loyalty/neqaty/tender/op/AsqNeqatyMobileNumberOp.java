/**
 * 
 */
package asq.pos.loyalty.neqaty.tender.op;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.loyalty.neqaty.gen.NeqatyWSAPIRedeemOption;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyHelper;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyServiceRequest;
import asq.pos.loyalty.neqaty.tender.service.AsqNeqatyServiceResponse;
import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.neqaty.tender.service.IAsqNeqatyService;
import asq.pos.loyalty.neqaty.tender.service.IAsqNeqatyServiceRequest;
import asq.pos.loyalty.neqaty.tender.service.NeqatyMethod;
import dtv.data2.access.IQueryResultList;
import dtv.data2.access.QueryResultList;
import dtv.i18n.IFormattable;
import dtv.pos.common.OpChainKey;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.event.IXstEventObserver;
import dtv.pos.iframework.event.IXstEventType;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.op.IReversibleOp;
import dtv.xst.dao.crm.IParty;
import dtv.xst.dao.crm.IPartyTelephone;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trn.IPosTransaction;

/**
 * @author RA20221457
 *
 */
public class AsqNeqatyMobileNumberOp extends AsqNeqatyAbstractMobileNumberOp
		implements IXstEventObserver, IReversibleOp {

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
	protected String getFormKey() {
		return "ASQ_CAP_CUST_MOBILE_NO";
	}

	@Override
	protected IOpResponse handleInitialState() {
		AsqNeqatyMobileNumberEditModel editModel = getModel();
		try {
			IRetailTransaction trans = (IRetailTransaction) _transactionScope.getTransaction();
			if (trans != null && trans.getCustomerParty() != null)// Transactions Typecode condition to be included
			{
				LOG.info("Neqaty API Mobile number form execution Customer is Linked to Transaction:");
				List<IPartyTelephone> custMobileType = trans.getCustomerParty().getTelephoneInformation();

				for (IPartyTelephone custMobile : custMobileType) {
					if (custMobile.getTelephoneType().equalsIgnoreCase("MOBILE")) {
						custMobileNumber = custMobile.getTelephoneNumber();
						break;
					}
				}
				editModel.setCustMobileNumber(custMobileNumber);
				setScopedValue(AsqValueKeys.ASQ_NEQATY_MOBILE, editModel.getCustMobileNumber());
				return super.handleInitialState();
			}
		} catch (Exception ex) {
			LOG.info(
					"Neqaty API Mobile number form execution exception customer is not available in the transaction and Trans object is null:");
		}
		return super.handleInitialState();
	}

	@Override
	protected IOpResponse handleDataAction(IXstDataAction argAction) {

		try {
			if (XstDataActionKey.ACCEPT.equals(argAction.getActionKey())) {

				if (getOpState() == SHOWING_ERROR_PROMPT) {

					setOpState(null);
					return null;
				}

				IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
				AsqNeqatyMobileNumberEditModel editModel = this.getModel();
				custMobileNumber = editModel.getCustMobileNumber();
				LOG.debug("Neqaty API Mobile number captured :" + custMobileNumber);
				if (custMobileNumber != null && !custMobileNumber.equals("")) {
					LOG.debug("Process of Neqaty tender starts here");
					if (null != trans.getCustomerParty()) {
						IParty info = trans.getCustomerParty();
						// Mobile number field
						info.setTelephone3(custMobileNumber);
						LOG.debug(
								"Neqaty API setting updated customer mobile number to transaction, this will be udpated once the transaciton is completed");
					}
				} else if (custMobileNumber == null) {
					LOG.debug("Neqaty Inquire OTP Operation customer mobile number field is null :");
					return super.handleDataAction(argAction);
				}
				_transactionScope.setValue(AsqValueKeys.ASQ_NEQATY_MOBILE, custMobileNumber);
				return inquire();

			}

			else if (argAction.getData() != null) { // in this line we get the selected rowfrom screen
				IPosTransaction trans = _transactionScope.getTransaction();
				BigDecimal trxSubTotal = trans.getSubtotal();
				BigDecimal trxAmountDueTotal = trans.getAmountDue();
				BigDecimal redemptionValueBigDec = BigDecimal
						.valueOf(((NeqatyWSAPIRedeemOption) argAction.getData()).getRedeemAmount());
				if (trxSubTotal.compareTo(redemptionValueBigDec) < 0
						|| trxAmountDueTotal.compareTo(redemptionValueBigDec) < 0) {
					setOpState(SHOWING_ERROR_PROMPT);
					return HELPER.getPromptResponse("ASQ_NEQATY_NO_POINTS");
				}
				setScopedValue(AsqValueKeys.ASQ_NEQATY_REDEEM_POINTS, (NeqatyWSAPIRedeemOption) argAction.getData());
				return redeemPoints(custMobileNumber, (NeqatyWSAPIRedeemOption) argAction.getData());
			}

		} catch (Exception exception) {
			LOG.error("Exception from Neqaty form in Handling Data Action :" + exception);
			return technicalErrorScreen(exception.getLocalizedMessage());
		}
		return this.HELPER.completeResponse();
	}

	private IOpResponse inquire() {
		LOG.debug("Neqaty Inquire OTP Operation service call starts here: ");
		IAsqNeqatyServiceRequest request = new AsqNeqatyServiceRequest();
		request.setAuthenticationKey(System.getProperty("asq.neqaty.auth.key"));
		request.setOperationType("Inquire");
		request.setMsisdn(custMobileNumber);
		request.setTid(0);
		request.setMethod(NeqatyMethod.AUTHORIZE);
		AsqNeqatyServiceResponse response = (AsqNeqatyServiceResponse) asqNeqatyService.get()
				.callNeqatyService(request);
		LOG.debug("Neqaty Inquire OTP Operation service response here: ");
		return validateResponseAndDsipalyRedeemPointsToCustomer(response);
	}

	private IOpResponse validateResponseAndDsipalyRedeemPointsToCustomer(AsqNeqatyServiceResponse response) {
		if (null != response && 0 != response.getResultCode()) {
			return handleServiceError(response);
		} else if (null == response) {
			return technicalErrorScreen("Service has null response");
		}
		// if inquire call is success then taking redeem option and displaying in the
		// table.
		setScopedValue(AsqValueKeys.ASQ_NEQATY_TRANS_TOKEN, response.getToken());
		List<NeqatyWSAPIRedeemOption> redeemOptionsDisplay = runQueryWrapResults(
				response.getOptionsData().getRedeemOptions());
		return getSearchResultsPrompt(redeemOptionsDisplay, null);
	}

	private IOpResponse getSearchResultsPrompt(List<NeqatyWSAPIRedeemOption> resource, Object argMessage) {
		String promptKey = getSearchResultsPromptKey();
		return this.HELPER.getListPromptResponse(promptKey, resource.toArray(), null,
				displaySearchResultsAsFullScreen(), false, resultsHaveImplicitAccept(), null);
	}

	private IOpResponse redeemPoints(String custMobileNmbr, NeqatyWSAPIRedeemOption neqatyRedeemOption) {
		IAsqNeqatyServiceRequest request = new AsqNeqatyServiceRequest();
		request.setAuthenticationKey(System.getProperty("asq.neqaty.auth.key"));
		request.setOperationType("Redeem-OTP");
		request.setMsisdn(custMobileNmbr);
		request.setToken(getScopedValue(AsqValueKeys.ASQ_NEQATY_TRANS_TOKEN));
		request.setAmount(neqatyRedeemOption.getRedeemAmount());
		request.setRedeemCode(neqatyRedeemOption.getRedeemCode());// redeem code from the selected option
		request.setRedeemPoints(neqatyRedeemOption.getRedeemPoints());// redeem point from the selected option
		request.setTid(0);
		request.setMethod(NeqatyMethod.AUTHORIZE);
		AsqNeqatyServiceResponse response = (AsqNeqatyServiceResponse) asqNeqatyService.get()
				.callNeqatyService(request);
		return validateResponse(response);
	}

	public IOpResponse validateResponse(AsqNeqatyServiceResponse response) {
		if (null != response && 0 != response.getResultCode()) {
			return handleServiceError(response);
		} else if (null == response) {
			return technicalErrorScreen("Service has null response");
		}
		_transactionScope.setValue(AsqValueKeys.ASQ_NEQATY_TRANS_REFERENCE, response.getTransactionReference());
		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_NEQATY_OTP"));
	}

	public IOpResponse handleServiceError(AsqNeqatyServiceResponse asqServiceResponse) {
		IFormattable[] args = new IFormattable[2];
		args[0] = _formattables.getSimpleFormattable(String.valueOf(asqServiceResponse.getResultCode()));
		args[1] = _formattables.getSimpleFormattable(asqServiceResponse.getResultDescription());
		String errorConstant = asqNeqatyHelper.mapError(asqServiceResponse.getResultCode());
		LOG.info("Error From Neqaty Inquire OTP Operation : " + asqServiceResponse.getResultCode() + " - "
				+ asqServiceResponse.getResultDescription());
		LOG.info("Error Message Generated By Xstore based on Neqaty Inquire OTP Operation Response: " + errorConstant);
		return HELPER.getPromptResponse(errorConstant, args);
	}

	private IOpResponse technicalErrorScreen(String message) {
		IFormattable[] args = new IFormattable[2];
		args[1] = _formattables.getSimpleFormattable(message);
		LOG.info("Neqaty Inquire OTP Operation::::: " + message);
		return HELPER.getPromptResponse("ASQ_NEQATY_TECHNICAL_ERROR", args);
	}

	@Override
	public IXstEventType[] getObservedEvents() {
		return new IXstEventType[0];
	}

	protected IQueryResultList<NeqatyWSAPIRedeemOption> runQueryWrapResults(
			List<NeqatyWSAPIRedeemOption> neqatyRedeemOptionsList) {

		IQueryResultList<NeqatyWSAPIRedeemOption> neqatyDisplayResults = null;
		try {
			neqatyDisplayResults = QueryResultList.makeList(neqatyRedeemOptionsList, NeqatyWSAPIRedeemOption.class);
		} catch (Exception ex) {
			LOG.error("Exception while handling Neqaty response.", ex);
		}
		return neqatyDisplayResults;
	}
}
