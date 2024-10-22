package asq.pos.common;

import dtv.pos.common.ConfigurationMgr;
import dtv.pos.common.SysConfigSettingFactory;

/**
 * This class extends Xstore Standard Configuration Manager to check the
 * customer availability parameter and Value for calculating the points for Earn
 * API to customer
 * 
 * @author RA20221457
 */
public class AsqConfigurationMgr extends ConfigurationMgr {

	private static SysConfigSettingFactory _settingsFactory;

	/**
	 * Parameter to check the customer availability
	 * 
	 * @return true/false
	 */
	public static boolean getSTCCustomerAvailable() {
		String value = _settingsFactory.getString(new String[] { "STCCustomerEarnPoints---AddSTCPointsToCustomerOnTender" });
		if (value != null && value.equalsIgnoreCase("true")) {
			return true;
		}
		return false;
	}

	static void setConfigSettingFactory(AsqSysConfigSettingFactory argFactory) {
		_settingsFactory = argFactory;
	}

	/**
	 * Parameter to calculate the Earn points
	 * 
	 * @return value for calculation
	 */
	public static int getSTCPointsCalculation() {
		return _settingsFactory.getInt(new String[] { "STCCustomerEarnPoints---PointsCalculation" });
	}
	
	
	/**
	 * Planet Tax Merchant
	 */
	public String getPlanetMerchantIdNumber() {
		return _settingsFactory.getString(new String[] {"PlanetTaxFree---MerchantIdNumber"});
	}
	
	/**
	 * Planet Tax Terminal
	 */
	public  String getPlanetTerminalNumber() {
		return _settingsFactory.getString(new String[] {"PlanetTaxFree---TerminalNumber"});
	}
	
	/**
	 * Planet Tax Doc Type
	 */
	public String getPlanetType() {
		return _settingsFactory.getString(new String[] {"PlanetTaxFree---Type"});
	}
}
