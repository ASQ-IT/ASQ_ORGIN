/**
 * 
 */
package asq.pos.inventory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.activation.DataSource;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraAmountObj;
import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraItemObj;
import asq.pos.common.AsqConfigurationMgr;
import asq.pos.common.AsqValueKeys;
import asq.pos.inventory.AsqBinTransferEmailTemplateType;
import asq.pos.zatca.AsqZatcaConstant;
import dtv.pos.common.LocationFactory;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.email.DtvByteArrayDataSource;
import dtv.pos.framework.email.EmailTemplateHelper;
import dtv.pos.framework.email.IXstoreEmailJob;
import dtv.pos.framework.email.XstoreEmailManager;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.inventory.lookup.InventoryLocationBucketSelectionModel;
import dtv.servicex.impl.ServiceCredentialProvider;
import dtv.xst.dao.inv.IInventoryDocument;
import dtv.xst.dao.inv.IInventoryDocumentLineItem;
import dtv.xst.dao.loc.IRetailLocation;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import dtv.xst.daocommon.IInventoriedLineItem;
import dtv.xst.daocommon.IInventoryLocationModifier;

/**
 * @author RA20221457
 *
 */
public class AsqBinTransferEmailFormOp extends Operation {
	private static final Logger LOG = LogManager.getLogger(AsqBinTransferEmailFormOp.class);

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

		LOG.info("Return Transaction approval email");
		try {
			setScopedValue(AsqValueKeys.ASQ_RETURN_OFFLINE, false);
			IXstoreEmailJob retrunJob = _emailManager.getFactory()
					.setServiceCredentials(_credentialProvider.getCredentials("SMTP")).createJob();
			DataSource body = new DtvByteArrayDataSource("body", getMailBody().getBytes(), "text/html");
			retrunJob.setBody(body);
			retrunJob.setSubject(getMailSubject());

			retrunJob.getOptions().setMailTos(_sysConfig.getBinTransferApprovalEmailOTP());
			_emailManager.sendEmailNow(retrunJob);
		} catch (MessagingException e) {
			System.out.println("Showing Email Exception");
			LOG.info("Bin Transfer approval email" + e);
			setScopedValue(AsqValueKeys.ASQ_RETURN_OFFLINE, true);
		}
		return HELPER.completeResponse();
	}

	protected String getTemplateName() {
		return AsqBinTransferEmailTemplateType.ASQ_BIN_TRANSFER_EMAIL_RECEIPT.getName();
	}

	protected String getMailSubject() {
		return EmailTemplateHelper.getInstance().getSubjectLine();
	}

	protected String getMailBody() {
		return EmailTemplateHelper.getInstance().createMessageBody(getTemplateName(), getTemplateParams());
	}

	protected Map<String, Object> getTemplateParams() {
		Map<String, Object> newMap = new HashMap<String, Object>();
		IRetailLocation thisStore = _locationFactory.getStoreById(_stationState.getRetailLocationId());
		IInventoryDocument doc = (IInventoryDocument) getScopedValue(ValueKeys.CURRENT_INV_DOC);
		String emailDisplay = "<table border=\"1\"><tr><th>Sl No</th><th>ItemID</th><th>Item Qty</th></tr>";
		int itemCount = 0;
		for (IInventoryDocumentLineItem lineItem : doc.getInventoryDocumentLineItems()) {
			newMap.put("thisStore", thisStore.getStoreName());
			newMap.put("storeID", thisStore.getStoreNbr());
			newMap.put("documentID", lineItem.getDocumentId());
			for (IInventoryLocationModifier lctnModfr : lineItem.getAllInventoryLocationModifiers()) {
				itemCount = itemCount + 1;
				emailDisplay = emailDisplay + "<tr><td>" + itemCount + "</td><td>" + lctnModfr.getItemId() + "</td>"
						+ "<td>" + lctnModfr.getQuantity() + "</td></tr>";
			}
		}
		emailDisplay = emailDisplay + "</table>";
		newMap.put("itemDetails", emailDisplay);
		setScopedValue(AsqValueKeys.ASQ_BIN_TRANSFER_OTP, binTransferOtp());
		newMap.put("otp", getScopedValue(AsqValueKeys.ASQ_BIN_TRANSFER_OTP));
		LOG.info("Bin Transfer item details for Email otp:" + getScopedValue(AsqValueKeys.ASQ_BIN_TRANSFER_OTP));
		return newMap;
	}

	private int binTransferOtp() {
		SecureRandom randOtp = new SecureRandom();
		return randOtp.nextInt(10000);
	}
}
