/**
 * 
 */
package asq.pos.bnpl.tabby.tender.op;

import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.iframework.form.IEditModel;

/**
 * @author RA20221457
 *
 */
public class AsqBnplTabbyTenderOp extends AbstractFormOp<IEditModel>{

	@Override
	protected IEditModel createModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getFormKey() {
		return "ASQ_CAP_CUST_MOBILE_NO";
	}

}
