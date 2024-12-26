package asq.pos.loyalty;

import asq.pos.common.AsqValueKeys;
import dtv.pos.framework.op.AbstractPromptOp;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;

public class AsqPromptLoyalty extends AbstractPromptOp {

	@Override
	public String getDefaultPromptKey() {
		return "PROMPT_ASQ_LOTALITY";
	}

	@Override
	public IOpResponse handlePromptResponse(IXstEvent var1) {
		if (var1.getName().equalsIgnoreCase("Yes")) {
			_transactionScope.setValue(AsqValueKeys.ASQ_LOYALTY, true);
			return HELPER.completeResponse();
		}
		return HELPER.completeResponse();
	}
}
