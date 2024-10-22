package asq.pos.planet.vat.claim.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsqPlanetVatClaimShopperIdDocObj {

		 private String number;
		 private String expirationDate;
		 private String issuedBy;
		 private String type;
		 private String date;


		 // Getter Methods 

		 public String getNumber() {
		  return number;
		 }

		 public String getExpirationDate() {
		  return expirationDate;
		 }

		 public String getIssuedBy() {
		  return issuedBy;
		 }

		 public String getType() {
		  return type;
		 }

		 // Setter Methods 

		 public void setNumber(String number) {
		  this.number = number;
		 }

		 public void setExpirationDate(String expirationDate) {
		  this.expirationDate = expirationDate;
		 }

		 public void setIssuedBy(String issuedBy) {
		  this.issuedBy = issuedBy;
		 }

		 public void setType(String type) {
		  this.type = type;
		 }


		 // Getter Methods 

		 public String getDate() {
		  return date;
		 }

		 // Setter Methods 

		 public void setDate(String date) {
		  this.date = date;
		 }

}
