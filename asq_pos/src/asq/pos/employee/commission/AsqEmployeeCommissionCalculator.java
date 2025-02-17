package asq.pos.employee.commission;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqConstant;
import asq.pos.register.sale.AsqHelper;
import asq.xst.pricing.refresh.AsqDtxDealRefreshStrategy;
import dtv.pos.iframework.security.StationState;
import dtv.pos.storecalendar.DateRangeHelper;
import dtv.pos.storecalendar.IStoreCalendar;
import dtv.pos.storecalendar.StoreCalendarException;
import dtv.pricing2.IItemMatcher;
import dtv.pricing2.PricingDeal;
import dtv.util.DateRange;
import dtv.xst.pricing.XSTPricingDescriptor;

public class AsqEmployeeCommissionCalculator {

	@Inject
	AsqHelper asqHelper;

	@Inject
	StationState state;

	@Inject
	private AsqDtxDealRefreshStrategy _dealSpaceGlobal;

	@Inject
	private IStoreCalendar _storeCalendar;

	private static final Logger LOG = LogManager.getLogger(AsqEmployeeCommissionCalculator.class);

	/**
	 * Employee commission : based on item sale commission amount
	 *
	 * @return
	 */
	public List<AsqCalclatedEmployeeCommission> calculateEmployeeItemSaleCommission() {
		List<AsqCalclatedEmployeeCommission> calculatedItemCommissionResults = new ArrayList<AsqCalclatedEmployeeCommission>();
		List<AsqEmpCommissionQueryResult> results = asqHelper.getEmployeeItemCommissionOnNetSale(state.getRetailLocationId(), state.getCurrentBusinessDate());

		if (!results.isEmpty()) {
			List<PricingDeal> commissionDeals = getCommissionDeal(AsqConstant.ASQ_ITEM_COMMISSION, _dealSpaceGlobal.getDeals());

			for (PricingDeal commissionDeal : commissionDeals) {
				if (null != commissionDeal) {
					// matching Item and calculating the commission
					for (IItemMatcher matcher : commissionDeal.getItemMatchers()) {
						String promoCommissionItem = matcher.getFieldTest().toString();
						for (AsqEmpCommissionQueryResult result : results) {
							if (promoCommissionItem.contains(result.getItem_id())) {
								AsqCalclatedEmployeeCommission calcCommission = new AsqCalclatedEmployeeCommission();
								calcCommission.setEmployeeName(result.getEmployee_name());
								calcCommission.setEmployeeID(result.getEmployee_id());
								calcCommission.setCommissionBusinessDate(String.valueOf(state.getCurrentBusinessDate()));
								calcCommission.setEmployeeID(result.getEmployee_id());
								calcCommission.setItemCount(result.getItem_quantity());

								calcCommission.setCommissionCalclauted((new BigDecimal(commissionDeal.getSubtotalMin()).divide(new BigDecimal(100))).multiply(result.getItem_quantity(), MathContext.DECIMAL64));

								calcCommission.setCommissionMonth(null);
								calcCommission.setCommissionTargetAchived(null);
								calcCommission.setCommissionTargetDiff(null);

								calculatedItemCommissionResults.add(calcCommission);
							}
						}
					}
				}
			}
		}
		return calculatedItemCommissionResults;
	}

