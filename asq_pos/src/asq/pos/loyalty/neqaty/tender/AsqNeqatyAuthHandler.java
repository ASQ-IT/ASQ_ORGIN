package asq.pos.loyalty.neqaty.tender;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import dtv.util.crypto.DtvDecrypter;

public class AsqNeqatyAuthHandler extends Object implements SOAPHandler<SOAPMessageContext> {

	private String authToken;
	private String authTokenName;

	@Override
	public void close(MessageContext arg0) {
	}

	@Override
	public boolean handleFault(SOAPMessageContext arg0) {
		return true;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		Boolean outboundProperty = (Boolean) context.get("javax.xml.ws.handler.message.outbound");
		if (outboundProperty.booleanValue()) {
			Map<String, List<String>> httpReqHeaders = null;
			Object o = context.get("javax.xml.ws.http.request.headers");
			if (o != null && o instanceof Map) {
				httpReqHeaders = (Map) o;
			} else {
				httpReqHeaders = new HashMap<String, List<String>>();
			}
			String decryptedToken = DtvDecrypter.getInstance("config").decryptIfEncrypted(this.authToken);
			httpReqHeaders.put(this.authTokenName, Collections.singletonList(decryptedToken));
			context.put("javax.xml.ws.http.request.headers", httpReqHeaders);
		}
		return true;
	}

	@Override
	public Set<QName> getHeaders() {
		return new TreeSet();
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getAuthTokenName() {
		return authTokenName;
	}

	public void setAuthTokenName(String authTokenName) {
		this.authTokenName = authTokenName;
	}

}
