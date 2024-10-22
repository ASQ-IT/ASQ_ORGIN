package asq.pos.bnpl.tabby.tender.op;

import dtv.pos.framework.form.BasicEditModel;


/**
 * @author RA20221457
 *
 */

public class AsqBnplTabbyEditModel extends BasicEditModel{
	public static final String ASQ_MOBILE_NUMBER_FIELD = "custMobileNumber";
	public static final String ASQ_CUSTOMER_EMAIL_FIELD = "custEmailAddress";

	private String custMobileNumber;
	private String custEmailAddress;

	public String getCustMobileNumber() {
		return custMobileNumber;
	}

	public void setCustMobileNumber(String custMobileNumber) {
		this.custMobileNumber = custMobileNumber;
	}

	public String getCustEmailAddress() {
		return custEmailAddress;
	}

	public void setCustEmailAddress(String custEmailAddress) {
		this.custEmailAddress = custEmailAddress;
	}

	public AsqBnplTabbyEditModel() {
		super(FF.getTranslatable("_asqCaptureCustMobileNumber&EmailAddressTitle"),FF.getTranslatable("_asqCaptureCustMobileNumber&EmailAddressDescription"));
		addField(ASQ_MOBILE_NUMBER_FIELD, String.class);
		addField(ASQ_CUSTOMER_EMAIL_FIELD, String.class);
		initializeFieldState();
	}

}
