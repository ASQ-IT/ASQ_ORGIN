package asq.pos.zatca.invoice.submition.worker.service;

import dtv.service.IService;
import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;

public interface IAsqZatcaInvoiceSubmittToOICService extends IService {

	IServiceResponse submitInvoiceToOIC(IAsqSubmitZatcaInvoiceToOicServiceRequest paramAsqSubmitZatcaInvoiceToOicServiceRequest) throws ServiceException;

}
