package dtv.hardware;

import dtv.docbuilding.IPrinterTargetInfo;
import dtv.hardware.biometric.IDtvBiometricDevice;
import dtv.hardware.cashdrawer.ActiveCashDrawerContainer;
import dtv.hardware.cashdrawer.IDtvCashDrawer;
import dtv.hardware.cashdrawer.IpCashDrawerException;
import dtv.hardware.cashdrawer.ip.ApgCashdrawerUtils;
import dtv.hardware.cashdrawer.ip.IDtvIpCashDrawer;
import dtv.hardware.config.DeviceConfig;
import dtv.hardware.custdisplay.DtvMultiCustDisplay;
import dtv.hardware.custdisplay.IDtvCustDisplay;
import dtv.hardware.electronicjournal.IDtvElectronicJournal;
import dtv.hardware.fiscal.IDtvFiscalDevice;
import dtv.hardware.inputrules.EventDiscriminator;
import dtv.hardware.inputrules.IEventDiscriminator;
import dtv.hardware.keyboard.KeyboardInput;
import dtv.hardware.labelprinting.IDtvLabelPrinter;
import dtv.hardware.labelprinting.config.elements.ILabel;
import dtv.hardware.lights.IDtvLights;
import dtv.hardware.log.HardwareLogModel;
import dtv.hardware.micr.IDtvMicr;
import dtv.hardware.micr.MicrRead;
import dtv.hardware.msr.IDtvJposMsr;
import dtv.hardware.msr.MsrSwipe;
import dtv.hardware.posprinting.IDtvPosPrinter;
import dtv.hardware.posprinting.IPosPrintStation;
import dtv.hardware.posprinting.IPrintQueue;
import dtv.hardware.posprinting.PageBreakException;
import dtv.hardware.posprinting.PosPrintJob;
import dtv.hardware.posprinting.PosPrinterException;
import dtv.hardware.posprinting.RcptStack;
import dtv.hardware.ppad.IDtvJposPinPad;
import dtv.hardware.ppad.IDtvPinPad;
import dtv.hardware.ppad.PinEntry;
import dtv.hardware.question.IDtvQuestionDevice;
import dtv.hardware.scale.IDtvPricingScaleDevice;
import dtv.hardware.service.JposServiceFrame;
import dtv.hardware.service.posprinter.Log4jRcptLogger;
import dtv.hardware.sigcap.IDtvJposSigCap;
import dtv.hardware.sigcap.IDtvSigCap;
import dtv.hardware.sigcap.ISignature;
import dtv.hardware.startup.StartupDialogManager;
import dtv.hardware.startup.StartupMessageType;
import dtv.hardware.types.DtvHardwareEventType;
import dtv.hardware.types.HardwareFamilyType;
import dtv.hardware.types.HardwareType;
import dtv.i18n.FormattableFactory;
import dtv.i18n.Translator;
import dtv.logbuilder.LogBuilder;
import dtv.pos.framework.scope.WorkstationScope;
import dtv.pos.framework.scope.WorkstationScopeKey;
import dtv.pos.iframework.XstApplication;
import dtv.pos.iframework.event.IExitEvent;
import dtv.pos.iframework.event.IExitListener;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.hardware.IHardwareType;
import dtv.pos.iframework.hardware.IInput;
import dtv.util.ArrayUtils;
import dtv.util.DtvMultiStorage;
import dtv.util.ObjectUtils;
import dtv.util.StringUtils;
import dtv.util.crypto.EncString;
import dtv.util.temp.InjectionHammer;
import dtv.xst.dao.ctl.IIpCashDrawerDevice;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import javax.inject.Inject;
import javax.inject.Provider;
import jpos.JposException;
import jpos.events.ErrorEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;

public class HardwareMgr implements IHardwareMgr {
	
	private static final Logger LOG = LogManager.getLogger(HardwareMgr.class);

	private static final boolean THREAD_HARDWARE_STARTUP = Boolean.getBoolean("dtv.hardware.HardwareMgr.threadStartup");

	private static final Class<?>[] DEVICE_CTOR_CLASSES = new Class[]{HardwareType.class};

	private static final Class<?>[] DEVICE_CTOR_CLASSES_LEGACY = new Class[]{IHardwareMgr.class, HardwareType.class};

	private static final Object STATUS_UPDATE = "STATUS_UPDATE";

	protected DtvMultiCustDisplay custDisplayMulti_;

	protected final ThreadLocal<PosPrintJob> printJob_ = new ThreadLocal<>();

	protected final ThreadGroup threadGroup_;

	@Inject
	protected FormattableFactory _formattableFactory;

	@Inject
	protected ApgCashdrawerUtils _apgCashdrawerUtils;

	protected IHardwareConfig hardwareConfig_;

	protected HardwareStartupMessages startupMessages_;

	protected Thread startupThread_;

	protected Map<String, Object> propMap_;

	protected boolean isInitialized_ = false;

	protected boolean isShuttingDown_ = false;

	protected boolean isDocumentPresent_ = false;

	protected boolean starting_ = false;

	private final BiConsumer<String, StartupMessageType> _startupMessageConsumer;

	@Inject
	private IPrintQueue _printQueue;

	@Inject
	private IHardwareFamilyTypeMgr _hardwareFamilyTypeMgr;

	@Inject
	private Provider<ActiveCashDrawerContainer> _activeCashDrawerInfo;

	private Map<IHardwareType, IDtvDevice> devicesByUse_;

	private Map<String, IDtvDevice> devicesByName_;

