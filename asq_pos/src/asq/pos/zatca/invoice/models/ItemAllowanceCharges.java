package asq.pos.zatca.invoice.models;

public class ItemAllowanceCharges {

	private String itemAllowanceChargeIndicator;
	private String itemAllowanceChargeReason;
	private String itemAllowanceChargeAmount;
	private String itemMultiplierFactorNummeric;
	private String itemAllowanceChargeAmountCurrencyID;
	private String itemBaseAmount;

	public ItemAllowanceCharges() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemAllowanceCharges(String itemAllowanceChargeIndicator, String itemAllowanceChargeReason,
			String itemAllowanceChargeAmount, String itemMultiplierFactorNummeric,
			String itemAllowanceChargeAmountCurrencyID, String itemBaseAmount) {
		super();
		this.itemAllowanceChargeIndicator = itemAllowanceChargeIndicator;
		this.itemAllowanceChargeReason = itemAllowanceChargeReason;
		this.itemAllowanceChargeAmount = itemAllowanceChargeAmount;
		this.itemMultiplierFactorNummeric = itemMultiplierFactorNummeric;
		this.itemAllowanceChargeAmountCurrencyID = itemAllowanceChargeAmountCurrencyID;
		this.itemBaseAmount = itemBaseAmount;

	}

	public String isItemAllowanceChargeIndicator() {
		return itemAllowanceChargeIndicator;
	}

	public void setItemAllowanceChargeIndicator(String itemAllowanceChargeIndicator) {
		this.itemAllowanceChargeIndicator = itemAllowanceChargeIndicator;
	}

	public String getItemAllowanceChargeReason() {
		return itemAllowanceChargeReason;
	}

	public void setItemAllowanceChargeReason(String itemAllowanceChargeReason) {
		this.itemAllowanceChargeReason = itemAllowanceChargeReason;
	}

	public String getItemAllowanceChargeAmount() {
		return itemAllowanceChargeAmount;
	}

	public void setItemAllowanceChargeAmount(String itemAllowanceChargeAmount) {
		this.itemAllowanceChargeAmount = itemAllowanceChargeAmount;
	}

	public String getItemMultiplierFactorNummeric() {
		return itemMultiplierFactorNummeric;
	}

	public void setItemMultiplierFactorNummeric(String itemMultiplierFactorNummeric) {
		this.itemMultiplierFactorNummeric = itemMultiplierFactorNummeric;
	}

	public String getItemAllowanceChargeAmountCurrencyID() {
		return itemAllowanceChargeAmountCurrencyID;
	}

	public void setItemAllowanceChargeAmountCurrencyID(String itemAllowanceChargeAmountCurrencyID) {
		this.itemAllowanceChargeAmountCurrencyID = itemAllowanceChargeAmountCurrencyID;
	}

	public String getItemBaseAmount() {
		return itemBaseAmount;
	}

	public void setItemBaseAmount(String itemBaseAmount) {
		this.itemBaseAmount = itemBaseAmount;
	}

	public String getItemAllowanceChargeIndicator() {
		return itemAllowanceChargeIndicator;
	}
	
	

}
