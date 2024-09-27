package asq.pos.bnpl.tabby.tender.service;

import dtv.service.IService;
import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;

public interface IAsqBnplTabbyServices extends IService{
	
	public IServiceResponse createSession(IAsqSubmitBnplTabbyServiceRequest argRequest) throws ServiceException;
	public IServiceResponse notificationRequest(IAsqSubmitBnplTabbyServiceRequest argRequest) throws ServiceException;
	public IServiceResponse retrievePayment(IAsqSubmitBnplTabbyServiceRequest argRequest) throws ServiceException;
	public IServiceResponse refundPayment(IAsqSubmitBnplTabbyServiceRequest argRequest) throws ServiceException;
	public IServiceResponse cancelSession(IAsqSubmitBnplTabbyServiceRequest argRequest) throws ServiceException;


}
