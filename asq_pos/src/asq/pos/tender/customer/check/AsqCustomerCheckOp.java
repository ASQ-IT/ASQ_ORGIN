/**
 * 
 */
package asq.pos.tender.customer.check;

import dtv.pos.common.OpChainKey;
import dtv.pos.common.SysConfigAccessor;
import dtv.pos.customer.CustomerUtil;
import dtv.pos.framework.op.Operation;
import dtv.pos.framework.scope.TransactionScope;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trl.IRetailTransaction;
import javax.inject.Inject;

/**
 * @author SA20547171
 *
 */
public class AsqCustomerCheckOp extends Operation {

	@Inject
	protected TransactionScope _transactionScope;

	@Inject
	private SysConfigAccessor _sysConfig;

	@Override
	public IOpResponse handleOpExec(IXstEvent var1) {
		boolean customerRequiredAtLogin = _sysConfig.isCustomerRequiredOnLogin();
		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("CUST_ASSOCIATION"),
				CustomerUtil.getCustomerRequiredEvent(customerRequiredAtLogin));
	}

	@Override
	public boolean isOperationApplicable() {
		IRetailTransaction trans = (IRetailTransaction) _transactionScope.getTransaction();
		return trans != null && trans.getCustomerParty() == null;

	}

}
