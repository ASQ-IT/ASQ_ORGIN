package asq.pos.employee.commission;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import asq.pos.common.AsqConstant;
import asq.pos.register.sale.AsqHelper;
import dtv.pos.framework.op.AbstractListPromptOp;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.security.StationState;
import dtv.pos.storecalendar.DateRangeHelper;
import dtv.pos.storecalendar.IStoreCalendar;
import dtv.pos.storecalendar.StoreCalendarException;
import dtv.pricing2.DealSpaceGlobal;
import dtv.pricing2.PricingDeal;
import dtv.util.DateRange;
import dtv.xst.pricing.XSTPricingDescriptor;

public class AsqEmployeeMonthlyCommissionReport extends AbstractListPromptOp{

	
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
		// TODO Auto-generated method stub
		return "MONTHLY_EMPLOYEE_COMMSSION";
	}

	@Override
	public IOpResponse handlePromptResponse(IXstEvent arg0) {
		// TODO Auto-generated method stub
		return this.HELPER.completeResponse();
	}

	@Override
	protected String getEmptyListPromptKey() {
		// TODO Auto-generated method stub
		return "ASSOCIATE_TASK_NO_NOTES";
	}

	@Override
	protected Object[] getPromptList(IXstEvent arg0) {
		List<AsqCalclatedEmployeeCommission> calculatedItemCommissionResults = new ArrayList<AsqCalclatedEmployeeCommission>();
		DateRange date;
		try {
			date = DateRangeHelper.getMonth(_storeCalendar, new Date(), 0);
		List<AsqEmpCommissionQueryResult> results = asqHelper.getEmployeeMonthlyCommissionOnNetSale(state.getRetailLocationId(), date);
		if (!results.isEmpty()) {
			PricingDeal commissionDeal = getCommissionDeal(AsqConstant.ASQ_STORE_SALE_COMMISSION, _dealSpaceGlobal.getDeals());

			if (null != commissionDeal && 0 != commissionDeal.getSubtotalMin() && 0 != commissionDeal.getSubtotalMax()) {
				BigDecimal caluatedTarget = new BigDecimal(0);
				for (AsqEmpCommissionQueryResult result : results) {
					caluatedTarget = caluatedTarget.add(result.getNet_amt());
				}

				BigDecimal commisionPerc = new BigDecimal(commissionDeal.getSubtotalMin());
				BigDecimal commisionTarget = new BigDecimal(commissionDeal.getSubtotalMax());

				caluatedTarget = commisionTarget.subtract(caluatedTarget);
				BigDecimal calclatedAmount = caluatedTarget.divide(commisionPerc, 2, asqHelper.getSystemRoundingMode()).scaleByPowerOfTen(2);

				for (AsqEmpCommissionQueryResult result : results) {
					AsqCalclatedEmployeeCommission calcCommission = new AsqCalclatedEmployeeCommission();
					calcCommission.setEmployeeName(result.getEmployee_name());
					calcCommission.setEmployeeID(result.getEmployee_id());

					Calendar mCalendar = Calendar.getInstance();
					String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
					calcCommission.setCommissionMonth(month);

					calcCommission.setCommissionTargetDiff(caluatedTarget);
					if (caluatedTarget.compareTo(commisionTarget) >= 0) {
						calcCommission.setCommissionTargetAchived(true);
						calcCommission.setCommissionCalclauted(calclatedAmount);
					} else {
						calcCommission.setCommissionTargetAchived(false);
						calcCommission.setCommissionCalclauted(null);
					}
					calculatedItemCommissionResults.add(calcCommission);
				}
			}
		}
		} catch (StoreCalendarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calculatedItemCommissionResults.toArray();
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
