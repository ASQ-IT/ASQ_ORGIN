/**
 * 
 */
package asq.pos.inventory;

import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import asq.pos.common.AsqConfigurationMgr;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.op.AbstractRunCondition;
import dtv.pos.framework.scope.TransactionScope;
import dtv.xst.dao.inv.IInventoryLocation;
import dtv.xst.dao.inv.IInventoryLocationBucket;

/**
 * @author RA20221457
 *
 */
public class AsqBinTransferAllowedCheck extends AbstractRunCondition {
	private static final Logger LOG = LogManager.getLogger(AsqBinTransferAllowedCheck.class);

	@Inject
	private AsqConfigurationMgr _sysConfig;

	protected static final String XSTORE_BUCKET_ON_HAND = "ON_HAND";
	protected static final String XSTORE_BUCKET_DAMAGED = "DAMAGED";
	String sourceBucket = null;
	String destnBucket = null;
	Long sourceLocationId = null;
	Long destnLocationId = null;

	@Override
	protected boolean shouldRunImpl() {

		if (_sysConfig.isBinTransferEmailApproved()) {
			try {
				sourceLocationId = getScopedValue(ValueKeys.SELECTED_INVENTORY_SOURCE_LOCATION).getRetailLocationId();
				destnLocationId = getScopedValue(ValueKeys.SELECTED_INVENTORY_LOCATION).getRetailLocationId();
				sourceBucket = getScopedValue(ValueKeys.SELECTED_INVENTORY_SOURCE_LOCATION_BUCKET).getBucketId();
				destnBucket = getScopedValue(ValueKeys.SELECTED_INVENTORY_LOCATION_BUCKET).getBucketId();
				if (sourceLocationId.equals(destnLocationId)) {
					if ((XSTORE_BUCKET_DAMAGED.equalsIgnoreCase(sourceBucket))
							&& (XSTORE_BUCKET_ON_HAND.equalsIgnoreCase(destnBucket))) {
						return true;
					}
				}
			} catch (Exception exception) {
				LOG.error("Exception from Return OTP form in Handle Initial state :" + exception);
			}
		}
		return false;
	}
}
