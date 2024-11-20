package asq.pos.loyalty.mokafaa.tender.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonAutoDetect;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;
import com.oracle.shaded.fasterxml.jackson.annotation.PropertyAccessor;
import com.oracle.shaded.fasterxml.jackson.core.JsonProcessingException;
import com.oracle.shaded.fasterxml.jackson.databind.DeserializationFeature;
import com.oracle.shaded.fasterxml.jackson.databind.JsonMappingException;
import com.oracle.shaded.fasterxml.jackson.databind.MapperFeature;
import com.oracle.shaded.fasterxml.jackson.databind.ObjectMapper;

import asq.pos.zatca.AsqZatcaConstant;
import dtv.data2.access.DataFactory;
import dtv.data2.access.exception.PersistenceException;
import dtv.pos.common.OpExecutionException;
import dtv.pos.framework.scope.TransactionScope;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trn.IPosTransactionProperty;

public class AsqMokafaaHelper {

	private static final Logger LOG = LogManager.getLogger(AsqMokafaaHelper.class);

	/**
	 * This class checks helps to implement all the required methods for STC API
	 */

	@Inject
	protected TransactionScope _transactionScope;

	/**
	 * This method converts API response to JSON object for Earn API
	 * 
	 * @param request object
	 * @return response result
	 */

	@SuppressWarnings("deprecation")
	public String convertTojson(Object argObj) {
		String result = null;
		try {
			ObjectMapper objMapper = new ObjectMapper();
			objMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
			objMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);
			objMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			objMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
			objMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
			result = objMapper.writeValueAsString(argObj);
			LOG.info("STC API Helper method convertTojson values:", result);
		} catch (Exception exception) {
			LOG.error("Exception caught in converting response object to json:", exception);
		}
		return result;
	}

	/**
	 * This method converts JSON to POJO object for Earn API
	 * 
	 * @param JSON Object
	 * @return
	 */

	public <T> T convertJSONToPojo(String argJSONResponse, Class<T> arObjectToConvert)
			throws JsonMappingException, JsonProcessingException {
		if (argJSONResponse != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			LOG.info("STC API Helper method convertJSONToPojo values:");
			return objectMapper.readValue(argJSONResponse, arObjectToConvert);
		}
		return null;
	}

	/**
	 * This method maps all errors received from STC API
	 * 
	 * @param Error Code
	 * @return Error prompts
	 */

	public String mapError(String code) {
		switch (code) {
		case "101":
			return "VALIDATION_FAILED";
		case "206":
			return "STORE_IS_INVALID";
		case "207":
			return "DUPLICATE_MOBILE_NUMBER";
		case "209":
			return "USER_DOES_NOT_EXIST";
		case "205":
			return "LOWER_THAN_THE_MINIMUM_BALANCE";
		case "210":
			return "OTP_IS_STILL_VALID";
		case "204":
			return "BALANCE_INSUFFICIENT";
		case "301":
			return "INVALID_OTP";
		case "303":
			return "OTP_EXPIRED";
		case "304":
			return "TRANSACTION_FAILED";
		case "401":
			return "TIME_EXCEEDED";
		default:
			return "GENERAL_API_ERROR";
		}
	}

	/**
	 * This method generates globalID for STC API
	 * 
	 * @param
	 * @return generated globalID for STC API request
	 */

	public String generateGlobalId() {
		String globalId = System.getProperty("asq.stc.branchid");
		Random rand = new Random();
		String finalpart = "";
		for (int i = 0; i < 3; i++) {
			String randomNumber = String.format("%04d%n", rand.nextInt(9999));
			globalId = globalId + "-" + randomNumber.trim();
			finalpart = finalpart + randomNumber.trim();
		}
		globalId = globalId + "-" + finalpart;
		LOG.info("STC API Generated Global ID:" + globalId);
		return globalId;
	}
}
