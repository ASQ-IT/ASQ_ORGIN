/**
 * 
 */
package asq.pos.loyalty.stc.tender.op;

import dtv.pos.framework.form.BasicEditModel;

/**
 * @author RA20221457
 *
 */
public class AsqSTCMobileNumberEditModel extends BasicEditModel {
	public static final String STC_MOBILE_FIELD = "custMobileNumber";
		
	private String custMobileNumber;
	
	
	
	public String getCustMobileNumber() {
		return custMobileNumber;
	}



	public void setCustMobileNumber(String custMobileNumber) {
		this.custMobileNumber = custMobileNumber;
	}



	public AsqSTCMobileNumberEditModel() {
		super(FF.getTranslatable("_captureMobileNumberTitle"), FF.getTranslatable("_captureMobileNumberDescription"));
		addField(STC_MOBILE_FIELD, String.class);
		initializeFieldState();
	}
	
}
	



