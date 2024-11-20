/**
 * 
 */
package asq.pos.loyalty.mokafaa.tender.op;

import dtv.pos.framework.form.BasicEditModel;

/**
 * @author RA20221457
 *
 */

public class AsqMokafaaTenderOTPEditModel extends BasicEditModel {

	/**
	 * This class extends the Xstore Standard model class to handle all actions
	 * related to OTP field
	 */

	public static final String MOKAFAA_OTP_FIELD = "mokafaaOTP";
	public static final String MOKAFAA_REDEEM_POINTS_FIELD = "mokafaaRedeemPoints";

	private String mokafaaOTP;
	private String mokafaaRedeemPoints;

	

	public String getMokafaaOTP() {
		return mokafaaOTP;
	}



	public void setMokafaaOTP(String mokafaaOTP) {
		this.mokafaaOTP = mokafaaOTP;
	}



	public String getMokafaaRedeemPoints() {
		return mokafaaRedeemPoints;
	}



	public void setMokafaaRedeemPoints(String mokafaaRedeemPoints) {
		this.mokafaaRedeemPoints = mokafaaRedeemPoints;
	}



	public AsqMokafaaTenderOTPEditModel() {
		super(FF.getTranslatable("_asqCaptureOTP&RedeemPointsTitle"),
				FF.getTranslatable("_asqCaptureOTP&RedeemPointsDescription"));
		addField(MOKAFAA_OTP_FIELD, String.class);
		addField(MOKAFAA_REDEEM_POINTS_FIELD, String.class);
		initializeFieldState();
	}

}
