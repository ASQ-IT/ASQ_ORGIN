package asq.pos.bnpl.tamara.tender.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author RA20221457
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqBnplTamaraDisAmtObj {

	private String currency;

	private Number amount;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Number getAmount() {
		return amount;
	}

	public void setAmount(Number amount) {
		this.amount = amount;
	}

}
