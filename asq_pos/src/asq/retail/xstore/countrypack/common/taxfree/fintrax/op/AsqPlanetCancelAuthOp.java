/**
 * 
 */
package asq.retail.xstore.countrypack.common.taxfree.fintrax.op;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.planet.vat.claim.service.AsqPlanetVatClaimServiceRequest;
import asq.pos.planet.vat.claim.service.AsqPlanetVatClaimServiceResponse;
import asq.pos.planet.vat.claim.service.IAsqPlanetVatClaimServiceRequest;
import asq.pos.planet.vat.claim.service.IAsqPlanetVatClaimServices;
import asq.pos.zatca.AsqZatcaConstant;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trn.IPosTransaction;

/**
 * @author SA20547171 Cancel Planet tax auth
 */
public class AsqPlanetCancelAuthOp extends Operation {
	private static final Logger LOG = LogManager.getLogger(AsqPlanetCancelAuthOp.class);

	@Inject
	protected Provider<IAsqPlanetVatClaimServices> planetService;

	@Override
	public IOpResponse handleOpExec(IXstEvent var1) {
		if (var1 != null && (var1.getName() != null && var1.getName().equalsIgnoreCase("CONTINUE"))) {
			return HELPER.completeResponse();

		}

		IAsqPlanetVatClaimServiceRequest planetVatCancel = new AsqPlanetVatClaimServiceRequest();
		IPosTransaction argTrans = _transactionScope.getTransaction();

		planetVatCancel.setTagNumber(argTrans.getStringProperty(AsqZatcaConstant.ASQ_PLANET_TAX_ID));
		planetVatCancel.setNote(String.valueOf(argTrans.getRetailLocationId()).concat("-")
				.concat(String.valueOf(argTrans.getWorkstationId()).concat("-")
						.concat(String.valueOf(argTrans.getTransactionSequence()))));
		AsqPlanetVatClaimServiceResponse asqPlanetVatClaimServiceResponse = cancelVatClaim(planetVatCancel);
		return validateResponse(asqPlanetVatClaimServiceResponse);
	}

	private IOpResponse validateResponse(AsqPlanetVatClaimServiceResponse response) {
		LOG.info("Planet REDEEM API::::: " + response);
		return HELPER.completeResponse();
	}

	public AsqPlanetVatClaimServiceResponse cancelVatClaim(
			IAsqPlanetVatClaimServiceRequest asqPlanetVatClaimServiceRequest) {
		AsqPlanetVatClaimServiceResponse response = new AsqPlanetVatClaimServiceResponse();
		try {
			response = (AsqPlanetVatClaimServiceResponse) planetService.get()
					.cancelVatClaim(asqPlanetVatClaimServiceRequest);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	@Override
	public boolean isOperationApplicable() {
		IPosTransaction argTrans = _transactionScope.getTransaction();
		return argTrans.getStringProperty(AsqZatcaConstant.ASQ_PLANET_TAX_ID) != null;
	}

}
