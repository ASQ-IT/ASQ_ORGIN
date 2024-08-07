package asq.pos.zatca.integration.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class ConnectionService {

	public String sendRequest(String request, String requestUrl, String requestMethod, Map<String, String> header) {
		StringBuilder response = new StringBuilder();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(requestMethod);
			if ("POST".equals(requestMethod)) {
				con.setRequestProperty("Content-Type", "application/json");
			}
			for (Map.Entry<String, String> entry : header.entrySet()) {
				con.setRequestProperty(entry.getKey(), entry.getValue());
			}
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();

			os.write(request.getBytes());
			os.close();

			if (200 == con.getResponseCode()) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String readLine;
				while ((readLine = br.readLine()) != null) {
					response.append(readLine);
				}
				br.close();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response.toString();
	}

}
