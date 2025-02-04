package asq.pos.employee.commission;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import asq.pos.common.AsqConstant;
import asq.pos.register.sale.AsqHelper;
import dtv.pos.iframework.security.StationState;
import dtv.pos.iframework.type.IDtvDateRange;
import dtv.pricing2.DealSpaceGlobal;
import dtv.pricing2.IItemMatcher;
import dtv.pricing2.PricingDeal;
import dtv.xst.pricing.XSTPricingDescriptor;

public class AsqEmployeeCommissionCalculator {

	@Inject
	AsqHelper asqHelper;

	@Inject
	StationState state;

	@Inject
	private DealSpaceGlobal _dealSpaceGlobal;

	public String calculateNetSaleCommission() {
		List<AsqEmpCommissionQueryResult> results = asqHelper.getEmployeeCommissionOnNetSale(state.getRetailLocationId(), state.getCurrentBusinessDate());
		return getEmployeeComssionRcptFormat(results);
	}

	public String calculateEmployeeItemSaleCommission() {
		List<AsqEmpCommissionQueryResult> calculatedItemCommissionResults = new ArrayList<AsqEmpCommissionQueryResult>();
		List<AsqEmpCommissionQueryResult> results = asqHelper.getEmployeeItemCommissionOnNetSale(state.getRetailLocationId(), state.getCurrentBusinessDate());

		if (!results.isEmpty()) {
			PricingDeal commissionDeal = getCommissionDeal(AsqConstant.ASQ_ITEM_COMMISSION, _dealSpaceGlobal.getDeals());
			if (null != commissionDeal) {
				// matching Item and calculating the commission
				for (IItemMatcher matcher : commissionDeal.getItemMatchers()) {
					String promoCommissionItem = matcher.getFieldTest().toString();
					for (AsqEmpCommissionQueryResult result : results) {
						if (promoCommissionItem.contains(result.getItem_id())) {
							result.setPerc_amt(new BigDecimal(matcher.getMinTotal()).multiply(result.getItem_quantity(), MathContext.DECIMAL64));
							calculatedItemCommissionResults.add(result);
						}
					}
				}
			}
		}
		return getEmployeeComssionRcptFormat(calculatedItemCommissionResults);
	}

	public String calculateStoreSaleCommission() {
		List<AsqEmpCommissionQueryResult> results = asqHelper.getEmployeeItemCommissionOnNetSale(state.getRetailLocationId(), state.getCurrentBusinessDate());

		if (!results.isEmpty()) {
			PricingDeal commissionDeal = getCommissionDeal(AsqConstant.ASQ_STORE_SALE_COMMISSION, _dealSpaceGlobal.getDeals());
			if (null != commissionDeal) {
				// matching Item and calculating the commission
				for (IItemMatcher matcher : commissionDeal.getItemMatchers()) {
					String promoCommissionItem = matcher.getFieldTest().toString();
					IDtvDateRange date;
					// date.getDateRange();
				}
			}
		}
		return null;
	}

	private PricingDeal getCommissionDeal(String argDealIdName, List<PricingDeal> argDealFromSys) {
		for (PricingDeal argCommissionDeal : argDealFromSys) {
			XSTPricingDescriptor nativeDeal = (XSTPricingDescriptor) argCommissionDeal.getNativeDeal();
			if (argDealIdName.equalsIgnoreCase(nativeDeal.getDescription())) {
				return argCommissionDeal;
			}
		}
		return null;
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
}
