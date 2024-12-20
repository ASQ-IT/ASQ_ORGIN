package asq.pos.common;

import javax.mail.internet.InternetAddress;

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
		return _settingsFactory.getString(new String[] {"ASQ---PlanetTaxFree---MerchantIdNumber"});
	}
	
	/**
	 * Planet Tax Terminal
	 */
	public  String getPlanetTerminalNumber() {
		return _settingsFactory.getString(new String[] {"ASQ---PlanetTaxFree---TerminalNumber"});
	}
	
	/**
	 * Planet Tax Doc Type
	 */
	public String getPlanetType() {
		return _settingsFactory.getString(new String[] {"ASQ---PlanetTaxFree---Type"});
	}

	/**
	 * Blind Return Email Approval
	 */
	public boolean isBlindReturnEmailApproved() {
		return _settingsFactory.getBoolean(new String[] {"ASQ---ItemReturn---EmailApproval"});
	}

	public String getReturnApprovalEmailOTP() {
		return _settingsFactory.getString(new String[] {"ASQ---ReturnApprovalEmailOTP---To"});
	}
	
	public int getReturnOfflineEmailOTP() {
		return _settingsFactory.getInt(new String[] {"ASQ---ReturnOfflineOTP---To"});
	}
	
	/**
	 * Bin Transfer Email Approval
	 */
	public boolean isBinTransferEmailApproved() {
		return _settingsFactory.getBoolean(new String[] { "ASQ---BinTransfer---EmailApproval" });
	}

	public String getBinTransferApprovalEmailOTP() {
		return _settingsFactory.getString(new String[] { "ASQ---BinTransferApprovalEmailOTP---To" });
	}

	public int getBinTransferOfflineEmailOTP() {
		return _settingsFactory.getInt(new String[] { "ASQ---BinTransferOfflineOTP---To" });
	}

	public String getPlanetMerchantIdItem() {
		// TODO Auto-generated method stub
		return _settingsFactory.getString(new String[] {"ASQ---PlanetTaxFree---MerchantIdItem"});
	}

	public String getstoreTransferApprovalEmailOTP() {
		// TODO Auto-generated method stub
		return _settingsFactory.getString(new String[] { "ASQ---StoreTransferApprovalEmailOTP---To"  });
	}
}
