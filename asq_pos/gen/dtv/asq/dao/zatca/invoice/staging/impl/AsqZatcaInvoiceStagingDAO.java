// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.GenerateDaoAndDba 2024-08-22T18:06:17
// CHECKSTYLE:OFF
package dtv.asq.dao.zatca.invoice.staging.impl;

import java.util.Date;

import dtv.data2.access.IObjectId;

/**
 * Auto-generated DAO
 *
 * DO NOT MANUALLY MODIFY THIS FILE.
 *
 * @author DAOGen
 */
@SuppressWarnings("all")
public class AsqZatcaInvoiceStagingDAO
    extends dtv.data2.access.impl.AbstractDAOImpl
 {

  // Fix serialization compatibility based on the name of the DAO
  private static final long serialVersionUID = 586070938L;
  private Long _organizationId;
  private String _invoiceId;
  private dtv.util.DtvDate _businessDate;
  private Long _transactionSeq;
  private Long _workStationId;
  private Long _icv;
  private String _status;
  private byte[] _submittedJSON;
  private byte[] _invoiceQrCode;
  private String _invoiceUUID;
  private String _invoiceHashCode;
  private dtv.util.DtvDate _invoiceDate;
  private String _createUserId;
  private dtv.util.DtvDate _createDate;
  private String _updateUserId;
  private dtv.util.DtvDate _updateDate;

  /**
   * Gets the value of the organization_id field.
   * @return The value of the field.
   */
  public final Long getOrganizationId() {
    return _organizationId;
  }

  /**
   * Sets the value of the organization_id field.
   * @param argOrganizationId The new value of the field.
   */
  public final void setOrganizationId(Long argOrganizationId) {
    if (changed(argOrganizationId, _organizationId, "organizationId")) {
      _organizationId = argOrganizationId;
    }
  }

  /**
   * Gets the value of the INVOICE_ID field.
   * @return The value of the field.
   */
  public final String getInvoiceId() {
    return _invoiceId;
  }

  /**
   * Sets the value of the INVOICE_ID field.
   * @param argInvoiceId The new value of the field.
   */
  public final void setInvoiceId(String argInvoiceId) {
    if (changed(argInvoiceId, _invoiceId, "invoiceId")) {
      _invoiceId = (argInvoiceId != null && MANAGE_CASE) ? argInvoiceId.toUpperCase() : argInvoiceId;
    }
  }

  /**
   * Gets the value of the BUSINESS_DATE field.
   * @return The value of the field.
   */
  public final Date getBusinessDate() {
    return _businessDate;
  }

  /**
   * Sets the value of the BUSINESS_DATE field.
   * @param argBusinessDate The new value of the field.
   */
  public final void setBusinessDate(Date argBusinessDate) {
    if (changed(argBusinessDate, _businessDate, "businessDate")) {
      _businessDate = (argBusinessDate == null || argBusinessDate instanceof dtv.util.DtvDate) ? 
          (dtv.util.DtvDate) argBusinessDate : new dtv.util.DtvDate(argBusinessDate);
    }
  }

  /**
   * Gets the value of the TRANS_SEQ field.
   * @return The value of the field.
   */
  public final Long getTransactionSeq() {
    return _transactionSeq;
  }

  /**
   * Sets the value of the TRANS_SEQ field.
   * @param argTransactionSeq The new value of the field.
   */
  public final void setTransactionSeq(Long argTransactionSeq) {
    if (changed(argTransactionSeq, _transactionSeq, "transactionSeq")) {
      _transactionSeq = argTransactionSeq;
    }
  }

  /**
   * Gets the value of the wkstn_id field.
   * @return The value of the field.
   */
  public final Long getWorkStationId() {
    return _workStationId;
  }

  /**
   * Sets the value of the wkstn_id field.
   * @param argWorkStationId The new value of the field.
   */
  public final void setWorkStationId(Long argWorkStationId) {
    if (changed(argWorkStationId, _workStationId, "workStationId")) {
      _workStationId = argWorkStationId;
    }
  }

  /**
   * Gets the value of the ICV field.
   * @return The value of the field.
   */
  public final Long getIcv() {
    return _icv;
  }

  /**
   * Sets the value of the ICV field.
   * @param argIcv The new value of the field.
   */
  public final void setIcv(Long argIcv) {
    if (changed(argIcv, _icv, "icv")) {
      _icv = argIcv;
    }
  }

  /**
   * Gets the value of the Status field.
   * @return The value of the field.
   */
  public final String getStatus() {
    return _status;
  }

  /**
   * Sets the value of the Status field.
   * @param argStatus The new value of the field.
   */
  public final void setStatus(String argStatus) {
    if (changed(argStatus, _status, "status")) {
      _status = argStatus;
    }
  }

  /**
   * Gets the value of the INVOICE_XML field.
   * @return The value of the field.
   */
  public final byte[] getSubmittedJSON() {
    return _submittedJSON;
  }

  /**
   * Sets the value of the INVOICE_XML field.
   * @param argSubmittedJSON The new value of the field.
   */
  public final void setSubmittedJSON(byte[] argSubmittedJSON) {
    if (changed(argSubmittedJSON, _submittedJSON, "submittedJSON")) {
      _submittedJSON = argSubmittedJSON;
    }
  }

  /**
   * Gets the value of the INVOICE_QRCODE field.
   * @return The value of the field.
   */
  public final byte[] getInvoiceQrCode() {
    return _invoiceQrCode;
  }

  /**
   * Sets the value of the INVOICE_QRCODE field.
   * @param argInvoiceQrCode The new value of the field.
   */
  public final void setInvoiceQrCode(byte[] argInvoiceQrCode) {
    if (changed(argInvoiceQrCode, _invoiceQrCode, "invoiceQrCode")) {
      _invoiceQrCode = argInvoiceQrCode;
    }
  }

  /**
   * Gets the value of the INVOICE_UUID field.
   * @return The value of the field.
   */
  public final String getInvoiceUUID() {
    return _invoiceUUID;
  }

  /**
   * Sets the value of the INVOICE_UUID field.
   * @param argInvoiceUUID The new value of the field.
   */
  public final void setInvoiceUUID(String argInvoiceUUID) {
    if (changed(argInvoiceUUID, _invoiceUUID, "invoiceUUID")) {
      _invoiceUUID = argInvoiceUUID;
    }
  }

  /**
   * Gets the value of the INVOICE_HASHCODE field.
   * @return The value of the field.
   */
  public final String getInvoiceHashCode() {
    return _invoiceHashCode;
  }

  /**
   * Sets the value of the INVOICE_HASHCODE field.
   * @param argInvoiceHashCode The new value of the field.
   */
  public final void setInvoiceHashCode(String argInvoiceHashCode) {
    if (changed(argInvoiceHashCode, _invoiceHashCode, "invoiceHashCode")) {
      _invoiceHashCode = argInvoiceHashCode;
    }
  }

  /**
   * Gets the value of the INVOICE_DATE field.
   * @return The value of the field.
   */
  public final Date getInvoiceDate() {
    return _invoiceDate;
  }

  /**
   * Sets the value of the INVOICE_DATE field.
   * @param argInvoiceDate The new value of the field.
   */
  public final void setInvoiceDate(Date argInvoiceDate) {
    if (changed(argInvoiceDate, _invoiceDate, "invoiceDate")) {
      _invoiceDate = (argInvoiceDate == null || argInvoiceDate instanceof dtv.util.DtvDate) ? 
          (dtv.util.DtvDate) argInvoiceDate : new dtv.util.DtvDate(argInvoiceDate);
    }
  }

  /**
   * Gets the value of the create_user_id field.
   * @return The value of the field.
   */
  public final String getCreateUserId() {
    return _createUserId;
  }

  /**
   * Sets the value of the create_user_id field.
   * @param argCreateUserId The new value of the field.
   */
  public final void setCreateUserId(String argCreateUserId) {
    if (changed(argCreateUserId, _createUserId, "createUserId")) {
      _createUserId = argCreateUserId;
    }
  }

  /**
   * Gets the value of the create_date field.
   * @return The value of the field.
   */
  public final Date getCreateDate() {
    return _createDate;
  }

  /**
   * Sets the value of the create_date field.
   * @param argCreateDate The new value of the field.
   */
  public final void setCreateDate(Date argCreateDate) {
    if (changed(argCreateDate, _createDate, "createDate")) {
      _createDate = (argCreateDate == null || argCreateDate instanceof dtv.util.DtvDate) ? 
          (dtv.util.DtvDate) argCreateDate : new dtv.util.DtvDate(argCreateDate);
    }
  }

  /**
   * Gets the value of the update_user_id field.
   * @return The value of the field.
   */
  public final String getUpdateUserId() {
    return _updateUserId;
  }

  /**
   * Sets the value of the update_user_id field.
   * @param argUpdateUserId The new value of the field.
   */
  public final void setUpdateUserId(String argUpdateUserId) {
    if (changed(argUpdateUserId, _updateUserId, "updateUserId")) {
      _updateUserId = argUpdateUserId;
    }
  }

  /**
   * Gets the value of the update_date field.
   * @return The value of the field.
   */
  public final Date getUpdateDate() {
    return _updateDate;
  }

  /**
   * Sets the value of the update_date field.
   * @param argUpdateDate The new value of the field.
   */
  public final void setUpdateDate(Date argUpdateDate) {
    if (changed(argUpdateDate, _updateDate, "updateDate")) {
      _updateDate = (argUpdateDate == null || argUpdateDate instanceof dtv.util.DtvDate) ? 
          (dtv.util.DtvDate) argUpdateDate : new dtv.util.DtvDate(argUpdateDate);
    }
  }


  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(512);
    buf.append(super.toString()).append(" Id: ").append(getObjectId()).append(" Values: ");
    if (getOrganizationId() != null) {
      buf.append("organizationId").append("=").append(getOrganizationId()).append(" ");
    }
    if (getInvoiceId() != null) {
      buf.append("invoiceId").append("=").append(getInvoiceId()).append(" ");
    }
    if (getBusinessDate() != null) {
      buf.append("businessDate").append("=").append(getBusinessDate()).append(" ");
    }
    if (getTransactionSeq() != null) {
      buf.append("transactionSeq").append("=").append(getTransactionSeq()).append(" ");
    }
    if (getWorkStationId() != null) {
      buf.append("workStationId").append("=").append(getWorkStationId()).append(" ");
    }
    if (getIcv() != null) {
      buf.append("icv").append("=").append(getIcv()).append(" ");
    }
    if (getStatus() != null) {
      buf.append("status").append("=").append(getStatus()).append(" ");
    }
    if (getSubmittedJSON() != null) {
      buf.append("submittedJSON").append("=").append(dtv.util.ByteUtils.toString(getSubmittedJSON())).append(" ");
    }
    if (getInvoiceQrCode() != null) {
      buf.append("invoiceQrCode").append("=").append(dtv.util.ByteUtils.toString(getInvoiceQrCode())).append(" ");
    }
    if (getInvoiceUUID() != null) {
      buf.append("invoiceUUID").append("=").append(getInvoiceUUID()).append(" ");
    }
    if (getInvoiceHashCode() != null) {
      buf.append("invoiceHashCode").append("=").append(getInvoiceHashCode()).append(" ");
    }
    if (getInvoiceDate() != null) {
      buf.append("invoiceDate").append("=").append(getInvoiceDate()).append(" ");
    }
    if (getCreateUserId() != null) {
      buf.append("createUserId").append("=").append(getCreateUserId()).append(" ");
    }
    if (getCreateDate() != null) {
      buf.append("createDate").append("=").append(getCreateDate()).append(" ");
    }
    if (getUpdateUserId() != null) {
      buf.append("updateUserId").append("=").append(getUpdateUserId()).append(" ");
    }
    if (getUpdateDate() != null) {
      buf.append("updateDate").append("=").append(getUpdateDate()).append(" ");
    }

    return buf.toString();
  }

  @Override
  public String getDtxTypeName() {
    return "AsqZatcaInvoiceStaging";
  }

  @Override
  public IObjectId getObjectId() {
    return new dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingId(this);
  }

  @Override
  public void setObjectId(IObjectId argObjectId) {
    dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingId objectId = (dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingId) argObjectId;
    _organizationId = objectId.getOrganizationId();
    _invoiceId = objectId.getInvoiceId();
    _businessDate =  (objectId.getBusinessDate() == null || objectId.getBusinessDate() instanceof dtv.util.DtvDate) ? 
        (dtv.util.DtvDate) objectId.getBusinessDate() : new dtv.util.DtvDate(objectId.getBusinessDate());
    _transactionSeq = objectId.getTransactionSeq();
    _workStationId = objectId.getWorkStationId();
    _icv = objectId.getIcv();
  }

  /**
   * Gets the name of of the implementing class for this DAO.
   * @return Gets the name of of the implementing class for this DAO, or null if it is not extensible.
   */
  public String getImplementingClass() {
    return null; // This DAO is not extensible
  }

  @Override
  protected int getToStringBufferSize() {
    return 800;
  }

  private static final java.util.Collection<String> FIELD_NAMES;
  static {
    java.util.Collection<String> names = new java.util.LinkedList<>();
      names.add("OrganizationId");
      names.add("InvoiceId");
      names.add("BusinessDate");
      names.add("TransactionSeq");
      names.add("WorkStationId");
      names.add("Icv");
      names.add("Status");
      names.add("SubmittedJSON");
      names.add("InvoiceQrCode");
      names.add("InvoiceUUID");
      names.add("InvoiceHashCode");
      names.add("InvoiceDate");
      names.add("CreateUserId");
      names.add("CreateDate");
      names.add("UpdateUserId");
      names.add("UpdateDate");
      names.add("TransientObject");
      FIELD_NAMES = java.util.Collections.unmodifiableCollection(names);
  }

  @Override
  protected Iterable<String> getFieldNames() {
    return FIELD_NAMES;
  }

  @Override
  public Object getValue(String argFieldName) {
    switch (argFieldName) {
      case "OrganizationId": return _organizationId;
      case "InvoiceId": return _invoiceId;
      case "BusinessDate": return _businessDate;
      case "TransactionSeq": return _transactionSeq;
      case "WorkStationId": return _workStationId;
      case "Icv": return _icv;
      case "Status": return _status;
      case "SubmittedJSON": return _submittedJSON;
      case "InvoiceQrCode": return _invoiceQrCode;
      case "InvoiceUUID": return _invoiceUUID;
      case "InvoiceHashCode": return _invoiceHashCode;
      case "InvoiceDate": return _invoiceDate;
      case "CreateUserId": return _createUserId;
      case "CreateDate": return _createDate;
      case "UpdateUserId": return _updateUserId;
      case "UpdateDate": return _updateDate;
      default: return super.getValue(argFieldName);
    }
  }

  @Override
  public dtv.data2.access.FieldDataType getFieldType(String argFieldName) {
    switch (argFieldName) {
      case "OrganizationId": return dtv.data2.access.FieldDataType.LONG;
      case "InvoiceId": return dtv.data2.access.FieldDataType.STRING;
      case "BusinessDate": return dtv.data2.access.FieldDataType.DATE;
      case "TransactionSeq": return dtv.data2.access.FieldDataType.LONG;
      case "WorkStationId": return dtv.data2.access.FieldDataType.LONG;
      case "Icv": return dtv.data2.access.FieldDataType.LONG;
      case "Status": return dtv.data2.access.FieldDataType.STRING;
      case "SubmittedJSON": return dtv.data2.access.FieldDataType.OBJECT;
      case "InvoiceQrCode": return dtv.data2.access.FieldDataType.OBJECT;
      case "InvoiceUUID": return dtv.data2.access.FieldDataType.STRING;
      case "InvoiceHashCode": return dtv.data2.access.FieldDataType.STRING;
      case "InvoiceDate": return dtv.data2.access.FieldDataType.DATE;
      case "CreateUserId": return dtv.data2.access.FieldDataType.STRING;
      case "CreateDate": return dtv.data2.access.FieldDataType.DATE;
      case "UpdateUserId": return dtv.data2.access.FieldDataType.STRING;
      case "UpdateDate": return dtv.data2.access.FieldDataType.DATE;
      default: return super.getFieldType(argFieldName);
    }
  }

  @Override
  public void setValue(String argFieldName, Object argValue) {
    switch (argFieldName) {
      case "OrganizationId":
        try {
          setOrganizationId(valueForLong(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setOrganizationId() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "InvoiceId":
        try {
          setInvoiceId(valueForString(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setInvoiceId() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "BusinessDate":
        try {
          setBusinessDate(valueForDate(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setBusinessDate() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "TransactionSeq":
        try {
          setTransactionSeq(valueForLong(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setTransactionSeq() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "WorkStationId":
        try {
          setWorkStationId(valueForLong(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setWorkStationId() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "Icv":
        try {
          setIcv(valueForLong(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setIcv() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "Status":
        try {
          setStatus(valueForString(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setStatus() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "SubmittedJSON":
        try {
          setSubmittedJSON(valueForObject(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setSubmittedJSON() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "InvoiceQrCode":
        try {
          setInvoiceQrCode(valueForObject(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setInvoiceQrCode() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "InvoiceUUID":
        try {
          setInvoiceUUID(valueForString(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setInvoiceUUID() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "InvoiceHashCode":
        try {
          setInvoiceHashCode(valueForString(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setInvoiceHashCode() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "InvoiceDate":
        try {
          setInvoiceDate(valueForDate(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setInvoiceDate() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "CreateUserId":
        try {
          setCreateUserId(valueForString(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setCreateUserId() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "CreateDate":
        try {
          setCreateDate(valueForDate(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setCreateDate() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "UpdateUserId":
        try {
          setUpdateUserId(valueForString(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setUpdateUserId() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "UpdateDate":
        try {
          setUpdateDate(valueForDate(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setUpdateDate() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      default:
        super.setValue(argFieldName, argValue);
        break;
    }
  }
} 