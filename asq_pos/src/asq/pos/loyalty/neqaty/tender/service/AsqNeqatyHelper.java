package asq.pos.loyalty.neqaty.tender.service;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

public class AsqNeqatyHelper {

	private static final Logger LOG = LogManager.getLogger(AsqNeqatyHelper.class);

	/**
	 * This class checks helps to implement all the required methods for STC API
	 */

	/**
	 * This method maps all errors received from Neqaty API
	 * 
	 * @param Error Code
	 * @return Error prompts
	 */

	public String mapError(int code) {
		switch (code) {
		case -52:
		case -53:
		case -59:
		case -67:
		case -83:
		case -99:
		case -407:
		case -500:
			return "ASQ_NEQATY_TECHNICAL_ERROR";
		case -518:
			return "ASQ_NEQATY_STORNO_INVALID_REF";
		case -519:
			return "ASQ_NEQATY_STORNO_INVALID_AMOUNT";
		case -523:
			return "ASQ_NEQATY_TERMINAL_MISMATCH";
		case -524:
			return "ASQ_NEQATY_DUPLICATE_REQ";
		case -525:
			return "ASQ_NEQATY_INVALID_MSISDN";
		case -527:
			return "ASQ_NEQATY_APP_BLOCKED";
		case -529:
			return "ASQ_NEQATY_BRANCH_LIMIT";
		case -554:
			return "ASQ_NEQATY_CID_MSISDN_MISMATCH";
		case -555:
			return "ASQ_NEQATY_CID_PIN_MISMATCH";
		case -558:
			return "ASQ_NEQATY_FRAUD_DOUBLE_MSISDN";
		case -572:
			return "ASQ_NEQATY_INQ_NOT_AUTH";
		case -573:
			return "ASQ_NEQATY_INQ_CANCELED";
		case -576:
			return "ASQ_NEQATY_REDEEM_NOT_TRANSFERED";
		case -584:
			return "ASQ_NEQATY_CUSTOMER_DOES_NOT_EXIST";
		case -591:
			return "ASQ_NEQATY_UNKNOWN_ERROR";
		case -611:
			return "ASQ_NEQATY_WRONG_TERMINAL_TYPE";
		case -614:
			return "ASQ_NEQATY_WRONG_REFERENCE_NUMBER";
		case -705:
			return "ASQ_NEQATY_COLLECT_NOT_TRANSFERED";
		case -706:
			return "ASQ_NEQATY_COLLECT_REVERSE_AMOUNT";
		case -721:
			return "ASQ_NEQATY_INVALID_PARAMETERS";
		case -722:
			return "ASQ_NEQATY_INVALID_CREDENTIALS";
		case -829:
			return "ASQ_NEQATY_LAST_REDEEM_STIL_PENDING";
		case -850:
			return "ASQ_NEQATY_NEQATY_MALFORMED_XML";
		case -851:
			return "ASQ_NEQATY_NEQATY_INVALID_MSISDN";
		case -852:
			return "ASQ_NEQATY_NEQATY_INVALID_ITEM_CODE";
		case -853:
			return "ASQ_NEQATY_NEQATY_INVALID_REF_NUMBER";
		case -854:
			return "ASQ_NEQATY_NEQATY_INVALID_PARTNER_ID";
		case -855:
			return "ASQ_NEQATY_NEQATY_MSISDN_DOESNT_EXIST";
		case -856:
			return "ASQ_NEQATY_NEQATY_MSISDN_NOT_DEFINED";
		case -857:
			return "ASQ_NEQATY_NEQATY_MSISDN_BARRED_ON_NETWORK";
		case -858:
			return "ASQ_NEQATY_NEQATY_CUST_PKG_NOT_ELIGIBLE";
		case -859:
			return "ASQ_NEQATY_NEQATY_ALLOWD_REDEMPTS_EXCEEDED";
		case -860:
			return "ASQ_NEQATY_NEQATY_NOT_ENOUGH_POINTS";
		case -861:
			return "ASQ_NEQATY_NEQATY_ITEM_NOT_IN_CUST_TIER";
		case -862:
			return "ASQ_NEQATY_NEQATY_REF_NUMBER_NOT_FOUND";
		case -863:
			return "ASQ_NEQATY_NEQATY_REF_TRANS_TOO_OLD";
		case -880:
			return "ASQ_NEQATY_CUST_ID_INVALID";
		case -883:
			return "ASQ_NEQATY_MSISDN_NOT_PREPAID";
		case -884:
			return "ASQ_NEQATY_CAN_NOT_ROLLBACK";
		case -885:
			return "ASQ_NEQATY_TRANS_DATA_NOT_FOUND";
		default:
			return "ASQ_NEQATY_TECHNICAL_ERROR";
		}
	}

	/**
	 * This method generated transactionReference for Neqaty API
	 * 
	 * @param
	 * @return generated transactionReference for Neqaty API request
	 */

	public String generateTransactionReference() {
		Random rand = new Random();
		String transactionReference = "asq_neqaty_" + String.format("%04d%n", rand.nextInt(99999)).trim() + "_"
				+ DateTime.now().toString();
		LOG.info("NEQATY API Generated Transaction Reference:" + transactionReference);
		return transactionReference;
	}
}
