package asq.pos.zatca.cert.generation.service;

import javax.inject.Inject;

import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;
import dtv.servicex.IServiceHandler;
import dtv.servicex.IServiceHandlerFactory;
import dtv.servicex.ServiceType;

public class AsqZatcaCertRegistrationServices implements IAsqZatcaCertRegistrationServices {
	
	@Inject
	private IServiceHandlerFactory _serviceHandlerFactory;
		
	private static final ServiceType<IAsqSubmitZatcaCertServiceRequest, IServiceResponse> ZATCA_CERT_REST = new ServiceType<IAsqSubmitZatcaCertServiceRequest, IServiceResponse>("ZATCA_CERT_REST");

	@Override
	public IServiceResponse submitCertForRegistration(IAsqSubmitZatcaCertServiceRequest argRequest) throws ServiceException {
		try {
			IServiceHandler<IAsqSubmitZatcaCertServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(ZATCA_CERT_REST);
			IServiceResponse response = serviceHandler.handleService(argRequest,ZATCA_CERT_REST);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}

}
