package asq.pos.zatca.invoice.models;

public class PaymentTerms {

	private String paymentMethod;
	private String subPaymentMethod;
	private String supplierBankDetail;
	
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getSubPaymentMethod() {
		return subPaymentMethod;
	}
	public void setSubPaymentMethod(String subPaymentMethod) {
		this.subPaymentMethod = subPaymentMethod;
	}
	public String getSupplierBankDetail() {
		return supplierBankDetail;
	}
	public void setSupplierBankDetail(String supplierBankDetail) {
		this.supplierBankDetail = supplierBankDetail;
	}
	public PaymentTerms(String paymentMethod, String subPaymentMethod, String supplierBankDetail) {
		super();
		this.paymentMethod = paymentMethod;
		this.subPaymentMethod = subPaymentMethod;
		this.supplierBankDetail = supplierBankDetail;
	}
	@Override
	public String toString() {
		return "PaymentTerms [paymentMethod=" + paymentMethod + ", subPaymentMethod=" + subPaymentMethod
				+ ", supplierBankDetail=" + supplierBankDetail + "]";
	}
	public PaymentTerms() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}