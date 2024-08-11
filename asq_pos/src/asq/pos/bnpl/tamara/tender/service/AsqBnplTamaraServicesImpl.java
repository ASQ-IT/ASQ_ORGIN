package asq.pos.bnpl.tamara.tender.service;

import javax.inject.Inject;

import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;
import dtv.servicex.IServiceHandler;
import dtv.servicex.IServiceHandlerFactory;
import dtv.servicex.ServiceType;

public class AsqBnplTamaraServicesImpl implements IAsqBnplTamaraServices {

	@Inject
	private IServiceHandlerFactory _serviceHandlerFactory;

	private static final ServiceType<IAsqSubmitBnplTamraServiceRequest, IServiceResponse> BNPL_TAMARA_CREATE_SESSION_SRV = new ServiceType<IAsqSubmitBnplTamraServiceRequest, IServiceResponse>("BNPL_TAMARA_CREATE_SESSION_SRV");
	private static final ServiceType<IAsqSubmitBnplTamraServiceRequest, IServiceResponse> BNPL_TAMARA_ORDER_DETAIL_SRV = new ServiceType<IAsqSubmitBnplTamraServiceRequest, IServiceResponse>("BNPL_TAMARA_ORDER_DETAIL_SRV");
	private static final ServiceType<IAsqSubmitBnplTamraServiceRequest, IServiceResponse> BNPL_TAMARA_REFUND_SRV = new ServiceType<IAsqSubmitBnplTamraServiceRequest, IServiceResponse>("BNPL_TAMARA_REFUND_SRV");
	private static final ServiceType<IAsqSubmitBnplTamraServiceRequest, IServiceResponse> BNPL_TAMARA_CANCEL_SESSION_SRV = new ServiceType<IAsqSubmitBnplTamraServiceRequest, IServiceResponse>("BNPL_TAMARA_CANCEL_SESSION_SRV");
	

	@Override
	public IServiceResponse createInStoreCheckoutSession(IAsqSubmitBnplTamraServiceRequest argRequest) throws ServiceException {
		try {
			IServiceHandler<IAsqSubmitBnplTamraServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(BNPL_TAMARA_CREATE_SESSION_SRV);
			IServiceResponse response = serviceHandler.handleService(argRequest, BNPL_TAMARA_CREATE_SESSION_SRV);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public IServiceResponse getOrderDetails(IAsqSubmitBnplTamraServiceRequest argRequest) throws ServiceException {
		try {
			IServiceHandler<IAsqSubmitBnplTamraServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(BNPL_TAMARA_ORDER_DETAIL_SRV);
			IServiceResponse response = serviceHandler.handleService(argRequest, BNPL_TAMARA_ORDER_DETAIL_SRV);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}	
	}

	@Override
	public IServiceResponse simplifiedRefunds(IAsqSubmitBnplTamraServiceRequest argRequest) throws ServiceException {
		try {
			IServiceHandler<IAsqSubmitBnplTamraServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(BNPL_TAMARA_REFUND_SRV);
			IServiceResponse response = serviceHandler.handleService(argRequest, BNPL_TAMARA_REFUND_SRV);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public IServiceResponse cancelSession(IAsqSubmitBnplTamraServiceRequest argRequest) throws ServiceException {
		try {
			IServiceHandler<IAsqSubmitBnplTamraServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(BNPL_TAMARA_CANCEL_SESSION_SRV);
			IServiceResponse response = serviceHandler.handleService(argRequest, BNPL_TAMARA_CANCEL_SESSION_SRV);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}
	

}