	private final IRcptLogger rcptLogger_ = (IRcptLogger) new Log4jRcptLogger("RCPT_LOG", 50);

	private final IExitListener exitListener_ = new IExitListener() {
		public void exiting(IExitEvent argEvent) {
			HardwareMgr.this.shutdown(false);
		}
	};

	private DtvMultiStorage<String, IDtvDevice> devicesByFamily_;

	protected HardwareMgr() {
		this.threadGroup_ = new ThreadGroup("HARDWARE");
		if (Boolean.getBoolean("dtv.hardware.StartupDialogManager.disable")) {
			this._startupMessageConsumer = ((message, type) -> {

			});
		} else {
			this._startupMessageConsumer = new StartupDialogManager();
		}
		IExitListener exitListener = getExitListener();
		if (exitListener != null)
			XstApplication.addExitListener(exitListener);
	}

	public boolean areIpCashDrawersPresent() {
		IDtvCashDrawer[] devices = getDevices("CashDrawer", IDtvCashDrawer.class);
		if (devices == null || devices.length == 0)
			return false;
		return Arrays.<IDtvCashDrawer>stream(devices).anyMatch(cd -> cd instanceof IDtvIpCashDrawer);
	}

	public <T extends IDtvDevice> T createDevice(Class<T> argDeviceClass, HardwareType<?> argHardwareType) {
		IDtvDevice iDtvDevice = null;
		Class<?> implClass = argDeviceClass;
		HardwareFamilyType<?> familyType = this._hardwareFamilyTypeMgr.forName(argHardwareType.getFamily());
		if (!ObjectUtils.isImplementing(implClass, familyType.getInterface())) {
			LOG.warn("" + implClass + " does not implement " + implClass);
			implClass = familyType.getDefaultImplClass();
		}
		T device = null;
		try {
			Method getInstanceMethod = implClass.getMethod("getInstance", new Class[]{HardwareType.class});
			iDtvDevice = (IDtvDevice) getInstanceMethod.invoke(null, new Object[]{argHardwareType});
		} catch (NoSuchMethodException | SecurityException ex) {
			LOG.debug("" + argHardwareType + ": could not obtain an instance of " + argHardwareType
					+ " using getInstance", ex);
		} catch (Exception ex) {
			LOG.error("" + argHardwareType + ": could not obtain an instance of " + argHardwareType
					+ " using getInstance", ex);
		}
		if (iDtvDevice == null)
			try {
				Constructor<?> constructor = implClass.getConstructor(DEVICE_CTOR_CLASSES);
				iDtvDevice = (IDtvDevice) constructor.newInstance(new Object[]{argHardwareType});
			} catch (NoSuchMethodException | SecurityException ex) {
				LOG.debug("" + argHardwareType + ": could not find a 1 arg constructor on " + argHardwareType, ex);
			} catch (Exception ex) {
				LOG.error("" + argHardwareType + ": could not create an instance of " + argHardwareType
						+ "using a single arg", ex);
			}
		if (iDtvDevice == null)
			try {
				Constructor<?> constructor = implClass.getConstructor(DEVICE_CTOR_CLASSES_LEGACY);
				iDtvDevice = (IDtvDevice) constructor.newInstance(new Object[]{this, argHardwareType});
			} catch (NoSuchMethodException | SecurityException ex) {
				LOG.debug("" + argHardwareType + ": could not find a 2 arg constructor on " + argHardwareType, ex);
			} catch (Exception ex) {
				LOG.error("" + argHardwareType + ": could not create an instance of " + argHardwareType
						+ "using two args", ex);
			}
		if (iDtvDevice == null) {
			LOG.error("" + argHardwareType + ": could not create instance of " + argHardwareType + "!");
		} else {
			InjectionHammer.forceAtInjectProcessing(iDtvDevice);
		}
		return (T) iDtvDevice;
	}

	public List<IIpCashDrawerDevice> discover() {
		return this._apgCashdrawerUtils.discover();
	}

	public void fireStatusUpdateEvent(IDtvDevice argSource, DtvHardwareEventType argStatus) {
		argSource.getEventor().post(STATUS_UPDATE,
				new DtvHardwareEvent((IHardwareType) argSource.getType(), argStatus, argStatus.toString(), null));
	}

	public IDtvCashDrawer getActiveCashdrawer() {
		if (((ActiveCashDrawerContainer) this._activeCashDrawerInfo.get()).getActiveDrawer() == null) {
			if (((ActiveCashDrawerContainer) this._activeCashDrawerInfo.get()).getActiveDrawerId() != null)
				try {
					return setActiveCashdrawer(
							((ActiveCashDrawerContainer) this._activeCashDrawerInfo.get()).getActiveDrawerId());
				} catch (Throwable ex) {
					LOG.error("CAUGHT EXCEPTION", ex);
				}
			IDtvCashDrawer[] cds = getDevices("CashDrawer", IDtvCashDrawer.class);
			Optional<IDtvCashDrawer> optdrawer = Arrays.<IDtvCashDrawer>stream(cds)
					.filter(cd -> !(cd instanceof IDtvIpCashDrawer)).findFirst();
			if (!optdrawer.isPresent()) {
				LOG.info("no cash drawer");
				((ActiveCashDrawerContainer) this._activeCashDrawerInfo.get())
						.setActiveDrawer(makeDefaultDeviceAdapter("CashDrawer", "", IDtvCashDrawer.class));
			} else {
				((ActiveCashDrawerContainer) this._activeCashDrawerInfo.get()).setActiveDrawer(optdrawer.get());
			}
			if (((ActiveCashDrawerContainer) this._activeCashDrawerInfo.get()).getActiveDrawer() != null)
				((ActiveCashDrawerContainer) this._activeCashDrawerInfo.get()).setActiveDrawerId(
						((ActiveCashDrawerContainer) this._activeCashDrawerInfo.get()).getActiveDrawer().getDrawerId());
		}
		return ((ActiveCashDrawerContainer) this._activeCashDrawerInfo.get()).getActiveDrawer();
	}

