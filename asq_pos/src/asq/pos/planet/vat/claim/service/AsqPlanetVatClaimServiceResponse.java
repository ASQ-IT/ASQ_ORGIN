package asq.pos.planet.vat.claim.service;

import java.util.ArrayList;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

import asq.pos.zatca.cert.generation.AsqZatcaErrorDesc;
import asq.retail.xstore.countrypack.common.taxfree.fintrax.op.AsqPlanetLineItemData;
import dtv.service.req.IServiceResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqPlanetVatClaimServiceResponse implements IServiceResponse{
	
	     @JsonProperty("access_token")
		 private String access_token;
	     
	     @JsonProperty("status")
		 private String status;
	     
	     @JsonProperty("message")
		 private String message;
	     
	     @JsonProperty("code")
		 private String code;
	     
	     @JsonProperty("receiptNumber")
		 private String receiptNumber;
	     
	     @JsonProperty("totalAmount")
		 private float totalAmount;
	     
	     @JsonProperty("vatAmount")
		 private float vatAmount;
	     
	     @JsonProperty("taxRefundAmount")
		 private float taxRefundAmount;
	     
	     @JsonProperty("transactionItems")
		 ArrayList< AsqPlanetLineItemData > transactionItems;
	     
	     @JsonProperty("shopperDetails")
		 AsqPlanetVatClaimShopperObj shopperDetails;
	     
	     @JsonProperty("taxRegisterResponse")
		 AsqPlanetVatClaimTaxRegRefRespObj taxRegisterResponse;
	     
	     @JsonProperty("taxRefundResponse")
		 AsqPlanetVatClaimTaxRegRefRespObj taxRefundResponse;
	     
	     @JsonProperty("tagNumber")
		 private String tagNumber;
	     

		private AsqPlanetVatClaimErrorDesc error;

		 // Getter Methods 
		 

		 public String getStatus() {
		  return status;
		 }

		 public String getAccess_token() {
			return access_token;
		}

		public String getMessage() {
		  return message;
		 }

		 public String getCode() {
		  return code;
		 }

		 public String getReceiptNumber() {
		  return receiptNumber;
		 }

		 public float getTotalAmount() {
		  return totalAmount;
		 }

		 public float getVatAmount() {
		  return vatAmount;
		 }

		 public float getTaxRefundAmount() {
		  return taxRefundAmount;
		 }

		 public AsqPlanetVatClaimShopperObj getShopperDetails() {
		  return shopperDetails;
		 }

		 public AsqPlanetVatClaimTaxRegRefRespObj getTaxRegisterResponse() {
		  return taxRegisterResponse;
		 }

		 public AsqPlanetVatClaimTaxRegRefRespObj getTaxRefundResponse() {
		  return taxRefundResponse;
		 }
		 
		 public String getTagNumber() {
		  return tagNumber;
		 }


		 // Setter Methods 
		 
		 public void setAccess_token(String access_token) {
		  this.access_token = access_token;
		 }


		 public void setStatus(String status) {
		  this.status = status;
		 }

		 public void setMessage(String message) {
		  this.message = message;
		 }

		 public void setCode(String code) {
		  this.code = code;
		 }

		 public void setReceiptNumber(String receiptNumber) {
		  this.receiptNumber = receiptNumber;
		 }

		 public void setTotalAmount(float totalAmount) {
		  this.totalAmount = totalAmount;
		 }

		 public void setVatAmount(float vatAmount) {
		  this.vatAmount = vatAmount;
		 }

		 public void setTaxRefundAmount(float taxRefundAmount) {
		  this.taxRefundAmount = taxRefundAmount;
		 }

		 public void setShopperDetails(AsqPlanetVatClaimShopperObj shopperDetails) {
		  this.shopperDetails = shopperDetails;
		 }

		 public void setTaxRegisterResponse(AsqPlanetVatClaimTaxRegRefRespObj taxRegisterResponse) {
		  this.taxRegisterResponse = taxRegisterResponse;
		 }

		 public void setTaxRefundResponse(AsqPlanetVatClaimTaxRegRefRespObj taxRefundResponse) {
		  this.taxRefundResponse = taxRefundResponse;
		 }

		public ArrayList<AsqPlanetLineItemData> getTransactionItems() {
			return transactionItems;
		}

		public void setTransactionItems(ArrayList<AsqPlanetLineItemData> transactionItems) {
			this.transactionItems = transactionItems;
		}

		public void setTagNumber(String tagNumber) {
			this.tagNumber = tagNumber;
		}
		
		 public AsqPlanetVatClaimErrorDesc getError() {
			  return error;
			 }

			 // Setter Methods 
			 

			public void setError(AsqPlanetVatClaimErrorDesc error) {
				this.error = error;
			}

		 
	}
