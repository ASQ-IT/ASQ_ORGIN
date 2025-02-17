package asq.pos.employee.commission;

import java.util.List;

import javax.inject.Inject;

import dtv.pos.framework.op.AbstractListPromptOp;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;

public class AsqEmployeeMonthlyCommissionOp extends AbstractListPromptOp {

	@Inject
	AsqEmployeeCommissionCalculator comissionCal;

	@Override
	public String getDefaultPromptKey() {
		return "MONTHLY_EMPLOYEE_COMMSSION";
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
		List<AsqCalclatedEmployeeCommission> calculatedItemCommissionResults = comissionCal.calculateStoreSaleCommission();
		if (!calculatedItemCommissionResults.isEmpty()) {
			return calculatedItemCommissionResults.toArray();
		}
		return null;
	}

}
