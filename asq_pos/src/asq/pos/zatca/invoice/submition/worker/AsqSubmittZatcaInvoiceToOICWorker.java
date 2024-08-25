package asq.pos.zatca.invoice.submition.worker;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.zatca.database.helper.AsqZatcaDatabaseHelper;
import asq.pos.zatca.database.helper.AsqZatcaInvoicesQueryResult;
import asq.pos.zatca.invoice.submition.worker.service.AsqSubmitZatcaInvoiceToOicServiceRequest;
import asq.pos.zatca.invoice.submition.worker.service.IAsqZatcaInvoiceSubmittToOICService;
import dtv.pos.common.LocationFactory;
import dtv.pos.framework.worker.AbstractWorker;
import dtv.service.req.IServiceResponse;
import dtv.xst.dao.loc.IRetailLocation;

public class AsqSubmittZatcaInvoiceToOICWorker extends AbstractWorker {

	private static final Logger LOG = LogManager.getLogger(AsqSubmittZatcaInvoiceToOICWorker.class);

	@Inject
	AsqZatcaDatabaseHelper zatcaDatabaseHelper;

	@Inject
	protected Provider<IAsqZatcaInvoiceSubmittToOICService> zatcaOicService;

	@Inject
	private LocationFactory locFactory;

	@Override
	protected void performWorkImpl() throws Exception {
		LOG.debug("Started working on submitting the Zatca Invoices to OIC service ");
		try {
			List<AsqZatcaInvoicesQueryResult> results = zatcaDatabaseHelper.getNewZatcaInvoices();
			if (null != results && results.size() > 0) {
				for (AsqZatcaInvoicesQueryResult invoice : results) {
					AsqSubmitZatcaInvoiceToOicServiceRequest oicZatcaRequest = new AsqSubmitZatcaInvoiceToOicServiceRequest();

					oicZatcaRequest.setCreationDate(invoice.getCREATE_DATE().toString());
					oicZatcaRequest.setBusinessDate(invoice.getBUSINESS_DATE().toString());
					oicZatcaRequest.setInvoice(new String(invoice.getINVOICE_XML(), StandardCharsets.UTF_16));
					oicZatcaRequest.setInvoiceHash(invoice.getINVOICE_HASH());
					oicZatcaRequest.setUuid(invoice.getINVOICE_UUID());
					oicZatcaRequest.setTillId(invoice.getWKSTN_ID());
					oicZatcaRequest.setTransactionNo(invoice.getINVOICE_ID());

					oicZatcaRequest.setPassword(System.getProperty("asq.zatca.uri.binarySecurityToken"));
					oicZatcaRequest.setUsername(System.getProperty("asq.zatca.uri.secret"));

					IRetailLocation loc = locFactory.getStoreById(Long.valueOf(System.getProperty("dtv.location.storeNumber")));
					oicZatcaRequest.setVatRegNo(String.valueOf(loc.getStringProperty("ASQ_VAT_NUMBER")));
					oicZatcaRequest.setStoreNumber(loc.getStoreNbr());

					IServiceResponse response = zatcaOicService.get().submitInvoiceToOIC(oicZatcaRequest);
					if (response != null) {
						LOG.debug("Successfully submitted the Zatca Invoices to OIC server service with invoice Id : " + oicZatcaRequest.getUuid());
						zatcaDatabaseHelper.updateZatcaInvoiceStatus(invoice);
						LOG.debug("Successfully updated the Zatca Invoices status in the staging Table");
					}
				}
			}
		} catch (Exception workerExc) {
			LOG.error("We have recieved exception in Store to OIC zatca invoice submition", workerExc);
		}
		LOG.debug("Ended working on submitting the Zatca Invoices to OIC service ");
	}

}
