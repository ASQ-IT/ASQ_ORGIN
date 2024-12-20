/**
 * 
 */
package asq.retail.xstore.countrypack.common.taxfree.fintrax.op;

import java.math.BigDecimal;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

import oracle.retail.xstore.countrypack.common.taxfree.fintrax.auth.json.PlanetLineItemData;

/**
 * @author SA20547171
 *
 */
public class AsqPlanetLineItemData extends PlanetLineItemData {

	private String code;
	private String merchandiseGroup;
	private Number netAmount;
	private Number unitPrice;
	private boolean taxRefundEligible;
	private Number vatAmount;
	private String vatCode;
 	 private Number discountAmount;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMerchandiseGroup() {
		return merchandiseGroup;
	}

	public void setMerchandiseGroup(String merchandiseGroup) {
		this.merchandiseGroup = merchandiseGroup;
	}

	public Number getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(Number netAmount) {
		this.netAmount = netAmount;
	}

	public Number getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Number unitPrice) {
		this.unitPrice = unitPrice;
	}

	public boolean isTaxRefundEligible() {
		return taxRefundEligible;
	}

	public void setTaxRefundEligible(boolean taxRefundEligible) {
		this.taxRefundEligible = taxRefundEligible;
	}

	public Number getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(Number vatAmount) {
		this.vatAmount = vatAmount;
	}

	public String getVatCode() {
		return vatCode;
	}

	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}
	
  

		 
	  
	     public Number getDiscountAmount() {
			return discountAmount;
		}

		public void setDiscountAmount(Number discountAmount) {
			this.discountAmount = discountAmount;
		}



}
