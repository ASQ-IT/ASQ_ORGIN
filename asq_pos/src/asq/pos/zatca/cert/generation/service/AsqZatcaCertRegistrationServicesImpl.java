package asq.pos.zatca.cert.generation.service;

import javax.inject.Inject;

import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;
import dtv.servicex.IServiceHandler;
import dtv.servicex.IServiceHandlerFactory;
import dtv.servicex.ServiceType;

public class AsqZatcaCertRegistrationServicesImpl implements IAsqZatcaCertRegistrationServices {
	
	@Inject
	private IServiceHandlerFactory _serviceHandlerFactory;
		
	private static final ServiceType<IAsqSubmitZatcaCertServiceRequest, IServiceResponse> ZATCA_CERT_GEN_SRV = new ServiceType<IAsqSubmitZatcaCertServiceRequest, IServiceResponse>("ZATCA_CERT_GEN_SRV");

	private static final ServiceType<IAsqSubmitZatcaCertServiceRequest, IServiceResponse> ZATCA_CERT_GEN_INVOICE_SRV = new ServiceType<IAsqSubmitZatcaCertServiceRequest, IServiceResponse>("ZATCA_CERT_GEN_INVOICE_SRV");
	
	private static final ServiceType<IAsqSubmitZatcaCertServiceRequest, IServiceResponse> ZATCA_CERT_CSIDS_SRV = new ServiceType<IAsqSubmitZatcaCertServiceRequest, IServiceResponse>("ZATCA_CERT_CSIDS_SRV");
	
	@Override
	public IServiceResponse submitCertForRegistration(IAsqSubmitZatcaCertServiceRequest argRequest) throws ServiceException {
		try {
			IServiceHandler<IAsqSubmitZatcaCertServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(ZATCA_CERT_GEN_SRV);
			IServiceResponse response = serviceHandler.handleService(argRequest,ZATCA_CERT_GEN_SRV);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public IServiceResponse submitInvoiceForRegistration(
			IAsqSubmitZatcaCertServiceRequest paramAsqISubmitZatcaCertServiceRequest) throws ServiceException {
		try {
			IServiceHandler<IAsqSubmitZatcaCertServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(ZATCA_CERT_GEN_INVOICE_SRV);
			IServiceResponse response = serviceHandler.handleService(paramAsqISubmitZatcaCertServiceRequest,ZATCA_CERT_GEN_INVOICE_SRV);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public IServiceResponse submitCSIDSForRegistration(
			IAsqSubmitZatcaCertServiceRequest paramAsqISubmitZatcaCertServiceRequest) throws ServiceException {
		try {
			IServiceHandler<IAsqSubmitZatcaCertServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(ZATCA_CERT_CSIDS_SRV);
			IServiceResponse response = serviceHandler.handleService(paramAsqISubmitZatcaCertServiceRequest,ZATCA_CERT_CSIDS_SRV);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}

}
