/**
 * 
 */
package asq.pos.inventory.lookup;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import dtv.pos.inventory.lookup.ItemLookupFormData;
import dtv.pos.inventory.lookup.ItemLookupModule;
import dtv.xst.dao.com.ICodeValue;
import dtv.xst.dao.itm.ItemAvailabilityCode;
import dtv.xst.query.results.BasicItemQueryResult;

/**
 * @author RA20221457
 *
 */
public class AsqStyleItmRestrictModule {
	
	private static final Logger LOG = LogManager.getLogger(AsqStyleItmRestrictModule.class);
	
	protected IQueryResultList<BasicItemQueryResult> lookupItemByAttributes(ItemLookupFormData argData,
			boolean argFindFast, long argRetailLocationId) {

		@SuppressWarnings({ "unchecked", "rawtypes" })
		final IQueryKey<BasicItemQueryResult> ITEM_LOOKUP_UPC = (IQueryKey<BasicItemQueryResult>) new QueryKey(
				"ITEM_LOOKUP_UPC", BasicItemQueryResult.class);
		Map<String, Object> params = getSearchParameters(argData, argFindFast, argRetailLocationId);
		IQueryResultList<BasicItemQueryResult> results = null;
		try {
			LOG.debug("starting first query");
			results = DataFactory.getObjectByQuery(AsqIItemLocator.ASQ_ITEM_LOOKUP, params);
			for (Iterator<BasicItemQueryResult> iter = results.iterator(); iter.hasNext();) {
				BasicItemQueryResult result = iter.next();
				if (ItemAvailabilityCode.NA.name().equals(result.getAvailabilityCode()))
					iter.remove();
			}
		} catch (ObjectNotFoundException objectNotFoundException) {
		}
		LOG.debug("done with first query");
		IQueryResultList<BasicItemQueryResult> upcResults = null;
		if (isUpcResultPossible(params)) {
			try {
				LOG.debug("starting second query");
				upcResults = DataFactory.getObjectByQuery(ITEM_LOOKUP_UPC, params);
			} catch (ObjectNotFoundException objectNotFoundException) {
			}
			LOG.debug("done with second query");
		} else {
			LOG.debug("second query skipped");
		}
		if (results == null) {
			if (upcResults != null)
				return upcResults;
			return (IQueryResultList<BasicItemQueryResult>) new QueryResultList(BasicItemQueryResult.class);
		}
		if (upcResults != null && !upcResults.isEmpty()) {
			QueryResultList queryResultList = new QueryResultList(BasicItemQueryResult.class);
			for (BasicItemQueryResult result : results) {
				for (BasicItemQueryResult upcResult : upcResults) {
					if (upcResult.getItemId().equals(result.getItemId()))
						queryResultList.add(upcResult);
				}
			}
			upcResults.removeAll((Collection) queryResultList);
			if (!upcResults.isEmpty())
				results.addAll((Collection) upcResults);
			Collections.sort((List<BasicItemQueryResult>) results, getItemSorter());
		}
		return results;
	}

	protected Map<String, Object> getSearchParameters(ItemLookupFormData argData, boolean argFindFast,
			long argRetailLocationId) {
		Map<String, Object> params = new LinkedHashMap<>();
		if (!argData.getItemGroupDetails().values().isEmpty()) {
			int i = 1;
			for (ICodeValue includeItemGroupDetails : argData.getItemGroupDetails().values()) {
				CommonHelper.setParameter(params, "argIncludeItemPropCode" + i,
						includeItemGroupDetails.getCategory());
				CommonHelper.setParameter(params, "argIncludeItemPropValue" + i,
						includeItemGroupDetails.getCode());
				i++;
			}
		}
		if (!argData.getExcludeItemGroupDetails().values().isEmpty()) {
			int i = 1;
			for (ICodeValue excludeItemGroupDetails : argData.getExcludeItemGroupDetails().values()) {
				CommonHelper.setParameter(params, "argExcludeItemPropCode" + i,
						excludeItemGroupDetails.getCategory());
				CommonHelper.setParameter(params, "argExcludeItemPropValue" + i,
						excludeItemGroupDetails.getCode());
				i++;
			}
		}
		CommonHelper.setParameter(params, "argMerchlevel1Id",
				(argData.getMerchLevel1Id() != null) ? argData.getMerchLevel1Id().getCode() : null);
		CommonHelper.setParameter(params, "argMerchlevel2Id",
				(argData.getMerchLevel2Id() != null) ? argData.getMerchLevel2Id().getCode() : null);
		CommonHelper.setParameter(params, "argMerchlevel3Id",
				(argData.getMerchLevel3Id() != null) ? argData.getMerchLevel3Id().getCode() : null);
		CommonHelper.setParameter(params, "argMerchlevel4Id",
				(argData.getMerchLevel4Id() != null) ? argData.getMerchLevel4Id().getCode() : null);
		String itemIdArg = argFindFast ? "argItemIdFast" : "argItemId";
		CommonHelper.setParameter(params, itemIdArg, stripZeros(argData.getItemId()));
		CommonHelper.setParameter(params, "argDescription", argData.getDescription());
		CommonHelper.setParameter(params, "argManufacturer", argData.getManufacturer());
		CommonHelper.setParameter(params, "argNonmerchandiseCode", argData.getNonmerchandiseCode());
		CommonHelper.setParameter(params, "argSerialNumber", argData.getSerialNumber());
		CommonHelper.setParameter(params, argFindFast ? "argStyleIdFast" : "argStyleId", argData.getStyleId());
		CommonHelper.setParameter(params, "argItemTypeCode", argData.getItemTypeCode());
		CommonHelper.setParameter(params, "argItemLvlCode", argData.getItemLvlCode());
		CommonHelper.setParameter(params, "argUpc", argData.getUpc());
		params.put("argRetailLocationId", Long.valueOf(argRetailLocationId));
		params.put("argInventoryLocationId", ConfigurationMgr.getDefaultInventoryLocationId());
		params.put("argBucketId", ConfigurationMgr.getOnHandInventoryBucketId());
		//params.put("argItemId", argData.getItemId());
		return params;
	}

	private static boolean stripZeros_ = Boolean.getBoolean("dtv.item.StripZeros");

	private static String stripZeros(String argId) {
		String retVal = argId;
		if (!StringUtils.isBlank(argId) && stripZeros_)
			retVal = StringUtils.stripStart(retVal, "0");
		return retVal;
	}

	protected Comparator<? super BasicItemQueryResult> getItemSorter() {
		return itemSorter_;
	}

	protected boolean isUpcResultPossible(Map<String, Object> argParams) {
		for (String key : argParams.keySet()) {
			if ("argItemId".equalsIgnoreCase(key) || "argItemIdfast".equalsIgnoreCase(key))
				return true;
		}
		return false;
	}

	private static final Comparator<? super BasicItemQueryResult> itemSorter_ = new Comparator<BasicItemQueryResult>() {
		public int compare(BasicItemQueryResult argObj1, BasicItemQueryResult argObj2) {
			return argObj1.getItemId().compareTo(argObj2.getItemId());
		}
	};

}
