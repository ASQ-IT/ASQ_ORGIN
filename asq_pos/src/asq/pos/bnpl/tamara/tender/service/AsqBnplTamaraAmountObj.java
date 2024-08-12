package asq.pos.bnpl.tamara.tender.service;

import java.math.BigDecimal;
import java.util.Currency;

public class AsqBnplTamaraAmountObj {

    private BigDecimal amount;
	private Currency currency;
    
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
