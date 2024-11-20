package asq.pos.registery;

import java.math.BigDecimal;

import javax.inject.Inject;
import org.apache.logging.log4j.Logger;
import dtv.event.IEventSource;
import dtv.event.eventor.DefaultEventor;
import dtv.hardware.custdisplay.IDtvCustDisplayDevice;
import dtv.hardware.posprinting.ReceiptInfo;
import dtv.pos.common.OpChainKey;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.scope.ValueKey;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.register.CheckSaleCompleteOp;
import dtv.pos.tender.TenderHelper;
import dtv.util.NumberUtils;
import dtv.xst.dao.itm.IItem;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import dtv.xst.dao.trn.IPosTransaction;
import org.apache.logging.log4j.LogManager;

public class AsqCheckSaleCompleteOp extends CheckSaleCompleteOp {
	private static final Logger logger_ = LogManager.getLogger(CheckSaleCompleteOp.class);
	@Inject
	private TenderHelper _tenderHelper;
	@Override
	
	 protected OpChainKey getSaleCompleteOpChainKey() {
		               return OpChainKey.valueOf("SALE_COMPLETE1");
		           }
    protected IOpResponse handleInitialState() {
    	IPosTransaction trans = this._transactionScope.getTransaction();
    	if(trans!=null) {
	      BigDecimal tenderAmt = trans.getAmountTendered();// 526
	      BigDecimal totalAmt = trans.getTotal().add(trans.getRoundedAmount());// 527
	      this.setScopedValue(ValueKeys.RECEIPT_INFO, new ReceiptInfo());// 529
	      BigDecimal accountCreditTotal = this._tenderHelper.getAccountCreditTotal(trans);// 531
	      if (totalAmt == null) {// 533
	         logger_.error("Null total for transaction with ID [" + trans.getObjectIdAsString() + "]", new Throwable());// 534
	         return this.HELPER.errorNotifyResponse();// 536
	      } else {
	         if (this._transactionHelper.getReturnCount(trans).compareTo(BigDecimal.ZERO) > 0) {// 538
	            tenderAmt = tenderAmt.subtract(accountCreditTotal);// 539
	            totalAmt = totalAmt.subtract(accountCreditTotal);// 540
	         }
				else {
	            return this.handleSaleComplete();// 546
	         }
	      }
	   }
    	return this.HELPER.getStartChainResponse(OpChainKey.valueOf("SALE_ITEM_START1"));
    }
    protected String getPromptKeyPrintReceipt() {
                   return "PRINT_RECEIPTS1";
    	           }
    protected String getPromptKeyEmailOptPrintReceipt() {
    	                return "PRINT_RECEIPTS1";
    	           }
}
