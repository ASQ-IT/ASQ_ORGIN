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

	private static final ServiceType<IAsqSubmitBnplTamraServiceRequest, IServiceResponse> BNPL_TAMARA_SRVC = new ServiceType<IAsqSubmitBnplTamraServiceRequest, IServiceResponse>("BNPL_TAMARA_SRV");

	@Override
	public IServiceResponse submitPaymentlinkToCustomer(IAsqSubmitBnplTamraServiceRequest argRequest) throws ServiceException {
		try {
			IServiceHandler<IAsqSubmitBnplTamraServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(BNPL_TAMARA_SRVC);
			IServiceResponse response = serviceHandler.handleService(argRequest, BNPL_TAMARA_SRVC);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}

}
