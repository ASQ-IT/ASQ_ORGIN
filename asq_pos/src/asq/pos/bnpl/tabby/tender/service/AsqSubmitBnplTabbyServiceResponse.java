package asq.pos.bnpl.tabby.tender.service;

import java.util.ArrayList;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

import asq.pos.loyalty.stc.tender.service.AsqSTCErrorDesc;
import dtv.service.req.IServiceResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqSubmitBnplTabbyServiceResponse implements IServiceResponse{

	@JsonProperty("id")
    private String id;
	
//	@JsonProperty("warnings")
//    ArrayList< Object > warnings;
	
	@JsonProperty("configuration")
    private AsqBnplTabbyConfigurationObj configuration;
	
	@JsonProperty("api_url")
    private String api_url;
	
	@JsonProperty("token")
    private String token;
	
	@JsonProperty("flow")
    private String flow;
	
	@JsonProperty("payment")
    private AsqBnplTabbyPaymentObj payment;
	
	@JsonProperty("status")
    private String status; 
	
	@JsonProperty("customer")
    private Object customer;
	
	@JsonProperty("juicyscore")
    private Object juicyscore;
	
	@JsonProperty("merchant_urls")
    private AsqBnplTabbyMerchantObj merchant_urls;
	
	@JsonProperty("product_type")
    private String product_type = null;
	
	@JsonProperty("lang")
    private String lang;
	
	@JsonProperty("locale")
    private String locale;
	
	@JsonProperty("seon_session_id")
    private String seon_session_id;
	
	@JsonProperty("merchant")
    private AsqBnplTabbyMerchantObj merchant;
	
	@JsonProperty("merchant_code")
    private String merchant_code;
	
	@JsonProperty("terms_accepted")
    private boolean terms_accepted;
	
	@JsonProperty("promo")
    private String promo;
	
	@JsonProperty("created_at")
    private String created_at;
	
	@JsonProperty("expires_at")
    private String expires_at;

	@JsonProperty("is_test")
    private boolean is_test;
	
	@JsonProperty("test")
    private boolean test;
	
	@JsonProperty("product")
    private AsqBnplTabbyDetailsObj product;
	
	@JsonProperty("amount")
    private String amount;
	
	@JsonProperty("currency")
    private String currency;
	
	@JsonProperty("description")
    private String description;
	
	@JsonProperty("buyer")
    private AsqBnplTabbyDetailsObj buyer;
	
	@JsonProperty("shipping_address")
    private AsqBnplTabbyDetailsObj shipping_address;
	
	@JsonProperty("order")
    private AsqBnplTabbyDetailsObj order;
	
	@JsonProperty("captures")
    ArrayList< AsqBnplTabbyCapturesObj > captures;
	
	@JsonProperty("refunds")
    ArrayList < AsqBnplTabbyRefundsObj > refunds;
	
	@JsonProperty("buyer_history")
    private AsqBnplTabbyHistoryObj buyer_history;
	
	@JsonProperty("order_history")
    ArrayList <AsqBnplTabbyHistoryObj> order_history;
	
	@JsonProperty("is_ipqs_requested")
    private boolean is_ipqs_requested;
	
	@JsonProperty("errors")
	private AsqBnplTabbyErrorDesc error;
	
	/*
	 * @JsonProperty("errors") private AsqBnplTabbyErrorDesc [] errors;
	 */
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApi_url() {
        return api_url;
    }

    public void setApi_url(String api_url) {
        this.api_url = api_url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public AsqBnplTabbyPaymentObj getPayment() {
        return payment;
    }

    public void setPayment(AsqBnplTabbyPaymentObj payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getSeon_session_id() {
        return seon_session_id;
    }

    public void setSeon_session_id(String seon_session_id) {
        this.seon_session_id = seon_session_id;
    }

    public String getMerchant_code() {
        return merchant_code;
    }

    public void setMerchant_code(String merchant_code) {
        this.merchant_code = merchant_code;
    }

    public boolean isTerms_accepted() {
        return terms_accepted;
    }

    public void setTerms_accepted(boolean terms_accepted) {
        this.terms_accepted = terms_accepted;
    }

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

	public AsqBnplTabbyConfigurationObj getConfiguration() {
		return configuration;
	}

	public void setConfiguration(AsqBnplTabbyConfigurationObj configuration) {
		this.configuration = configuration;
	}

	public Object getCustomer() {
		return customer;
	}

	public void setCustomer(Object customer) {
		this.customer = customer;
	}

	public Object getJuicyscore() {
		return juicyscore;
	}

	public void setJuicyscore(Object juicyscore) {
		this.juicyscore = juicyscore;
	}

	public AsqBnplTabbyMerchantObj getMerchant_urls() {
		return merchant_urls;
	}

	public void setMerchant_urls(AsqBnplTabbyMerchantObj merchant_urls) {
		this.merchant_urls = merchant_urls;
	}

	public AsqBnplTabbyMerchantObj getMerchant() {
		return merchant;
	}

	public void setMerchant(AsqBnplTabbyMerchantObj merchant) {
		this.merchant = merchant;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getExpires_at() {
		return expires_at;
	}

	public void setExpires_at(String expires_at) {
		this.expires_at = expires_at;
	}

	public boolean isIs_test() {
		return is_test;
	}

	public void setIs_test(boolean is_test) {
		this.is_test = is_test;
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

	public AsqBnplTabbyDetailsObj getProduct() {
		return product;
	}

	public void setProduct(AsqBnplTabbyDetailsObj product) {
		this.product = product;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AsqBnplTabbyDetailsObj getBuyer() {
		return buyer;
	}

	public void setBuyer(AsqBnplTabbyDetailsObj buyer) {
		this.buyer = buyer;
	}

	public AsqBnplTabbyDetailsObj getShipping_address() {
		return shipping_address;
	}

	public void setShipping_address(AsqBnplTabbyDetailsObj shipping_address) {
		this.shipping_address = shipping_address;
	}

	public AsqBnplTabbyDetailsObj getOrder() {
		return order;
	}

	public void setOrder(AsqBnplTabbyDetailsObj order) {
		this.order = order;
	}

	public ArrayList<AsqBnplTabbyCapturesObj> getCaptures() {
		return captures;
	}

	public void setCaptures(ArrayList<AsqBnplTabbyCapturesObj> captures) {
		this.captures = captures;
	}

	public ArrayList<AsqBnplTabbyRefundsObj> getRefunds() {
		return refunds;
	}

	public void setRefunds(ArrayList<AsqBnplTabbyRefundsObj> refunds) {
		this.refunds = refunds;
	}

	public AsqBnplTabbyHistoryObj getBuyer_history() {
		return buyer_history;
	}

	public void setBuyer_history(AsqBnplTabbyHistoryObj buyer_history) {
		this.buyer_history = buyer_history;
	}

	public ArrayList<AsqBnplTabbyHistoryObj> getOrder_history() {
		return order_history;
	}

	public void setOrder_history(ArrayList<AsqBnplTabbyHistoryObj> order_history) {
		this.order_history = order_history;
	}

	public AsqBnplTabbyErrorDesc getError() {
		return error;
	}

	public void setError(AsqBnplTabbyErrorDesc error) {
		this.error = error;
	}

	public boolean isIs_ipqs_requested() {
		return is_ipqs_requested;
	}

	public void setIs_ipqs_requested(boolean is_ipqs_requested) {
		this.is_ipqs_requested = is_ipqs_requested;
	}
	/*
	 * public void setErrors(AsqBnplTabbyErrorDesc[] errors) { this.errors = errors;
	 * }
	 */

}
