package asq.pos.loyalty.stc.tender.op;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.stc.tender.AsqStcHelper;
import asq.pos.loyalty.stc.tender.service.AsqSTCLoyaltyServiceRequest;
import asq.pos.loyalty.stc.tender.service.AsqSTCLoyaltyServiceResponse;
import asq.pos.loyalty.stc.tender.service.IAsqSTCLoyaltyServiceRequest;
import asq.pos.loyalty.stc.tender.service.IAsqSTCLoyaltyTenderService;
import dtv.pos.common.OpChainKey;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trl.IRetailTransaction;

/**
 * @author RA20221457
 *
 */
public class AsqSTCMobileNumberOp extends AbstractFormOp<AsqSTCMobileNumberEditModel> {

	private static final Logger LOG = LogManager.getLogger(AsqSTCMobileNumberOp.class);

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

	@Override
	protected IOpResponse handleBeforeDataAction(IXstEvent event) {
		try {
			IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
			// custMobileNumber =
			// trans.getCustomerParty().getTelephoneInformation().get(i).getTelephoneNumber();

			if (trans != null && trans.getCustomerParty() != null)// Transactions Typecode condition to be included
			{
				custMobileNumber = trans.getCustomerParty().getTelephoneInformation().get(i).getTelephoneNumber();
				return this.handleFormResponse(event);
			}

		} catch (Exception ex) {

		}
		return super.handleBeforeDataAction(event);
	}

	@Override
	protected IOpResponse handleDataAction(IXstDataAction argAction) {

		if (XstDataActionKey.ACCEPT.equals(argAction.getActionKey())) {
			AsqSTCMobileNumberEditModel model = getModel();
			custMobileNumber = model.getCustMobileNumber();
			if (custMobileNumber != null && !custMobileNumber.equals("")) {
				LOG.debug("Process of STC tender starts here");
				setScopedValue(AsqValueKeys.ASQ_STC_MOBILE, model.getCustMobileNumber());
			}
			triggerOTP();
		}

		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_STC_OTP"));
	}

	@Override
	protected IOpResponse handleFormResponse(IXstEvent argEvent) {
		AsqSTCMobileNumberEditModel editModel = new AsqSTCMobileNumberEditModel();
		editModel.setCustMobileNumber(custMobileNumber);
		return HELPER.getChangeFormResponse(getFormKey(), editModel, getActionGroupKey(), true, null, isEditable());
	}

	private void triggerOTP() {
		AsqSTCLoyaltyServiceResponse response = new AsqSTCLoyaltyServiceResponse();

		IAsqSTCLoyaltyServiceRequest request = new AsqSTCLoyaltyServiceRequest();

		request.setMsisdn(Long.parseLong(custMobileNumber.trim()));
		request.setBranchId(System.getProperty("asq.stc.branchid"));
		request.setTerminalId(System.getProperty("asq.stc.terminalid"));
		request.setRequestDate(DateTime.now().toString());
		request.setPIN(null);
		request.setAmount(null);

		IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
		request.setTransactionId(String.valueOf(trans.getTransactionSequence()));

		response = (AsqSTCLoyaltyServiceResponse) _asqSTCLoyalityTenderService.get().triggerOTPRequest(request);
		if (response != null) {
			if (!asqStcHelper.isSTCResponseValid(response)) {
				response.getErrors();
			}
		}
	}

}
