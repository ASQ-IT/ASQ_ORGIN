/**
 * 
 */
package asq.pos.zatca.cert.generation.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.oracle.shaded.eclipse.persistence.internal.xr.Invocation;
import com.oracle.shaded.eclipse.persistence.internal.xr.Operation;
import com.oracle.shaded.eclipse.persistence.internal.xr.XRServiceAdapter;

import asq.pos.zatca.integration.zatca.util.POSUtil;
import dtv.pos.iframework.event.IXstEvent;
import dtv.pos.iframework.op.IOpResponse;

/**
 * @author RA20221457
 *
 */
public class AsqZatcaComplianceCSIDGeneratorOp extends Operation{
	

	 IOpResponse handleOpExec(IXstEvent argVar1) {
	
		// ZATCA Req
		try {
		final String csrFilePath=System.getProperty("asq.zatca.csrFilePath");
		AsqSubmitZatcaCertServiceRequest complianceRequest = new AsqSubmitZatcaCertServiceRequest();
		String file = POSUtil.encodeFileToBase64(new File(csrFilePath));
		//set otp here
		complianceRequest.setCsr(file);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
		// Sending Request to ZATC and recieving response
		/*
		 * String response = sendRequest(complianceRequest, otp); // ZATCA Response
		 * Mapping ComplianceResponse complianceResponse = mapResponse(response);
		 * 
		 * try { mapRequiredValuesToPropertiesFile(complianceResponse);
		 * CSIDUtil.generateCSIDFile(complianceResponse.getBinarySecurityToken()); }
		 * catch (IOException e) { e.printStackTrace(); } catch (URISyntaxException e) {
		 * e.printStackTrace(); }
		 */
	

	
	



	@Override
	public Object invoke(XRServiceAdapter arg0, Invocation arg1) {
		// TODO Auto-generated method stub
		return null;
	}
}
