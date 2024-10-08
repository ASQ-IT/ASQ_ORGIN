package asq.pos.loyalty.stc.tender;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
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

import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceResponse;
import asq.pos.zatca.AsqZatcaConstant;
import dtv.data2.access.DataFactory;
import dtv.data2.access.exception.PersistenceException;
import dtv.pos.common.OpExecutionException;
import dtv.pos.framework.scope.TransactionScope;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import dtv.xst.dao.trn.IPosTransactionProperty;
import oracle.dss.dataView.datacache.Map;

public class AsqStcHelper {

	private static final Logger LOG = LogManager.getLogger(AsqStcHelper.class);

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
		case "9999":
		case "9":
			return "ASQ_STC_INVALID_MOBILE_ERROR";
		case "1019":
			return "ASQ_STC_WRONG_MOBILE_ERROR";
		case "1017":
			return "ASQ_STC_NOTALLOWED_MOBILE_ERROR";
		case "1022":
			return "ASQ_STC_NON_STC_CUST_ERROR";
		case "1070":
			return "ASQ_STC_REDEMTION_NOTALLOWED_ERROR";
		case "1007":
			return "ASQ_STC_PIN_INVALID_ERROR";
		case "1824":
			return "ASQ_STC_AMOUNT_INVALID_ERROR";
		case "1012":
			return "ASQ_STC_REDEMPTION_EXCEEDED_ERROR";
		case "1014":
			return "ASQ_STC_INSUFFICIENT_BALANCE_ERROR";
		case "1015":
			return "ASQ_STC_OTP_USED_ERROR";
		case "1030":
			return "ASQ_STC_OTP_EXPIRED_ERROR";
		case "2007":
			return "ASQ_STC_OTP_ATTEMPT_ERROR";
		case "907":
			return "ASQ_STC_REFREQDATE_INVALID_ERROR";
		case "1018":
			return "ASQ_STC_REDEMPTION_NOTFOUND_ERROR";
		case "1040":
			return "ASQ_STC_REDEMPTION_REVERESED_ERROR";
		case "2311":
			return "ASQ_STC_TECHNICAL_ERROR";
		case "1020":
			return "ASQ_STC_AMOUNT_LESS_ERROR";
		case "914":
			return "ASQ_STC_NOT_QITAF_SUBSCRIBER_ERROR";
		case "1046":
			return "ASQ_STC_REWARD_NOTALLOWED_ERROR";
		case "1047":
			return "ASQ_STC_REWARD_ALREADY_SUCCEEDED_ERROR";
		case "1829":
			return "ASQ_STC_AMOUNT_ERROR";
		case "9179":
			return "ASQ_STC_AMOUNT_EXCEEDED_ERROR";
		case "1041":
			return "ASQ_STC_REFUND_PERIOD_ERROR";
		case "1048":
			return "ASQ_STC_REWARD_UPDATE_ERROR";
		case "1045":
			return "ASQ_STC_REWARD_CANCEL_ERROR";
		case "1049":
			return "ASQ_STC_REWARD_REVERSAL_ERROR";
		case "2510":
			return "ASQ_STC_REWARD_AMOUNT_GREATER_ERROR";
		case "1044":
			return "ASQ_STC_REWARD_AMOUNT_GREATER_2_ERROR";
		default:
			return "ASQ_STC_TECHNICAL_ERROR";
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
