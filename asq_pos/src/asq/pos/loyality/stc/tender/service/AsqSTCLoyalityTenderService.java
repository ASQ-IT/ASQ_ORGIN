package asq.pos.loyality.stc.tender.service;

import javax.inject.Inject;

import asq.pos.zatca.cert.generation.service.IAsqSubmitZatcaCertServiceRequest;
import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;
import dtv.servicex.IServiceHandlerFactory;
import dtv.servicex.ServiceType;

public class AsqSTCLoyalityTenderService implements IAsqSTCLoyalityTenderService {

	@Inject
	private IServiceHandlerFactory _serviceHandlerFactory;
		
	private static final ServiceType<IAsqSTCLoyalityServiceRequest, IServiceResponse> STC_REST = new ServiceType<IAsqSTCLoyalityServiceRequest, IServiceResponse>("STC_REST");

	
	@Override
	public IServiceResponse submitOTPRequest(AsqSTCLoyalityServiceRequest paramAsqSTCLoyalityRequest)
			throws ServiceException {
		// TODO Auto-generated method stub
		
		IServiceResponse response = _serviceHandlerFactory.handleService(paramAsqSTCLoyalityRequest,STC_REST);
		return null;
	}

	@Override
	public IServiceResponse submitRedeemRequest(AsqSTCLoyalityServiceRequest paramAsqSTCLoyalityRequest)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IServiceResponse submitReverseRedeemRequest(AsqSTCLoyalityServiceRequest paramAsqSTCLoyalityRequest)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
