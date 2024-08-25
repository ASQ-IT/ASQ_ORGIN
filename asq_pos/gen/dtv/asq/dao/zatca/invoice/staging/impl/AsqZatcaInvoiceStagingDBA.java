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
public class AsqZatcaInvoiceStagingDBA implements IJDBCTableAdapter {

  private Long _organizationId;
  private String _invoiceId;
  private Date _businessDate;
  private Long _transactionSeq;
  private Long _workStationId;
  private Long _icv;
  private String _status;
  private byte[] _submittedJSON;
  private byte[] _invoiceQrCode;
  private String _invoiceUUID;
  private String _invoiceHashCode;
  private Date _invoiceDate;
  private String _createUserId;
  private Date _createDate;
  private String _updateUserId;
  private Date _updateDate;

  private static final String SELECT_OBJECT = "SELECT t.organization_id, t.INVOICE_ID, t.BUSINESS_DATE, t.TRANS_SEQ, t.wkstn_id, t.ICV, t.Status, t.INVOICE_XML, t.INVOICE_QRCODE, t.INVOICE_UUID, t.INVOICE_HASHCODE, t.INVOICE_DATE, t.create_user_id, t.create_date, t.update_user_id, t.update_date FROM ASQ_ZATCA_INVOICE_STAGING t";

  public String getSelect() {
    return getSelectImpl();
  }

  private String getSelectImpl() {
    return SELECT_OBJECT;
  }

  private static final String SELECT_WHERE_OBJECT = " WHERE organization_id = ?  AND INVOICE_ID = ?  AND BUSINESS_DATE = ?  AND TRANS_SEQ = ?  AND wkstn_id = ?  AND ICV = ?  ";

  public String getSelectWhere() {
    return SELECT_WHERE_OBJECT;
  }

  private static final String[] INSERT_OBJECT = new String[] {"INSERT INTO ASQ_ZATCA_INVOICE_STAGING(organization_id, INVOICE_ID, BUSINESS_DATE, TRANS_SEQ, wkstn_id, ICV, Status, INVOICE_XML, INVOICE_QRCODE, INVOICE_UUID, INVOICE_HASHCODE, INVOICE_DATE, create_user_id, create_date, update_user_id, update_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"};

  public String[] getInsert() {
    return INSERT_OBJECT;
  }

  public Object[][] getInsertParameters() {
    Object[][] insertParameterObject = new Object[][] {{_organizationId, _invoiceId, _businessDate, _transactionSeq, _workStationId, _icv, _status, _submittedJSON, _invoiceQrCode, _invoiceUUID, _invoiceHashCode, _invoiceDate, _createUserId, _createDate, _updateUserId, _updateDate}};
    return insertParameterObject;
  }

  private static final int[][] INSERT_PARAMETER_TYPES_OBJECT = new int[][] {{-5, 12, 91, -5, -5, -5, 12, -4, -4, 12, 12, 91, 12, 91, 12, 91}};

  public int[][] getInsertParameterTypes() {
    return INSERT_PARAMETER_TYPES_OBJECT;
  }

  private static final String[] UPDATE_OBJECT = new String[] {"UPDATE ASQ_ZATCA_INVOICE_STAGING SET Status = ?, INVOICE_XML = ?, INVOICE_QRCODE = ?, INVOICE_UUID = ?, INVOICE_HASHCODE = ?, INVOICE_DATE = ?, update_user_id = ?, update_date = ?"};

  public String[] getUpdate() {
    return UPDATE_OBJECT;
  }

  public Object[][] getUpdateParameters() {
    Object[][] updateParameterObject = new Object[][] {{_status, _submittedJSON, _invoiceQrCode, _invoiceUUID, _invoiceHashCode, _invoiceDate, _updateUserId, _updateDate}};
    return updateParameterObject;
  }

  private static final int[][] UPDATE_PARAMETER_TYPE_OBJECT = new int[][] {{12, -4, -4, 12, 12, 91, 12, 91}};
  public int[][] getUpdateParameterTypes() {
    return UPDATE_PARAMETER_TYPE_OBJECT;
  }

