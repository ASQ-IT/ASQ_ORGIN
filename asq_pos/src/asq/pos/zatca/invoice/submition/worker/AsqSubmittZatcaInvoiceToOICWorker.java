package asq.pos.zatca.invoice.submition.worker;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import asq.pos.zatca.database.helper.AsqZatcaDatabaseHelper;
import asq.pos.zatca.database.helper.AsqZatcaInvoicesQueryResult;
import asq.pos.zatca.invoice.submition.worker.service.AsqSubmitZatcaInvoiceToOicServiceRequest;
import dtv.pos.common.LocationFactory;
import dtv.pos.framework.worker.WorkerAdapter;
import dtv.xst.dao.loc.IRetailLocation;

public class AsqSubmittZatcaInvoiceToOICWorker extends WorkerAdapter {

	private static final Logger LOG = LogManager.getLogger(AsqSubmittZatcaInvoiceToOICWorker.class);

	@Inject
	AsqZatcaDatabaseHelper zatcaDatabaseHelper;

	@Inject
	AsqZatcaHelper zatcaHelper;

	@Inject
	private LocationFactory locFactory;

	@Override
	protected void performWorkImpl() throws Exception {
		LOG.debug("Started working on submitting the Zatca Invoices to OIC service ");
		List<AsqZatcaInvoicesQueryResult> results = zatcaDatabaseHelper.getNewZatcaInvoices();
		if (null != results && results.size() > 0) {
			for (AsqZatcaInvoicesQueryResult invoice : results) {
				AsqSubmitZatcaInvoiceToOicServiceRequest oicZatcaRequest = new AsqSubmitZatcaInvoiceToOicServiceRequest();

				oicZatcaRequest.setCreationDate(invoice.getCREATE_DATE());
				oicZatcaRequest.setBusinessDate(invoice.getBUSINESS_DATE());
				oicZatcaRequest.setInvoice(invoice.getJSON_INVOICE().toString());
				oicZatcaRequest.setInvoiceHash(invoice.getINVOICE_HASH().toString());
				oicZatcaRequest.setUuid(invoice.getINVOICE_UUID());
				oicZatcaRequest.setTillId(invoice.getWKSTN_ID());
				oicZatcaRequest.setTransactionNo(invoice.getINVOICE_ID());

				oicZatcaRequest.setPassword(System.getProperty("asq.zatca.uri.binarySecurityToken"));
				oicZatcaRequest.setUsername(System.getProperty("asq.zatca.uri.secret"));

				IRetailLocation loc = locFactory.getStoreById(Long.valueOf(System.getProperty("dtv.location.storeNumber")));
				oicZatcaRequest.setVatRegNo(String.valueOf(loc.getStringProperty("ASQ_VAT_NUMBER")));
				oicZatcaRequest.setStoreNumber(loc.getStoreNbr());

				zatcaHelper.convertTojson(oicZatcaRequest);

				zatcaDatabaseHelper.updateZatcaInvoiceStatus(invoice);

			}
		}
	}

}
