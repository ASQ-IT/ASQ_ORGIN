package asq.pos.bnpl.tamara.tender.service;

import java.util.Date;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

import dtv.service.req.IServiceResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqSubmitBnplTamraServiceResponse implements IServiceResponse{
	
	//create session srv response
	@JsonProperty("order_id")
    private String order_id;
	
	@JsonProperty("checkout_id")
	private String checkout_id;

//    private ArrayList<Errors> errors;

	@JsonProperty("message")
	private String message;
	
	@JsonProperty("data")
    private Object data;
	
	@JsonProperty("title")
    private Object title;
	
	@JsonProperty("screen_type")
    private Object screen_type;
    
    
    //cancel session srv response	
	@JsonProperty("order_was_voided")
	private boolean order_was_voided;
	
    @JsonProperty("captured_amount")
    private AsqBnplTamaraAmountObj captured_amount;

//    private String message;
    
    @JsonProperty("store_code")
    private String store_code;

//    private ArrayList<Errors> errors;
    
    //refund srv response    
    @JsonProperty("comment")
    private String comment;

    @JsonProperty("refund_id")
    private String refund_id;
    
    @JsonProperty("capture_id")
    private String capture_id;

//    private String order_id;
//    private ArrayList<Errors> errors;
//    private String message;
//    private Object data;
//    private Object title;
//    private Object screen_type;

    //order detail srv response
//    private String order_id;
    @JsonProperty("order_reference_id")
    private String order_reference_id;
    
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
//    private ArrayList<Item> items;
    
    @JsonProperty("payment_type")
    private String payment_type;
    
    @JsonProperty("instalments")
    private int instalments;
//    private TotalAmount total_amount;
    
    @JsonProperty("shipping_amount")
    private AsqBnplTamaraAmountObj shipping_amount;
    
    @JsonProperty("tax_amount")
    private AsqBnplTamaraAmountObj tax_amount;
    
    @JsonProperty("discount_amount")
    private AsqBnplTamaraAmountObj discount_amount;
    
//    private CapturedAmount captured_amount;
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
    
    @JsonProperty("wallet_prepaid_amount")
    private AsqBnplTamaraAmountObj wallet_prepaid_amount;
    
//    @JsonProperty("transactions")
//    private Transactions transactions;
    
    @JsonProperty("processing")
    private boolean processing;
//    private String store_code;
    
    @JsonProperty("additional_data")
    private AsqBnplTamaraAddDataObj additional_data;
//    private ArrayList<Errors> errors;
//    private String message;
//    private Object data;
//    private Object title;
//    private Object screen_type;
    


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

//    public ArrayList<Errors> getErrors() {
//        return errors;
//    }
//
//    public void setErrors(ArrayList<Errors> errors) {
//        this.errors = errors;
//    }

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


}
