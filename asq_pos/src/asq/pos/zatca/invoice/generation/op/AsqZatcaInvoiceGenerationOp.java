package asq.pos.zatca.invoice.generation.op;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oracle.shaded.fasterxml.jackson.core.JsonProcessingException;
import com.oracle.shaded.fasterxml.jackson.databind.JsonMappingException;
import com.oracle.shaded.fasterxml.jackson.databind.ObjectMapper;

import asq.pos.zatca.cert.generation.AsqZatcaErrorDesc;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceRequest;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceResponse;
import asq.pos.zatca.cert.generation.service.IAsqZatcaCertRegistrationServices;
import dtv.i18n.IFormattable;
import dtv.pos.framework.action.XstDataAction;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;

public class AsqZatcaInvoiceGenerationOp extends Operation {

	private static final Logger logger = LogManager.getLogger(AsqZatcaInvoiceGenerationOp.class);

	@Inject
	private AsqZatcaInvoiceGenerationHelper asqZatcaInvoiceGenerationHelper;

	@Inject
	protected Provider<IAsqZatcaCertRegistrationServices> zatcaService;

	@Override
	public boolean isOperationApplicable() {
		return !isComplete();
	}

	@Override
	public IOpResponse handleOpExec(IXstEvent paramIXstEvent) {
		try {
			if (null != paramIXstEvent && paramIXstEvent instanceof XstDataAction) {
				XstDataAction ata = (XstDataAction) paramIXstEvent;
				if ("CERT_INVOICE".equalsIgnoreCase(ata.getActionKey().toString())) {
					return HELPER.completeCurrentChainResponse();
				}
			}
			logger.debug(" Reading Sample XML File ");
			File[] listOfFiles = asqZatcaInvoiceGenerationHelper.getFilesFromLocation(System.getProperty("asq.zatca.certificate.work.dir.invoice"), ".xml");
			if (listOfFiles.length > 0) {
				Arrays.sort(listOfFiles, NameFileComparator.NAME_COMPARATOR);
				removeExistingSubmiitedInvoiceFile();
				for (File sampleInvoice : listOfFiles) {
					logger.debug("Reading the Sample Invoice Name : " + sampleInvoice.getName());
					String invoiceXmlString = Files.readString(Path.of(sampleInvoice.getPath()));
					InvoiceType invoice = asqZatcaInvoiceGenerationHelper.getInvoiceOject(sampleInvoice);
					logger.debug("Suceessfully Comnverted Sample Invoice Name : " + sampleInvoice.getName() + "to Java Object");
					// starting the process of updating the invoice object
					if (sampleInvoice != null) {
						logger.debug("Submiited the invoice object for creating Zatca required invoice JSON request ");
						AsqSubmitZatcaCertServiceResponse result = asqZatcaInvoiceGenerationHelper.generateSampleRegInvoice(invoice, invoiceXmlString);
						if (result.getErrors() == null) {
							logger.debug("WE are able to create the Zatca required invoice JSON request from smaple Invoice");
						} else {
							return handleServiceError(result);
						}
					}
				}
				logger.debug("Successfully created the invoice json for three Zataca request");

				// submitting generated XML for approval
				submittGenZatcaInvoices();
			} else {
				logger.error("Their are no file to read in sample Invoices directory");
			}
		} catch (Exception exception) {
			logger.error("We have recieved in submiiting the invoices :" + exception);
			IFormattable[] args = new IFormattable[2];
			args[0] = _formattables.getSimpleFormattable("System Error");
			args[1] = _formattables.getSimpleFormattable(exception.getLocalizedMessage());
			return HELPER.getPromptResponse("ASQ_ZATCA_REGISTOR_ERROR", args);

		}
		return HELPER.getCompletePromptResponse("ASQ_ZATCA_INVOICE_NOTIFIY");
	}

	public IOpResponse handleServiceError(AsqSubmitZatcaCertServiceResponse asqServiceResponse) throws JsonMappingException, JsonProcessingException {
		IFormattable[] args = new IFormattable[2];
		if (null == asqServiceResponse) {
			args[1] = _formattables.getSimpleFormattable("Service has null response");
		} else {
			AsqZatcaErrorDesc error = asqServiceResponse.getErrors();
			args[0] = _formattables.getSimpleFormattable(error.getCode());
			args[1] = _formattables.getSimpleFormattable(error.getMessage());
		}
		return HELPER.getPromptResponse("ASQ_ZATCA_REGISTOR_ERROR", args);
	}

	/**
	 * Removes the old pre generated file from location
	 */
	public void removeExistingSubmiitedInvoiceFile() {
		File[] listOfFiles = asqZatcaInvoiceGenerationHelper.getFilesFromLocation(System.getProperty("asq.pos.invoice.outboundFolder"), ".json");
		if (listOfFiles.length > 0) {
			for (File sampleSignedInvoice : listOfFiles) {
				sampleSignedInvoice.delete();
			}
		}
	}

	/**
	 * This method reads the generated Zatca request and submit them for approval in
	 * compliance invoice service
	 * 
	 * @return
	 * @throws IOException
	 */
	public IOpResponse submittGenZatcaInvoices() throws IOException {
		logger.debug("Started the process of submitting three Zataca request");
		AsqSubmitZatcaCertServiceResponse response = null;
		logger.debug("Reading the file from the loacation");
		File[] listOfFiles = asqZatcaInvoiceGenerationHelper.getFilesFromLocation(System.getProperty("asq.pos.invoice.outboundFolder"), ".json");
		if (listOfFiles.length > 0) {
			response = new AsqSubmitZatcaCertServiceResponse();
			for (File sampleSignedInvoice : listOfFiles) {
				logger.debug("Submitting Zatca request with file Name: " + sampleSignedInvoice.getName());
				String requestXmlString = Files.readString(Path.of(sampleSignedInvoice.getPath()));
				AsqSubmitZatcaCertServiceRequest asqSubmitZatcaCertServiceRequest = zatcaInvoiceRequestMapper(requestXmlString);
				response = (AsqSubmitZatcaCertServiceResponse) zatcaService.get().submitZatcaInvoiceForRegistration(asqSubmitZatcaCertServiceRequest);
				if ("PASS".equalsIgnoreCase(response.getValidationResults().getStatus())) {
					logger.debug("File Name: " + sampleSignedInvoice.getName() + "has been submitted sucessfully on Zatca ");
				} else {
					return handleServiceError(response);
				}
			}
		}
		return HELPER.completeResponse();
	}

	/**
	 * This method reads the generated XML and return the
	 * AsqSubmitZatcaCertServiceRequest object with values mapped
	 * 
	 * @param zatcaRequest
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	private AsqSubmitZatcaCertServiceRequest zatcaInvoiceRequestMapper(String zatcaRequest) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(zatcaRequest, AsqSubmitZatcaCertServiceRequest.class);
	}
}
