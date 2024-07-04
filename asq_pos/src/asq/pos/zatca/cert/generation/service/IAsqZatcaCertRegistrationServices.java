package asq.pos.zatca.cert.generation.service;

import dtv.service.IService;
import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;

public interface IAsqZatcaCertRegistrationServices extends IService {
	
	IServiceResponse submitCertForRegistration(IAsqSubmitZatcaCertServiceRequest paramAsqISubmitZatcaCertServiceRequest)throws ServiceException;

}
