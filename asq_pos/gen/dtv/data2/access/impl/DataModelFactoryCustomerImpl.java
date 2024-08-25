// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.GenerateDataModelFactoryOverride 2024-08-22T18:06:17
// CHECKSTYLE:OFF
package dtv.data2.access.impl;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;
import dtv.data2.access.*;
import dtv.data2.access.impl.*;
import dtv.data2.access.ChainedRelationshipSetProducer;
import dtv.data2.access.IDataModelRelationship;

/**
 * Generated code.
 *
 * @author DAOGen
 */
@SuppressWarnings("all")
public class DataModelFactoryCustomerImpl extends dtv.data2.access.impl.DataModelFactoryImpl {

  {
    Map<Class<?>, Supplier<IDataModelRelationship[]>> extRelationships = new HashMap<>();
    Supplier<IDataModelRelationship[]> relationshipProducer;
    String parentClassName = null;

    Supplier<IDataModelRelationship[]> baseRelationshipProducer;
    Supplier<IDataModelRelationship[]> custRelationshipProducer;
    relationshipProducer = new Supplier<IDataModelRelationship[]>() {
      @Override
      public IDataModelRelationship[] get() {
       IDataModelRelationship[] rels = new IDataModelRelationship[1];
        rels[0] = new OneToManyRelationship("Properties", "dtv.asq.dao.zatca.AsqZatcaInvoiceHashPropertyId", false, false, true);
        return rels;
      }
    };
    addRelationshipProducer("dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashDAO", "AsqZatcaInvoiceHash", relationshipProducer);
    addDataModels("AsqZatcaInvoiceHash", new AbstractInstanceGenerator<IDataModelImpl>(){
      @Override
      protected Class<? extends IDataModelImpl> getType() {
        return dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashModel.class;
      }
    });
    addDaos("AsqZatcaInvoiceHash", new AbstractInstanceGenerator<IDataAccessObject>(){
      @Override
      protected Class<? extends IDataAccessObject> getType() {
        return dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashDAO.class;
      }
    });
    addObjectIds("AsqZatcaInvoiceHash", new AbstractInstanceGenerator<IObjectId>(){
      @Override
      protected Class<? extends IObjectId> getType() {
        return dtv.asq.dao.zatca.AsqZatcaInvoiceHashId.class;
      }
    });
    relationshipProducer = new Supplier<IDataModelRelationship[]>() {
      @Override
      public IDataModelRelationship[] get() {
       IDataModelRelationship[] rels = new IDataModelRelationship[1];
        rels[0] = new OneToManyRelationship("Properties", "dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingPropertyId", false, false, true);
        return rels;
      }
    };
    addRelationshipProducer("dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingDAO", "AsqZatcaInvoiceStaging", relationshipProducer);
    addDataModels("AsqZatcaInvoiceStaging", new AbstractInstanceGenerator<IDataModelImpl>(){
      @Override
      protected Class<? extends IDataModelImpl> getType() {
        return dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingModel.class;
      }
    });
    addDaos("AsqZatcaInvoiceStaging", new AbstractInstanceGenerator<IDataAccessObject>(){
      @Override
      protected Class<? extends IDataAccessObject> getType() {
        return dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingDAO.class;
      }
    });
    addObjectIds("AsqZatcaInvoiceStaging", new AbstractInstanceGenerator<IObjectId>(){
      @Override
      protected Class<? extends IObjectId> getType() {
        return dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingId.class;
      }
    });
    addDataModels("AsqZatcaInvoiceHashProperty", new AbstractInstanceGenerator<IDataModelImpl>(){
      @Override
      protected Class<? extends IDataModelImpl> getType() {
        return dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashPropertyModel.class;
      }
    });
    addDaos("AsqZatcaInvoiceHashProperty", new AbstractInstanceGenerator<IDataAccessObject>(){
      @Override
      protected Class<? extends IDataAccessObject> getType() {
        return dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashPropertyDAO.class;
      }
    });
    addObjectIds("AsqZatcaInvoiceHashProperty", new AbstractInstanceGenerator<IObjectId>(){
      @Override
      protected Class<? extends IObjectId> getType() {
        return dtv.asq.dao.zatca.AsqZatcaInvoiceHashPropertyId.class;
      }
    });
    addDataModels("AsqZatcaInvoiceStagingProperty", new AbstractInstanceGenerator<IDataModelImpl>(){
      @Override
      protected Class<? extends IDataModelImpl> getType() {
        return dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingPropertyModel.class;
      }
    });
    addDaos("AsqZatcaInvoiceStagingProperty", new AbstractInstanceGenerator<IDataAccessObject>(){
      @Override
      protected Class<? extends IDataAccessObject> getType() {
        return dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingPropertyDAO.class;
      }
    });
    addObjectIds("AsqZatcaInvoiceStagingProperty", new AbstractInstanceGenerator<IObjectId>(){
      @Override
      protected Class<? extends IObjectId> getType() {
        return dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingPropertyId.class;
      }
    });
  }

}

