package asq.pos.register.sale;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.inject.Inject;

import asq.pos.common.AsqConstant;
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
import dtv.xst.dao.itm.IItemOptions;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import oracle.retail.xstore.inv.impl.InventoryStockAdjuster;

public class ASQPromptForItemWeightOp extends PromptForItemWeightOp {

	@Inject
	ItemLookupHelper itemHelper;

	@Inject
	InventoryStockAdjuster stockAdjuster;

	@Inject
	AsqHelper asqHelper;

	@Override
	public boolean isOperationApplicable() {
		IItem item = getScopedValue(ValueKeys.CURRENT_ITEM);
		String tolaItem = item.getDimension1();
		if (null != tolaItem && tolaItem.contains(AsqConstant.ASQ_TOLA)) {
			return true;
		}
		return super.isOperationApplicable();
	}

	@Override
	public IOpResponse getPromptResponse(IXstEvent argEvent, String argPromptKey, IFormattable[] argPromptArgs) {
		IItem item = getScopedValue(ValueKeys.CURRENT_ITEM);

		PromptOverrideProperties overrides = new PromptOverrideProperties();
		IItem superInventoriedItem = asqHelper.getSuperParentItem(item);
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
		IItem superInventoriedItem = asqHelper.getSuperParentItem(item);
		if (null != superInventoriedItem) {
			return (IFormattable[]) ArrayUtils.insert((Object[]) args, this._formattables.getSimpleFormattable(superInventoriedItem.getOptions().getUnitOfMeasureCode(), FormatterType.UNIT_OF_MEASURE_NAME), 0);
		} else {
			return (IFormattable[]) ArrayUtils.insert((Object[]) args, this._formattables.getSimpleFormattable(item.getOptions().getUnitOfMeasureCode(), FormatterType.UNIT_OF_MEASURE_NAME), 0);
		}
	}

	@Override
	public IOpResponse handlePromptResponse(IXstEvent argEvent) {
		ISaleReturnLineItem lineItem = getScopedValue(ValueKeys.CURRENT_SALE_LINE);
		BigDecimal enteredWeight = getScopedValue(ValueKeys.ENTERED_ITEM_WEIGHT);
		IItem superInventoriedItem = asqHelper.getSuperParentItem(lineItem.getItem());
		if (null != superInventoriedItem) {
			IStockLedger ledger = stockAdjuster.getStockLedger(superInventoriedItem.getItemId(), AsqConstant.ASQ_DEFAULT, AsqConstant.ASQ_ON_HAND, _stationState.getRetailLocationId());
			HashMap<String, BigDecimal> tolaWeight = _transactionScope.getValue(AsqValueKeys.ASQ_TOLA_WEIGHT);
			if (null == tolaWeight) {
				tolaWeight = new HashMap<String, BigDecimal>();
			}

			IOpResponse errorResponse = validateTolaQuantitySOH(ledger, enteredWeight, lineItem, tolaWeight);
			if (errorResponse != null) {
				return errorResponse;
			}

			BigDecimal qty = ledger.getUnitcount();
			qty = qty.subtract(tolaWeight.get(ledger.getItemId()));
			ledger.setUnitcount(qty);

			// remove the existing Persistables
			asqHelper.removeAndAddStockLedger(_transactionHelper, ledger);

			lineItem.setStringProperty(AsqConstant.ASQ_INVENTORY_TOLA_ITEM, superInventoriedItem.getItemId());
			lineItem.setDecimalProperty(AsqConstant.ASQ_INVENTORY_TOLA_ITEM_WEIGHT, enteredWeight);
		} else {
			lineItem.setQuantity(enteredWeight);
			lineItem.setQuantityToAllocate(enteredWeight);
			lineItem.setWeightEntryMethodCode(ItemWeightEntryMethodCode.KEYBOARD.name());
			setScopedValue(ValueKeys.CURRENT_INVENTORIED_LINE_ITEM, lineItem);
		}
		return this.HELPER.completeResponse();
	}

