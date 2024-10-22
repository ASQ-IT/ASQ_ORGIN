package asq.pos.planet.vat.claim.service;

import dtv.service.IService;
import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;

public interface IAsqPlanetVatClaimServices extends IService{
	
	public IServiceResponse vatRegistration(IAsqPlanetVatClaimServiceRequest argRequest) throws ServiceException;
	public IServiceResponse cancelVatClaim(IAsqPlanetVatClaimServiceRequest argRequest) throws ServiceException;
	public IServiceResponse getAuthToken(IAsqPlanetVatClaimServiceRequest argRequest) throws ServiceException;


}
