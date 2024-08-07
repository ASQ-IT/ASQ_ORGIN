package asq.pos.zatca.invoice.models;

import java.util.ArrayList;
import java.util.List;

public class InvoiceData {

//	private String invoiceTypeDesc;
	private String[] notes;
	private String invoiceTypeCode;
	private String invoiceTypeCodeName;
	private String documentCurrencyCode;
	private String taxCurrencyCode;
	private String storeId;
	private String registerId;
	private String specialBillAgreementFlag;
	private String specialTransactionTypeFlag;
//	private String typeOfNote;
	private String profileID;
	private String irn;
	private String invoiceIssueDate;
	private String invoiceIssueTime;
	private String noteIssueDate;
//	private String noteIssueTime;
	private String orgSupplyDate;
	private String supplyDate;
	private String supplierPartyIdentificationID;
	private String supplierPartyIdentificationschemeID;
	private String sellerName;
	private String sellerAddressStreet;
	private String sellerAddressAdditinalStreet;
	private String sellerBuildingNumber;
	private String sellerAdditionalBuildNumber;
	private String sellerPlotIdentification;
	private String sellerCity;
	private String sellerCitySubdivisionName;
	private String sellerPostalCode;
	private String selletCountrySubentity;
	private String sellerState;
	private String sellerDistrict;
	private String sellerCountryCode;
	private String sellerVATRegNumber;
	private String sellerAdditionalID;
	private String paymentMeansCode;
//	private String allowanceChargeID;
	private String allowanceChargeIndicator;
	private String allowanceChargeReason;
	private String allowanceChargeAmountCurrencyID;
	private String allowanceChargeAmount;
	private String allowanceChargeTaxCategorySchemeAgencyID;
	private String allowanceChargeTaxCategorySchemeID;
	private String allowanceChargeTaxCategoryPercent;
	private String allowanceChargeTaxCategoryID;
	private String allowanceChargeTaxSchemeSchemeAgencyID;
	private String allowanceChargeTaxSchemeSchemeID;
	private String allowanceChargeTaxCategoryTaxScheme;
	private String lineExtensionAmount;
	private String taxExclusiveAmount;
	private String taxInclusiveAmount;
	private String allowanceTotalAmount;
	private String prepaidAmount;
	private String payableAmount;
	private String payableRoundingAmount;
	private String orderPurchaseOrder;
	private String orderContactNumber;
//	private String invoiceSubtotalIncVAT;
//	private String invoiceDiscountPercentage;
//	private String invoiceDiscountAmount;
//	private String invoiceTaxableAmount;
//	private String invoiceVATTotal;
//	private String invoiceGrossTotalIncVAT;
//	private String paymentMethod;
	private String subPaymentMethod;
//	private String amount;
//	private String supplierBankDetail;
//	private String notes;
	private String specialTaxTreatement;
	private String originalInvoiceNumbers;
	private String creditDebitReason;
	private String currency;

	private ArrayList<AdditionalDocumentReference> additionalDocumentReference;
	private ArrayList<LineItems> lineItems;
	private ArrayList<TaxTotal> taxTotal;

	
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
	
	
	//customerShippingAddress
	private String buyerPartyIdentificationID;
	private String buyerPartyIdentificationschemeID;
	private String buyerName; 
	private String buyerAddressStreet;
	private String buyerAddressAdditionalStreet;
	private String buyerBuildingNumber;
	private String buyerPlotIdentification;
	private String buyerCitySubdivisionName;
	private String buyerCity;
	private String buyerPostalCode;
	private String buyerState;
	private String buyerDistrict;
	private String buyerCountryCode;
	private String buyerTaxScheme;
	private String buyerPhoneNumber;
	
	private String businessDate;
	
	

	public String getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	public String getBuyerPhoneNumber() {
		return buyerPhoneNumber;
	}

	public void setBuyerPhoneNumber(String buyerPhoneNumber) {
		this.buyerPhoneNumber = buyerPhoneNumber;
	}

