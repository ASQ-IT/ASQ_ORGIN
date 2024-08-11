// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.GenerateDaoAndDba 2024-08-11T15:27:53
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
public class AsqZatcaInvoiceHashPropertyDAO
    extends dtv.data2.access.impl.AbstractDAOImpl
 {

  // Fix serialization compatibility based on the name of the DAO
  private static final long serialVersionUID = -985156348L;
  private Long _organizationId;
  private Long _icv;
  private String _propertyCode;
  private String _type;
  private String _stringValue;
  private dtv.util.DtvDate _dateValue;
  private java.math.BigDecimal _decimalValue;
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
   * Gets the value of the property_code field.
   * @return The value of the field.
   */
  public final String getPropertyCode() {
    return _propertyCode;
  }

  /**
   * Sets the value of the property_code field.
   * @param argPropertyCode The new value of the field.
   */
  public final void setPropertyCode(String argPropertyCode) {
    if (changed(argPropertyCode, _propertyCode, "propertyCode")) {
      _propertyCode = (argPropertyCode != null && MANAGE_CASE) ? argPropertyCode.toUpperCase() : argPropertyCode;
    }
  }

  /**
   * Gets the value of the type field.
   * @return The value of the field.
   */
  public final String getType() {
    return _type;
  }

  /**
   * Sets the value of the type field.
   * @param argType The new value of the field.
   */
  public final void setType(String argType) {
    if (changed(argType, _type, "type")) {
      _type = argType;
    }
  }

  /**
   * Gets the value of the string_value field.
   * @return The value of the field.
   */
  public final String getStringValue() {
    return _stringValue;
  }

  /**
   * Sets the value of the string_value field.
   * @param argStringValue The new value of the field.
   */
  public final void setStringValue(String argStringValue) {
    if (changed(argStringValue, _stringValue, "stringValue")) {
      _stringValue = argStringValue;
    }
  }

  /**
   * Gets the value of the date_value field.
   * @return The value of the field.
   */
  public final Date getDateValue() {
    return _dateValue;
  }

  /**
   * Sets the value of the date_value field.
   * @param argDateValue The new value of the field.
   */
  public final void setDateValue(Date argDateValue) {
    if (changed(argDateValue, _dateValue, "dateValue")) {
      _dateValue = (argDateValue == null || argDateValue instanceof dtv.util.DtvDate) ? 
          (dtv.util.DtvDate) argDateValue : new dtv.util.DtvDate(argDateValue);
    }
  }

  /**
   * Gets the value of the decimal_value field.
   * @return The value of the field.
   */
  public final java.math.BigDecimal getDecimalValue() {
    return _decimalValue;
  }

  /**
   * Sets the value of the decimal_value field.
   * @param argDecimalValue The new value of the field.
   */
  public final void setDecimalValue(java.math.BigDecimal argDecimalValue) {
    if (changed(argDecimalValue, _decimalValue, "decimalValue")) {
      _decimalValue = argDecimalValue;
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
    if (getPropertyCode() != null) {
      buf.append("propertyCode").append("=").append(getPropertyCode()).append(" ");
    }
    if (getType() != null) {
      buf.append("type").append("=").append(getType()).append(" ");
    }
    if (getStringValue() != null) {
      buf.append("stringValue").append("=").append(getStringValue()).append(" ");
    }
    if (getDateValue() != null) {
      buf.append("dateValue").append("=").append(getDateValue()).append(" ");
    }
    if (getDecimalValue() != null) {
      buf.append("decimalValue").append("=").append(getDecimalValue()).append(" ");
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
    return "AsqZatcaInvoiceHashProperty";
  }

  @Override
  public IObjectId getObjectId() {
    return new dtv.asq.dao.zatca.AsqZatcaInvoiceHashPropertyId(this);
  }

  @Override
  public void setObjectId(IObjectId argObjectId) {
    dtv.asq.dao.zatca.AsqZatcaInvoiceHashPropertyId objectId = (dtv.asq.dao.zatca.AsqZatcaInvoiceHashPropertyId) argObjectId;
    _organizationId = objectId.getOrganizationId();
    _icv = objectId.getIcv();
    _propertyCode = objectId.getPropertyCode();
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
    return 550;
  }

  private static final java.util.Collection<String> FIELD_NAMES;
  static {
    java.util.Collection<String> names = new java.util.LinkedList<>();
      names.add("OrganizationId");
      names.add("Icv");
      names.add("PropertyCode");
      names.add("Type");
      names.add("StringValue");
      names.add("DateValue");
      names.add("DecimalValue");
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
      case "PropertyCode": return _propertyCode;
      case "Type": return _type;
      case "StringValue": return _stringValue;
      case "DateValue": return _dateValue;
      case "DecimalValue": return _decimalValue;
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
      case "PropertyCode": return dtv.data2.access.FieldDataType.STRING;
      case "Type": return dtv.data2.access.FieldDataType.STRING;
      case "StringValue": return dtv.data2.access.FieldDataType.STRING;
      case "DateValue": return dtv.data2.access.FieldDataType.DATE;
      case "DecimalValue": return dtv.data2.access.FieldDataType.DECIMAL;
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
      case "PropertyCode":
        try {
          setPropertyCode(valueForString(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setPropertyCode() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "Type":
        try {
          setType(valueForString(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setType() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "StringValue":
        try {
          setStringValue(valueForString(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setStringValue() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "DateValue":
        try {
          setDateValue(valueForDate(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setDateValue() with " + argValue + " on: " + this + " " + ee.toString(), ee);
        }
        break;
      case "DecimalValue":
        try {
          setDecimalValue(valueForBigDecimal(argValue));
        }
        catch (Exception ee) {
          throw new dtv.data2.access.exception.DtxException("An exception occurred while calling setDecimalValue() with " + argValue + " on: " + this + " " + ee.toString(), ee);
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