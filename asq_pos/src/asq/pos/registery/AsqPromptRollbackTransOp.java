package asq.pos.registery;

import dtv.pos.common.OpChainKey;
import dtv.pos.register.PromptRollbackTransOp;

public class AsqPromptRollbackTransOp extends PromptRollbackTransOp {
	
	 protected OpChainKey getRollbackChainKey() {
	      return OpChainKey.valueOf("ROLLBACK_TRANS_START1");// 92
	   }
}
