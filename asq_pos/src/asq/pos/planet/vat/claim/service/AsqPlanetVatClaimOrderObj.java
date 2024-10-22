package asq.pos.planet.vat.claim.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;

import asq.retail.xstore.countrypack.common.taxfree.fintrax.op.AsqPlanetLineItemData;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsqPlanetVatClaimOrderObj {
	
		 private BigDecimal totalBeforeVAT;
		 private BigDecimal discountAmount;
		 private BigDecimal vatIncl;
		 private BigDecimal total;
		 ArrayList<AsqPlanetLineItemData> items;


		 // Getter Methods 

		 public BigDecimal getTotalBeforeVAT() {
		  return totalBeforeVAT;
		 }

		 public BigDecimal getDiscountAmount() {
		  return discountAmount;
		 }

		 public BigDecimal getVatIncl() {
		  return vatIncl;
		 }

		 public BigDecimal getTotal() {
		  return total;
		 }

		 public ArrayList<AsqPlanetLineItemData> getItems() {
		  return items;
		 }

		 // Setter Methods 

		 public void setTotalBeforeVAT(BigDecimal totalBeforeVAT) {
		  this.totalBeforeVAT = totalBeforeVAT;
		 }

		 public void setDiscountAmount(BigDecimal discountAmount) {
		  this.discountAmount = discountAmount;
		 }

		 public void setVatIncl(BigDecimal vatIncl) {
		  this.vatIncl = vatIncl;
		 }

		 public void setTotal(BigDecimal total) {
		  this.total = total;
		 }

		public void setItems(ArrayList<AsqPlanetLineItemData> asqPlanetLineItemData) {
			this.items = asqPlanetLineItemData;
		}

}
