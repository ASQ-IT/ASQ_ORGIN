package asq.pos.zatca.integration.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandPrompt {
	
//	public static void main(String[] args) {
//		String command="keytool -genkey -alias pos_csr -keyalg EC -keysize 256 -sigalg SHA256withECDSA  -dname CN=atg.altayer.com,OU=RiyadBranch,O=NibrasAlArabiaCompanyLimited,L=Riyadh,ST=Riyadh,C=SA -validity 365 -storetype JKS  -keypass 27934968 -keystore D:/AL-Q/sample/pos_csr.jks -storepass 27934968";
//		runCommand(command);
//	}
	public static void runCommand(String command) {
		Runtime runtime=Runtime.getRuntime();
		try {
			Process process = runtime.exec("cmd /c "+command);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String readLine;
			while ((readLine = br.readLine()) != null) {
				
				System.out.println(readLine);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
