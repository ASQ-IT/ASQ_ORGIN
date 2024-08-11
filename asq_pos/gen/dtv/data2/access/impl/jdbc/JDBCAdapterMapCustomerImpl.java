// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.GenerateJDBCMappingOverride 2024-08-11T15:27:53
// CHECKSTYLE:OFF
package dtv.data2.access.impl.jdbc;

import dtv.data2.access.AbstractInstanceGenerator;
import dtv.data2.access.impl.jdbc.IJDBCTableAdapter;
import dtv.data2.access.impl.jdbc.IJDBCRelationshipAdapter;


/**
 * Generated code.
 *
 * @author DAOGen
 */
@SuppressWarnings("all")
public class JDBCAdapterMapCustomerImpl extends dtv.data2.access.impl.jdbc.JDBCAdapterMapImpl {

  {
    // populate the adapter map
    AbstractInstanceGenerator<IJDBCTableAdapter> generator;
    generator = new AbstractInstanceGenerator<IJDBCTableAdapter>() {
      @Override
      protected Class<? extends IJDBCTableAdapter> getType() {
        return dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashDBA.class;
      }
    };
    addAdapter("dtv.asq.dao.zatca.AsqZatcaInvoiceHashId", generator);
    addAdapter("dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashDAO", generator);
    addAdapter("dtv.asq.dao.zatca.IAsqZatcaInvoiceHash", generator);
    addAdapter("AsqZatcaInvoiceHash", generator);
    generator = new AbstractInstanceGenerator<IJDBCTableAdapter>() {
      @Override
      protected Class<? extends IJDBCTableAdapter> getType() {
        return dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingDBA.class;
      }
    };
    addAdapter("dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingId", generator);
    addAdapter("dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingDAO", generator);
    addAdapter("dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging", generator);
    addAdapter("AsqZatcaInvoiceStaging", generator);
    generator = new AbstractInstanceGenerator<IJDBCTableAdapter>() {
      @Override
      protected Class<? extends IJDBCTableAdapter> getType() {
        return dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashPropertyDBA.class;
      }
    };
    addAdapter("dtv.asq.dao.zatca.AsqZatcaInvoiceHashPropertyId", generator);
    addAdapter("dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashPropertyDAO", generator);
    addAdapter("dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty", generator);
    addAdapter("AsqZatcaInvoiceHashProperty", generator);
    generator = new AbstractInstanceGenerator<IJDBCTableAdapter>() {
      @Override
      protected Class<? extends IJDBCTableAdapter> getType() {
        return dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingPropertyDBA.class;
      }
    };
    addAdapter("dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingPropertyId", generator);
    addAdapter("dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingPropertyDAO", generator);
    addAdapter("dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStagingProperty", generator);
    addAdapter("AsqZatcaInvoiceStagingProperty", generator);


    // populate the relationship adapter map
    addRelationshipAdapter("dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashDAO-Properties", new AbstractInstanceGenerator<IJDBCRelationshipAdapter>() {
      @Override
      protected Class<? extends IJDBCRelationshipAdapter> getType() {
        return dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashPropertiesRelationshipDBA.class;
      }
    });
    addRelationshipAdapter("dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingDAO-Properties", new AbstractInstanceGenerator<IJDBCRelationshipAdapter>() {
      @Override
      protected Class<? extends IJDBCRelationshipAdapter> getType() {
        return dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingPropertiesRelationshipDBA.class;
      }
    });
  }

}

