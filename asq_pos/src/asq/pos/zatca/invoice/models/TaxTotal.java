package asq.pos.zatca.invoice.models;

import java.util.ArrayList;

public class TaxTotal {
	
	private String taxAmount;
	private String currency;
	private ArrayList<TaxSubtotal> taxSubtotal;
	
	public String getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public ArrayList<TaxSubtotal> getTaxSubtotal() {
		return taxSubtotal;
	}
	public void setTaxSubtotal(ArrayList<TaxSubtotal> taxSubtotal) {
		this.taxSubtotal = taxSubtotal;
	}
	
}
