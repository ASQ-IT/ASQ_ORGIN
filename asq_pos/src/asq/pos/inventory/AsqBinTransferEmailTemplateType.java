/**
 * 
 */
package asq.pos.inventory;

import dtv.pos.framework.email.EmailTemplateType;

/**
 * @author RA20221457
 *
 */
public class AsqBinTransferEmailTemplateType extends EmailTemplateType{
	
	public static final AsqBinTransferEmailTemplateType ASQ_BIN_TRANSFER_EMAIL_RECEIPT =
	 new AsqBinTransferEmailTemplateType("ASQ_BIN_TRANSFER_EMAIL_RECEIPT", "_asqBinTransferApprovalEmailReceipt");

	public AsqBinTransferEmailTemplateType(String argName, String argTemplateKey) {
		super(argName, argTemplateKey);
	}

}
