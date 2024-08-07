package asq.pos.zatca.integration.data;

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


public class ProductionCSIDGenerator {

	public static Properties csidProperties = POSUtil.getCSIDProperties();

	public static void generatePCSID() {

		if ("true".equalsIgnoreCase(csidProperties.getProperty("isComplianceCheck"))) {
			// ZATCA Req
			ProductionRequest productionRequest = new ProductionRequest();
			productionRequest.setCompliance_request_id(csidProperties.getProperty("complianceRequestID"));

			// Sending Request to ZATC and recieving response
			String response = sendRequest(productionRequest);
//			ZATCA Response Mapping
			ProductionResponse productionResponse = mapResponse(response);

			try {
				mapRequiredValuesToPropertiesFile(productionResponse);
				CSIDUtil.generateCSIDFile(productionResponse.getBinarySecurityToken());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		} else {
			// compliance check is not done yet error
		}

	}

	private static void mapRequiredValuesToPropertiesFile(ProductionResponse productionResponse)
			throws URISyntaxException {
		try {
			if (null != productionResponse) {
				FileOutputStream out = new FileOutputStream(POSUtil.getBasePath("\\cust_config\\csid.properties"));
				csidProperties.setProperty("onboardingType", "PCSID");
				csidProperties.setProperty("complianceRequestID", productionResponse.getRequestID());
				csidProperties.setProperty("binarySecurityToken", productionResponse.getBinarySecurityToken());
				csidProperties.setProperty("secret", productionResponse.getSecret());
				csidProperties.setProperty("isComplianceCheck", String.valueOf(false));
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

	private static ProductionResponse mapResponse(String response) {
		ObjectMapper mapper = new ObjectMapper();
		ProductionResponse productionResponse = null;
		try {
			productionResponse = mapper.readValue(response, ProductionResponse.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return productionResponse;
	}

	private static String sendRequest(ProductionRequest productionRequest) {

		ObjectMapper mapper = new ObjectMapper();
		String request = "";
		try {
			request = mapper.writeValueAsString(productionRequest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		Map<String, String> header = new HashMap<>();
//		header.put("OTP", otp);
		return new ConnectionService().sendRequest(request, csidProperties.getProperty("ccsidRequestURL"), "POST",
				header);
	}

}
