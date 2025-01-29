package asq.pos.inventory.movement;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dtv.data2.access.DataFactory;
import dtv.pos.common.ConfigurationMgr;
import dtv.pos.common.ValueKeys;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.inventory.movement.PromptTransferItemChangeQtyOp;
import dtv.xst.dao.inv.IStockLedger;
import dtv.xst.dao.inv.StockLedgerId;
import dtv.xst.query.results.InventoryItemTransferResult;

public class AsqPromptTransferItemChangeQtyOp extends PromptTransferItemChangeQtyOp {
	private static final Logger LOG = LogManager.getLogger(AsqPromptTransferItemChangeQtyOp.class);

	@Override
	public IOpResponse handlePromptResponse(IXstEvent argEvent) {
		InventoryItemTransferResult result = getScopedValue(ValueKeys.CURRENT_ITEM_TRANSFER_ITEM);
		BigDecimal quantity = getScopedValue(ValueKeys.ENTERED_ITEM_QUANTITY);
		Boolean damagedItmQtyUnavailable = getDamagedItmQty(result.getItemId(), quantity);
		if (damagedItmQtyUnavailable) {
			return HELPER.getCompletePromptResponse("ASQ_BIN_TRANSFER_QTY_UNAVAILABLE");
		}
		result.setTransferQuantity(quantity);
		return this.HELPER.completeResponse();
	}

	protected boolean getDamagedItmQty(String itemId, BigDecimal enteredQty) {
		boolean damadedItmQtyUnavailableFlag = false;
		StockLedgerId id = new StockLedgerId();
		id.setOrganizationId(ConfigurationMgr.getOrganizationId());
		id.setRetailLocationId((long) this._stationState.getRetailLocationId());
		id.setItemId(itemId);
		id.setInvLocationId("DEFAULT");
		id.setBucketId("DAMAGED");
		try {
			IStockLedger stock = DataFactory.getObjectById(id);
			if (stock != null) {
				BigDecimal stockQty = new BigDecimal(0.00);
				stockQty = stock.getUnitcount();
				if (stockQty.compareTo(enteredQty) == 1) {
					// Entered Quantity Lesser than UnitCount
					return damadedItmQtyUnavailableFlag;
				} else if (stockQty.compareTo(enteredQty) == -1) {
					// Entered Quantity greater than UnitCount
					damadedItmQtyUnavailableFlag = true;
				} else if (stockQty.compareTo(enteredQty) == 0) {
					// Entered Quantity is equal to UnitCount
					return damadedItmQtyUnavailableFlag;
				}
			}
		} catch (Exception ex) {
			LOG.error("Setting Damage item Quantity not available to true as Damage item is bucket is not available");
			damadedItmQtyUnavailableFlag = true;
		}
		return damadedItmQtyUnavailableFlag;
	}
}
