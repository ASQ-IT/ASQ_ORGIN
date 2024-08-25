// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.GenerateInterfaces 2024-08-22T18:06:17
// CHECKSTYLE:OFF
package dtv.asq.dao.zatca.invoice.staging;

import java.util.Date;
import dtv.data2.access.IDataModel;


/**
 * Generated code.
 *
 * @author DAOGen
 */
@SuppressWarnings("all")
public interface IAsqZatcaInvoiceStaging extends IDataModel, dtv.data2.access.IHasDataProperty<dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty> {
  // Events which may be posted by implementors of this model.
  public static final dtv.event.EventEnum SET_ORGANIZATIONID = new dtv.event.EventEnum("set organizationId");
  public static final dtv.event.EventEnum SET_INVOICEID = new dtv.event.EventEnum("set invoiceId");
  public static final dtv.event.EventEnum SET_BUSINESSDATE = new dtv.event.EventEnum("set businessDate");
  public static final dtv.event.EventEnum SET_TRANSACTIONSEQ = new dtv.event.EventEnum("set transactionSeq");
  public static final dtv.event.EventEnum SET_WORKSTATIONID = new dtv.event.EventEnum("set workStationId");
  public static final dtv.event.EventEnum SET_ICV = new dtv.event.EventEnum("set icv");
  public static final dtv.event.EventEnum SET_STATUS = new dtv.event.EventEnum("set status");
  public static final dtv.event.EventEnum SET_SUBMITTEDJSON = new dtv.event.EventEnum("set submittedJSON");
  public static final dtv.event.EventEnum SET_INVOICEQRCODE = new dtv.event.EventEnum("set invoiceQrCode");
  public static final dtv.event.EventEnum SET_INVOICEUUID = new dtv.event.EventEnum("set invoiceUUID");
  public static final dtv.event.EventEnum SET_INVOICEHASHCODE = new dtv.event.EventEnum("set invoiceHashCode");
  public static final dtv.event.EventEnum SET_INVOICEDATE = new dtv.event.EventEnum("set invoiceDate");
  public static final dtv.event.EventEnum SET_CREATEUSERID = new dtv.event.EventEnum("set createUserId");
  public static final dtv.event.EventEnum SET_CREATEDATE = new dtv.event.EventEnum("set createDate");
  public static final dtv.event.EventEnum SET_UPDATEUSERID = new dtv.event.EventEnum("set updateUserId");
  public static final dtv.event.EventEnum SET_UPDATEDATE = new dtv.event.EventEnum("set updateDate");
  public static final dtv.event.EventEnum ADD_PROPERTIES = new dtv.event.EventEnum("add Properties");
  public static final dtv.event.EventEnum REMOVE_PROPERTIES = new dtv.event.EventEnum("remove Properties");
  public static final dtv.event.EventEnum SET_PROPERTIES = new dtv.event.EventEnum("set Properties");
  public static final dtv.event.EventEnum START_TRANSACTION = new dtv.event.EventEnum("start transaction");
  public static final dtv.event.EventEnum ROLLBACK_TRANSACTION = new dtv.event.EventEnum("rollback transaction");
  public static final dtv.event.EventEnum COMMIT_TRANSACTION = new dtv.event.EventEnum("commit transaction");


  // Methods related to DAO methods.  public void initDAO();
  public void setDAO(dtv.data2.access.IDataAccessObject argDAO);
  /**
   * Getter for organizationId.
   * @return DAO alias for column organization_id
   */
  public long getOrganizationId();

  /**
   * Setter for organizationId.
   * @param argOrganizationId DAO alias for column organization_id
   */
  public void setOrganizationId(long argOrganizationId);

  /**
   * Getter for invoiceId.
   * @return DAO alias for column INVOICE_ID
   */
  public String getInvoiceId();

  /**
   * Setter for invoiceId.
   * @param argInvoiceId DAO alias for column INVOICE_ID
   */
  public void setInvoiceId(String argInvoiceId);

  /**
   * Getter for businessDate.
   * @return DAO alias for column BUSINESS_DATE
   */
  public Date getBusinessDate();

  /**
   * Setter for businessDate.
   * @param argBusinessDate DAO alias for column BUSINESS_DATE
   */
  public void setBusinessDate(Date argBusinessDate);

  /**
   * Getter for transactionSeq.
   * @return DAO alias for column TRANS_SEQ
   */
  public long getTransactionSeq();

  /**
   * Setter for transactionSeq.
   * @param argTransactionSeq DAO alias for column TRANS_SEQ
   */
  public void setTransactionSeq(long argTransactionSeq);

  /**
   * Getter for workStationId.
   * @return DAO alias for column wkstn_id
   */
  public long getWorkStationId();

  /**
   * Setter for workStationId.
   * @param argWorkStationId DAO alias for column wkstn_id
   */
  public void setWorkStationId(long argWorkStationId);

  /**
   * Getter for icv.
   * @return DAO alias for column ICV
   */
  public long getIcv();

  /**
   * Setter for icv.
   * @param argIcv DAO alias for column ICV
   */
  public void setIcv(long argIcv);

  /**
   * Getter for createUserId.
   * The ID of the user who created the record.
   * @return DAO alias for column create_user_id
   */
  public String getCreateUserId();

  /**
   * Setter for createUserId.
   * @param argCreateUserId DAO alias for column create_user_id
   */
  public void setCreateUserId(String argCreateUserId);

  /**
   * Getter for createDate.
   * The date and time the record was created.
   * @return DAO alias for column create_date
   */
  public Date getCreateDate();

  /**
   * Setter for createDate.
   * @param argCreateDate DAO alias for column create_date
   */
  public void setCreateDate(Date argCreateDate);

  /**
   * Getter for updateUserId.
   * The ID of the user who last updated the record.
   * @return DAO alias for column update_user_id
   */
  public String getUpdateUserId();

  /**
   * Setter for updateUserId.
   * @param argUpdateUserId DAO alias for column update_user_id
   */
  public void setUpdateUserId(String argUpdateUserId);

  /**
   * Getter for updateDate.
   * The date and the record was last updated.
   * @return DAO alias for column update_date
   */
  public Date getUpdateDate();

  /**
   * Setter for updateDate.
   * @param argUpdateDate DAO alias for column update_date
   */
  public void setUpdateDate(Date argUpdateDate);

  /**
   * This method is used to get a customer extention object, if one is present.
   * @return IDataModel - this will either be null or a cust extention model.
   */
  public IDataModel getAsqZatcaInvoiceStagingExt();

  public void setAsqZatcaInvoiceStagingExt(IDataModel argExt);

  // Methods related to data relationships
  public java.util.List<dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty> getProperties();
  public void setProperties(java.util.List<dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty> argProperties);
  public void addAsqZatcaInvoiceStagingProperty(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty argAsqZatcaInvoiceStagingProperty);
  public void removeAsqZatcaInvoiceStagingProperty(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty argAsqZatcaInvoiceStagingProperty);

  // Methods required for transactions
  public void startTransaction();
  public void rollbackChanges();
  public void commitTransaction();
}
