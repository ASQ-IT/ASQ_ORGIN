/**
 * 
 */
package asq.pos.loyalty.stc.tender.op;

import dtv.pos.framework.form.BasicEditModel;

/**
 * @author RA20221457
 *
 */

public class AsqSTCTenderOTPEditModel extends BasicEditModel {

	/**
	 * This class extends the Xstore Standard model class to handle all actions
	 * related to OTP field
	 */

	public static final String STC_OTP_FIELD = "stcOTP";
	public static final String STC_REDEEM_POINTS_FIELD = "stcRedeemPoints";

	private String stcOTP;
	private String stcRedeemPoints;

	public String getStcRedeemPoints() {
		return stcRedeemPoints;
	}

	public void setStcRedeemPoints(String stcRedeemPoints) {
		this.stcRedeemPoints = stcRedeemPoints;
	}

	public String getStcOTP() {
		return stcOTP;
	}

	public void setStcOTP(String stcOTP) {
		this.stcOTP = stcOTP;
	}

	public AsqSTCTenderOTPEditModel() {
		super(FF.getTranslatable("_asqCaptureOTP&RedeemPointsTitle"),
				FF.getTranslatable("_asqCaptureOTP&RedeemPointsDescription"));
		addField(STC_OTP_FIELD, String.class);
		addField(STC_REDEEM_POINTS_FIELD, String.class);
		initializeFieldState();
	}

}
