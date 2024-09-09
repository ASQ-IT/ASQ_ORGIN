/**
 * 
 */
package asq.pos.loyalty.neqaty.tender.op;

import dtv.pos.framework.form.BasicEditModel;

/**
 * @author RA20221457
 *
 */
public class AsqNeqatyRedeemEditModel extends BasicEditModel {

	/**
	 * This class implements the model for customer mobile number field
	 */

	public static final String NEQATY_REDEEM_POINTS_FIELD = "neqatyRedeemPoints";

	private String neqatyRedeemPoints;
	
	public String getNeqatyRedeemPoints() {
		return neqatyRedeemPoints;
	}
	public void setNeqatyRedeemPoints(String neqatyRedeemPoints) {
		this.neqatyRedeemPoints = neqatyRedeemPoints;
	}
	
	public AsqNeqatyRedeemEditModel() {
		super(FF.getTranslatable("_asqCaptureOTP&RedeemPointsTitle"),
				FF.getTranslatable("_asqCaptureOTP&RedeemPointsDescription"));
		addField(NEQATY_REDEEM_POINTS_FIELD, String.class);
		initializeFieldState();
	}


}
