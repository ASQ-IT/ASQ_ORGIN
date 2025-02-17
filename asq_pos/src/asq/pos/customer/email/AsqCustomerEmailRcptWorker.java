package asq.pos.customer.email;

import dtv.hardware.rcptbuilding.EmailRcpt;
import dtv.i18n.OutputContextType;
import dtv.pos.customer.email.EmailRcptsWorker;
import dtv.xst.dao.trl.IRetailTransaction;

/**
 * @author RA20221457
 *
 */
public class AsqCustomerEmailRcptWorker extends EmailRcptsWorker {

	@Override
	protected String getMailSubject() {
		String subject = super.getMailSubject();
		String country = System.getProperty("dtv.location.RegionId");
		IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
		subject = ("ASQ ").concat(country).concat(" invoice number : ").concat(Long.toString(trans.getTransactionSequence()));
		if (subject.isEmpty()) {
			subject = Long.toString(trans.getTransactionSequence());
		}
		return this._ff.getSimpleFormattable(subject).toString(OutputContextType.DOCUMENT);
	}

	@Override
	protected String createPdfFileName(final EmailRcpt argRcpt) {
		final StringBuilder sb = new StringBuilder();
		IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
		sb.append(Long.toString(trans.getTransactionSequence()));
		sb.append(".pdf");
		return sb.toString();
	}
}
