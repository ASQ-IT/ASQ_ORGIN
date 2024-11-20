package asq.pos.loyalty.mokafaa.tender.service;

import dtv.service.req.IServiceRequest;

public interface IAsqMokafaaLoyaltyServiceRequest extends IServiceRequest {

	public String getMobile();
	public void setMobile(String mobile);
	public String getCurrency();
	public void setCurrency(String currency);
	public String getLang();
	public void setLang(String lang);
	public String getLoyaltyCICNo();
	public void setLoyaltyCICNo(String loyaltyCICNo);
	public String getOTPValue();
	public void setOTPValue(String oTPValue);
	public String getOTPToken();
	public void setOTPToken(String oTPToken);
	public String getAmount();
	public void setAmount(String amount);
	public String getLanguage();
	public void setLanguage(String language);
	public Long getTransactionID();
	public void setTransactionID(Long transactionID);
	public String getAuthToken();
	public void setAuthToken(String authToken);
	
	public String getServiceRequest();
	public void setServiceRequest(String serviceRequest);
	
}
