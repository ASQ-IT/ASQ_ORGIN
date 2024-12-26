package asq.pos.common;

import java.math.BigDecimal;
import java.util.HashMap;

import asq.pos.loyalty.neqaty.gen.NeqatyWSAPIRedeemOption;
import dtv.pos.framework.scope.ValueKey;

public final class AsqValueKeys {

	public static final ValueKey<String> ZATCA_OTP = new ValueKey<String>(String.class, "ZATCA_OTP");
	public static final ValueKey<String> ASQ_ZATCA_QR_CODE = new ValueKey(String.class, "");

	public static final ValueKey<HashMap<String, BigDecimal>> ASQ_TOLA_WEIGHT = new ValueKey(HashMap.class, "ASQ_TOLA_WEIGHT");

	public static final ValueKey<String> ASQ_MOBILE_NUMBER = new ValueKey<String>(String.class, "ASQ_MOBILE_NUMBER");
	public static final ValueKey<String> ASQ_STC_OTP = new ValueKey<String>(String.class, "ASQ_STC_OTP");
	public static final ValueKey<BigDecimal> ASQ_STC_PNT_RDMPTN = new ValueKey<BigDecimal>(BigDecimal.class, "ASQ_STC_PNT_RDMPTN");
	public static final ValueKey<String> ASQ_STC_REF_REQUEST_ID = new ValueKey<String>(String.class, "ASQ_STC_REF_REQUEST_ID");
	public static final ValueKey<String> ASQ_STC_REF_REQUEST_DATE = new ValueKey<String>(String.class, "ASQ_STC_REF_REQUEST_DATE");
	public static final ValueKey<String> STC_SUCCESS_REFUND_REDEEM_RESPONSE = new ValueKey<String>(String.class, "STC_SUCCESS_REFUND_REDEEM_RESPONSE");

	public static final ValueKey<String> TAMARA_RESPONSE = new ValueKey<String>(String.class, "TAMARA_RESPONSE");
	public static final ValueKey<String> ASQ_TAMARA_ORDERID = new ValueKey<String>(String.class, "ASQ_TAMARA_ORDERID");
	public static final ValueKey<String> ASQ_TAMARA_PMNT_AMNT = new ValueKey<String>(String.class, "ASQ_TAMARA_PMNT_AMNT");
	public static final ValueKey<String> ASQ_TAMARA_CHECKOUTID = new ValueKey<String>(String.class, "ASQ_TAMARA_CHECKOUTID");
	public static final ValueKey<Boolean> ASQ_TAMARA_PAYMENT_SUCCESS = new ValueKey<Boolean>(Boolean.class, "ASQ_TAMARA_PAYMENT_SUCCESS");
	public static final ValueKey<Boolean> ASQ_TAMARA_PAYMENT_EXPIRED = new ValueKey<Boolean>(Boolean.class, "ASQ_TAMARA_PAYMENT_EXPIRED");
	public static final ValueKey<Boolean> ASQ_TAMARA_CUSTOMER_PAYMENT_CONFIRMATION = new ValueKey<Boolean>(Boolean.class, "ASQ_TAMARA_CUSTOMER_PAYMENT_CONFIRMATION");

	public static final ValueKey<String> ASQ_NEQATY_MOBILE = new ValueKey(String.class, "ASQ_NEQATY_MOBILE");
	public static final ValueKey<String> ASQ_NEQATY_TRANS_REFERENCE = new ValueKey(String.class, "ASQ_NEQATY_TRANS_REFERENCE");
	public static final ValueKey<Integer> ASQ_NEQATY_TRANS_TOKEN = new ValueKey(Integer.class, "ASQ_NEQATY_TRANS_TOKEN");
	public static final ValueKey<Integer> ASQ_RETURN_OTP = new ValueKey<>(Integer.class, "ASQ_RETURN_OTP");
	public static final ValueKey<NeqatyWSAPIRedeemOption> ASQ_NEQATY_REDEEM_POINTS = new ValueKey(NeqatyWSAPIRedeemOption.class, "ASQ_NEQATY_REDEEM_POINTS");

	public static final ValueKey<String> TABBY_RESPONSE = new ValueKey<String>(String.class, "TABBY_RESPONSE");
	public static final ValueKey<String> ASQ_TABBY_PMNT_ID = new ValueKey<String>(String.class, "ASQ_TABBY_PMNT_ID");
	public static final ValueKey<String> ASQ_TABBY_SESSION_ID = new ValueKey<String>(String.class, "ASQ_TABBY_SESSION_ID");
	public static final ValueKey<String> ASQ_TABBY_PMNT_AMNT = new ValueKey<String>(String.class, "ASQ_TABBY_PMNT_AMNT");
	public static final ValueKey<String> ASQ_TABBY_PMNT_STATUS = new ValueKey<String>(String.class, "ASQ_TABBY_PMNT_STATUS");
	public static final ValueKey<String> ASQ_TABBY_PMNT_CRTN_DATE = new ValueKey<String>(String.class, "ASQ_TABBY_PMNT_CRTN_DATE");
	public static final ValueKey<String> ASQ_TABBY_PMNT_EXPRY_DATE = new ValueKey<String>(String.class, "ASQ_TABBY_PMNT_EXPRY_DATE");
	public static final ValueKey<Boolean> ASQ_TABBY_PAYMENT_EXPIRED = new ValueKey<Boolean>(Boolean.class, "ASQ_TABBY_PAYMENT_EXPIRED");
	public static final ValueKey<Boolean> ASQ_TABBY_PAYMENT_SUCCESS = new ValueKey<Boolean>(Boolean.class, "ASQ_TABBY_PAYMENT_SUCCESS");

	public static final ValueKey<String> ASQ_MOKAFAA_AUTH_TOKEN = new ValueKey<String>(String.class, "ASQ_MOKAFAA_AUTH_TOKEN");
	public static final ValueKey<BigDecimal> ASQ_MOKAFAA_AMOUNT = new ValueKey<>(BigDecimal.class, "ASQ_MOKAFAA_AMOUNT");
	public static final ValueKey<String> ASQ_MOKAFAA_OTP_TOKEN = new ValueKey<String>(String.class, "ASQ_MOKAFAA_OTP_TOKEN");
	public static final ValueKey<String> ASQ_MOKAFAA_LOYALITY_CIC_NO = new ValueKey<String>(String.class, "ASQ_MOKAFAA_LOYALITY_CIC_NO");
	public static final ValueKey<Long> ASQ_MOKAFAA_REDEEM_TRANSACTION_ID = new ValueKey<>(Long.class, "ASQ_MOKAFAA_REDEEM_TRANSACTION_ID");
	public static final ValueKey<String> ASQ_NEQATY_TRANS_REFERENCE_EARN = new ValueKey<>(String.class, "ASQ_NEQATY_TRANS_REFERENCE_EARN");
	public static final ValueKey<Boolean> ASQ_RETURN_OFFLINE = new ValueKey<Boolean>(Boolean.class, "ASQ_RETURN_OFFLINE");
	public static final ValueKey<Integer> ASQ_BIN_TRANSFER_OTP = new ValueKey<Integer>(Integer.class, "ASQ_BIN_TRANSFER_OTP");
	public static final ValueKey<Boolean> ASQ_LOYALTY = new ValueKey<Boolean>(Boolean.class, "ASQ_LOYALTY");

	public static final ValueKey<Integer> ASQ_STORE_TRANSFER_OTP = new ValueKey<Integer>(Integer.class, "ASQ_STORE_TRANSFER_OTP");

}
