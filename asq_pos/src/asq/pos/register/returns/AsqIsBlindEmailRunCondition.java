/**
 * 
 */
package asq.pos.register.returns;

import javax.inject.Inject;

import asq.pos.common.AsqConfigurationMgr;
import dtv.pos.framework.op.AbstractRunCondition;
import dtv.pos.framework.scope.TransactionScope;
import dtv.pos.register.returns.ReturnType;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import dtv.xst.dao.trn.IPosTransaction;

/**
 * @author SA20547171
 *
 */
public class AsqIsBlindEmailRunCondition extends AbstractRunCondition {

	@Inject
	private AsqConfigurationMgr _sysConfig;

	@Inject
	private TransactionScope _transactionScope;

	@Override
	protected boolean shouldRunImpl() {
		if (_sysConfig.isBlindReturnEmailApproved()) {
			IPosTransaction trans = _transactionScope.getTransaction();

			for (ISaleReturnLineItem line : trans.getLineItems(ISaleReturnLineItem.class)) {
				if (!line.getVoid() && line.getReturn() && (ReturnType.BLIND.matches(line.getReturnTypeCode())
						|| ReturnType.UNVERIFIED.matches(line.getReturnTypeCode()))) {
					return true;
				}
			}
		}
		return false;
	}

}
