package asq.pos.registery;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dtv.hardware.posprinting.ReceiptInfo;
import dtv.hardware.rcptbuilding.IRcptBuilder;
import dtv.hardware.rcptbuilding.ReceiptDocInfo;
import dtv.pos.common.OpChainKey;
import dtv.pos.common.SysConfigAccessor;
import dtv.pos.common.TransactionScopeKeys;
import dtv.pos.common.TransactionType;
import dtv.pos.common.ValueKeys;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.register.CheckSaleCompleteOp;
import dtv.pos.tender.TenderHelper;
import dtv.util.IReceiptSource;
import dtv.xst.dao.crm.IParty;
import dtv.xst.dao.trn.IPosTransaction;

public class AsqCheckSaleCompleteOp extends CheckSaleCompleteOp {

	private static final Logger logger_ = LogManager.getLogger(CheckSaleCompleteOp.class);

	@Inject
	private TenderHelper _tenderHelper;

	@Inject
	private IRcptBuilder _rcptBuilder;

	@Inject
	private SysConfigAccessor _sysConfig;

	@Override
	protected OpChainKey getSaleCompleteOpChainKey() {
		return OpChainKey.valueOf("SALE_COMPLETE1");
	}

	@Override
	protected String getPromptKeySaleComplete() {
		return "";
	}

	@Override
	protected IOpResponse handleInitialState() {
		IPosTransaction trans = this._transactionScope.getTransaction();
		if (trans != null) {
			BigDecimal tenderAmt = trans.getAmountTendered();
			BigDecimal totalAmt = trans.getTotal().add(trans.getRoundedAmount());
			this.setScopedValue(ValueKeys.RECEIPT_INFO, new ReceiptInfo());
			BigDecimal accountCreditTotal = this._tenderHelper.getAccountCreditTotal(trans);
			if (totalAmt == null) {
				logger_.error("Null total for transaction with ID [" + trans.getObjectIdAsString() + "]", new Throwable());
				return this.HELPER.errorNotifyResponse();
			} else {
				if (this._transactionHelper.getReturnCount(trans).compareTo(BigDecimal.ZERO) > 0) {
					tenderAmt = tenderAmt.subtract(accountCreditTotal);
					totalAmt = totalAmt.subtract(accountCreditTotal);
				} else {
					return this.handleSaleComplete();
				}
			}
		}
		return this.HELPER.getStartChainResponse(OpChainKey.valueOf("SALE_ITEM_START1"));
	}

	@Override
	protected void createReceiptsList() {
		List<IReceiptSource> rcptSrcList = this.getScopedValue(TransactionScopeKeys.CURRENT_RECEIPT_SOURCE);
		if (rcptSrcList == null) {
			rcptSrcList = new ArrayList<IReceiptSource>();
		}
		if (!rcptSrcList.stream().anyMatch(r -> r instanceof IPosTransaction)) {
			rcptSrcList.add(this._transactionScope.getTransaction());
		}
		// this._transactionScope.setValue(TransactionScopeKeys.CURRENT_RECEIPT_SOURCE,
		// rcptSrcList);
		IPosTransaction trans = this._transactionScope.getTransaction();
		List<ReceiptDocInfo> rcptDocListInfo = new ArrayList<ReceiptDocInfo>();
		for (final IReceiptSource rcptSrc : rcptSrcList) {
			rcptDocListInfo = this._rcptBuilder.getRcptCopyList(rcptSrc);
		}
		final ReceiptInfo rcptInfo = this.getScopedValue(ValueKeys.RECEIPT_INFO);
		rcptInfo.setRcptList(rcptDocListInfo);
		trans.setTransactionTypeCode("RAIN_CHECK");
		trans.setStringProperty("PRICE_QUOTATION", "True");
	}

	@Override
	protected String getPromptKeyPrintReceipt() {
		return "PRINT_RECEIPTS1";
	}

	@Override
	protected String getPromptKeyEmailOptPrintReceipt() {
		return "PRINT_RECEIPTS1";
	}

	@Override
	protected OpChainKey getPrintEmailReceiptsChainKey() {
		return OpChainKey.valueOf("PRINT_EMAIL_RECEIPTS1");
	}

	@Override
	protected OpChainKey getEmailReceiptsChainKey() {
		return OpChainKey.valueOf("EMAIL_RECEIPTS1");
	}

	@Override
	protected IOpResponse handleSaleComplete() {
		createReceiptsList();
		IParty customer = this._transactionScope.getTransaction(TransactionType.RETAIL_SALE).getCustomerParty();
		if ((anyEmailableReceipts() && this._sysConfig.getSendEmailReceipts().booleanValue() && this._sysConfig.getAlwaysPromptToEmail().booleanValue()) || customerWantsAnEmailReceipt(customer))
			return showPrintOrEmailReceiptsPrompt();
		if (anyNonPrintableReceipt())
			return handleShowPrompt(getPromptKeyNoPrintReceipt(), false);
		return saleCompleteWithNoPrompt();
	}
}
