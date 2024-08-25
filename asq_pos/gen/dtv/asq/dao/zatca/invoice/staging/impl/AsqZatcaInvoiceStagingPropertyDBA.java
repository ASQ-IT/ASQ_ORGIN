// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.GenerateDaoAndDba 2024-08-22T18:06:17
// CHECKSTYLE:OFF
package dtv.asq.dao.zatca.invoice.staging.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import dtv.data2.access.IDataAccessObject;
import dtv.data2.access.IObjectId;
import dtv.data2.access.impl.jdbc.IFiller;
import dtv.data2.access.impl.jdbc.IJDBCTableAdapter;


/**
 * Auto-generated DBA.
 *
 * @author DAOGen
 */
@SuppressWarnings("all")
public class AsqZatcaInvoiceStagingPropertyDBA implements IJDBCTableAdapter {

  private Long _organizationId;
  private String _invoiceId;
  private Date _businessDate;
  private Long _transactionSeq;
  private Long _workStationId;
  private Long _icv;
  private String _propertyCode;
  private String _type;
  private String _stringValue;
  private Date _dateValue;
  private java.math.BigDecimal _decimalValue;
  private String _createUserId;
  private Date _createDate;
  private String _updateUserId;
  private Date _updateDate;

  private static final String SELECT_OBJECT = "SELECT t.organization_id, t.INVOICE_ID, t.BUSINESS_DATE, t.TRANS_SEQ, t.wkstn_id, t.ICV, t.property_code, t.type, t.string_value, t.date_value, t.decimal_value, t.create_user_id, t.create_date, t.update_user_id, t.update_date FROM ASQ_ZATCA_INVOICE_STAGING_p t";

  public String getSelect() {
    return getSelectImpl();
  }

  private String getSelectImpl() {
    return SELECT_OBJECT;
  }

  private static final String SELECT_WHERE_OBJECT = " WHERE organization_id = ?  AND INVOICE_ID = ?  AND BUSINESS_DATE = ?  AND TRANS_SEQ = ?  AND wkstn_id = ?  AND ICV = ?  AND property_code = ?  ";

  public String getSelectWhere() {
    return SELECT_WHERE_OBJECT;
  }

  private static final String[] INSERT_OBJECT = new String[] {"INSERT INTO ASQ_ZATCA_INVOICE_STAGING_p(organization_id, INVOICE_ID, BUSINESS_DATE, TRANS_SEQ, wkstn_id, ICV, property_code, type, string_value, date_value, decimal_value, create_user_id, create_date, update_user_id, update_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"};

  public String[] getInsert() {
    return INSERT_OBJECT;
  }

  public Object[][] getInsertParameters() {
    Object[][] insertParameterObject = new Object[][] {{_organizationId, _invoiceId, _businessDate, _transactionSeq, _workStationId, _icv, _propertyCode, _type, _stringValue, _dateValue, _decimalValue, _createUserId, _createDate, _updateUserId, _updateDate}};
    return insertParameterObject;
  }

  private static final int[][] INSERT_PARAMETER_TYPES_OBJECT = new int[][] {{-5, 12, 91, -5, -5, -5, 12, 12, 12, 91, 3, 12, 91, 12, 91}};

  public int[][] getInsertParameterTypes() {
    return INSERT_PARAMETER_TYPES_OBJECT;
  }

  private static final String[] UPDATE_OBJECT = new String[] {"UPDATE ASQ_ZATCA_INVOICE_STAGING_p SET type = ?, string_value = ?, date_value = ?, decimal_value = ?, update_user_id = ?, update_date = ?"};

  public String[] getUpdate() {
    return UPDATE_OBJECT;
  }

  public Object[][] getUpdateParameters() {
    Object[][] updateParameterObject = new Object[][] {{_type, _stringValue, _dateValue, _decimalValue, _updateUserId, _updateDate}};
    return updateParameterObject;
  }

  private static final int[][] UPDATE_PARAMETER_TYPE_OBJECT = new int[][] {{12, 12, 91, 3, 12, 91}};
  public int[][] getUpdateParameterTypes() {
    return UPDATE_PARAMETER_TYPE_OBJECT;
  }

  private static final String[] DELETE_OBJECT = new String[] {"DELETE FROM ASQ_ZATCA_INVOICE_STAGING_p"};

  public String[] getDelete() {
    return DELETE_OBJECT;
  }

  private static final String WHERE_OBJECT = " WHERE organization_id = ?  AND INVOICE_ID = ?  AND BUSINESS_DATE = ?  AND TRANS_SEQ = ?  AND wkstn_id = ?  AND ICV = ?  AND property_code = ?  ";

