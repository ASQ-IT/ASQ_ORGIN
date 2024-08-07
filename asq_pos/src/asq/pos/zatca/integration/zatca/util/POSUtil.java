package asq.pos.zatca.integration.zatca.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Properties;

public class POSUtil {
	
	public static Properties getCSIDProperties() {
		Properties prop = new Properties();
		try {
			// Determine where the input file is; assuming it's in the same directory as the
			// jar
			String fileName =(System.getProperty("asq.zatca.certificate.work.dir").concat("csid.properties"));
			String inputFilePath = getBasePath(fileName);
			// String inputFilePath = fileName;
			FileInputStream inStream = new FileInputStream(new File(inputFilePath));

			prop.load(inStream);
			// load a properties file from class path, inside static method
			// prop.load(SmartHubUtil.class.getClassLoader().getResourceAsStream("app.properties"));
		} catch (IOException ex) {
			ex.printStackTrace();
		} 
			  catch (URISyntaxException e) { e.printStackTrace(); }
			 
		return prop;
	}
	
	public static String getBasePath(String path) throws URISyntaxException {
		System.out.println();
		File jarFile = new File(
				POSUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		return  path;
	}
	
	public static String encodeFileToBase64(File file) {
		try {
			byte[] fileContent = Files.readAllBytes(file.toPath());
			return java.util.Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			throw new IllegalStateException("could not read file " + file, e);
		}
	}

}
