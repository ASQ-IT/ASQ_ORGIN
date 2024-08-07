package asq.pos.zatca.invoice.models;

public class TaxSubtotal {
	
	private String taxableAmount;
	private String taxCategorySchemeAgencyID;
	private String taxCategorySchemeID;
	private String taxCategoryTaxSchemeSchemeAgencyID;
	private String taxCategoryTaxSchemeSchemeID;
	private String taxCategoryID;
	private String taxCategoryPercent;
	private String taxCategoryTaxSchemeID;
	private String currency;
	private String taxAmount;
	private String taxExemptionReasonCode;
	private String taxExemptionReason;
	
	public String getTaxExemptionReasonCode() {
		return taxExemptionReasonCode;
	}
	public void setTaxExemptionReasonCode(String taxExemptionReasonCode) {
		this.taxExemptionReasonCode = taxExemptionReasonCode;
	}
	public String getTaxCategorySchemeID() {
		return taxCategorySchemeID;
	}
	public void setTaxCategorySchemeID(String taxCategorySchemeID) {
		this.taxCategorySchemeID = taxCategorySchemeID;
	}
	public String getTaxCategoryTaxSchemeSchemeAgencyID() {
		return taxCategoryTaxSchemeSchemeAgencyID;
	}
	public void setTaxCategoryTaxSchemeSchemeAgencyID(String taxCategoryTaxSchemeSchemeAgencyID) {
		this.taxCategoryTaxSchemeSchemeAgencyID = taxCategoryTaxSchemeSchemeAgencyID;
	}
	public String getTaxCategoryTaxSchemeSchemeID() {
		return taxCategoryTaxSchemeSchemeID;
	}
	public void setTaxCategoryTaxSchemeSchemeID(String taxCategoryTaxSchemeSchemeID) {
		this.taxCategoryTaxSchemeSchemeID = taxCategoryTaxSchemeSchemeID;
	}
	public String getTaxableAmount() {
		return taxableAmount;
	}
	public void setTaxableAmount(String taxableAmount) {
		this.taxableAmount = taxableAmount;
	}
	public String getTaxCategorySchemeAgencyID() {
		return taxCategorySchemeAgencyID;
	}
	public void setTaxCategorySchemeAgencyID(String taxCategorySchemeAgencyID) {
		this.taxCategorySchemeAgencyID = taxCategorySchemeAgencyID;
	}
	public String getTaxCategoryID() {
		return taxCategoryID;
	}
	public void setTaxCategoryID(String taxCategoryID) {
		this.taxCategoryID = taxCategoryID;
	}
	public String getTaxCategoryPercent() {
		return taxCategoryPercent;
	}
	public void setTaxCategoryPercent(String taxCategoryPercent) {
		this.taxCategoryPercent = taxCategoryPercent;
	}
	public String getTaxCategoryTaxSchemeID() {
		return taxCategoryTaxSchemeID;
	}
	public void setTaxCategoryTaxSchemeID(String taxCategoryTaxSchemeID) {
		this.taxCategoryTaxSchemeID = taxCategoryTaxSchemeID;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getTaxExemptionReason() {
		return taxExemptionReason;
	}
	public void setTaxExemptionReason(String taxExemptionReason) {
		this.taxExemptionReason = taxExemptionReason;
	}
	
	
}