  private static final String[] DELETE_OBJECT = new String[] {"DELETE FROM ASQ_ZATCA_INVOICE_STAGING"};

  public String[] getDelete() {
    return DELETE_OBJECT;
  }

  private static final String WHERE_OBJECT = " WHERE organization_id = ?  AND INVOICE_ID = ?  AND BUSINESS_DATE = ?  AND TRANS_SEQ = ?  AND wkstn_id = ?  AND ICV = ?  ";

  public String getWhere() {
    return WHERE_OBJECT;
  }

  public Object[] getWhereParameters() {
    return new Object[] { _organizationId, _invoiceId, _businessDate, _transactionSeq, _workStationId, _icv };
  }

  private static final int[] WHERE_PARAMETER_OBJECT = new int[] { -5, 12, 91, -5, -5, -5 };

  public int[] getWhereParameterTypes() {
    return WHERE_PARAMETER_OBJECT;
  }

  private static final Map<String, String> TABLE_COLUMN_MAP;

  static {
    Map<String, String> tmpMap = new HashMap<>(16);
    tmpMap.put("organizationId", "organization_id");
    tmpMap.put("invoiceId", "INVOICE_ID");
    tmpMap.put("businessDate", "BUSINESS_DATE");
    tmpMap.put("transactionSeq", "TRANS_SEQ");
    tmpMap.put("workStationId", "wkstn_id");
    tmpMap.put("icv", "ICV");
    tmpMap.put("status", "Status");
    tmpMap.put("submittedJSON", "INVOICE_XML");
    tmpMap.put("invoiceQrCode", "INVOICE_QRCODE");
    tmpMap.put("invoiceUUID", "INVOICE_UUID");
    tmpMap.put("invoiceHashCode", "INVOICE_HASHCODE");
    tmpMap.put("invoiceDate", "INVOICE_DATE");
    tmpMap.put("createUserId", "create_user_id");
    tmpMap.put("createDate", "create_date");
    tmpMap.put("updateUserId", "update_user_id");
    tmpMap.put("updateDate", "update_date");
    TABLE_COLUMN_MAP = Collections.unmodifiableMap(tmpMap);
  }

  public String getTableName() {
    return "ASQ_ZATCA_INVOICE_STAGING";
  }

  public Map<String, String> getTableColumnMap() {
    return TABLE_COLUMN_MAP;
  }

  public IFiller getFiller() {
    return getFillerImpl();
  }

  private IFiller getFillerImpl() {
    return new AsqZatcaInvoiceStagingFiller(this);
  }

  private static class AsqZatcaInvoiceStagingFiller implements IFiller {

    private AsqZatcaInvoiceStagingDBA _parent;

    public AsqZatcaInvoiceStagingFiller(AsqZatcaInvoiceStagingDBA argParent) {
      _parent = argParent;
    }

    public dtv.data2.access.impl.jdbc.IJDBCTableAdapter fill(ResultSet argResultSet) throws java.sql.SQLException {
      dtv.data2.access.impl.jdbc.IJDBCTableAdapter filledObject = new AsqZatcaInvoiceStagingDBA();
      fill(argResultSet, filledObject);
      return filledObject;
    }

