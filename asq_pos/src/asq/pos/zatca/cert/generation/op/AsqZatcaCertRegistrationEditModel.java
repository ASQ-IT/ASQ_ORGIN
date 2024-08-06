/**
 * 
 */
package asq.pos.zatca.cert.generation.op;

import dtv.pos.framework.form.BasicEditModel;

/**
 * @author RA20221457
 *
 */
public class AsqZatcaCertRegistrationEditModel extends BasicEditModel {
	public static final String CAPTURE_OTP_FIELD = "captureOTP";

	private String captureOTP;

	public AsqZatcaCertRegistrationEditModel() {
		super(FF.getTranslatable("_captureOTPTitle"), FF.getTranslatable("_captureOTPDescription"));
		addField(CAPTURE_OTP_FIELD, String.class);
		initializeFieldState();
	}

	public String getCaptureOTP() {
		return this.captureOTP;
	}

	public void setCaptureOTP(String argCaptureOTP) {
		this.captureOTP = argCaptureOTP;
	}
}
