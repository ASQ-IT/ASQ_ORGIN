// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.GenerateIds 2024-08-11T15:27:53
// CHECKSTYLE:OFF
package dtv.asq.dao.zatca;

import java.util.Date;
import java.math.BigDecimal;
import dtv.util.common.CommonConstants;


/**
 * Auto generated Id Object for AsqZatcaInvoiceHashProperty.
 *
 * @author DAOGen
 */
@SuppressWarnings("all")
public class AsqZatcaInvoiceHashPropertyId
    extends dtv.data2.access.AbstractObjectId {

  // Fix serialization compatibility based on the name of the DAO
  private static final long serialVersionUID = -985156348L;

  public static final String[] FIELDS = { "organizationId", "icv", "propertyCode" };

  @Override
  public int getBaseVersion() {
    return 23;
  }

  public AsqZatcaInvoiceHashPropertyId() {
    super();    // default
  }

  public AsqZatcaInvoiceHashPropertyId(String argObjectIdValue) {
    setValue(argObjectIdValue);
  }

  public AsqZatcaInvoiceHashPropertyId(dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashPropertyDAO argDao) {
    _organizationId = argDao.getOrganizationId();
    _icv = argDao.getIcv();
    _propertyCode = argDao.getPropertyCode();
  }

  private Long _icv;
  private String _propertyCode;

  public String getDtxTypeName() {
    return "AsqZatcaInvoiceHashProperty";
  }

  @Override
  public String[] getFieldNames() {
    return FIELDS;
  }

  /**
   * Gets the value of the ICV field.
   * @return The value of the field.
   */
  public Long getIcv() {
    return _icv;
  }

  /**
   * Sets the value of the ICV field.
   * @param argIcv The new value of the field.
   */
  public void setIcv(Long argIcv) {
    _icv = argIcv;
  }

  /**
   * Gets the value of the property_code field.
   * @return The value of the field.
   */
  public String getPropertyCode() {
    return _propertyCode;
  }

  /**
   * Sets the value of the property_code field.
   * @param argPropertyCode The new value of the field.
   */
  public void setPropertyCode(String argPropertyCode) {
    _propertyCode = (argPropertyCode != null && MANAGE_CASE) ? argPropertyCode.toUpperCase() : argPropertyCode;
  }

  public void setValue(String argObjectIdValue) {
    String str = argObjectIdValue;
    if (dtv.util.StringUtils.isEmpty(str)) {
      throw new dtv.data2.access.exception.DtxException("argument passed to setValue() is null or empty - a valid value must be passed");
    }
    try {
      String[] tokens = str.split("::");
      str = tokens[0];

      setOrganizationId(java.lang.Long.valueOf(str));
      str = tokens[1];

      setIcv(java.lang.Long.valueOf(str));
      str = tokens[2];

      if ("null".equals(str)) {
        setPropertyCode(null);
      }
      else {
        setPropertyCode(str);
      }
    }
    catch (Exception ee) {
      throw new dtv.data2.access.exception.DtxException("An exception occured while parsing object id string: " + argObjectIdValue, ee);
    }
  }

  @Override
  public boolean equals(Object ob) {
    if (this == ob) {
      return true;
    }
    if (!(ob instanceof AsqZatcaInvoiceHashPropertyId)) {
      return false;
    }
    AsqZatcaInvoiceHashPropertyId other = (AsqZatcaInvoiceHashPropertyId) ob;
    return
          ((_organizationId == null && other._organizationId == null) ||
            (this._organizationId != null &&
             this._organizationId.equals(other._organizationId)))
        && ((_icv == null && other._icv == null) ||
            (this._icv != null &&
             this._icv.equals(other._icv)))
        && ((_propertyCode == null && other._propertyCode == null) ||
            (this._propertyCode != null &&
             this._propertyCode.equals(other._propertyCode)))
          ;
  }

  @Override
  public int hashCode() {
    return (
        ((_organizationId == null) ? 0 : _organizationId.hashCode())
        + ((_icv == null) ? 0 : _icv.hashCode())
        + ((_propertyCode == null) ? 0 : _propertyCode.hashCode())
        );
  }

  @Override
  public String toString() {
    StringBuilder buff = new StringBuilder(12 * 3);

    return buff.append(
      String.valueOf(_organizationId)).
      append("::").append(String.valueOf(_icv)).
      append("::").append(_propertyCode).
    toString();
  }

  public boolean validate() {
    if (_icv == null) {
      return false;
    }
    if (_propertyCode == null) {
      return false;
    }
    return true;
  }

} 