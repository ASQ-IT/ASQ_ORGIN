package asq.pos.bnpl.tamara.tender.op;

import dtv.pos.framework.form.BasicEditModel;

public class AsqBnplTamaraEditModel extends BasicEditModel {

	public static final String ASQ_MOBILE_NUMBER_FIELD = "custMobileNumber";

	private String custMobileNumber;

	public String getCustMobileNumber() {
		return custMobileNumber;
	}

	public void setCustMobileNumber(String custMobileNumber) {
		this.custMobileNumber = custMobileNumber;
	}

	public AsqBnplTamaraEditModel() {
		super(FF.getTranslatable("_captureCustMobileNumberTitle"), FF.getTranslatable("_captureCustMobileNumberDescription"));
		addField(ASQ_MOBILE_NUMBER_FIELD, String.class);
		initializeFieldState();
	}
}
