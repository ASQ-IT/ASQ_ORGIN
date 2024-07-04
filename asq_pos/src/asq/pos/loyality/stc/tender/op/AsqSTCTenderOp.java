package asq.pos.loyality.stc.tender.op;

import javax.inject.Inject;
import javax.inject.Provider;

import asq.pos.loyality.stc.tender.service.AsqSTCLoyalityServiceRequest;
import asq.pos.loyality.stc.tender.service.IAsqSTCLoyalityTenderService;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;

public class AsqSTCTenderOp extends Operation {
	
	@Inject
	protected Provider<IAsqSTCLoyalityTenderService> _asqSTCLoyalityTenderService;

	@Override
	public IOpResponse handleOpExec(IXstEvent arg0) {
				
		//Logic to initiate the OTP screen and adding the STC tender in transaction
		
		AsqSTCLoyalityServiceRequest serviceReq = new AsqSTCLoyalityServiceRequest();
		//Get Receive request
		_asqSTCLoyalityTenderService.get().submitOTPRequest(serviceReq);
		
		return null;
	}

}
