package asq.pos.customer;

import java.util.Map;
import dtv.pos.customer.CustomerHelper;
import dtv.xst.dao.crm.IParty;

public class AsqCustomerHelper extends CustomerHelper {

	@Override
	public IParty createParty(Map<String, Object> argKnownFields, long argRetailLocationId) {
		IParty asqModified = super.createParty(argKnownFields, argRetailLocationId);
		Object[] keys = argKnownFields.keySet().toArray();
		for (Object element : keys) {
			String fieldKey = element.toString();
			Object value = argKnownFields.get(element);
			if (value != null)
				if ("telephone".equals(fieldKey)) {
					asqModified.setTelephone3((String) value);
					asqModified.setTelephone1(null);
				}
		}
		return asqModified;
	}
}
