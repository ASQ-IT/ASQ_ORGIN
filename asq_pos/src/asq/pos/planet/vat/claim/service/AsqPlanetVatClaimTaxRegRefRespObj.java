package asq.pos.planet.vat.claim.service;

import java.util.ArrayList;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqPlanetVatClaimTaxRegRefRespObj {
	
	private boolean taxRegisterStatus;
	private String taxRegisterTagNumber;
	private String digitalReceipt;
	private String taxRefundTagNumber;
	private boolean taxRefundStatus;
	private String taxRefundQrCode;
	private Number refundAmount;
	private String message;
	private Number taxRefundStatusCode;
//	private ArrayList<Object> taxRefundExcludedItems;
	
	public boolean isTaxRegisterStatus() {
		return taxRegisterStatus;
	}
	public void setTaxRegisterStatus(boolean taxRegisterStatus) {
		this.taxRegisterStatus = taxRegisterStatus;
	}
	public String getTaxRegisterTagNumber() {
		return taxRegisterTagNumber;
	}
	public void setTaxRegisterTagNumber(String taxRegisterTagNumber) {
		this.taxRegisterTagNumber = taxRegisterTagNumber;
	}
	public String getDigitalReceipt() {
		return digitalReceipt;
	}
	public void setDigitalReceipt(String digitalReceipt) {
		this.digitalReceipt = digitalReceipt;
	}
	public String getTaxRefundTagNumber() {
		return taxRefundTagNumber;
	}
	public void setTaxRefundTagNumber(String taxRefundTagNumber) {
		this.taxRefundTagNumber = taxRefundTagNumber;
	}
	public boolean isTaxRefundStatus() {
		return taxRefundStatus;
	}
	public void setTaxRefundStatus(boolean taxRefundStatus) {
		this.taxRefundStatus = taxRefundStatus;
	}
	public String getTaxRefundQrCode() {
		return taxRefundQrCode;
	}
	public void setTaxRefundQrCode(String taxRefundQrCode) {
		this.taxRefundQrCode = taxRefundQrCode;
	}
	public Number getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Number refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Number getTaxRefundStatusCode() {
		return taxRefundStatusCode;
	}
	public void setTaxRefundStatusCode(Number taxRefundStatusCode) {
		this.taxRefundStatusCode = taxRefundStatusCode;
	}

}
