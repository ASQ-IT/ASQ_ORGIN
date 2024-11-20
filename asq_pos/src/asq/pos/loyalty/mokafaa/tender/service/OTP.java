package asq.pos.loyalty.mokafaa.tender.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonProperty;

public class OTP {
	
	@JsonProperty("currency")
	private String currency;
	@JsonProperty("otp_token_expired_in_min")
	private String otp_token_expired_in_min;
	@JsonProperty("otp_token")
	private String otp_token;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getOtp_token_expired_in_min() {
		return otp_token_expired_in_min;
	}
	public void setOtp_token_expired_in_min(String otp_token_expired_in_min) {
		this.otp_token_expired_in_min = otp_token_expired_in_min;
	}
	public String getOtp_token() {
		return otp_token;
	}
	public void setOtp_token(String otp_token) {
		this.otp_token = otp_token;
	}

}
