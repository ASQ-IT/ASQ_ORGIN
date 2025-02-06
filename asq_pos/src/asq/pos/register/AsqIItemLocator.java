package asq.pos.register;
 
import dtv.data2.access.IDataModel;
import dtv.data2.access.IHasDataProperty;
import dtv.data2.access.IQueryKey;
import dtv.data2.access.IQueryResult;
import dtv.data2.access.QueryKey;
import dtv.pos.iframework.hardware.IHardwareType;
import dtv.pos.inventory.lookup.style.DimensionInfo;
import dtv.pos.register.IItemLocator;
import dtv.pos.register.LineItemAssociationType;
import dtv.pos.register.returns.ReturnItemType;
import dtv.pos.register.type.NonPhysicalItemSubType;
import dtv.pos.register.type.NonPhysicalItemType;
import dtv.xst.dao.itm.IItem;
import dtv.xst.dao.itm.INonPhysicalItem;
import dtv.xst.dao.trl.CorrectionModifierReasonCode;
import dtv.xst.dao.trl.ICommissionModifier;
import dtv.xst.dao.trl.ILineItemAssociationModifier;
import dtv.xst.dao.trl.ILineItemAssociationTypeCode;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trl.IRetailTransactionLineItem;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import dtv.xst.dao.trl.IVoucherSaleLineItem;
import dtv.xst.dao.trl.SaleItemType;
import dtv.xst.dao.trn.ILineItemGenericStorage;
import dtv.xst.dao.trn.IPosTransaction;
import dtv.xst.query.results.BasicItemQueryResult;import java.util.Date;import java.util.List;import java.util.Set;

 public interface AsqIItemLocator extends IItemLocator {
	 
  @SuppressWarnings({ "unchecked", "rawtypes" })
public static final IQueryKey<BasicItemQueryResult> ASQ_ITEM_LOOKUP = (IQueryKey<BasicItemQueryResult>)new QueryKey("ASQ_ITEM_LOOKUP", BasicItemQueryResult.class);
  
 }
