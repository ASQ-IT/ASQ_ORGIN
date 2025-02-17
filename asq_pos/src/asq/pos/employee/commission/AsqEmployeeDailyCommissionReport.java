package asq.pos.employee.commission;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import asq.pos.common.AsqConstant;
import asq.pos.register.sale.AsqHelper;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.op.AbstractListPromptOp;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.security.StationState;
import dtv.pos.storecalendar.IStoreCalendar;
import dtv.pricing2.DealSpaceGlobal;
import dtv.pricing2.IItemMatcher;
import dtv.pricing2.PricingDeal;
import dtv.xst.crm.impl.task.TaskNoteModel;
import dtv.xst.crm.impl.task.TaskQueryResult;
import dtv.xst.pricing.XSTPricingDescriptor;

public class AsqEmployeeDailyCommissionReport extends AbstractListPromptOp {

	@Inject
	AsqHelper asqHelper;

	@Inject
	StationState state;

	@Inject
	private DealSpaceGlobal _dealSpaceGlobal;

	@Inject
	private IStoreCalendar _storeCalendar;
	@Override
	public String getDefaultPromptKey() {
		return "DAILY_EMPLOYEE_COMMSSION";
	}

	@Override
	public IOpResponse handlePromptResponse(IXstEvent arg0) {
		return this.HELPER.completeResponse();
	}

	@Override
	protected String getEmptyListPromptKey() {
		return "ASSOCIATE_TASK_NO_NOTES";
	}

	@Override
	protected Object[] getPromptList(IXstEvent arg0) {
		List<AsqCalclatedEmployeeCommission> calculatedItemCommissionResults = new ArrayList<AsqCalclatedEmployeeCommission>();
		List<AsqEmpCommissionQueryResult> results = asqHelper.getEmployeeItemCommissionOnNetSale(state.getRetailLocationId(), state.getCurrentBusinessDate());

		if (!results.isEmpty()) {
			PricingDeal commissionDeal = getCommissionDeal(AsqConstant.ASQ_ITEM_COMMISSION, _dealSpaceGlobal.getDeals());
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

							calcCommission.setCommissionCalclauted(new BigDecimal(matcher.getMinTotal()).multiply(result.getItem_quantity(), MathContext.DECIMAL64));

							calcCommission.setCommissionMonth(null);
							calcCommission.setCommissionTargetAchived(null);
							calcCommission.setCommissionTargetDiff(null);

							calculatedItemCommissionResults.add(calcCommission);
						}
					}
				}
			}
		}
		return calculatedItemCommissionResults.toArray();
		   //      return noteList.toArray(new TaskNoteModel[noteList.size()]);
	}

	private PricingDeal getCommissionDeal(String argDealIdName, List<PricingDeal> argDealFromSys) {
		for (PricingDeal argCommissionDeal : argDealFromSys) {
			XSTPricingDescriptor nativeDeal = (XSTPricingDescriptor) argCommissionDeal.getNativeDeal();
			if (nativeDeal.getDescription().startsWith(argDealIdName)) {
				return argCommissionDeal;
			}
		}
		return null;
	}
}
