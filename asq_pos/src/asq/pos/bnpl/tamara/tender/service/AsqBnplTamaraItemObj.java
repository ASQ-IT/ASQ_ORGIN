package asq.pos.bnpl.tamara.tender.service;

import java.math.BigDecimal;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown =true)
public class AsqBnplTamaraItemObj {

    private int reference_id;
    private String type;
    private String name;
    private String sku;
    private BigDecimal quantity;
   // private BigDecimal amount;

	private AsqBnplTamaraAmountObj total_amount;
	private AsqBnplTamaraDisAmtObj discount_amount;

    public int getReference_id() {
        return reference_id;
    }

    public void setReference_id(int reference_id) {
        this.reference_id = reference_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

	
	/*
	 * public BigDecimal getAmount() { return amount; }
	 * 
	 * public void setAmount(BigDecimal amount) { this.amount = amount; }
	 */
	 

    public AsqBnplTamaraAmountObj getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(AsqBnplTamaraAmountObj total_amount) {
        this.total_amount = total_amount;
    }

	public AsqBnplTamaraDisAmtObj getDiscount_amount() {
		return discount_amount;
	}

	public void setDiscount_amount(AsqBnplTamaraDisAmtObj discount_amount) {
		this.discount_amount = discount_amount;
	}
}
