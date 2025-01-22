package asq.pos.bnpl.tabby.tender.service;

import java.util.ArrayList;
import java.util.Locale;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;

import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraAddDataObj;
import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraAmountObj;
import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraItemObj;
import dtv.servicex.impl.req.ServiceRequest;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsqSubmitBnplTabbyServiceRequest extends ServiceRequest implements IAsqSubmitBnplTabbyServiceRequest {

	private String id;
	private AsqBnplTabbyPaymentObj payment;
	private String lang;
	private String merchant_code;
	private String amount;
	private String reference_id;
	private String reason;
	private String created_at;
	private AsqBnplTabbyAmountObj total_amount;
	private String phone_number;
	private ArrayList<AsqBnplTabbyItemsObj> items;
	private String order_reference_id;
	private AsqBnplTabbyAddDataObj additional_data;
	private String order_id;
	private String comment;
	private String store_code;
	private String checkout_id;
	private Locale locale;

	public AsqBnplTabbyPaymentObj getPayment() {
		return payment;
	}

	public String getMerchant_code() {
		return merchant_code;
	}

	public void setPayment(AsqBnplTabbyPaymentObj payment) {
		this.payment = payment;
	}

	public void setMerchant_code(String merchant_code) {
		this.merchant_code = merchant_code;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReference_id() {
		return reference_id;
	}

	public void setReference_id(String reference_id) {
		this.reference_id = reference_id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public AsqBnplTabbyAmountObj getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(AsqBnplTabbyAmountObj total_amount) {
		this.total_amount = total_amount;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getOrder_reference_id() {
		return order_reference_id;
	}

	public void setOrder_reference_id(String order_reference_id) {
		this.order_reference_id = order_reference_id;
	}

	public ArrayList<AsqBnplTabbyItemsObj> getItems() {
		return items;
	}

	public void setItems(ArrayList<AsqBnplTabbyItemsObj> items) {
		this.items = items;
	}

	public AsqBnplTabbyAddDataObj getAdditional_data() {
		return additional_data;
	}

	public void setAdditional_data(AsqBnplTabbyAddDataObj additional_data) {
		this.additional_data = additional_data;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getCheckout_id() {
		return checkout_id;
	}

	public void setCheckout_id(String checkout_id) {
		this.checkout_id = checkout_id;
	}

}
