package asq.pos.bnpl.tamara.tender.service;

import java.util.ArrayList;
import java.util.Date;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

import asq.pos.loyalty.stc.tender.service.AsqSTCErrorDesc;
import dtv.service.req.IServiceResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqSubmitBnplTamraServiceResponse implements IServiceResponse{
	
/**create session srv response */
	
	@JsonProperty("order_id")
    private String order_id;
	
	@JsonProperty("checkout_id")
	private String checkout_id;

	@JsonProperty("message")
	private String message;
	
	@JsonProperty("data")
    private Object data;
	
	@JsonProperty("title")
    private Object title;
	
	@JsonProperty("screen_type")
    private Object screen_type;
	
	@JsonProperty("checkout_link")
    private String checkout_link;
    
/** cancel session srv response */		
	@JsonProperty("order_was_voided")
	private boolean order_was_voided;
	
    @JsonProperty("captured_amount")
    private AsqBnplTamaraAmountObj captured_amount;
    
    @JsonProperty("store_code")
    private String store_code;
    
/** refund srv response */    
    @JsonProperty("comment")
    private String comment;

    @JsonProperty("refund_id")
    private String refund_id;
    
    @JsonProperty("capture_id")
    private String capture_id;

/** order detail srv response */    
//	@JsonProperty("order_id")
//    private String order_id;

    @JsonProperty("order_reference_id")
    private Long order_reference_id;
    
    @JsonProperty("order_number")
    private String order_number;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("consumer")
    private AsqBnplTamaraConsumerObj consumer;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("shipping_address")
    private AsqBnplTamaraAddressObj shipping_address;
    
    @JsonProperty("billing_address")
    private AsqBnplTamaraAddressObj billing_address;
    
    @JsonProperty("items")
    private ArrayList<AsqBnplTamaraItemObj> items;
    
    @JsonProperty("payment_type")
    private String payment_type;
    
    @JsonProperty("instalments")
    private int instalments;
    
    @JsonProperty("total_amount")
    private AsqBnplTamaraAmountObj total_amount;
    
    @JsonProperty("shipping_amount")
    private AsqBnplTamaraAmountObj shipping_amount;
    
    @JsonProperty("tax_amount")
    private AsqBnplTamaraAmountObj tax_amount;
    
    public AsqBnplTamaraDisAmtObj getDiscount_amount() {
		return discount_amount;
	}
	public void setDiscount_amount(AsqBnplTamaraDisAmtObj discount_amount) {
		this.discount_amount = discount_amount;
	}

	@JsonProperty("discount_amount")
    private AsqBnplTamaraDisAmtObj discount_amount;
    
//    @JsonProperty("captured_amount")
//    private AsqBnplTamaraAmountObj captured_amount;
    
    @JsonProperty("refunded_amount")
    private AsqBnplTamaraAmountObj refunded_amount;
    
    @JsonProperty("canceled_amount")
    private AsqBnplTamaraAmountObj canceled_amount;
    
    @JsonProperty("paid_amount")
    private AsqBnplTamaraAmountObj paid_amount;
    
    @JsonProperty("settlement_status")
    private String settlement_status;
    
    @JsonProperty("settlement_date")
    private Date settlement_date;
    
    @JsonProperty("created_at")
    private Date created_at;
    
    @JsonProperty("checkout_deeplink")
	private String checkout_deeplink;
    
    public String getCheckout_deeplink() {
		return checkout_deeplink;
	}
	public void setCheckout_deeplink(String checkout_deeplink) {
		this.checkout_deeplink = checkout_deeplink;
	}

	@JsonProperty("wallet_prepaid_amount")
    private AsqBnplTamaraAmountObj wallet_prepaid_amount;
    
//    @JsonProperty("transactions")
//    private Transactions transactions;
    
    @JsonProperty("processing")
    private boolean processing;
    
//    @JsonProperty("store_code")
//    private String store_code;
    
//    @JsonProperty("additional_data")
//    private AsqBnplTamaraAddDataObj additional_data;
    
//    private ArrayList<Errors> errors;
//    private String message;
//    private Object data;
//    private Object title;
//    private Object screen_type;
    @JsonProperty("errors")
	private AsqSTCErrorDesc [] errors;
    
    public AsqSTCErrorDesc[] getErrors() {
		return errors;
	}
	public void setErrors(AsqSTCErrorDesc[] errors) {
		this.errors = errors;
	}


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCheckout_id() {
        return checkout_id;
    }

    public void setCheckout_id(String checkout_id) {
        this.checkout_id = checkout_id;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public Object getScreen_type() {
        return screen_type;
    }

    public void setScreen_type(Object screen_type) {
        this.screen_type = screen_type;
    }

	public boolean isOrder_was_voided() {
		return order_was_voided;
	}

	public void setOrder_was_voided(boolean order_was_voided) {
		this.order_was_voided = order_was_voided;
	}

	public AsqBnplTamaraAmountObj getCaptured_amount() {
		return captured_amount;
	}

	public void setCaptured_amount(AsqBnplTamaraAmountObj captured_amount) {
		this.captured_amount = captured_amount;
	}

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRefund_id() {
		return refund_id;
	}

	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}

	public String getCapture_id() {
		return capture_id;
	}

	public void setCapture_id(String capture_id) {
		this.capture_id = capture_id;
	}

	public Long getOrder_reference_id() {
		return order_reference_id;
	}

	public void setOrder_reference_id(Long order_reference_id) {
		this.order_reference_id = order_reference_id;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AsqBnplTamaraConsumerObj getConsumer() {
		return consumer;
	}

	public void setConsumer(AsqBnplTamaraConsumerObj consumer) {
		this.consumer = consumer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AsqBnplTamaraAddressObj getShipping_address() {
		return shipping_address;
	}

	public void setShipping_address(AsqBnplTamaraAddressObj shipping_address) {
		this.shipping_address = shipping_address;
	}

	public AsqBnplTamaraAddressObj getBilling_address() {
		return billing_address;
	}

	public void setBilling_address(AsqBnplTamaraAddressObj billing_address) {
		this.billing_address = billing_address;
	}

	public ArrayList<AsqBnplTamaraItemObj> getItems() {
		return items;
	}

	public void setItems(ArrayList<AsqBnplTamaraItemObj> items) {
		this.items = items;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public int getInstalments() {
		return instalments;
	}

	public void setInstalments(int instalments) {
		this.instalments = instalments;
	}

	public AsqBnplTamaraAmountObj getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(AsqBnplTamaraAmountObj total_amount) {
		this.total_amount = total_amount;
	}

	public AsqBnplTamaraAmountObj getShipping_amount() {
		return shipping_amount;
	}

	public void setShipping_amount(AsqBnplTamaraAmountObj shipping_amount) {
		this.shipping_amount = shipping_amount;
	}

	public AsqBnplTamaraAmountObj getTax_amount() {
		return tax_amount;
	}

	public void setTax_amount(AsqBnplTamaraAmountObj tax_amount) {
		this.tax_amount = tax_amount;
	}

	/*
	 * public AsqBnplTamaraAmountObj getDiscount_amount() { return discount_amount;
	 * }
	 * 
	 * public void setDiscount_amount(AsqBnplTamaraAmountObj discount_amount) {
	 * this.discount_amount = discount_amount; }
	 */

	public AsqBnplTamaraAmountObj getRefunded_amount() {
		return refunded_amount;
	}

	public void setRefunded_amount(AsqBnplTamaraAmountObj refunded_amount) {
		this.refunded_amount = refunded_amount;
	}

	public AsqBnplTamaraAmountObj getCanceled_amount() {
		return canceled_amount;
	}

	public void setCanceled_amount(AsqBnplTamaraAmountObj canceled_amount) {
		this.canceled_amount = canceled_amount;
	}

	public AsqBnplTamaraAmountObj getPaid_amount() {
		return paid_amount;
	}

	public void setPaid_amount(AsqBnplTamaraAmountObj paid_amount) {
		this.paid_amount = paid_amount;
	}

	public String getSettlement_status() {
		return settlement_status;
	}

	public void setSettlement_status(String settlement_status) {
		this.settlement_status = settlement_status;
	}

	public Date getSettlement_date() {
		return settlement_date;
	}

	public void setSettlement_date(Date settlement_date) {
		this.settlement_date = settlement_date;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public AsqBnplTamaraAmountObj getWallet_prepaid_amount() {
		return wallet_prepaid_amount;
	}

	public void setWallet_prepaid_amount(AsqBnplTamaraAmountObj wallet_prepaid_amount) {
		this.wallet_prepaid_amount = wallet_prepaid_amount;
	}

	public boolean isProcessing() {
		return processing;
	}

	public void setProcessing(boolean processing) {
		this.processing = processing;
	}
	
	public String getCheckout_link() {
		return checkout_link;
	}
	public void setCheckout_link(String checkout_link) {
		this.checkout_link = checkout_link;
	}
}
