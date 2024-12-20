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
			employee.append("Epmloyee Name").append(result.getEmployee_name()).append(" ID ").append(result.getEmployee_id());
			employee.append("\n");
			employee.append("Epmloyee Net Sale : ").append(result.getNet_amt());
			employee.append("\n");
			employee.append("Epmloyee Commission : ").append(result.getPerc_amt());
			employee.append("\n");
		}
		return employee.toString();
	}
}
