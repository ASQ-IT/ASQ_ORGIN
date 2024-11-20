package asq.pos.registery.sale;

import dtv.pos.common.OpChainKey;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.register.sale.PromptItemScanOp;

public class AsqPromptItemScanOp extends PromptItemScanOp {
	   protected IOpResponse getTenderingResponse(final IXstEvent argEvent) {
		                 return this.HELPER.getStartChainResponse(OpChainKey.valueOf("CHECK_SALE_COMPLETE1"));
		             }
}
