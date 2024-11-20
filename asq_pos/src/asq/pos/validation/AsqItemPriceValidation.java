package asq.pos.validation;

import java.math.BigDecimal;

import dtv.pos.common.ValueKeys;
import dtv.pos.framework.validation.AbstractValidationRule;
import dtv.pos.iframework.validation.IValidationResult;
import dtv.pos.iframework.validation.SimpleValidationResult;
import dtv.xst.dao.trl.ISaleReturnLineItem;

public class AsqItemPriceValidation extends AbstractValidationRule {

	@Override
	public IValidationResult validate() {
		BigDecimal enteredPrice = BigDecimal.ZERO;
		Boolean flag = false;
		ISaleReturnLineItem lineItem = this.getScopedValue(ValueKeys.CURRENT_SALE_LINE);
		if (lineItem != null && !lineItem.getAttachedItemFlag() && !lineItem.getDiscounted() && !"DEFAULT".equalsIgnoreCase(lineItem.getMerchLevel1Id())) {
			if (this.getScopedValue(ValueKeys.ENTERED_ITEM_PRICE) != null) {
				enteredPrice = this.getScopedValue(ValueKeys.ENTERED_ITEM_PRICE);
				if (enteredPrice.compareTo(BigDecimal.ZERO) == 0) {
					flag = true;
				}
			}
			if (lineItem.getExtendedAmount().compareTo(BigDecimal.ZERO) == 0 || flag) {
				return SimpleValidationResult.getFailed("_priceValidation");
			}
			return SimpleValidationResult.SUCCESS;
		}
		return SimpleValidationResult.SUCCESS;
	}
}
