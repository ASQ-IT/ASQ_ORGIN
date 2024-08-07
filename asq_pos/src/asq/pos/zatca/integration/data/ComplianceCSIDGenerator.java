package asq.pos.zatca.integration.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.oracle.shaded.fasterxml.jackson.core.JsonProcessingException;
import com.oracle.shaded.fasterxml.jackson.databind.ObjectMapper;

import asq.pos.zatca.integration.zatca.util.CSIDUtil;
import asq.pos.zatca.integration.zatca.util.POSUtil;

public class ComplianceCSIDGenerator {

	public static Properties csidProperties = POSUtil.getCSIDProperties();

	public static void generateCCSID(String otp) {

		// ZATCA Req
		final String csrFilePath = csidProperties.getProperty("csrFilePath");
		ComplianceRequest complianceRequest = new ComplianceRequest();
		complianceRequest.setCsr(POSUtil.encodeFileToBase64(new File(csrFilePath)));

		// Sending Request to ZATC and recieving response
		String response = sendRequest(complianceRequest, otp);
//		ZATCA Response Mapping
		ComplianceResponse complianceResponse = mapResponse(response);

		try {
			mapRequiredValuesToPropertiesFile(complianceResponse);
			CSIDUtil.generateCSIDFile(complianceResponse.getBinarySecurityToken());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private static void mapRequiredValuesToPropertiesFile(ComplianceResponse complianceResponse)
			throws URISyntaxException {
		try {
			if (null != complianceResponse) {
				FileOutputStream out = new FileOutputStream(POSUtil.getBasePath("\\cust_config\\csid\\csid.properties"));
				csidProperties.setProperty("isCSIDGenerated", String.valueOf(true));
				csidProperties.setProperty("onboardingType", "CCSID");
				csidProperties.setProperty("complianceRequestID", complianceResponse.getRequestID());
				csidProperties.setProperty("binarySecurityToken", complianceResponse.getBinarySecurityToken());
				csidProperties.setProperty("secret", complianceResponse.getSecret());
				csidProperties.setProperty("isComplianceCheck", String.valueOf(true));
				csidProperties.store(out, null);
				out.close();
			} else {
				// error
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static ComplianceResponse mapResponse(String response) {
		ObjectMapper mapper = new ObjectMapper();
		ComplianceResponse complianceResponse = null;
		try {
			complianceResponse = mapper.readValue(response, ComplianceResponse.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return complianceResponse;
	}

	private static String sendRequest(ComplianceRequest complianceRequest, String otp) {

		ObjectMapper mapper = new ObjectMapper();
		String request = "";
		try {
			request = mapper.writeValueAsString(complianceRequest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		Map<String, String> header = new HashMap<>();
		header.put("OTP", otp);
		return new ConnectionService().sendRequest(request, csidProperties.getProperty("ccsidRequestURL"), "POST",
				header);
	}

}
