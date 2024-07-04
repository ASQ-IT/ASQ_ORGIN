package dtv.pos.tender;

import dtv.hardware.HardwareMgr;
import dtv.pos.framework.appmanagement.preflight.check.IPreFlightCheck;
import dtv.pos.framework.appmanagement.preflight.result.IPreFlightCheckResult;
import dtv.pos.framework.appmanagement.preflight.result.PreFlightCheckResult;
import dtv.xst.cleandto.tnd.Tender;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MissingStoreSafeTendersPreflightCheck extends AbstractSessionTendersPreflightCheck {
	
	private static final Logger LOG = LogManager.getLogger(MissingStoreSafeTendersPreflightCheck.class);
	
	protected IPreFlightCheckResult evaluateTendersResult(Collection<? extends Tender> argAllTenders,
			Set<String> argSessionTenders) {
		LOG.debug("argSessionTenders " + argSessionTenders);
		LOG.debug("argAllTenders " + argAllTenders);
		if (argSessionTenders.isEmpty() && !argAllTenders.isEmpty()) {
			return getFailed();
		}
		
		for (String sess : argSessionTenders) {
			LOG.debug("SessionTenders  " + sess);
		}
		
		if (!argAllTenders.isEmpty())
			for (Tender t : argAllTenders) {
				LOG.debug("Tender t.getTenderId() " + t.getTenderId());
				if (!argSessionTenders.contains(t.getTenderId())) {
					LOG.debug("Tender not present " + t.getTenderId());
					return getFailed();
				}
			}
		LOG.debug("Tender present getQueryKey "  + getQueryKey());
		return PreFlightCheckResult.getPassed((IPreFlightCheck) this);
	}

	protected Map<String, Object> getQueryParameters() {
		Map<String, Object> queryParameters = super.getQueryParameters();
		queryParameters.put("argRepositoryTypeCode", "STOREBANK");
		return queryParameters;
	}
}