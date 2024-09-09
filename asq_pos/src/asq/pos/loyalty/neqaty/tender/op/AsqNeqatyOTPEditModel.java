package asq.pos.loyalty.neqaty.tender.op;

import dtv.pos.framework.form.BasicEditModel;

/**
 * @author RA20221457
 *
 */
public class AsqNeqatyOTPEditModel extends BasicEditModel {

	/**
	 * This class implements the model for customer mobile number field
	 */

	public static final String NEQATY_OTP_FIELD = "neqatyOTP";
	public static final String NEQATY_REDEEM_POINTS_FIELD = "neqatyRedeemPoints";

	private String neqatyOTP;
	public String getNeqatyOTP() {
		return neqatyOTP;
	}
	public void setNeqatyOTP(String neqatyOTP) {
		this.neqatyOTP = neqatyOTP;
	}
	
	public AsqNeqatyOTPEditModel() {
		super(FF.getTranslatable("_asqCaptureOTP&RedeemPointsTitle"),
				FF.getTranslatable("_asqCaptureOTP&RedeemPointsDescription"));
		addField(NEQATY_OTP_FIELD, String.class);
		initializeFieldState();
	}


}
