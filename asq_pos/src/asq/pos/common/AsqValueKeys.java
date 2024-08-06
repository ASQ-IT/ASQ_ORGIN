package asq.pos.common;

import java.math.BigDecimal;
import java.util.HashMap;

import dtv.pos.framework.scope.ValueKey;

public final class AsqValueKeys {

	public static final ValueKey<String> ZATCA_OTP = new ValueKey<String>(String.class, "ZATCA_OTP");

	public static final ValueKey<HashMap<String, BigDecimal>> ASQ_TOLA_WEIGHT = new ValueKey(HashMap.class, "ASQ_TOLA_WEIGHT");

	public static final ValueKey<String> ASQ_STC_MOBILE = new ValueKey<String>(String.class, "ASQ_STC_MOBILE");

	public static final ValueKey<String> STC_OTP = new ValueKey(String.class, "STC_OTP");

	public static final ValueKey<String> ASQ_ZATCA_CERT_GEN = new ValueKey(String.class, "FALSE");

	public static final ValueKey<String> ASQ_ZATCA_SAMPLE_INVOICE_REG = new ValueKey(String.class, "FALSE");
}
