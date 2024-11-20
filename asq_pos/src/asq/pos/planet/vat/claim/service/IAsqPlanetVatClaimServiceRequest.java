package asq.pos.planet.vat.claim.service;

import dtv.service.req.IServiceRequest;

public interface IAsqPlanetVatClaimServiceRequest extends IServiceRequest{
	
	 public boolean getIssueTaxRefundTag() ;

		 public String getDate() ;

		 public String getReceiptNumber() ;

		 public String getMerchantId() ;

		 public String getTerminal() ;

		 public String getType();
		 
		 public String getToken();

		 public AsqPlanetVatClaimOrderObj getOrder();

		 public AsqPlanetVatClaimShopperObj getShopper();

		 public void setIssueTaxRefundTag(boolean issueTaxRefundTag);

		 public void setDate(String date);

		 public void setReceiptNumber(String receiptNumber);

		 public void setMerchantId(String merchantId);

		 public void setTerminal(String terminal);

		 public void setType(String type);

		 public void setOrder(AsqPlanetVatClaimOrderObj order);

		 public void setShopper(AsqPlanetVatClaimShopperObj shopper);
		 
		 public void setToken(String token);
		 
		 public void setTagNumber(String tagNumber);

		 public String getTagNumber();

		public void setNote(String retailLocationId);
		
		public String getNote();
}
