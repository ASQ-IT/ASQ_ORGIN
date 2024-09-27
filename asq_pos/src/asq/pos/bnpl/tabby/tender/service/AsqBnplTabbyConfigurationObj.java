package asq.pos.bnpl.tabby.tender.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqBnplTabbyConfigurationObj {
	
//    private String currency;
//    private String app_type;
//    private boolean new_customer;
//    private String available_limit;
//    private String min_limit;
    private AsqBnplTabbyAvlPrdObj available_products;
//    ExtraAvailableProducts extra_available_products;
//    private String country;
    private String expires_at;
//    private boolean is_bank_card_required;
//    private String blocked_until;
//    private boolean hide_closing_icon;
//    private String pos_provider;
//    private boolean is_tokenized;
//    private String disclaimer;
//    private String help;
//    private boolean is_ipqs_required;
//    private String orders_count;
//    private String has_identity;
//    MonthlyBilling monthly_billing;
    AsqBnplTabbyDetailsObj products;
//    private String order_details_mode;
	public AsqBnplTabbyAvlPrdObj getAvailable_products() {
		return available_products;
	}
	public void setAvailable_products(AsqBnplTabbyAvlPrdObj available_products) {
		this.available_products = available_products;
	}
	public String getExpires_at() {
		return expires_at;
	}
	public void setExpires_at(String expires_at) {
		this.expires_at = expires_at;
	}
	public AsqBnplTabbyDetailsObj getProducts() {
		return products;
	}
	public void setProducts(AsqBnplTabbyDetailsObj products) {
		this.products = products;
	}


}
