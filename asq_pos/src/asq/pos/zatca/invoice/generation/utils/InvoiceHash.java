package asq.pos.zatca.invoice.generation.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xml.security.Init;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.xml.sax.SAXException;

public class InvoiceHash {

	static final Logger logger = LogManager.getLogger(InvoiceHash.class);

	public String getInvoiceHash(String xmlDocument) throws ParserConfigurationException, TransformerException, IOException, SAXException, InvalidCanonicalizerException, CanonicalizationException {
		Transformer transformer = getTransformer();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		StreamResult xmlOutput = new StreamResult(byteArrayOutputStream);
		transformer.transform(new StreamSource(new StringReader(xmlDocument)), xmlOutput);
		if (hashStringToBytes(canonicalizeXml(byteArrayOutputStream.toByteArray())) != null) {
			return Base64.getEncoder().encodeToString(hashStringToBytes(canonicalizeXml(byteArrayOutputStream.toByteArray())));
		}
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws TransformerConfigurationException
	 * @throws IOException
	 */
	private Transformer getTransformer() throws TransformerConfigurationException, IOException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		transformerFactory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
		transformerFactory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalStylesheet", "");

		// Transformer transformer = transformerFactory.newTransformer(new StreamSource(
		// (new ClassPathResource("invoice.xsl")).getInputStream()));
		Transformer transformer = transformerFactory.newTransformer(new StreamSource(new FileInputStream(System.getProperty("asq.pos.invoice.xls.path"))));
		transformer.setOutputProperty("encoding", "UTF-8");
		transformer.setOutputProperty("indent", "no");
		transformer.setOutputProperty("omit-xml-declaration", "yes");
		return transformer;
	}

	private String canonicalizeXml(byte[] xmlDocument) throws InvalidCanonicalizerException, CanonicalizationException, ParserConfigurationException, IOException, SAXException {
		Init.init();
		Canonicalizer canon = Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N11_OMIT_COMMENTS);
		return new String(canon.canonicalize(xmlDocument));
	}

	private byte[] hashStringToBytes(String input) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			logger.error(Level.SEVERE + " {}", e.getMessage());
		}
		if (digest != null) {
			return digest.digest(input.getBytes(StandardCharsets.UTF_8));
		}
		return new byte[0];
	}

}
