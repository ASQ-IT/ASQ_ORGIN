/**
 * 
 */
package asq.pos.query;

import dtv.data2.access.AbstractQueryResult;
import dtv.data2.access.IObjectId;

/**
 * @author SA20547171
 *
 */
public class AsqRelatedItemResult extends AbstractQueryResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String itemId;
	private String itemDescription;

	@Override
	protected IObjectId getObjectIdImpl() {
		return null;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	

}