	public IDtvPricingScaleDevice getActiveScale() {
		IDtvDevice[] scales = getDevices("PRICING_SCALE");
		for (IDtvDevice dev : scales) {
			if (dev instanceof IDtvPricingScaleDevice)
				return (IDtvPricingScaleDevice) dev;
		}
		return null;
	}

	public IDtvCustDisplay getCustDisplay() {
		return (IDtvCustDisplay) this.custDisplayMulti_;
	}

	public <T extends IDtvDevice> T getDevice(HardwareType<?> argType, Class<T> argInterface) {
		IDtvDevice iDtvDevice = this.devicesByUse_.get(argType);
		if (iDtvDevice == null)
			iDtvDevice = makeDefaultDeviceAdapter(
					this._hardwareFamilyTypeMgr.forName(argType.getFamily(), argInterface), argType);
		return (T) iDtvDevice;
	}

	public IDtvDevice[] getDevices(String argFamily) {
		return getDevices(this._hardwareFamilyTypeMgr.forName(argFamily));
	}

	public <T extends IDtvDevice> T[] getDevices(String argFamily, Class<T> argInterface) {
		return getDevices(this._hardwareFamilyTypeMgr.forName(argFamily, argInterface));
	}

	public boolean getDocumentPresent() {
		return this.isDocumentPresent_;
	}

	public IDtvElectronicJournal getElectronicJournal(String argType) {
		IDtvElectronicJournal[] devices = getDevices("ElectronicJournal", IDtvElectronicJournal.class);
		if (devices.length == 0) {
			LOG.warn("no electronic journal");
			return makeDefaultDeviceAdapter("ElectronicJournal", argType, IDtvElectronicJournal.class);
		}
		IDtvElectronicJournal printer = getDevice(HardwareType.forUse("ElectronicJournal", argType),
				IDtvElectronicJournal.class);
		if (printer != null)
			return printer;
		LOG.warn("no electronic journal for electronic journal type [" + argType + "]");
		return makeDefaultDeviceAdapter("ElectronicJournal", argType, IDtvElectronicJournal.class);
	}

	public IEventDiscriminator getEventDiscriminator() {
		return EventDiscriminator.getInstance();
	}

	public IDtvFiscalDevice getFiscalPrinter(String argType) {
		IDtvFiscalDevice[] devices = getDevices("FISCAL", IDtvFiscalDevice.class);
		if (devices.length == 0) {
			LOG.warn("no fiscal printer");
			return makeDefaultDeviceAdapter("FISCAL", argType, IDtvFiscalDevice.class);
		}
		IDtvFiscalDevice printer = getDevice(HardwareType.forUse("FISCAL", argType), IDtvFiscalDevice.class);
		if (printer != null)
			return printer;
		LOG.warn("no fiscal printer defined for printer type [" + argType + "]");
		return makeDefaultDeviceAdapter("FISCAL", argType, IDtvFiscalDevice.class);
	}

	public IHardwareConfig getHardwareConfig() {
		return this.hardwareConfig_;
	}

	public ThreadGroup getHardwareThreadGroup() {
		return this.threadGroup_;
	}

	public IDtvLights getLights() {
		IDtvLights[] lights = getDevices("Lights", IDtvLights.class);
		if (lights.length == 0) {
			LOG.info("no Light");
			return makeDefaultDeviceAdapter("Lights", "", IDtvLights.class);
		}
		return (IDtvLights) ObjectUtils.greatest((Comparable[]) lights);
	}

	public IDtvMicr getMicr() {
		IDtvMicr[] micrs = getDevices("MICR", IDtvMicr.class);
		if (micrs.length == 0) {
			LOG.info("no MICR");
			return makeDefaultDeviceAdapter("MICR", "", IDtvMicr.class);
		}
		return (IDtvMicr) ObjectUtils.greatest((Comparable[]) micrs);
	}

	public IDtvPinPad getPinPad() {
		IDtvPinPad[] devices = getDevices("PinPad", IDtvPinPad.class);
		if (devices.length == 0) {
			LOG.info("no PINpad");
			return makeDefaultDeviceAdapter("PinPad", "", IDtvPinPad.class);
		}
		return (IDtvPinPad) ObjectUtils.greatest((Comparable[]) devices);
	}

	public IDtvPosPrinter getPOSPrinter(String argType) {
		IDtvPosPrinter printer = getDevice(HardwareType.forUse("POSPrinter", argType), IDtvPosPrinter.class);
		if (printer != null)
			return printer;
		LOG.warn("no printer defined for printer type [" + argType + "]");
		return makeDefaultDeviceAdapter("POSPrinter", argType, IDtvPosPrinter.class);
	}

	public Object getProperty(String argKey) {
		Object value = this.propMap_.get(argKey);
		if (value == null)
			LOG.warn("property [" + argKey + "] not found", new Throwable("STACK TRACE"));
		return value;
	}

	public IDtvQuestionDevice getQuestionDevice() {
		IDtvQuestionDevice[] devices = getDevices("QUESTION", IDtvQuestionDevice.class);
		if (devices.length == 0) {
			LOG.info("no question device");
			return makeDefaultDeviceAdapter("QUESTION", "", IDtvQuestionDevice.class);
		}
		return (IDtvQuestionDevice) ObjectUtils.greatest((Comparable[]) devices);
	}

