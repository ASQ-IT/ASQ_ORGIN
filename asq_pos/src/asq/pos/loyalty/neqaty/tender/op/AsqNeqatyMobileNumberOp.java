/**
 * 
 */
package asq.pos.loyalty.neqaty.tender.op;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dtv.pos.framework.op.AbstractFormOp;

/**
 * @author RA20221457
 *
 */
public class AsqNeqatyMobileNumberOp extends AbstractFormOp<AsqNeqatyMobileNumberEditModel> {

	private static final Logger LOG = LogManager.getLogger(AsqNeqatyMobileNumberOp.class);

	/**
	 * This class extends the Xstore Standard form class to handle all actions
	 * related to Mobile number field
	 */

	@Override
	protected AsqNeqatyMobileNumberEditModel createModel() {
		return new AsqNeqatyMobileNumberEditModel();
	}

	@Override
	protected String getFormKey() {
		return "ASQ_CAP_CUST_MOBILE_NO";
	}


}
