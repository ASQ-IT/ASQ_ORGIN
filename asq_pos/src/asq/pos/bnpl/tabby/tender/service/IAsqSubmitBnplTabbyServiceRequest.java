package asq.pos.bnpl.tabby.tender.service;

import java.util.ArrayList;
import java.util.Locale;

import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraAddDataObj;
import dtv.service.req.IServiceRequest;

public interface IAsqSubmitBnplTabbyServiceRequest extends IServiceRequest {

	public AsqBnplTabbyAmountObj getTotal_amount();

	public void setTotal_amount(AsqBnplTabbyAmountObj total_amount);

	public ArrayList<AsqBnplTabbyItemsObj> getItems();

	public void setItems(ArrayList<AsqBnplTabbyItemsObj> items);

	public String getPhone_number();

	public void setPhone_number(String phone_number);

	public AsqBnplTabbyPaymentObj getPayment();

	public String getOrder_reference_id();

	public void setOrder_reference_id(String order_reference_id);

	public String getMerchant_code();

	public void setPayment(AsqBnplTabbyPaymentObj payment);

	public void setMerchant_code(String merchant_code);

	public String getLang();

	public void setLang(String lang);

	public String getAmount();

	public void setAmount(String amount);

	public String getReference_id();

	public void setReference_id(String reference_id);

	public String getReason();

	public void setReason(String reason);

	public String getCreated_at();

	public void setCreated_at(String created_at);

	public String getId();

	public void setId(String id);
	
	public void setAdditional_data(AsqBnplTabbyAddDataObj storeData);
	
	public void setLocale(Locale loc);

}
