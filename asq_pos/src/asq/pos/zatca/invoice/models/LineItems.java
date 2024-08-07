package asq.pos.zatca.invoice.models;

import java.math.BigDecimal;
import java.util.ArrayList;

public class LineItems {
	
//	private String invoiceLineID;
//	private String invoicedQuantityUnitCode;
//	private BigDecimal invoiceLineInvoicedQuantity;
//	private String invoiceLineExtensionAmountCurrencyID;
//	private BigDecimal invoiceLineLineExtensionAmount;
//	private BigDecimal invoiceLineTaxAmount;
//	private String invoiceLineTaxAmountCurrencyID;
//	private BigDecimal invoiceLineRoundingAmount;
//	private String invoiceLineRoundingAmountCurrencyID;
//	private String itemName;
//	private String itemClassifiedTaxCategoryID;
//	private BigDecimal itemClassifiedTaxCategoryPercent;
//	private String itemClassifiedTaxCategoryTaxSchemeID;
//	private String itemPriceAmountCurrencyID;
//	private BigDecimal itemPriceAmount;
//	private ArrayList<ItemAllowanceCharge> itemAllowanceChargesList;

	private String invoiceLineID;
	private String invoicedQuantityUnitCode;
	private String invoiceLineInvoicedQuantity;
	private String invoiceLineExtensionAmountCurrencyID;
	private String invoiceLineLineExtensionAmount;
	private String invoiceLineTaxAmount;
	private String invoiceLineTaxAmountCurrencyID;
	private String invoiceLineRoundingAmount;
	private String invoiceLineRoundingAmountCurrencyID;
	private String itemName;
	private String itemClassifiedTaxCategoryID;
	private String itemClassifiedTaxCategoryPercent;
	private String itemClassifiedTaxCategoryTaxSchemeID;
	private String itemPriceAmountCurrencyID;
	private String itemPriceAmount;
	private ArrayList<ItemAllowanceCharges> itemAllowanceCharges;
	private ArrayList<ItemAllowanceCharges> itemLineAllowanceCharges;
	private String productID;
	private String engDescription;
	private String[] notes;
	
	
	
	public String[] getNotes() {
		return notes;
	}

	public void setNotes(String[] notes) {
		this.notes = notes;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getEngDescription() {
		return engDescription;
	}

	public void setEngDescription(String engDescription) {
		this.engDescription = engDescription;
	}

	public LineItems() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getInvoiceLineID() {
		return invoiceLineID;
	}

	public void setInvoiceLineID(String invoiceLineID) {
		this.invoiceLineID = invoiceLineID;
	}

	public String getInvoicedQuantityUnitCode() {
		return invoicedQuantityUnitCode;
	}

	public void setInvoicedQuantityUnitCode(String invoicedQuantityUnitCode) {
		this.invoicedQuantityUnitCode = invoicedQuantityUnitCode;
	}

	public String getInvoiceLineInvoicedQuantity() {
		return invoiceLineInvoicedQuantity;
	}

	public void setInvoiceLineInvoicedQuantity(String invoiceLineInvoicedQuantity) {
		this.invoiceLineInvoicedQuantity = invoiceLineInvoicedQuantity;
	}

	public String getInvoiceLineExtensionAmountCurrencyID() {
		return invoiceLineExtensionAmountCurrencyID;
	}

	public void setInvoiceLineExtensionAmountCurrencyID(String invoiceLineExtensionAmountCurrencyID) {
		this.invoiceLineExtensionAmountCurrencyID = invoiceLineExtensionAmountCurrencyID;
	}

	public String getInvoiceLineLineExtensionAmount() {
		return invoiceLineLineExtensionAmount;
	}

	public void setInvoiceLineLineExtensionAmount(String invoiceLineLineExtensionAmount) {
		this.invoiceLineLineExtensionAmount = invoiceLineLineExtensionAmount;
	}

	public String getInvoiceLineTaxAmount() {
		return invoiceLineTaxAmount;
	}

	public void setInvoiceLineTaxAmount(String invoiceLineTaxAmount) {
		this.invoiceLineTaxAmount = invoiceLineTaxAmount;
	}

	public String getInvoiceLineTaxAmountCurrencyID() {
		return invoiceLineTaxAmountCurrencyID;
	}

	public void setInvoiceLineTaxAmountCurrencyID(String invoiceLineTaxAmountCurrencyID) {
		this.invoiceLineTaxAmountCurrencyID = invoiceLineTaxAmountCurrencyID;
	}

	public String getInvoiceLineRoundingAmount() {
		return invoiceLineRoundingAmount;
	}

	public void setInvoiceLineRoundingAmount(String invoiceLineRoundingAmount) {
		this.invoiceLineRoundingAmount = invoiceLineRoundingAmount;
	}

	public String getInvoiceLineRoundingAmountCurrencyID() {
		return invoiceLineRoundingAmountCurrencyID;
	}

	public void setInvoiceLineRoundingAmountCurrencyID(String invoiceLineRoundingAmountCurrencyID) {
		this.invoiceLineRoundingAmountCurrencyID = invoiceLineRoundingAmountCurrencyID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemClassifiedTaxCategoryID() {
		return itemClassifiedTaxCategoryID;
	}

	public void setItemClassifiedTaxCategoryID(String itemClassifiedTaxCategoryID) {
		this.itemClassifiedTaxCategoryID = itemClassifiedTaxCategoryID;
	}

	public String getItemClassifiedTaxCategoryPercent() {
		return itemClassifiedTaxCategoryPercent;
	}

	public void setItemClassifiedTaxCategoryPercent(String itemClassifiedTaxCategoryPercent) {
		this.itemClassifiedTaxCategoryPercent = itemClassifiedTaxCategoryPercent;
	}

	public String getItemClassifiedTaxCategoryTaxSchemeID() {
		return itemClassifiedTaxCategoryTaxSchemeID;
	}

	public void setItemClassifiedTaxCategoryTaxSchemeID(String itemClassifiedTaxCategoryTaxSchemeID) {
		this.itemClassifiedTaxCategoryTaxSchemeID = itemClassifiedTaxCategoryTaxSchemeID;
	}

	public String getItemPriceAmountCurrencyID() {
		return itemPriceAmountCurrencyID;
	}

	public void setItemPriceAmountCurrencyID(String itemPriceAmountCurrencyID) {
		this.itemPriceAmountCurrencyID = itemPriceAmountCurrencyID;
	}

	public String getItemPriceAmount() {
		return itemPriceAmount;
	}

	public void setItemPriceAmount(String itemPriceAmount) {
		this.itemPriceAmount = itemPriceAmount;
	}

	public ArrayList<ItemAllowanceCharges> getItemAllowanceCharges() {
		return itemAllowanceCharges;
	}

	public void setItemAllowanceChargesList(ArrayList<ItemAllowanceCharges> itemAllowanceCharges) {
		this.itemAllowanceCharges = itemAllowanceCharges;
	}

	public ArrayList<ItemAllowanceCharges> getItemLineAllowanceCharges() {
		return itemLineAllowanceCharges;
	}

	public void setItemLineAllowanceCharges(ArrayList<ItemAllowanceCharges> itemLineAllowanceCharges) {
		this.itemLineAllowanceCharges = itemLineAllowanceCharges;
	}

	public void setItemAllowanceCharges(ArrayList<ItemAllowanceCharges> itemAllowanceCharges) {
		this.itemAllowanceCharges = itemAllowanceCharges;
	}
	
	
	
	
	
}
