package asq.pos.bnpl.tabby.tender.service;

import java.util.ArrayList;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqBnplTabbyHistoryObj {
	
	//Buyer History
    private String registered_since;
    private Number loyalty_level;
    private Number wishlist_count;
    private boolean is_social_networks_connected;
    private boolean is_phone_number_verified;
    private boolean is_email_verified;

    //Order History
    private String purchased_at;
    private String amount;
    private String payment_method;
    private String status;
    private AsqBnplTabbyDetailsObj buyer;
    private AsqBnplTabbyDetailsObj shipping_address;
    private ArrayList<AsqBnplTabbyItemsObj> items;

    // Getter Methods

    public String getRegistered_since() {
        return registered_since;
    }

    public Number getLoyalty_level() {
        return loyalty_level;
    }

    public Number getWishlist_count() {
        return wishlist_count;
    }

    public boolean getIs_social_networks_connected() {
        return is_social_networks_connected;
    }

    public boolean getIs_phone_number_verified() {
        return is_phone_number_verified;
    }

    public boolean getIs_email_verified() {
        return is_email_verified;
    }

    // Setter Methods

    public void setRegistered_since(String registered_since) {
        this.registered_since = registered_since;
    }

    public void setLoyalty_level(Number loyalty_level) {
        this.loyalty_level = loyalty_level;
    }

    public void setWishlist_count(Number wishlist_count) {
        this.wishlist_count = wishlist_count;
    }

    public void setIs_social_networks_connected(boolean is_social_networks_connected) {
        this.is_social_networks_connected = is_social_networks_connected;
    }

    public void setIs_phone_number_verified(boolean is_phone_number_verified) {
        this.is_phone_number_verified = is_phone_number_verified;
    }

    public void setIs_email_verified(boolean is_email_verified) {
        this.is_email_verified = is_email_verified;
    }

	public String getPurchased_at() {
		return purchased_at;
	}

	public void setPurchased_at(String purchased_at) {
		this.purchased_at = purchased_at;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public ArrayList<AsqBnplTabbyItemsObj> getItems() {
		return items;
	}

	public void setItems(ArrayList<AsqBnplTabbyItemsObj> items) {
		this.items = items;
	}

}
