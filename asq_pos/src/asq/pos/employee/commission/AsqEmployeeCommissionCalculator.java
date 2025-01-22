package asq.pos.employee.commission;

import java.util.List;

import javax.inject.Inject;

import asq.pos.register.sale.AsqHelper;
import dtv.pos.iframework.security.StationState;

public class AsqEmployeeCommissionCalculator {

	@Inject
	AsqHelper asqHelper;

	@Inject
	StationState state;

	public String calculateNetSaleCommission() {
		List<AsqEmpCommissionQueryResult> results = asqHelper.getEmployeeCommissionOnNetSale(state.getRetailLocationId(), state.getCurrentBusinessDate());

		StringBuilder employee = new StringBuilder("Employee Commission \n");
		for (AsqEmpCommissionQueryResult result : results) {
			employee.append("Employee Name  ").append(result.getEmployee_name());
			employee.append("\n");
			employee.append("ID : ").append(result.getEmployee_id());
			employee.append("\n");
			employee.append("Employee Net Sale : ").append(result.getNet_amt());
			employee.append("\n");
			employee.append("Employee Commission : ").append(result.getPerc_amt());
			employee.append("\n");
		}
		return employee.toString();
	}
}
