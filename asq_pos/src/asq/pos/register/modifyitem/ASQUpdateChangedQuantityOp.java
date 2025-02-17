package asq.pos.register.modifyitem;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.inject.Inject;

import asq.pos.common.AsqConstant;
import asq.pos.common.AsqValueKeys;
import asq.pos.register.sale.AsqHelper;
import dtv.i18n.IFormattable;
import dtv.pos.common.ValueKeys;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.register.modifyitem.UpdateChangedQuantityOp;
import dtv.xst.dao.inv.IStockLedger;
import dtv.xst.dao.itm.IItem;
import dtv.xst.dao.itm.IItemOptions;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import oracle.retail.xstore.inv.impl.InventoryStockAdjuster;

public class ASQUpdateChangedQuantityOp extends UpdateChangedQuantityOp {

	@Inject
	AsqHelper asqHelper;

	@Inject
	InventoryStockAdjuster stockAdjuster;

	@Override
	public IOpResponse handleOpExec(IXstEvent argEvent) {
		ISaleReturnLineItem lineItem = getScopedValue(ValueKeys.CURRENT_SALE_LINE);
		if (asqHelper.isTOLAItem(lineItem)) {
			BigDecimal quantity = getScopedValue(ValueKeys.ENTERED_ITEM_QUANTITY);
			if (quantity != null) {
				IItem superInventoriedItem = asqHelper.getSuperParentItem(lineItem.getItem());
				if (null != superInventoriedItem) {
					IStockLedger ledger = stockAdjuster.getStockLedger(superInventoriedItem.getItemId(), AsqConstant.ASQ_DEFAULT, AsqConstant.ASQ_ON_HAND, _stationState.getRetailLocationId());
					BigDecimal enteredWeight = lineItem.getDecimalProperty(AsqConstant.ASQ_INVENTORY_TOLA_ITEM_WEIGHT);

					HashMap<String, BigDecimal> tolaWeight = _transactionScope.getValue(AsqValueKeys.ASQ_TOLA_WEIGHT);
					if (null == tolaWeight) {
						tolaWeight = new HashMap<String, BigDecimal>();
					}

					if (null != ledger && null != enteredWeight) {
						quantity = quantity.subtract(lineItem.getGrossQuantity());
						BigDecimal calculatedWeight = enteredWeight.multiply(quantity);

						calculatedWeight = calculatedWeight.add(tolaWeight.get(ledger.getItemId()));

						IOpResponse errorResponse = validateTolaQuantitySOH(ledger, calculatedWeight, lineItem);
						if (errorResponse != null) {
							return errorResponse;
						}

						BigDecimal qty = ledger.getUnitcount();
						qty = qty.subtract(calculatedWeight);
						ledger.setUnitcount(qty);

						// remove the existing Persistables
						asqHelper.removeAndAddStockLedger(_transactionHelper, ledger);

						tolaWeight.put(ledger.getItemId(), calculatedWeight);
						_transactionScope.setValue(AsqValueKeys.ASQ_TOLA_WEIGHT, tolaWeight);
					}
				}
			}
		}
		return super.handleOpExec(argEvent);
	}

	public IOpResponse validateTolaQuantitySOH(IStockLedger ledger, BigDecimal argEnteredWeight, ISaleReturnLineItem lineItem) {
		IItemOptions itemToMeasure = lineItem.getItem().getItemOptions().get(0);
		String measureCode = itemToMeasure.getUnitOfMeasureCode();

		IFormattable formatMeasureCode = this._formattables.getSimpleFormattable(measureCode);
		IFormattable formatWeight = this._formattables.getSimpleFormattable(String.valueOf(argEnteredWeight));
		IFormattable[] args = new IFormattable[] { formatWeight, formatMeasureCode };

		if (null != argEnteredWeight && ledger.getUnitcount().subtract(argEnteredWeight).floatValue() < 0) {
			IFormattable sohUnit = this._formattables.getSimpleFormattable(String.valueOf(ledger.getUnitcount()));
			args = new IFormattable[] { formatWeight, sohUnit };
			return this.HELPER.getPromptResponse(AsqConstant.ASQ_TOLA_QTY_SOH_ERROR, args);
		}
		return null;
	}
}
