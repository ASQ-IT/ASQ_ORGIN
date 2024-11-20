package asq.pos.registery;

import javax.inject.Inject;

import dtv.pos.common.OpChainKey;
import dtv.pos.common.SysConfigAccessor;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.register.ResetAfterTransactionOp;

public class AsqResetAfterTransactionOp extends ResetAfterTransactionOp {
	  @Inject
	   private SysConfigAccessor _sysConfig;
	   public IOpResponse handleOpExec(IXstEvent argEvent) {
	      String chain = this._sysConfig.getSaleEndingChain();// 30
	      OpChainKey key = chain == null ? OpChainKey.valueOf("PRE_SALE_TRANSACTION1") : OpChainKey.valueOf(chain);// 32
	      return this.HELPER.getStartChainResponse(key);// 34
	   }
}
