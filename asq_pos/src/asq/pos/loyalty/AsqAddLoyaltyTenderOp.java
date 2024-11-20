/**
 * 
 */

package asq.pos.loyalty;

import java.math.BigDecimal;

import asq.pos.bnpl.tabby.tender.service.AsqSubmitBnplTabbyServiceResponse;
import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.neqaty.gen.NeqatyWSAPIRedeemOption;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.op.IReversibleOp;
import dtv.xst.dao.trn.IPosTransaction;
import dtv.xst.dao.ttr.ITenderLineItem;

import dtv.util.DateUtils;

/**
 * @author SA20547171 Common Tender class for Loyalty
 */
public class AsqAddLoyaltyTenderOp extends Operation implements IReversibleOp {

	@Override
	public IOpResponse handleOpExec(IXstEvent var1) {
		IPosTransaction tran = _transactionScope.getTransaction();
		ITenderLineItem tenderLine = getScopedValue(ValueKeys.CURRENT_TENDER_LINE);
		if(tenderLine.getTenderId().equalsIgnoreCase("NEQATI")) {
		NeqatyWSAPIRedeemOption reedem = getScopedValue(AsqValueKeys.ASQ_NEQATY_REDEEM_POINTS);
		tenderLine.setAmount(BigDecimal.valueOf(reedem.getRedeemAmount()));
		}
		else if(tenderLine.getTenderId().equalsIgnoreCase("STC") ) {
			BigDecimal pymntAmnt =	_transactionScope.getValue(AsqValueKeys.ASQ_STC_PNT_RDMPTN);
			tenderLine.setAmount(pymntAmnt);
		}
		else if(tenderLine.getTenderId().equalsIgnoreCase("TABBY") ) {
			String paymentAmount = _transactionScope.getValue(AsqValueKeys.ASQ_TABBY_PMNT_AMNT);
			Double pymntAmnt = new Double(paymentAmount); 
			tenderLine.setAmount(BigDecimal.valueOf(pymntAmnt));
		}
		else if(tenderLine.getTenderId().equalsIgnoreCase("TAMARA") ) {
			Double pymntAmnt = new Double(tran.getTotal().toString()); 
			tenderLine.setAmount(BigDecimal.valueOf(pymntAmnt));
		}
		else if(tenderLine.getTenderId().equalsIgnoreCase("MOKAFAT")) {
			tenderLine.setAmount(getScopedValue(AsqValueKeys.ASQ_MOKAFAA_AMOUNT));
			}
		tenderLine.setEndDateTimestamp(DateUtils.getNewDate());
		tran.addRetailTransactionLineItem(tenderLine);
		return this.HELPER.completeResponse();
	}

	@Override
	public IOpResponse handleOpReverse(IXstEvent var1) {
		ITenderLineItem tenderLine = getScopedValue(ValueKeys.CURRENT_TENDER_LINE);
		tenderLine.setVoid(true);
		return this.HELPER.completeResponse();
	}

}
