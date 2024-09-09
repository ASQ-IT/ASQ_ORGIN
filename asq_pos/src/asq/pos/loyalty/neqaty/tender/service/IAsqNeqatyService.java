package asq.pos.loyalty.neqaty.tender.service;

import dtv.service.IService;
import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;

public interface IAsqNeqatyService extends IService {

	// IServiceResponse inqueryWithOTP(IAsqNeqatyServiceRequest
	// paramAsqSTCLoyalityRequest) throws ServiceException;

	IServiceResponse redeemNeqityPoint(IAsqNeqatyServiceRequest paramAsqSTCLoyalityRequest) throws ServiceException;

	IServiceResponse earnNeqityPoint(IAsqNeqatyServiceRequest argRequest) throws ServiceException;

}
