// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.GenerateIds 2024-08-22T18:06:17
// CHECKSTYLE:OFF
package dtv.asq.dao.zatca.invoice.staging;

import java.util.Date;
import java.math.BigDecimal;
import dtv.util.common.CommonConstants;


/**
 * Auto generated Id Object for AsqZatcaInvoiceStagingProperty.
 *
 * @author DAOGen
 */
@SuppressWarnings("all")
public class AsqZatcaInvoiceStagingPropertyId
    extends dtv.data2.access.AbstractObjectId {

  // Fix serialization compatibility based on the name of the DAO
  private static final long serialVersionUID = 1272365967L;

  public static final String[] FIELDS = { "organizationId", "invoiceId", "businessDate", "transactionSeq", "workStationId", "icv", "propertyCode" };

  @Override
  public int getBaseVersion() {
    return 23;
  }

  public AsqZatcaInvoiceStagingPropertyId() {
    super();    // default
  }

  public AsqZatcaInvoiceStagingPropertyId(String argObjectIdValue) {
    setValue(argObjectIdValue);
  }

  public AsqZatcaInvoiceStagingPropertyId(dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingPropertyDAO argDao) {
    _organizationId = argDao.getOrganizationId();
    _invoiceId = argDao.getInvoiceId();
    _businessDate =  (argDao.getBusinessDate() == null || argDao.getBusinessDate() instanceof dtv.util.DtvDate) ? 
        (dtv.util.DtvDate) argDao.getBusinessDate() : new dtv.util.DtvDate(argDao.getBusinessDate());
    _transactionSeq = argDao.getTransactionSeq();
    _workStationId = argDao.getWorkStationId();
    _icv = argDao.getIcv();
    _propertyCode = argDao.getPropertyCode();
  }

  private String _invoiceId;
  private dtv.util.DtvDate _businessDate;
  private Long _transactionSeq;
  private Long _workStationId;
  private Long _icv;
  private String _propertyCode;

  public String getDtxTypeName() {
    return "AsqZatcaInvoiceStagingProperty";
  }

  @Override
  public String[] getFieldNames() {
    return FIELDS;
  }

  /**
   * Gets the value of the INVOICE_ID field.
   * @return The value of the field.
   */
  public String getInvoiceId() {
    return _invoiceId;
  }

  /**
   * Sets the value of the INVOICE_ID field.
   * @param argInvoiceId The new value of the field.
   */
  public void setInvoiceId(String argInvoiceId) {
    _invoiceId = (argInvoiceId != null && MANAGE_CASE) ? argInvoiceId.toUpperCase() : argInvoiceId;
  }

  /**
   * Gets the value of the BUSINESS_DATE field.
   * @return The value of the field.
   */
  public Date getBusinessDate() {
    return _businessDate;
  }

  /**
   * Sets the value of the BUSINESS_DATE field.
   * @param argBusinessDate The new value of the field.
   */
  public void setBusinessDate(Date argBusinessDate) {
    _businessDate = (argBusinessDate == null || argBusinessDate instanceof dtv.util.DtvDate) ? 
        (dtv.util.DtvDate) argBusinessDate : new dtv.util.DtvDate(argBusinessDate);
  }

  /**
   * Gets the value of the TRANS_SEQ field.
   * @return The value of the field.
   */
  public Long getTransactionSeq() {
    return _transactionSeq;
  }

  /**
   * Sets the value of the TRANS_SEQ field.
   * @param argTransactionSeq The new value of the field.
   */
  public void setTransactionSeq(Long argTransactionSeq) {
    _transactionSeq = argTransactionSeq;
  }

  /**
   * Gets the value of the wkstn_id field.
   * @return The value of the field.
   */
  public Long getWorkStationId() {
    return _workStationId;
  }

  /**
   * Sets the value of the wkstn_id field.
   * @param argWorkStationId The new value of the field.
   */
  public void setWorkStationId(Long argWorkStationId) {
    _workStationId = argWorkStationId;
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

      if ("null".equals(str)) {
        setInvoiceId(null);
      }
      else {
        setInvoiceId(str);
      }
      str = tokens[2];

      if ("null".equals(str)) {
        setBusinessDate(null);
      }
      else {
        setBusinessDate(new dtv.util.DtvDate());
        _businessDate.setTimeFromSerialization(Long.parseLong(str));
      }
      str = tokens[3];

      setTransactionSeq(java.lang.Long.valueOf(str));
      str = tokens[4];

      setWorkStationId(java.lang.Long.valueOf(str));
      str = tokens[5];

      setIcv(java.lang.Long.valueOf(str));
      str = tokens[6];

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
    if (!(ob instanceof AsqZatcaInvoiceStagingPropertyId)) {
      return false;
    }
    AsqZatcaInvoiceStagingPropertyId other = (AsqZatcaInvoiceStagingPropertyId) ob;
    return
          ((_organizationId == null && other._organizationId == null) ||
            (this._organizationId != null &&
             this._organizationId.equals(other._organizationId)))
        && ((_invoiceId == null && other._invoiceId == null) ||
            (this._invoiceId != null &&
             this._invoiceId.equals(other._invoiceId)))
        && ((_businessDate == null && other._businessDate == null) ||
            (this._businessDate != null &&
             this._businessDate.equals((Object)other._businessDate)))
        && ((_transactionSeq == null && other._transactionSeq == null) ||
            (this._transactionSeq != null &&
             this._transactionSeq.equals(other._transactionSeq)))
        && ((_workStationId == null && other._workStationId == null) ||
            (this._workStationId != null &&
             this._workStationId.equals(other._workStationId)))
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
        + ((_invoiceId == null) ? 0 : _invoiceId.hashCode())
        + ((_businessDate == null) ? 0 : _businessDate.hashCode())
        + ((_transactionSeq == null) ? 0 : _transactionSeq.hashCode())
        + ((_workStationId == null) ? 0 : _workStationId.hashCode())
        + ((_icv == null) ? 0 : _icv.hashCode())
        + ((_propertyCode == null) ? 0 : _propertyCode.hashCode())
        );
  }

  @Override
  public String toString() {
    StringBuilder buff = new StringBuilder(12 * 7);

    return buff.append(
      String.valueOf(_organizationId)).
      append("::").append(_invoiceId).
      append("::").append(_businessDate == null ? "null" : String.valueOf(_businessDate.getTimeSerializable())).
      append("::").append(String.valueOf(_transactionSeq)).
      append("::").append(String.valueOf(_workStationId)).
      append("::").append(String.valueOf(_icv)).
      append("::").append(_propertyCode).
    toString();
  }

  public boolean validate() {
    if (_invoiceId == null) {
      return false;
    }
    if (_businessDate == null) {
      return false;
    }
    if (_transactionSeq == null) {
      return false;
    }
    if (_workStationId == null) {
      return false;
    }
    if (_icv == null) {
      return false;
    }
    if (_propertyCode == null) {
      return false;
    }
    return true;
  }

} 