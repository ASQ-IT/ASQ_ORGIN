package asq.pos.loyalty.stc.earn.service;

import asq.pos.loyalty.stc.tender.service.AsqSTCLoyaltyServiceRequest;
import dtv.service.IService;
import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;

public interface IAsqSTCLoyalityEarnService extends IService {
	
	IServiceResponse earnSTCPointRequest(AsqSTCLoyaltyServiceRequest paramAsqSTCLoyalityRequest)throws ServiceException;


}