	public IRcptLogger getRcptLogger() {
		return this.rcptLogger_;
	}

	public List<IHardwareType> getSelectableHardwareList(IHardwareType argType) {
		return (argType == null)
				? new ArrayList<>()
				: getHardwareConfig().getHardwareRoot().getDeviceListForType(argType);
	}

	public IDtvSigCap getSigCap() {
		IDtvSigCap[] devices = getDevices("SignatureCapture", IDtvSigCap.class);
		if (devices.length == 0) {
			LOG.warn("no signature capture device");
			return makeDefaultDeviceAdapter("SignatureCapture", "", IDtvSigCap.class);
		}
		return (IDtvSigCap) ObjectUtils.greatest((Comparable[]) devices);
	}

	public void handleErrorEvent(ISignature argSignature, IDtvJposSigCap argSourceObject, ErrorEvent argErrorEvent) {
		handleErrorEventCommon("error capturing signature [" + argSignature + "]", (IDtvJposDevice) argSourceObject,
				(IInput) argSignature, argErrorEvent);
	}

	public void handleErrorEvent(MicrRead argRead, IDtvMicr argSourceObject, ErrorEvent argErrorEvent) {
		handleErrorEventCommon("error reading MICR [" + argRead + "]", (IDtvJposDevice) argSourceObject,
				(IInput) argRead, argErrorEvent);
	}

	public void handleErrorEvent(MsrSwipe argSwipe, IDtvJposMsr argSourceObject, ErrorEvent argErrorEvent) {
		handleErrorEventCommon("error reading MSR [" + argSwipe + "]", (IDtvJposDevice) argSourceObject,
				(IInput) argSwipe, argErrorEvent);
	}

	public void handleErrorEvent(PinEntry argEntry, IDtvPinPad argSourceObject, ErrorEvent argErrorEvent) {
		handleErrorEventCommon("error reading PIN [" + argEntry + "]", (IDtvJposDevice) argSourceObject,
				(IInput) argEntry, argErrorEvent);
	}

	public void handleJposException(IDtvJposPinPad sourceObject, JposException ex) {
		try {
			handleJposExceptionImpl((IDtvJposDevice) sourceObject, ex);
		} catch (PosPrinterException pex) {
			LOG.error("CAUGHT UNEXPECTED EXCEPTION", (Throwable) pex);
		}
	}

	public void handleJposException(IPosPrintStation sourceObject, JposException ex) throws PosPrinterException {
		handleJposExceptionImpl((IDtvJposDevice) sourceObject, ex);
	}

	public boolean hasCustomerControlledDevice(String argType) {
		DeviceConfig[] entries = this.hardwareConfig_.getEntries(argType);
		for (DeviceConfig entry : entries) {
			if (entry.isCustomerControlled()) {
				IDtvDevice device = getDevice(entry.getUse(), IDtvDevice.class);
				if (device != null && device.isPresent())
					return true;
			}
		}
		return false;
	}

	public boolean hasDeviceList(IHardwareType argType) {
		return (0 != getSelectableHardwareList(argType).size());
	}

	public boolean isBiometricDevicePresent() {
		IDtvBiometricDevice[] devices = getDevices("BIOMETRIC", IDtvBiometricDevice.class);
		if (devices == null || devices.length == 0)
			return false;
		return ((IDtvBiometricDevice) ObjectUtils.greatest((Comparable[]) devices)).isPresent();
	}

	public boolean isCustomerControlledDevice(IHardwareType argType) {
		return this.hardwareConfig_.isCustomerControlledDevice(argType);
	}

	public boolean isFiscalPrinterPresent() {
		IDtvFiscalDevice[] devices = getDevices("FISCAL", IDtvFiscalDevice.class);
		if (devices == null || devices.length == 0)
			return false;
		return ((IDtvFiscalDevice) ObjectUtils.greatest((Comparable[]) devices)).isPresent();
	}

	public boolean isScalePresent() {
		IDtvPricingScaleDevice[] devices = getDevices("PRICING_SCALE", IDtvPricingScaleDevice.class);
		if (devices == null || devices.length == 0)
			return false;
		return ((IDtvPricingScaleDevice) ObjectUtils.greatest((Comparable[]) devices)).isPresent();
	}

	public boolean isSigCapPresent() {
		IDtvSigCap[] devices = getDevices("SignatureCapture", IDtvSigCap.class);
		if (devices == null || devices.length == 0)
			return false;
		return ((IDtvSigCap) ObjectUtils.greatest((Comparable[]) devices)).isPresent();
	}

	public boolean isTargetAvailable(IPrinterTargetInfo argTargetInfo) {
		IDtvPosPrinter p = getPOSPrinter(argTargetInfo.getPrinterType());
		if (p.isPresent())
			return true;
		p = getPOSPrinter(argTargetInfo.getPrinterType());
		return p.isPresent();
	}

	public void logJposException(IDtvJposDevice argSource, JposException ex) {
		logJposException("", argSource, ex);
	}

