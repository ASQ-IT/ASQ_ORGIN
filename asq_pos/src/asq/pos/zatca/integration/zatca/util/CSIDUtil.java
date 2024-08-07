package asq.pos.zatca.integration.zatca.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

public class CSIDUtil {

	public static void generateCSIDFile(String binaryToken) throws IOException {
		
		String csidCertificateFilePath = (System.getProperty("asq.zatca.certificate.work.dir").
				                           concat(System.getProperty("asq.zatca.certificate.csidFileName")));
		File csidFile = new File(csidCertificateFilePath);
		if (!csidFile.exists()) {
			csidFile.createNewFile();
		}
//		String decodedValue=new String(Base64.decodeBase64(binaryToken));

		String decodedValue = new String(Base64.getDecoder().decode(binaryToken), "UTF-8");
		String certificate = "-----BEGIN CERTIFICATE-----\n" + decodedValue + "\n-----END CERTIFICATE-----";
		FileWriter writer = new FileWriter(csidCertificateFilePath);
		writer.write(certificate);
		writer.close();

	}

}
