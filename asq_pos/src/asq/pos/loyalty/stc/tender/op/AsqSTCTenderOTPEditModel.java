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
	public static final String STC_OTP_FIELD = "stcOTP";

	private String stcOTP;
	
	public String getStcOTP() {
		return stcOTP;
	}

	public void setStcOTP(String stcOTP) {
		this.stcOTP = stcOTP;
	}

	public AsqSTCTenderOTPEditModel() {
		super(FF.getTranslatable("_captureOTPTitle"), FF.getTranslatable("_captureOTPDescription"));
		addField(STC_OTP_FIELD, String.class);
		initializeFieldState();
	}
	
}

