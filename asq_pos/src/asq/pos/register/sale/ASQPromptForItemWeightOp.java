package asq.pos.register.sale;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.inject.Inject;

import asq.pos.common.AsqValueKeys;
import dtv.i18n.FormatterType;
import dtv.i18n.IFormattable;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.ui.prompt.PromptOverrideProperties;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.inventory.replenishment.lookup.ItemLookupHelper;
import dtv.pos.item.barcode.ItemWeightEntryMethodCode;
import dtv.pos.register.sale.PromptForItemWeightOp;
import dtv.util.ArrayUtils;
import dtv.xst.dao.inv.IStockLedger;
import dtv.xst.dao.itm.IItem;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import oracle.retail.xstore.inv.impl.InventoryStockAdjuster;

public class ASQPromptForItemWeightOp extends PromptForItemWeightOp {

	@Inject
	ItemLookupHelper itemHelper;

	@Inject
	InventoryStockAdjuster stockAdjuster;

	@Override
	public boolean isOperationApplicable() {
		IItem item = getScopedValue(ValueKeys.CURRENT_ITEM);
		IItem superInventoriedItem = getSuperParentItem(item);
		if (null != superInventoriedItem) {
			return superInventoriedItem.getMeasurementRequired();
		}
		return (getScopedValue(ValueKeys.ENTERED_ITEM_WEIGHT) != null) ? false : getScopedValue(ValueKeys.CURRENT_ITEM).getMeasurementRequired();
	}

	@Override
	public IOpResponse getPromptResponse(IXstEvent argEvent, String argPromptKey, IFormattable[] argPromptArgs) {
		IItem item = getScopedValue(ValueKeys.CURRENT_ITEM);

		PromptOverrideProperties overrides = new PromptOverrideProperties();
		IItem superInventoriedItem = getSuperParentItem(item);
		if (null != superInventoriedItem) {
			overrides.setMaxFractionalDigits(Integer.valueOf(superInventoriedItem.getOptions().getQtyScale()));
		} else {
			overrides.setMaxFractionalDigits(Integer.valueOf(item.getOptions().getQtyScale()));
		}
		overrides.setDefaultValue(BigDecimal.ZERO);
		return this.HELPER.getPromptResponse(argPromptKey, overrides, argPromptArgs);
	}

	@Override
	protected IFormattable[] getPromptArgs(IXstEvent argEvent) {
		IFormattable[] args = super.getPromptArgs(argEvent);
		IItem item = getScopedValue(ValueKeys.CURRENT_ITEM);
		IItem superInventoriedItem = getSuperParentItem(item);
		if (null != superInventoriedItem) {
			return (IFormattable[]) ArrayUtils.insert((Object[]) args,
					this._formattables.getSimpleFormattable(superInventoriedItem.getOptions().getUnitOfMeasureCode(), FormatterType.UNIT_OF_MEASURE_NAME), 0);
		} else {
			return (IFormattable[]) ArrayUtils.insert((Object[]) args, this._formattables.getSimpleFormattable(item.getOptions().getUnitOfMeasureCode(), FormatterType.UNIT_OF_MEASURE_NAME), 0);
		}
	}

	@Override
	public IOpResponse handlePromptResponse(IXstEvent argEvent) {
		ISaleReturnLineItem lineItem = getScopedValue(ValueKeys.CURRENT_SALE_LINE);
		BigDecimal enteredWeight = getScopedValue(ValueKeys.ENTERED_ITEM_WEIGHT);
		IItem superInventoriedItem = getSuperParentItem(lineItem.getItem());
		if (null != superInventoriedItem) {
			IStockLedger ledger = stockAdjuster.getStockLedger(superInventoriedItem.getItemId(), "DEFAULT", "ON_HAND", _stationState.getRetailLocationId());
			IOpResponse errorResponse = validateTolaQuantitySOH(ledger, enteredWeight, lineItem);
			if (errorResponse != null) {
				return errorResponse;
			}
			BigDecimal qty = ledger.getUnitcount();
			qty = qty.subtract(enteredWeight);
			ledger.setUnitcount(qty);
			ledger.setStringProperty("Last_Weight_Entered_", String.valueOf(qty));
			_transactionHelper.addPersistable(ledger);
		} else {
			lineItem.setQuantity(enteredWeight);
			lineItem.setQuantityToAllocate(enteredWeight);
			lineItem.setWeightEntryMethodCode(ItemWeightEntryMethodCode.KEYBOARD.name());
			setScopedValue(ValueKeys.CURRENT_INVENTORIED_LINE_ITEM, lineItem);
		}
		return this.HELPER.completeResponse();
	}

