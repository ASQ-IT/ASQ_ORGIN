package asq.pos.zatca.invoice.submition.worker;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.zatca.AsqZatcaConstant;
import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceResponse;
import asq.pos.zatca.database.helper.AsqZatcaDatabaseHelper;
import asq.pos.zatca.database.helper.AsqZatcaInvoicesQueryResult;
import asq.pos.zatca.invoice.submition.worker.service.AsqSubmitZatcaInvoiceToOicServiceRequest;
import asq.pos.zatca.invoice.submition.worker.service.IAsqZatcaInvoiceSubmittToOICService;
import dtv.pos.framework.worker.AbstractWorker;

public class AsqSubmittZatcaInvoiceToOICWorker extends AbstractWorker {

	private static final Logger LOG = LogManager.getLogger(AsqSubmittZatcaInvoiceToOICWorker.class);

	@Inject
	AsqZatcaDatabaseHelper zatcaDatabaseHelper;

	@Inject
	protected Provider<IAsqZatcaInvoiceSubmittToOICService> zatcaOicService;

	@Inject
	AsqZatcaHelper asqZatcaHelper;

	@Override
	protected void performWorkImpl() throws Exception {
		LOG.debug("Started working on submitting the Zatca Invoices to OIC service ");
		try {
			List<AsqZatcaInvoicesQueryResult> results = zatcaDatabaseHelper.getNewZatcaInvoices();
			if (null != results && results.size() > 0) {
				for (AsqZatcaInvoicesQueryResult invoice : results) {
					AsqSubmitZatcaInvoiceToOicServiceRequest oicZatcaRequest = new AsqSubmitZatcaInvoiceToOicServiceRequest();

					oicZatcaRequest.setCreationDate(String.valueOf(invoice.getCREATE_DATE()));
					oicZatcaRequest.setBusinessDate(String.valueOf(invoice.getBUSINESS_DATE()));
					oicZatcaRequest.setInvoice(new String(invoice.getINVOICE_XML(), StandardCharsets.UTF_8));
					oicZatcaRequest.setInvoiceHash(invoice.getINVOICE_HASH());
					oicZatcaRequest.setUuid(invoice.getINVOICE_UUID());
					oicZatcaRequest.setTillId(invoice.getWKSTN_ID());
					oicZatcaRequest.setTransactionNo(invoice.getINVOICE_ID());

					Properties zatcaProp = asqZatcaHelper.getCSIDProperties();
					oicZatcaRequest.setPassword(zatcaProp.getProperty(AsqZatcaConstant.ASQ_ZATCA_TOKEN_KEY));
					oicZatcaRequest.setUsername(zatcaProp.getProperty(AsqZatcaConstant.ASQ_ZATCA_SECRET_KEY));

					oicZatcaRequest.setVatRegNo(System.getProperty("asq.zatca.company.vat.reg.number"));
					oicZatcaRequest.setStoreNumber(System.getProperty("dtv.location.storeNumber"));

					AsqSubmitZatcaCertServiceResponse response = (AsqSubmitZatcaCertServiceResponse) zatcaOicService.get().submitInvoiceToOIC(oicZatcaRequest);
					if (response != null && null != response.getErrors()) {
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
