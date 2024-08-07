package asq.pos.zatca.invoice.generation.op;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.etsi.uri._01903.v1_3.SignedPropertiesType;
import org.etsi.uri._01903.v1_3.SignedSignaturePropertiesType;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.oracle.shaded.fasterxml.jackson.core.JsonProcessingException;
import com.oracle.shaded.fasterxml.jackson.databind.ObjectMapper;

import asq.pos.zatca.cert.generation.AsqZatcaErrorDesc;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceRequest;
import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceResponse;
import asq.pos.zatca.invoice.generation.utils.ASQException;
import asq.pos.zatca.invoice.models.OutboundInvoice;
import asq.pos.zatca.invoice.models.QRCode;
import oasis.names.specification.ubl.schema.xsd.commonsignaturecomponents_2.UBLDocumentSignaturesType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;

public class SmartHubUtil {

	private static final Logger logger = LogManager.getLogger(SmartHubUtil.class);

	/**
	 * This method converts File to Base64 encoding
	 * 
	 * @param file
	 * @throws IOException
	 * @return
	 */

	public static String encodeFileToBase64(File file) {
		try {
			byte[] fileContent = Files.readAllBytes(file.toPath());
			return java.util.Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException fileException) {
			throw new IllegalStateException("could not read file " + file, fileException);
		}
	}

	/**
	 * This method decodes Base64 encoding to File
	 * 
	 * @param file
	 * @throws IOException
	 * @return
	 */

	public static void decodeBase64ToFile(String csidBase64String, String filePath) {
		try {
			byte[] decodedImg = java.util.Base64.getDecoder().decode(csidBase64String.getBytes(StandardCharsets.UTF_8));
			Path destinationFile = Paths.get(filePath);
			Files.write(destinationFile, decodedImg);
		} catch (IOException decodeException) {
			logger.error("Exception while decoding Base64 To file : " + decodeException.toString());
		}
	}

	// update the csid properties
	public static void updateCsidPropertyFile(boolean b) throws IOException {
		// updating property file with CSID geenrated flag
		// FileOutputStream out = new
		// FileOutputStream(properties.getProperty("asq.pos.invoice.resourceDir") +
		// "csid.properties");
		System.setProperty("isCSIDGenerated", String.valueOf(b));
		// csidProperties.setProperty("isCSIDGenerated", String.valueOf(b));
		// csidProperties.store(out, null);
		// out.close();
	}

	private static void setDBResetFlag() {
		// updating resetDB flag value in property file
		// try {
		// FileOutputStream appPropertiesOut = new
		// FileOutputStream(properties.getProperty("asq.pos.invoice.resourceDir") +
		// "app.properties");
		System.setProperty("asq.pos.invoice.resetDB", "true");
		// properties.setProperty("asq.pos.invoice.resetDB", "true");
		// properties.store(appPropertiesOut, null);
		// appPropertiesOut.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

	}

