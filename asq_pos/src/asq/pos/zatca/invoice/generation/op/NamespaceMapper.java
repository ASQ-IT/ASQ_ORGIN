package asq.pos.zatca.invoice.generation.op;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class NamespaceMapper extends NamespacePrefixMapper {
	
	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
			 if ("urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2".equals(namespaceUri)) return "cac";
		else if ("urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2".equals(namespaceUri)) return "cbc";
		else if ("urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2".equals(namespaceUri)) return "ext";
		else if ("urn:oasis:names:specification:ubl:schema:xsd:CommonSignatureComponents-2".equals(namespaceUri)) return "sig";
		else if ("urn:oasis:names:specification:ubl:schema:xsd:SignatureAggregateComponents-2".equals(namespaceUri)) return "sac";
		else if ("urn:oasis:names:specification:ubl:schema:xsd:SignatureBasicComponents-2".equals(namespaceUri)) return "sbc";
		else if ("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2".equals(namespaceUri)) return "";
		else if ("http://www.w3.org/2000/09/xmldsig#".equals(namespaceUri)) return "ds";
		else if ("http://uri.etsi.org/01903/v1.3.2#".equals(namespaceUri)) return "xades";
			 
		return suggestion; 
	}

	@Override
	public String[] getPreDeclaredNamespaceUris() {
		return new String[] { };
	}

}
