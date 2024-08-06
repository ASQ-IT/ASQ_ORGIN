// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.GenerateJDBCMappingOverride 2024-07-13T15:19:37
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
        return dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashPropertyDBA.class;
      }
    };
    addAdapter("dtv.asq.dao.zatca.AsqZatcaInvoiceHashPropertyId", generator);
    addAdapter("dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashPropertyDAO", generator);
    addAdapter("dtv.asq.dao.zatca.IAsqZatcaInvoiceHashProperty", generator);
    addAdapter("AsqZatcaInvoiceHashProperty", generator);


    // populate the relationship adapter map
    addRelationshipAdapter("dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashDAO-Properties", new AbstractInstanceGenerator<IJDBCRelationshipAdapter>() {
      @Override
      protected Class<? extends IJDBCRelationshipAdapter> getType() {
        return dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashPropertiesRelationshipDBA.class;
      }
    });
  }

}