	public void logJposException(String argMsg, IDtvJposDevice argSource, JposException ex) {
		Level loggingLevel;
		if (this.isShuttingDown_) {
			loggingLevel = Level.INFO;
		} else {
			loggingLevel = Level.WARN;
		}
		if (ex.getCause() == null && ex.getOrigException() != null)
			ex.initCause(ex.getOrigException());
		String message = argMsg + ":jpos error '" + argMsg + "' with " + argSource.getJposErrorType(ex) + "("
				+ argSource.getName() + ")";
		LOG.log(loggingLevel, message, (Throwable) ex);
		if (loggingLevel.isInRange(Level.WARN, Level.FATAL))
			LogBuilder.getInstance()
					.saveLogEntry(new HardwareLogModel((IDtvDevice) argSource, message, (Throwable) ex));
	}

	public void print(List<ILabel> argLabels) throws IOException {
		((IDtvLabelPrinter) getDevice(HardwareType.forUse("LabelPrinter", "LABELS"), IDtvLabelPrinter.class))
				.print(argLabels);
	}

	public void print(RcptStack argReceipts, PageBreakException argPreviousException, boolean argUseBackup) {
		if (argReceipts == null || argReceipts.isEmpty())
			return;
		PosPrintJob job = new PosPrintJob(argReceipts, argPreviousException, argUseBackup);
		this.printJob_.set(job);
		this._printQueue.enqueueJob(job);
	}

	public void reloadCashdrawers() {
		this.devicesByFamily_.remove("CashDrawer");
		List<IDtvDevice> drawers = makeDeviceAdapters("CashDrawer");
		for (IDtvDevice device : drawers) {
			if (device instanceof IStartableDevice)
				((IStartableDevice) device).startup(null);
		}
		setActiveCashdrawer(null);
		getActiveCashdrawer();
	}

	public synchronized HardwareStartupMessages restart() {
		shutdown(true);
		this.isInitialized_ = false;
		this.startupMessages_ = null;
		startup(this.hardwareConfig_);
		this._printQueue.reset();
		return waitForStartupComplete();
	}

	public HardwareStartupMessages restart(IHardwareConfig argConfig) {
		this.hardwareConfig_ = argConfig;
		return restart();
	}

	public IDtvCashDrawer setActiveCashdrawer(String argID) throws IllegalArgumentException {
		if (argID == null) {
			((ActiveCashDrawerContainer) this._activeCashDrawerInfo.get()).clear();
			return null;
		}
		IDtvDevice[] cds = getDevices("CashDrawer");
		boolean drawerExists = Arrays.<IDtvDevice>stream(cds).map(device -> (IDtvCashDrawer) device)
				.anyMatch(drawer -> argID.equals(drawer.getDrawerId()));
		if (!drawerExists) {
			reloadCashdrawers();
			cds = getDevices("CashDrawer");
		}
		for (IDtvDevice device : cds) {
			IDtvCashDrawer drawer = (IDtvCashDrawer) device;
			if (argID.equals(drawer.getDrawerId())) {
				((ActiveCashDrawerContainer) this._activeCashDrawerInfo.get()).setActiveDrawer(drawer);
				((ActiveCashDrawerContainer) this._activeCashDrawerInfo.get()).setActiveDrawerId(argID);
				return ((ActiveCashDrawerContainer) this._activeCashDrawerInfo.get()).getActiveDrawer();
			}
		}
		((ActiveCashDrawerContainer) this._activeCashDrawerInfo.get()).setActiveDrawerId(null);
		throw new IllegalArgumentException("drawer '" + argID + "' does not exist");
	}

	public void setDocumentPresent(boolean newValue) {
		this.isDocumentPresent_ = newValue;
	}

	public synchronized void shutdown(boolean argIsRestarting) {
		try {
			if (this.isShuttingDown_)
				return;
			this.isShuttingDown_ = true;
			if (this.starting_) {
				Thread t = this.startupThread_;
				if (t != null && t.isAlive()) {
					t.interrupt();
					t.join(500L);
					if (t.isAlive())
						t.setDaemon(true);
				}
			}
			if (argIsRestarting)
				this._printQueue.shutdown();
			HardwareFamilyType[] arrayOfHardwareFamilyType = this._hardwareFamilyTypeMgr.getHardwareFamilyTypes();
			Set<IDtvDevice> uniqueDevices = new HashSet<>();
			for (int familyIndex = arrayOfHardwareFamilyType.length - 1; familyIndex >= 0; familyIndex--) {
				IDtvDevice[] devices = getDevices(arrayOfHardwareFamilyType[familyIndex]);
				for (IDtvDevice d : devices) {
					if (d instanceof IStartableDevice && !uniqueDevices.contains(d)) {
						uniqueDevices.add(d);
						shutdownDevice((IStartableDevice) d);
					}
				}
			}
		} catch (Throwable ex) {
			LOG.error("CAUGHT EXCEPTION", ex);
		} finally {
			if (argIsRestarting)
				((ActiveCashDrawerContainer) this._activeCashDrawerInfo.get()).setActiveDrawer(null);
			this.devicesByFamily_ = null;
			this.devicesByName_ = null;
			this.devicesByUse_ = null;
			this.isShuttingDown_ = false;
			this.isInitialized_ = false;
			if (!argIsRestarting)
				this.hardwareConfig_ = null;
		}
	}

	public synchronized void startup(IHardwareConfig argConfig) {
		if (!this.isInitialized_) {
			init(argConfig);
			if (!this.starting_) {
				this.starting_ = true;
				this.startupThread_ = new Thread(getHardwareThreadGroup(), new StartupRunnable(),
						createThreadName("HardwareStartupThread"));
				this.startupThread_.start();
				if (!THREAD_HARDWARE_STARTUP)
					try {
						this.startupThread_.join();
						this.starting_ = false;
					} catch (InterruptedException ex) {
						LOG.warn("INTERRUPTED!!", ex);
					}
			}
		}
	}

