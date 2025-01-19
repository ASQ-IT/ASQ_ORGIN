/**
 * 
 */
package asq.pos.query;

import dtv.pos.framework.ui.listview.AbstractListViewRule;

/**
 * @author SA20547171
 *
 */
public class AsqRelatedItemListViewRule extends AbstractListViewRule{

	@Override
	public boolean checkListViewRule(Object var1) {
		return var1 instanceof AsqRelatedItemResult;
	}

}
