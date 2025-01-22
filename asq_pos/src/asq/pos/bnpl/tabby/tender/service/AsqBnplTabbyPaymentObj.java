package asq.pos.bnpl.tabby.tender.service;

import java.util.ArrayList;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqBnplTabbyPaymentObj {
    private String id;
    private String created_at;
    private String expires_at;
    private String status;
    private boolean test;
    private AsqBnplTabbyDetailsObj product;
    private String amount;
    private String currency;
    private String description;
    private AsqBnplTabbyDetailsObj buyer;
    private AsqBnplTabbyDetailsObj shipping_address;
    private AsqBnplTabbyOrderDetailsObj order;
    private AsqBnplTabbyHistoryObj buyer_history;
    ArrayList <AsqBnplTabbyHistoryObj> order_history;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public AsqBnplTabbyOrderDetailsObj getOrder() {
        return order;
    }

    public void setOrder(AsqBnplTabbyOrderDetailsObj order) {
        this.order = order;
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

//    public Meta getMeta() {
//        return meta;
//    }
//
//    public void setMeta(Meta meta) {
//        this.meta = meta;
//    }
//
//    public boolean isCancelable() {
//        return cancelable;
//    }
//
//    public void setCancelable(boolean cancelable) {
//        this.cancelable = cancelable;
//    }

    public AsqBnplTabbyDetailsObj getProduct() {
        return product;
    }

    public void setProduct(AsqBnplTabbyDetailsObj product) {
        this.product = product;
    }

//    public Attachment getAttachment() {
//        return attachment;
//    }
//
//    public void setAttachment(Attachment attachment) {
//        this.attachment = attachment;
//    }
//
//    public ErrorResponse getErrorResponse() {
//        return errorResponse;
//    }
//
//    public void setErrorResponse(ErrorResponse errorResponse) {
//        this.errorResponse = errorResponse;
//    }
//
//    public boolean isTest() {
//        return test;
//    }
//
//    public void setTest(boolean test) {
//        this.test = test;
//    }
//
//    public boolean isIs_expired() {
//        return is_expired;
//    }
//
//    public void setIs_expired(boolean is_expired) {
//        this.is_expired = is_expired;
//    }

}
