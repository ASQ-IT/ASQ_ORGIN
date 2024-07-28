/**
 * 
 */
package asq.pos.loyalty.stc.tender.service;

import javax.inject.Inject;
import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;
import dtv.servicex.IServiceHandler;
import dtv.servicex.IServiceHandlerFactory;
import dtv.servicex.ServiceType;

/**
 * @author RA20221457
 *
 */
public class AsqSTCTenderServiceImpl implements IAsqSTCLoyaltyTenderService {
	
	@Inject
	private IServiceHandlerFactory _serviceHandlerFactory;
		
	private static final ServiceType<IAsqSTCLoyaltyServiceRequest, IServiceResponse> ASQ_STC_TENDER_REDEEM_SERVICE = new ServiceType<IAsqSTCLoyaltyServiceRequest, IServiceResponse>("ASQ_STC_TENDER_REDEEM_SERVICE");
	private static final ServiceType<IAsqSTCLoyaltyServiceRequest, IServiceResponse> ASQ_STC_TENDER_TRIGGER_OTP_SERVICE = new ServiceType<IAsqSTCLoyaltyServiceRequest, IServiceResponse>("ASQ_STC_TENDER_TRIGGER_OTP_SERVICE");

	@Override
	public IServiceResponse submitOTPRequest(IAsqSTCLoyaltyServiceRequest argRequest) throws ServiceException {
		
		try {
			IServiceHandler<IAsqSTCLoyaltyServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(ASQ_STC_TENDER_REDEEM_SERVICE);
			IServiceResponse response = serviceHandler.handleService(argRequest,ASQ_STC_TENDER_REDEEM_SERVICE);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public IServiceResponse triggerOTPRequest(IAsqSTCLoyaltyServiceRequest argRequest) throws ServiceException {
		
		try {
			IServiceHandler<IAsqSTCLoyaltyServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(ASQ_STC_TENDER_TRIGGER_OTP_SERVICE);
			IServiceResponse response = serviceHandler.handleService(argRequest,ASQ_STC_TENDER_TRIGGER_OTP_SERVICE);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}
}

	
	

