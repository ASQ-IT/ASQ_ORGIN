/**
 * 
 */
package asq.pos.loyalty.mokafaa.tender.service;

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

public class AsqMokafaaTenderServiceImpl implements IAsqMokafaaLoyaltyTenderService {
	

	/**
	 * This class checks helps to implement all service calls
	 */

	private static final Logger LOG = LogManager.getLogger(AsqMokafaaTenderServiceImpl.class);
	
	@Inject
	private IServiceHandlerFactory _serviceHandlerFactory;
	private static final ServiceType<IAsqMokafaaLoyaltyServiceRequest, IServiceResponse> ASQ_MOKAFAA_TENDER_REDEEM_SERVICE = new ServiceType<IAsqMokafaaLoyaltyServiceRequest, IServiceResponse>("ASQ_MOKAFAA_TENDER_REDEEM_SERVICE");
	private static final ServiceType<IAsqMokafaaLoyaltyServiceRequest, IServiceResponse> ASQ_MOKFAA_TENDER_TRIGGER_OTP_SERVICE = new ServiceType<IAsqMokafaaLoyaltyServiceRequest, IServiceResponse>("ASQ_MOKAFAA_TENDER_TRIGGER_OTP_SERVICE");
	private static final ServiceType<IAsqMokafaaLoyaltyServiceRequest, IServiceResponse> ASQ_MOKFAA_TENDER_AUTH_TOKEN_SERVICE = new ServiceType<IAsqMokafaaLoyaltyServiceRequest, IServiceResponse>("ASQ_MOKFAA_TENDER_AUTH_TOKEN_SERVICE");
	private static final ServiceType<IAsqMokafaaLoyaltyServiceRequest, IServiceResponse> ASQ_MOKFAA_TENDER_REFUND_REDEEM_SERVICE = new ServiceType<IAsqMokafaaLoyaltyServiceRequest, IServiceResponse>("ASQ_MOKFAA_TENDER_REFUND_REDEEM_SERVICE");

	/**
	 * This method submits Mokafa'a API OTP request
	 * @param  argRequest
	 */
	
	@Override
	public IServiceResponse submitOTPRequest(IAsqMokafaaLoyaltyServiceRequest argRequest) throws ServiceException {
		
		try {
			IServiceHandler<IAsqMokafaaLoyaltyServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(ASQ_MOKAFAA_TENDER_REDEEM_SERVICE);
			LOG.info("Mokafa'a API SubmitOTPRequest Service call starts here:");
			IServiceResponse response = serviceHandler.handleService(argRequest,ASQ_MOKAFAA_TENDER_REDEEM_SERVICE);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	/**
	 * This method Triggers Mokafa'a API OTP request
	 * @param  argRequest
	 */
	
	@Override
	public IServiceResponse triggerOTPRequest(IAsqMokafaaLoyaltyServiceRequest argRequest) throws ServiceException {
		
		try {
			IServiceHandler<IAsqMokafaaLoyaltyServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(ASQ_MOKFAA_TENDER_TRIGGER_OTP_SERVICE);
			LOG.info("Mokafa'a API TriggerOTPRequest Service call starts here:");
			IServiceResponse response = serviceHandler.handleService(argRequest,ASQ_MOKFAA_TENDER_TRIGGER_OTP_SERVICE);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public IServiceResponse getAuthTokenCall(IAsqMokafaaLoyaltyServiceRequest argRequest) throws ServiceException {
		
		try {
			IServiceHandler<IAsqMokafaaLoyaltyServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(ASQ_MOKFAA_TENDER_AUTH_TOKEN_SERVICE);
			LOG.info("Mokafa'a API get auth Service call starts here:");
			IServiceResponse response = serviceHandler.handleService(argRequest,ASQ_MOKFAA_TENDER_AUTH_TOKEN_SERVICE);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public IServiceResponse refundRedeem(IAsqMokafaaLoyaltyServiceRequest argRequest) throws ServiceException {
		
		try {
			IServiceHandler<IAsqMokafaaLoyaltyServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(ASQ_MOKFAA_TENDER_REFUND_REDEEM_SERVICE);
			LOG.info("Mokafa'a API refundRedeem Service call starts here:");
			IServiceResponse response = serviceHandler.handleService(argRequest,ASQ_MOKFAA_TENDER_REFUND_REDEEM_SERVICE);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}
}

	
	

