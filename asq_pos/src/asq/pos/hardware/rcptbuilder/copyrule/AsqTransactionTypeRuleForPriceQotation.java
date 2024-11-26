package asq.pos.hardware.rcptbuilder.copyrule;

import javax.inject.Inject;

import asq.pos.common.AsqValueKeys;
import dtv.hardware.rcptbuilding.copyrules.AbstractRcptCopyRule;
import dtv.pos.framework.scope.TransactionScope;
import dtv.xst.dao.trn.IPosTransaction;

public class AsqTransactionTypeRuleForPriceQotation extends AbstractRcptCopyRule{

	@Inject
	protected TransactionScope _transactionScope;
	@Override
	protected boolean doesRuleApply(Object var1) {
		IPosTransaction tran = (IPosTransaction)var1;
		String priceQuotation=tran.getStringProperty("PRICE_QUOTATION");
		if(priceQuotation!=null)
		{
			if(priceQuotation.equals("True"))
			{
				return true	;
			}
			else 
				return false;
		}
		return false;
	}

}
