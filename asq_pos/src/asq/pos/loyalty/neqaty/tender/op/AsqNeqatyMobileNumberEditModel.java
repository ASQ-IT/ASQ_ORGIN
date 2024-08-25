/**
 * 
 */
package asq.pos.loyalty.neqaty.tender.op;

import dtv.pos.framework.form.BasicEditModel;

/**
 * @author RA20221457
 *
 */
public class AsqNeqatyMobileNumberEditModel extends BasicEditModel {

	/**
	 * This class implements the model for customer mobile number field
	 */

	public static final String NEQATY_MOBILE_FIELD = "custMobileNumber";

	private String custMobileNumber;

	public String getCustMobileNumber() {
		return custMobileNumber;
	}

	public void setCustMobileNumber(String custMobileNumber) {
		this.custMobileNumber = custMobileNumber;
	}

	public AsqNeqatyMobileNumberEditModel() {
		super(FF.getTranslatable("_asqCaptureMobileNumberTitle"),
				FF.getTranslatable("_asqCaptureMobileNumberDescription"));

		addField(NEQATY_MOBILE_FIELD, String.class);
		initializeFieldState();
	}

}
