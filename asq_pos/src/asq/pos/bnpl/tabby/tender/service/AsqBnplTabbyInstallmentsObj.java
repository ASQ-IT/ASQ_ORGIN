package asq.pos.bnpl.tabby.tender.service;

import java.util.ArrayList;
import java.util.Date;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqBnplTabbyInstallmentsObj {

    private String type;
    private boolean is_available;
    private String rejection_reason = null;
    private String web_url;
    private String qr_code;
    
    private String downpayment;
    private String downpayment_percent;
    private Object downpayment_increased_reason;
    private String amount_to_pay;
    private Object old_downpayment_total;
    private String downpayment_total;
    private String total_service_fee;
    private String service_fee_policy;
    private String order_amount;
    private Date next_payment_date;
    private ArrayList<AsqBnplTabbyAvlPrdInstObj> installments;
    private boolean pay_after_delivery;
    private String pay_per_installment;
    private Object original_type;
    private String status;
    private int id;
    private int installments_count;
    private String installment_period;
    private String service_fee;
    private String due_date;
    private Object old_amount;
    private String amount;
    private String principal;
    
	public String getWeb_url() {
		return web_url;
	}
	public void setWeb_url(String web_url) {
		this.web_url = web_url;
	}
	public String getQr_code() {
		return qr_code;
	}
	public void setQr_code(String qr_code) {
		this.qr_code = qr_code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isIs_available() {
		return is_available;
	}
	public void setIs_available(boolean is_available) {
		this.is_available = is_available;
	}
	public String getRejection_reason() {
		return rejection_reason;
	}
	public void setRejection_reason(String rejection_reason) {
		this.rejection_reason = rejection_reason;
	}
	public String getDownpayment() {
		return downpayment;
	}
	public void setDownpayment(String downpayment) {
		this.downpayment = downpayment;
	}
	public String getDownpayment_percent() {
		return downpayment_percent;
	}
	public void setDownpayment_percent(String downpayment_percent) {
		this.downpayment_percent = downpayment_percent;
	}
	public Object getDownpayment_increased_reason() {
		return downpayment_increased_reason;
	}
	public void setDownpayment_increased_reason(Object downpayment_increased_reason) {
		this.downpayment_increased_reason = downpayment_increased_reason;
	}
	public String getAmount_to_pay() {
		return amount_to_pay;
	}
	public void setAmount_to_pay(String amount_to_pay) {
		this.amount_to_pay = amount_to_pay;
	}
	public Object getOld_downpayment_total() {
		return old_downpayment_total;
	}
	public void setOld_downpayment_total(Object old_downpayment_total) {
		this.old_downpayment_total = old_downpayment_total;
	}
	public String getDownpayment_total() {
		return downpayment_total;
	}
	public void setDownpayment_total(String downpayment_total) {
		this.downpayment_total = downpayment_total;
	}
	public String getTotal_service_fee() {
		return total_service_fee;
	}
	public void setTotal_service_fee(String total_service_fee) {
		this.total_service_fee = total_service_fee;
	}
	public String getService_fee_policy() {
		return service_fee_policy;
	}
	public void setService_fee_policy(String service_fee_policy) {
		this.service_fee_policy = service_fee_policy;
	}
	public String getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}
	public Date getNext_payment_date() {
		return next_payment_date;
	}
	public void setNext_payment_date(Date next_payment_date) {
		this.next_payment_date = next_payment_date;
	}
	public ArrayList<AsqBnplTabbyAvlPrdInstObj> getInstallments() {
		return installments;
	}
	public void setInstallments(ArrayList<AsqBnplTabbyAvlPrdInstObj> installments) {
		this.installments = installments;
	}
	public boolean isPay_after_delivery() {
		return pay_after_delivery;
	}
	public void setPay_after_delivery(boolean pay_after_delivery) {
		this.pay_after_delivery = pay_after_delivery;
	}
	public String getPay_per_installment() {
		return pay_per_installment;
	}
	public void setPay_per_installment(String pay_per_installment) {
		this.pay_per_installment = pay_per_installment;
	}
	public Object getOriginal_type() {
		return original_type;
	}
	public void setOriginal_type(Object original_type) {
		this.original_type = original_type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInstallments_count() {
		return installments_count;
	}
	public void setInstallments_count(int installments_count) {
		this.installments_count = installments_count;
	}
	public String getInstallment_period() {
		return installment_period;
	}
	public void setInstallment_period(String installment_period) {
		this.installment_period = installment_period;
	}
	public String getService_fee() {
		return service_fee;
	}
	public void setService_fee(String service_fee) {
		this.service_fee = service_fee;
	}
	public String getDue_date() {
		return due_date;
	}
	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	public Object getOld_amount() {
		return old_amount;
	}
	public void setOld_amount(Object old_amount) {
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
}
