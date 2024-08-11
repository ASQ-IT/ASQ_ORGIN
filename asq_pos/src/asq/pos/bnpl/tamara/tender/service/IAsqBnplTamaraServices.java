package asq.pos.bnpl.tamara.tender.service;

import dtv.service.IService;
import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;

public interface IAsqBnplTamaraServices extends IService {

	public IServiceResponse createInStoreCheckoutSession(IAsqSubmitBnplTamraServiceRequest argRequest) throws ServiceException;
	public IServiceResponse getOrderDetails(IAsqSubmitBnplTamraServiceRequest argRequest) throws ServiceException;
	public IServiceResponse simplifiedRefunds(IAsqSubmitBnplTamraServiceRequest argRequest) throws ServiceException;
	public IServiceResponse cancelSession(IAsqSubmitBnplTamraServiceRequest argRequest) throws ServiceException;

}
