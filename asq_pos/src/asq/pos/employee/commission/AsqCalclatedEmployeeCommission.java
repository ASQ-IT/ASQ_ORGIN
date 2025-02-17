package asq.pos.employee.commission;

import java.math.BigDecimal;

public class AsqCalclatedEmployeeCommission {

	// Common
	String employeeName;
	Long employeeID;
	BigDecimal commissionCalclauted;

	// Item Commission
	BigDecimal itemCount;
	String commissionBusinessDate;

	// Monthly Commission
	String commissionMonth;
	Boolean commissionTargetAchived;
	BigDecimal commissionTargetDiff;

	public String getEmployeeName() {
		return employeeName;
	}

	public Long getEmployeeID() {
		return employeeID;
	}

	public BigDecimal getItemCount() {
		return itemCount;
	}

	public BigDecimal getCommissionCalclauted() {
		return commissionCalclauted;
	}

	public String getCommissionMonth() {
		return commissionMonth;
	}

	public String getCommissionBusinessDate() {
		return commissionBusinessDate;
	}

	public Boolean getCommissionTargetAchived() {
		return commissionTargetAchived;
	}

	public BigDecimal getCommissionTargetDiff() {
		return commissionTargetDiff;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public void setEmployeeID(Long employeeID) {
		this.employeeID = employeeID;
	}

	public void setItemCount(BigDecimal itemCount) {
		this.itemCount = itemCount;
	}

	public void setCommissionCalclauted(BigDecimal commissionCalclauted) {
		this.commissionCalclauted = commissionCalclauted;
	}

	public void setCommissionMonth(String commissionMonth) {
		this.commissionMonth = commissionMonth;
	}

	public void setCommissionBusinessDate(String commissionBusinessDate) {
		this.commissionBusinessDate = commissionBusinessDate;
	}

	public void setCommissionTargetAchived(Boolean commissionTargetAchived) {
		this.commissionTargetAchived = commissionTargetAchived;
	}

	public void setCommissionTargetDiff(BigDecimal commissionTargetDiff) {
		this.commissionTargetDiff = commissionTargetDiff;
	}

	@Override
	public String toString() {
		return "AsqCalclatedEmployeeCommission [employeeName=" + employeeName + ", employeeID=" + employeeID + ", itemCount=" + itemCount + ", commissionCalclauted=" + commissionCalclauted + ", commissionMonth="
				+ commissionMonth + ", commissionBusinessDate=" + commissionBusinessDate + ", commissionTargetAchived=" + commissionTargetAchived + ", commissionTargetDiff=" + commissionTargetDiff + "]";
	}

}
