package asq.pos.loyalty.neqaty.tender.op;
 
 import dtv.pos.framework.ui.listview.AbstractListViewRule;
 
 public class AsqNeqatyRule extends AbstractListViewRule {

	@Override
	public boolean checkListViewRule(Object paramObject) {
		if (paramObject instanceof dtv.xst.dao.com.IReasonCode) {
			return true;
		}
		return false;
	}
  
 }
