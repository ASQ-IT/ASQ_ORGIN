package asq.pos.register.returns.verification;

import javax.inject.Inject;

import asq.pos.register.sale.AsqHelper;
import dtv.i18n.IFormattable;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.ui.prompt.PromptOverrideProperties;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.register.returns.verification.PromptReturnVerificationItemQuantityOp;
import dtv.xst.dao.trl.ISaleReturnLineItem;

public class ASQPromptReturnVerificationItemQuantityOp extends PromptReturnVerificationItemQuantityOp {

	@Inject
	AsqHelper asqHelper;

	@Override
	public IOpResponse getPromptResponse(IXstEvent argEvent, String argKey, IFormattable[] argPromptArgs) {
		ISaleReturnLineItem returnLine = getScopedValue(ValueKeys.ORIGINAL_SALE_LINE);
		if (asqHelper.isTOLAItem(returnLine)) {
			PromptOverrideProperties overrideProps = new PromptOverrideProperties();
			overrideProps.setMaxFractionalDigits(0);
			overrideProps.setDefaultValue(returnLine.getQuantity());
			return this.HELPER.getPromptResponse(argKey, overrideProps, argPromptArgs);
		}
		return super.getPromptResponse(argEvent, argKey, argPromptArgs);
	}

}
