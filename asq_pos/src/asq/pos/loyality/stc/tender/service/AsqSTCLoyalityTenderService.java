package asq.pos.loyality.stc.tender.service;

import javax.inject.Inject;

import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;
import dtv.servicex.IServiceHandler;
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
		IServiceHandler<IAsqSTCLoyalityServiceRequest, IServiceResponse> serviceHandler  = _serviceHandlerFactory.getServiceHandler(STC_REST);
		serviceHandler.handleService(paramAsqSTCLoyalityRequest, STC_REST);
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
