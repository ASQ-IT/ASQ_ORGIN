package asq.pos.zatca.invoice.submition.worker.service;

import javax.inject.Inject;

import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;
import dtv.servicex.IServiceHandler;
import dtv.servicex.IServiceHandlerFactory;
import dtv.servicex.ServiceType;

public class AsqZatcaInvoiceSubmittToOICServiceImpl implements IAsqZatcaInvoiceSubmittToOICService {

	@Inject
	private IServiceHandlerFactory _serviceHandlerFactory;

	private static final ServiceType<IAsqSubmitZatcaInvoiceToOicServiceRequest, IServiceResponse> ZATCA_INVOICE_TO_OIC = new ServiceType<IAsqSubmitZatcaInvoiceToOicServiceRequest, IServiceResponse>(
			"ZATCA_OIC_SRV");

	@Override
	public IServiceResponse submitInvoiceToOIC(IAsqSubmitZatcaInvoiceToOicServiceRequest argRequest) throws ServiceException {
		IServiceHandler<IAsqSubmitZatcaInvoiceToOicServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(ZATCA_INVOICE_TO_OIC);
		return serviceHandler.handleService(argRequest, ZATCA_INVOICE_TO_OIC);
	}
}
