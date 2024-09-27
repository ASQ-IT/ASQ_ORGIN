package asq.pos.bnpl.tabby.tender.service;

import java.util.ArrayList;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqBnplTabbyCapturesObj {

    private String id;
    private String created_at;
    private String amount;
    private String tax_amount;
    private String shipping_amount;
    private String discount_amount;
    private ArrayList<AsqBnplTabbyItemsObj> items;
    private String reference_id;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(String tax_amount) {
        this.tax_amount = tax_amount;
    }

    public String getShipping_amount() {
        return shipping_amount;
    }

    public void setShipping_amount(String shipping_amount) {
        this.shipping_amount = shipping_amount;
    }

    public String getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(String discount_amount) {
        this.discount_amount = discount_amount;
    }

    public ArrayList<AsqBnplTabbyItemsObj> getItems() {
        return items;
    }

    public void setItems(ArrayList<AsqBnplTabbyItemsObj> items) {
        this.items = items;
    }

	public String getReference_id() {
		return reference_id;
	}

	public void setReference_id(String reference_id) {
		this.reference_id = reference_id;
	}

}
