// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.GenerateDaoAndDba 2024-07-13T15:19:37
// CHECKSTYLE:OFF
package dtv.asq.dao.zatca.impl;

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
public class AsqZatcaInvoiceHashDAO
    extends dtv.data2.access.impl.AbstractDAOImpl
 {

  // Fix serialization compatibility based on the name of the DAO
  private static final long serialVersionUID = 699331087L;
  private Long _organizationId;
  private Long _icv;
  private String _invoiceHash;
  private String _invoiceId;
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
   * Gets the value of the INVOICE_HASH field.
   * @return The value of the field.
   */
  public final String getInvoiceHash() {
    return _invoiceHash;
  }

  /**
   * Sets the value of the INVOICE_HASH field.
   * @param argInvoiceHash The new value of the field.
   */
  public final void setInvoiceHash(String argInvoiceHash) {
    if (changed(argInvoiceHash, _invoiceHash, "invoiceHash")) {
      _invoiceHash = argInvoiceHash;
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
      _invoiceId = argInvoiceId;
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
    if (getIcv() != null) {
      buf.append("icv").append("=").append(getIcv()).append(" ");
    }
    if (getInvoiceHash() != null) {
      buf.append("invoiceHash").append("=").append(getInvoiceHash()).append(" ");
    }
    if (getInvoiceId() != null) {
      buf.append("invoiceId").append("=").append(getInvoiceId()).append(" ");
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
    return "AsqZatcaInvoiceHash";
  }

  @Override
  public IObjectId getObjectId() {
    return new dtv.asq.dao.zatca.AsqZatcaInvoiceHashId(this);
  }

  @Override
  public void setObjectId(IObjectId argObjectId) {
    dtv.asq.dao.zatca.AsqZatcaInvoiceHashId objectId = (dtv.asq.dao.zatca.AsqZatcaInvoiceHashId) argObjectId;
    _organizationId = objectId.getOrganizationId();
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
    return 450;
  }

  private static final java.util.Collection<String> FIELD_NAMES;
  static {
    java.util.Collection<String> names = new java.util.LinkedList<>();
      names.add("OrganizationId");
      names.add("Icv");
      names.add("InvoiceHash");
      names.add("InvoiceId");
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
      case "Icv": return _icv;
      case "InvoiceHash": return _invoiceHash;
      case "InvoiceId": return _invoiceId;
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
      case "Icv": return dtv.data2.access.FieldDataType.LONG;
      case "InvoiceHash": return dtv.data2.access.FieldDataType.STRING;
      case "InvoiceId": return dtv.data2.access.FieldDataType.STRING;
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
      case "Icv":
        try {
          setIcv(valueForLong(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setIcv() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "InvoiceHash":
        try {
          setInvoiceHash(valueForString(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setInvoiceHash() with " + argValue + " on: " + this + " " + ee.toString(), ee);
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