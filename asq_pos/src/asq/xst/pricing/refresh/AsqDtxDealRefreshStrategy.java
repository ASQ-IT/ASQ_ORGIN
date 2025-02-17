package asq.xst.pricing.refresh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import dtv.data2.access.DataFactory;
import dtv.data2.access.IQueryKey;
import dtv.data2.access.QueryKey;
import dtv.pos.pricing.deal.itemField.Sku;
import dtv.pricing2.DealActionFactory;
import dtv.pricing2.ItemField;
import dtv.pricing2.MatchRuleFactory;
import dtv.pricing2.PricingAdapter;
import dtv.pricing2.PricingDeal;
import dtv.xst.dao.prc.IDeal;
import dtv.xst.pricing.refresh.DtxDealRefreshStrategy;

public class AsqDtxDealRefreshStrategy extends DtxDealRefreshStrategy {

	@Inject
	PricingAdapter pricingAdapter;

	@Inject
	Sku argSku;

	@Inject
	DealActionFactory argDealActionFactory;

	@Inject
	MatchRuleFactory matchRuleFactory;

	protected Map<String, ItemField<?>> itemFields = new HashMap<String, ItemField<?>>();

	protected static final IQueryKey<IDeal> LOAD_DEALS_QUERY = new QueryKey<IDeal>("LOAD_DEALS", IDeal.class);

	@Override
	protected List<IDeal> loadSingles() {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("argDeferred", Boolean.FALSE);
		params.put("argRetailLocationId", Integer.valueOf(Integer.parseInt(System.getProperty("dtv.location.storeNumber"))));
		params.put("argExpirationDateGreaterThan", "01-JAN-00 12.00.00.000000000 AM");
		params.put("argEffectiveDateLessThan", "01-JAN-00 12.00.00.000000000 AM");
		return DataFactory.getObjectByQueryNoThrow(LOAD_DEALS_QUERY, params);
	}

	@Override
	public List<PricingDeal> getDeals() {
		setPricingAdapter(pricingAdapter);
		setActionFactory(argDealActionFactory);
		setMatchRuleFactory(matchRuleFactory);
		itemFields.put("SKU", argSku);
		setItemFields(itemFields);
		refresh();
		return this._deals;
	}

}