	public IOpResponse validateTolaQuantitySOH(IStockLedger ledger, BigDecimal argEnteredWeight, ISaleReturnLineItem lineItem, HashMap<String, BigDecimal> tolaWeight) {

		IItemOptions itemToMeasure = lineItem.getItem().getItemOptions().get(0);
		String measureCode = itemToMeasure.getUnitOfMeasureCode();
		BigDecimal maxUnitCount = new BigDecimal(lineItem.getItem().getStringProperty(AsqConstant.ASQ_TOLA_MAX_WEIGHT, "0"));
		BigDecimal minUnitCount = new BigDecimal(lineItem.getItem().getStringProperty(AsqConstant.ASQ_TOLA_MIN_WEIGHT, "0"));

		IFormattable formatMeasureCode = this._formattables.getSimpleFormattable(measureCode);
		IFormattable formatWeight = this._formattables.getSimpleFormattable(String.valueOf(argEnteredWeight));
		IFormattable[] args = new IFormattable[] { formatWeight, formatMeasureCode };

		if (measureCode.equalsIgnoreCase(AsqConstant.ASQ_TOLA) && (argEnteredWeight.compareTo(maxUnitCount) == 1 || argEnteredWeight.compareTo(minUnitCount) == -1)) {
			return this.HELPER.getPromptResponse(AsqConstant.ASQ_TOLA_WEIGHT_RANGE_ERROR, args);
		} else if (measureCode.equalsIgnoreCase(AsqConstant.ASQ_HTOLA) && (argEnteredWeight.compareTo(maxUnitCount) == 1 || argEnteredWeight.compareTo(minUnitCount) == -1)) {
			return this.HELPER.getPromptResponse(AsqConstant.ASQ_TOLA_WEIGHT_RANGE_ERROR, args);
		} else if (measureCode.equalsIgnoreCase(AsqConstant.ASQ_QTOLA) && (argEnteredWeight.compareTo(maxUnitCount) == 1 || argEnteredWeight.compareTo(minUnitCount) == -1)) {
			return this.HELPER.getPromptResponse(AsqConstant.ASQ_TOLA_WEIGHT_RANGE_ERROR, args);
		} else if (measureCode.equalsIgnoreCase(AsqConstant.ASQ_2TOLA) && (argEnteredWeight.compareTo(maxUnitCount) == 1 || argEnteredWeight.compareTo(minUnitCount) == -1)) {
			return this.HELPER.getPromptResponse(AsqConstant.ASQ_TOLA_WEIGHT_RANGE_ERROR, args);
		} else if (measureCode.equalsIgnoreCase(AsqConstant.ASQ_3TOLA) && (argEnteredWeight.compareTo(maxUnitCount) == 1 || argEnteredWeight.compareTo(minUnitCount) == -1)) {
			return this.HELPER.getPromptResponse(AsqConstant.ASQ_TOLA_WEIGHT_RANGE_ERROR, args);
		} else if (measureCode.equalsIgnoreCase(AsqConstant.ASQ_5TOLA) && (argEnteredWeight.compareTo(maxUnitCount) == 1 || argEnteredWeight.compareTo(minUnitCount) == -1)) {
			return this.HELPER.getPromptResponse(AsqConstant.ASQ_TOLA_WEIGHT_RANGE_ERROR, args);
		} else if (measureCode.equalsIgnoreCase(AsqConstant.ASQ_10TOLA) && (argEnteredWeight.compareTo(maxUnitCount) == 1 || argEnteredWeight.compareTo(minUnitCount) == -1)) {
			return this.HELPER.getPromptResponse(AsqConstant.ASQ_TOLA_WEIGHT_RANGE_ERROR, args);
		}

		BigDecimal calulatedWeight = new BigDecimal(0);
		if (null != tolaWeight && !tolaWeight.isEmpty()) {
			calulatedWeight = tolaWeight.get(ledger.getItemId());
			if (null != calulatedWeight) {
				calulatedWeight = calulatedWeight.add(argEnteredWeight);
			} else {
				calulatedWeight = argEnteredWeight;
			}
		} else {
			calulatedWeight = argEnteredWeight;
		}
		if (null != calulatedWeight && ledger.getUnitcount().subtract(calulatedWeight).floatValue() < 0) {
			IFormattable sohUnit = this._formattables.getSimpleFormattable(String.valueOf(ledger.getUnitcount()));
			args = new IFormattable[] { formatWeight, sohUnit };
			return this.HELPER.getPromptResponse(AsqConstant.ASQ_TOLA_QTY_SOH_ERROR, args);
		}
		tolaWeight.put(ledger.getItemId(), calulatedWeight);
		_transactionScope.setValue(AsqValueKeys.ASQ_TOLA_WEIGHT, tolaWeight);
		return null;
	}
}
