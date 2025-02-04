/**
 * 
 */
package asq.pos.tender;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.activation.DataSource;
import javax.inject.Inject;
import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqConfigurationMgr;
import asq.pos.common.AsqValueKeys;
import asq.pos.retrun.email.ASQEmailTemplateType;
import dtv.pos.common.LocationFactory;
import dtv.pos.framework.email.DtvByteArrayDataSource;
import dtv.pos.framework.email.EmailTemplateHelper;
import dtv.pos.framework.email.IXstoreEmailJob;
import dtv.pos.framework.email.XstoreEmailManager;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.servicex.impl.ServiceCredentialProvider;
import dtv.xst.dao.loc.IRetailLocation;
import dtv.xst.dao.trn.IPosTransaction;

/**
 * @author SA20547171
 *
 */
public class AsqBankTransferEmailOp extends Operation {

	private static final Logger LOG = LogManager.getLogger(AsqBankTransferEmailOp.class);

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

		LOG.info("Bank Transfer Transaction approval email");
		try {
			//setScopedValue(AsqValueKeys.ASQ_RETURN_OFFLINE, false);
			IXstoreEmailJob retrunJob = _emailManager.getFactory()
					.setServiceCredentials(_credentialProvider.getCredentials("SMTP")).createJob();

			DataSource body = new DtvByteArrayDataSource("body", getMailBody().getBytes(), "text/html");
			retrunJob.setBody(body);
			retrunJob.setSubject(getMailSubject());

			retrunJob.getOptions().setMailTos(_sysConfig.getBankTransferApprovalEmailOTP());
			_emailManager.sendEmailNow(retrunJob);
		} catch (MessagingException e) {
			LOG.info("Exception in sending Return Transaction approval email" + e);
			//setScopedValue(AsqValueKeys.ASQ_RETURN_OFFLINE, true);
		}
		return HELPER.completeResponse();
	}

	protected String getTemplateName() {
		return ASQEmailTemplateType.ASQ_BANK_TRANS_EMAIL_RECEIPT.getName();
	}

	protected String getMailSubject() {
		return EmailTemplateHelper.getInstance().getSubjectLine();
	}

	protected String getMailBody() {
		return EmailTemplateHelper.getInstance().createMessageBody(getTemplateName(), getTemplateParams());
	}

	protected Map<String, Object> getTemplateParams() {
		Map<String, Object> newMap = new HashMap<>();

		IPosTransaction trans = _transactionScope.getTransaction();

		IRetailLocation thisStore = _locationFactory.getStoreById(_stationState.getRetailLocationId());
		newMap.put("thisStore", thisStore.getStoreName());
		newMap.put("thisStoreAdress", thisStore.getAddress1());
		newMap.put("transactionSeq", trans.getTransactionSequence());
		newMap.put("amountDue", trans.getAmountDue());

		setScopedValue(AsqValueKeys.ASQ_BANK_OTP, returnOtp());
		newMap.put("otp", getScopedValue(AsqValueKeys.ASQ_BANK_OTP));
		LOG.info("Bank Transfer Transaction details for Email otp:");

		return newMap;
	}

	private int returnOtp() {
		SecureRandom randOtp = new SecureRandom();
		return randOtp.nextInt(10000) + 999;
	}

//	DigestRandomGenerator gen=new 	DigestRandomGenerator(null);

}
