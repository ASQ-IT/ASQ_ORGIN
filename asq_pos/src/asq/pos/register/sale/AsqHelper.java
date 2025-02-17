package asq.pos.register.sale;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.python.netty.util.internal.StringUtil;

import asq.pos.common.AsqConstant;
import asq.pos.employee.commission.AsqEmpCommissionQueryResult;
import dtv.data2.access.DataFactory;
import dtv.data2.access.IPersistable;
import dtv.data2.access.IQueryKey;
import dtv.data2.access.QueryKey;
import dtv.pos.common.ConfigurationMgr;
import dtv.pos.common.ITransactionHelper;
import dtv.pos.common.SysConfigSettingFactory;
import dtv.pos.iframework.validation.IValidationResult;
import dtv.pos.iframework.validation.SimpleValidationResult;
import dtv.util.DateRange;
import dtv.util.DtvDate;
import dtv.util.NumberUtils;
import dtv.xst.dao.inv.IStockLedger;
import dtv.xst.dao.inv.impl.StockLedgerModel;
import dtv.xst.dao.itm.IItem;
import dtv.xst.dao.trl.ISaleReturnLineItem;

public class AsqHelper {

	@Inject
	protected SysConfigSettingFactory sysConfig;

	private static final IQueryKey<AsqRecievingQueryResult> ASQ_IN_PROGRESS_RECEIVING = new QueryKey<AsqRecievingQueryResult>(AsqConstant.ASQ_RECEIVING_REG_CLOSE_QRY, AsqRecievingQueryResult.class);

	private static final IQueryKey<AsqEmpCommissionQueryResult> ASQ_EMPLOYEE_MONTHLY_NETSALE_COMMISSION_QRY = new QueryKey<AsqEmpCommissionQueryResult>(AsqConstant.ASQ_EMPLOYEE_MONTHLY_NETSALE_COMMISSION_QRY,
			AsqEmpCommissionQueryResult.class);

	private static final IQueryKey<AsqEmpCommissionQueryResult> ASQ_GET_EMPLOYEE_ITEM_COMM_NETSALE_QRY = new QueryKey<AsqEmpCommissionQueryResult>(AsqConstant.ASQ_GET_EMPLOYEE_ITEM_COMM_NETSALE_QRY,
			AsqEmpCommissionQueryResult.class);

	public RoundingMode getSystemRoundingMode() {
		RoundingMode mode = NumberUtils.getRoundingModeForName(sysConfig.getString(new String[] { "CurrencyRounding---RoundingMode" }));
		if (mode == null) {
			return RoundingMode.HALF_DOWN;
		}
		return mode;
	}

	public IValidationResult isReceivingInProgress() {
		Map<String, Object> asqRecievingInProgress = new HashMap<String, Object>();
		List<AsqRecievingQueryResult> results = DataFactory.getObjectByQueryNoThrow(ASQ_IN_PROGRESS_RECEIVING, asqRecievingInProgress);
		if (null != results && !results.isEmpty()) {
			StringBuilder shipingDoc = new StringBuilder();
			StringBuilder shipingOty = new StringBuilder();
			for (int i = 0; i < results.size(); i++) {
				AsqRecievingQueryResult result = results.get(i);
				shipingDoc = shipingDoc.append(result.getINVCTL_DOCUMENT_ID());
				shipingOty = shipingOty.append(result.getEXPECTED_COUNT().subtract(result.getPOSTED_COUNT()));
				if (i + 1 != results.size()) {
					shipingDoc.append(StringUtil.COMMA);
					shipingOty.append(StringUtil.COMMA);
				}
			}
			return SimpleValidationResult.getFailed(AsqConstant.ASQ_RECEIVING_REG_CLOSE_MSG, shipingDoc.toString(), shipingOty.toString());
		}
		return IValidationResult.SUCCESS;
	}

	public List<AsqEmpCommissionQueryResult> getEmployeeMonthlyCommissionOnNetSale(int locationId, DateRange argDateRange) {
		if (null != argDateRange) {
			Map<String, Object> asqEmpCommission = new HashMap<String, Object>();
			asqEmpCommission.put("argOrgbusinessStartDate", argDateRange.getStartDate());
			asqEmpCommission.put("argOrgbusinessEndDate", argDateRange.getEndDate());
			asqEmpCommission.put("argOrganizationId", ConfigurationMgr.getOrganizationId());
			asqEmpCommission.put("argRetailLocId", Integer.valueOf(locationId));

			return DataFactory.getObjectByQueryNoThrow(ASQ_EMPLOYEE_MONTHLY_NETSALE_COMMISSION_QRY, asqEmpCommission);
		}
		return null;
	}

	public List<AsqEmpCommissionQueryResult> getEmployeeItemCommissionOnNetSale(int locationId, DtvDate bussinessDate) {
		Map<String, Object> asqEmpCommission = new HashMap<String, Object>();
		asqEmpCommission.put("argOrganizationId", ConfigurationMgr.getOrganizationId());
		asqEmpCommission.put("argRetailLocId", Integer.valueOf(locationId));
		asqEmpCommission.put("argOrgbusinessDate", bussinessDate);
		return DataFactory.getObjectByQueryNoThrow(ASQ_GET_EMPLOYEE_ITEM_COMM_NETSALE_QRY, asqEmpCommission);
	}

	public IItem getSuperParentItem(IItem item) {
		IItem itemTola = null;
		if (null != item.getItemOptions() && item.getItemOptions().size() > 0) {
			String measureCode = item.getItemOptions().get(0).getUnitOfMeasureCode();
			if (measureCode != null && measureCode.contains(AsqConstant.ASQ_TOLA)) {
				if (null != item.getParentItem() && null != item.getParentItem().getParentItem()) {
					itemTola = item.getParentItem().getParentItem();
				}
			}
		}
		return itemTola;
	}

	public boolean isTOLAItem(ISaleReturnLineItem argLineItem) {
		if (null != argLineItem && null != argLineItem.getItem()) {
			String tolaItem = argLineItem.getItem().getDimension1();
			if (null != tolaItem && tolaItem.contains(AsqConstant.ASQ_TOLA)) {
				return true;
			}
		}
		return false;
	}

	public void removeAndAddStockLedger(ITransactionHelper argTransactionHelper, IStockLedger ledger) {
		// remove the existing Persistables
		if (null != argTransactionHelper.getPersistables()) {
			IPersistable[] transPersist = argTransactionHelper.getPersistables().getObjects();
			for (IPersistable ledgerPers : transPersist) {
				if (ledgerPers instanceof StockLedgerModel) {
					StockLedgerModel led = (StockLedgerModel) ledgerPers;
					if (led.getItemId().equalsIgnoreCase(ledger.getItemId())) {
						argTransactionHelper.getPersistables().removeObject(led);
					}
				}
			}
		}
		argTransactionHelper.addPersistable(ledger);
	}

}
