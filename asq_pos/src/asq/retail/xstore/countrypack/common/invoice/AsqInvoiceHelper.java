package asq.retail.xstore.countrypack.common.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dtv.data2.access.DataFactory;
import dtv.data2.access.DefaultQueryResult;
import dtv.data2.access.IObjectId;
import dtv.data2.access.IPersistable;
import dtv.data2.access.IQueryKey;
import dtv.data2.access.IQueryResultList;
import dtv.data2.access.ObjectNotPresentException;
import dtv.data2.access.QueryKey;
import dtv.data2.access.datasource.DataSourceDescriptor;
import dtv.data2.access.datasource.DataSourceFactory;
import dtv.data2.access.pm.PersistenceMgrTypeFactory;
import dtv.data2.access.status.StatusMgr;
import dtv.i18n.FormattableFactory;
import dtv.pos.common.ConfigurationMgr;
import dtv.pos.common.ITransactionHelper;
import dtv.pos.common.TransactionType;
import dtv.pos.iframework.ILocationFactory;
import dtv.pos.iframework.invoice.IInvoiceConfiguration;
import dtv.pos.iframework.validation.IValidationResult;
import dtv.pos.iframework.validation.SimpleValidationResult;
import dtv.pos.register.returns.ReturnManager;
import dtv.pos.register.type.LineItemType;
import dtv.util.StringUtils;
import dtv.util.config.locator.GlobalConfigPathElement;
import dtv.util.sequence.SequenceConfigHelper;
import dtv.xst.dao.countrypack.civc.ISaleInvoice;
import dtv.xst.dao.countrypack.civc.ISaleInvoiceCrossReference;
import dtv.xst.dao.countrypack.civc.ISaleInvoiceProperty;
import dtv.xst.dao.crm.IParty;
import dtv.xst.dao.loc.ILegalEntity;
import dtv.xst.dao.loc.IRetailLocation;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trl.IRetailTransactionLineItem;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import dtv.xst.dao.trn.IPosTransaction;
import dtv.xst.dao.trn.IPosTransactionLink;
import dtv.xst.dao.trn.IPosTransactionProperty;
import dtv.xst.dao.trn.ITransactionReportData;
import dtv.xst.dao.trn.PosTransactionId;
import dtv.xst.dao.trn.TransactionReportDataId;
import oracle.retail.xstore.countrypack.common.FiscalHelper;
import oracle.retail.xstore.countrypack.common.invoice.IInvoiceNumberHelper;
import oracle.retail.xstore.countrypack.common.invoice.IInvoiceNumberHelperFactory;
import oracle.retail.xstore.countrypack.common.invoice.InvoiceReturnManager;
import oracle.retail.xstore.countrypack.common.invoice.InvoiceType;
import oracle.retail.xstore.countrypack.common.invoice.reporting.IInvoiceBean;
import oracle.retail.xstore.countrypack.common.invoice.reporting.InvoiceBeanTransaction;

public class AsqInvoiceHelper {
  private static final Logger LOG = LogManager.getLogger(AsqInvoiceHelper.class);
  
  protected static final IQueryKey<ISaleInvoice> INVOICE_LOOKUP = (IQueryKey<ISaleInvoice>)new QueryKey("COUNTRYPACK.INVOICE_LOOKUP", ISaleInvoice.class);
  
  public static final String INVOICE_PATH = "invoice";
  
  protected static final String TRANSACTION_LOOKUP = "TRANSACTION_LOOKUP";
  
  protected static final IQueryKey<IRetailTransactionLineItem> RETURNS_FROM_ORIGINAL_SALE = (IQueryKey<IRetailTransactionLineItem>)new QueryKey("COUNTRYPACK.GET_ALL_RETURNS_FROM_ORIGINAL_SALE", IRetailTransactionLineItem.class);
  
  private static final IQueryKey<DefaultQueryResult> TRANS_TO_GLOBAL_INVOICE = (IQueryKey<DefaultQueryResult>)new QueryKey("COUNTRYPACK.GLOBALINVOICE_SEARCH_TRANSACTIONS", DefaultQueryResult.class);
  
  @Inject
  protected ILocationFactory locationFactory_;
  
  @Inject
  protected IInvoiceConfiguration invoiceConfig_;
  
  @Inject
  private FiscalHelper _fiscalHelper;
  
  @Inject
  private ITransactionHelper _transactionHelper;
  
  @Inject
  protected PersistenceMgrTypeFactory _persistenceMgrTypeFactory;
  
  @Inject
  protected IInvoiceNumberHelperFactory _invoiceNumberHelperFactory;
  
  @Inject
  private FormattableFactory _formattables;
  
