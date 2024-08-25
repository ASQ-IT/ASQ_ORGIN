package asq.pos.bnpl.tamara.tender.service;

import java.util.ArrayList;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;

import dtv.servicex.impl.req.ServiceRequest;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsqSubmitBnplTamraServiceRequest extends ServiceRequest implements IAsqSubmitBnplTamraServiceRequest {
	
    private AsqBnplTamaraAmountObj total_amount;
    private String phone_number;
    private ArrayList<AsqBnplTamaraItemObj> items;
    private Long order_reference_id;
    private AsqBnplTamaraAddDataObj additional_data;
    private String order_id;
    private String comment;
    private String store_code;
    private String checkout_id;

    public AsqBnplTamaraAmountObj getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(AsqBnplTamaraAmountObj total_amount) {
        this.total_amount = total_amount;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


    public Long getOrder_reference_id() {
        return order_reference_id;
    }

    public void setOrder_reference_id(Long order_reference_id) {
        this.order_reference_id = order_reference_id;
    }

	public ArrayList<AsqBnplTamaraItemObj> getItems() {
		return items;
	}

	public void setItems(ArrayList<AsqBnplTamaraItemObj> items) {
		this.items = items;
	}

	public AsqBnplTamaraAddDataObj getAdditional_data() {
		return additional_data;
	}

	public void setAdditional_data(AsqBnplTamaraAddDataObj additional_data) {
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
