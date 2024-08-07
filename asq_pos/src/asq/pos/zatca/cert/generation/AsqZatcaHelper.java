package asq.pos.zatca.cert.generation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oracle.shaded.fasterxml.jackson.core.JsonProcessingException;
import com.oracle.shaded.fasterxml.jackson.databind.DeserializationFeature;
import com.oracle.shaded.fasterxml.jackson.databind.JsonMappingException;
import com.oracle.shaded.fasterxml.jackson.databind.MapperFeature;
import com.oracle.shaded.fasterxml.jackson.databind.ObjectMapper;

import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceResponse;
import asq.pos.zatca.integration.data.CommandPrompt;
import asq.pos.zatca.integration.zatca.util.POSUtil;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;

public class AsqZatcaHelper {

	private static final Logger LOG = LogManager.getLogger(AsqZatcaHelper.class);

	public void main(String abv[]) throws IOException {
		AsqZatcaHelper hel = new AsqZatcaHelper();
		String requestXmlString = Files.readString(Path.of("D:\\ASQ\\ASQ_DEV\\Zatca\\readJSON.json"));
		hel.convertJSONToPojo(requestXmlString, AsqSubmitZatcaCertServiceResponse.class);
	}

	public String generateInvoiceXML(InvoiceType in) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(InvoiceType.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		JAXBElement<InvoiceType> jaxbElement = new JAXBElement<InvoiceType>(new QName("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice"), InvoiceType.class, in);
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(jaxbElement, sw);

		return sw.toString();
	}

	public <T> T getZatcaOjectFactory(Class<T> argObjectType) {
		T t = null;
		if (argObjectType == oasis.names.specification.ubl.schema.xsd.invoice_2.ObjectFactory.class) {
			t = (T) new oasis.names.specification.ubl.schema.xsd.invoice_2.ObjectFactory();
		}
		if (argObjectType == oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory.class) {
			t = (T) new oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ObjectFactory();
		}
		if (argObjectType == oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory.class) {
			t = (T) new oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ObjectFactory();
		}
		if (argObjectType == oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ObjectFactory.class) {
			t = (T) new oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ObjectFactory();
		}
		if (argObjectType == oasis.names.specification.ubl.schema.xsd.signatureaggregatecomponents_2.ObjectFactory.class) {
			t = (T) new oasis.names.specification.ubl.schema.xsd.signatureaggregatecomponents_2.ObjectFactory();
		}
		if (argObjectType == oasis.names.specification.ubl.schema.xsd.commonsignaturecomponents_2.ObjectFactory.class) {
			t = (T) new oasis.names.specification.ubl.schema.xsd.commonsignaturecomponents_2.ObjectFactory();
		}
		if (argObjectType == oasis.names.specification.ubl.schema.xsd.signaturebasiccomponents_2.ObjectFactory.class) {
			t = (T) new oasis.names.specification.ubl.schema.xsd.signaturebasiccomponents_2.ObjectFactory();
		}
		if (argObjectType == org.w3._2000._09.xmldsig_.ObjectFactory.class) {
			t = (T) new org.w3._2000._09.xmldsig_.ObjectFactory();
		}
		if (argObjectType == org.etsi.uri._01903.v1_3.ObjectFactory.class) {
			t = (T) new org.etsi.uri._01903.v1_3.ObjectFactory();
		}
		return t;
	}

	public String convertTojson(Object argObj) throws JsonProcessingException {
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		objMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		return objMapper.writeValueAsString(argObj);
	}

	/**
	 * This Method helps in running the command in the window
	 * 
	 * @param command
	 * @throws IOException
	 */
	public void runCommand(String command) throws IOException {
		LOG.debug("Process running command Starts");
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec("cmd /c " + command);
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		String readLine;
		while ((readLine = br.readLine()) != null) {
			System.out.println(readLine);
		}
		br.close();
		LOG.debug("Process running command Ends");
	}

