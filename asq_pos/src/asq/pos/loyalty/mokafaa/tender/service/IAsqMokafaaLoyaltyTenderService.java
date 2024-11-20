package asq.pos.loyalty.mokafaa.tender.service;

import dtv.service.IService;
import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;

public interface IAsqMokafaaLoyaltyTenderService extends IService {
	
	IServiceResponse submitOTPRequest(IAsqMokafaaLoyaltyServiceRequest paramAsqSTCLoyalityRequest)throws ServiceException;

	IServiceResponse triggerOTPRequest(IAsqMokafaaLoyaltyServiceRequest request)throws ServiceException;

	IServiceResponse getAuthTokenCall(IAsqMokafaaLoyaltyServiceRequest request)throws ServiceException;
	
	IServiceResponse refundRedeem(IAsqMokafaaLoyaltyServiceRequest request)throws ServiceException;

}
