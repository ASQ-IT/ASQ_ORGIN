package asq.pos.employee.commission;

import java.math.BigDecimal;

import dtv.data2.access.AbstractQueryResult;
import dtv.data2.access.IObjectId;

public class AsqEmpCommissionQueryResult extends AbstractQueryResult {

	private static final long serialVersionUID = 1L;

	private long employee_id;
	private String employee_name;
	private String item_id;
	private BigDecimal item_quantity;
	private BigDecimal trans_count;
	private BigDecimal net_amt;
	private BigDecimal perc_amt;

	@Override
	protected IObjectId getObjectIdImpl() {
		return super.getObjectId();
	}

	public long getEmployee_id() {
		return employee_id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public String getItem_id() {
		return item_id;
	}

	public BigDecimal getItem_quantity() {
		return item_quantity;
	}

	public BigDecimal getTrans_count() {
		return trans_count;
	}

	public BigDecimal getNet_amt() {
		return net_amt;
	}

	public BigDecimal getPerc_amt() {
		return perc_amt;
	}

	public void setEmployee_id(long employee_id) {
		this.employee_id = employee_id;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public void setItem_quantity(BigDecimal item_quantity) {
		this.item_quantity = item_quantity;
	}

	public void setTrans_count(BigDecimal trans_count) {
		this.trans_count = trans_count;
	}

	public void setNet_amt(BigDecimal net_amt) {
		this.net_amt = net_amt;
	}

	public void setPerc_amt(BigDecimal perc_amt) {
		this.perc_amt = perc_amt;
	}
}
