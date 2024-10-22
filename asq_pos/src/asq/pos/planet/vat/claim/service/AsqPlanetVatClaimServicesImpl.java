package asq.pos.planet.vat.claim.service;

import javax.inject.Inject;

import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;
import dtv.servicex.IServiceHandler;
import dtv.servicex.IServiceHandlerFactory;
import dtv.servicex.ServiceType;

public class AsqPlanetVatClaimServicesImpl implements IAsqPlanetVatClaimServices {
	@Inject
	private IServiceHandlerFactory _serviceHandlerFactory;

	private static final ServiceType<IAsqPlanetVatClaimServiceRequest, IServiceResponse> PLANET_VAT_CLAIM_REG_SRV = new ServiceType<IAsqPlanetVatClaimServiceRequest, IServiceResponse>("PLANET_VAT_CLAIM_REG_SRV");
	private static final ServiceType<IAsqPlanetVatClaimServiceRequest, IServiceResponse> PLANET_VAT_CLAIM_CANCEL_SRV = new ServiceType<IAsqPlanetVatClaimServiceRequest, IServiceResponse>("PLANET_VAT_CLAIM_CANCEL_SRV");
	private static final ServiceType<IAsqPlanetVatClaimServiceRequest, IServiceResponse> PLANET_AUTH_TOKEN_SRV = new ServiceType<IAsqPlanetVatClaimServiceRequest, IServiceResponse>("PLANET_AUTH_TOKEN_SRV");
	  
	@Override
	public IServiceResponse vatRegistration(IAsqPlanetVatClaimServiceRequest argRequest) throws ServiceException {
		try {
			AsqPlanetVatClaimServiceResponse asqPlanetVatClaimServiceResponse = (AsqPlanetVatClaimServiceResponse)getAuthToken(argRequest);
			argRequest.setToken(asqPlanetVatClaimServiceResponse.getAccess_token());
			IServiceHandler<IAsqPlanetVatClaimServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(PLANET_VAT_CLAIM_REG_SRV);
			return serviceHandler.handleService(argRequest, PLANET_VAT_CLAIM_REG_SRV);
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public IServiceResponse cancelVatClaim(IAsqPlanetVatClaimServiceRequest argRequest) throws ServiceException {
		try {
			IServiceHandler<IAsqPlanetVatClaimServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(PLANET_VAT_CLAIM_CANCEL_SRV);
			IServiceResponse response = serviceHandler.handleService(argRequest, PLANET_VAT_CLAIM_CANCEL_SRV);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public IServiceResponse getAuthToken(IAsqPlanetVatClaimServiceRequest argRequest) throws ServiceException {
		try {
			IServiceHandler<IAsqPlanetVatClaimServiceRequest, IServiceResponse> serviceHandler = _serviceHandlerFactory.getServiceHandler(PLANET_AUTH_TOKEN_SRV);
			IServiceResponse response = serviceHandler.handleService(argRequest, PLANET_AUTH_TOKEN_SRV);
			return response;
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}


}
