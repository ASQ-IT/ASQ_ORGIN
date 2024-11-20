package asq.pos.zatca.database.helper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import asq.pos.zatca.AsqZatcaConstant;
import dtv.asq.dao.zatca.AsqZatcaInvoiceHashId;
import dtv.asq.dao.zatca.IAsqZatcaInvoiceHash;
import dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashModel;
import dtv.asq.dao.zatca.invoice.staging.AsqZatcaInvoiceStagingId;
import dtv.asq.dao.zatca.invoice.staging.IAsqZatcaInvoiceStaging;
import dtv.asq.dao.zatca.invoice.staging.impl.AsqZatcaInvoiceStagingModel;
import dtv.data2.access.DataFactory;
import dtv.data2.access.IQueryKey;
import dtv.data2.access.QueryKey;
import dtv.data2.access.impl.DaoState;
import dtv.data2.access.query.QueryRequest;
import dtv.pos.common.ConfigurationMgr;
import dtv.util.DateUtils;
import dtv.util.sequence.SequenceFactory;

/**
 * @author RA20221457
 *
 */
public class AsqZatcaDatabaseHelper {

	public static final Logger _logger = Logger.getLogger(AsqZatcaDatabaseHelper.class);

	private static final IQueryKey<AsqZatcaInvoiceHashQueryResult> ASQ_SELECT_ZATCA_INVOICE_HASH = new QueryKey<>("ASQ_SELECT_ZATCA_INVOICE_HASH", AsqZatcaInvoiceHashQueryResult.class);

	private static final IQueryKey<AsqZatcaInvoicesQueryResult> ASQ_ZATCA_INVOICES = new QueryKey<>("ASQ_ZATCA_INVOICES", AsqZatcaInvoicesQueryResult.class);

	public List<AsqZatcaInvoiceHashQueryResult> getPrevInvoiceHashFromDB() {
		Map<String, Object> invoiceHashParams = new HashMap<String, Object>();
		invoiceHashParams.put("argOrganizationId", ConfigurationMgr.getOrganizationId());
		return DataFactory.getObjectByQueryNoThrow(ASQ_SELECT_ZATCA_INVOICE_HASH, invoiceHashParams);
	}

	/**
	 * This method saves the Zatca Invoice hash value into the database
	 * 
	 * @param xmlIrnValue
	 * @param xmlUUID
	 * @param hashedXML
	 * @return
	 */
	public AsqZatcaInvoiceHashModel saveInvoiceHash(String xmlIrnValue, String xmlUUID, String hashedXML, String invoiceIssueDate) {
		AsqZatcaInvoiceHashId invoiceHashId = new AsqZatcaInvoiceHashId();
		invoiceHashId.setIcv(SequenceFactory.getNextLongValue("ASQ_ZATCA_SEQ"));
		invoiceHashId.setOrganizationId(ConfigurationMgr.getOrganizationId());

		AsqZatcaInvoiceHashModel invoiceHash = (AsqZatcaInvoiceHashModel) DataFactory.createObject(invoiceHashId, IAsqZatcaInvoiceHash.class);
		invoiceHash.setInvoiceHash(hashedXML);
		invoiceHash.setInvoiceId(xmlIrnValue);
		invoiceHash.setInvoiceDate(DateUtils.parseDate(invoiceIssueDate));
		invoiceHash.getDAO().setObjectState(DaoState.INSERT_OR_UPDATE.intVal());
		return DataFactory.makePersistent(invoiceHash);
	}

	public AsqZatcaInvoiceStagingModel saveInvoiceInStaging(String argInvoiceID, Date argBusinessDate, Long argWkStnID, Long argTranSeq, String argJsonInvoice, String argInvoiceQRCode,
			String argXmlUUID, String argInvoiceHashCode, Long argnextICV) {
		AsqZatcaInvoiceStagingId invoiceJsonId = new AsqZatcaInvoiceStagingId();
		invoiceJsonId.setInvoiceId(argInvoiceID);
		invoiceJsonId.setOrganizationId(ConfigurationMgr.getOrganizationId());
		invoiceJsonId.setBusinessDate(argBusinessDate);
		invoiceJsonId.setWorkStationId(argWkStnID);
		invoiceJsonId.setTransactionSeq(argTranSeq);
		invoiceJsonId.setIcv(argnextICV);

		AsqZatcaInvoiceStagingModel invoice = (AsqZatcaInvoiceStagingModel) DataFactory.createObject(invoiceJsonId, IAsqZatcaInvoiceStaging.class);
		invoice.setSubmittedJSON(argJsonInvoice.getBytes());
		invoice.setInvoiceQrCode(argInvoiceQRCode.getBytes());
		invoice.setStatus(AsqZatcaConstant.ZATCA_STATUS_NEW);
		invoice.setInvoiceUUID(argXmlUUID);
		invoice.setInvoiceHashCode(argInvoiceHashCode);
		invoice.setInvoiceDate(new Date());
		invoice.getDAO().setObjectState(DaoState.INSERT_OR_UPDATE.intVal());
		return DataFactory.makePersistent(invoice);
	}

	public List<AsqZatcaInvoicesQueryResult> getNewZatcaInvoices() {
		Map<String, Object> zatcaInvoices = new HashMap<String, Object>();
		return DataFactory.getObjectByQueryNoThrow(ASQ_ZATCA_INVOICES, zatcaInvoices);
	}

	public boolean updateZatcaInvoiceStatus(AsqZatcaInvoicesQueryResult invoice, String argStatus) {
		Map<String, Object> zatcaInvoices = new HashMap<String, Object>();
		zatcaInvoices.put("argZatcaStatus", argStatus);
		zatcaInvoices.put("argOrganizationId", invoice.getORGANIZATION_ID());
		zatcaInvoices.put("argOrgInvoiceid", invoice.getINVOICE_ID());
		zatcaInvoices.put("argOrgbusinessDate", invoice.getBUSINESS_DATE());
		zatcaInvoices.put("argOrgtransSeq", invoice.getTRANS_SEQ());
		zatcaInvoices.put("argOrgWkstnId", invoice.getWKSTN_ID());
		QueryRequest query = new QueryRequest("ASQ_ZATCA_INVOICES_UPDATE", zatcaInvoices);
		query = DataFactory.makePersistent(query);
		return true;
	}

}
