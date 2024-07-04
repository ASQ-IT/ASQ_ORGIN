package asq.pos.loyality.stc.tender.service;

import dtv.service.IService;
import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;

public interface IAsqSTCLoyalityTenderService extends IService {
	
	IServiceResponse submitOTPRequest(AsqSTCLoyalityServiceRequest paramAsqSTCLoyalityRequest)throws ServiceException;

	IServiceResponse submitRedeemRequest(AsqSTCLoyalityServiceRequest paramAsqSTCLoyalityRequest)throws ServiceException;

	IServiceResponse submitReverseRedeemRequest(AsqSTCLoyalityServiceRequest paramAsqSTCLoyalityRequest)throws ServiceException;

}
