package asq.pos;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;

public class RESRTEST {

	public static void main(String[] args) {

		BigDecimal cal = new BigDecimal("498.9965");

		cal = cal.setScale(2, RoundingMode.HALF_UP);

		System.out.println(cal);

		/*
		 * HttpFileDownloader downloader = new HttpFileDownloader();
		 *
		 * try { URL url = makeUrl("https://uat.asqgroup.com/deploy/",
		 * "1_29_!!_3_COUNTRY_SA_DISCOUNT_TRANSLATION_AR_V1.0.zip"); SetOfHashes hasshes
		 * = downloader.downloadFile(url, new
		 * File("1_29_!!_3_COUNTRY_SA_DISCOUNT_TRANSLATION_AR_V1.0.zip"));
		 * System.out.println(hasshes); } catch (Exception ex) { System.out.println(ex);
		 * }
		 */

	}

	public static URL makeUrl(String argBaseUrl, String argResource) throws MalformedURLException, UnsupportedEncodingException {
		StringBuilder url = new StringBuilder();
		url.append(argBaseUrl);
		boolean baseEndsWithSlash = argBaseUrl.endsWith("/");
		boolean resourceStartsWithSlash = argResource.startsWith("/");
		if (!baseEndsWithSlash && !resourceStartsWithSlash) {
			url.append('/');
		} else if (baseEndsWithSlash && resourceStartsWithSlash) {
			url.setLength(url.length() - 1);
		}
		url.append(argResource);
		return new URL(url.toString());
	}

}
