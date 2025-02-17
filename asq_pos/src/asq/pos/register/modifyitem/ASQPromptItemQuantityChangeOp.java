package asq.pos.register.modifyitem;

import javax.inject.Inject;

import asq.pos.register.sale.AsqHelper;
import dtv.i18n.IFormattable;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.ui.prompt.PromptOverrideProperties;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.register.modifyitem.PromptItemQuantityChangeOp;
import dtv.xst.dao.trl.ISaleReturnLineItem;

public class ASQPromptItemQuantityChangeOp extends PromptItemQuantityChangeOp {

	@Inject
	AsqHelper asqHelper;

	@Override
	public IOpResponse getPromptResponse(IXstEvent argEvent, String argKey, IFormattable[] argPromptArgs) {
		ISaleReturnLineItem lineItem = getScopedValue(ValueKeys.CURRENT_SALE_LINE);
		if (asqHelper.isTOLAItem(lineItem)) {
			PromptOverrideProperties overrideProps = new PromptOverrideProperties();
			overrideProps.setMaxFractionalDigits(0);
			overrideProps.setDefaultValue(lineItem.getQuantity().intValue());
			return this.HELPER.getPromptResponse(argKey, overrideProps, argPromptArgs);
		}
		return super.getPromptResponse(argEvent, argKey, argPromptArgs);
	}

}
