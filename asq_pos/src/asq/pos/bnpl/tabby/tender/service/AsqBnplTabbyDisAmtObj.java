package asq.pos.bnpl.tabby.tender.service;

/**
 * @author RA20221457
 *
 */

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqBnplTabbyDisAmtObj {

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
