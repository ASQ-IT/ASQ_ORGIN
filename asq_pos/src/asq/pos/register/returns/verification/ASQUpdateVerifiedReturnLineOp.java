package asq.pos.register.returns.verification;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.inject.Inject;

import asq.pos.common.AsqConstant;
import asq.pos.common.AsqValueKeys;
import asq.pos.register.sale.AsqHelper;
import dtv.pos.common.ValueKeys;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.register.returns.verification.UpdateVerifiedReturnLineOp;
import dtv.xst.dao.inv.IStockLedger;
import dtv.xst.dao.itm.IItem;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import oracle.retail.xstore.inv.impl.InventoryStockAdjuster;

public class ASQUpdateVerifiedReturnLineOp extends UpdateVerifiedReturnLineOp {

	@Inject
	AsqHelper asqHelper;

	@Inject
	InventoryStockAdjuster stockAdjuster;

	@Override
	public IOpResponse handleOpExec(IXstEvent argEvent) {
		ISaleReturnLineItem origLine = getScopedValue(ValueKeys.ORIGINAL_SALE_LINE);
		if (asqHelper.isTOLAItem(origLine)) {
			BigDecimal quantity = getScopedValue(ValueKeys.ENTERED_ITEM_QUANTITY);
			if (quantity != null) {
				IItem superInventoriedItem = asqHelper.getSuperParentItem(origLine.getItem());
				if (null != superInventoriedItem) {
					IStockLedger ledger = stockAdjuster.getStockLedger(superInventoriedItem.getItemId(), AsqConstant.ASQ_DEFAULT, AsqConstant.ASQ_ON_HAND, _stationState.getRetailLocationId());
					BigDecimal enteredWeight = origLine.getDecimalProperty(AsqConstant.ASQ_INVENTORY_TOLA_ITEM_WEIGHT);

					HashMap<String, BigDecimal> tolaWeight = _transactionScope.getValue(AsqValueKeys.ASQ_TOLA_WEIGHT);
					if (null == tolaWeight) {
						tolaWeight = new HashMap<String, BigDecimal>();
					}

					if (null != ledger && null != enteredWeight) {
						enteredWeight = enteredWeight.multiply(quantity);

						BigDecimal qty = ledger.getUnitcount();
						BigDecimal prevEnteredWeight = tolaWeight.get(ledger.getItemId());

						if (null != prevEnteredWeight) {
							enteredWeight = enteredWeight.add(prevEnteredWeight);
						}

						qty = qty.add(enteredWeight);
						ledger.setUnitcount(qty);

						asqHelper.removeAndAddStockLedger(_transactionHelper, ledger);

						tolaWeight.put(ledger.getItemId(), enteredWeight);
						_transactionScope.setValue(AsqValueKeys.ASQ_TOLA_WEIGHT, tolaWeight);
					}
				}
			}
		}
		return super.handleOpExec(argEvent);
	}
}
