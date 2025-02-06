package asq.pos.inventory.lookup;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import asq.pos.register.AsqIItemLocator;
import dtv.data2.access.DataFactory;
import dtv.data2.access.IQueryKey;
import dtv.data2.access.IQueryResultList;
import dtv.data2.access.ObjectNotFoundException;
import dtv.data2.access.QueryKey;
import dtv.data2.access.QueryResultList;
import dtv.pos.common.CommonHelper;
import dtv.pos.common.ConfigurationMgr;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.iframework.action.IXstActionKey;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.inventory.lookup.ItemLookupFormData;
import dtv.pos.inventory.lookup.ItemLookupModule;
import dtv.pos.inventory.lookup.ItemSearchOp;
import dtv.util.IKeyedValue;
import dtv.xst.dao.com.ICodeValue;
import dtv.xst.dao.itm.ItemAvailabilityCode;
import dtv.xst.query.results.BasicItemQueryResult;

public class AsqItemSearchOp extends ItemSearchOp {

	private static final Logger LOG = LogManager.getLogger(AsqItemSearchOp.class);

	@Inject
	private AsqStyleItmRestrictModule _asqStyleItmRestrictModule;

	@Override
	protected IOpResponse handleDataAction(IXstDataAction argAction) {
		IXstActionKey actionKey = argAction.getActionKey();

		if (actionKey == XstDataActionKey.ACCEPT) {
			ItemLookupFormData searchModel = (ItemLookupFormData) getModel();

			if (!searchModel.getItemGroupDetails().isEmpty() || !searchModel.getExcludeItemGroupDetails().isEmpty())
				searchModel.setValue("itemGroupField", searchModel.getItemGroup());
		}
		return super.handleDataAction(argAction);
	}

	@Override
	protected IQueryResultList<BasicItemQueryResult> runQueryWrapResults(Collection<IKeyedValue<String, ?>> argFields) {
		ItemLookupFormData searchModel = (ItemLookupFormData) getModel();
		boolean argFindFast = false;
		//String argItemId = null;
		return _asqStyleItmRestrictModule.lookupItemByAttributes(searchModel, argFindFast, this._stationState.getRetailLocationId());
	}

}