	public void storeProperty(String argKey, Object argValue) {
		if (this.propMap_ == null)
			this.propMap_ = new HashMap<>();
		this.propMap_.put(argKey, argValue);
	}

	public void updateIpCashdrawer(IIpCashDrawerDevice argDevice, String argOldIpAddress, long argOldTcpPort)
			throws IpCashDrawerException {
		this._apgCashdrawerUtils.updateDrawer(argDevice, argOldIpAddress, argOldTcpPort);
	}

	public void waitForPrintCompletion() throws PosPrinterException, PageBreakException {
		PosPrintJob job = this.printJob_.get();
		if (job != null) {
			job.waitForPrintCompletion();
			this.printJob_.remove();
		}
	}

	public HardwareStartupMessages waitForStartupComplete() {
		return waitForStartupComplete(0);
	}

	public HardwareStartupMessages waitForStartupComplete(int argMillis) {
		try {
			if(null != startupThread_ ) {
				this.startupThread_.join(argMillis);
				if (this.startupThread_.isAlive())
					return null;
				this.starting_ = false;
			}
		} catch (InterruptedException ex) {
			LOG.warn("INTERRUPTED!!", ex);
		}
		return this.startupMessages_;
	}

	protected <T extends IDtvDevice> T[] getDevices(HardwareFamilyType<T> argFamily) {
		Class<? extends IDtvDevice> clz = null;
		if (argFamily != null)
			clz = argFamily.getInterface();
		if (clz == null)
			clz = IDtvDevice.class;
		if (this.devicesByFamily_ == null || argFamily == null)
			return (T[]) Array.newInstance(clz, 0);
		List<IDtvDevice> list = this.devicesByFamily_.getList(argFamily.getName());
		List<T> devices = new LinkedList<>();
		for (IDtvDevice device : list) {
			if (clz.isInstance(device)) {
				devices.add((T) device);
				continue;
			}
			LOG.warn("Found hardware device " + ObjectUtils.getClassNameFromObject(device)
					+ " that does not belong to requested hardware family " + String.valueOf(argFamily)
					+ " with source description " + ((device == null) ? "null" : device.getSourceDescription()));
		}
		IDtvDevice[] arrayOfIDtvDevice = (IDtvDevice[]) Array.newInstance(clz, devices.size());
		return (T[]) devices.<IDtvDevice>toArray(arrayOfIDtvDevice);
	}

	protected IExitListener getExitListener() {
		return this.exitListener_;
	}

	protected void init(IHardwareConfig argConfig) {
		if (!this.isInitialized_) {
			this.hardwareConfig_ = argConfig;
			this.devicesByUse_ = new HashMap<>();
			this.devicesByName_ = new HashMap<>();
			this.devicesByFamily_ = new DtvMultiStorage();
			this._printQueue.reset();
			this.custDisplayMulti_ = new DtvMultiCustDisplay(
					HardwareType.forUse("CustomerDisplay", "CustDisplayMultiplexer"));
			this.startupMessages_ = null;
			for (HardwareFamilyType<?> type : this._hardwareFamilyTypeMgr.getHardwareFamilyTypes())
				makeDeviceAdapters(type.getName());
			this.isInitialized_ = true;
		}
	}

	protected void initStartupMessages() {
		if (this.startupMessages_ == null)
			this.startupMessages_ = new HardwareStartupMessages(this._startupMessageConsumer);
	}

	protected <T extends IDtvDevice> T makeDefaultDeviceAdapter(HardwareFamilyType<T> argType, HardwareType<?> argUse) {
		T device = createDevice(argType.getDefaultImplClass(), argUse);
		if (device != null) {
			this.devicesByFamily_.put(argUse.getFamily(), device);
			this.devicesByUse_.put(argUse, (IDtvDevice) device);
			if (device instanceof IStartableDevice)
				((IStartableDevice) device).setDisabled();
		}
		return device;
	}

	protected <T extends IDtvDevice> T makeDefaultDeviceAdapter(String argType, String argUse, Class<T> argInterface) {
		return makeDefaultDeviceAdapter(this._hardwareFamilyTypeMgr.forName(argType, argInterface),
				HardwareType.forUse(argType, argUse));
	}

	protected IDtvDevice makeDeviceAdapter(DeviceConfig argEntry) {
		return makeDeviceAdapter(argEntry, null, null);
	}

	protected IDtvDevice makeDeviceAdapter(DeviceConfig argEntry, IIpCashDrawerDevice argIpDevice, String argSeq) {
		if (StringUtils.isEmpty(argEntry.getName()))
			throw new IllegalArgumentException();
		if (!argEntry.isEnabled())
			return null;
		IDtvDevice device = this.devicesByName_.get((argIpDevice != null && argSeq != null)
				? (argEntry.getName() + argEntry.getName())
				: argEntry.getName());
		HardwareType<?> use = argEntry.getUse();
		if (device == null) {
			Class<? extends IDtvDevice> implClass = (Class<? extends IDtvDevice>) hardwareConfig_.getImplementationClass(use,this._hardwareFamilyTypeMgr);
			device = createDevice((Class) implClass, use);
			if (argIpDevice != null && argSeq != null)
				use = HardwareType.forUse("CashDrawer", "IP_CASHDRAWER" + argSeq);
			if (device == null) {
				initStartupMessages();
				String message = Translator.translate("_deviceCreateError." + implClass.getName());
				if (Translator.isMissingTranslation(message))
					message = Translator.translate("_deviceCreateError", new String[]{implClass.getSimpleName()});
				this.startupMessages_.appendMessage(this._formattableFactory.getLiteral(message));
				return null;
			}
			this.devicesByName_.put((argIpDevice != null && argSeq != null)
					? (argEntry.getName() + argEntry.getName())
					: argEntry.getName(), device);
			if (device instanceof IDtvCustDisplay)
				this.custDisplayMulti_.addDevice((IDtvCustDisplay) device);
		}
		device.addSourceDescription(argEntry.getSourceDescription());
		device.setPriority(argEntry.getPriority());
		if (device instanceof IDtvMultiuseDevice)
			((IDtvMultiuseDevice) device).addUse(use);
		Object oldValue = this.devicesByUse_.put(use, device);
		if (oldValue != null)
			LOG.warn("already had a " + argEntry.getUse() + " registered...overwriting");
		this.devicesByFamily_.put(argEntry.getType(), device);
		if (device instanceof IDtvIpCashDrawer && argIpDevice != null)
			((IDtvIpCashDrawer) device).setIpDevice(argIpDevice);
		return device;
	}

