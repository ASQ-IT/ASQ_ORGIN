/**
 * 
 */
package asq.pos.loyalty.neqaty.tender.op;

import java.util.Collection;

import dtv.data2.access.IQueryResultList;
import dtv.pos.framework.op.AbstractSearchFormOp;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.util.IKeyedValue;
import dtv.xst.dao.itm.IItem;
import oracle.retail.xstore.inv.lookup.IAvailableLocResult;

/**
 * @author RA20221457
 *
 */
public abstract class AsqNeqatyAbstractMobileNumberOp extends AbstractSearchFormOp<IAvailableLocResult, IItem, AsqNeqatyMobileNumberEditModel> {

	protected AsqNeqatyMobileNumberEditModel createModel() {
		return new AsqNeqatyMobileNumberEditModel();
	}

	protected String getSearchResultsPromptKey() {

		return "ASQ_NEQATY_REDEEM_OPTIONS_LIST";
	}

	@Override
	protected String getNoResultsPromptKey() {
		return "ASQ_NEQATY_NO_POINTS";
	}

	@Override
	protected IQueryResultList<IAvailableLocResult> runQueryWrapResults(Collection<IKeyedValue<String, ?>> arg0) {
		return null;
	}

	@Override
	protected void setSelectedResult(IItem arg0) {
		
	}
	
	@Override
	protected IOpResponse handleListSelection(IXstEvent argEvent) {
	      return this.HELPER.completeResponse();
		    }
}
