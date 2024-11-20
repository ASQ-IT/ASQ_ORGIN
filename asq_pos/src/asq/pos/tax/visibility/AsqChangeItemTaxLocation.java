package asq.pos.tax.visibility;

import javax.inject.Inject;

import dtv.pos.common.SysConfigAccessor;
import dtv.pos.common.SysConfigSettingFactory;
import dtv.pos.framework.action.access.AbstractVisibilityRule;
import dtv.pos.iframework.visibilityrules.AccessLevel;
import dtv.pos.iframework.visibilityrules.IAccessLevel;

public class AsqChangeItemTaxLocation extends AbstractVisibilityRule {
	@Inject
	private SysConfigAccessor _sysConfig;
	@Inject
	private SysConfigSettingFactory _settingsFactory;

	@Override
	protected IAccessLevel checkVisibilityImpl() throws Exception {
		String value = _settingsFactory
				.getString(new String[] { "Tax---DisableChangeTaxItemLocation" });
		if (value != null && value.equalsIgnoreCase("true")) {
			return AccessLevel.DENIED;
		}
		return AccessLevel.GRANTED;
	}

}