  public String getWhere() {
    return WHERE_OBJECT;
  }

  public Object[] getWhereParameters() {
    return new Object[] { _organizationId, _invoiceId, _businessDate, _transactionSeq, _workStationId, _icv, _propertyCode };
  }

  private static final int[] WHERE_PARAMETER_OBJECT = new int[] { -5, 12, 91, -5, -5, -5, 12 };

  public int[] getWhereParameterTypes() {
    return WHERE_PARAMETER_OBJECT;
  }

  private static final Map<String, String> TABLE_COLUMN_MAP;

  static {
    Map<String, String> tmpMap = new HashMap<>(15);
    tmpMap.put("organizationId", "organization_id");
    tmpMap.put("invoiceId", "INVOICE_ID");
    tmpMap.put("businessDate", "BUSINESS_DATE");
    tmpMap.put("transactionSeq", "TRANS_SEQ");
    tmpMap.put("workStationId", "wkstn_id");
    tmpMap.put("icv", "ICV");
    tmpMap.put("propertyCode", "property_code");
    tmpMap.put("type", "type");
    tmpMap.put("stringValue", "string_value");
    tmpMap.put("dateValue", "date_value");
    tmpMap.put("decimalValue", "decimal_value");
    tmpMap.put("createUserId", "create_user_id");
    tmpMap.put("createDate", "create_date");
    tmpMap.put("updateUserId", "update_user_id");
    tmpMap.put("updateDate", "update_date");
    TABLE_COLUMN_MAP = Collections.unmodifiableMap(tmpMap);
  }

  public String getTableName() {
    return "ASQ_ZATCA_INVOICE_STAGING_p";
  }

  public Map<String, String> getTableColumnMap() {
    return TABLE_COLUMN_MAP;
  }

  public IFiller getFiller() {
    return getFillerImpl();
  }

  private IFiller getFillerImpl() {
    return new AsqZatcaInvoiceStagingPropertyFiller(this);
  }

  private static class AsqZatcaInvoiceStagingPropertyFiller implements IFiller {

    private AsqZatcaInvoiceStagingPropertyDBA _parent;

    public AsqZatcaInvoiceStagingPropertyFiller(AsqZatcaInvoiceStagingPropertyDBA argParent) {
      _parent = argParent;
    }

    public dtv.data2.access.impl.jdbc.IJDBCTableAdapter fill(ResultSet argResultSet) throws java.sql.SQLException {
      dtv.data2.access.impl.jdbc.IJDBCTableAdapter filledObject = new AsqZatcaInvoiceStagingPropertyDBA();
      fill(argResultSet, filledObject);
      return filledObject;
    }

    public void fill(ResultSet argResultSet, dtv.data2.access.impl.jdbc.IJDBCTableAdapter argAdapter) throws java.sql.SQLException {
      AsqZatcaInvoiceStagingPropertyDBA adapter = (AsqZatcaInvoiceStagingPropertyDBA) argAdapter;


      { // load Long value for field: organizationId while preserving null values.
        long primitiveResult = argResultSet.getLong(1);
        if (primitiveResult != 0 || argResultSet.getObject(1) != null) {
              adapter._organizationId = Long.valueOf(primitiveResult);
        }
      }

      adapter._invoiceId = argResultSet.getString(2);

      java.sql.Timestamp t3 = argResultSet.getTimestamp(3);
      if (t3 != null) {
          adapter._businessDate =  new dtv.util.DtvDate(t3.getTime());
      }
      else {
              adapter._businessDate =  null;
      }


      { // load Long value for field: transactionSeq while preserving null values.
        long primitiveResult = argResultSet.getLong(4);
        if (primitiveResult != 0 || argResultSet.getObject(4) != null) {
              adapter._transactionSeq = Long.valueOf(primitiveResult);
        }
      }


      { // load Long value for field: workStationId while preserving null values.
        long primitiveResult = argResultSet.getLong(5);
        if (primitiveResult != 0 || argResultSet.getObject(5) != null) {
              adapter._workStationId = Long.valueOf(primitiveResult);
        }
      }


      { // load Long value for field: icv while preserving null values.
        long primitiveResult = argResultSet.getLong(6);
        if (primitiveResult != 0 || argResultSet.getObject(6) != null) {
              adapter._icv = Long.valueOf(primitiveResult);
        }
      }

      adapter._propertyCode = argResultSet.getString(7);
      adapter._type = argResultSet.getString(8);
      adapter._stringValue = argResultSet.getString(9);

      java.sql.Timestamp t10 = argResultSet.getTimestamp(10);
      if (t10 != null) {
          adapter._dateValue =  new dtv.util.DtvDate(t10.getTime());
      }
      else {
              adapter._dateValue =  null;
      }

      adapter._decimalValue = argResultSet.getBigDecimal(11);
      adapter._createUserId = argResultSet.getString(12);

      java.sql.Timestamp t13 = argResultSet.getTimestamp(13);
      if (t13 != null) {
          adapter._createDate =  new dtv.util.DtvDate(t13.getTime());
      }
      else {
              adapter._createDate =  null;
      }

      adapter._updateUserId = argResultSet.getString(14);

      java.sql.Timestamp t15 = argResultSet.getTimestamp(15);
      if (t15 != null) {
          adapter._updateDate =  new dtv.util.DtvDate(t15.getTime());
      }
      else {
              adapter._updateDate =  null;
      }

      }
  }

