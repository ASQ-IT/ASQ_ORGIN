package asq.pos.employee.condition;

import javax.inject.Inject;

import dtv.docbuilding.conditions.AbstractInvertableCondition;
import dtv.pos.common.SysConfigSettingFactory;

public class AsqEmpCommissionCondition extends AbstractInvertableCondition {
	@Inject
	private SysConfigSettingFactory _settingsFactory;

	@Override
	protected boolean conditionMetImpl(Object arg0) {
		String value = _settingsFactory.getString(new String[] { "EmployeeCommision---Enable" });
		if (value != null && value.equalsIgnoreCase("true")) {
			return true;
		} else
			return false;
	}
}