    public void fill(ResultSet argResultSet, dtv.data2.access.impl.jdbc.IJDBCTableAdapter argAdapter) throws java.sql.SQLException {
      AsqZatcaInvoiceStagingDBA adapter = (AsqZatcaInvoiceStagingDBA) argAdapter;


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

      adapter._status = argResultSet.getString(7);
      adapter._submittedJSON = argResultSet.getBytes(8);
      adapter._invoiceQrCode = argResultSet.getBytes(9);
      adapter._invoiceUUID = argResultSet.getString(10);
      adapter._invoiceHashCode = argResultSet.getString(11);

      java.sql.Timestamp t12 = argResultSet.getTimestamp(12);
      if (t12 != null) {
          adapter._invoiceDate =  new dtv.util.DtvDate(t12.getTime());
      }
      else {
              adapter._invoiceDate =  null;
      }

      adapter._createUserId = argResultSet.getString(13);

      java.sql.Timestamp t14 = argResultSet.getTimestamp(14);
      if (t14 != null) {
          adapter._createDate =  new dtv.util.DtvDate(t14.getTime());
      }
      else {
              adapter._createDate =  null;
      }

      adapter._updateUserId = argResultSet.getString(15);

      java.sql.Timestamp t16 = argResultSet.getTimestamp(16);
      if (t16 != null) {
          adapter._updateDate =  new dtv.util.DtvDate(t16.getTime());
      }
      else {
              adapter._updateDate =  null;
      }

      }
  }

  public IDataAccessObject loadDAO(IDataAccessObject argDAO) {
    argDAO.suppressStateChanges(true);
    AsqZatcaInvoiceStagingDAO dao = (AsqZatcaInvoiceStagingDAO)argDAO;
    dao.setOrganizationId(_organizationId);
    dao.setInvoiceId(_invoiceId);
    dao.setBusinessDate(_businessDate);
    dao.setTransactionSeq(_transactionSeq);
    dao.setWorkStationId(_workStationId);
    dao.setIcv(_icv);
    dao.setStatus(_status);
    dao.setSubmittedJSON(_submittedJSON);
    dao.setInvoiceQrCode(_invoiceQrCode);
    dao.setInvoiceUUID(_invoiceUUID);
    dao.setInvoiceHashCode(_invoiceHashCode);
    dao.setInvoiceDate(_invoiceDate);
    dao.setCreateUserId(_createUserId);
    dao.setCreateDate(_createDate);
    dao.setUpdateUserId(_updateUserId);
    dao.setUpdateDate(_updateDate);
    argDAO.suppressStateChanges(false);
    return dao;
  }

  public IDataAccessObject loadDefaultDAO() {
    return loadDAO(new AsqZatcaInvoiceStagingDAO());
  }

  public void fill(IDataAccessObject argDAO) {
    AsqZatcaInvoiceStagingDAO dao = (AsqZatcaInvoiceStagingDAO)argDAO;
    _organizationId = dao.getOrganizationId();
    _invoiceId = dao.getInvoiceId();
    _businessDate = dao.getBusinessDate();
    _transactionSeq = dao.getTransactionSeq();
    _workStationId = dao.getWorkStationId();
    _icv = dao.getIcv();
    _status = dao.getStatus();
    _submittedJSON = dao.getSubmittedJSON();
    _invoiceQrCode = dao.getInvoiceQrCode();
    _invoiceUUID = dao.getInvoiceUUID();
    _invoiceHashCode = dao.getInvoiceHashCode();
    _invoiceDate = dao.getInvoiceDate();
    _createUserId = dao.getCreateUserId();
    _createDate = dao.getCreateDate();
    _updateUserId = dao.getUpdateUserId();
    _updateDate = dao.getUpdateDate();
  }

  public PreparedStatement writeObjectId(IObjectId argId, PreparedStatement argStatement) throws java.sql.SQLException {
    dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingId id = (dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingId) argId;
      argStatement.setLong(1, id.getOrganizationId().longValue());
      argStatement.setString(2, id.getInvoiceId());
      argStatement.setTimestamp(3, new java.sql.Timestamp(id.getBusinessDate().getTime()));
      argStatement.setLong(4, id.getTransactionSeq().longValue());
      argStatement.setLong(5, id.getWorkStationId().longValue());
      argStatement.setLong(6, id.getIcv().longValue());
    return argStatement;
  }

  public IObjectId getObjectId() {
    dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingId id = new dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingId();
    id.setOrganizationId(_organizationId);
    id.setInvoiceId(_invoiceId);
    id.setBusinessDate(_businessDate);
    id.setTransactionSeq(_transactionSeq);
    id.setWorkStationId(_workStationId);
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
