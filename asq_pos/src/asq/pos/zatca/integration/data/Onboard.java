package asq.pos.zatca.integration.data;

import java.net.URISyntaxException;

import asq.pos.zatca.integration.zatca.util.POSUtil;



public class Onboard {

	public static void processCCSID(String otp) {
		String path;
		try {
			path = POSUtil.getBasePath("\\cust_config\\csid\\");

			// Generating private key
						String command = "openssl ecparam -name secp256k1 -genkey -noout -out " + path + "PrivateKey.pem";
						CommandPrompt.runCommand(command);

						// Generating public key
						command = "openssl ec -in " + path + "PrivateKey.pem -pubout -conv_form compressed -out " + path
								+ "publickey.pem";
						CommandPrompt.runCommand(command);

						// Converting public key pem to bin
						command = "openssl base64 -d -in " + path + "publickey.pem -out " + path + "publickey.bin";
						CommandPrompt.runCommand(command);

						// Generating csr from the given config with private key and public key
						command = "openssl req -new -sha256 -key " + path + "PrivateKey.pem -extensions v3_req -config " + path
								+ "config.cnf -out " + path + "pos_cert.csr";
						CommandPrompt.runCommand(command);

			// Sending Request to ZATCA to generate Compliance CSID
			ComplianceCSIDGenerator.generateCCSID(otp);

			// Converting cert to p12 file.
			command = "openssl pkcs12 -export -in " + path + "csid.cer -inkey " + path
					+ "PrivateKey.pem -name pos_csr -out " + path + "cert-and-key.p12 -password pass:27934968";
			CommandPrompt.runCommand(command);

			// Creating empty JKS file if its not already available
			command = "keytool -genkey -alias pos_csr -keyalg EC -keysize 256 -sigalg SHA256withECDSA  -dname CN=atg.altayer.com,OU=RiyadBranch,O=NibrasAlArabiaCompanyLimited,L=Riyadh,ST=Riyadh,C=SA -validity 365 -storetype JKS  -keypass 27934968 -keystore resources/pos_csr.jks -storepass 27934968";
			CommandPrompt.runCommand(command);

			// Deleting the cert from JKS if its already exist
			command = "keytool -delete -alias pos_csr -keystore resources/pos_csr.jks -storepass 27934968";
			CommandPrompt.runCommand(command);

			// Adding newly generated cert to the JKS
			command = "keytool -v -importkeystore -srckeystore resources/cert-and-key.p12 -srcstoretype PKCS12 -srcstorepass 27934968 -destkeystore resources/pos_csr.jks -deststoretype JKS -storepass 27934968";
			CommandPrompt.runCommand(command);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static void processPCSID() {

		// Sending Request to ZATCA to generate Production CSID
		ProductionCSIDGenerator.generatePCSID();

		String path;
		try {
			path = POSUtil.getBasePath("\\cust_config\\csid\\");

			// Converting cert to p12 file.
			String command = "openssl pkcs12 -export -in " + path + "csid.cer -inkey " + path
					+ "PrivateKey.pem -name pos_csr -out " + path + "cert-and-key.p12 -password pass:27934968";
			CommandPrompt.runCommand(command);

			// Creating empty JKS file if its not already available
			command = "keytool -genkey -alias pos_csr -keyalg EC -keysize 256 -sigalg SHA256withECDSA  -dname CN=atg.altayer.com,OU=RiyadBranch,O=NibrasAlArabiaCompanyLimited,L=Riyadh,ST=Riyadh,C=SA -validity 365 -storetype JKS  -keypass 27934968 -keystore resources/pos_csr.jks -storepass 27934968";
			CommandPrompt.runCommand(command);

			// Deleting the cert from JKS if its already exist
			command = "keytool -delete -alias pos_csr -keystore resources/pos_csr.jks -storepass 27934968";
			CommandPrompt.runCommand(command);

			// Adding newly generated cert to the JKS
			command = "keytool -v -importkeystore -srckeystore resources/cert-and-key.p12 -srcstoretype PKCS12 -srcstorepass 27934968 -destkeystore resources/pos_csr.jks -deststoretype JKS -storepass 27934968";
			CommandPrompt.runCommand(command);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
