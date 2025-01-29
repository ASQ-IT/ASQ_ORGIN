package asq.pos.inventory.movement;

import dtv.data2.access.DataFactory;
import dtv.hardware.types.HardwareType;
import dtv.pos.common.ConfigurationMgr;
import dtv.pos.common.SysConfigAccessor;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.validation.ValidationWithoutPromptOp;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.hardware.IHardwareType;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.inventory.movement.ValidateTransferItemQtyOp;
import dtv.xst.dao.inv.IStockLedger;
import dtv.xst.dao.inv.StockLedgerId;
import dtv.xst.dao.itm.IItem;
import dtv.xst.query.results.InventoryItemTransferResult;
import java.math.BigDecimal;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqConfigurationMgr;
import asq.pos.loyalty.stc.tender.service.AsqSTCloyaltyServiceHandler;
import asq.pos.zatca.AsqZatcaConstant;

public class AsqValidateTransferItemQtyOp extends ValidateTransferItemQtyOp {
	private static final Logger LOG = LogManager.getLogger(AsqValidateTransferItemQtyOp.class);
	
	 @Inject
		private AsqConfigurationMgr sysConfig;
	 @Inject
	  private SysConfigAccessor _sysConfig;
	 
	 protected static final String XSTORE_BUCKET_DAMAGED = "DAMAGED";
		String sourceBucket = null;

	@Override
	protected IOpResponse handleValid(IXstEvent argEvent) {
		InventoryItemTransferResult result = (InventoryItemTransferResult) getScopedValue(
				ValueKeys.CURRENT_ITEM_TRANSFER_ITEM);
		sourceBucket = getScopedValue(ValueKeys.SELECTED_INVENTORY_SOURCE_LOCATION_BUCKET).getBucketId();
		BigDecimal quantity = (BigDecimal) getScopedValue(ValueKeys.ENTERED_ITEM_QUANTITY);
		 if (sourceBucket!=null && sourceBucket.equalsIgnoreCase(XSTORE_BUCKET_DAMAGED)) {
		Boolean damagedItmQtyUnavailable = getDamagedItmQty(result.getItemId());
		if (damagedItmQtyUnavailable) {
			return HELPER.getCompletePromptResponse("ASQ_BIN_TRANSFER_QTY_UNAVAILABLE");
		}
		 }
		result.setTransferQuantity(result.getTransferQuantity().add(quantity));
		return super.handleValid(argEvent);
	}

	protected boolean getDamagedItmQty(String itemId) {
		boolean damadedItmQtyUnavailableFlag = true;
		int damagedItmQty = 0;
		StockLedgerId id = new StockLedgerId();
		id.setOrganizationId(ConfigurationMgr.getOrganizationId());
		id.setRetailLocationId((long) this._stationState.getRetailLocationId());
		id.setItemId(itemId);
		id.setInvLocationId("DEFAULT");
		id.setBucketId("DAMAGED");
		try {
			IStockLedger stock = DataFactory.getObjectById(id);
			if ((stock != null) && (stock.getUnitcount().intValue() <= 0)) {
				damadedItmQtyUnavailableFlag = false;
			}
		} catch (Exception ex) {
			LOG.error("Setting Damage item Quantity not available to true as Damage item is bucket is not available");
			damadedItmQtyUnavailableFlag = false;
		}
		return damadedItmQtyUnavailableFlag;
	}
}
