// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.DefaultModelGenerator 2024-08-11T15:27:53
// CHECKSTYLE:OFF
package dtv.asq.dao.zatca.invoice.staging.impl;

import java.math.BigDecimal;
import java.util.*;
import dtv.data2.access.impl.*;
import dtv.data2.access.IDataModel;
import dtv.data2.access.ModelEventor;
import dtv.util.HistoricalList;
import dtv.data2.access.*;
import dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingId;

/**
 * Auto Generated Model.
 *
 * @author DAOGen
 */
@SuppressWarnings("all")
public class AsqZatcaInvoiceStagingModel 
  extends dtv.data2.access.impl.AbstractDataModelWithPropertyImpl<dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty>
  implements dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging {

  // Fix serialization compatibility based on the name of the DAO
  private static final long serialVersionUID = 586070938L;


  private transient boolean _alreadyInStart = false;
  private transient boolean _alreadyInCommit = false;

  private IDataModel _myExtension;

  @Override
  public String toString() {
    return super.toString() + " Id: " + this.getObjectId();
  }

  @Override
  public void initDAO() {
    setDAO(new AsqZatcaInvoiceStagingDAO());
  }

  /**
   * Return the data access object associated with this data model
   * @return our DAO, properly casted
   */
  private AsqZatcaInvoiceStagingDAO getDAO_() {
     return (AsqZatcaInvoiceStagingDAO) _daoImpl;
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
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging.SET_ORGANIZATIONID, Long.valueOf(argOrganizationId));
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
      if (_properties != null) {
        // Propagate changes to related objects in relation Properties.
        java.util.Iterator it = _properties.iterator();
        while(it.hasNext()) {
          // Use the non-eventing setter directly.
          ((dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingPropertyModel) it.next()).setOrganizationId_noev(argOrganizationId);
        }
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
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging.SET_INVOICEID, argInvoiceId);
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
      if (_properties != null) {
        // Propagate changes to related objects in relation Properties.
        java.util.Iterator it = _properties.iterator();
        while(it.hasNext()) {
          // Use the non-eventing setter directly.
          ((dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingPropertyModel) it.next()).setInvoiceId_noev(argInvoiceId);
        }
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
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging.SET_BUSINESSDATE, argBusinessDate);
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
      if (_properties != null) {
        // Propagate changes to related objects in relation Properties.
        java.util.Iterator it = _properties.iterator();
        while(it.hasNext()) {
          // Use the non-eventing setter directly.
          ((dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingPropertyModel) it.next()).setBusinessDate_noev(argBusinessDate);
        }
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
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging.SET_TRANSACTIONSEQ, Long.valueOf(argTransactionSeq));
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
      if (_properties != null) {
        // Propagate changes to related objects in relation Properties.
        java.util.Iterator it = _properties.iterator();
        while(it.hasNext()) {
          // Use the non-eventing setter directly.
          ((dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingPropertyModel) it.next()).setTransactionSeq_noev(argTransactionSeq);
        }
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
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging.SET_WORKSTATIONID, Long.valueOf(argWorkStationId));
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
      if (_properties != null) {
        // Propagate changes to related objects in relation Properties.
        java.util.Iterator it = _properties.iterator();
        while(it.hasNext()) {
          // Use the non-eventing setter directly.
          ((dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingPropertyModel) it.next()).setWorkStationId_noev(argWorkStationId);
        }
      }
    }

    return ev_postable;
  }

  /**
   * Gets the value of the Status field.
   * @return The value of the field.
   */
  public String getStatus() {
    return getDAO_().getStatus();
  }

  /**
   * Sets the value of the Status field.
   * @param argStatus The new value for the field.
   */
  public void setStatus(String argStatus) {
    if (setStatus_noev(argStatus)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging.SET_STATUS, argStatus);
        }
      }
    }
  }

  public boolean setStatus_noev(String argStatus) {
    boolean ev_postable = false;

    if ((getDAO_().getStatus() == null && argStatus != null) ||
        (getDAO_().getStatus() != null && !getDAO_().getStatus().equals(argStatus))) {
      getDAO_().setStatus(argStatus);
      ev_postable = true;
    }

    return ev_postable;
  }

  /**
   * Gets the value of the JSON_INVOICE field.
   * @return The value of the field.
   */
  public byte[] getSubmittedJSON() {
    return getDAO_().getSubmittedJSON();
  }

  /**
   * Sets the value of the JSON_INVOICE field.
   * @param argSubmittedJSON The new value for the field.
   */
  public void setSubmittedJSON(byte[] argSubmittedJSON) {
    if (setSubmittedJSON_noev(argSubmittedJSON)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging.SET_SUBMITTEDJSON, argSubmittedJSON);
        }
      }
    }
  }

  public boolean setSubmittedJSON_noev(byte[] argSubmittedJSON) {
    boolean ev_postable = false;

    if ((getDAO_().getSubmittedJSON() == null && argSubmittedJSON != null) ||
        (getDAO_().getSubmittedJSON() != null && !getDAO_().getSubmittedJSON().equals(argSubmittedJSON))) {
      getDAO_().setSubmittedJSON(argSubmittedJSON);
      ev_postable = true;
    }

    return ev_postable;
  }

  /**
   * Gets the value of the INVOICE_QRCODE field.
   * @return The value of the field.
   */
  public byte[] getInvoiceQrCode() {
    return getDAO_().getInvoiceQrCode();
  }

  /**
   * Sets the value of the INVOICE_QRCODE field.
   * @param argInvoiceQrCode The new value for the field.
   */
  public void setInvoiceQrCode(byte[] argInvoiceQrCode) {
    if (setInvoiceQrCode_noev(argInvoiceQrCode)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging.SET_INVOICEQRCODE, argInvoiceQrCode);
        }
      }
    }
  }

  public boolean setInvoiceQrCode_noev(byte[] argInvoiceQrCode) {
    boolean ev_postable = false;

    if ((getDAO_().getInvoiceQrCode() == null && argInvoiceQrCode != null) ||
        (getDAO_().getInvoiceQrCode() != null && !getDAO_().getInvoiceQrCode().equals(argInvoiceQrCode))) {
      getDAO_().setInvoiceQrCode(argInvoiceQrCode);
      ev_postable = true;
    }

    return ev_postable;
  }

  /**
   * Gets the value of the INVOICE_UIID field.
   * @return The value of the field.
   */
  public String getInvoiceUUID() {
    return getDAO_().getInvoiceUUID();
  }

  /**
   * Sets the value of the INVOICE_UIID field.
   * @param argInvoiceUUID The new value for the field.
   */
  public void setInvoiceUUID(String argInvoiceUUID) {
    if (setInvoiceUUID_noev(argInvoiceUUID)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging.SET_INVOICEUUID, argInvoiceUUID);
        }
      }
    }
  }

  public boolean setInvoiceUUID_noev(String argInvoiceUUID) {
    boolean ev_postable = false;

    if ((getDAO_().getInvoiceUUID() == null && argInvoiceUUID != null) ||
        (getDAO_().getInvoiceUUID() != null && !getDAO_().getInvoiceUUID().equals(argInvoiceUUID))) {
      getDAO_().setInvoiceUUID(argInvoiceUUID);
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
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging.SET_CREATEUSERID, argCreateUserId);
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
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging.SET_CREATEDATE, argCreateDate);
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
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging.SET_UPDATEUSERID, argUpdateUserId);
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
          _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging.SET_UPDATEDATE, argUpdateDate);
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

  protected dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty newProperty(String argPropertyName)  {
    dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingPropertyId id = new dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingPropertyId();

    id.setOrganizationId(Long.valueOf(getOrganizationId()));
    id.setInvoiceId(getInvoiceId());
    id.setBusinessDate(getBusinessDate());
    id.setTransactionSeq(Long.valueOf(getTransactionSeq()));
    id.setWorkStationId(Long.valueOf(getWorkStationId()));
    id.setPropertyCode(argPropertyName);

    dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty prop = dtv.data2.access.DataFactory.createObject(id, dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty.class);
    return prop;
  }

  private HistoricalList<dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty> _properties;
  // So that rollback() reverts to proper value
  private transient HistoricalList<dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty> _propertiesSavepoint;

  @Relationship(name="Properties")
  public java.util.List<dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty> getProperties() {
    if (_properties == null) {
      _properties = new HistoricalList<dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty>(null);
    }
    return _properties;
  }

  public void setProperties( java.util.List<dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty> argProperties) {
    if (_properties == null) {
      _properties = new HistoricalList<dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty>(argProperties);
    } else { 
      _properties.setCurrentList(argProperties);
    }
    // Propagate identification information through the graph.
    for( dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty child : _properties) {
      dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingPropertyModel model = (dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingPropertyModel) child;
      model.setOrganizationId_noev(this.getOrganizationId());
      model.setInvoiceId_noev(this.getInvoiceId());
      model.setBusinessDate_noev(this.getBusinessDate());
      model.setTransactionSeq_noev(this.getTransactionSeq());
      model.setWorkStationId_noev(this.getWorkStationId());
      if (child instanceof IDataModelImpl) {
        IDataAccessObject childDao = ((IDataModelImpl) child).getDAO();
        if (dtv.util.StringUtils.isEmpty(childDao.getOriginDataSource()) && 
            !dtv.util.StringUtils.isEmpty(this.getDAO().getOriginDataSource())) {
          childDao.setOriginDataSource(this.getDAO().getOriginDataSource());
        }
      }
      if (postEventsForChanges()) {
        _eventManager.registerEventHandler(_eventCascade, child);
      }
    }
  }

  public void addAsqZatcaInvoiceStagingProperty(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty argAsqZatcaInvoiceStagingProperty) {
    if (_properties == null) {
      _properties = new HistoricalList<dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty>(null);
    }
    argAsqZatcaInvoiceStagingProperty.setOrganizationId(this.getOrganizationId());
    argAsqZatcaInvoiceStagingProperty.setInvoiceId(this.getInvoiceId());
    argAsqZatcaInvoiceStagingProperty.setBusinessDate(this.getBusinessDate());
    argAsqZatcaInvoiceStagingProperty.setTransactionSeq(this.getTransactionSeq());
    argAsqZatcaInvoiceStagingProperty.setWorkStationId(this.getWorkStationId());
    if (argAsqZatcaInvoiceStagingProperty instanceof IDataModelImpl) {
      IDataAccessObject childDao = ((IDataModelImpl) argAsqZatcaInvoiceStagingProperty).getDAO();
      if (dtv.util.StringUtils.isEmpty(childDao.getOriginDataSource()) && 
          !dtv.util.StringUtils.isEmpty(this.getDAO().getOriginDataSource())) {
        childDao.setOriginDataSource(this.getDAO().getOriginDataSource());
      }
    }

    // Register the _handleChildEvent method to receive events from the new child.
    if (postEventsForChanges()) {
      _eventManager.registerEventHandler(_eventCascade, new dtv.event.EventDescriptor(argAsqZatcaInvoiceStagingProperty));
    }

    _properties.add(argAsqZatcaInvoiceStagingProperty);
    if (postEventsForChanges()) {
      _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging.ADD_PROPERTIES, argAsqZatcaInvoiceStagingProperty);
    }
  }

  public void removeAsqZatcaInvoiceStagingProperty(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty argAsqZatcaInvoiceStagingProperty) {
    if (_properties != null) {
    // De-Register the _handleChildEvent method from receiving the child events.
    if (postEventsForChanges()) {
      _eventManager.deregisterEventHandler(_eventCascade, new dtv.event.EventDescriptor(argAsqZatcaInvoiceStagingProperty));
    }
      _properties.remove(argAsqZatcaInvoiceStagingProperty);
      if (postEventsForChanges()) {
        _events.post(dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging.REMOVE_PROPERTIES, argAsqZatcaInvoiceStagingProperty);
      }
    }
  }

  @Override
  public Object getValue(String argFieldId) {
    switch(argFieldId) {
      case "Properties": return getProperties();
      case "AsqZatcaInvoiceStagingExtension": return _myExtension;
      default: return super.getValue(argFieldId);
    }
  }

  @Override
  public void setValue(String argFieldId, Object argValue) {
    switch (argFieldId) {
      case "Properties":
        setProperties(changeToList(argValue, dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty.class));
        break;
      case "AsqZatcaInvoiceStagingExtension":
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
    if (_properties != null) {
      for (dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty relationship : _properties) {
        ((dtv.data2.access.impl.IDataModelImpl)relationship).setDependencies(argPD, argEM);
      }
    }
  }

  public IDataModel getAsqZatcaInvoiceStagingExt() {
    return _myExtension;
  }

  public void setAsqZatcaInvoiceStagingExt(IDataModel argExt) {
    _myExtension = argExt;
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

    _propertiesSavepoint = _properties;
    if (_properties != null) {
      _propertiesSavepoint = new HistoricalList<dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty>(_properties);
      java.util.Iterator it = _properties.iterator();
      while (it.hasNext()) {
        ((dtv.data2.access.IDataModel)it.next()).startTransaction();
      }
    }

    
    _alreadyInStart = false;
  }

  @Override
  public void rollbackChanges() {
    if (isAlreadyRolledBack())
      return;

    super.rollbackChanges();

    _properties = _propertiesSavepoint;
    _propertiesSavepoint = null;
    if (_properties != null) {
      java.util.Iterator it = _properties.iterator();
      while (it.hasNext()) {
        ((dtv.data2.access.IDataModel)it.next()).rollbackChanges();
      }
    }

  }

  @Override
  public void commitTransaction() {
    if (_alreadyInCommit) {
      return;
    } else {
      _alreadyInCommit = true;
    }

    super.commitTransaction();

    _propertiesSavepoint = _properties;
    if (_properties != null) {
      java.util.Iterator it = _properties.iterator();
      while (it.hasNext()) {
        ((dtv.data2.access.IDataModel)it.next()).commitTransaction();
      }
      _properties = new HistoricalList<dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty>(_properties);
    }

    
    _alreadyInCommit = false;
  }

  private void readObject(java.io.ObjectInputStream argStream)
                         throws java.io.IOException, ClassNotFoundException {
    argStream.defaultReadObject();
  }

}
