package asq.pos.loyalty.stc.tender.service;

import dtv.service.IService;
import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;

public interface IAsqSTCLoyaltyTenderService extends IService {
	
	IServiceResponse submitOTPRequest(IAsqSTCLoyaltyServiceRequest paramAsqSTCLoyalityRequest)throws ServiceException;

	IServiceResponse triggerOTPRequest(IAsqSTCLoyaltyServiceRequest paramAsqSTCLoyalityRequest)throws ServiceException;
	
	IServiceResponse earnReward(IAsqSTCLoyaltyServiceRequest argRequest)throws ServiceException;
	
	IServiceResponse refundRedeem(IAsqSTCLoyaltyServiceRequest argRequest) throws ServiceException;

}
