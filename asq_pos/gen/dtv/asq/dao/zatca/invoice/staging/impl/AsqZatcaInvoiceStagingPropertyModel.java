// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.DefaultModelGenerator 2024-08-22T18:06:17
// CHECKSTYLE:OFF
package dtv.asq.dao.zatca.invoice.staging.impl;

import java.math.BigDecimal;
import java.util.*;
import dtv.data2.access.impl.*;
import dtv.data2.access.IDataModel;
import dtv.data2.access.ModelEventor;
import dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingPropertyId;

/**
 * Auto Generated Model.
 *
 * @author DAOGen
 */
@SuppressWarnings("all")
public class AsqZatcaInvoiceStagingPropertyModel 
  extends dtv.data2.access.impl.AbstractDataModelPropertiesImpl
  implements dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty {

  // Fix serialization compatibility based on the name of the DAO
  private static final long serialVersionUID = 1272365967L;


  private transient boolean _alreadyInStart = false;
  private transient boolean _alreadyInCommit = false;

  private IDataModel _myExtension;

  @Override
  public String toString() {
    return super.toString() + " Id: " + this.getObjectId();
  }

  @Override
  public void initDAO() {
    setDAO(new AsqZatcaInvoiceStagingPropertyDAO());
  }

  /**
   * Return the data access object associated with this data model
   * @return our DAO, properly casted
   */
  private AsqZatcaInvoiceStagingPropertyDAO getDAO_() {
     return (AsqZatcaInvoiceStagingPropertyDAO) _daoImpl;
  }

  /**
   * Gets the value of the organization_id field.
   * @return The value of the field.
   */
  public long getOrganizationId() {
    if (getDAO_().getOrganizationId() != null) {
      return getDAO_().getOrganizationId().longValue();
    }
    else {
      return 0; // no default specified in the dtx; we default to 0
    }
  }

  /**
   * Sets the value of the organization_id field.
   * @param argOrganizationId The new value for the field.
   */
  public void setOrganizationId(long argOrganizationId) {
    if (setOrganizationId_noev(argOrganizationId)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty.SET_ORGANIZATIONID, Long.valueOf(argOrganizationId));
        }
      }
    }
  }

  public boolean setOrganizationId_noev(long argOrganizationId) {
    boolean ev_postable = false;

    if ((getDAO_().getOrganizationId() == null && Long.valueOf(argOrganizationId) != null) ||
        (getDAO_().getOrganizationId() != null && !getDAO_().getOrganizationId().equals(Long.valueOf(argOrganizationId)))) {
      getDAO_().setOrganizationId(Long.valueOf(argOrganizationId));
      ev_postable = true;
      if (_myExtension != null) {
        // Propagate changes to customer extension object.
        dtv.util.ObjectUtils.invokeMethod("setOrganizationId_noev", _myExtension, new Class<?>[] {long.class}, new Object[] {argOrganizationId}, null);
      }
    }

    return ev_postable;
  }

  /**
   * Gets the value of the INVOICE_ID field.
   * @return The value of the field.
   */
  public String getInvoiceId() {
    return getDAO_().getInvoiceId();
  }

  /**
   * Sets the value of the INVOICE_ID field.
   * @param argInvoiceId The new value for the field.
   */
  public void setInvoiceId(String argInvoiceId) {
    if (setInvoiceId_noev(argInvoiceId)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty.SET_INVOICEID, argInvoiceId);
        }
      }
    }
  }

  public boolean setInvoiceId_noev(String argInvoiceId) {
    boolean ev_postable = false;

    if ((getDAO_().getInvoiceId() == null && argInvoiceId != null) ||
        (getDAO_().getInvoiceId() != null && !getDAO_().getInvoiceId().equals(argInvoiceId))) {
      getDAO_().setInvoiceId(argInvoiceId);
      ev_postable = true;
      if (_myExtension != null) {
        // Propagate changes to customer extension object.
        dtv.util.ObjectUtils.invokeMethod("setInvoiceId_noev", _myExtension, new Class<?>[] {String.class}, new Object[] {argInvoiceId}, null);
      }
    }

    return ev_postable;
  }

  /**
   * Gets the value of the BUSINESS_DATE field.
   * @return The value of the field.
   */
  public Date getBusinessDate() {
    return getDAO_().getBusinessDate();
  }

  /**
   * Sets the value of the BUSINESS_DATE field.
   * @param argBusinessDate The new value for the field.
   */
  public void setBusinessDate(Date argBusinessDate) {
    if (setBusinessDate_noev(argBusinessDate)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty.SET_BUSINESSDATE, argBusinessDate);
        }
      }
    }
  }

  public boolean setBusinessDate_noev(Date argBusinessDate) {
    boolean ev_postable = false;

    if ((getDAO_().getBusinessDate() == null && argBusinessDate != null) ||
        (getDAO_().getBusinessDate() != null && !getDAO_().getBusinessDate().equals(argBusinessDate))) {
      getDAO_().setBusinessDate(argBusinessDate);
      ev_postable = true;
      if (_myExtension != null) {
        // Propagate changes to customer extension object.
        dtv.util.ObjectUtils.invokeMethod("setBusinessDate_noev", _myExtension, new Class<?>[] {Date.class}, new Object[] {argBusinessDate}, null);
      }
    }

    return ev_postable;
  }

  /**
   * Gets the value of the TRANS_SEQ field.
   * @return The value of the field.
   */
  public long getTransactionSeq() {
    if (getDAO_().getTransactionSeq() != null) {
      return getDAO_().getTransactionSeq().longValue();
    }
    else {
      return 0; // no default specified in the dtx; we default to 0
    }
  }

  /**
   * Sets the value of the TRANS_SEQ field.
   * @param argTransactionSeq The new value for the field.
   */
  public void setTransactionSeq(long argTransactionSeq) {
    if (setTransactionSeq_noev(argTransactionSeq)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty.SET_TRANSACTIONSEQ, Long.valueOf(argTransactionSeq));
        }
      }
    }
  }

  public boolean setTransactionSeq_noev(long argTransactionSeq) {
    boolean ev_postable = false;

    if ((getDAO_().getTransactionSeq() == null && Long.valueOf(argTransactionSeq) != null) ||
        (getDAO_().getTransactionSeq() != null && !getDAO_().getTransactionSeq().equals(Long.valueOf(argTransactionSeq)))) {
      getDAO_().setTransactionSeq(Long.valueOf(argTransactionSeq));
      ev_postable = true;
      if (_myExtension != null) {
        // Propagate changes to customer extension object.
        dtv.util.ObjectUtils.invokeMethod("setTransactionSeq_noev", _myExtension, new Class<?>[] {long.class}, new Object[] {argTransactionSeq}, null);
      }
    }

    return ev_postable;
  }

  /**
   * Gets the value of the wkstn_id field.
   * @return The value of the field.
   */
  public long getWorkStationId() {
    if (getDAO_().getWorkStationId() != null) {
      return getDAO_().getWorkStationId().longValue();
    }
    else {
      return 0; // no default specified in the dtx; we default to 0
    }
  }

  /**
   * Sets the value of the wkstn_id field.
   * @param argWorkStationId The new value for the field.
   */
  public void setWorkStationId(long argWorkStationId) {
    if (setWorkStationId_noev(argWorkStationId)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty.SET_WORKSTATIONID, Long.valueOf(argWorkStationId));
        }
      }
    }
  }

  public boolean setWorkStationId_noev(long argWorkStationId) {
    boolean ev_postable = false;

    if ((getDAO_().getWorkStationId() == null && Long.valueOf(argWorkStationId) != null) ||
        (getDAO_().getWorkStationId() != null && !getDAO_().getWorkStationId().equals(Long.valueOf(argWorkStationId)))) {
      getDAO_().setWorkStationId(Long.valueOf(argWorkStationId));
      ev_postable = true;
      if (_myExtension != null) {
        // Propagate changes to customer extension object.
        dtv.util.ObjectUtils.invokeMethod("setWorkStationId_noev", _myExtension, new Class<?>[] {long.class}, new Object[] {argWorkStationId}, null);
      }
    }

    return ev_postable;
  }

  /**
   * Gets the value of the ICV field.
   * @return The value of the field.
   */
  public long getIcv() {
    if (getDAO_().getIcv() != null) {
      return getDAO_().getIcv().longValue();
    }
    else {
      return 0; // no default specified in the dtx; we default to 0
    }
  }

  /**
   * Sets the value of the ICV field.
   * @param argIcv The new value for the field.
   */
  public void setIcv(long argIcv) {
    if (setIcv_noev(argIcv)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty.SET_ICV, Long.valueOf(argIcv));
        }
      }
    }
  }

  public boolean setIcv_noev(long argIcv) {
    boolean ev_postable = false;

    if ((getDAO_().getIcv() == null && Long.valueOf(argIcv) != null) ||
        (getDAO_().getIcv() != null && !getDAO_().getIcv().equals(Long.valueOf(argIcv)))) {
      getDAO_().setIcv(Long.valueOf(argIcv));
      ev_postable = true;
      if (_myExtension != null) {
        // Propagate changes to customer extension object.
        dtv.util.ObjectUtils.invokeMethod("setIcv_noev", _myExtension, new Class<?>[] {long.class}, new Object[] {argIcv}, null);
      }
    }

    return ev_postable;
  }

  /**
   * Gets the value of the property_code field.
   * @return The value of the field.
   */
  public String getPropertyCode() {
    return getDAO_().getPropertyCode();
  }

  /**
   * Sets the value of the property_code field.
   * @param argPropertyCode The new value for the field.
   */
  public void setPropertyCode(String argPropertyCode) {
    if (setPropertyCode_noev(argPropertyCode)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty.SET_PROPERTYCODE, argPropertyCode);
        }
      }
    }
  }

  public boolean setPropertyCode_noev(String argPropertyCode) {
    boolean ev_postable = false;

    if ((getDAO_().getPropertyCode() == null && argPropertyCode != null) ||
        (getDAO_().getPropertyCode() != null && !getDAO_().getPropertyCode().equals(argPropertyCode))) {
      getDAO_().setPropertyCode(argPropertyCode);
      ev_postable = true;
      if (_myExtension != null) {
        // Propagate changes to customer extension object.
        dtv.util.ObjectUtils.invokeMethod("setPropertyCode_noev", _myExtension, new Class<?>[] {String.class}, new Object[] {argPropertyCode}, null);
      }
    }

    return ev_postable;
  }

  /**
   * Gets the value of the type field.
   * @return The value of the field.
   */
  public String getType() {
    return getDAO_().getType();
  }

  /**
   * Sets the value of the type field.
   * @param argType The new value for the field.
   */
  public void setType(String argType) {
    if (setType_noev(argType)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty.SET_TYPE, argType);
        }
      }
    }
  }

  public boolean setType_noev(String argType) {
    boolean ev_postable = false;

    if ((getDAO_().getType() == null && argType != null) ||
        (getDAO_().getType() != null && !getDAO_().getType().equals(argType))) {
      getDAO_().setType(argType);
      ev_postable = true;
    }

    return ev_postable;
  }

  /**
   * Gets the value of the string_value field.
   * @return The value of the field.
   */
  public String getStringValue() {
    return getDAO_().getStringValue();
  }

  /**
   * Sets the value of the string_value field.
   * @param argStringValue The new value for the field.
   */
  public void setStringValue(String argStringValue) {
    if (setStringValue_noev(argStringValue)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty.SET_STRINGVALUE, argStringValue);
        }
      }
    }
  }

  public boolean setStringValue_noev(String argStringValue) {
    boolean ev_postable = false;

    if ((getDAO_().getStringValue() == null && argStringValue != null) ||
        (getDAO_().getStringValue() != null && !getDAO_().getStringValue().equals(argStringValue))) {
      getDAO_().setStringValue(argStringValue);
      ev_postable = true;
    }

    return ev_postable;
  }

  /**
   * Gets the value of the date_value field.
   * @return The value of the field.
   */
  public Date getDateValue() {
    return getDAO_().getDateValue();
  }

  /**
   * Sets the value of the date_value field.
   * @param argDateValue The new value for the field.
   */
  public void setDateValue(Date argDateValue) {
    if (setDateValue_noev(argDateValue)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty.SET_DATEVALUE, argDateValue);
        }
      }
    }
  }

  public boolean setDateValue_noev(Date argDateValue) {
    boolean ev_postable = false;

    if ((getDAO_().getDateValue() == null && argDateValue != null) ||
        (getDAO_().getDateValue() != null && !getDAO_().getDateValue().equals(argDateValue))) {
      getDAO_().setDateValue(argDateValue);
      ev_postable = true;
    }

    return ev_postable;
  }

  /**
   * Gets the value of the decimal_value field.
   * @return The value of the field.
   */
  public java.math.BigDecimal getDecimalValue() {
    return getDAO_().getDecimalValue();
  }

  /**
   * Sets the value of the decimal_value field.
   * @param argDecimalValue The new value for the field.
   */
  public void setDecimalValue(java.math.BigDecimal argDecimalValue) {
    if (setDecimalValue_noev(argDecimalValue)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty.SET_DECIMALVALUE, argDecimalValue);
        }
      }
    }
  }

  public boolean setDecimalValue_noev(java.math.BigDecimal argDecimalValue) {
    boolean ev_postable = false;

    if ((getDAO_().getDecimalValue() == null && argDecimalValue != null) ||
        (getDAO_().getDecimalValue() != null && !getDAO_().getDecimalValue().equals(argDecimalValue))) {
      getDAO_().setDecimalValue(argDecimalValue);
      ev_postable = true;
    }

    return ev_postable;
  }

  /**
   * Gets the value of the create_user_id field.
   * @return The value of the field.
   */
  public String getCreateUserId() {
    return getDAO_().getCreateUserId();
  }

  /**
   * Sets the value of the create_user_id field.
   * @param argCreateUserId The new value for the field.
   */
  public void setCreateUserId(String argCreateUserId) {
    if (setCreateUserId_noev(argCreateUserId)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty.SET_CREATEUSERID, argCreateUserId);
        }
      }
    }
  }

  public boolean setCreateUserId_noev(String argCreateUserId) {
    boolean ev_postable = false;

    if ((getDAO_().getCreateUserId() == null && argCreateUserId != null) ||
        (getDAO_().getCreateUserId() != null && !getDAO_().getCreateUserId().equals(argCreateUserId))) {
      getDAO_().setCreateUserId(argCreateUserId);
      ev_postable = true;
    }

    return ev_postable;
  }

  /**
   * Gets the value of the create_date field.
   * @return The value of the field.
   */
  public Date getCreateDate() {
    return getDAO_().getCreateDate();
  }

  /**
   * Sets the value of the create_date field.
   * @param argCreateDate The new value for the field.
   */
  public void setCreateDate(Date argCreateDate) {
    if (setCreateDate_noev(argCreateDate)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty.SET_CREATEDATE, argCreateDate);
        }
      }
    }
  }

  public boolean setCreateDate_noev(Date argCreateDate) {
    boolean ev_postable = false;

    if ((getDAO_().getCreateDate() == null && argCreateDate != null) ||
        (getDAO_().getCreateDate() != null && !getDAO_().getCreateDate().equals(argCreateDate))) {
      getDAO_().setCreateDate(argCreateDate);
      ev_postable = true;
    }

    return ev_postable;
  }

  /**
   * Gets the value of the update_user_id field.
   * @return The value of the field.
   */
  public String getUpdateUserId() {
    return getDAO_().getUpdateUserId();
  }

  /**
   * Sets the value of the update_user_id field.
   * @param argUpdateUserId The new value for the field.
   */
  public void setUpdateUserId(String argUpdateUserId) {
    if (setUpdateUserId_noev(argUpdateUserId)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty.SET_UPDATEUSERID, argUpdateUserId);
        }
      }
    }
  }

  public boolean setUpdateUserId_noev(String argUpdateUserId) {
    boolean ev_postable = false;

    if ((getDAO_().getUpdateUserId() == null && argUpdateUserId != null) ||
        (getDAO_().getUpdateUserId() != null && !getDAO_().getUpdateUserId().equals(argUpdateUserId))) {
      getDAO_().setUpdateUserId(argUpdateUserId);
      ev_postable = true;
    }

    return ev_postable;
  }

  /**
   * Gets the value of the update_date field.
   * @return The value of the field.
   */
  public Date getUpdateDate() {
    return getDAO_().getUpdateDate();
  }

  /**
   * Sets the value of the update_date field.
   * @param argUpdateDate The new value for the field.
   */
  public void setUpdateDate(Date argUpdateDate) {
    if (setUpdateDate_noev(argUpdateDate)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty.SET_UPDATEDATE, argUpdateDate);
        }
      }
    }
  }

  public boolean setUpdateDate_noev(Date argUpdateDate) {
    boolean ev_postable = false;

    if ((getDAO_().getUpdateDate() == null && argUpdateDate != null) ||
        (getDAO_().getUpdateDate() != null && !getDAO_().getUpdateDate().equals(argUpdateDate))) {
      getDAO_().setUpdateDate(argUpdateDate);
      ev_postable = true;
    }

    return ev_postable;
  }


  @Override
  public Object getValue(String argFieldId) {
    switch(argFieldId) {
      case "AsqZatcaInvoiceStagingPropertyExtension": return _myExtension;
      default: return super.getValue(argFieldId);
    }
  }

  @Override
  public void setValue(String argFieldId, Object argValue) {
    switch (argFieldId) {
      case "AsqZatcaInvoiceStagingPropertyExtension":
        _myExtension = (IDataModel)argValue;
        break;
      default:
        super.setValue(argFieldId, argValue);
        break;
    }
  }

  @Override
  public void setDependencies(dtv.data2.IPersistenceDefaults argPD, dtv.event.EventManager argEM) {
    _persistenceDefaults = argPD;
    _daoImpl.setPersistenceDefaults(argPD);
    _eventManager = argEM;
    _events = new ModelEventor(this, argEM);
    _eventCascade = new dtv.event.handler.CascadingHandler(this);
    if (_myExtension != null) {
      ((dtv.data2.access.impl.IDataModelImpl)_myExtension).setDependencies(argPD, argEM);
    }
  }

  @Override
  public void startTransaction() {
    if (_alreadyInStart) {
      return;
    }
    else {
      _alreadyInStart = true;
    }

    super.startTransaction();

    
    _alreadyInStart = false;
  }

  @Override
  public void rollbackChanges() {
    if (isAlreadyRolledBack())
      return;

    super.rollbackChanges();

  }

  @Override
  public void commitTransaction() {
    if (_alreadyInCommit) {
      return;
    } else {
      _alreadyInCommit = true;
    }

    super.commitTransaction();

    
    _alreadyInCommit = false;
  }

  private void readObject(java.io.ObjectInputStream argStream)
                         throws java.io.IOException, ClassNotFoundException {
    argStream.defaultReadObject();
  }

}