	public String getBuyerPartyIdentificationID() {
		return buyerPartyIdentificationID;
	}

	public void setBuyerPartyIdentificationID(String buyerPartyIdentificationID) {
		this.buyerPartyIdentificationID = buyerPartyIdentificationID;
	}

	public String getBuyerPartyIdentificationschemeID() {
		return buyerPartyIdentificationschemeID;
	}

	public void setBuyerPartyIdentificationschemeID(String buyerPartyIdentificationschemeID) {
		this.buyerPartyIdentificationschemeID = buyerPartyIdentificationschemeID;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerAddressStreet() {
		return buyerAddressStreet;
	}

	public void setBuyerAddressStreet(String buyerAddressStreet) {
		this.buyerAddressStreet = buyerAddressStreet;
	}

	public String getBuyerAddressAdditionalStreet() {
		return buyerAddressAdditionalStreet;
	}

	public void setBuyerAddressAdditionalStreet(String buyerAddressAdditionalStreet) {
		this.buyerAddressAdditionalStreet = buyerAddressAdditionalStreet;
	}

	public String getBuyerBuildingNumber() {
		return buyerBuildingNumber;
	}

	public void setBuyerBuildingNumber(String buyerBuildingNumber) {
		this.buyerBuildingNumber = buyerBuildingNumber;
	}

	public String getBuyerPlotIdentification() {
		return buyerPlotIdentification;
	}

	public void setBuyerPlotIdentification(String buyerPlotIdentification) {
		this.buyerPlotIdentification = buyerPlotIdentification;
	}

	public String getBuyerCitySubdivisionName() {
		return buyerCitySubdivisionName;
	}

	public void setBuyerCitySubdivisionName(String buyerCitySubdivisionName) {
		this.buyerCitySubdivisionName = buyerCitySubdivisionName;
	}

	public String getBuyerCity() {
		return buyerCity;
	}

	public void setBuyerCity(String buyerCity) {
		this.buyerCity = buyerCity;
	}

	public String getBuyerPostalCode() {
		return buyerPostalCode;
	}

	public void setBuyerPostalCode(String buyerPostalCode) {
		this.buyerPostalCode = buyerPostalCode;
	}

	public String getBuyerState() {
		return buyerState;
	}

	public void setBuyerState(String buyerState) {
		this.buyerState = buyerState;
	}

	public String getBuyerDistrict() {
		return buyerDistrict;
	}

	public void setBuyerDistrict(String buyerDistrict) {
		this.buyerDistrict = buyerDistrict;
	}

	public String getBuyerCountryCode() {
		return buyerCountryCode;
	}

	public void setBuyerCountryCode(String buyerCountryCode) {
		this.buyerCountryCode = buyerCountryCode;
	}

	public String getBuyerTaxScheme() {
		return buyerTaxScheme;
	}

	public void setBuyerTaxScheme(String buyerTaxScheme) {
		this.buyerTaxScheme = buyerTaxScheme;
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

	//	public String getInvoiceTypeDesc() {
//		return invoiceTypeDesc;
//	}
//	public void setInvoiceTypeDesc(String invoiceTypeDesc) {
//		this.invoiceTypeDesc = invoiceTypeDesc;
//	}
	public String getInvoiceTypeCode() {
		return invoiceTypeCode;
	}

	public void setInvoiceTypeCode(String invoiceTypeCode) {
		this.invoiceTypeCode = invoiceTypeCode;
	}

	public String getInvoiceTypeCodeName() {
		return invoiceTypeCodeName;
	}

	public void setInvoiceTypeCodeName(String invoiceTypeCodeName) {
		this.invoiceTypeCodeName = invoiceTypeCodeName;
	}

	public String getDocumentCurrencyCode() {
		return documentCurrencyCode;
	}

	public void setDocumentCurrencyCode(String documentCurrencyCode) {
		this.documentCurrencyCode = documentCurrencyCode;
	}

	public String getTaxCurrencyCode() {
		return taxCurrencyCode;
	}

	public void setTaxCurrencyCode(String taxCurrencyCode) {
		this.taxCurrencyCode = taxCurrencyCode;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getSpecialBillAgreementFlag() {
		return specialBillAgreementFlag;
	}

	public void setSpecialBillAgreementFlag(String specialBillAgreementFlag) {
		this.specialBillAgreementFlag = specialBillAgreementFlag;
	}

	public String getSpecialTransactionTypeFlag() {
		return specialTransactionTypeFlag;
	}

	public void setSpecialTransactionTypeFlag(String specialTransactionTypeFlag) {
		this.specialTransactionTypeFlag = specialTransactionTypeFlag;
	}

//	public String getTypeOfNote() {
//		return typeOfNote;
//	}
//	public void setTypeOfNote(String typeOfNote) {
//		this.typeOfNote = typeOfNote;
//	}
	public String getProfileID() {
		return profileID;
	}

	public void setProfileID(String profileID) {
		this.profileID = profileID;
	}

	public String getIrn() {
		return irn;
	}

	public void setIrn(String iRN) {
		irn = iRN;
	}

	public String getInvoiceIssueDate() {
		return invoiceIssueDate;
	}

	public void setInvoiceIssueDate(String invoiceIssueDate) {
		this.invoiceIssueDate = invoiceIssueDate;
	}

	public String getInvoiceIssueTime() {
		return invoiceIssueTime;
	}

	public void setInvoiceIssueTime(String invoiceIssueTime) {
		this.invoiceIssueTime = invoiceIssueTime;
	}

	public String getNoteIssueDate() {
		return noteIssueDate;
	}

	public void setNoteIssueDate(String noteIssueDate) {
		this.noteIssueDate = noteIssueDate;
	}

//	public String getNoteIssueTime() {
//		return noteIssueTime;
//	}
//	public void setNoteIssueTime(String noteIssueTime) {
//		this.noteIssueTime = noteIssueTime;
//	}
	public String getOrgSupplyDate() {
		return orgSupplyDate;
	}

	public void setOrgSupplyDate(String orgSupplyDate) {
		this.orgSupplyDate = orgSupplyDate;
	}

	public String getSupplyDate() {
		return supplyDate;
	}

	public void setSupplyDate(String supplyDate) {
		this.supplyDate = supplyDate;
	}

	public String getSupplierPartyIdentificationID() {
		return supplierPartyIdentificationID;
	}

	public void setSupplierPartyIdentificationID(String supplierPartyIdentificationID) {
		this.supplierPartyIdentificationID = supplierPartyIdentificationID;
	}

	public String getSupplierPartyIdentificationschemeID() {
		return supplierPartyIdentificationschemeID;
	}

	public void setSupplierPartyIdentificationschemeID(String supplierPartyIdentificationschemeID) {
		this.supplierPartyIdentificationschemeID = supplierPartyIdentificationschemeID;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerAddressStreet() {
		return sellerAddressStreet;
	}

	public void setSellerAddressStreet(String sellerAddressStreet) {
		this.sellerAddressStreet = sellerAddressStreet;
	}

	public String getSellerAddressAdditinalStreet() {
		return sellerAddressAdditinalStreet;
	}

	public void setSellerAddressAdditinalStreet(String sellerAddressAdditinalStreet) {
		this.sellerAddressAdditinalStreet = sellerAddressAdditinalStreet;
	}

	public String getSellerBuildingNumber() {
		return sellerBuildingNumber;
	}

	public void setSellerBuildingNumber(String sellerBuildingNumber) {
		this.sellerBuildingNumber = sellerBuildingNumber;
	}

	public String getSellerAdditionalBuildNumber() {
		return sellerAdditionalBuildNumber;
	}

	public void setSellerAdditionalBuildNumber(String sellerAdditionalBuildNumber) {
		this.sellerAdditionalBuildNumber = sellerAdditionalBuildNumber;
	}

	public String getSellerPlotIdentification() {
		return sellerPlotIdentification;
	}

	public void setSellerPlotIdentification(String sellerPlotIdentification) {
		this.sellerPlotIdentification = sellerPlotIdentification;
	}

	public String getSellerCity() {
		return sellerCity;
	}

	public void setSellerCity(String sellerCity) {
		this.sellerCity = sellerCity;
	}

	public String getSellerCitySubdivisionName() {
		return sellerCitySubdivisionName;
	}

	public void setSellerCitySubdivisionName(String sellerCitySubdivisionName) {
		this.sellerCitySubdivisionName = sellerCitySubdivisionName;
	}

	public String getSellerPostalCode() {
		return sellerPostalCode;
	}

	public void setSellerPostalCode(String sellerPostalCode) {
		this.sellerPostalCode = sellerPostalCode;
	}

	public String getSelletCountrySubentity() {
		return selletCountrySubentity;
	}

	public void setSelletCountrySubentity(String selletCountrySubentity) {
		this.selletCountrySubentity = selletCountrySubentity;
	}

	public String getSellerState() {
		return sellerState;
	}

	public void setSellerState(String sellerState) {
		this.sellerState = sellerState;
	}

	public String getSellerDistrict() {
		return sellerDistrict;
	}

	public void setSellerDistrict(String sellerDistrict) {
		this.sellerDistrict = sellerDistrict;
	}

	public String getSellerCountryCode() {
		return sellerCountryCode;
	}

	public void setSellerCountryCode(String sellerCountryCode) {
		this.sellerCountryCode = sellerCountryCode;
	}

	public String getSellerVATRegNumber() {
		return sellerVATRegNumber;
	}

	public void setSellerVATRegNumber(String sellerVATRegNumber) {
		this.sellerVATRegNumber = sellerVATRegNumber;
	}

	public String getSellerAdditionalID() {
		return sellerAdditionalID;
	}

	public void setSellerAdditionalID(String sellerAdditionalID) {
		this.sellerAdditionalID = sellerAdditionalID;
	}

	public String getPaymentMeansCode() {
		return paymentMeansCode;
	}

	public void setPaymentMeansCode(String paymentMeansCode) {
		this.paymentMeansCode = paymentMeansCode;
	}

//	public String getAllowanceChargeID() {
//		return allowanceChargeID;
//	}
//	public void setAllowanceChargeID(String allowanceChargeID) {
//		this.allowanceChargeID = allowanceChargeID;
//	}
	public String getAllowanceChargeIndicator() {
		return allowanceChargeIndicator;
	}

	public void setAllowanceChargeIndicator(String allowanceChargeIndicator) {
		this.allowanceChargeIndicator = allowanceChargeIndicator;
	}

	public String getAllowanceChargeReason() {
		return allowanceChargeReason;
	}

	public void setAllowanceChargeReason(String allowanceChargeReason) {
		this.allowanceChargeReason = allowanceChargeReason;
	}

	public String getAllowanceChargeAmountCurrencyID() {
		return allowanceChargeAmountCurrencyID;
	}

	public void setAllowanceChargeAmountCurrencyID(String allowanceChargeAmountCurrencyID) {
		this.allowanceChargeAmountCurrencyID = allowanceChargeAmountCurrencyID;
	}

	public String getAllowanceChargeAmount() {
		return allowanceChargeAmount;
	}

	public void setAllowanceChargeAmount(String allowanceChargeAmount) {
		this.allowanceChargeAmount = allowanceChargeAmount;
	}

	public String getAllowanceChargeTaxCategorySchemeAgencyID() {
		return allowanceChargeTaxCategorySchemeAgencyID;
	}

	public void setAllowanceChargeTaxCategorySchemeAgencyID(String allowanceChargeTaxCategorySchemeAgencyID) {
		this.allowanceChargeTaxCategorySchemeAgencyID = allowanceChargeTaxCategorySchemeAgencyID;
	}

	public String getAllowanceChargeTaxCategorySchemeID() {
		return allowanceChargeTaxCategorySchemeID;
	}

	public void setAllowanceChargeTaxCategorySchemeID(String allowanceChargeTaxCategorySchemeID) {
		this.allowanceChargeTaxCategorySchemeID = allowanceChargeTaxCategorySchemeID;
	}

	public String getAllowanceChargeTaxCategoryPercent() {
		return allowanceChargeTaxCategoryPercent;
	}

	public void setAllowanceChargeTaxCategoryPercent(String allowanceChargeTaxCategoryPercent) {
		this.allowanceChargeTaxCategoryPercent = allowanceChargeTaxCategoryPercent;
	}

	public String getAllowanceChargeTaxCategoryID() {
		return allowanceChargeTaxCategoryID;
	}

	public void setAllowanceChargeTaxCategoryID(String allowanceChargeTaxCategoryID) {
		this.allowanceChargeTaxCategoryID = allowanceChargeTaxCategoryID;
	}

	public String getAllowanceChargeTaxSchemeSchemeAgencyID() {
		return allowanceChargeTaxSchemeSchemeAgencyID;
	}

	public void setAllowanceChargeTaxSchemeSchemeAgencyID(String allowanceChargeTaxSchemeSchemeAgencyID) {
		this.allowanceChargeTaxSchemeSchemeAgencyID = allowanceChargeTaxSchemeSchemeAgencyID;
	}

	public String getAllowanceChargeTaxSchemeSchemeID() {
		return allowanceChargeTaxSchemeSchemeID;
	}

	public void setAllowanceChargeTaxSchemeSchemeID(String allowanceChargeTaxSchemeSchemeID) {
		this.allowanceChargeTaxSchemeSchemeID = allowanceChargeTaxSchemeSchemeID;
	}

	public String getAllowanceChargeTaxCategoryTaxScheme() {
		return allowanceChargeTaxCategoryTaxScheme;
	}

	public void setAllowanceChargeTaxCategoryTaxScheme(String allowanceChargeTaxCategoryTaxScheme) {
		this.allowanceChargeTaxCategoryTaxScheme = allowanceChargeTaxCategoryTaxScheme;
	}

	public String getLineExtensionAmount() {
		return lineExtensionAmount;
	}

	public void setLineExtensionAmount(String lineExtensionAmount) {
		this.lineExtensionAmount = lineExtensionAmount;
	}

	public String getTaxExclusiveAmount() {
		return taxExclusiveAmount;
	}

	public void setTaxExclusiveAmount(String taxExclusiveAmount) {
		this.taxExclusiveAmount = taxExclusiveAmount;
	}

	public String getTaxInclusiveAmount() {
		return taxInclusiveAmount;
	}

	public void setTaxInclusiveAmount(String taxInclusiveAmount) {
		this.taxInclusiveAmount = taxInclusiveAmount;
	}

	public String getAllowanceTotalAmount() {
		return allowanceTotalAmount;
	}

	public void setAllowanceTotalAmount(String allowanceTotalAmount) {
		this.allowanceTotalAmount = allowanceTotalAmount;
	}

	public String getPrepaidAmount() {
		return prepaidAmount;
	}

	public void setPrepaidAmount(String prepaidAmount) {
		this.prepaidAmount = prepaidAmount;
	}

	public String getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(String payableAmount) {
		this.payableAmount = payableAmount;
	}
	

	public String getPayableRoundingAmount() {
		return payableRoundingAmount;
	}

	public void setPayableRoundingAmount(String payableRoundingAmount) {
		this.payableRoundingAmount = payableRoundingAmount;
	}

	public String getOrderPurchaseOrder() {
		return orderPurchaseOrder;
	}

	public void setOrderPurchaseOrder(String orderPurchaseOrder) {
		this.orderPurchaseOrder = orderPurchaseOrder;
	}

	public String getOrderContactNumber() {
		return orderContactNumber;
	}

	public void setOrderContactNumber(String orderContactNumber) {
		this.orderContactNumber = orderContactNumber;
	}

//	public String getInvoiceSubtotalIncVAT() {
//		return invoiceSubtotalIncVAT;
//	}
//	public void setInvoiceSubtotalIncVAT(String invoiceSubtotalIncVAT) {
//		this.invoiceSubtotalIncVAT = invoiceSubtotalIncVAT;
//	}
//	public String getInvoiceDiscountPercentage() {
//		return invoiceDiscountPercentage;
//	}
//	public void setInvoiceDiscountPercentage(String invoiceDiscountPercentage) {
//		this.invoiceDiscountPercentage = invoiceDiscountPercentage;
//	}
//	public String getInvoiceDiscountAmount() {
//		return invoiceDiscountAmount;
//	}
//	public void setInvoiceDiscountAmount(String invoiceDiscountAmount) {
//		this.invoiceDiscountAmount = invoiceDiscountAmount;
//	}
//	public String getInvoiceTaxableAmount() {
//		return invoiceTaxableAmount;
//	}
//	public void setInvoiceTaxableAmount(String invoiceTaxableAmount) {
//		this.invoiceTaxableAmount = invoiceTaxableAmount;
//	}
//	public String getInvoiceVATTotal() {
//		return invoiceVATTotal;
//	}
//
//	public void setInvoiceVATTotal(String invoiceVATTotal) {
//		this.invoiceVATTotal = invoiceVATTotal;
//	}

//	public String getInvoiceGrossTotalIncVAT() {
//		return invoiceGrossTotalIncVAT;
//	}
//	public void setInvoiceGrossTotalIncVAT(String invoiceGrossTotalIncVAT) {
//		this.invoiceGrossTotalIncVAT = invoiceGrossTotalIncVAT;
//	}
//	public String getPaymentMethod() {
//		return paymentMethod;
//	}
//	public void setPaymentMethod(String paymentMethod) {
//		this.paymentMethod = paymentMethod;
//	}
	public String getSubPaymentMethod() {
		return subPaymentMethod;
	}

	public void setSubPaymentMethod(String subPaymentMethod) {
		this.subPaymentMethod = subPaymentMethod;
	}

//	public String getAmount() {
//		return amount;
//	}
//	public void setAmount(String amount) {
//		this.amount = amount;
//	}
//	public String getSupplierBankDetail() {
//		return supplierBankDetail;
//	}
//	public void setSupplierBankDetail(String supplierBankDetail) {
//		this.supplierBankDetail = supplierBankDetail;
//	}
	public String[] getNotes() {
		return notes;
	}

	public void setNotes(String[] notes) {
		this.notes = notes;
	}

	public String getSpecialTaxTreatement() {
		return specialTaxTreatement;
	}

	public void setSpecialTaxTreatement(String specialTaxTreatement) {
		this.specialTaxTreatement = specialTaxTreatement;
	}

	public String getOriginalInvoiceNumbers() {
		return originalInvoiceNumbers;
	}

	public void setOriginalInvoiceNumbers(String originalInvoiceNumbers) {
		this.originalInvoiceNumbers = originalInvoiceNumbers;
	}

	public String getCreditDebitReason() {
		return creditDebitReason;
	}

	public void setCreditDebitReason(String creditDebitReason) {
		this.creditDebitReason = creditDebitReason;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public ArrayList<AdditionalDocumentReference> getAdditionalDocumentReference() {
		return additionalDocumentReference;
	}

	public void setAdditionalDocumentReference(ArrayList<AdditionalDocumentReference> additionalDocumentReference) {
		this.additionalDocumentReference = additionalDocumentReference;
	}

	public ArrayList<LineItems> getLineItems() {
		return lineItems;
	}

	public void setLineItems(ArrayList<LineItems> lineItems) {
		this.lineItems = lineItems;
	}

	public ArrayList<TaxTotal> getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(ArrayList<TaxTotal> taxTotal) {
		this.taxTotal = taxTotal;
	}

	public InvoiceData() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "InvoiceData [invoiceTypeCode=" + invoiceTypeCode + ", invoiceTypeCodeName=" + invoiceTypeCodeName
				+ ", documentCurrencyCode=" + documentCurrencyCode + ", taxCurrencyCode=" + taxCurrencyCode
				+ ", storeId=" + storeId + ", registerId=" + registerId + ", specialBillAgreementFlag="
				+ specialBillAgreementFlag + ", specialTransactionTypeFlag=" + profileID + ", irn=" + irn
				+ ", invoiceIssueDate=" + invoiceIssueDate + ", invoiceIssueTime=" + invoiceIssueTime
				+ ", noteIssueDate=" + noteIssueDate + ", orgSupplyDate=" + orgSupplyDate + ", supplyDate=" + supplyDate
				+ ", supplierPartyIdentificationID=" + supplierPartyIdentificationID
				+ ", supplierPartyIdentificationschemeID=" + supplierPartyIdentificationschemeID + ", sellerName="
				+ sellerName + ", sellerAddressStreet=" + sellerAddressStreet + ", sellerAddressAdditinalStreet="
				+ sellerAddressAdditinalStreet + ", sellerBuildingNumber=" + sellerBuildingNumber
				+ ", sellerAdditionalBuildNumber=" + sellerAdditionalBuildNumber + ", sellerPlotIdentification="
				+ sellerPlotIdentification + ", sellerCity=" + sellerCity + ", sellerCitySubdivisionName="
				+ sellerCitySubdivisionName + ", sellerPostalCode=" + sellerPostalCode + ", selletCountrySubentity="
				+ selletCountrySubentity + ", sellerState=" + sellerState + ", sellerDistrict=" + sellerDistrict
				+ ", sellerCountryCode=" + sellerCountryCode + ", sellerVATRegNumber=" + sellerVATRegNumber
				+ ", sellerAdditionalID=" + sellerAdditionalID + ", paymentMeansCode=" + paymentMeansCode
				+ ", allowanceChargeIndicator=" + allowanceChargeIndicator + ", allowanceChargeReason="
				+ allowanceChargeReason + ", allowanceChargeAmountCurrencyID=" + allowanceChargeAmountCurrencyID
				+ ", allowanceChargeAmount=" + allowanceChargeAmount + ", allowanceChargeTaxCategorySchemeAgencyID="
				+ allowanceChargeTaxCategorySchemeAgencyID + ", allowanceChargeTaxCategorySchemeID="
				+ allowanceChargeTaxCategorySchemeID + ", allowanceChargeTaxCategoryPercent="
				+ allowanceChargeTaxCategoryPercent + ", allowanceChargeTaxCategoryID=" + allowanceChargeTaxCategoryID
				+ ", allowanceChargeTaxSchemeSchemeAgencyID=" + allowanceChargeTaxSchemeSchemeAgencyID
				+ ", allowanceChargeTaxSchemeSchemeID=" + allowanceChargeTaxSchemeSchemeID
				+ ", allowanceChargeTaxCategoryTaxScheme=" + allowanceChargeTaxCategoryTaxScheme
				+ ", lineExtensionAmount=" + lineExtensionAmount + ", taxExclusiveAmount=" + taxExclusiveAmount
				+ ", taxInclusiveAmount=" + taxInclusiveAmount + ", allowanceTotalAmount=" + allowanceTotalAmount
				+ ", prepaidAmount=" + prepaidAmount + ", payableAmount=" + payableAmount + ", payableRoundingAmount="+payableRoundingAmount+", orderPurchaseOrder="
				+ orderPurchaseOrder + ", orderContactNumber=" + orderContactNumber + ", subPaymentMethod="
				+ subPaymentMethod + ", orderPurchaseOrder=" + ", notes=" + notes + ", specialTaxTreatement="
				+ specialTaxTreatement + ", originalInvoiceNumbers=" + originalInvoiceNumbers + ", creditDebitReason="
				+ creditDebitReason + ", currency=" + currency + ", additionalDocumentReference="
				+ additionalDocumentReference + ", lineItems=" + lineItems + ", taxTotal=" + taxTotal + "]";
	}

}