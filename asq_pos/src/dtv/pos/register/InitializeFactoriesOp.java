package dtv.pos.register;

import dtv.hardware.IHardwareMgr;
import dtv.pos.common.ModelKeys;
import dtv.pos.common.SysConfigAccessor;
import dtv.pos.common.TransactionFactory;
import dtv.pos.framework.op.Operation;
import dtv.pos.framework.scope.WorkstationScope;
import dtv.pos.framework.tax.ITaxHelper;
import dtv.pos.iframework.IModeController;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.ui.model.IStationModel;
import dtv.pos.tender.TenderHelper;
import dtv.test.ICaptureHarness;
import dtv.test.ITestHarness;
import dtv.util.DtvDate;
import dtv.util.IDateProvider;
import dtv.util.sequence.SequenceFactory;
import dtv.xst.dao.tnd.TenderCategory;
import javax.inject.Inject;
import javax.inject.Provider;
import oracle.retail.xstore.inv.IInventoryMovementServices;

public class InitializeFactoriesOp extends Operation {
	@Inject
	private ICaptureHarness _captureHarness;

	@Inject
	private ITestHarness _testHarness;

	@Inject
	private TenderHelper _tenderHelper;

	@Inject
	private ITaxHelper _taxHelper;

	@Inject
	private IHardwareMgr _hardwareMgr;

	@Inject
	private Provider<IInventoryMovementServices> _invMovementServices;

	@Inject
	private SysConfigAccessor _sysConfig;

	public IOpResponse handleOpExec(IXstEvent argEvent) {
		this._taxHelper.preload(this._stationState.getRetailLocationId());
		SequenceFactory.getSequenceTypes();
		SequenceFactory.peekNextLongValue("TRANSACTION", new Object[0]);
		new DtvDate();
		TransactionFactory.getInstance();
		this._invMovementServices.get();
		this._tenderHelper.getAvailableTendersForCategories((IDateProvider) this._transDateProvider,
				TenderCategory.getAll(), Long.valueOf(this._stationState.getWorkstationId()));
		//this._hardwareMgr.getActiveCashdrawer();
		if (this._captureHarness.isEnabled()) {
			this._captureHarness.initConsole(WorkstationScope.getKeyFromCurrentThread());
		} else if (this._testHarness.isEnabled()) {
			this._testHarness.initConsole(WorkstationScope.getKeyFromCurrentThread());
		}
		IModeController mode = (IModeController) this._modeProvider.get();
		IStationModel stationModel = mode.getStationModel();
		stationModel.getModel(ModelKeys.CURRENT_TRANSACTION);
		if (this._sysConfig.isDualScreenEnabled())
			stationModel.getModel(ModelKeys.CUSTOMER_CURRENT_TRANSACTION);
		return this.HELPER.completeResponse();
	}
}