	/**
	 * Monthly Store Net Sale commission
	 */
	public List<AsqCalclatedEmployeeCommission> calculateStoreSaleCommission() {
		List<AsqCalclatedEmployeeCommission> calculatedItemCommissionResults = new ArrayList<AsqCalclatedEmployeeCommission>();
		DateRange date = null;
		try {
			date = DateRangeHelper.getMonth(_storeCalendar, new Date(), 0);
		} catch (StoreCalendarException e) {
			LOG.error("Got Error in getting data range", e);
		}
		List<AsqEmpCommissionQueryResult> results = asqHelper.getEmployeeMonthlyCommissionOnNetSale(state.getRetailLocationId(), date);

		if (!results.isEmpty()) {
			List<PricingDeal> commissionDeals = getCommissionDeal(AsqConstant.ASQ_STORE_SALE_COMMISSION, _dealSpaceGlobal.getDeals());

			for (PricingDeal commissionDeal : commissionDeals) {
				if (null != commissionDeal && 0 != commissionDeal.getSubtotalMin() && 0 != commissionDeal.getSubtotalMax()) {
					BigDecimal caluatedTarget = new BigDecimal(0);
					for (AsqEmpCommissionQueryResult result : results) {
						caluatedTarget = caluatedTarget.add(result.getNet_amt());
					}

					BigDecimal commisionPerc = new BigDecimal(commissionDeal.getSubtotalMin());
					commisionPerc = commisionPerc.divide(new BigDecimal(100));
					BigDecimal commisionTarget = new BigDecimal(commissionDeal.getSubtotalMax());
					commisionTarget = commisionTarget.divide(new BigDecimal(100));

					Calendar mCalendar = Calendar.getInstance();
					String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

					for (AsqEmpCommissionQueryResult result : results) {
						AsqCalclatedEmployeeCommission calcCommission = new AsqCalclatedEmployeeCommission();
						calcCommission.setEmployeeName(result.getEmployee_name());
						calcCommission.setEmployeeID(result.getEmployee_id());
						calcCommission.setCommissionMonth(month);
						calcCommission.setCommissionTargetDiff(commisionTarget.subtract(caluatedTarget));

						if (caluatedTarget.compareTo(commisionTarget) >= 0) {
							calcCommission.setCommissionTargetAchived(true);
							calcCommission.setCommissionCalclauted(caluatedTarget.divide(commisionPerc, 2, asqHelper.getSystemRoundingMode()));
						} else {
							calcCommission.setCommissionTargetAchived(false);
							calcCommission.setCommissionCalclauted(null);
						}
						calculatedItemCommissionResults.add(calcCommission);
					}
				}
			}
		}
		return calculatedItemCommissionResults;
	}

	private List<PricingDeal> getCommissionDeal(String argDealIdName, List<PricingDeal> argDealFromSys) {
		List<PricingDeal> pricingDealList = new ArrayList<PricingDeal>();
		for (PricingDeal argCommissionDeal : argDealFromSys) {
			XSTPricingDescriptor nativeDeal = (XSTPricingDescriptor) argCommissionDeal.getNativeDeal();
			if (nativeDeal.getDescription().startsWith(argDealIdName)) {
				pricingDealList.add(argCommissionDeal);
			}
		}
		return pricingDealList;
	}

	public String getEmployeeComssionRcptFormat(List<AsqEmpCommissionQueryResult> results) {
		StringBuilder employee = new StringBuilder("Employee Commission \n");
		for (AsqEmpCommissionQueryResult result : results) {
			employee.append("Employee Name  ").append(result.getEmployee_name());
			employee.append("\n");
			employee.append("ID : ").append(result.getEmployee_id());
			// employee.append("\n");
			// employee.append("Employee Net Sale : ").append(result.getNet_amt());
			employee.append("\n");
			employee.append("Employee Commission : ").append(result.getPerc_amt());
			employee.append("\n");
		}
		return employee.toString();
	}

	public void calculateComm() {
		LOG.info("################################################ Starts");
		List<AsqCalclatedEmployeeCommission> calculatedItemCommissionResults = new ArrayList<AsqCalclatedEmployeeCommission>();
		LOG.info("Store Commission ################################################");
		calculatedItemCommissionResults = calculateStoreSaleCommission();
		for (AsqCalclatedEmployeeCommission calculatedItemCommissionResult : calculatedItemCommissionResults) {
			LOG.info("Store Commission #### " + calculatedItemCommissionResult.toString());
		}
		LOG.info("Item Commission ################################################");
		calculatedItemCommissionResults = calculateEmployeeItemSaleCommission();
		for (AsqCalclatedEmployeeCommission calculatedItemCommissionResult : calculatedItemCommissionResults) {
			LOG.info("Item Commission ##### " + calculatedItemCommissionResult.toString());
		}
		LOG.info("################################################ Ends");
	}

}
