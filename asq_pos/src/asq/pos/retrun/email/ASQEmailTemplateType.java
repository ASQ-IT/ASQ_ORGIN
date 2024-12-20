package asq.pos.retrun.email;

import dtv.pos.framework.email.EmailTemplateType;

public class ASQEmailTemplateType extends EmailTemplateType{
	
	public static final ASQEmailTemplateType ASQ_RETURN_EMAIL_RECEIPT = new ASQEmailTemplateType("ASQ_RETURN_EMAIL_RECEIPT", "_asqReturnEmailReceipt");
	
	public static final ASQEmailTemplateType ASQ_SHIP_EMAIL_RECEIPT = new ASQEmailTemplateType("ASQ_SHIP_EMAIL_RECEIPT", "_asqStoreTransferEmailReceipt");

	public ASQEmailTemplateType(String argName, String argTemplateKey) {
		super(argName, argTemplateKey);
	}

}
