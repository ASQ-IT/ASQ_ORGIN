/**
 * 
 */
package asq.pos.zatca.database.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dtv.asq.dao.zatca.AsqZatcaInvoiceHashId;
import dtv.asq.dao.zatca.IAsqZatcaInvoiceHash;
import dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashModel;
import dtv.data2.access.DataFactory;
import dtv.data2.access.IQueryKey;
import dtv.data2.access.QueryKey;
import dtv.data2.access.impl.DaoState;
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
	public AsqZatcaInvoiceHashModel saveInvoiceHashFromDB(String xmlIrnValue, String xmlUUID, String hashedXML, String invoiceIssueDate) {
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
}
