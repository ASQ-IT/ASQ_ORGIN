/**
 * 
 */
package asq.pos.html;

import javax.inject.Inject;
import dtv.data2.access.DataFactory;
import dtv.data2.access.IQueryKey;
import dtv.data2.access.ObjectNotFoundException;
import dtv.data2.access.QueryKey;
import dtv.pos.common.ConfigurationMgr;
import dtv.pos.common.ViewElementType;
import dtv.pos.framework.html.AbstractListContentBuilder;
import dtv.pos.framework.scope.TransactionScope;
import dtv.pos.framework.systemcycle.TransDateProvider;
import dtv.util.CompositeObject;
import dtv.util.CompositeObject.TwoPiece;
import dtv.util.spring.IgnoreSingletonValidation;
import dtv.xst.dao.trl.IRetailTransactionLineItem;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import dtv.xst.dao.trn.IPosTransaction;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.loyalty.neqaty.tender.op.AsqNeqatyEarnPointsOp;
import asq.pos.query.AsqRelatedItemResult;

/**
 * @author SA20547171
 *
 */
public class AsqRelatedItemsContentBuilder extends AbstractListContentBuilder {
	
	private static final Logger LOG = LogManager.getLogger(AsqRelatedItemsContentBuilder.class);

	@Inject
	@IgnoreSingletonValidation(justification = "This will be accessed from session-bound thread on mobile. The provider will return the appropriate instance.")
	private Provider<TransDateProvider> _transDateProvider;

	@Inject
	private TransactionScope _transactionScope;

	public static final IQueryKey<AsqRelatedItemResult> ASQ_RELATED_QUERY = new QueryKey<>("ASQ_RELATED_ITEMS",
			AsqRelatedItemResult.class);

	@SuppressWarnings("unused")
	public TwoPiece<List<? extends Object>, ViewElementType> retrieveResultData() {
		List<AsqRelatedItemResult> message = null;
		try {
			IPosTransaction trans = _transactionScope.getTransaction();
			if (_transactionScope.getTransaction() != null
					&& _transactionScope.getTransaction().getSaleLineItems() != null) {
				int lastIndex = _transactionScope.getTransaction().getSaleLineItems().size();
				if (lastIndex > 0) {
					IRetailTransactionLineItem item = _transactionScope.getTransaction().getSaleLineItems()
							.get(lastIndex - 1);
					String itemMerch2 = ((ISaleReturnLineItem) item).getItem().getMerchLevel2Id();
					Map<String, Object> params = new HashMap<>();
					params.put("argOrganizationId", ConfigurationMgr.getOrganizationId());
					Date currentDate = _transDateProvider.get().getDate();
					params.put("argBusinessDate", currentDate);
					params.put("argMerchLevel2Id", itemMerch2);
					params.put("argRetailLocationId", _transactionScope.getTransaction().getRetailLocationId());

					message = DataFactory.getObjectByQuery(ASQ_RELATED_QUERY, params);
				}
			}
		} catch (ClassCastException | ObjectNotFoundException ex) {
			message = new ArrayList<>();
		}
		if (message == null) {
			message = new ArrayList<>();
		}
		// Collections.sort(message, new MessageComparator());
		return CompositeObject.make(message, ViewElementType.valueOf("ASQ_RELATED_ITEM"));
	}

}
