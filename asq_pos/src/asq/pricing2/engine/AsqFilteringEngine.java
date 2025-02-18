package asq.pricing2.engine;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqConfigurationMgr;
import dtv.pricing2.EligibilityMatrix;
import dtv.pricing2.IExtendedEngine;
import dtv.pricing2.IItemMatcher;
import dtv.pricing2.Item;
import dtv.pricing2.PricingDeal;
import dtv.pricing2.Result;
import dtv.pricing2.Result_Impl;
import dtv.pricing2.engine.FilteringEngine;
import dtv.xst.pricing.XSTPricingDescriptor;

/**
 * @author SA20547171
 *
 */
public class AsqFilteringEngine extends FilteringEngine {
	private static final Logger LOG = LogManager.getLogger(AsqFilteringEngine.class);

	private static final Result EMPTY_RESULT = new Result_Impl();

	@Inject
	private AsqConfigurationMgr _sysConfig;

	public AsqFilteringEngine(IExtendedEngine argDelegate) {
		super(argDelegate);
	}

	@Override
	public Result calculate(List<PricingDeal> argDeals, List<Item> argItems) {
		if (argDeals.isEmpty()) {
			return EMPTY_RESULT;
		} else {
			// ASQ BOGO deal Changes start
			List<Item> asqItemPrcSort = new ArrayList<>();
			for (int itemDeal = 0; itemDeal < argDeals.size(); ++itemDeal) {
				PricingDeal itemD = argDeals.get(itemDeal);
				XSTPricingDescriptor dealName = (XSTPricingDescriptor) itemD.getNativeDeal();
				if (!itemD.isTransactionDeal() && dealName.getDescription().contains(_sysConfig.getAsqBOGODealName())) {
					LOG.debug("ASQ BOGO promtions start sorting the base Item list" + argItems);
					// Argitems start Index and end
					int itemIdx = 0;
					int itemSize = argItems.size() - 1;

					sort: while (itemSize >= 0) {
						int itemMat = 0;
						// Deal matcher 1 condition
						IItemMatcher itemMat1 = itemD.getItemMatchers().get(itemMat);
						long minMat1Qty = itemMat1.getMinQty();

						while (minMat1Qty > 0L) {

							Item item = argItems.get(itemIdx);
							asqItemPrcSort.add(item);
							// Condition to break outer while loop
							if (argItems.size() == asqItemPrcSort.size()) {
								break sort;
							}
							++itemIdx;
							minMat1Qty -= item.getQuantity();
							// 60,60,9 ----60,9,60
						}

						++itemMat;
						// Deal matcher 2 condition
						IItemMatcher itemMat2 = itemD.getItemMatchers().get(itemMat);
						long minMat2Qty = itemMat2.getMinQty();
						while (minMat2Qty > 0L) {
							Item item = argItems.get(itemSize);
							asqItemPrcSort.add(item);
							if (argItems.size() == asqItemPrcSort.size()) {
								break sort;
							}
							--itemSize;
							minMat2Qty -= item.getQuantity();
						}
					}
				}
			}
			if (!asqItemPrcSort.isEmpty()) {
				argItems = asqItemPrcSort;
				LOG.debug("ASQ BOGO promtions sorted the item list" + asqItemPrcSort);
			}
			// ASQ BOGO deal Changes ends

			long qtyOne = this._pricingAdapter.getSingleQuantity();
			long subtotal = 0L;

			for (Item item : argItems) {
				subtotal += item.getPrice() * item.getQuantity() / qtyOne;
			}

			EligibilityMatrix eligibilityMatrix = new EligibilityMatrix(argDeals, argItems);
			List<PricingDeal> eligibleDeals = new ArrayList(argDeals.size());

			dealId: for (int dealIdx = 0; dealIdx < argDeals.size(); ++dealIdx) {
				PricingDeal deal = argDeals.get(dealIdx);
				if (deal.getSubtotalMin() <= subtotal) {
					if (deal.getItemMatchers() != null && !deal.getItemMatchers().isEmpty()) {
						List<IItemMatcher> itemMatchers = deal.getItemMatchers();

						for (int matcherIdx = 0; matcherIdx < itemMatchers.size(); ++matcherIdx) {
							IItemMatcher matcher = itemMatchers.get(matcherIdx);
							long remainingQty = matcher.getMinQty();
							long remainingSubtotal = matcher.getMinTotal();

							for (int itemIdx = 0; itemIdx < argItems.size(); ++itemIdx) {
								Item item = argItems.get(itemIdx);
								if (eligibilityMatrix.check(dealIdx, itemIdx) && eligibilityMatrix.check(dealIdx, matcherIdx, itemIdx)) {
									remainingQty -= item.getQuantity();
									remainingSubtotal -= item.getPrice() * item.getQuantity() / qtyOne;
									if (remainingQty <= 0L && remainingSubtotal <= 0L) {
										break;
									}
								}
							}
							if (remainingQty > 0L || remainingSubtotal > 0L) {
								continue dealId;
							}
						}
					}
					eligibleDeals.add(deal);
				}
			}

			if (eligibleDeals.isEmpty()) {
				LOG.info(() -> "No eligible deals.");
				return EMPTY_RESULT;
			} else {
				return this._delegate.calculate(eligibleDeals, argItems, new EligibilityMatrix(eligibleDeals, argItems));
			}
		}
	}

}
