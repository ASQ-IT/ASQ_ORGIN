package asq.pos.register.sale;

import javax.inject.Inject;

import com.micros.xstore.config.listview.ListViewColumnType;

import asq.pos.common.AsqSysConfigSettingFactory;
import dtv.pos.common.ConfigurationMgr;
import dtv.pos.common.SysConfigSettingFactory;
import dtv.pos.framework.ui.listview.CellContent;
import dtv.pos.framework.ui.listview.ICellDataHandler;
import dtv.pos.iframework.security.StationState;
import dtv.xst.dao.inv.IStockLedger;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import oracle.retail.xstore.inv.IInventoryStockAdjuster;

public class AsqStockOnHand implements ICellDataHandler {
	@Inject
	private IInventoryStockAdjuster _invStockAdjuster;
	@Inject
	private StationState _stationState;
	@Inject
	private SysConfigSettingFactory _settingsFactory;
	@Override
	public CellContent buildCellContent(ListViewColumnType arg0, Object argModel) {
		String value = _settingsFactory.getString(new String[] { "StockOnHold---Enable" });
		if (value != null && value.equalsIgnoreCase("true")) {
			if (argModel instanceof ISaleReturnLineItem) {
				ISaleReturnLineItem item = (ISaleReturnLineItem) argModel;
				IStockLedger ledger = this._invStockAdjuster.getStockLedger(item.getItemId(),
						ConfigurationMgr.getDefaultInventoryLocationId(), "ON_HAND",
						this._stationState.getRetailLocationId());
				if(ledger.getUnitcount()!=null) {
				return new CellContent(ledger.getUnitcount().toString());
				}
				else
				{
					return new CellContent();
				}
			} 
		}
		return new CellContent();
	}
}
