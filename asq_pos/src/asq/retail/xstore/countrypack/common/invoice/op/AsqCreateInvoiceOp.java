package asq.retail.xstore.countrypack.common.invoice.op;

import dtv.data2.access.DataFactory;
import dtv.data2.access.IPersistable;
import dtv.pos.common.ValueKeys;
import dtv.pos.common.attachments.TransactionAttachmentData;
import dtv.pos.common.attachments.TransactionAttachmentManager;
import dtv.pos.framework.op.Operation;
import dtv.pos.framework.reporting.ReportInfo;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.reporting.IReportMgr;
import dtv.util.StringUtils;
import dtv.xst.dao.countrypack.civc.ISaleInvoice;
import dtv.xst.dao.trn.IPosTransaction;
import dtv.xst.dao.trn.ITransactionReportData;
import dtv.xst.dao.trn.PosTransactionId;
import javax.inject.Inject;
import oracle.retail.xstore.countrypack.common.CountryValueKeys;
import oracle.retail.xstore.countrypack.common.edocument.bean.EDocumentHelper;
import oracle.retail.xstore.countrypack.common.edocument.bean.EDocumentResponse;
import oracle.retail.xstore.countrypack.common.invoice.InvoiceConfiguration;
import oracle.retail.xstore.countrypack.common.invoice.InvoiceHelper;
import oracle.retail.xstore.countrypack.common.invoice.op.CreateInvoiceOp;
import oracle.retail.xstore.countrypack.common.invoice.reporting.IInvoiceBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.retail.xstore.countrypack.common.invoice.AsqInvoiceHelper;

public class AsqCreateInvoiceOp extends Operation {
  private static final Logger logger_ = LogManager.getLogger(CreateInvoiceOp.class);
  
  @Inject
  protected AsqInvoiceHelper _invoiceHelper;
  
  @Inject
  protected IReportMgr _reportMgr;
  
  @Inject
  protected InvoiceConfiguration _invoiceConfig;
  
  @Inject
  private TransactionAttachmentManager _tranAttachment;
  
  @Inject
  private EDocumentHelper _eDocumentHelper;
  
  public IOpResponse handleOpExec(IXstEvent argEvent) {
    ISaleInvoice invoice = createInvoice();
    IPosTransaction trans = getTransaction();
    IInvoiceBean invoiceBean = (IInvoiceBean)getScopedValue(CountryValueKeys.INVOICE_BEAN);
    addInvoiceReport(invoice, trans, invoiceBean);
    this._invoiceHelper.addInvoiceHeaderProperties(invoice, trans);
    this._invoiceHelper.addInvoiceCustomerProperties(invoice, trans);
    setScopedValue(CountryValueKeys.CURRENT_INVOICE, invoice);
    this._transactionHelper.getPersistables().addObject((IPersistable)invoice);
    return this.HELPER.completeResponse();
  }
  
  public boolean isOperationApplicable() {
    return Boolean.TRUE.equals(getScopedValue(CountryValueKeys.PRINT_FISCAL_INVOICE));
  }
  
  protected void addInvoiceReport(ISaleInvoice argInvoice, IPosTransaction argTransaction, IInvoiceBean argBean) {
    if (this._invoiceConfig.isPrintXstoreInvoice() && !argBean.isPendingGlobalInvoiceOrCreditNote()) {
      ReportInfo reportInfo = (ReportInfo)getScopedValue(ValueKeys.REPORT_INFO);
      String invoiceType = argInvoice.getInvoiceType();
      byte[] data = null;
      if (reportInfo != null && reportInfo.getPrint() != null) {
        data = this._reportMgr.convertReportForSave(reportInfo.getPrint());
        addInvoiceReportRecord(argTransaction, invoiceType, data, false);
      } 
      if (this._invoiceConfig.getEnableExtendedReprint()) {
        if (argBean.getXmlDataset() != null)
          addInvoiceReportRecord(argTransaction, invoiceType + "_DATA", argBean
              .getXmlDataset(), true); 
        if (argBean.getXslLayout() != null)
          addInvoiceReportRecord(argTransaction, invoiceType + "_LAYOUT", argBean
              .getXslLayout(), true); 
      } 
    } 
  }
  
  protected void addInvoiceReportRecord(IPosTransaction argTransaction, String argReportId, byte[] argData, boolean argInternalData) {
    ITransactionReportData data = (ITransactionReportData)DataFactory.createObject(ITransactionReportData.class);
    data.setOrganizationId(argTransaction.getOrganizationId());
    data.setRetailLocationId(argTransaction.getRetailLocationId());
    data.setBusinessDate(argTransaction.getBusinessDate());
    data.setWorkstationId(argTransaction.getWorkstationId());
    data.setTransactionSequence(argTransaction.getTransactionSequence());
    data.setReportId(argReportId);
    data.setReportBytes(argData);
    data.setInternalData(argInternalData);
    this._transactionHelper.getPersistables().addObject((IPersistable)data);
  }
  
  protected void addInvoiceXML(IInvoiceBean argBean, IPosTransaction argTransaction) {
    if (this._invoiceConfig.isSaveElectronicInvoice()) {
      TransactionAttachmentData invoiceRequestData = new TransactionAttachmentData();
      invoiceRequestData.setAttachmentType("INVOICE_PAYLOAD");
      invoiceRequestData.setAttachmentBytes(
          StringUtils.getBytes((String)getScopedValue(CountryValueKeys.CURRENT_EINVOICE_PAYLOAD)));
      this._tranAttachment.saveAttachment((PosTransactionId)argTransaction.getObjectId(), invoiceRequestData);
      EDocumentResponse response = (EDocumentResponse)getScopedValue(CountryValueKeys.CURRENT_EINVOICE_RESPONSE);
      this._eDocumentHelper.storeEInvoiceResponseAttachment(response, (PosTransactionId)argTransaction
          .getObjectId(), this._transactionHelper.getPersistables());
    } 
  }
  
  protected ISaleInvoice createInvoice() {
    IInvoiceBean bean = getInvoiceBean();
    ISaleInvoice invoice = bean.getInvoice();
    IPosTransaction currentTrans = getTransaction();
    invoice = this._invoiceHelper.createInvoice(bean, currentTrans);
    setTransactionPropertiesForCountries(bean, currentTrans);
    if (!StringUtils.isEmpty(bean.getXML()))
      addInvoiceXML(bean, currentTrans); 
    return invoice;
  }
  
  protected IInvoiceBean getInvoiceBean() {
    IInvoiceBean result = (IInvoiceBean)getScopedValue(CountryValueKeys.INVOICE_BEAN);
    if (result == null)
      logger_.error("Trying to create an invoice without a bean"); 
    return result;
  }
  
  protected void setTransactionPropertiesForCountries(IInvoiceBean argBean, IPosTransaction argTransaction) {}
  
  private IPosTransaction getTransaction() {
    IPosTransaction trn = this._transactionScope.getTransaction();
    if (trn == null)
      return (IPosTransaction)getScopedValue(ValueKeys.SELECTED_TRANSACTION); 
    return trn;
  }
}
