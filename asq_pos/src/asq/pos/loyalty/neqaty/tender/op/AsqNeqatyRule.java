package asq.pos.loyalty.neqaty.tender.op;

//Changed by SA20547171

import dtv.pos.framework.ui.listview.AbstractListViewRule;

public class AsqNeqatyRule extends AbstractListViewRule {

	@Override
	public boolean checkListViewRule(Object paramObject) {

		return paramObject instanceof dtv.xst.dao.com.IReasonCode;
	}
}
