package asq.pos.loyalty.stc.tender.op;

import javax.inject.Inject;
import javax.inject.Provider;

import org.joda.time.DateTime;

import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.stc.tender.AsqStcHelper;
import asq.pos.loyalty.stc.tender.service.AsqSTCLoyaltyServiceRequest;
import asq.pos.loyalty.stc.tender.service.AsqSTCLoyaltyServiceResponse;
import asq.pos.loyalty.stc.tender.service.IAsqSTCLoyaltyServiceRequest;
import asq.pos.loyalty.stc.tender.service.IAsqSTCLoyaltyTenderService;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.iframework.action.IXstActionKey;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.action.IXstDataActionKey;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trl.IRetailTransaction;

public class AsqSTCTenderOp extends AbstractFormOp<AsqSTCTenderOTPEditModel> {

	@Inject
	protected Provider<IAsqSTCLoyaltyTenderService> _asqSTCLoyalityTenderService;

	@Inject
	AsqStcHelper asqStcHelper;

	public static final IXstDataActionKey ACCEPT = XstDataActionKey.valueOf("ACCEPT");

	@Override
	protected AsqSTCTenderOTPEditModel createModel() {
		return new AsqSTCTenderOTPEditModel();
	}

	@Override
	protected String getFormKey() {

		return "STC_OTP";
	}

	@Override
	protected IOpResponse handleDataAction(IXstDataAction argAction) {

		String custMobileNmbr = "";
		if (getScopedValue(AsqValueKeys.ASQ_STC_MOBILE) != null) {
			custMobileNmbr = getScopedValue(AsqValueKeys.ASQ_STC_MOBILE);
			System.out.println();
		}
		try {
			IXstActionKey actionKey = argAction.getActionKey();
			if (actionKey == ACCEPT) {
				AsqSTCLoyaltyServiceResponse response = new AsqSTCLoyaltyServiceResponse();
				AsqSTCTenderOTPEditModel model = (AsqSTCTenderOTPEditModel) getModel();
				String otp = model.getStcOTP();
				setScopedValue(AsqValueKeys.STC_OTP, otp);

				IAsqSTCLoyaltyServiceRequest request = new AsqSTCLoyaltyServiceRequest();
				request.setMsisdn(Long.parseLong(custMobileNmbr.trim()));
				request.setBranchId(System.getProperty("asq.stc.branchid"));
				request.setTerminalId(System.getProperty("asq.stc.terminalid"));	
				request.setRequestDate(DateTime.now().toString());
				request.setPIN(Integer.parseInt(otp));
				request.setAmount(10);
				
				IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
				request.setTransactionId(String.valueOf(trans.getTransactionSequence()));
				
				response = (AsqSTCLoyaltyServiceResponse) _asqSTCLoyalityTenderService.get().submitOTPRequest(request);
				if (response != null) {
					if (!asqStcHelper.isSTCResponseValid(response)) {
						response.getErrors();
					}
				}

			}
		} catch (Exception ex) {

			return HELPER.getPromptResponse("");
		}

		return this.HELPER.completeResponse();
	}

}
