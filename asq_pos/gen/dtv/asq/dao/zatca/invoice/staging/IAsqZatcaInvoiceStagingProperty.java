// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.GenerateInterfaces 2024-08-11T15:27:53
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
public interface IAsqZatcaInvoiceStagingProperty extends IDataModel, dtv.data2.access.IDataProperty {
  // Events which may be posted by implementors of this model.
  public static final dtv.event.EventEnum SET_ORGANIZATIONID = new dtv.event.EventEnum("set organizationId");
  public static final dtv.event.EventEnum SET_INVOICEID = new dtv.event.EventEnum("set invoiceId");
  public static final dtv.event.EventEnum SET_BUSINESSDATE = new dtv.event.EventEnum("set businessDate");
  public static final dtv.event.EventEnum SET_TRANSACTIONSEQ = new dtv.event.EventEnum("set transactionSeq");
  public static final dtv.event.EventEnum SET_WORKSTATIONID = new dtv.event.EventEnum("set workStationId");
  public static final dtv.event.EventEnum SET_PROPERTYCODE = new dtv.event.EventEnum("set propertyCode");
  public static final dtv.event.EventEnum SET_TYPE = new dtv.event.EventEnum("set type");
  public static final dtv.event.EventEnum SET_STRINGVALUE = new dtv.event.EventEnum("set stringValue");
  public static final dtv.event.EventEnum SET_DATEVALUE = new dtv.event.EventEnum("set dateValue");
  public static final dtv.event.EventEnum SET_DECIMALVALUE = new dtv.event.EventEnum("set decimalValue");
  public static final dtv.event.EventEnum SET_CREATEUSERID = new dtv.event.EventEnum("set createUserId");
  public static final dtv.event.EventEnum SET_CREATEDATE = new dtv.event.EventEnum("set createDate");
  public static final dtv.event.EventEnum SET_UPDATEUSERID = new dtv.event.EventEnum("set updateUserId");
  public static final dtv.event.EventEnum SET_UPDATEDATE = new dtv.event.EventEnum("set updateDate");
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
   * Getter for propertyCode.
   * The name of the property.
   * @return DAO alias for column property_code
   */
  public String getPropertyCode();

  /**
   * Setter for propertyCode.
   * @param argPropertyCode DAO alias for column property_code
   */
  public void setPropertyCode(String argPropertyCode);

  /**
   * Getter for type.
   * The type of property. Valid values are 'STRING', 'DATE', 'BIGDECIMAL', and 'BOOLEAN'.
   * @return DAO alias for column type
   */
  public String getType();

  /**
   * Setter for type.
   * @param argType DAO alias for column type
   */
  public void setType(String argType);

  /**
   * Getter for stringValue.
   * The value of the property if 'type' is 'STRING'.
   * @return DAO alias for column string_value
   */
  public String getStringValue();

  /**
   * Setter for stringValue.
   * @param argStringValue DAO alias for column string_value
   */
  public void setStringValue(String argStringValue);

  /**
   * Getter for dateValue.
   * The value of the property if 'type' is 'DATE'.
   * @return DAO alias for column date_value
   */
  public Date getDateValue();

  /**
   * Setter for dateValue.
   * @param argDateValue DAO alias for column date_value
   */
  public void setDateValue(Date argDateValue);

  /**
   * Getter for decimalValue.
   * The value of the property if 'type' is 'BIGDECIMAL' or 'BOOLEAN'. For 'BOOLEAN' type, the value should be 0 or 1.
   * @return DAO alias for column decimal_value
   */
  public java.math.BigDecimal getDecimalValue();

  /**
   * Setter for decimalValue.
   * @param argDecimalValue DAO alias for column decimal_value
   */
  public void setDecimalValue(java.math.BigDecimal argDecimalValue);

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

  // Methods related to data relationships

  // Methods required for transactions
  public void startTransaction();
  public void rollbackChanges();
  public void commitTransaction();
}
