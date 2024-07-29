package asq.pos.bnpl.tamara.tender.service;

import dtv.service.IService;
import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;

public interface IAsqBnplTamaraServices extends IService {

	public IServiceResponse submitPaymentlinkToCustomer(IAsqSubmitBnplTamraServiceRequest argRequest) throws ServiceException;

}
