package asq.pos.zatca.invoice.generation.op;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oracle.shaded.fasterxml.jackson.core.JsonProcessingException;
import com.oracle.shaded.fasterxml.jackson.databind.ObjectMapper;

import asq.pos.zatca.cert.generation.AsqZatcaHelper;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceRequest;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceResponse;
import asq.pos.zatca.cert.generation.service.IAsqZatcaCertRegistrationServices;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;

/**
 * @author RA20221457
 *
 */
public class AsqZatcaInvoiceGenerationOp extends Operation {

	private static final Logger logger = LogManager.getLogger(AsqZatcaInvoiceGenerationOp.class);

	@Inject
	private AsqZatcaInvoiceGenerationHelper asqZatcaInvoiceGenerationHelper;

	@Inject
	private AsqZatcaHelper asqZatcaHelper;

	@Inject
	protected Provider<IAsqZatcaCertRegistrationServices> zatcaService;

	@Override
	public IOpResponse handleOpExec(IXstEvent paramIXstEvent) {
		try {
			// Reading XML File ASQZatcaRegSimpleInvoice.xml
			File sampleInvoiceDir = new File(System.getProperty("asq.zatca.certificate.work.dir.invoice"));
			File[] listOfFiles = sampleInvoiceDir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if (name.endsWith(".xml")) {
						return true;
					}
					return false;
				}
			});
			if (listOfFiles.length > 0) {
				for (File sampleInvoice : listOfFiles) {
					String invoiceXmlString = Files.readString(Path.of(sampleInvoice.getPath()));
					InvoiceType invoice = asqZatcaInvoiceGenerationHelper.getInvoiceOject(sampleInvoice);
					// starting the process of updating the invoice object
					logger.info("******Calling GenerateInvoice method******");
					if (sampleInvoice != null) {
						removeExistingSubmiitedInvoiceFile();
						asqZatcaInvoiceGenerationHelper.generateSampleRegInvoice(invoice, invoiceXmlString);
					}
				}
				// submitting generated XML for approval
				submittGenZatcaInvoices();
			} else {
				logger.debug("Their are no file to read in sample Invoices directory");
			}
		} catch (Exception exception) {
			logger.error("We have recieved  :" + exception);
		}
		return HELPER.completeResponse();
	}

	public void removeExistingSubmiitedInvoiceFile() {
		File signedInvoiceFolder = new File(System.getProperty("asq.pos.invoice.outboundFolder"));
		File[] listOfFiles = signedInvoiceFolder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith(".json")) {
					return true;
				}
				return false;
			}
		});
		if (listOfFiles.length > 0) {
			for (File sampleSignedInvoice : listOfFiles) {
				sampleSignedInvoice.delete();
			}
		}
	}

	public AsqSubmitZatcaCertServiceResponse submittGenZatcaInvoices() throws IOException {
		AsqSubmitZatcaCertServiceResponse response = null;
		File signedInvoiceFolder = new File(System.getProperty("asq.pos.invoice.outboundFolder"));

		File[] listOfFiles = signedInvoiceFolder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith(".json")) {
					return true;
				}
				return false;
			}
		});
		System.out.println();
		if (listOfFiles.length > 0) {
			response = new AsqSubmitZatcaCertServiceResponse();
			for (File sampleSignedInvoice : listOfFiles) {
				String requestXmlString = Files.readString(Path.of(sampleSignedInvoice.getPath()));
				AsqSubmitZatcaCertServiceRequest asqSubmitZatcaCertServiceRequest = zatcaInvoiceRequestMapper(requestXmlString);
				response = (AsqSubmitZatcaCertServiceResponse) zatcaService.get().submitZatcaInvoiceForRegistration(asqSubmitZatcaCertServiceRequest);
			}
		}
		return response;
	}

	private static AsqSubmitZatcaCertServiceRequest zatcaInvoiceRequestMapper(String zatcaRequest) {
		ObjectMapper mapper = new ObjectMapper();
		AsqSubmitZatcaCertServiceRequest asqSubmitZatcaCertServiceRequest = null;
		try {
			asqSubmitZatcaCertServiceRequest = mapper.readValue(zatcaRequest, AsqSubmitZatcaCertServiceRequest.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return asqSubmitZatcaCertServiceRequest;
	}
}
