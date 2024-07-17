// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.GenerateDaoAndDba 2024-07-13T15:19:37
// CHECKSTYLE:OFF
package dtv.asq.dao.zatca.impl;

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
public class AsqZatcaInvoiceHashPropertyDBA implements IJDBCTableAdapter {

  private Long _organizationId;
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

  private static final String SELECT_OBJECT = "SELECT t.organization_id, t.ICV, t.property_code, t.type, t.string_value, t.date_value, t.decimal_value, t.create_user_id, t.create_date, t.update_user_id, t.update_date FROM ASQ_ZATCA_INVOICE_HASH_p t";

  public String getSelect() {
    return getSelectImpl();
  }

  private String getSelectImpl() {
    return SELECT_OBJECT;
  }

  private static final String SELECT_WHERE_OBJECT = " WHERE organization_id = ?  AND ICV = ?  AND property_code = ?  ";

  public String getSelectWhere() {
    return SELECT_WHERE_OBJECT;
  }

  private static final String[] INSERT_OBJECT = new String[] {"INSERT INTO ASQ_ZATCA_INVOICE_HASH_p(organization_id, ICV, property_code, type, string_value, date_value, decimal_value, create_user_id, create_date, update_user_id, update_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"};

  public String[] getInsert() {
    return INSERT_OBJECT;
  }

  public Object[][] getInsertParameters() {
    Object[][] insertParameterObject = new Object[][] {{_organizationId, _icv, _propertyCode, _type, _stringValue, _dateValue, _decimalValue, _createUserId, _createDate, _updateUserId, _updateDate}};
    return insertParameterObject;
  }

  private static final int[][] INSERT_PARAMETER_TYPES_OBJECT = new int[][] {{-5, -5, 12, 12, 12, 91, 3, 12, 91, 12, 91}};

  public int[][] getInsertParameterTypes() {
    return INSERT_PARAMETER_TYPES_OBJECT;
  }

  private static final String[] UPDATE_OBJECT = new String[] {"UPDATE ASQ_ZATCA_INVOICE_HASH_p SET type = ?, string_value = ?, date_value = ?, decimal_value = ?, update_user_id = ?, update_date = ?"};

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

  private static final String[] DELETE_OBJECT = new String[] {"DELETE FROM ASQ_ZATCA_INVOICE_HASH_p"};

  public String[] getDelete() {
    return DELETE_OBJECT;
  }

  private static final String WHERE_OBJECT = " WHERE organization_id = ?  AND ICV = ?  AND property_code = ?  ";

  public String getWhere() {
    return WHERE_OBJECT;
  }

  public Object[] getWhereParameters() {
    return new Object[] { _organizationId, _icv, _propertyCode };
  }

  private static final int[] WHERE_PARAMETER_OBJECT = new int[] { -5, -5, 12 };

  public int[] getWhereParameterTypes() {
    return WHERE_PARAMETER_OBJECT;
  }

  private static final Map<String, String> TABLE_COLUMN_MAP;

  static {
    Map<String, String> tmpMap = new HashMap<>(11);
    tmpMap.put("organizationId", "organization_id");
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
    return "ASQ_ZATCA_INVOICE_HASH_p";
  }

  public Map<String, String> getTableColumnMap() {
    return TABLE_COLUMN_MAP;
  }

  public IFiller getFiller() {
    return getFillerImpl();
  }

  private IFiller getFillerImpl() {
    return new AsqZatcaInvoiceHashPropertyFiller(this);
  }

  private static class AsqZatcaInvoiceHashPropertyFiller implements IFiller {

    private AsqZatcaInvoiceHashPropertyDBA _parent;

    public AsqZatcaInvoiceHashPropertyFiller(AsqZatcaInvoiceHashPropertyDBA argParent) {
      _parent = argParent;
    }

    public dtv.data2.access.impl.jdbc.IJDBCTableAdapter fill(ResultSet argResultSet) throws java.sql.SQLException {
      dtv.data2.access.impl.jdbc.IJDBCTableAdapter filledObject = new AsqZatcaInvoiceHashPropertyDBA();
      fill(argResultSet, filledObject);
      return filledObject;
    }

    public void fill(ResultSet argResultSet, dtv.data2.access.impl.jdbc.IJDBCTableAdapter argAdapter) throws java.sql.SQLException {
      AsqZatcaInvoiceHashPropertyDBA adapter = (AsqZatcaInvoiceHashPropertyDBA) argAdapter;


      { // load Long value for field: organizationId while preserving null values.
        long primitiveResult = argResultSet.getLong(1);
        if (primitiveResult != 0 || argResultSet.getObject(1) != null) {
              adapter._organizationId = Long.valueOf(primitiveResult);
        }
      }


      { // load Long value for field: icv while preserving null values.
        long primitiveResult = argResultSet.getLong(2);
        if (primitiveResult != 0 || argResultSet.getObject(2) != null) {
              adapter._icv = Long.valueOf(primitiveResult);
        }
      }

      adapter._propertyCode = argResultSet.getString(3);
      adapter._type = argResultSet.getString(4);
      adapter._stringValue = argResultSet.getString(5);

      java.sql.Timestamp t6 = argResultSet.getTimestamp(6);
      if (t6 != null) {
          adapter._dateValue =  new dtv.util.DtvDate(t6.getTime());
      }
      else {
              adapter._dateValue =  null;
      }

      adapter._decimalValue = argResultSet.getBigDecimal(7);
      adapter._createUserId = argResultSet.getString(8);

      java.sql.Timestamp t9 = argResultSet.getTimestamp(9);
      if (t9 != null) {
          adapter._createDate =  new dtv.util.DtvDate(t9.getTime());
      }
      else {
              adapter._createDate =  null;
      }

      adapter._updateUserId = argResultSet.getString(10);

      java.sql.Timestamp t11 = argResultSet.getTimestamp(11);
      if (t11 != null) {
          adapter._updateDate =  new dtv.util.DtvDate(t11.getTime());
      }
      else {
              adapter._updateDate =  null;
      }

      }
  }

  public IDataAccessObject loadDAO(IDataAccessObject argDAO) {
    argDAO.suppressStateChanges(true);
    AsqZatcaInvoiceHashPropertyDAO dao = (AsqZatcaInvoiceHashPropertyDAO)argDAO;
    dao.setOrganizationId(_organizationId);
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
    return loadDAO(new AsqZatcaInvoiceHashPropertyDAO());
  }

  public void fill(IDataAccessObject argDAO) {
    AsqZatcaInvoiceHashPropertyDAO dao = (AsqZatcaInvoiceHashPropertyDAO)argDAO;
    _organizationId = dao.getOrganizationId();
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
    dtv.asq.dao.zatca.AsqZatcaInvoiceHashPropertyId id = (dtv.asq.dao.zatca.AsqZatcaInvoiceHashPropertyId) argId;
      argStatement.setLong(1, id.getOrganizationId().longValue());
      argStatement.setLong(2, id.getIcv().longValue());
      argStatement.setString(3, id.getPropertyCode());
    return argStatement;
  }

  public IObjectId getObjectId() {
    dtv.asq.dao.zatca.AsqZatcaInvoiceHashPropertyId id = new dtv.asq.dao.zatca.AsqZatcaInvoiceHashPropertyId();
    id.setOrganizationId(_organizationId);
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
