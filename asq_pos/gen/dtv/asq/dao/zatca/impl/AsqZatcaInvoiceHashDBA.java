// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.GenerateDaoAndDba 2024-08-22T18:06:17
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
public class AsqZatcaInvoiceHashDBA implements IJDBCTableAdapter {

  private Long _organizationId;
  private Long _icv;
  private String _invoiceHash;
  private String _invoiceId;
  private Date _invoiceDate;
  private String _createUserId;
  private Date _createDate;
  private String _updateUserId;
  private Date _updateDate;

  private static final String SELECT_OBJECT = "SELECT t.organization_id, t.ICV, t.INVOICE_HASH, t.INVOICE_ID, t.INVOICE_DATE, t.create_user_id, t.create_date, t.update_user_id, t.update_date FROM ASQ_ZATCA_INVOICE_HASH t";

  public String getSelect() {
    return getSelectImpl();
  }

  private String getSelectImpl() {
    return SELECT_OBJECT;
  }

  private static final String SELECT_WHERE_OBJECT = " WHERE organization_id = ?  AND ICV = ?  ";

  public String getSelectWhere() {
    return SELECT_WHERE_OBJECT;
  }

  private static final String[] INSERT_OBJECT = new String[] {"INSERT INTO ASQ_ZATCA_INVOICE_HASH(organization_id, ICV, INVOICE_HASH, INVOICE_ID, INVOICE_DATE, create_user_id, create_date, update_user_id, update_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"};

  public String[] getInsert() {
    return INSERT_OBJECT;
  }

  public Object[][] getInsertParameters() {
    Object[][] insertParameterObject = new Object[][] {{_organizationId, _icv, _invoiceHash, _invoiceId, _invoiceDate, _createUserId, _createDate, _updateUserId, _updateDate}};
    return insertParameterObject;
  }

  private static final int[][] INSERT_PARAMETER_TYPES_OBJECT = new int[][] {{-5, -5, 12, 12, 91, 12, 91, 12, 91}};

  public int[][] getInsertParameterTypes() {
    return INSERT_PARAMETER_TYPES_OBJECT;
  }

  private static final String[] UPDATE_OBJECT = new String[] {"UPDATE ASQ_ZATCA_INVOICE_HASH SET INVOICE_HASH = ?, INVOICE_ID = ?, INVOICE_DATE = ?, update_user_id = ?, update_date = ?"};

  public String[] getUpdate() {
    return UPDATE_OBJECT;
  }

  public Object[][] getUpdateParameters() {
    Object[][] updateParameterObject = new Object[][] {{_invoiceHash, _invoiceId, _invoiceDate, _updateUserId, _updateDate}};
    return updateParameterObject;
  }

  private static final int[][] UPDATE_PARAMETER_TYPE_OBJECT = new int[][] {{12, 12, 91, 12, 91}};
  public int[][] getUpdateParameterTypes() {
    return UPDATE_PARAMETER_TYPE_OBJECT;
  }

  private static final String[] DELETE_OBJECT = new String[] {"DELETE FROM ASQ_ZATCA_INVOICE_HASH"};

  public String[] getDelete() {
    return DELETE_OBJECT;
  }

  private static final String WHERE_OBJECT = " WHERE organization_id = ?  AND ICV = ?  ";

  public String getWhere() {
    return WHERE_OBJECT;
  }

  public Object[] getWhereParameters() {
    return new Object[] { _organizationId, _icv };
  }

  private static final int[] WHERE_PARAMETER_OBJECT = new int[] { -5, -5 };

  public int[] getWhereParameterTypes() {
    return WHERE_PARAMETER_OBJECT;
  }

  private static final Map<String, String> TABLE_COLUMN_MAP;

  static {
    Map<String, String> tmpMap = new HashMap<>(9);
    tmpMap.put("organizationId", "organization_id");
    tmpMap.put("icv", "ICV");
    tmpMap.put("invoiceHash", "INVOICE_HASH");
    tmpMap.put("invoiceId", "INVOICE_ID");
    tmpMap.put("invoiceDate", "INVOICE_DATE");
    tmpMap.put("createUserId", "create_user_id");
    tmpMap.put("createDate", "create_date");
    tmpMap.put("updateUserId", "update_user_id");
    tmpMap.put("updateDate", "update_date");
    TABLE_COLUMN_MAP = Collections.unmodifiableMap(tmpMap);
  }

  public String getTableName() {
    return "ASQ_ZATCA_INVOICE_HASH";
  }

