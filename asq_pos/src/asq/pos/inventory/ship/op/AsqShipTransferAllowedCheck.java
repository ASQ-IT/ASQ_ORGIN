/**
 * 
 */
package asq.pos.inventory.ship.op;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dtv.pos.common.ValueKeys;
import dtv.pos.framework.op.AbstractRunCondition;
import dtv.xst.dao.inv.IInventoryDocument;

/**
 * @author SA20547171
 *
 */
public class AsqShipTransferAllowedCheck extends AbstractRunCondition {
	private static final Logger LOG = LogManager.getLogger(AsqShipTransferAllowedCheck.class);

	@Override
	protected boolean shouldRunImpl() {
		IInventoryDocument doc = getScopedValue(ValueKeys.CURRENT_INV_DOC);
		return doc.getDocumentTypeCode().equalsIgnoreCase("SHIPPING")
				&& doc.getDocumentSubtypeCode().equalsIgnoreCase("STORE_TRANSFER");

	}
}
