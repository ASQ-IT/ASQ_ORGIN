package asq.pos.retrun.email;

import dtv.pos.framework.email.EmailTemplateType;

public class ASQEmailTemplateType extends EmailTemplateType{
	
	public static final ASQEmailTemplateType ASQ_RETURN_EMAIL_RECEIPT = new ASQEmailTemplateType("ASQ_RETURN_EMAIL_RECEIPT", "_asqReturnEmailReceipt");
	
	public static final ASQEmailTemplateType ASQ_SHIP_EMAIL_RECEIPT = new ASQEmailTemplateType("ASQ_SHIP_EMAIL_RECEIPT", "_asqStoreTransferEmailReceipt");

	public static final ASQEmailTemplateType ASQ_DEPOSIT_EMAIL_RECEIPT = new ASQEmailTemplateType("ASQ_DEPOSIT_EMAIL_RECEIPT", "_asqBankEmailReceipt");
	
	public static final ASQEmailTemplateType ASQ_BANK_TRANS_EMAIL_RECEIPT = new ASQEmailTemplateType("ASQ_BANK_TRANS_EMAIL_RECEIPT", "_asqBankTransferEmailReceipt");;

	public ASQEmailTemplateType(String argName, String argTemplateKey) {
		super(argName, argTemplateKey);
	}

}
