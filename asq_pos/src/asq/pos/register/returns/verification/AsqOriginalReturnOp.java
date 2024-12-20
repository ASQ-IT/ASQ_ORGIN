/**
 * 
 */
package asq.pos.register.returns.verification;

import dtv.pos.common.ValueKeys;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trl.IRetailTransaction;

/**
 * @author SA20547171
 *
 */
public class AsqOriginalReturnOp extends Operation{

	@Override
	public IOpResponse handleOpExec(IXstEvent var1) {
		IRetailTransaction rtrans = getScopedValue(ValueKeys.CURRENT_ORIGINAL_TRANSACTION);
		 _transactionScope.setValue(ValueKeys.CURRENT_ORIGINAL_TRANSACTION, rtrans);
		return HELPER.completeResponse();
	}

}
