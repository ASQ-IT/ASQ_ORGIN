package asq.pos.bnpl.tabby.tender.service;

import java.util.ArrayList;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;

import dtv.servicex.impl.req.ServiceRequest;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsqSubmitBnplTabbyServiceRequest extends ServiceRequest implements IAsqSubmitBnplTabbyServiceRequest {
   
	private String id;
	private AsqBnplTabbyPaymentObj payment;
    private String lang;
    private String merchant_code;
    private String amount;
    private String reference_id;
    private String reason;
    private String created_at;
//    ArrayList< Object > items;



    // Getter Methods

    public AsqBnplTabbyPaymentObj getPayment() {
        return payment;
    }

    public String getMerchant_code() {
        return merchant_code;
    }

    // Setter Methods

    public void setPayment(AsqBnplTabbyPaymentObj payment) {
        this.payment = payment;
    }

    public void setMerchant_code(String merchant_code) {
        this.merchant_code = merchant_code;
    }

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReference_id() {
		return reference_id;
	}

	public void setReference_id(String reference_id) {
		this.reference_id = reference_id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
