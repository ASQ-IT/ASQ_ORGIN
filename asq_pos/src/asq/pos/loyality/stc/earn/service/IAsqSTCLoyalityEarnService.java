package asq.pos.loyality.stc.earn.service;

import asq.pos.loyality.stc.tender.service.AsqSTCLoyalityServiceRequest;
import dtv.service.IService;
import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;

public interface IAsqSTCLoyalityEarnService extends IService {
	
	IServiceResponse earnSTCPointRequest(AsqSTCLoyalityServiceRequest paramAsqSTCLoyalityRequest)throws ServiceException;


}
