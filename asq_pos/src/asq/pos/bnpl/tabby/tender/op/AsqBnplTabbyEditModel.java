package asq.pos.bnpl.tabby.tender.op;

import dtv.pos.framework.form.BasicEditModel;


/**
 * @author RA20221457
 *
 */

public class AsqBnplTabbyEditModel extends BasicEditModel{
	public static final String ASQ_MOBILE_NUMBER_FIELD = "custMobileNumber";

	private String custMobileNumber;

	public String getCustMobileNumber() {
		return custMobileNumber;
	}

	public void setCustMobileNumber(String custMobileNumber) {
		this.custMobileNumber = custMobileNumber;
	}

	public AsqBnplTabbyEditModel() {
		super(FF.getTranslatable("_asqCaptureMobileNumberTitle"), FF.getTranslatable("_asqCaptureMobileNumberDescription"));
		addField(ASQ_MOBILE_NUMBER_FIELD, String.class);
		initializeFieldState();
	}

}
