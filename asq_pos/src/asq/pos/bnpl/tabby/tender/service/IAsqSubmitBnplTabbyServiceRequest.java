package asq.pos.bnpl.tabby.tender.service;

import dtv.service.req.IServiceRequest;

public interface IAsqSubmitBnplTabbyServiceRequest extends IServiceRequest{
	
	   public AsqBnplTabbyPaymentObj getPayment();

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

}
