package asq.pos.zatca.invoice.generation.op;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;

import javax.inject.Inject;

import asq.pos.zatca.invoice.generation.utils.ASQException;
import dtv.pos.framework.op.Operation;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;

/**
 * @author RA20221457
 *
 */
public class AsqZatcaInvoiceGenerationOp extends Operation {

	@Inject
	private AsqZatcaInvoiceGenerationHelper asqZatcaInvoiceGenerationHelper;

	@Override
	public IOpResponse handleOpExec(IXstEvent paramIXstEvent) {

		System.out.println("Calling Generate Invoice");
		String invoiceJson = "";
		try {
			invoiceJson = new String(Files.readAllBytes(Paths.get(System.getProperty("asq.zatca.certificate.work.dir.invoice").concat(System.getProperty("asq.zatca.sample.invoice")))));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			if (invoiceJson != null) {
				asqZatcaInvoiceGenerationHelper.generateInvoice(invoiceJson);
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated
			e.printStackTrace();
		} catch (ASQException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return HELPER.completeResponse();
	}

}
