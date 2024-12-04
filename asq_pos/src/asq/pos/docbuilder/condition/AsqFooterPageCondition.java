package asq.pos.docbuilder.condition;

import javax.inject.Inject;

import dtv.docbuilding.conditions.AbstractInvertableCondition;
import dtv.pos.common.SysConfigSettingFactory;

public class AsqFooterPageCondition extends AbstractInvertableCondition {
	@Inject
	private SysConfigSettingFactory _settingsFactory;

	@Override
	protected boolean conditionMetImpl(Object arg0) {
		String value = _settingsFactory.getString(new String[] { "Footer_disclaimer---Enable" });
		if (value != null && value.equalsIgnoreCase("true")) {
			return true;
		} else
			return false;
	}
}
