package asq.pos.common;

import java.math.BigDecimal;
import java.util.HashMap;

import dtv.pos.framework.scope.ValueKey;

public final class AsqValueKeys {

	public static final ValueKey<String> ZATCA_OTP = new ValueKey<String>(String.class, "ZATCA_OTP");

	public static final ValueKey<HashMap<String, BigDecimal>> ASQ_TOLA_WEIGHT = new ValueKey(HashMap.class, "ASQ_TOLA_WEIGHT");

	public static final ValueKey<String> ASQ_MOBILE_NUMBER = new ValueKey<String>(String.class, "ASQ_MOBILE_NUMBER");

	public static final ValueKey<String> ASQ_STC_OTP = new ValueKey<String>(String.class, "ASQ_STC_OTP");

	public static final ValueKey<String> ASQ_ZATCA_QR_CODE = new ValueKey<String>(String.class, "");

	 public static final ValueKey<Boolean> ASQ_TAMARA_PAYMENT_SUCCESS = new ValueKey<Boolean>(Boolean.class, "ASQ_TAMARA_PAYMENT_SUCCESS");

	 public static final ValueKey<String> ASQ_TAMARA_ORDERID = new ValueKey(String.class, "ASQ_TAMARA_ORDERID");
	 
	 public static final ValueKey<String> ASQ_TAMARA_CHECKOUTID = new ValueKey(String.class, "ASQ_TAMARA_CHECKOUTID");
	 
	 public static final ValueKey<String> ASQ_NEQATY_MOBILE = new ValueKey(String.class, "ASQ_NEQATY_MOBILE");
		
	public static final ValueKey<String> ASQ_NEQATY_TRANS_REFERENCE = new ValueKey(String.class, "ASQ_NEQATY_TRANS_REFERENCE");
		
	public static final ValueKey<String> ASQ_NEQATY_TRANS_TOKEN = new ValueKey(String.class, "ASQ_NEQATY_TRANS_TOKEN");
}
