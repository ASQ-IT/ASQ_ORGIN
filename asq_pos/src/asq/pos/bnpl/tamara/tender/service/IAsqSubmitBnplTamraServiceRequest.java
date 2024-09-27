package asq.pos.bnpl.tamara.tender.service;

import java.util.ArrayList;

import dtv.service.req.IServiceRequest;

public interface IAsqSubmitBnplTamraServiceRequest extends IServiceRequest {
	
	    public AsqBnplTamaraAmountObj getTotal_amount();

	    public void setTotal_amount(AsqBnplTamaraAmountObj total_amount);

	    public String getPhone_number();

	    public void setPhone_number(String phone_number);

	    public ArrayList<AsqBnplTamaraItemObj> getItems();

	    public void setItems(ArrayList<AsqBnplTamaraItemObj> items);

	    public String getOrder_reference_id();

	    public void setOrder_reference_id(String order_reference_id);
	    
		public String getOrder_id();

		public void setOrder_id(String order_id);
		
		public String getComment();

		public void setComment(String comment);
		
		public String getStore_code();

		public void setStore_code(String store_code);
		
		public String getCheckout_id();

		public void setCheckout_id(String checkout_id);
}