	protected List<IDtvDevice> makeDeviceAdapters(String argFamily) {
		List<IDtvDevice> devices = new ArrayList<>();
		for (DeviceConfig entry : this.hardwareConfig_.getEntries(argFamily)) {
			if (HardwareType.forUse("CashDrawer", "IP_CASHDRAWER").equals(entry.getUse())) {
				List<IDtvDevice> ipDevices = makeIpDeviceAdapters(entry);
				if (!ipDevices.isEmpty())
					devices.addAll(ipDevices);
			} else {
				IDtvDevice device = makeDeviceAdapter(entry);
				if (device != null)
					devices.add(device);
			}
		}
		return devices;
	}

	protected List<IDtvDevice> makeIpDeviceAdapters(DeviceConfig argEntry) {
		List<IDtvDevice> ipDevices = new ArrayList<>();
		int cnt = 0;
		for (IIpCashDrawerDevice device : this._apgCashdrawerUtils.getIpCashdrawerList()) {
			cnt++;
			IDtvDevice ipDevice = makeDeviceAdapter(argEntry, device, "-" + cnt);
			if (ipDevice != null)
				ipDevices.add(ipDevice);
		}
		return ipDevices;
	}

	protected void shutdownDevice(final IStartableDevice argDevice) {
		try {
			LOG.debug("before shutdown {}", new Supplier[]{() -> argDevice.getType()});
			Thread t = new Thread(createThreadName(argDevice.getName() + "-Shutdown")) {
				public void run() {
					argDevice.shutdown();
				}
			};
			t.setDaemon(true);
			t.start();
			t.join(60000L);
			if (t.isAlive())
				t.interrupt();
			LOG.debug("after shutdown {}", new Supplier[]{() -> argDevice.getType()});
		} catch (Throwable ex) {
			LOG.error("CAUGHT EXCEPTION", ex);
		}
	}

	protected void startDevice(final IStartableDevice argDevice, final HardwareStartupMessages argStartupMessages,
			final List<IStartableDevice> devicesToStartAgain) {
		Thread t = new Thread(createThreadName(argDevice.getName() + "-Startup")) {
			public void run() {
				HardwareMgr.this.log("" + argDevice.getType() + " - Starting", StartupMessageType.HEADING);
				if (!argDevice.startup((IHardwareStartupMessages) argStartupMessages)) {
					devicesToStartAgain.add(argDevice);
					HardwareMgr.this.log("" + argDevice.getType() + " - Failed (Will Retry)", StartupMessageType.INFO);
				} else {
					HardwareMgr.this.log("" + argDevice.getType() + " - Started", StartupMessageType.INFO);
				}
			}
		};
		t.setDaemon(true);
		t.start();
		try {
			t.join(argDevice.getStartupTimeoutMillis());
		} catch (InterruptedException ex) {
			LOG.debug("CAUGHT EXCEPTION", ex);
		}
		if (t.isAlive()) {
			log("" + argDevice.getType() + " - Failed (Won't Retry - Timeout) :: " + argDevice.getType(),
					StartupMessageType.ERROR);
			argStartupMessages.appendMessage((IDtvDevice) argDevice, "_deviceStartTimeout");
			t.interrupt();
			shutdownDevice(argDevice);
		}
	}

	protected void testCp858(HardwareStartupMessages argReturnMessages) {
		try {
			"test".getBytes("Cp858");
		} catch (UnsupportedEncodingException ex) {
			argReturnMessages.appendMessage(this._formattableFactory.getTranslatable("_codepage858error"));
			LOG.warn(
					"Code page 858 is not supported.  Make sure you are using an internationalized JRE that includes charset.jar.");
		}
	}

	protected void throwIfPrinter(IDtvDevice argSourceObject, JposException argEx) throws PosPrinterException {
		if ("POSPrinter".equals(argSourceObject.getType().getFamily()))
			throw new PosPrinterException(argSourceObject, argEx);
	}

	void log(String argMessage, StartupMessageType argType) {
		if (this.startupMessages_ != null) {
			this.startupMessages_.addDialogMessage(argMessage, argType);
		} else {
			LOG.info(argMessage);
		}
	}

	private String createThreadName(String argNameConstant) {
		String name = argNameConstant;
		WorkstationScopeKey wkstnScopeKey = WorkstationScope.getKeyFromCurrentThread();
		if (wkstnScopeKey != null)
			name = name + "-" + name;
		return name;
	}

