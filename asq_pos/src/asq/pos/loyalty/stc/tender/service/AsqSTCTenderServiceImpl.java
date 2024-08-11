/**
 * 
 */
package asq.pos.loyalty.stc.tender.service;

import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	

	/**
	 * This class checks helps to implement all service calls
	 */

	private static final Logger LOG = LogManager.getLogger(AsqSTCTenderServiceImpl.class);
	
	@Inject
	private IServiceHandlerFactory _serviceHandlerFactory;
	private static final ServiceType<IAsqSTCLoyaltyServiceRequest, IServiceResponse> ASQ_STC_TENDER_REDEEM_SERVICE = new ServiceType<IAsqSTCLoyaltyServiceRequest, IServiceResponse>("ASQ_STC_TENDER_REDEEM_SERVICE");
	private static final ServiceType<IAsqSTCLoyaltyServiceRequest, IServiceResponse> ASQ_STC_TENDER_TRIGGER_OTP_SERVICE = new ServiceType<IAsqSTCLoyaltyServiceRequest, IServiceResponse>("ASQ_STC_TENDER_TRIGGER_OTP_SERVICE");
	private static final ServiceType<IAsqSTCLoyaltyServiceRequest, IServiceResponse> ASQ_STC_EARN_REWARD_SERVICE = new ServiceType<IAsqSTCLoyaltyServiceRequest, IServiceResponse>("ASQ_STC_EARN_REWARD_SERVICE");
	
	/**
	 * This method submits STC API OTP request
	 * @param  argRequest
	 */
	
	@Override
	public IServiceResponse submitOTPRequest(IAsqSTCLoyaltyServiceRequest argRequest) throws ServiceException {
		
		try {
			IServiceHandler<IAsqSTCLoyaltyServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(ASQ_STC_TENDER_REDEEM_SERVICE);
			LOG.info("STC API SubmitOTPRequest Service call starts here:");
			IServiceResponse response = serviceHandler.handleService(argRequest,ASQ_STC_TENDER_REDEEM_SERVICE);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	/**
	 * This method Triggers STC API OTP request
	 * @param  argRequest
	 */
	
	@Override
	public IServiceResponse triggerOTPRequest(IAsqSTCLoyaltyServiceRequest argRequest) throws ServiceException {
		
		try {
			IServiceHandler<IAsqSTCLoyaltyServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(ASQ_STC_TENDER_TRIGGER_OTP_SERVICE);
			LOG.info("STC API TriggerOTPRequest Service call starts here:");
			IServiceResponse response = serviceHandler.handleService(argRequest,ASQ_STC_TENDER_TRIGGER_OTP_SERVICE);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	/**
	 * This method submits STC API Earn Reward request
	 * @param  argRequest
	 */
	
	@Override
	public IServiceResponse earnReward(IAsqSTCLoyaltyServiceRequest argRequest) throws ServiceException {
		
		try {
			IServiceHandler<IAsqSTCLoyaltyServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(ASQ_STC_TENDER_TRIGGER_OTP_SERVICE);
			LOG.info("STC API Earn Reward Service call starts here:");
			IServiceResponse response = serviceHandler.handleService(argRequest,ASQ_STC_EARN_REWARD_SERVICE);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}
}

	
	