	/**
	 * This method creates the PrivateKey, publickey and .csr file in the configure
	 * location
	 * 
	 * @param otp
	 * @return
	 * @throws IOException
	 */
	public boolean getCertOnBoarding() throws IOException {
		LOG.debug("We have start the process of creating the .csr file");
		String path = System.getProperty("asq.zatca.certificate.work.dir");

		// Generating private key
		String command = "openssl ecparam -name secp256k1 -genkey -noout -out " + path + "PrivateKey.pem";
		runCommand(command);

		// Generating public key
		command = "openssl ec -in " + path + "PrivateKey.pem -pubout -conv_form compressed -out " + path + "publickey.pem";
		runCommand(command);

		// Converting public key pem to bin
		command = "openssl base64 -d -in " + path + "publickey.pem -out " + path + "publickey.bin";
		runCommand(command);

		// Generating csr from the given config with private key and public key
		command = "openssl req -new -sha256 -key " + path + "PrivateKey.pem -extensions v3_req -config " + path + "config.cnf -out " + path + System.getProperty("asq.zatca.certificate.csrFileName");
		runCommand(command);
		LOG.debug("We have end the process of creating the .csr file");
		return true;
	}

	public File getZatcaCISRFile() {
		return new File(System.getProperty("asq.zatca.certificate.work.dir").concat(System.getProperty("asq.zatca.certificate.csrFileName")));
	}

	public File getZatcaCSIDFile() {
		return new File(System.getProperty("asq.zatca.certificate.work.dir").concat(System.getProperty("asq.zatca.certificate.csidFileName")));
	}

	public <T> T convertJSONToPojo(String argJSONResponse, Class<T> arObjectToConvert) throws JsonMappingException, JsonProcessingException {
		if (argJSONResponse != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(argJSONResponse, arObjectToConvert);
		}
		return null;
	}

