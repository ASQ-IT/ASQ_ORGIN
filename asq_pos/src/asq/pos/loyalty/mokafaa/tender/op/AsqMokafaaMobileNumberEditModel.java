/**
 * 
 */
package asq.pos.loyalty.mokafaa.tender.op;

import dtv.pos.framework.form.BasicEditModel;

/**
 * @author RA20221457
 *
 */

public class AsqMokafaaMobileNumberEditModel extends BasicEditModel {

	/**
	 * This class implements the model for customer mobile number field
	 */

	public static final String MOKAFAA_MOBILE_FIELD = "custMobileNumber";
	public static final String MOKAFAA_LOYALITY_CIC_FIELD = "loyaltyCICNo";

	private String custMobileNumber;
	private String loyaltyCICNo;

	public String getCustMobileNumber() {
		return custMobileNumber;
	}

	public void setCustMobileNumber(String custMobileNumber) {
		this.custMobileNumber = custMobileNumber;
	}

	public String getLoyaltyCICNo() {
		return loyaltyCICNo;
	}

	public void setLoyaltyCICNo(String loyaltyCICNo) {
		this.loyaltyCICNo = loyaltyCICNo;
	}

	public AsqMokafaaMobileNumberEditModel() {
		super(FF.getTranslatable("_asqCaptureMobileNumberLoyaltyCICNoTitle"),
				FF.getTranslatable("_asqCaptureMobileNumberLoyaltyCICNoDescription"));

		addField(MOKAFAA_MOBILE_FIELD, String.class);
		addField(MOKAFAA_LOYALITY_CIC_FIELD, String.class);
		initializeFieldState();
	}

}
