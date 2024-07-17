package asq.pos.loyalty.stc.tender.op;

import dtv.pos.framework.op.AbstractFormOp;

public class AsqSTCTenderOp extends AbstractFormOp<AsqSTCTenderOTPEditModel> {
	
	/*
	 * @Inject protected Provider<IAsqSTCLoyaltyTenderService>
	 * _asqSTCLoyalityTenderService;
	 */

	@Override
	protected AsqSTCTenderOTPEditModel createModel() {
		return new AsqSTCTenderOTPEditModel();
	}

	@Override
	protected String getFormKey() {
		
		return "STC_OTP";
	}
	

	/*public IOpResponse handleOpExec(IXstEvent arg0) {
				
		//Logic to initiate the OTP screen and adding the STC tender in transaction
		
		AsqSTCLoyaltyServiceRequest serviceReq = new AsqSTCLoyaltyServiceRequest();
		//Get Receive request
		_asqSTCLoyalityTenderService.get().submitOTPRequest(serviceReq);
		
		return null;
	}*/

}