	public IItem getSuperParentItem(IItem item) {
		IItem itemTola = null;
		if (null != item.getItemOptions() && item.getItemOptions().size() > 0 && item.getItemOptions().get(0).getUnitOfMeasureCode().contains("TOLA")) {
			if (null != item.getParentItem() && null != item.getParentItem().getParentItem()) {
				itemTola = item.getParentItem().getParentItem();
			}
		}
		return itemTola;
	}

	public IOpResponse validateTolaQuantitySOH(IStockLedger ledger, BigDecimal argEnteredWeight, ISaleReturnLineItem lineItem) {
		HashMap<String, BigDecimal> tolaWeight = _transactionScope.getValue(AsqValueKeys.ASQ_TOLA_WEIGHT);

		String measureCode = lineItem.getItem().getItemOptions().get(0).getUnitOfMeasureCode();

		IFormattable formatMeasureCode = this._formattables.getSimpleFormattable(measureCode);
		IFormattable formatWeight = this._formattables.getSimpleFormattable(String.valueOf(argEnteredWeight));
		IFormattable[] args = new IFormattable[] { formatWeight, formatMeasureCode };

		if (measureCode.equalsIgnoreCase("TOLA") && (argEnteredWeight.compareTo(new BigDecimal(13)) == 1 || argEnteredWeight.compareTo(new BigDecimal(11)) == -1)) {
			return this.HELPER.getPromptResponse("ASQ_TOLA_WEIGHT_RANGE_ERROR", args);
		} else if (measureCode.equalsIgnoreCase("HTOLA") && (argEnteredWeight.compareTo(new BigDecimal(7)) == 1 || argEnteredWeight.compareTo(new BigDecimal(5)) == -1)) {
			return this.HELPER.getPromptResponse("ASQ_TOLA_WEIGHT_RANGE_ERROR", args);
		} else if (measureCode.equalsIgnoreCase("QTOLA") && (argEnteredWeight.compareTo(new BigDecimal(4)) == 1 || argEnteredWeight.compareTo(new BigDecimal(2)) == -1)) {
			return this.HELPER.getPromptResponse("ASQ_TOLA_WEIGHT_RANGE_ERROR", args);
		}

		BigDecimal calulatedWeight = new BigDecimal(0);
		if (null != tolaWeight && !tolaWeight.isEmpty()) {
			calulatedWeight = tolaWeight.get(ledger.getItemId());
			calulatedWeight = calulatedWeight.add(argEnteredWeight);
		} else {
			tolaWeight = new HashMap<String, BigDecimal>();
			calulatedWeight = argEnteredWeight;
		}
		if (ledger.getUnitcount().subtract(calulatedWeight).floatValue() < 0) {
			IFormattable sohUnit = this._formattables.getSimpleFormattable(String.valueOf(ledger.getUnitcount()));
			args = new IFormattable[] { formatWeight, sohUnit };
			return this.HELPER.getPromptResponse("ASQ_TOLA_QTY_SOH_ERROR", args);
		}
		tolaWeight.put(ledger.getItemId(), calulatedWeight);
		_transactionScope.setValue(AsqValueKeys.ASQ_TOLA_WEIGHT, tolaWeight);
		return null;
	}
}
