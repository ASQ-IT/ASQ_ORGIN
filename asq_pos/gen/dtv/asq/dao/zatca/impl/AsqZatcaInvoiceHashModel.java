// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.DefaultModelGenerator 2024-08-22T18:06:17
// CHECKSTYLE:OFF
package dtv.asq.dao.zatca.impl;

import java.math.BigDecimal;
import java.util.*;
import dtv.data2.access.impl.*;
import dtv.data2.access.IDataModel;
import dtv.data2.access.ModelEventor;
import dtv.util.HistoricalList;
import dtv.data2.access.*;
import dtv.asq.dao.zatca.AsqZatcaInvoiceHashId;

/**
 * Auto Generated Model.
 *
 * @author DAOGen
 */
@SuppressWarnings("all")
public class AsqZatcaInvoiceHashModel 
  extends dtv.data2.access.impl.AbstractDataModelWithPropertyImpl<dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty>
  implements dtv.asq.dao.zatca.IAsqZatcaInvoiceHash {

  // Fix serialization compatibility based on the name of the DAO
  private static final long serialVersionUID = 699331087L;


  private transient boolean _alreadyInStart = false;
  private transient boolean _alreadyInCommit = false;

  private IDataModel _myExtension;

  @Override
  public String toString() {
    return super.toString() + " Id: " + this.getObjectId();
  }

  @Override
  public void initDAO() {
    setDAO(new AsqZatcaInvoiceHashDAO());
  }

  /**
   * Return the data access object associated with this data model
   * @return our DAO, properly casted
   */
  private AsqZatcaInvoiceHashDAO getDAO_() {
     return (AsqZatcaInvoiceHashDAO) _daoImpl;
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
          _events.post(dtv.asq.dao.zatca.IAsqZatcaInvoiceHash.SET_ORGANIZATIONID, Long.valueOf(argOrganizationId));
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
          ((dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashPropertyModel) it.next()).setOrganizationId_noev(argOrganizationId);
        }
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
          _events.post(dtv.asq.dao.zatca.IAsqZatcaInvoiceHash.SET_ICV, Long.valueOf(argIcv));
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
      if (_properties != null) {
        // Propagate changes to related objects in relation Properties.
        java.util.Iterator it = _properties.iterator();
        while(it.hasNext()) {
          // Use the non-eventing setter directly.
          ((dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashPropertyModel) it.next()).setIcv_noev(argIcv);
        }
      }
    }

    return ev_postable;
  }

  /**
   * Gets the value of the INVOICE_HASH field.
   * @return The value of the field.
   */
  public String getInvoiceHash() {
    return getDAO_().getInvoiceHash();
  }

  /**
   * Sets the value of the INVOICE_HASH field.
   * @param argInvoiceHash The new value for the field.
   */
  public void setInvoiceHash(String argInvoiceHash) {
    if (setInvoiceHash_noev(argInvoiceHash)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.IAsqZatcaInvoiceHash.SET_INVOICEHASH, argInvoiceHash);
        }
      }
    }
  }

  public boolean setInvoiceHash_noev(String argInvoiceHash) {
    boolean ev_postable = false;

    if ((getDAO_().getInvoiceHash() == null && argInvoiceHash != null) ||
        (getDAO_().getInvoiceHash() != null && !getDAO_().getInvoiceHash().equals(argInvoiceHash))) {
      getDAO_().setInvoiceHash(argInvoiceHash);
      ev_postable = true;
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
          _events.post(dtv.asq.dao.zatca.IAsqZatcaInvoiceHash.SET_INVOICEID, argInvoiceId);
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
    }

    return ev_postable;
  }

  /**
   * Gets the value of the INVOICE_DATE field.
   * @return The value of the field.
   */
  public Date getInvoiceDate() {
    return getDAO_().getInvoiceDate();
  }

  /**
   * Sets the value of the INVOICE_DATE field.
   * @param argInvoiceDate The new value for the field.
   */
  public void setInvoiceDate(Date argInvoiceDate) {
    if (setInvoiceDate_noev(argInvoiceDate)) {
      if (_events != null) {
        if (postEventsForChanges()) {
          _events.post(dtv.asq.dao.zatca.IAsqZatcaInvoiceHash.SET_INVOICEDATE, argInvoiceDate);
        }
      }
    }
  }

  public boolean setInvoiceDate_noev(Date argInvoiceDate) {
    boolean ev_postable = false;

    if ((getDAO_().getInvoiceDate() == null && argInvoiceDate != null) ||
        (getDAO_().getInvoiceDate() != null && !getDAO_().getInvoiceDate().equals(argInvoiceDate))) {
      getDAO_().setInvoiceDate(argInvoiceDate);
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
          _events.post(dtv.asq.dao.zatca.IAsqZatcaInvoiceHash.SET_CREATEUSERID, argCreateUserId);
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
          _events.post(dtv.asq.dao.zatca.IAsqZatcaInvoiceHash.SET_CREATEDATE, argCreateDate);
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
          _events.post(dtv.asq.dao.zatca.IAsqZatcaInvoiceHash.SET_UPDATEUSERID, argUpdateUserId);
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
          _events.post(dtv.asq.dao.zatca.IAsqZatcaInvoiceHash.SET_UPDATEDATE, argUpdateDate);
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

  protected dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty newProperty(String argPropertyName)  {
    dtv.asq.dao.zatca.AsqZatcaInvoiceHashPropertyId id = new dtv.asq.dao.zatca.AsqZatcaInvoiceHashPropertyId();

    id.setOrganizationId(Long.valueOf(getOrganizationId()));
    id.setIcv(Long.valueOf(getIcv()));
    id.setPropertyCode(argPropertyName);

    dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty prop = dtv.data2.access.DataFactory.createObject(id, dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty.class);
    return prop;
  }

  private HistoricalList<dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty> _properties;
  // So that rollback() reverts to proper value
  private transient HistoricalList<dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty> _propertiesSavepoint;

  @Relationship(name="Properties")
  public java.util.List<dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty> getProperties() {
    if (_properties == null) {
      _properties = new HistoricalList<dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty>(null);
    }
    return _properties;
  }

  public void setProperties( java.util.List<dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty> argProperties) {
    if (_properties == null) {
      _properties = new HistoricalList<dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty>(argProperties);
    } else { 
      _properties.setCurrentList(argProperties);
    }
    // Propagate identification information through the graph.
    for( dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty child : _properties) {
      dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashPropertyModel model = (dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashPropertyModel) child;
      model.setOrganizationId_noev(this.getOrganizationId());
      model.setIcv_noev(this.getIcv());
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

  public void addAsqZatcaInvoiceHashProperty(dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty argAsqZatcaInvoiceHashProperty) {
    if (_properties == null) {
      _properties = new HistoricalList<dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty>(null);
    }
    argAsqZatcaInvoiceHashProperty.setOrganizationId(this.getOrganizationId());
    argAsqZatcaInvoiceHashProperty.setIcv(this.getIcv());
    if (argAsqZatcaInvoiceHashProperty instanceof IDataModelImpl) {
      IDataAccessObject childDao = ((IDataModelImpl) argAsqZatcaInvoiceHashProperty).getDAO();
      if (dtv.util.StringUtils.isEmpty(childDao.getOriginDataSource()) && 
          !dtv.util.StringUtils.isEmpty(this.getDAO().getOriginDataSource())) {
        childDao.setOriginDataSource(this.getDAO().getOriginDataSource());
      }
    }

    // Register the _handleChildEvent method to receive events from the new child.
    if (postEventsForChanges()) {
      _eventManager.registerEventHandler(_eventCascade, new dtv.event.EventDescriptor(argAsqZatcaInvoiceHashProperty));
    }

    _properties.add(argAsqZatcaInvoiceHashProperty);
    if (postEventsForChanges()) {
      _events.post(dtv.asq.dao.zatca.IAsqZatcaInvoiceHash.ADD_PROPERTIES, argAsqZatcaInvoiceHashProperty);
    }
  }

  public void removeAsqZatcaInvoiceHashProperty(dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty argAsqZatcaInvoiceHashProperty) {
    if (_properties != null) {
    // De-Register the _handleChildEvent method from receiving the child events.
    if (postEventsForChanges()) {
      _eventManager.deregisterEventHandler(_eventCascade, new dtv.event.EventDescriptor(argAsqZatcaInvoiceHashProperty));
    }
      _properties.remove(argAsqZatcaInvoiceHashProperty);
      if (postEventsForChanges()) {
        _events.post(dtv.asq.dao.zatca.IAsqZatcaInvoiceHash.REMOVE_PROPERTIES, argAsqZatcaInvoiceHashProperty);
      }
    }
  }

  @Override
  public Object getValue(String argFieldId) {
    switch(argFieldId) {
      case "Properties": return getProperties();
      case "AsqZatcaInvoiceHashExtension": return _myExtension;
      default: return super.getValue(argFieldId);
    }
  }

  @Override
  public void setValue(String argFieldId, Object argValue) {
    switch (argFieldId) {
      case "Properties":
        setProperties(changeToList(argValue, dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty.class));
        break;
      case "AsqZatcaInvoiceHashExtension":
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
      for (dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty relationship : _properties) {
        ((dtv.data2.access.impl.IDataModelImpl)relationship).setDependencies(argPD, argEM);
      }
    }
  }

  public IDataModel getAsqZatcaInvoiceHashExt() {
    return _myExtension;
  }

  public void setAsqZatcaInvoiceHashExt(IDataModel argExt) {
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
      _propertiesSavepoint = new HistoricalList<dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty>(_properties);
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
      _properties = new HistoricalList<dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty>(_properties);
    }

    
    _alreadyInCommit = false;
  }

  private void readObject(java.io.ObjectInputStream argStream)
                         throws java.io.IOException, ClassNotFoundException {
    argStream.defaultReadObject();
  }

}