  public IDataAccessObject loadDAO(IDataAccessObject argDAO) {
    argDAO.suppressStateChanges(true);
    AsqZatcaInvoiceStagingPropertyDAO dao = (AsqZatcaInvoiceStagingPropertyDAO)argDAO;
    dao.setOrganizationId(_organizationId);
    dao.setInvoiceId(_invoiceId);
    dao.setBusinessDate(_businessDate);
    dao.setTransactionSeq(_transactionSeq);
    dao.setWorkStationId(_workStationId);
    dao.setIcv(_icv);
    dao.setPropertyCode(_propertyCode);
    dao.setType(_type);
    dao.setStringValue(_stringValue);
    dao.setDateValue(_dateValue);
    dao.setDecimalValue(_decimalValue);
    dao.setCreateUserId(_createUserId);
    dao.setCreateDate(_createDate);
    dao.setUpdateUserId(_updateUserId);
    dao.setUpdateDate(_updateDate);
    argDAO.suppressStateChanges(false);
    return dao;
  }

  public IDataAccessObject loadDefaultDAO() {
    return loadDAO(new AsqZatcaInvoiceStagingPropertyDAO());
  }

  public void fill(IDataAccessObject argDAO) {
    AsqZatcaInvoiceStagingPropertyDAO dao = (AsqZatcaInvoiceStagingPropertyDAO)argDAO;
    _organizationId = dao.getOrganizationId();
    _invoiceId = dao.getInvoiceId();
    _businessDate = dao.getBusinessDate();
    _transactionSeq = dao.getTransactionSeq();
    _workStationId = dao.getWorkStationId();
    _icv = dao.getIcv();
    _propertyCode = dao.getPropertyCode();
    _type = dao.getType();
    _stringValue = dao.getStringValue();
    _dateValue = dao.getDateValue();
    _decimalValue = dao.getDecimalValue();
    _createUserId = dao.getCreateUserId();
    _createDate = dao.getCreateDate();
    _updateUserId = dao.getUpdateUserId();
    _updateDate = dao.getUpdateDate();
  }

  public PreparedStatement writeObjectId(IObjectId argId, PreparedStatement argStatement) throws java.sql.SQLException {
    dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingPropertyId id = (dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingPropertyId) argId;
      argStatement.setLong(1, id.getOrganizationId().longValue());
      argStatement.setString(2, id.getInvoiceId());
      argStatement.setTimestamp(3, new java.sql.Timestamp(id.getBusinessDate().getTime()));
      argStatement.setLong(4, id.getTransactionSeq().longValue());
      argStatement.setLong(5, id.getWorkStationId().longValue());
      argStatement.setLong(6, id.getIcv().longValue());
      argStatement.setString(7, id.getPropertyCode());
    return argStatement;
  }

  public IObjectId getObjectId() {
    dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingPropertyId id = new dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingPropertyId();
    id.setOrganizationId(_organizationId);
    id.setInvoiceId(_invoiceId);
    id.setBusinessDate(_businessDate);
    id.setTransactionSeq(_transactionSeq);
    id.setWorkStationId(_workStationId);
    id.setIcv(_icv);
    id.setPropertyCode(_propertyCode);
    return id;
  }

  public void fill(IJDBCTableAdapter argAdapter) {
    // This DBA is not extensible, no need to implement!
  }

  public boolean isExtensible() {
    return false;
  }

  public String getImplementingClass() {
    return null; // This DBA is not extensible
  }

}
