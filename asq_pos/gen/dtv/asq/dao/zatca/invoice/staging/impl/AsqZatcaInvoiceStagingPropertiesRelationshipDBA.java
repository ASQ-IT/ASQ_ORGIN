// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.GenerateRelationships 2024-08-22T18:06:17
// CHECKSTYLE:OFF
package dtv.asq.dao.zatca.invoice.staging.impl;

import java.util.ArrayList;
import java.util.List;

import dtv.data2.access.IDataAccessObject;
import dtv.data2.access.impl.jdbc.IJDBCRelationshipAdapter;

/**
 * Auto-generated DBA.
 * DO NOT MANUALLY MODIFY THIS FILE.
 * ANY CHANGES WILL BE OVERWRITTEN BY REGENERATION!!
 *
 * @author DAOGen
 */
@SuppressWarnings("all")
public class AsqZatcaInvoiceStagingPropertiesRelationshipDBA implements IJDBCRelationshipAdapter {

 private List<Object> _parameterList = new ArrayList<Object>(6);

  public String getSelect() {
    return "SELECT organization_id, INVOICE_ID, BUSINESS_DATE, TRANS_SEQ, wkstn_id, ICV, property_code, type, string_value, date_value, decimal_value, create_user_id, create_date, update_user_id, update_date FROM ASQ_ZATCA_INVOICE_STAGING_p WHERE organization_id = ? AND INVOICE_ID = ? AND BUSINESS_DATE = ? AND TRANS_SEQ = ? AND wkstn_id = ? AND ICV = ?";

  }

  public void setParent(IDataAccessObject argDAO) {
    AsqZatcaInvoiceStagingDAO dao = (AsqZatcaInvoiceStagingDAO)argDAO;
    _parameterList.add(dao.getOrganizationId());
    _parameterList.add(dao.getInvoiceId());
    _parameterList.add(dao.getBusinessDate());
    _parameterList.add(dao.getTransactionSeq());
    _parameterList.add(dao.getWorkStationId());
    _parameterList.add(dao.getIcv());
  }

  @Override
  public boolean isOrgHierarchyJoinRequired() {
    return false;
  }

  @Override
  public boolean isLevelHierarchyJoinRequired() {
    return false;
  }

  public List getParameterList() {
    return _parameterList;
  }

  public dtv.data2.access.IObjectId getChildObjectId() {
    throw new dtv.data2.access.exception.DtxException("getChildObjectId() can only be called on ONE-ONE relationships. This is an invalid call for relationship adapter: " + this.getClass().getName());
  }
}
