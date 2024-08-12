/**
 * 
 */
package asq.pos.common;

import dtv.pos.common.SysConfigSettingFactory;
import com.micros.xstore.config.ISingleElementConfigMgr;
import com.micros.xstore.config.settings.SettingType;
import com.micros.xstore.config.settings.SysConfig;

/**
 * @author RA20221457
 *
 */
public class AsqSysConfigSettingFactory extends SysConfigSettingFactory {

	/**
	 * Constructs a <code>LevSysConfigSettingFactory</code>.
	 * 
	 * @param argConfigMgr
	 */
	public AsqSysConfigSettingFactory(ISingleElementConfigMgr<SysConfig, SettingType> argConfigMgr) {
		super(argConfigMgr);
	}

	void initializeAsqConfigurationMgr() {
		AsqConfigurationMgr.setConfigSettingFactory(this);
	}
}