  private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
    Map<Object, Boolean> map = new ConcurrentHashMap<>();
    return t -> (map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null);
  }
  
  public void addCreditNoteHeaderProperties(IInvoiceNumberHelper argCreditNote, IPosTransaction argTransaction) {
    argTransaction.setDecimalProperty("CIVC_CN_YEAR", 
        BigDecimal.valueOf(argCreditNote.getBusinessYear()));
    argTransaction.setDecimalProperty("CIVC_CN_SEQUENCE_NBR", 
        BigDecimal.valueOf(argCreditNote.getDocNumberAsLong().longValue()));
    argTransaction.setStringProperty("CIVC_CN_TYPE", argCreditNote.getInvoiceType().getCode());
    argTransaction.setStringProperty("CIVC_CN_DOCUMENT_NUMBER", argCreditNote
        .getDocFullNumber());
    if (!StringUtils.isEmpty(argCreditNote.getDocPrefix()))
      argTransaction.setStringProperty("CIVC_CN_SEQUENCE_PREFIX", argCreditNote
          .getDocPrefix()); 
  }
  
  public void addInvoiceCustomerProperties(ISaleInvoice argInvoice, IPosTransaction argTransaction) {
    argTransaction.setStringProperty("CIVC_CUST_PARTY_ID", 
        String.valueOf(argInvoice.getPartyId()));
    for (ISaleInvoiceProperty prop : argInvoice.getProperties()) {
      Object propValue = prop.getPropertyValue();
      if (propValue instanceof String) {
        argTransaction.setStringProperty(prop.getPropertyCode(), (String)propValue);
        continue;
      } 
      if (propValue instanceof Boolean) {
        argTransaction.setBooleanProperty(prop.getPropertyCode(), ((Boolean)propValue).booleanValue());
        continue;
      } 
      if (propValue instanceof Date) {
        argTransaction.setDateProperty(prop.getPropertyCode(), (Date)propValue);
        continue;
      } 
      if (propValue instanceof BigDecimal)
        argTransaction.setDecimalProperty(prop.getPropertyCode(), (BigDecimal)propValue); 
    } 
  }
  
  public void addInvoiceHeaderProperties(IInvoiceNumberHelper argInvoice, IPosTransaction argTransaction) {
    addInvoiceHeaderProperties(argInvoice.getBusinessYear(), argInvoice.getDocNumberAsLong().longValue(), argInvoice
        .getInvoiceType().getCode(), argInvoice.getDocFullNumber(), null, argInvoice.getDocPrefix(), null, argTransaction);
  }
  
  public void addInvoiceHeaderProperties(ISaleInvoice argInvoice, IPosTransaction argTransaction) {
    addInvoiceHeaderProperties(argInvoice.getBusinessYear(), argInvoice.getSequenceNumber(), argInvoice
        .getInvoiceType(), argInvoice.getDocumentNumber(), argInvoice.getInvoiceDate(), argInvoice
        .getInvoicePrefix(), argInvoice.getExternalInvoiceId(), argTransaction);
  }
  
  public void addInvoiceVoidProperty(IPosTransaction argTransaction) {
    argTransaction.setBooleanProperty("CIVC_VOID", true);
  }
  
  public void addInvoiceXref(ISaleInvoice argInvoice, IPosTransaction argTransaction) {
    if (argTransaction != null)
      for (ISaleReturnLineItem line : argTransaction.getLineItemsByTypeCode(LineItemType.ITEM.getName(), ISaleReturnLineItem.class)) {
        if (this._fiscalHelper.itemIsFiscal(line))
          addInvoiceXref(argInvoice, Long.valueOf(line.getRetailLocationId()), line.getBusinessDate(), 
              Long.valueOf(line.getWorkstationId()), Long.valueOf(line.getTransactionSequence()), 
              Long.valueOf(line.getRetailTransactionLineItemSequence())); 
      }  
  }
  
  public void addInvoiceXref(ISaleInvoice argInvoice, List<ISaleInvoiceCrossReference> argSaleInvoiceCrossReference) {
    if (argSaleInvoiceCrossReference != null)
      for (ISaleInvoiceCrossReference xref : argSaleInvoiceCrossReference)
        addInvoiceXref(argInvoice, Long.valueOf(xref.getTransRetailLocationId()), xref.getTransBusinessDate(), 
            Long.valueOf(xref.getTransWorkstationId()), Long.valueOf(xref.getTransactionSequence()), 
            Long.valueOf(xref.getTransactionLineItemSequence()));  
  }
  
  public void addInvoiceXref(ISaleInvoice argInvoice, Long argRetailLocationId, Date argBusinessDate, Long argWorkstationId, Long argTransactionSequence, Long argRetailTransactionLineItemSequence) {
    ISaleInvoiceCrossReference xref = (ISaleInvoiceCrossReference)DataFactory.createObject(ISaleInvoiceCrossReference.class);
    xref.setOrganizationId(argInvoice.getOrganizationId());
    xref.setRetailLocationId(argInvoice.getRetailLocationId());
    xref.setWorkstationId(argInvoice.getWorkstationId());
    xref.setBusinessYear(argInvoice.getBusinessYear());
    xref.setSequenceId(argInvoice.getSequenceId());
    xref.setSequenceNumber(argInvoice.getSequenceNumber());
    xref.setTransRetailLocationId(argRetailLocationId.longValue());
    xref.setTransBusinessDate(argBusinessDate);
    xref.setTransWorkstationId(argWorkstationId.longValue());
    xref.setTransactionSequence(argTransactionSequence.longValue());
    xref.setTransactionLineItemSequence(argRetailTransactionLineItemSequence.longValue());
    argInvoice.addSaleInvoiceCrossReference(xref);
  }
  
  public void addTransCustomerProperties(IInvoiceBean argBean, IPosTransaction argTransaction) {
    addTransCustomerPropertiesInternal(argBean.getInvoiceParty(), argTransaction);
    if (argBean.getInvoiceParty() != null) {
      addTransCustomerProperty(argTransaction, "CIVC_CUST_FISCAL_CODE", argBean
          .getFiscalCode());
      addTransCustomerProperty(argTransaction, "CIVC_CUST_TAX_CODE", argBean.getTaxCode());
      if (this.invoiceConfig_.getEnableItalyElectronicInvoice()) {
        addInvoiceCustomerProperty(argBean.getInvoice(), "IT_ELEC_INV_ID_CODE", argBean
            .getInvoiceParty().getStringProperty("IT_ELEC_INV_ID_CODE"));
        addInvoiceCustomerProperty(argBean.getInvoice(), "IT_ELEC_INV_PEC", argBean
            .getInvoiceParty().getStringProperty("IT_ELEC_INV_PEC"));
      } 
    } 
  }
  
  public void addTransCustomerProperties(IPosTransaction argTransaction) {
    IParty party = (argTransaction instanceof IRetailTransaction) ? ((IRetailTransaction)argTransaction).getCustomerParty() : null;
    addTransCustomerPropertiesInternal(party, argTransaction);
    if (party != null) {
      addTransCustomerProperty(argTransaction, "CIVC_CUST_FISCAL_CODE", party
          .getPersonalTaxId());
      addTransCustomerProperty(argTransaction, "CIVC_CUST_TAX_CODE", party.getNationalTaxId());
    } 
  }
  
  public boolean compareCrossStoresTaxFreeCountry(long actualLocId, long remoteLocId) {
    if (remoteLocId == actualLocId)
      return true; 
    IRetailLocation remoteStore = this.locationFactory_.getStoreById(remoteLocId);
    IRetailLocation actualStore = this.locationFactory_.getStoreById(actualLocId);
    if (actualStore != null && actualStore.getCountry() != null && remoteStore != null && remoteStore
      .getCountry() != null)
      if (actualStore.getCountry().toString().equals(remoteStore.getCountry().toString()))
        return true;  
    return false;
  }
  
  public void copyInvoiceCustomerProperties(IPosTransaction argSourceTransaction, IPosTransaction argDestinationTransaction) {
    for (IPosTransactionProperty prop : argSourceTransaction.getProperties()) {
      if (prop.getPropertyCode().startsWith("CIVC_CUST_")) {
        Object propValue = prop.getPropertyValue();
        if (propValue instanceof String) {
          argDestinationTransaction.setStringProperty(prop.getPropertyCode(), (String)propValue);
          continue;
        } 
        if (propValue instanceof Boolean) {
          argDestinationTransaction.setBooleanProperty(prop.getPropertyCode(), ((Boolean)propValue).booleanValue());
          continue;
        } 
        if (propValue instanceof Date) {
          argDestinationTransaction.setDateProperty(prop.getPropertyCode(), (Date)propValue);
          continue;
        } 
        if (propValue instanceof BigDecimal)
          argDestinationTransaction.setDecimalProperty(prop.getPropertyCode(), (BigDecimal)propValue); 
      } 
    } 
  }
  
  public ISaleInvoice createInvoice(IInvoiceBean argInvoiceBean, IPosTransaction argTransaction) {
    if (argInvoiceBean.getPendingInvoiceReference() == null)
      argInvoiceBean.getInvoiceNumberData().getNextNumber(this._formattables); 
    IPosTransaction currentTrans = argTransaction;
    addTransCustomerProperties(argInvoiceBean, currentTrans);
    addTransBeanProperties(argInvoiceBean, currentTrans);
    if (argInvoiceBean.getPendingInvoiceReference() != null) {
      ISaleInvoice oldInvoice = (ISaleInvoice)DataFactory.getObjectByIdNoThrow(argInvoiceBean.getPendingInvoiceReference());
      oldInvoice.setVoidFlag(true);
      this._transactionHelper.getPersistables().addObject((IPersistable)oldInvoice);
      IPosTransaction deferredTrans = getTransactionFromInvoice(oldInvoice);
      deferredTrans.setPostVoid(true);
      this._transactionHelper.getPersistables().addObject((IPersistable)deferredTrans);
    } 
    referenceOriginalTrans(argInvoiceBean.getInvoice(), argInvoiceBean.getTransactions());
    argInvoiceBean.getInvoice().setInvoiceTransactionSequence(currentTrans.getTransactionSequence());
    argInvoiceBean.getInvoice().setBusinessDate(currentTrans.getBusinessDate());
    argInvoiceBean.getInvoice().setWorkstationId(currentTrans.getWorkstationId());
    return argInvoiceBean.getInvoice();
  }
  
  public void createTransactionLinks(IPosTransaction argTran, List<PosTransactionId> argTransToLink) {
    for (PosTransactionId tran : argTransToLink)
      argTran.getTransactionLinks().add(createTransactionLink(argTran, tran)); 
  }
  
  public List<PosTransactionId> extracTransactionsToLink(InvoiceType argInvoiceType, long argRetailLocationId, Date argBusinessDate) {
    List<PosTransactionId> tranIds = new ArrayList<>();
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("argRetailLocationId", Long.valueOf(argRetailLocationId));
    parameters.put("argBusinessDate", argBusinessDate);
    if (argInvoiceType.equals(InvoiceType.GLOBAL_CREDIT_NOTE)) {
      parameters.put("argNegativeAmnt", Integer.valueOf(0));
    } else {
      parameters.put("argPositiveAmnt", Integer.valueOf(0));
    } 
    IQueryResultList<DefaultQueryResult> results = DataFactory.getObjectByQueryNoThrow(TRANS_TO_GLOBAL_INVOICE, parameters);
    for (DefaultQueryResult result : results)
      tranIds.add(createTransactionId(result)); 
    return tranIds;
  }
  
  public List<IRetailTransactionLineItem> getAllReturnsFromOriginalSale(PosTransactionId argTrnId) {
    Map<String, Object> params = new HashMap<>();
    params.put("argOrganizationId", Long.valueOf(ConfigurationMgr.getOrganizationId()));
    params.put("argOriginalRetailLocationId", argTrnId.getRetailLocationId());
    params.put("argOriginalWorkstationId", argTrnId.getWorkstationId());
    params.put("argOriginalBusinessDate", argTrnId.getBusinessDate());
    params.put("argOriginalTransactionSequence", argTrnId.getTransactionSequence());
    return (List<IRetailTransactionLineItem>)DataFactory.getObjectByQueryNoThrow(RETURNS_FROM_ORIGINAL_SALE, params);
  }
  
  public String getDefaultTaxFreeSearchPmType() {
    DataSourceDescriptor desc = DataSourceFactory.getInstance().getDataSourceDescriptor("Xcenter");
    if (desc.isEnabled() && StatusMgr.getInstance().isOnline("Xcenter") == true)
      return this.invoiceConfig_.getTaxFreeRemotePmType(); 
    return this.invoiceConfig_.getTaxFreeLocalPmType();
  }
  
  public List<ISaleInvoice> getInvoicesFromTransaction(PosTransactionId argTransId, boolean argExcludeTaxFreeCreditNotes) {
    return getInvoicesFromTransaction(argTransId, argExcludeTaxFreeCreditNotes, false);
  }
  
  public List<ISaleInvoice> getInvoicesFromTransaction(PosTransactionId argTransId, boolean argExcludeTaxFreeCreditNotes, boolean argCheckXcenter) {
    Map<String, Object> params = new HashMap<>();
    params.put("argOrganizationId", argTransId.getOrganizationId());
    params.put("argRtlLocId", argTransId.getRetailLocationId());
    params.put("argBusinessDate", argTransId.getBusinessDate());
    params.put("argWorkstationId", argTransId.getWorkstationId());
    params.put("argTransactionSequence", argTransId.getTransactionSequence());
    if (argExcludeTaxFreeCreditNotes)
      params.put("@argIgnoredInvoiceTypes", Arrays.asList(new String[] { InvoiceType.TAXFREE_CREDIT_NOTE.getCode() })); 
    DataSourceDescriptor desc = DataSourceFactory.getInstance().getDataSourceDescriptor("Xcenter");
    if (argCheckXcenter && !desc.isEnabled())
      LOG.warn("Xcenter datasource is not enabled, the application will perform the serach in the local datsource."); 
    if (argCheckXcenter && desc.isEnabled()) {
      IQueryResultList iQueryResultList;
      List<ISaleInvoice> invoices = null;
      try {
        iQueryResultList = DataFactory.getObjectByQuery(INVOICE_LOOKUP, params, "RESERVED_CENTRAL");
      } catch (ObjectNotPresentException ex) {
        if (StatusMgr.getInstance().isOnline("Xcenter"))
          return new ArrayList<>(); 
        LOG.debug("CAUGHT EXCEPTION", (Throwable)ex);
        return new ArrayList<>();
      } 
      return (List<ISaleInvoice>)iQueryResultList;
    } 
    return (List<ISaleInvoice>)DataFactory.getObjectByQueryNoThrow(INVOICE_LOOKUP, params);
  }
  
  public List<InvoiceTransaction> getInvoicesObjForInvoice(IPosTransaction argTrans) {
    if (argTrans instanceof IRetailTransaction)
      return Collections.singletonList(new InvoiceTransaction(this, (IRetailTransaction)argTrans)); 
    if (TransactionType.DEFERRED_INVOICE.matches(argTrans)) {
      List<InvoiceTransaction> transactions = new ArrayList<>();
      for (IPosTransactionLink link : argTrans.getTransactionLinks()) {
        LOG.info("Processing transaction link: " + link.getObjectIdAsString());
        transactions.add(new InvoiceTransaction(this, link));
      } 
      return transactions;
    } 
    return new ArrayList<>();
  }
  
  public String getLocalTaxFreeSearchPmType() {
    return this.invoiceConfig_.getTaxFreeLocalPmType();
  }
  
  public IInvoiceNumberHelper getNumberHelper(InvoiceType argInvoiceType, Date argBusinessDate) {
    IInvoiceNumberHelper retval = this._invoiceNumberHelperFactory.getInvoiceNumberHelper();
    retval.initialize(argInvoiceType, argBusinessDate);
    return retval;
  }
  
  public IInvoiceNumberHelper getNumberHelper(ISaleInvoice argInvoice) {
    IInvoiceNumberHelper retval = this._invoiceNumberHelperFactory.getInvoiceNumberHelper();
    retval.initialize(argInvoice);
    return retval;
  }
  
  public String getRemoteTaxFreeSearchPmType() {
    return this.invoiceConfig_.getTaxFreeRemotePmType();
  }
  
  public boolean getTaxFreeAllowReceiptsFromDifferentLegalEntities() {
    return this.invoiceConfig_.getTaxFreeAllowReceiptsFromDifferentLegalEntities();
  }
  
  public boolean getTaxFreeAllowReceiptsFromOtherStores() {
    return this.invoiceConfig_.getTaxFreeAllowReceiptsFromOtherStores();
  }
  
  public boolean getTaxFreeAllowReturnsFromDifferentLegalEntities() {
    return this.invoiceConfig_.getTaxFreeAllowReturnsFromDifferentLegalEntities();
  }
  
  public boolean getTaxFreeAllowReturnsFromOtherStores() {
    return this.invoiceConfig_.getTaxFreeAllowReturnsFromOtherStores();
  }
  
  public IPosTransaction getTransactionFromInvoice(ISaleInvoice argInvoice) {
    PosTransactionId posId = getTransactionIdFromInvoice(argInvoice);
    IPosTransaction tran = (IPosTransaction)DataFactory.getObjectByIdNoThrow((IObjectId)posId);
    return tran;
  }
  
  public IPosTransaction getTransactionFromLink(IPosTransactionLink argLink) {
    PosTransactionId transId = new PosTransactionId();
    transId.setBusinessDate(argLink.getLinkBusinessDate());
    transId.setOrganizationId(Long.valueOf(argLink.getOrganizationId()));
    transId.setRetailLocationId(Long.valueOf(argLink.getLinkRetailLocationId()));
    transId.setTransactionSequence(Long.valueOf(argLink.getLinkTransactionSequence()));
    transId.setWorkstationId(Long.valueOf(argLink.getLinkWorkstationId()));
    return (IPosTransaction)DataFactory.getObjectById((IObjectId)transId);
  }
  
  public PosTransactionId getTransactionIdFromInvoice(ISaleInvoice argInvoice) {
    PosTransactionId posId = new PosTransactionId();
    posId.setOrganizationId(Long.valueOf(argInvoice.getOrganizationId()));
    posId.setBusinessDate(argInvoice.getBusinessDate());
    posId.setRetailLocationId(Long.valueOf(argInvoice.getRetailLocationId()));
    posId.setWorkstationId(Long.valueOf(argInvoice.getWorkstationId()));
    posId.setTransactionSequence(Long.valueOf(argInvoice.getInvoiceTransactionSequence()));
    return posId;
  }
  
  public List<IPosTransaction> getTransactionsFromInvoice(ISaleInvoice argInvoice) {
    List<IPosTransaction> trans = new ArrayList<>();
    List<PosTransactionId> transIdList = new ArrayList<>();
    transIdList = (List<PosTransactionId>)argInvoice.getInvoiceTransactionsLines().stream().map(related -> {
          PosTransactionId transId = new PosTransactionId();
          transId.setBusinessDate(related.getTransBusinessDate());
          transId.setOrganizationId(Long.valueOf(related.getOrganizationId()));
          transId.setRetailLocationId(Long.valueOf(related.getTransRetailLocationId()));
          transId.setTransactionSequence(Long.valueOf(related.getTransactionSequence()));
          transId.setWorkstationId(Long.valueOf(related.getTransWorkstationId()));
          return transId;
        }).collect(Collectors.toCollection(ArrayList::new));
    transIdList = (List<PosTransactionId>)transIdList.stream().filter(distinctByKey(PosTransactionId::toString)).collect(Collectors.toCollection(ArrayList::new));
    for (PosTransactionId tranId : transIdList) {
      IPosTransaction tran = (IPosTransaction)DataFactory.getObjectByIdNoThrow((IObjectId)tranId, getDefaultTaxFreeSearchPmType());
      if (tran != null) {
        trans.add(tran);
        continue;
      } 
      tran = (IPosTransaction)DataFactory.getObjectByIdNoThrow((IObjectId)tranId, getLocalTaxFreeSearchPmType());
      if (tran != null)
        trans.add(tran); 
    } 
    return trans;
  }
  
  public int getTrnAgeLimitMinutesIfXcenterOffline() {
    return this.invoiceConfig_.getTrnAgeLimitMinutesIfXcenterOffline();
  }
  
  public boolean isCreditNote(IPosTransaction argTrans) {
    if (argTrans == null)
      return false; 
    List<InvoiceTransaction> transList = getInvoicesObjForInvoice(argTrans);
    BigDecimal transTotal = BigDecimal.ZERO;
    for (InvoiceTransaction trans : transList)
      transTotal = transTotal.add(trans.getTransaction().getLineItems(ISaleReturnLineItem.class).stream()
          .filter(l -> !l.getVoid()).filter(l -> this._fiscalHelper.itemIsFiscal(l))
          .map(l -> l.getExtendedAmount()).reduce(BigDecimal.ZERO, BigDecimal::add)); 
    return (transTotal.compareTo(BigDecimal.ZERO) < 0);
  }
  
  public boolean isCustomerCompany(IParty argParty) {
    return (argParty != null && isCustomerCompany(argParty.getOrganizationTypeCode()));
  }
  
  public boolean isCustomerCompany(String argOrganizationType) {
    return !StringUtils.isEmpty(argOrganizationType);
  }
  
  public boolean isCustomerNonBusiness(IRetailTransaction argTrans) {
    return (argTrans.getCustomerParty() != null && 
      !StringUtils.isEmpty(argTrans.getCustomerParty().getOrganizationTypeCode()) && this.invoiceConfig_
      .getOrganizationTypeToAllowInvoice()
      .contains(argTrans.getCustomerParty().getOrganizationTypeCode()));
  }
  
  public boolean isGlobalInvoicedItems(IPosTransaction argTransaction, InvoiceReturnManager argInvoiceReturnMgr) {
    if (isCreditNote(argTransaction)) {
      argInvoiceReturnMgr.loadTransaction(argTransaction, null, null);
      return (argInvoiceReturnMgr.hasReturnsFromInvoice() && argInvoiceReturnMgr.hasAtLeastOneGlobalInvoice());
    } 
    return false;
  }
  
  public boolean isGlobalInvoiceOrCreditNote(ISaleInvoice argInvoice) {
    return (argInvoice.getInvoiceType().equalsIgnoreCase(InvoiceType.GLOBAL_INVOICE.getName()) || argInvoice
      .getInvoiceType().equalsIgnoreCase(InvoiceType.GLOBAL_CREDIT_NOTE.getName()));
  }
  
  public boolean isInvoicedReturnTransaction(IPosTransaction argTransaction, InvoiceReturnManager argInvoiceReturnMgr, ReturnManager argReturnManager) {
    if (isCreditNote(argTransaction)) {
      argInvoiceReturnMgr.loadTransaction(argTransaction, null, null);
      return (argInvoiceReturnMgr.hasReturnsFromInvoice() && 
        !argInvoiceReturnMgr.hasDifferentReturnCustomer(argReturnManager));
    } 
    return false;
  }
  
  public boolean isInvoicePathEnabled() {
    List<String> baseFeatures = GlobalConfigPathElement.BASE_FEATURES.getConfigPathEntries();
    return (baseFeatures.stream().filter(path -> "invoice".equals(path)).count() != 0L);
  }
  
  public boolean isInvoiceSequenceAtStoreLevel() {
    SequenceConfigHelper helper = SequenceConfigHelper.getInstance();
    return helper.getSequence(this.invoiceConfig_.getInvoiceSequenceName()).isRetailLocationLevel();
  }
  
  public boolean isPendingGlobalInvoiceOrCreditNote(ISaleInvoice argInvoice) {
    return (argInvoice.getInvoiceType().equalsIgnoreCase(InvoiceType.PENDING_GLOBAL_INVOICE.getName()) || argInvoice
      .getInvoiceType().equalsIgnoreCase(InvoiceType.PENDING_GLOBAL_CREDIT_NOTE.getName()));
  }
  
  public boolean isSameLegalEntityByLocation(long argFirstLocationId, long argSecondLocationId) {
    if (argSecondLocationId == argFirstLocationId)
      return true; 
    IRetailLocation remoteStore = this.locationFactory_.getStoreById(argSecondLocationId);
    IRetailLocation actualStore = this.locationFactory_.getStoreById(argFirstLocationId);
    if (actualStore != null && actualStore.getLegalEntityId() != null && remoteStore != null && remoteStore
      .getLegalEntityId() != null && 
      actualStore.getLegalEntityId().equals(remoteStore.getLegalEntityId()))
      return true; 
    return false;
  }
  
  public boolean isUsingTheSameSequence() {
    return this.invoiceConfig_.getCreditNoteSequenceName().equals(this.invoiceConfig_.getInvoiceSequenceName());
  }
  
  public ITransactionReportData loadReportData(ISaleInvoice argInvoice, String argReportId) {
    TransactionReportDataId id = new TransactionReportDataId();
    id.setOrganizationId(Long.valueOf(argInvoice.getOrganizationId()));
    id.setBusinessDate(argInvoice.getBusinessDate());
    id.setRetailLocationId(Long.valueOf(argInvoice.getRetailLocationId()));
    id.setWorkstationId(Long.valueOf(argInvoice.getWorkstationId()));
    id.setTransactionSequence(Long.valueOf(argInvoice.getInvoiceTransactionSequence()));
    id.setReportId(argReportId);
    return (ITransactionReportData)DataFactory.getObjectByIdNoThrow((IObjectId)id);
  }
  
  public IValidationResult validateCrossStoresTaxFreeOperations(long actualLocId, long remoteLocId, FormattableFactory argFormattableFactory, boolean fromBarcodeScan) {
    if (remoteLocId == actualLocId)
      return IValidationResult.SUCCESS; 
    IRetailLocation location = this.locationFactory_.getStoreById(remoteLocId);
    if (location == null)
      return SimpleValidationResult.getFailed(argFormattableFactory.getTranslatable("_oracle.retail.xstore.countrypack.common.taxfree.taxFreeTranSearchInvalidLocation")); 
    if (!getTaxFreeAllowReceiptsFromOtherStores() && remoteLocId != actualLocId)
      return SimpleValidationResult.getFailed(argFormattableFactory.getTranslatable("_oracle.retail.xstore.countrypack.common.taxfree.taxFreeTranSearchOtherLocationsNotAllowed")); 
    if (!compareCrossStoresTaxFreeCountry(actualLocId, remoteLocId))
      return SimpleValidationResult.getFailed(argFormattableFactory.getTranslatable(
            fromBarcodeScan ? 
            "_oracle.retail.xstore.countrypack.common.taxfree.taxFreeTranScanInvalidCountry" : 
            "_oracle.retail.xstore.countrypack.common.taxfree.taxFreeTranSearchInvalidCountry")); 
    if (!getTaxFreeAllowReceiptsFromDifferentLegalEntities() && 
      !isSameLegalEntityByLocation(actualLocId, remoteLocId))
      return SimpleValidationResult.getFailed(argFormattableFactory.getTranslatable(
            fromBarcodeScan ? 
            "_oracle.retail.xstore.countrypack.common.taxfree.taxFreeTranScanInvalidLegalEntity" : 
            "_oracle.retail.xstore.countrypack.common.taxfree.taxFreeTranSearchInvalidLegalEntity")); 
    return IValidationResult.SUCCESS;
  }
  
  public IValidationResult validateCrossStoresTaxFreeReturns(long actualLocId, long remoteLocId, FormattableFactory argFormattableFactory, boolean fromBarcodeScan) {
    if (remoteLocId == actualLocId)
      return IValidationResult.SUCCESS; 
    IRetailLocation location = this.locationFactory_.getStoreById(remoteLocId);
    if (location == null)
      return SimpleValidationResult.getFailed(argFormattableFactory.getTranslatable("_oracle.retail.xstore.countrypack.common.taxfree.taxFreeTranSearchInvalidLocation")); 
    if (!getTaxFreeAllowReturnsFromOtherStores() && remoteLocId != actualLocId)
      return SimpleValidationResult.getFailed(argFormattableFactory.getTranslatable("_oracle.retail.xstore.countrypack.common.taxfree.taxFreeTranSearchOtherLocationsNotAllowed")); 
    if (!compareCrossStoresTaxFreeCountry(actualLocId, remoteLocId))
      return SimpleValidationResult.getFailed(argFormattableFactory.getTranslatable(
            fromBarcodeScan ? 
            "_oracle.retail.xstore.countrypack.common.taxfree.taxFreeTranScanInvalidCountry" : 
            "_oracle.retail.xstore.countrypack.common.taxfree.taxFreeTranSearchInvalidCountry")); 
    if (!getTaxFreeAllowReturnsFromDifferentLegalEntities() && 
      !isSameLegalEntityByLocation(actualLocId, remoteLocId))
      return SimpleValidationResult.getFailed(argFormattableFactory.getTranslatable(
            fromBarcodeScan ? 
            "_oracle.retail.xstore.countrypack.common.taxfree.taxFreeTranScanInvalidLegalEntity" : 
            "_oracle.retail.xstore.countrypack.common.taxfree.taxFreeTranSearchInvalidLegalEntity")); 
    return IValidationResult.SUCCESS;
  }
  
  protected void addInvoiceCustomerProperty(ISaleInvoice argInvoice, String argPropertyCode, String argValue) {
    if (!StringUtils.isEmpty(argValue))
      argInvoice.setStringProperty(argPropertyCode, argValue); 
  }
  
  protected void addTransBeanProperties(IInvoiceBean argBean, IPosTransaction argTransaction) {
    Map<String, Object> props = argBean.getProperties();
    for (Map.Entry<String, Object> prop : props.entrySet()) {
      Object propValue = prop.getValue();
      if (propValue instanceof String) {
        argTransaction.setStringProperty(prop.getKey(), (String)propValue);
        continue;
      } 
      if (propValue instanceof Boolean) {
        argTransaction.setBooleanProperty(prop.getKey(), ((Boolean)propValue).booleanValue());
        continue;
      } 
      if (propValue instanceof Date) {
        argTransaction.setDateProperty(prop.getKey(), (Date)propValue);
        continue;
      } 
      if (propValue instanceof BigDecimal) {
        argTransaction.setDecimalProperty(prop.getKey(), (BigDecimal)propValue);
        continue;
      } 
      LOG.warn("Invoice bean property {} is of undefined type", prop.getKey());
    } 
  }
  
  protected void addTransCustomerProperty(IPosTransaction argTransaction, String argPropertyCode, String argValue) {
    if (!StringUtils.isEmpty(argValue))
      argTransaction.setStringProperty(argPropertyCode, argValue); 
  }
  
  protected void referenceOriginalTrans(ISaleInvoice argInvoice, List<InvoiceBeanTransaction> argTransList) {
    for (InvoiceBeanTransaction ivcTrans : argTransList)
      addInvoiceXref(argInvoice, (IPosTransaction)ivcTrans.getTransaction()); 
  }
  
  private void addInvoiceHeaderProperties(long argYear, long argSequenceNumber, String argInvoiceType, String argDocumentNumber, Date argInvoiceDate, String argInvoicePrefix, String argInvoiceExternalId, IPosTransaction argTransaction) {
    argTransaction.setDecimalProperty("CIVC_YEAR", BigDecimal.valueOf(argYear));
    argTransaction.setDecimalProperty("CIVC_SEQUENCE_NBR", 
        BigDecimal.valueOf(argSequenceNumber));
    argTransaction.setStringProperty("CIVC_TYPE", argInvoiceType);
    argTransaction.setStringProperty("CIVC_DOCUMENT_NUMBER", argDocumentNumber);
    argTransaction.setDateProperty("CIVC_DOCUMENT_DATE", argInvoiceDate);
    if (!StringUtils.isEmpty(argInvoicePrefix))
      argTransaction.setStringProperty("CIVC_SEQUENCE_PREFIX", argInvoicePrefix); 
    if (!StringUtils.isEmpty(argInvoiceExternalId))
      argTransaction.setStringProperty("CIVC_EXT_INVOICE_ID", argInvoiceExternalId); 
  }
  
  private void addTransCustomerPropertiesInternal(IParty argParty, IPosTransaction argTransaction) {
    if (argParty != null) {
      addTransCustomerProperty(argTransaction, "CIVC_CUST_CUST_ID", argParty.getCustomerId());
      addTransCustomerProperty(argTransaction, "CIVC_CUST_FIRST_NAME", argParty
          .getFirstName());
      addTransCustomerProperty(argTransaction, "CIVC_CUST_MIDDLE_NAME", argParty
          .getMiddleName());
      addTransCustomerProperty(argTransaction, "CIVC_CUST_LAST_NAME", argParty.getLastName());
      addTransCustomerProperty(argTransaction, "CIVC_CUST_ORG_NAME", argParty
          .getOrganizationName());
      addTransCustomerProperty(argTransaction, "CIVC_CUST_COUNTRY", argParty
          .getPrimaryLocaleInformation().getCountry());
      addTransCustomerProperty(argTransaction, "CIVC_CUST_COUNTY", argParty
          .getPrimaryLocaleInformation().getCounty());
      addTransCustomerProperty(argTransaction, "CIVC_CUST_STATE", argParty
          .getPrimaryLocaleInformation().getState());
      addTransCustomerProperty(argTransaction, "CIVC_CUST_CITY", argParty
          .getPrimaryLocaleInformation().getCity());
      addTransCustomerProperty(argTransaction, "CIVC_CUST_NEIGHBORHOOD", argParty
          .getPrimaryLocaleInformation().getNeighborhood());
      addTransCustomerProperty(argTransaction, "CIVC_CUST_ADDRESS1", argParty
          .getPrimaryLocaleInformation().getAddress1());
      addTransCustomerProperty(argTransaction, "CIVC_CUST_ADDRESS2", argParty
          .getPrimaryLocaleInformation().getAddress2());
      addTransCustomerProperty(argTransaction, "CIVC_CUST_APARTMENT", argParty
          .getPrimaryLocaleInformation().getApartment());
      addTransCustomerProperty(argTransaction, "CIVC_CUST_POSTAL_CODE", argParty
          .getPrimaryLocaleInformation().getPostalCode());
    } 
    IRetailLocation currentStore = this.locationFactory_.getStoreById(argTransaction.getRetailLocationId());
    ILegalEntity legalEntity = currentStore.getLegalEntity();
	/*
	 * addTransCustomerProperty(argTransaction, "CIVC_RETAILER_FISCAL_CODE",
	 * legalEntity .getTaxId());
	 */
  }
  
  private PosTransactionId createTransactionId(DefaultQueryResult argResult) {
    return createTransactionId((Long)argResult
        .get("OrganizationId"), (Long)argResult
        .get("RetailLocationId"), (Date)argResult
        .get("BusinessDate"), (Long)argResult
        .get("WorkstationId"), (Long)argResult
        .get("TransactionSequence"));
  }
  
  private PosTransactionId createTransactionId(Long argOrganizationId, Long argRetailLocationId, Date argBusinessDate, Long argWorkstationId, Long argTransactionSequence) {
    PosTransactionId tranId = new PosTransactionId();
    tranId.setOrganizationId(argOrganizationId);
    tranId.setRetailLocationId(argRetailLocationId);
    tranId.setBusinessDate(argBusinessDate);
    tranId.setWorkstationId(argWorkstationId);
    tranId.setTransactionSequence(argTransactionSequence);
    return tranId;
  }
  
  private IPosTransactionLink createTransactionLink(IPosTransaction argTran, PosTransactionId argToLinkTran) {
    IPosTransactionLink link = (IPosTransactionLink)DataFactory.createObject(IPosTransactionLink.class);
    link.setOrganizationId(argTran.getOrganizationId());
    link.setRetailLocationId(argTran.getRetailLocationId());
    link.setBusinessDate(argTran.getBusinessDate());
    link.setWorkstationId(argTran.getWorkstationId());
    link.setTransactionSequence(argTran.getTransactionSequence());
    link.setLinkRetailLocationId(argToLinkTran.getRetailLocationId().longValue());
    link.setLinkBusinessDate(argToLinkTran.getBusinessDate());
    link.setLinkWorkstationId(argToLinkTran.getWorkstationId().longValue());
    link.setLinkTransactionSequence(argToLinkTran.getTransactionSequence().longValue());
    link.setLinkTypeCode("DEFERRED_INVOICE_TRANSACTION");
    return link;
  }
  
  public class InvoiceTransaction {
    private AsqInvoiceHelper invoiceHelper_;
    
    private IRetailTransaction trans_;
    
    private IPosTransactionLink transLink_;
    
    public InvoiceTransaction(AsqInvoiceHelper argHelper, IPosTransactionLink argTransLink) {
      this.invoiceHelper_ = argHelper;
      this.trans_ = null;
      this.transLink_ = argTransLink;
    }
    
    public InvoiceTransaction(AsqInvoiceHelper argHelper, IRetailTransaction argTrans) {
      this.invoiceHelper_ = argHelper;
      this.trans_ = argTrans;
      this.transLink_ = null;
    }
    
    public IRetailTransaction getTransaction() {
      if (this.trans_ != null)
        return this.trans_; 
      IPosTransaction trn = null;
      if (this.transLink_.getLinkedTransaction() != null) {
        trn = this.transLink_.getLinkedTransaction();
      } else {
        trn = this.invoiceHelper_.getTransactionFromLink(this.transLink_);
      } 
      return (trn instanceof IRetailTransaction) ? (IRetailTransaction)trn : null;
    }
  }
}
