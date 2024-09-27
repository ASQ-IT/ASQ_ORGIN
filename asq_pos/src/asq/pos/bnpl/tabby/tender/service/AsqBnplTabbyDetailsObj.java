package asq.pos.bnpl.tabby.tender.service;

import java.util.ArrayList;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsqBnplTabbyDetailsObj {
	
		//Buyer details
	    private String id;
	    private String name;
	    private String email;
	    private String phone;
	    private String dob;

	    //Shipping Address
	    private String city;
	    private String address;
	    private String zip;

	    //Order details
	    private String tax_amount;
	    private String shipping_amount;
	    private String discount_amount;
	    private String updated_at;
	    private String reference_id;
	    ArrayList<AsqBnplTabbyItemsObj> items ;
	    
	    //Product details
	    private String type;
	    private Number installments_count;
	    private String installment_period;
	    
	    //Products and Available Products details
	    private AsqBnplTabbyInstallmentsObj installments;

	    // Getter Methods

	    public String getId() {
	        return id;
	    }

	    public String getName() {
	        return name;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public String getPhone() {
	        return phone;
	    }

	    public String getDob() {
	        return dob;
	    }

	    // Setter Methods

	    public void setId(String id) {
	        this.id = id;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public void setPhone(String phone) {
	        this.phone = phone;
	    }

	    public void setDob(String dob) {
	        this.dob = dob;
	    }

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
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

		public String getUpdated_at() {
			return updated_at;
		}

		public void setUpdated_at(String updated_at) {
			this.updated_at = updated_at;
		}

		public String getReference_id() {
			return reference_id;
		}

		public void setReference_id(String reference_id) {
			this.reference_id = reference_id;
		}

		public ArrayList<AsqBnplTabbyItemsObj> getItems() {
			return items;
		}

		public void setItems(ArrayList<AsqBnplTabbyItemsObj> items) {
			this.items = items;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Number getInstallments_count() {
			return installments_count;
		}

		public void setInstallments_count(Number installments_count) {
			this.installments_count = installments_count;
		}

		public String getInstallment_period() {
			return installment_period;
		}

		public void setInstallment_period(String installment_period) {
			this.installment_period = installment_period;
		}

		public AsqBnplTabbyInstallmentsObj getInstallments() {
			return installments;
		}

		public void setInstallments(AsqBnplTabbyInstallmentsObj installments) {
			this.installments = installments;
		}

}
