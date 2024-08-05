package asq.pos.loyalty.stc.tender;

import com.oracle.shaded.fasterxml.jackson.core.JsonProcessingException;
import com.oracle.shaded.fasterxml.jackson.databind.DeserializationFeature;
import com.oracle.shaded.fasterxml.jackson.databind.JsonMappingException;
import com.oracle.shaded.fasterxml.jackson.databind.MapperFeature;
import com.oracle.shaded.fasterxml.jackson.databind.ObjectMapper;

import asq.pos.loyalty.stc.tender.service.AsqSTCLoyaltyServiceResponse;

public class AsqStcHelper {

	public String convertTojson(Object argObj) {
		String result = null;
		try {
			ObjectMapper objMapper = new ObjectMapper();
			objMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
			objMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);
			result = objMapper.writeValueAsString(argObj);

		} catch (Exception ex) {
			// _logger.error("Exception caught in converting object to json:", ex);
		}
		return result;
	}

	public <T> T convertJSONToPojo(String argJSONResponse, Class<T> arObjectToConvert) throws JsonMappingException, JsonProcessingException {
		if (argJSONResponse != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(argJSONResponse, arObjectToConvert);
		}
		return null;
	}

	public boolean isSTCResponseValid(AsqSTCLoyaltyServiceResponse response) {
		if (null != response) {
			if ("0".equals(response.getCode())) {
				return true;
			}
		}
		return false;
	}

	// public void generateTransactionid() {
	// IRetailTransaction trans = (IRetailTransaction)
	// this._transactionScope.getTransaction();
	// }

}
