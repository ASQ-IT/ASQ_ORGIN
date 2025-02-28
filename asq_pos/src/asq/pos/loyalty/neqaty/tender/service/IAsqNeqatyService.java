package asq.pos.loyalty.neqaty.tender.service;

import dtv.service.IService;
import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;

public interface IAsqNeqatyService extends IService {

    IServiceResponse redeemNeqityPoint(IAsqNeqatyServiceRequest paramAsqSTCLoyalityRequest) throws ServiceException;

	IServiceResponse callNeqatyService(IAsqNeqatyServiceRequest paramAsqNeqatyLoyalityRequest) throws ServiceException;

    IServiceResponse earnNeqityPoint(IAsqNeqatyServiceRequest paramAsqNeqatyLoyalityRequest) throws ServiceException;
}
