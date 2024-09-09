package asq.pos.loyalty.neqaty.tender.service;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;
import dtv.servicex.IServiceHandler;
import dtv.servicex.IServiceHandlerFactory;
import dtv.servicex.ServiceType;

/**
 * This class checks helps to implement all service calls
 * 
 * @author RA20221457
 */
public class AsqNeqatyServiceImpl implements IAsqNeqatyService {

	private static final Logger LOG = LogManager.getLogger(AsqNeqatyServiceImpl.class);

	@Inject
	private IServiceHandlerFactory _serviceHandlerFactory;

	private static final ServiceType<IAsqNeqatyServiceRequest, IServiceResponse> ASQ_NEQATY_SERVICE = new ServiceType<IAsqNeqatyServiceRequest, IServiceResponse>("ASQ_NEQATY_SRV");

//	@Override
//	public IServiceResponse inqueryWithOTP(IAsqNeqatyServiceRequest paramAsqSTCLoyalityRequest) throws ServiceException {
//		IServiceHandler<IAsqNeqatyServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(ASQ_NEQATY_SERVICE);
//		return serviceHandler.handleService(paramAsqSTCLoyalityRequest, ASQ_NEQATY_SERVICE);
//	}

	@Override
	public IServiceResponse redeemNeqityPoint(IAsqNeqatyServiceRequest paramAsqSTCLoyalityRequest) throws ServiceException {
		IServiceHandler<IAsqNeqatyServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(ASQ_NEQATY_SERVICE);
		return serviceHandler.handleService(paramAsqSTCLoyalityRequest, ASQ_NEQATY_SERVICE);
	}

	@Override
	public IServiceResponse earnNeqityPoint(IAsqNeqatyServiceRequest argRequest) throws ServiceException {

		return null;
	}

}
