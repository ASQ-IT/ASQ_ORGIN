package asq.pos.bnpl.tabby.tender.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqBnplTabbyAvlPrdInstObj {
	
	private String due_date;
	private String old_amount;
	private String amount;
	private String principal;
	private String service_fee;
	
	public String getDue_date() {
		return due_date;
	}
	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	public String getOld_amount() {
		return old_amount;
	}
	public void setOld_amount(String old_amount) {
		this.old_amount = old_amount;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getService_fee() {
		return service_fee;
	}
	public void setService_fee(String service_fee) {
		this.service_fee = service_fee;
	}

}
