/**
 * 
 */
package asq.pos.inventory.ship.op;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.activation.DataSource;
import javax.inject.Inject;
import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.common.AsqConfigurationMgr;
import asq.pos.common.AsqValueKeys;
import asq.pos.inventory.AsqBinTransferEmailFormOp;
import asq.pos.inventory.AsqBinTransferEmailTemplateType;
import asq.pos.inventory.ship.op.AsqStoreTransferEmailFormOp.OrderItem;
import asq.pos.retrun.email.ASQEmailTemplateType;
import dtv.pos.common.LocationFactory;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.email.DtvByteArrayDataSource;
import dtv.pos.framework.email.EmailTemplateHelper;
import dtv.pos.framework.email.IXstoreEmailJob;
import dtv.pos.framework.email.XstoreEmailManager;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import dtv.servicex.impl.ServiceCredentialProvider;
import dtv.xst.dao.inv.IInventoryDocument;
import dtv.xst.dao.inv.IInventoryDocumentLineItem;
import dtv.xst.dao.inv.IShipment;
import dtv.xst.dao.loc.IRetailLocation;
import dtv.xst.daocommon.IInventoryLocationModifier;

/**
 * @author SA20547171
 *
 */
public class AsqStoreTransferEmailFormOp extends Operation {

	private static final Logger LOG = LogManager.getLogger(AsqStoreTransferEmailFormOp.class);

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

			retrunJob.getOptions().setMailTos(_sysConfig.getstoreTransferApprovalEmailOTP());
			_emailManager.sendEmailNow(retrunJob);
		} catch (MessagingException e) {
			System.out.println("Showing Email Exception");
			LOG.info("Bin Transfer approval email" + e);
			setScopedValue(AsqValueKeys.ASQ_RETURN_OFFLINE, true);
		}
		return HELPER.completeResponse();
	}

	protected String getTemplateName() {
		return ASQEmailTemplateType.ASQ_SHIP_EMAIL_RECEIPT.getName();
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
		IInventoryDocument doc = getScopedValue(ValueKeys.CURRENT_INV_DOC);
		newMap.put("thisStore", thisStore.getStoreName());
		newMap.put("documentID", doc.getDocumentId());
		newMap.put("documentType", doc.getDocumentSubtypeCode());

		for (IShipment shipment : doc.getShipments()) {
			shipment.getDestinationName();
			newMap.put("destStore", shipment.getDestinationRetailLocation().getStoreName());
		}

		List<IInventoryDocumentLineItem> lineItems = doc.getInventoryDocumentLineItems();

		List<AsqStoreTransferEmailFormOp.OrderItem> lineItem = lineItems.stream()
				.map(itm -> new AsqStoreTransferEmailFormOp.OrderItem(itm.getInventoryItemId(),
						itm.getTempPostedCount(), itm.getInventoryItem().getDescription()))
				.collect(Collectors.toList());

		newMap.put("orderItems", lineItem);
		setScopedValue(AsqValueKeys.ASQ_STORE_TRANSFER_OTP, storeTransferOtp());
		newMap.put("otp", getScopedValue(AsqValueKeys.ASQ_STORE_TRANSFER_OTP));
		LOG.info("Store Transfer item details for Email otp:" + getScopedValue(AsqValueKeys.ASQ_BIN_TRANSFER_OTP));
		return newMap;
	}

	private int storeTransferOtp() {
		SecureRandom randOtp = new SecureRandom();
		return randOtp.nextInt(10000);
	}

	public class OrderItem {
		private final String itemId;
		private final BigDecimal quantity;
		private final String description;

		public OrderItem(String argItemId, BigDecimal argQuantity, String argDescription) {
			itemId = argItemId;
			quantity = argQuantity;
			description = argDescription;
		}

		public String getItemId() {
			return itemId;
		}

		public BigDecimal getQuantity() {
			return quantity;
		}

		public String getDescription() {
			return description;
		}

	}

}
