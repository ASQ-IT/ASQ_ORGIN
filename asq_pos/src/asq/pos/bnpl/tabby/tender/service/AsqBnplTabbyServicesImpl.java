package asq.pos.bnpl.tabby.tender.service;

import javax.inject.Inject;

import asq.pos.bnpl.tamara.tender.service.IAsqSubmitBnplTamraServiceRequest;
import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;
import dtv.servicex.IServiceHandler;
import dtv.servicex.IServiceHandlerFactory;
import dtv.servicex.ServiceType;

public class AsqBnplTabbyServicesImpl implements IAsqBnplTabbyServices {
	
	@Inject
	private IServiceHandlerFactory _serviceHandlerFactory;

	private static final ServiceType<IAsqSubmitBnplTabbyServiceRequest, IServiceResponse> BNPL_TABBY_CREATE_SESSION_SRV = new ServiceType<IAsqSubmitBnplTabbyServiceRequest, IServiceResponse>("BNPL_TABBY_CREATE_SESSION_SRV");
	private static final ServiceType<IAsqSubmitBnplTabbyServiceRequest, IServiceResponse> BNPL_TABBY_NOTIFICATION_SRV = new ServiceType<IAsqSubmitBnplTabbyServiceRequest, IServiceResponse>("BNPL_TABBY_NOTIFICATION_SRV");
	private static final ServiceType<IAsqSubmitBnplTabbyServiceRequest, IServiceResponse> BNPL_TABBY_RETRIEVE_PAYMENT_SRV = new ServiceType<IAsqSubmitBnplTabbyServiceRequest, IServiceResponse>("BNPL_TABBY_RETRIEVE_PAYMENT_SRV");
	private static final ServiceType<IAsqSubmitBnplTabbyServiceRequest, IServiceResponse> BNPL_TABBY_REFUND_PAYMENT_SRV = new ServiceType<IAsqSubmitBnplTabbyServiceRequest, IServiceResponse>("BNPL_TABBY_REFUND_PAYMENT_SRV");
	private static final ServiceType<IAsqSubmitBnplTabbyServiceRequest, IServiceResponse> BNPL_TABBY_CANCEL_SESSION_SRV = new ServiceType<IAsqSubmitBnplTabbyServiceRequest, IServiceResponse>("BNPL_TABBY_CANCEL_SESSION_SRV");
	  
	@Override
	public IServiceResponse createSession(IAsqSubmitBnplTabbyServiceRequest argRequest) throws ServiceException {
		try {
			IServiceHandler<IAsqSubmitBnplTabbyServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(BNPL_TABBY_CREATE_SESSION_SRV);
			IServiceResponse response = serviceHandler.handleService(argRequest, BNPL_TABBY_CREATE_SESSION_SRV);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}


	@Override
	public IServiceResponse notificationRequest(IAsqSubmitBnplTabbyServiceRequest argRequest) throws ServiceException {
		try {
			IServiceHandler<IAsqSubmitBnplTabbyServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(BNPL_TABBY_NOTIFICATION_SRV);
			IServiceResponse response = serviceHandler.handleService(argRequest, BNPL_TABBY_NOTIFICATION_SRV);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}


	@Override
	public IServiceResponse retrievePayment(IAsqSubmitBnplTabbyServiceRequest argRequest) throws ServiceException {
		try {
			IServiceHandler<IAsqSubmitBnplTabbyServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(BNPL_TABBY_RETRIEVE_PAYMENT_SRV);
			IServiceResponse response = serviceHandler.handleService(argRequest, BNPL_TABBY_RETRIEVE_PAYMENT_SRV);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}


	@Override
	public IServiceResponse refundPayment(IAsqSubmitBnplTabbyServiceRequest argRequest) throws ServiceException {
		try {
			IServiceHandler<IAsqSubmitBnplTabbyServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(BNPL_TABBY_REFUND_PAYMENT_SRV);
			IServiceResponse response = serviceHandler.handleService(argRequest, BNPL_TABBY_REFUND_PAYMENT_SRV);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}


	@Override
	public IServiceResponse cancelSession(IAsqSubmitBnplTabbyServiceRequest argRequest) throws ServiceException {
		try {
			IServiceHandler<IAsqSubmitBnplTabbyServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(BNPL_TABBY_CANCEL_SESSION_SRV);
			IServiceResponse response = serviceHandler.handleService(argRequest, BNPL_TABBY_CANCEL_SESSION_SRV);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}

}
