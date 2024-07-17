package asq.pos.zatca.invoice.models;

import java.util.ArrayList;

public class AdditionalTags {
	private String pdfTransactionType;
	private String pdfTransactionSubType;
	private String pdfOrderNo;
	private String pdfReservationNo ;
	private String pdfShippingPartyIdentificationID; 
	private String pdfShippingPartyIdentificationschemeID; 
	private String pdfShippingName; 
	private String pdfShippingAddressStreet; 
	private String pdfShippingAddressAdditionalStreet; 
	private String pdfShippingBuildingNumber;
	private String pdfShippingPlotIdentification; 
	private String pdfShippingCitySubdivisionName; 
	private String pdfShippingCity; 
	private String pdfShippingPostalCode; 
	private String pdfShippingState;
	private String pdfShippingDistrict; 
	private String pdfShippingCountryCode;
	private ArrayList<asq.pos.zatca.invoice.generation.utils.LineItems> ITEM_LINE;
	private String previousInvoiceHash;
	
	private String InvoiceTotal;
	private String TaxTotal;
	private String roundingAmount;
	
	
	
	
	
	public String getInvoiceTotal() {
		return InvoiceTotal;
	}
	public void setInvoiceTotal(String invoiceTotal) {
		InvoiceTotal = invoiceTotal;
	}
	public String getTaxTotal() {
		return TaxTotal;
	}
	public void setTaxTotal(String taxTotal) {
		TaxTotal = taxTotal;
	}
	public String getRoundingAmount() {
		return roundingAmount;
	}
	public void setRoundingAmount(String roundingAmount) {
		this.roundingAmount = roundingAmount;
	}
	public String getPreviousInvoiceHash() {
		return previousInvoiceHash;
	}
	public void setPreviousInvoiceHash(String previousInvoiceHash) {
		this.previousInvoiceHash = previousInvoiceHash;
	}
	public ArrayList<asq.pos.zatca.invoice.generation.utils.LineItems> getITEM_LINE() {
		return ITEM_LINE;
	}
	public void setITEM_LINE(ArrayList<asq.pos.zatca.invoice.generation.utils.LineItems> iTEM_LINE) {
		ITEM_LINE = iTEM_LINE;
	}
	public String getPdfTransactionType() {
		return pdfTransactionType;
	}
	public void setPdfTransactionType(String pdfTransactionType) {
		this.pdfTransactionType = pdfTransactionType;
	}
	public String getPdfTransactionSubType() {
		return pdfTransactionSubType;
	}
	public void setPdfTransactionSubType(String pdfTransactionSubType) {
		this.pdfTransactionSubType = pdfTransactionSubType;
	}
	public String getPdfOrderNo() {
		return pdfOrderNo;
	}
	public void setPdfOrderNo(String pdfOrderNo) {
		this.pdfOrderNo = pdfOrderNo;
	}
	public String getPdfReservationNo() {
		return pdfReservationNo;
	}
	public void setPdfReservationNo(String pdfReservationNo) {
		this.pdfReservationNo = pdfReservationNo;
	}
	public String getPdfShippingPartyIdentificationID() {
		return pdfShippingPartyIdentificationID;
	}
	public void setPdfShippingPartyIdentificationID(String pdfShippingPartyIdentificationID) {
		this.pdfShippingPartyIdentificationID = pdfShippingPartyIdentificationID;
	}
	public String getPdfShippingPartyIdentificationschemeID() {
		return pdfShippingPartyIdentificationschemeID;
	}
	public void setPdfShippingPartyIdentificationschemeID(String pdfShippingPartyIdentificationschemeID) {
		this.pdfShippingPartyIdentificationschemeID = pdfShippingPartyIdentificationschemeID;
	}
	public String getPdfShippingName() {
		return pdfShippingName;
	}
	public void setPdfShippingName(String pdfShippingName) {
		this.pdfShippingName = pdfShippingName;
	}
	public String getPdfShippingAddressStreet() {
		return pdfShippingAddressStreet;
	}
	public void setPdfShippingAddressStreet(String pdfShippingAddressStreet) {
		this.pdfShippingAddressStreet = pdfShippingAddressStreet;
	}
	public String getPdfShippingAddressAdditionalStreet() {
		return pdfShippingAddressAdditionalStreet;
	}
	public void setPdfShippingAddressAdditionalStreet(String pdfShippingAddressAdditionalStreet) {
		this.pdfShippingAddressAdditionalStreet = pdfShippingAddressAdditionalStreet;
	}
	public String getPdfShippingBuildingNumber() {
		return pdfShippingBuildingNumber;
	}
	public void setPdfShippingBuildingNumber(String pdfShippingBuildingNumber) {
		this.pdfShippingBuildingNumber = pdfShippingBuildingNumber;
	}
	public String getPdfShippingPlotIdentification() {
		return pdfShippingPlotIdentification;
	}
	public void setPdfShippingPlotIdentification(String pdfShippingPlotIdentification) {
		this.pdfShippingPlotIdentification = pdfShippingPlotIdentification;
	}
	public String getPdfShippingCitySubdivisionName() {
		return pdfShippingCitySubdivisionName;
	}
	public void setPdfShippingCitySubdivisionName(String pdfShippingCitySubdivisionName) {
		this.pdfShippingCitySubdivisionName = pdfShippingCitySubdivisionName;
	}
	public String getPdfShippingCity() {
		return pdfShippingCity;
	}
	public void setPdfShippingCity(String pdfShippingCity) {
		this.pdfShippingCity = pdfShippingCity;
	}
	public String getPdfShippingPostalCode() {
		return pdfShippingPostalCode;
	}
	public void setPdfShippingPostalCode(String pdfShippingPostalCode) {
		this.pdfShippingPostalCode = pdfShippingPostalCode;
	}
	public String getPdfShippingState() {
		return pdfShippingState;
	}
	public void setPdfShippingState(String pdfShippingState) {
		this.pdfShippingState = pdfShippingState;
	}
	public String getPdfShippingDistrict() {
		return pdfShippingDistrict;
	}
	public void setPdfShippingDistrict(String pdfShippingDistrict) {
		this.pdfShippingDistrict = pdfShippingDistrict;
	}
	public String getPdfShippingCountryCode() {
		return pdfShippingCountryCode;
	}
	public void setPdfShippingCountryCode(String pdfShippingCountryCode) {
		this.pdfShippingCountryCode = pdfShippingCountryCode;
	} 
	
	
	
	
}