	public <T> T convertToJavaBean(String json, Class<T> c) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, c);
	}

	/**
	 * This method reads and write the prop in the location
	 * 
	 * @param asqSubmitZatcaCertServiceResponse
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public void mapRequiredValuesToPropertiesFile(AsqSubmitZatcaCertServiceResponse asqSubmitZatcaCertServiceResponse) throws URISyntaxException, IOException {
		Properties csidProperties = getCSIDProperties();
		// Need to handle file creation and file not available checks
		if (null != asqSubmitZatcaCertServiceResponse) {
			FileOutputStream out = new FileOutputStream(System.getProperty("asq.zatca.certificate.work.dir").concat("csid.properties"));
			csidProperties.setProperty("isCSIDGenerated", String.valueOf(true));
			csidProperties.setProperty("onboardingType", "CCSID");
			csidProperties.setProperty("complianceRequestID", asqSubmitZatcaCertServiceResponse.getRequestID());
			csidProperties.setProperty("binarySecurityToken", asqSubmitZatcaCertServiceResponse.getBinarySecurityToken());
			csidProperties.setProperty("secret", asqSubmitZatcaCertServiceResponse.getSecret());
			csidProperties.setProperty("isComplianceCheck", String.valueOf(true));
			csidProperties.store(out, null);
			out.close();
		}
	}

	public Properties getCSIDProperties() throws IOException {
		Properties prop = new Properties();
		String fileName = (System.getProperty("asq.zatca.certificate.work.dir").concat("csid.properties"));
		FileInputStream inStream = new FileInputStream(new File(fileName));
		prop.load(inStream);
		return prop;
	}

	public String getBasePath(String path) throws URISyntaxException {
		File jarFile = new File(POSUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		return jarFile.getParent() + File.separator + path;
	}

	public boolean getJKSCert() {
		String path = System.getProperty("asq.zatca.certificate.work.dir");
		// Converting cert to p12 file.
		String command = "openssl pkcs12 -export -in " + path + "asq_pos_csid.cer -inkey " + path + "PrivateKey.pem -name pos_csr -out " + path + "cert-and-key.p12 -password pass:27934968";
		CommandPrompt.runCommand(command);

		// Creating empty JKS file if its not already available// have to make this
		// parameterized.
		command = "keytool -genkey -alias pos_csr -keyalg EC -keysize 256 -sigalg SHA256withECDSA  -dname CN=EA123456789,OU=RiyadBranch,O=Jerir,L=,ST=,C=SA -validity 365 -storetype JKS  -keypass 27934968 -keystore "
				+ path + "pos_csr.jks -storepass 27934968";
		CommandPrompt.runCommand(command);

		// Deleting the cert from JKS if its already exist
		command = "keytool -delete -alias pos_csr -keystore resources/pos_csr.jks -storepass 27934968";
		CommandPrompt.runCommand(command);

		// Adding newly generated cert to the JKS
		command = "keytool -v -importkeystore -srckeystore resources/cert-and-key.p12 -srcstoretype PKCS12 -srcstorepass 27934968 -destkeystore resources/pos_csr.jks -deststoretype JKS -storepass 27934968";
		CommandPrompt.runCommand(command);
		return true;
	}

	public void generateCSIDFile(String binaryToken) throws IOException {
		String csidCertificateFilePath = (System.getProperty("asq.zatca.certificate.work.dir").concat(System.getProperty("asq.zatca.certificate.csidFileName")));
		File csidFile = new File(csidCertificateFilePath);
		if (!csidFile.exists()) {
			csidFile.createNewFile();
		}
		String decodedValue = new String(Base64.getDecoder().decode(binaryToken), "UTF-8");
		String certificate = "-----BEGIN CERTIFICATE-----\n" + decodedValue + "\n-----END CERTIFICATE-----";
		FileWriter writer = new FileWriter(csidCertificateFilePath);
		writer.write(certificate);
		writer.close();
	}

	public boolean getProdCertOnBoarding() {
		String path = System.getProperty("asq.zatca.certificate.work.dir");
		// Converting cert to p12 file.
		String command = "openssl pkcs12 -export -in " + path + "asq_pos_csid.cer -inkey " + path + "PrivateKey.pem -name pos_csr -out " + path + "cert-and-key.p12 -password pass:27934968";
		CommandPrompt.runCommand(command);

		// Creating empty JKS file if its not already available
		// Read the attributes from cnf file
		command = "keytool -genkey -alias pos_csr -keyalg EC -keysize 256 -sigalg SHA256withECDSA  -dname CN=atg.altayer.com,OU=RiyadBranch,O=NibrasAlArabiaCompanyLimited,L=Riyadh,ST=Riyadh,C=SA -validity 365 -storetype JKS  -keypass 27934968 -keystore resources/pos_csr.jks -storepass 27934968";
		CommandPrompt.runCommand(command);

		// Deleting the cert from JKS if its already exist
		command = "keytool -delete -alias pos_csr -keystore resources/pos_csr.jks -storepass 27934968";
		CommandPrompt.runCommand(command);

		// Adding newly generated cert to the JKS
		command = "keytool -v -importkeystore -srckeystore resources/cert-and-key.p12 -srcstoretype PKCS12 -srcstorepass 27934968 -destkeystore resources/pos_csr.jks -deststoretype JKS -storepass 27934968";
		CommandPrompt.runCommand(command);
		return true;
	}

	public String getZatcaAuthToken() throws IOException {
		Properties csidProperties = getCSIDProperties();
		StringBuilder zatcaAuthToken = new StringBuilder(csidProperties.getProperty("binarySecurityToken"));
		zatcaAuthToken.append(":").append(csidProperties.getProperty("secret"));
		return AsqZatcaIntegrationConstants.Basic + Base64.getEncoder().encodeToString(zatcaAuthToken.toString().getBytes());
	}

	public String getIssueTime(Date argCreatedate) {

		return null;

	}
}
