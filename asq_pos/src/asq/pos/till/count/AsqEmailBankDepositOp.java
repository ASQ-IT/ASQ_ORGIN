/**
 * 
 */
package asq.pos.till.count;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.inject.Inject;
import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqConfigurationMgr;
import asq.pos.retrun.email.ASQEmailTemplateType;
import dtv.pos.common.LocationFactory;
import dtv.pos.common.TransactionType;
import dtv.pos.framework.email.DtvByteArrayDataSource;
import dtv.pos.framework.email.EmailTemplateHelper;
import dtv.pos.framework.email.IXstoreEmailJob;
import dtv.pos.framework.email.XstoreEmailManager;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.servicex.impl.ServiceCredentialProvider;
import dtv.xst.dao.loc.IRetailLocation;
import dtv.xst.dao.tsn.ITenderControlTransaction;
import dtv.xst.dao.tsn.ITenderTypeCount;

/**
 * @author SA20547171
 *
 */
public class AsqEmailBankDepositOp extends Operation {
	private static final Logger LOG = LogManager.getLogger(AsqEmailBankDepositOp.class);

	@Inject
	private XstoreEmailManager _emailManager;

	@Inject
	private ServiceCredentialProvider _credentialProvider;

	@Inject
	private AsqConfigurationMgr _sysConfig;

	@Inject
	private LocationFactory _locationFactory;

	@Override
	public IOpResponse handleOpExec(IXstEvent var1) {

		LOG.debug("Bank Deposit email Total");
		try {
			IXstoreEmailJob retrunJob = _emailManager.getFactory()
					.setServiceCredentials(_credentialProvider.getCredentials("SMTP")).createJob();

			DataSource body = new DtvByteArrayDataSource("body", getMailBody().getBytes(), "text/html");
			retrunJob.setBody(body);
			retrunJob.setSubject(getMailSubject());

			retrunJob.getOptions().setMailTos(_sysConfig.getAsqBankDpositEmail());
			_emailManager.sendEmailNow(retrunJob);
		} catch (MessagingException e) {
			LOG.debug("Exception in sending Bank Transaction email" + e);
		}
		return HELPER.completeResponse();

	}

	protected String getMailSubject() {
		return EmailTemplateHelper.getInstance().getSubjectLine();
	}

	protected String getMailBody() {
		return EmailTemplateHelper.getInstance().createMessageBody(getTemplateName(), getTemplateParams());
	}

	protected String getTemplateName() {
		return ASQEmailTemplateType.ASQ_DEPOSIT_EMAIL_RECEIPT.getName();
	}

	protected Map<String, Object> getTemplateParams() {
		Map<String, Object> newMap = new HashMap<>();

		ITenderControlTransaction transaction = _transactionScope.getTransaction(TransactionType.TENDER_CONTROL);

		IRetailLocation thisStore = _locationFactory.getStoreById(_stationState.getRetailLocationId());
		newMap.put("thisStoreId", thisStore.getRetailLocationId());
		newMap.put("thisStore", thisStore.getStoreName());
		if (transaction != null && transaction.getTransactionStatusCode().equalsIgnoreCase("COMPLETE")) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String deposictDate = formatter.format(transaction.getBusinessDate());
			newMap.put("transactionDate", deposictDate);
			newMap.put("transactionSeq", transaction.getTransactionSequence());

			List<ITenderTypeCount> tenderLists = transaction.getTenderTypeCounts();
			for (ITenderTypeCount tenderList : tenderLists) {
				if (tenderList.getTenderTypeCode().equalsIgnoreCase("CURRENCY")) {
					if (tenderList.getAmount().compareTo(BigDecimal.ZERO) == 0) {
						newMap.put("amountDue", "Entered ZERO");
					}
					else {
						newMap.put("amountDue", tenderList.getAmount());
					}
					break;
				}
			}

		}

		LOG.debug("Bank Depoist Transaction details for Email");

		return newMap;
	}

}
