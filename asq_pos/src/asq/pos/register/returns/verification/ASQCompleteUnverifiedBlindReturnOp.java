package asq.pos.register.returns.verification;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import asq.pos.common.AsqConstant;
import asq.pos.common.AsqValueKeys;
import asq.pos.register.sale.AsqHelper;
import dtv.pos.common.ValueKeys;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.register.returns.verification.CompleteUnverifiedBlindReturnOp;
import dtv.xst.dao.inv.IStockLedger;
import dtv.xst.dao.itm.IItem;
import dtv.xst.dao.itm.IItemProperty;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import oracle.retail.xstore.inv.impl.InventoryStockAdjuster;

public class ASQCompleteUnverifiedBlindReturnOp extends CompleteUnverifiedBlindReturnOp {

	@Inject
	AsqHelper asqHelper;

	@Inject
	InventoryStockAdjuster stockAdjuster;

	@Override
	public IOpResponse handleOpExec(IXstEvent argEvent) {
		ISaleReturnLineItem saleLine = getScopedValue(ValueKeys.CURRENT_SALE_LINE);
		if (asqHelper.isTOLAItem(saleLine)) {
			BigDecimal quantity = saleLine.getQuantity();
			if (quantity != null) {
				IItem superInventoriedItem = asqHelper.getSuperParentItem(saleLine.getItem());
				if (null != superInventoriedItem) {
					IStockLedger ledger = stockAdjuster.getStockLedger(superInventoriedItem.getItemId(), AsqConstant.ASQ_DEFAULT, AsqConstant.ASQ_ON_HAND, _stationState.getRetailLocationId());
					BigDecimal enteredWeight = new BigDecimal(0);

					HashMap<String, BigDecimal> tolaWeight = _transactionScope.getValue(AsqValueKeys.ASQ_TOLA_WEIGHT);
					if (null == tolaWeight) {
						tolaWeight = new HashMap<String, BigDecimal>();
					}

					List<IItemProperty> list = saleLine.getItem().getProperties();
					for (IItemProperty prop : list) {
						if ("MIN_SALE_UNIT_COUNT".equalsIgnoreCase(prop.getPropertyCode())) {
							enteredWeight = new BigDecimal(prop.getStringValue());
						}
					}
					if (null != ledger && enteredWeight.intValue() > 0) {
						enteredWeight = enteredWeight.multiply(quantity);

						BigDecimal qty = ledger.getUnitcount();
						qty = qty.add(enteredWeight.add(tolaWeight.get(ledger.getItemId())));
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