	/**
	 * This method converts XML file to XML document
	 * 
	 * @param file
	 * @throws IOException
	 * @return null
	 */
	public static Document convertXMLFileToXMLDocument(String filePath) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
			Document xmlDocument = builder.parse(new File(filePath));
			return xmlDocument;
		} catch (Exception conversionException) {
			logger.error("Default Document Builder failed to create & parse the content to document object : " + conversionException.toString());
		}
		return null;
	}

	/**
	 * This method gets XML String
	 * 
	 * @param xmlDocument
	 * @return xmlString
	 */
	public static String getXMLString(Document xmlDocument) {
		String xmlString = null;
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();

			// Uncomment if you do not require XML declaration
			// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

			// Print XML or Logs or Console
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));
			xmlString = writer.getBuffer().toString();

		} catch (TransformerException transformerException) {
			logger.error("Transformer exception while creating new transformer : " + transformerException.toString());
		} catch (Exception xmlException) {
			logger.error("Exception while writting XML String : " + xmlException.toString());
		}

		return xmlString;
	}

	/**
	 * This method writes XML to file
	 * 
	 * @param fileName
	 * @param xmlDocument
	 * @throws ParserConfigurationException
	 * @return
	 */
	public static void writeXMLFile(String fileName, String xmlDocument) throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		try {
			String tempFileExt = System.getProperty("asq.pos.invoice.tempFileExt");
			String invoiceFileExt = System.getProperty("asq.pos.invoice.invoiceFileExt");
			String unfinishedFileName = fileName + tempFileExt;

			// PrintStream out = new PrintStream(new FileOutputStream(fileName));
			// out.print(xmlDocument);
			Document document = builder.parse(new InputSource(new StringReader(xmlDocument)));
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			FileOutputStream outStream = new FileOutputStream(new File(unfinishedFileName));
			transformer.transform(new DOMSource(document), new StreamResult(outStream));
			outStream.close();

			File unfinishedFile = new File(unfinishedFileName); // handler to your ZIP file
			File completedFile = new File(fileName + invoiceFileExt); // destination dir of your file
			boolean success = unfinishedFile.renameTo(completedFile);
			if (success) {
				logger.info("Invoice file has been created in outbound dir : " + completedFile);
			} else {
				logger.info("Invoice file has not been created in outbound dir");
			}

		} catch (Exception xmlException) {
			logger.error("Exception while writting XML to file : " + xmlException.toString());
		}
	}

	/**
	 * This method writes InvoiceJSON
	 * 
	 * @param fileName
	 * @param OutboundInvoice
	 * @throws ParserConfigurationException
	 * @return
	 */

	public static void writeInvoiceJSON(String fileName, OutboundInvoice in) throws ParserConfigurationException {

		try {
			String tempFileExt = System.getProperty("asq.pos.invoice.tempFileExt");
			String invoiceFileExt = System.getProperty("asq.pos.invoice.invoiceFileJsonExt");
			String unfinishedFileName = fileName + tempFileExt;

			String outboundJson = "";
			ObjectMapper mapper = new ObjectMapper();
			try {
				outboundJson = mapper.writeValueAsString(in);
			} catch (JsonProcessingException jsonException) {
				logger.error("Exception while processing json : " + fileName + in + jsonException.toString());
			}

			// String outboundJson = new Gson().toJson(in);
			PrintWriter out = new PrintWriter(unfinishedFileName);
			out.println(outboundJson);
			out.close();

			File unfinishedFile = new File(unfinishedFileName);
			File completedFile = new File(fileName + invoiceFileExt);
			Files.move(Paths.get(unfinishedFileName), Paths.get(fileName + invoiceFileExt), StandardCopyOption.REPLACE_EXISTING);
			logger.info("Invoice file has been created in outbound dir : " + fileName + invoiceFileExt);

		} catch (IOException ioException) {
			logger.error("Invoice file has not been created in outbound dir : " + ioException.toString());
		}
	}

	/**
	 * This method et key store, params jks certificate and key secret
	 * 
	 * @param certificateFilePath
	 * @param keySecret
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ASQException
	 * @return keystore
	 */

	public static KeyStore getKeyStore(String certificateFilePath, String keySecret)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, ASQException {
		KeyStore keystore = KeyStore.getInstance("JKS");
		logger.info("****** Loading KeyStore........ *********");
		try {
			keystore.load(new FileInputStream(certificateFilePath), keySecret.toCharArray());
		} catch (Exception e) {
			logger.info("JKS Loading Failed: {}", e.toString());
			throw new ASQException("Error In Loading JKS: Please validate the JKS file and its path -" + certificateFilePath + " Error - " + e.toString());
		}
		logger.info("****** Loaded KeyStore *********");
		return keystore;
	}

	public static byte[] sigData(KeyStore ks, String keyAlias, String keySecret, String sigAlgo, String xmlHash) throws ASQException, Exception {
		KeyStore.PrivateKeyEntry keyEntry = null;
		try {
			keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(keyAlias, new KeyStore.PasswordProtection(keySecret.toCharArray()));
		} catch (Exception e) {
			throw new ASQException("PrivateKey Generation Failed : Fetching KeyEntry from JKS Failed - Exception" + e.getLocalizedMessage());
		}
		Signature sig = Signature.getInstance(sigAlgo);
		sig.initSign(keyEntry.getPrivateKey());
		// Security.addProvider(new BouncyCastleProvider());
		// sig.initSign(PrivateKeyGenerator.getPrivateKey());
		sig.update(xmlHash.getBytes());
		byte[] signatureBytes = sig.sign();
		return signatureBytes;

	}

	// verify the signed data using public key
	public static boolean verifySignedData(KeyStore ks, String keyAlias, String keySecret, String sigAlgo, String xmlData, byte[] signatureBytes)
			throws InvalidKeyException, NoSuchAlgorithmException, KeyStoreException, SignatureException, UnsupportedEncodingException {

		java.security.cert.Certificate cert = ks.getCertificate(keyAlias);
		Signature sig = Signature.getInstance(sigAlgo);
		PublicKey publicKey = cert.getPublicKey();
		sig.initVerify(publicKey);
		sig.update(xmlData.getBytes("UTF8"));
		return sig.verify(signatureBytes);

	}

	// generate QR code
	public static String generateQRCode(String sellerName, String vatNumber, String invoiceTimeStamp, String invoiceDate, String invoiceTotal, String vatTotal, String signedHash, String signatureECDA,
			byte[] publicKeyECDA, byte[] signatureCSID) throws DecoderException {

		QRCode qrCode = new QRCode(sellerName, vatNumber, invoiceTimeStamp, invoiceDate, invoiceTotal, vatTotal, signedHash, signatureECDA, publicKeyECDA, signatureCSID);
		String hexString = qrCode.getHexString(qrCode);

		// handling the Odd number of characters issue by adding the "0"
		if (hexString.length() % 2 == 1)
			hexString = "0" + hexString;
		String encodedQRCode = qrCode.encodeBase64String(qrCode.decodeHex(hexString));

		return encodedQRCode;
	}

	// Get the common application properties
	/*
	 * public static Properties getProperties() { Properties prop = new
	 * Properties(); try { // Determine where the input file is; assuming it's in
	 * the same directory as the // jar String fileName =
	 * "\\cust_config\\invoice\\invoice.properties"; File jarFile = new File(
	 * SmartHubUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI(
	 * ).getPath()); String inputFilePath = jarFile.getParent() + File.separator +
	 * fileName; // String inputFilePath = fileName; FileInputStream inStream = new
	 * FileInputStream(new File(inputFilePath));
	 * 
	 * prop.load(inStream); // load a properties file from class path, inside static
	 * method // prop.load(SmartHubUtil.class.getClassLoader().getResourceAsStream(
	 * "app.properties")); } catch (IOException ex) { ex.printStackTrace(); } catch
	 * (URISyntaxException ex) { ex.printStackTrace(); } return prop; }
	 */

	// Get the common application properties
	/*
	 * public static Properties getCSIDProperties() { Properties prop = new
	 * Properties(); try { // Determine where the input file is; assuming it's in
	 * the same directory as the // jar // String fileName =
	 * "C:\\SmartHubAgent\\csid.properties"; String fileName =
	 * "\\cust_config\\csid\\csid.properties"; // String fileName =
	 * "src\\main\\resources\\csid.properties"; File jarFile = new File(
	 * SmartHubUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI(
	 * ).getPath()); String inputFilePath = jarFile.getParent() + File.separator +
	 * fileName; // String inputFilePath = fileName; FileInputStream inStream = new
	 * FileInputStream(new File(inputFilePath)); prop.load(inStream); // load a
	 * properties file from class path, inside static method //
	 * prop.load(SmartHubUtil.class.getClassLoader().getResourceAsStream(
	 * "csid.properties")); } catch (IOException ex) { ex.printStackTrace(); } catch
	 * (URISyntaxException ex) { ex.printStackTrace(); } return prop; }
	 */

	/*
	 * public static Properties getJKSProperties() { Properties prop = new
	 * Properties(); try { String fileName = "\\config\\jks.properties"; File
	 * jarFile = new File(
	 * SmartHubUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI(
	 * ).getPath()); String inputFilePath = jarFile.getParent() + File.separator +
	 * fileName; FileInputStream inStream = new FileInputStream(new
	 * File(inputFilePath)); prop.load(inStream); } catch (IOException ex) {
	 * ex.printStackTrace(); } catch (URISyntaxException ex) { ex.printStackTrace();
	 * } return prop; }
	 */

	// Generate invoice xml using JAXB
	public static String generateCompleteSignedInvoiceXML(InvoiceType in) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(new oasis.names.specification.ubl.schema.xsd.invoice_2.ObjectFactory().getClass().getPackage().getName() + ":"
				+ new oasis.names.specification.ubl.schema.xsd.commonsignaturecomponents_2.ObjectFactory().getClass().getPackage().getName() + ":"
				+ new org.etsi.uri._01903.v1_3.ObjectFactory().getClass().getPackage().getName());

		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		JAXBElement<InvoiceType> jaxbElement = new JAXBElement<InvoiceType>(new QName("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice"), InvoiceType.class, in);

		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(jaxbElement, sw);

		return sw.toString();
	}

	// Generate invoice xml using JAXB
	public static String generateInvoiceXML(InvoiceType in) throws JAXBException {

		// JAXBContext jaxbContext = JAXBContext.newInstance(InvoiceType.class);
		JAXBContext jaxbContext = JAXBContext.newInstance(new oasis.names.specification.ubl.schema.xsd.invoice_2.ObjectFactory().getClass().getPackage().getName() + ":"
				+ new oasis.names.specification.ubl.schema.xsd.commonsignaturecomponents_2.ObjectFactory().getClass().getPackage().getName() + ":"
				+ new org.etsi.uri._01903.v1_3.ObjectFactory().getClass().getPackage().getName());

		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		JAXBElement<InvoiceType> jaxbElement = new JAXBElement<InvoiceType>(new QName("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice"), InvoiceType.class, in);
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(jaxbElement, sw);

		return sw.toString();
	}

	// Extract the signed signature properties from invoice XML
	public static String getSignedSignaturePropertiesXML(SignedSignaturePropertiesType in) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(SignedSignaturePropertiesType.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new NamespaceMapper());
		JAXBElement<SignedSignaturePropertiesType> jaxbElement = new JAXBElement<SignedSignaturePropertiesType>(new QName("", "SignedSignatureProperties"), SignedSignaturePropertiesType.class, in);
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(jaxbElement, sw);

		String xmlString = sw.toString();

		return xmlString;
	}

	public static String getSignedPropertiesXML(SignedPropertiesType in) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(SignedSignaturePropertiesType.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new NamespaceMapper());
		JAXBElement<SignedPropertiesType> jaxbElement = new JAXBElement<SignedPropertiesType>(new QName("", "SignedPropertiesType"), SignedPropertiesType.class, in);
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(jaxbElement, sw);

		String xmlString = sw.toString();

		return xmlString;
	}

	public static JAXBElement<UBLDocumentSignaturesType> getUBLDocumentSignaturesXML(UBLDocumentSignaturesType in) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(UBLDocumentSignaturesType.class);
		// JAXBContext jaxbContext = JAXBContext.newInstance(
		// new
		// oasis.names.specification.ubl.schema.xsd.commonsignaturecomponents_2.ObjectFactory().getClass().getPackage().getName()
		// + ":" + new
		// oasis.names.specification.ubl.schema.xsd.signatureaggregatecomponents_2.ObjectFactory().getClass().getPackage().getName()
		// + ":" + new
		// oasis.names.specification.ubl.schema.xsd.signaturebasiccomponents_2.ObjectFactory().getClass().getPackage().getName()
		// );
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty("com.sun.xml.bind.marshaller.NamespacePrefixMapper", new NamespaceMapper());
		JAXBElement<UBLDocumentSignaturesType> jaxbElement = new JAXBElement<UBLDocumentSignaturesType>(
				new QName("urn:oasis:names:specification:ubl:schema:xsd:CommonSignatureComponents-2", "UBLDocumentSignatures"), UBLDocumentSignaturesType.class, in);

		// NEED TO CLEAN
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(jaxbElement, sw);
		System.out.println(sw.toString());

		return jaxbElement;
	}

	public static String getUBLDocumentSignaturesXML(UBLDocumentSignaturesType in, String a) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(UBLDocumentSignaturesType.class);
		// JAXBContext jaxbContext = JAXBContext.newInstance(
		// new
		// oasis.names.specification.ubl.schema.xsd.commonsignaturecomponents_2.ObjectFactory().getClass().getPackage().getName()
		// + ":" + new
		// oasis.names.specification.ubl.schema.xsd.signatureaggregatecomponents_2.ObjectFactory().getClass().getPackage().getName()
		// + ":" + new
		// oasis.names.specification.ubl.schema.xsd.signaturebasiccomponents_2.ObjectFactory().getClass().getPackage().getName()
		// );
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new NamespaceMapper());
		JAXBElement<UBLDocumentSignaturesType> jaxbElement = new JAXBElement<UBLDocumentSignaturesType>(
				new QName("urn:oasis:names:specification:ubl:schema:xsd:CommonSignatureComponents-2", "UBLDocumentSignatures"), UBLDocumentSignaturesType.class, in);

		// NEED TO CLEAN
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(jaxbElement, sw);
		System.out.println(sw.toString());

		return sw.toString();
	}

	// Read CSID certificate file
	public static X509Certificate readCSIDFile(String certFilePath) throws IOException {

		String csidCertificate = SmartHubUtil.getReadCSIDCertificateString(certFilePath);
		csidCertificate = csidCertificate.replace(System.getProperty("asq.pos.invoice.certHeader"), "");
		csidCertificate = csidCertificate.replace(System.getProperty("asq.pos.invoice.certFooter"), "");
		csidCertificate = csidCertificate.trim();
		byte[] decodedCertificate = Base64.decodeBase64(csidCertificate.getBytes());

		// byte[] decodedCertificate =
		// Base64.decodeBase64(Files.readAllBytes(Paths.get(certFilePath)));
		CertificateFactory certificateFactory;
		Certificate certificate = null;

		try {
			certificateFactory = CertificateFactory.getInstance("X.509");
			certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(decodedCertificate));
		} catch (CertificateException e) {
			e.printStackTrace();
		}

		X509Certificate x509Certificate = (X509Certificate) certificate;
		return x509Certificate;
	}

	// Read CSID certificate file as string
	public static String getReadCSIDCertificateString(String certFilePath) throws IOException {
		// return new String(Files.readAllBytes(Paths.get(certFilePath)));
		return new String(Files.readAllBytes(Paths.get(System.getProperty("asq.zatca.certificate.work.dir").concat(System.getProperty("asq.zatca.certificate.csidFileName")))));
	}

	// Read CSID certificate file as byte
	public static byte[] getReadCSIDCertificateByte(String certFilePath) throws IOException {
		return Files.readAllBytes(Paths.get(certFilePath));
	}

	// Generate CSID certificate Hash - SHA 256 standard
	public static byte[] generateCSIDCertHash(String certFilePath) throws IOException, NoSuchAlgorithmException {
		Base64 base64 = new Base64();
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(Files.readAllBytes(Paths.get(certFilePath)));
		byte[] certificateHash = base64.encode(hash);
		return certificateHash;
	}

	public static String generateCSIDCertHashString(String certFilePath) throws IOException, NoSuchAlgorithmException {
		Base64 base64 = new Base64();
		MessageDigest digest = MessageDigest.getInstance("SHA-256");

		String cert = removeHeaderAndFooter(getReadCSIDCertificateString(certFilePath));
		byte[] hash = digest.digest(cert.getBytes("UTF-8"));
		// byte[] hash = digest.digest(Files.readAllBytes(Paths.get(certFilePath)));
		String certificateHash = base64.encodeBase64String(hash);
		return certificateHash;
	}

	public static String hashCertificate(String certFilePath) throws IOException, NoSuchAlgorithmException {
		String certificateAsString = removeHeaderAndFooter(getReadCSIDCertificateString(certFilePath));
		return encodeBase64(byteToHex(hashing(certificateAsString.getBytes(StandardCharsets.UTF_8))).getBytes(StandardCharsets.UTF_8));
	}

	public static String hashSignatureProperties(String signatureProperties) throws NoSuchAlgorithmException {
		return encodeBase64(byteToHex(hashing(signatureProperties.getBytes(StandardCharsets.UTF_8))).getBytes(StandardCharsets.UTF_8));
	}

	// Generate UUID 128 bit standard
	public static String generateUUID() {
		final String uuid = UUID.randomUUID().toString();
		return uuid;
	}

	public static UUID generateIntegerUUID() {
		final UUID uuid = UUID.randomUUID();
		return uuid;
	}

	public static String dateAndTimeFormatChange(String date, String time) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy hh:mm:ss aa");
		Date transactionDateTime = format1.parse(date + " " + time);
		return format2.format(transactionDateTime);
	}

	public static String generateResponse(String qrCode) {

		JSONObject response = new JSONObject();
		if (null != qrCode && !qrCode.isEmpty()) {
			response.put("responseCode", "200");
			response.put("qrCode", qrCode);
		} else {
			response.put("responseCode", "400");
			// response.put("errorCode", "");
			response.put("errorMessage", "QRCode generation is failed");
			response.put("qrCode", qrCode);
		}
		logger.info("Response from SmartHubAgent in generating QR Code : " + response.toString());
		return response.toString();

	}

	public static AsqSubmitZatcaCertServiceResponse generateCertificateExpiredErrorResponse(String expirationDate) {
		AsqSubmitZatcaCertServiceResponse response = new AsqSubmitZatcaCertServiceResponse();
		AsqZatcaErrorDesc error = new AsqZatcaErrorDesc();
		error.setCode("400");
		error.setMessage("Certificate is Expired : " + expirationDate);
		response.setErrors(error);
		return response;
	}

	/**
	 * Removing XML declaration ,line separator and characters
	 * 
	 * @param xml
	 * @return
	 */
	public static String removeNewlineAndWhiteSpaces(String xml) {
		String xmlVserion = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
		xml = xml.replace(xmlVserion, "");
		xml = xml.replaceAll(System.lineSeparator(), "");
		xml = xml.replaceAll("[>]\\s+\\<", "><");
		return xml.trim();
	}

	public static XMLGregorianCalendar getSigningTime() throws DatatypeConfigurationException {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		// sdf.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
		String formatted = sdf.format(d);
		XMLGregorianCalendar dateXMLGreg = DatatypeFactory.newInstance().newXMLGregorianCalendar(formatted);
		return dateXMLGreg;
	}

	public static XMLGregorianCalendar signingTimeConversion(String date, String time) throws ParseException, DatatypeConfigurationException {

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy HH:mm:ss aa");

		Date signingTime = format1.parse(date + " " + time);
		// Date signingTime = format1.parse("2023-01-11 11:28:50");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String formatted = sdf.format(signingTime);
		XMLGregorianCalendar dateXMLGreg = DatatypeFactory.newInstance().newXMLGregorianCalendar(formatted);
		return dateXMLGreg;

	}

	public static XMLGregorianCalendar IssueTimeFormatConversion(String time) throws ParseException, DatatypeConfigurationException {

		SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");
		Date issueTime = format1.parse(time);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String formatted = sdf.format(issueTime);
		XMLGregorianCalendar dateXMLGreg = DatatypeFactory.newInstance().newXMLGregorianCalendar(formatted);
		return dateXMLGreg;

	}

	public static String removeSigAttributes(String invoiceXML) {
		invoiceXML = invoiceXML.replaceFirst(" " + System.getProperty("asq.pos.invoice.sigAttr1"), "");
		invoiceXML = invoiceXML.replaceFirst(" " + System.getProperty("asq.pos.invoice.sigAttr2"), "");
		invoiceXML = invoiceXML.replaceFirst(" " + System.getProperty("asq.pos.invoice.sigAttr3"), "");
		invoiceXML = invoiceXML.replaceFirst(" " + System.getProperty("asq.pos.invoice.sigAttr4"), "");
		invoiceXML = invoiceXML.replaceFirst(" " + System.getProperty("asq.pos.invoice.sigAttr5"), "");
		return invoiceXML;
	}

	public static String removeHeaderAndFooter(String certificate) {
		certificate = certificate.replace(System.getProperty("asq.pos.invoice.certHeader"), "");
		certificate = certificate.replace(System.getProperty("asq.pos.invoice.certFooter"), "");
		certificate = certificate.trim();
		return certificate;
	}

	public static byte[] hashing(byte[] toBeHashed) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(toBeHashed);
		return hash;

	}

	public static String byteToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xFF & hash[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public static String encodeBase64(byte[] stringTobBeEncoded) {
		return java.util.Base64.getEncoder().encodeToString(stringTobBeEncoded);
	}

	public static void writeInvoiceJSON(String invoiceID, String invoiceIssueDate, String invoiceIssueTime, AsqSubmitZatcaCertServiceRequest zatcaInvoiceRequest)
			throws ParserConfigurationException, IOException {
		StringBuilder fileName = new StringBuilder(System.getProperty("asq.pos.invoice.outboundFolder")).append(System.getProperty("asq.pos.invoice.invoiceFileName")).append(invoiceID).append("_")
				.append(invoiceIssueDate.replace("-", "")).append(invoiceIssueTime.replace(":", "")).append(System.getProperty("asq.pos.invoice.invoiceFileJsonExt"));

		ObjectMapper mapper = new ObjectMapper();
		String outboundJson = mapper.writeValueAsString(zatcaInvoiceRequest);
		PrintWriter out = new PrintWriter(fileName.toString());
		out.println(outboundJson);
		out.close();

		logger.debug("Invoice file has been created in outbound dir : " + fileName.toString());
	}
}
