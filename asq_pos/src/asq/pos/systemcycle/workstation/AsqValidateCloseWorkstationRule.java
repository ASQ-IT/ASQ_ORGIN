package asq.pos.systemcycle.workstation;

import javax.inject.Inject;

import asq.pos.register.sale.AsqHelper;
import dtv.pos.iframework.validation.IValidationResult;
import dtv.pos.systemcycle.workstation.ValidateCloseWorkstationRule;

public class AsqValidateCloseWorkstationRule extends ValidateCloseWorkstationRule {

	@Inject
	AsqHelper helper;

	@Override
	public IValidationResult validate() {
		IValidationResult validationResult = super.validate();
		if (validationResult.isValid()) {
			validationResult = helper.isReceivingInProgress();
		}
		return validationResult;
	}

}
