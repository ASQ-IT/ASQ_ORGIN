// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.GenerateXloadTypeLookupImpl 2024-08-11T15:27:53
// CHECKSTYLE:OFF
package dtv.data2.dataloader.pluggable.xload;

import dtv.data2.access.FieldDataType;
import java.util.function.Supplier;

/**
 * Generated code.
 *
 * @author DAOGen
 */
@SuppressWarnings("all")
public class OverrideXloadTypeLookupImpl
  extends dtv.data2.dataloader.pluggable.xload.AbstractXloadTypeLookupImpl {
  {
    addColumns(new Supplier<ColumnMetadata[]>() {
      public ColumnMetadata[] get() { return new ColumnMetadata[] {
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH", "ORGANIZATION_ID", FieldDataType.LONG, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH", "ICV", FieldDataType.LONG, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH", "INVOICE_HASH", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH", "INVOICE_ID", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH", "INVOICE_DATE", FieldDataType.DATE, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH", "CREATE_USER_ID", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH", "CREATE_DATE", FieldDataType.DATE, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH", "UPDATE_USER_ID", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH", "UPDATE_DATE", FieldDataType.DATE, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH", "RECORD_STATE", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "ORGANIZATION_ID", FieldDataType.LONG, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "ICV", FieldDataType.LONG, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "PROPERTY_CODE", FieldDataType.STRING, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "TYPE", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "STRING_VALUE", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "DATE_VALUE", FieldDataType.DATE, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "DECIMAL_VALUE", FieldDataType.DECIMAL, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "CREATE_USER_ID", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "CREATE_DATE", FieldDataType.DATE, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "UPDATE_USER_ID", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "UPDATE_DATE", FieldDataType.DATE, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "RECORD_STATE", FieldDataType.STRING, false),
        };}});
    addColumns(new Supplier<ColumnMetadata[]>() {
      public ColumnMetadata[] get() { return new ColumnMetadata[] {
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING", "ORGANIZATION_ID", FieldDataType.LONG, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING", "INVOICE_ID", FieldDataType.STRING, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING", "BUSINESS_DATE", FieldDataType.DATE, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING", "TRANS_SEQ", FieldDataType.LONG, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING", "WKSTN_ID", FieldDataType.LONG, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING", "STATUS", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING", "JSON_INVOICE", FieldDataType.OBJECT, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING", "INVOICE_QRCODE", FieldDataType.OBJECT, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING", "INVOICE_UIID", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING", "CREATE_USER_ID", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING", "CREATE_DATE", FieldDataType.DATE, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING", "UPDATE_USER_ID", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING", "UPDATE_DATE", FieldDataType.DATE, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING", "RECORD_STATE", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "ORGANIZATION_ID", FieldDataType.LONG, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "INVOICE_ID", FieldDataType.STRING, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "BUSINESS_DATE", FieldDataType.DATE, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "TRANS_SEQ", FieldDataType.LONG, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "WKSTN_ID", FieldDataType.LONG, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "PROPERTY_CODE", FieldDataType.STRING, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "TYPE", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "STRING_VALUE", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "DATE_VALUE", FieldDataType.DATE, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "DECIMAL_VALUE", FieldDataType.DECIMAL, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "CREATE_USER_ID", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "CREATE_DATE", FieldDataType.DATE, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "UPDATE_USER_ID", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "UPDATE_DATE", FieldDataType.DATE, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "RECORD_STATE", FieldDataType.STRING, false),
        };}});
    addColumns(new Supplier<ColumnMetadata[]>() {
      public ColumnMetadata[] get() { return new ColumnMetadata[] {
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "ORGANIZATION_ID", FieldDataType.LONG, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "ICV", FieldDataType.LONG, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "PROPERTY_CODE", FieldDataType.STRING, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "TYPE", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "STRING_VALUE", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "DATE_VALUE", FieldDataType.DATE, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "DECIMAL_VALUE", FieldDataType.DECIMAL, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "CREATE_USER_ID", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "CREATE_DATE", FieldDataType.DATE, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "UPDATE_USER_ID", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "UPDATE_DATE", FieldDataType.DATE, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_HASH_P", "RECORD_STATE", FieldDataType.STRING, false),
        };}});
    addColumns(new Supplier<ColumnMetadata[]>() {
      public ColumnMetadata[] get() { return new ColumnMetadata[] {
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "ORGANIZATION_ID", FieldDataType.LONG, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "INVOICE_ID", FieldDataType.STRING, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "BUSINESS_DATE", FieldDataType.DATE, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "TRANS_SEQ", FieldDataType.LONG, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "WKSTN_ID", FieldDataType.LONG, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "PROPERTY_CODE", FieldDataType.STRING, true),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "TYPE", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "STRING_VALUE", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "DATE_VALUE", FieldDataType.DATE, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "DECIMAL_VALUE", FieldDataType.DECIMAL, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "CREATE_USER_ID", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "CREATE_DATE", FieldDataType.DATE, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "UPDATE_USER_ID", FieldDataType.STRING, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "UPDATE_DATE", FieldDataType.DATE, false),
          new ColumnMetadata("ASQ_ZATCA_INVOICE_STAGING_P", "RECORD_STATE", FieldDataType.STRING, false),
        };}});
  }
}

