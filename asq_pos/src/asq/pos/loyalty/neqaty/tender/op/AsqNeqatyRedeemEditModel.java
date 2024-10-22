/**
 * 
 */
package asq.pos.loyalty.neqaty.tender.op;

import dtv.pos.framework.form.BasicEditModel;
import dtv.pos.framework.form.CodeEnumValueWrapper;
import dtv.pos.framework.form.ValueWrapperFactory;
import dtv.pos.iframework.form.IValueWrapperFactory;

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

	private static final IValueWrapperFactory redeemWrapperFactory_ = ValueWrapperFactory
			.makeWrapperFactory(CodeEnumValueWrapper.class);

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
