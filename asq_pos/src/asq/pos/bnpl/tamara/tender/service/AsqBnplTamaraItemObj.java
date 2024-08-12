package asq.pos.bnpl.tamara.tender.service;

public class AsqBnplTamaraItemObj {

    private Long reference_id;
    private String type;
    private String itemDescription;
	private String sku;
    private int quantity;
    private AsqBnplTamaraAmountObj total_amount;

    public Long getReference_id() {
        return reference_id;
    }

    public void setReference_id(Long reference_id) {
        this.reference_id = reference_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
   
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public AsqBnplTamaraAmountObj getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(AsqBnplTamaraAmountObj total_amount) {
        this.total_amount = total_amount;
    }

}
