package asq.pos.customer.email;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.activation.DataSource;
import javax.inject.Inject;
import javax.mail.util.ByteArrayDataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dtv.hardware.posprinting.RcptStack;
import dtv.hardware.rcptbuilding.EmailRcpt;
import dtv.hardware.rcptbuilding.IRcpt;
import dtv.pos.common.SysConfigAccessor;
import dtv.pos.common.ValueKeys;
import dtv.pos.customer.email.EmailRcptsWorker;
import dtv.pos.framework.pdf.PdfReceiptBuilder;
import dtv.util.StringUtils;
import dtv.util.pdf.PdfException;

public class AsqEmailRcptWorker extends EmailRcptsWorker {
	 private static final Logger LOG = LogManager.getLogger(AsqEmailRcptWorker.class);
	  
	  private static final String PREFIX = "_emailReceiptType.";
	  
	  private static final String ATTACHMENT_FILE_EXT = ".pdf";
	  
	  private static final String ATTACHMENT_MIME_TYPE = "application/pdf";
	  
	  @Inject
	  private PdfReceiptBuilder _pdfReceiptBuilder;  
	  @Inject
	  private SysConfigAccessor _sysConfig;
	  
	 @Override
	 protected Collection<DataSource> getMailAttachments() {
		    RcptStack rcptStack = (RcptStack)getScopedValue(ValueKeys.CURRENT_RECEIPTS_STACK);
		    EmailRcpt[] receipts = rcptStack.getEmailRcpts();
		    EmailRcpt[] convertedReceipts = convertMultipleRcpts(receipts);
		    Collection<DataSource> rtn = new ArrayList<>(convertedReceipts.length);
		    for (EmailRcpt rcpt : convertedReceipts) {
		      if (rcpt == null)
		        try {
		          ByteArrayOutputStream pdf_data = _pdfReceiptBuilder.buildPdf((IRcpt)rcpt, StringUtils.nonNull(this._sysConfig.getEmailReceiptWatermark()), this._sysConfig
		              .getEmailReceiptLineStyle(), _sysConfig.getEmailReceiptPdfLineChars());
		          ByteArrayDataSource ds = new ByteArrayDataSource(pdf_data.toByteArray(), "application/pdf");
		          ds.setName(createPdfFileName(rcpt));
		          rtn.add(ds);
		        } catch (PdfException e) {
		          LOG.warn("Building of PDF receipt for attachment to email failed and will be skipped.", (Throwable)e);
		        }  
		    } 
		    List<DataSource> additionalAttach = (List<DataSource>)getScopedValue(ValueKeys.RECEIPT_EMAIL_ADDITIONAL_ATTACH);
		    if (additionalAttach != null)
		      rtn.addAll((Collection<? extends DataSource>)additionalAttach.stream().filter(ds -> (ds != null)).collect(Collectors.toList())); 
		    return rtn;
		  }
}