  public Map<String, String> getTableColumnMap() {
    return TABLE_COLUMN_MAP;
  }

  public IFiller getFiller() {
    return getFillerImpl();
  }

  private IFiller getFillerImpl() {
    return new AsqZatcaInvoiceHashFiller(this);
  }

  private static class AsqZatcaInvoiceHashFiller implements IFiller {

    private AsqZatcaInvoiceHashDBA _parent;

    public AsqZatcaInvoiceHashFiller(AsqZatcaInvoiceHashDBA argParent) {
      _parent = argParent;
    }

    public dtv.data2.access.impl.jdbc.IJDBCTableAdapter fill(ResultSet argResultSet) throws java.sql.SQLException {
      dtv.data2.access.impl.jdbc.IJDBCTableAdapter filledObject = new AsqZatcaInvoiceHashDBA();
      fill(argResultSet, filledObject);
      return filledObject;
    }

    public void fill(ResultSet argResultSet, dtv.data2.access.impl.jdbc.IJDBCTableAdapter argAdapter) throws java.sql.SQLException {
      AsqZatcaInvoiceHashDBA adapter = (AsqZatcaInvoiceHashDBA) argAdapter;


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

      adapter._invoiceHash = argResultSet.getString(3);
      adapter._invoiceId = argResultSet.getString(4);

      java.sql.Timestamp t5 = argResultSet.getTimestamp(5);
      if (t5 != null) {
          adapter._invoiceDate =  new dtv.util.DtvDate(t5.getTime());
      }
      else {
              adapter._invoiceDate =  null;
      }

      adapter._createUserId = argResultSet.getString(6);

      java.sql.Timestamp t7 = argResultSet.getTimestamp(7);
      if (t7 != null) {
          adapter._createDate =  new dtv.util.DtvDate(t7.getTime());
      }
      else {
              adapter._createDate =  null;
      }

      adapter._updateUserId = argResultSet.getString(8);

      java.sql.Timestamp t9 = argResultSet.getTimestamp(9);
      if (t9 != null) {
          adapter._updateDate =  new dtv.util.DtvDate(t9.getTime());
      }
      else {
              adapter._updateDate =  null;
      }

      }
  }

  public IDataAccessObject loadDAO(IDataAccessObject argDAO) {
    argDAO.suppressStateChanges(true);
    AsqZatcaInvoiceHashDAO dao = (AsqZatcaInvoiceHashDAO)argDAO;
    dao.setOrganizationId(_organizationId);
    dao.setIcv(_icv);
    dao.setInvoiceHash(_invoiceHash);
    dao.setInvoiceId(_invoiceId);
    dao.setInvoiceDate(_invoiceDate);
    dao.setCreateUserId(_createUserId);
    dao.setCreateDate(_createDate);
    dao.setUpdateUserId(_updateUserId);
    dao.setUpdateDate(_updateDate);
    argDAO.suppressStateChanges(false);
    return dao;
  }

  public IDataAccessObject loadDefaultDAO() {
    return loadDAO(new AsqZatcaInvoiceHashDAO());
  }

  public void fill(IDataAccessObject argDAO) {
    AsqZatcaInvoiceHashDAO dao = (AsqZatcaInvoiceHashDAO)argDAO;
    _organizationId = dao.getOrganizationId();
    _icv = dao.getIcv();
    _invoiceHash = dao.getInvoiceHash();
    _invoiceId = dao.getInvoiceId();
    _invoiceDate = dao.getInvoiceDate();
    _createUserId = dao.getCreateUserId();
    _createDate = dao.getCreateDate();
    _updateUserId = dao.getUpdateUserId();
    _updateDate = dao.getUpdateDate();
  }

  public PreparedStatement writeObjectId(IObjectId argId, PreparedStatement argStatement) throws java.sql.SQLException {
    dtv.asq.dao.zatca.AsqZatcaInvoiceHashId id = (dtv.asq.dao.zatca.AsqZatcaInvoiceHashId) argId;
      argStatement.setLong(1, id.getOrganizationId().longValue());
      argStatement.setLong(2, id.getIcv().longValue());
    return argStatement;
  }

  public IObjectId getObjectId() {
    dtv.asq.dao.zatca.AsqZatcaInvoiceHashId id = new dtv.asq.dao.zatca.AsqZatcaInvoiceHashId();
    id.setOrganizationId(_organizationId);
    id.setIcv(_icv);
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
