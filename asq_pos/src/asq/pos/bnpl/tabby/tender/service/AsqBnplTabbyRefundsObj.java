package asq.pos.bnpl.tabby.tender.service;

import java.util.ArrayList;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqBnplTabbyRefundsObj {

    private String id;
    private String capture_id;
    private String amount;
    private String reference_id;
    private String reason;
    private String created_at;
    private ArrayList<AsqBnplTabbyItemsObj> items;
    private String ack_type;
    private String acknowledged;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ArrayList<AsqBnplTabbyItemsObj> getItems() {
        return items;
    }

    public void setItems(ArrayList<AsqBnplTabbyItemsObj> items) {
        this.items = items;
    }

    public String getCapture_id() {
        return capture_id;
    }

    public void setCapture_id(String capture_id) {
        this.capture_id = capture_id;
    }

    public String getAck_type() {
        return ack_type;
    }

    public void setAck_type(String ack_type) {
        this.ack_type = ack_type;
    }

    public String getAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(String acknowledged) {
        this.acknowledged = acknowledged;
    }

}
