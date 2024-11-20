package asq.pos.register.sale;

import javax.inject.Inject;

import com.micros.xstore.config.listview.ListViewColumnType;

import dtv.i18n.FormattableFactory;
import dtv.i18n.IFormattable;
import dtv.pos.common.SysConfigSettingFactory;
import dtv.pos.framework.ui.listview.CellContent;
import dtv.pos.framework.ui.listview.ICellDataHandler;

public class AsqStockOnHandHeader implements ICellDataHandler {

	@Inject
	private SysConfigSettingFactory _settingsFactory;
	@Inject
	private FormattableFactory _formattables;

	@Override
	public CellContent buildCellContent(ListViewColumnType arg0, Object argModel) {
		IFormattable soh = this._formattables.getSimpleFormattable("_asqSOH");
		String value = _settingsFactory.getString(new String[] { "StockOnHold---Enable" });
		if (value != null && value.equalsIgnoreCase("true")) {
			return new CellContent(soh.toString());
		} else
			return new CellContent();
	}
}
