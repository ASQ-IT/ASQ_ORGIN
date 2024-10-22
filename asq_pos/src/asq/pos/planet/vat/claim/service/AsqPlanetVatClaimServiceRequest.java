package asq.pos.planet.vat.claim.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;

import dtv.servicex.impl.req.ServiceRequest;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsqPlanetVatClaimServiceRequest extends ServiceRequest implements IAsqPlanetVatClaimServiceRequest{
	
	private boolean issueTaxRefundTag;
	 private String date;
	 private String receiptNumber;
	 private String merchantId;
	 private String terminal;
	 private String type;
	 AsqPlanetVatClaimOrderObj order;
	 AsqPlanetVatClaimShopperObj shopper;
	 private String token;


	 // Getter Methods 
	 
	 

	 public boolean getIssueTaxRefundTag() {
	  return issueTaxRefundTag;
	 }

	 public String getToken() {
		return token;
	}

	public String getDate() {
	  return date;
	 }

	 public String getReceiptNumber() {
	  return receiptNumber;
	 }

	 public String getMerchantId() {
	  return merchantId;
	 }

	 public String getTerminal() {
	  return terminal;
	 }

	 public String getType() {
	  return type;
	 }

	 public AsqPlanetVatClaimOrderObj getOrder() {
	  return order;
	 }

	 public AsqPlanetVatClaimShopperObj getShopper() {
	  return shopper;
	 }

	 // Setter Methods 

	 public void setIssueTaxRefundTag(boolean issueTaxRefundTag) {
	  this.issueTaxRefundTag = issueTaxRefundTag;
	 }

	 public void setDate(String date) {
	  this.date = date;
	 }

	 public void setReceiptNumber(String receiptNumber) {
	  this.receiptNumber = receiptNumber;
	 }

	 public void setMerchantId(String merchantId) {
	  this.merchantId = merchantId;
	 }

	 public void setTerminal(String terminal) {
	  this.terminal = terminal;
	 }

	 public void setType(String type) {
	  this.type = type;
	 }

	 public void setOrder(AsqPlanetVatClaimOrderObj order) {
	  this.order = order;
	 }

	 public void setShopper(AsqPlanetVatClaimShopperObj shopper) {
	  this.shopper = shopper;
	 }
	 
		public void setToken(String token) {
			this.token = token;
		}
}