	private void handleErrorEventCommon(String argMsg, IDtvJposDevice argSourceObject, IInput argInput,
			ErrorEvent argErrorEvent) {
		String message = argMsg + ":jpos error '" + argMsg + "' with "
				+ argSourceObject.getJposErrorType(argErrorEvent.getErrorCode(), argErrorEvent.getErrorCodeExtended())
				+ " error locus = " + argSourceObject.getName();
		LOG.warn(message);
		LogBuilder.getInstance().saveLogEntry(new HardwareLogModel((IDtvDevice) argSourceObject, message));
		IXstEvent event = EventDiscriminator.getInstance().translateEvent(argInput);
		if (event != null)
			argSourceObject.getEventor().post(IDtvInputDevice.EVENT_ROUTED_EVENT, event);
		argErrorEvent.setErrorResponse(12);
	}

	private void handleJposExceptionImpl(IDtvJposDevice argSource, JposException ex) throws PosPrinterException {
		HardwareLogModel event;
		logJposException(argSource, ex);
		switch (ex.getErrorCode()) {
			case 108 :
				fireStatusUpdateEvent((IDtvDevice) argSource, DtvHardwareEventType.DEVICE_OFFLINE);
				break;
			case 114 :
				switch (ex.getErrorCodeExtended()) {
					case 201 :
						fireStatusUpdateEvent((IDtvDevice) argSource, DtvHardwareEventType.DEVICE_OFFLINE);
						break;
					case 203 :
						event = new HardwareLogModel((IDtvDevice) argSource, "receipt paper out");
						event.setSeverity("Fault");
						event.setEventId("JPOS_EPTR_REC_EMPTY");
						LogBuilder.getInstance().saveLogEntry(event);
						fireStatusUpdateEvent((IDtvDevice) argSource, DtvHardwareEventType.PAPER_OUT);
						break;
					case 202 :
						event = new HardwareLogModel((IDtvDevice) argSource, "journal paper out");
						event.setSeverity("Fault");
						event.setEventId("JPOS_EPTR_JRN_EMPTY");
						LogBuilder.getInstance().saveLogEntry(event);
						break;
				}
				break;
		}
		throwIfPrinter((IDtvDevice) argSource, ex);
	}

	protected class StartupRunnable implements Runnable {
		public void run() {
			while (!HardwareMgr.this.isInitialized_) {
				HardwareMgr.LOG.info("waiting for initialization...");
				try {
					Thread.sleep(200L);
				} catch (InterruptedException ex) {
					HardwareMgr.LOG.info("CAUGHT EXCEPTION", ex);
				}
			}
			try {
				HardwareMgr.this.initStartupMessages();
				HardwareMgr.this.log("Hardware Initialization: START", StartupMessageType.START);
				long startTime = System.currentTimeMillis();
				List<IStartableDevice> restartList = new ArrayList<>();
				HardwareMgr.this.testCp858(HardwareMgr.this.startupMessages_);
				HardwareMgr.this.propMap_ = new HashMap<>();
				if (HardwareMgr.LOG.isDebugEnabled()) {
					StringBuffer sb = new StringBuffer(1024);
					sb.append("\n\ndeviceKeys:\n");
					Object[] deviceKeys = HardwareMgr.this.devicesByName_.keySet().toArray();
					ArrayUtils.toString(sb, deviceKeys);
					sb.append("\n\nuseKeys:\n");
					Object[] useKeys = HardwareMgr.this.devicesByUse_.keySet().toArray();
					ArrayUtils.toString(sb, useKeys);
					sb.append("\n");
					HardwareMgr.LOG.debug(sb);
				}
				HardwareFamilyType[] arrayOfHardwareFamilyType = HardwareMgr.this._hardwareFamilyTypeMgr
						.getHardwareFamilyTypes();
				Set<IDtvDevice> uniqueDevices = new HashSet<>();
				for (HardwareFamilyType<? extends IDtvDevice> type : arrayOfHardwareFamilyType) {
					IDtvDevice[] devices = HardwareMgr.this.getDevices((HardwareFamilyType) type);
					Arrays.sort(devices, Comparator.comparing(IDtvDevice::getPriority));
					for (IDtvDevice d : devices) {
						if (d instanceof IStartableDevice && !uniqueDevices.contains(d)) {
							uniqueDevices.add(d);
							HardwareMgr.this.startDevice((IStartableDevice) d, HardwareMgr.this.startupMessages_,
									restartList);
						}
					}
				}
				while (!restartList.isEmpty()) {
					Thread.sleep(1000L);
					IStartableDevice device = restartList.remove(0);
					HardwareMgr.this.startDevice(device, HardwareMgr.this.startupMessages_, restartList);
				}
				for (int i = 0; i < restartList.size(); i++) {
					IStartableDevice device = restartList.get(i);
					device.appendTimeoutMessage(HardwareMgr.this.startupMessages_);
				}
				JposServiceFrame.makeVisible();
				initEventDiscriminator();
				long elapsed = System.currentTimeMillis() - startTime;
				HardwareMgr.this.log("Hardware Initialization: COMPLETE (" + elapsed + " ms)", StartupMessageType.END);
			} catch (Throwable ex) {
				HardwareMgr.LOG.error("CAUGHT EXCEPTION DURING HARDWARE INITIALIZATION", ex);
				HardwareMgr.this.startupMessages_.appendMessage(
						HardwareMgr.this._formattableFactory.getTranslatable("_catastrophicHardwareStartup"));
			}
		}

		private void initEventDiscriminator() {
			EventDiscriminator.getInstance().translateEvent((IInput) new KeyboardInput(EncString.blank()));
		}
	}
}