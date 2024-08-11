package asq.pos.bnpl.tamara.tender.service;

import java.util.ArrayList;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;

import dtv.servicex.impl.req.ServiceRequest;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsqSubmitBnplTamraServiceRequest extends ServiceRequest implements IAsqSubmitBnplTamraServiceRequest {
	
    private AsqBnplTamaraAmountObj total_amount;
    private String phone_number;
    private ArrayList<AsqBnplTamaraItemObj> items;
    private String order_reference_id;
    private AsqBnplTamaraAddDataObj additional_data;

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


    public String getOrder_reference_id() {
        return order_reference_id;
    }

    public void setOrder_reference_id(String order_reference_id) {
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

}
