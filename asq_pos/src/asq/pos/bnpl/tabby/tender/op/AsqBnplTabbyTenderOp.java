package asq.pos.bnpl.tabby.tender.op;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyAmountObj;
import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyDetailsObj;
import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyDisAmtObj;
import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyErrorDesc;
import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyItemsObj;
import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyOrderDetailsObj;
import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyPaymentObj;
import asq.pos.bnpl.tabby.tender.service.AsqSubmitBnplTabbyServiceRequest;
import asq.pos.bnpl.tabby.tender.service.AsqSubmitBnplTabbyServiceResponse;
import asq.pos.bnpl.tabby.tender.service.IAsqBnplTabbyServices;
import asq.pos.bnpl.tabby.tender.service.IAsqSubmitBnplTabbyServiceRequest;
import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraDisAmtObj;
import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.stc.tender.AsqStcHelper;
import asq.pos.zatca.AsqZatcaConstant;
import dtv.data2.access.DataFactory;
import dtv.i18n.IFormattable;
import dtv.i18n.TranslationHelper;
import dtv.pos.common.OpChainKey;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.validation.IValidationResultList;
import dtv.xst.dao.crm.IParty;
import dtv.xst.dao.itm.MerchandiseHierarchyId;
import dtv.xst.dao.itm.impl.MerchandiseHierarchyModel;
import dtv.xst.dao.trl.IRetailPriceModifier;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import dtv.xst.dao.trn.IPosTransaction;

/**
 * @author RA20221457
 *
 */

public class AsqBnplTabbyTenderOp extends AbstractFormOp<AsqBnplTabbyEditModel> {
	private static final Logger LOG = LogManager.getLogger(AsqBnplTabbyTenderOp.class);

	@Inject
	protected Provider<IAsqBnplTabbyServices> tabbyService;

	@Inject
	AsqStcHelper asqStcHelper;
	private String custMobileNumber = "";

	@Override
	protected AsqBnplTabbyEditModel createModel() {
		return new AsqBnplTabbyEditModel();
	}

	@Override
	protected String getFormKey() {
		return "ASQ_CAP_CUST_MOBILE_NO";
	}

	/**
	 * This method checks whether customer is linked to transaction or not
	 * 
	 * @param
	 * @return
	 */

	protected IOpResponse handleInitialState() {
		AsqBnplTabbyEditModel editModel = getModel();
		try {
			IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
			if (trans != null && trans.getCustomerParty() != null
					&& trans.getTransactionTypeCode().equalsIgnoreCase("RETAIL_SALE")) {
				LOG.info("TABBY API Mobile number form execution Customer is Linked to Transaction:");
				IParty argParty = trans.getCustomerParty();
				custMobileNumber = argParty.getTelephone3();
				editModel.setCustMobileNumber(custMobileNumber);
				_transactionScope.setValue(AsqValueKeys.ASQ_MOBILE_NUMBER, custMobileNumber);
				return super.handleInitialState();
			}
		} catch (Exception ex) {
			LOG.info(
					"TABBY API Mobile number form execution exception customer is not available in the transaction and Trans object is null:");
		}
		return super.handleInitialState();
	}

	/**
	 * This method handles the data operation after submitting the mobile number and
	 * calls request preparer
	 * 
	 * @param
	 * @return
	 */

	@Override
	protected IOpResponse handleDataAction(IXstDataAction argAction) {

		if (XstDataActionKey.ACCEPT.equals(argAction.getActionKey())) {
			IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
			AsqBnplTabbyEditModel editModel = this.getModel();
			IValidationResultList results = validateForm(editModel);
			if (!results.isValid()) {
				return super.handleDataAction(argAction);
			}
			if (editModel.getCustMobileNumber() != null) {
				custMobileNumber = editModel.getCustMobileNumber();
				if (custMobileNumber.length() <= 14 && custMobileNumber.length() >= 9) {
					_transactionScope.setValue(AsqValueKeys.ASQ_MOBILE_NUMBER, custMobileNumber);
				} else {
					return HELPER.getPromptResponse("ASQ_TAMARA_MOBILE_NUMBER_ERROR");
				}
			}
			LOG.debug("TABBY API Mobile number captured :" + custMobileNumber);
			if (custMobileNumber != null && !custMobileNumber.equals("")) {
				LOG.info("Process of TABBY tender starts here");
				if (null != trans.getCustomerParty() && !(this._transactionScope
						.getValue(AsqValueKeys.ASQ_MOBILE_NUMBER).equals(custMobileNumber))) {
					IParty info = trans.getCustomerParty();
					info.setTelephone3(custMobileNumber);
					_transactionScope.setValue(AsqValueKeys.ASQ_MOBILE_NUMBER, custMobileNumber);
					LOG.info(
							"TABBY API setting updated customer mobile number to transaction, this will be udpated once the transaciton is completed");
				}
			} else if (custMobileNumber == null || custMobileNumber.equals("")) {
				LOG.debug("TABBY API customer mobile number field is null :");
				return super.handleDataAction(argAction);
			}
			LOG.info("TABBY API request preparer for service call starts here: ");
			return requestPreparerStoreSession(trans, custMobileNumber);
		}
		LOG.info("Action key is not equal to ACCEPT, rolling back to Sale Screen :" + argAction.getActionKey());
		return this.HELPER.getOpChainRollBackRequest();
	}

	/**
	 * This method implements the requestPreparerStoreSession method for preparing
	 * the request attributes
	 * 
	 * @param trans
	 * @return requestPreparerStoreCheckOutSession submission to TAMARA Service
	 *         Handler
	 */

	private IOpResponse requestPreparerStoreSession(IRetailTransaction trans, String custMobileNumber) {

		AsqSubmitBnplTabbyServiceResponse asqSubmitBnplTabbyServiceResponse;
		IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest = new AsqSubmitBnplTabbyServiceRequest();
		AsqBnplTabbyPaymentObj payment = new AsqBnplTabbyPaymentObj();
		AsqBnplTabbyDetailsObj asqBnplTabbyDetailsObj = new AsqBnplTabbyDetailsObj();
		AsqBnplTabbyOrderDetailsObj asqBnplTabbyOrderDetailsObj = new AsqBnplTabbyOrderDetailsObj();
		LocalTime time = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		String timeString = time.format(formatter);
		asqBnplTabbyDetailsObj.setPhone(custMobileNumber);
		asqBnplTabbyOrderDetailsObj.setReference_id(Long.toString(trans.getRetailLocationId()).concat("-")
				.concat(Long.toString(trans.getTransactionSequence())).concat("-").concat(timeString));
		payment.setAmount(trans.getTotal().toString());
		payment.setCurrency(trans.getRetailTransactionLineItems().get(0).getCurrencyId());
		LOG.error("Currency :" + trans.getRetailTransactionLineItems().get(0).getCurrencyId());
		//payment.setCurrency("SAR");
		List<ISaleReturnLineItem> saleItemList = trans.getLineItems(ISaleReturnLineItem.class);
		ArrayList<AsqBnplTabbyItemsObj> asqBnplTabbyItemsList = new ArrayList<AsqBnplTabbyItemsObj>();
		for (ISaleReturnLineItem lineItem : saleItemList) {
			AsqBnplTabbyItemsObj asqBnplTabbyItemsObj = new AsqBnplTabbyItemsObj();
			AsqBnplTabbyAmountObj itemTotalAmnt = new AsqBnplTabbyAmountObj();
			asqBnplTabbyItemsObj.setCategory(lineItem.getItem().getItemTypeCode());
			if (lineItem.getMerchLevel1Id() != null) {
				MerchandiseHierarchyId id = new MerchandiseHierarchyId();
				id.setHierarchyId(lineItem.getMerchLevel1Id());
				MerchandiseHierarchyModel result = DataFactory.getObjectByIdNoThrow(id);
				if (result != null) {
					String desc = TranslationHelper.translate(Locale.getDefault(), result.getDescription());
					asqBnplTabbyItemsObj.setCategory(desc);
				}
			}
			AsqBnplTabbyDisAmtObj itemDiscAmnt = new AsqBnplTabbyDisAmtObj();
			asqBnplTabbyItemsObj.setItemId(lineItem.getItemId());
			asqBnplTabbyItemsObj.setTitle(lineItem.getItemDescription());
			asqBnplTabbyItemsObj.setReference_id(lineItem.getLineItemSequence());
			asqBnplTabbyItemsObj.setQuantity(lineItem.getQuantity());
			asqBnplTabbyItemsObj.setUnit_price(lineItem.getUnitPrice().toString());
			List<IRetailPriceModifier> discountRates = lineItem.getRetailPriceModifiers();
			BigDecimal discamount = BigDecimal.ZERO;
			for (IRetailPriceModifier discountRate : discountRates) {
				if (!discountRate.getVoid() && discountRate.getExtendedAmount() != null) {
					discamount = discamount.add(discountRate.getExtendedAmount());
				}
			}
			itemDiscAmnt.setAmount(discamount);
			//itemDiscAmnt.setCurrency("SAR");
			 itemDiscAmnt.setCurrency(trans.getRetailTransactionLineItems().get(0).getCurrencyId());
			asqBnplTabbyItemsObj.setDiscount_amount(itemDiscAmnt);
			itemTotalAmnt.setAmount(lineItem.getGrossAmount());
			//itemTotalAmnt.setCurrency("SAR");
			itemTotalAmnt.setCurrency(trans.getRetailTransactionLineItems().get(0).getCurrencyId());
			asqBnplTabbyItemsObj.setTotal_amount(itemTotalAmnt);
			asqBnplTabbyItemsList.add(asqBnplTabbyItemsObj);
		}
		asqBnplTabbyOrderDetailsObj.setItems(asqBnplTabbyItemsList);
		payment.setBuyer(asqBnplTabbyDetailsObj);
		payment.setOrder(asqBnplTabbyOrderDetailsObj);
		asqSubmitBnplTabbyServiceRequest.setPayment(payment);
		asqSubmitBnplTabbyServiceRequest.setMerchant_code(System.getProperty("asq.bnpl.tender.tabby.merchant.code"));
		asqSubmitBnplTabbyServiceRequest.setLang(System.getProperty("asq.bnpl.tender.language"));
		LOG.info("Calling CreateSession API");
		asqSubmitBnplTabbyServiceResponse = createSession(asqSubmitBnplTabbyServiceRequest);
		// for testing
		/*
		 * if (null != asqSubmitBnplTabbyServiceResponse &&
		 * !asqSubmitBnplTabbyServiceResponse.getStatus().equalsIgnoreCase("rejected")
		 * && asqSubmitBnplTabbyServiceResponse.getError()==null) { if
		 * (!asqSubmitBnplTabbyServiceResponse.getStatus().equalsIgnoreCase("SSL")) {
		 * LOG.info("Tabby Response Payment Link: " +
		 * asqSubmitBnplTabbyServiceResponse.getConfiguration()
		 * .getAvailable_products().getInstallments().get(0).getWeb_url()); } }
		 */
		LOG.info("Returned from CreateSession API");
		return validateSessionResponseAndStoreDataInDB(asqSubmitBnplTabbyServiceResponse);
	}

	private IOpResponse validateSessionResponseAndStoreDataInDB(AsqSubmitBnplTabbyServiceResponse response) {
		if (null != response && null != response.getError() && response.getStatus() != null) {
			return handleServiceError(response);
		} else if (null == response) {
			return technicalErrorScreen("TABBY API::::: Service has null response");
		} else if (response.getStatus().equalsIgnoreCase("rejected")) {
			return HELPER.getPromptResponse("ASQ_TABBY_PAYMENT_REJECTED");
		}
		IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
		HashMap<String, String> responseList = new HashMap<String, String>();
		responseList.put("sessionID", response.getId());
		responseList.put("paymentID", response.getPayment().getId());
		responseList.put("paymentCreationDate", response.getPayment().getCreated_at());
		responseList.put("paymentExpiryDate", response.getPayment().getExpires_at());
		responseList.put("paymentStatus", response.getPayment().getStatus());
		LOG.info("TABBY API saving response to DB started");
		_transactionScope.setValue(AsqValueKeys.ASQ_TABBY_PMNT_AMNT, response.getPayment().getAmount());
		saveTabbyResponseToDB(trans, responseList);
		LOG.info("TABBY API saving response to DB successfull");
		_transactionScope.setValue(AsqValueKeys.TABBY_RESPONSE, response.getId());
		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_TABBY_PAYMENT"));
	}

	public void saveTabbyResponseToDB(IPosTransaction originalPosTrx, HashMap<String, String> responseList) {
		IPosTransaction trans = this._transactionScope.getTransaction();
		if (responseList != null) {
			trans.setStringProperty("ASQ_TABBY_SESSION_ID", responseList.get("sessionID"));
			trans.setStringProperty("ASQ_TABBY_PAYMENT_ID", responseList.get("paymentID"));
			trans.setStringProperty("ASQ_TABBY_PAYMENT_CRTN_DATE", responseList.get("paymentCreationDate"));
			trans.setStringProperty("ASQ_TABBY_PAYMENT_EXPRY_DATE", responseList.get("paymentExpiryDate"));
			trans.setStringProperty("ASQ_TABBY_PAYMENT_STATUS", responseList.get("paymentStatus"));
			_transactionScope.setValue(AsqValueKeys.ASQ_TABBY_SESSION_ID, responseList.get("sessionID"));
			_transactionScope.setValue(AsqValueKeys.ASQ_TABBY_PMNT_ID, responseList.get("paymentID"));
			_transactionScope.setValue(AsqValueKeys.ASQ_TABBY_PMNT_CRTN_DATE, responseList.get("paymentCreationDate"));
			_transactionScope.setValue(AsqValueKeys.ASQ_TABBY_PMNT_EXPRY_DATE, responseList.get("paymentExpiryDate"));
			_transactionScope.setValue(AsqValueKeys.ASQ_TABBY_SESSION_ID, responseList.get("sessionID"));
			_transactionScope.setValue(AsqValueKeys.ASQ_TABBY_PMNT_STATUS, responseList.get("paymentStatus"));
			LOG.info("Tabby API response values have been successfully updated in Trans properties");
		}
	}

	public AsqSubmitBnplTabbyServiceResponse createSession(
			IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest) {
		AsqSubmitBnplTabbyServiceResponse response = new AsqSubmitBnplTabbyServiceResponse();
		try {
			LOG.info("Calling createSession API");
			response = (AsqSubmitBnplTabbyServiceResponse) tabbyService.get()
					.createSession(asqSubmitBnplTabbyServiceRequest);

		} catch (Exception ex) {
			LOG.info("Error during createSession API");
		}
		return response;
	}

	public AsqSubmitBnplTabbyServiceResponse cancelSession(
			IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest) {
		AsqSubmitBnplTabbyServiceResponse response = new AsqSubmitBnplTabbyServiceResponse();
		try {
			LOG.info("Calling cancelSession API");
			response = (AsqSubmitBnplTabbyServiceResponse) tabbyService.get()
					.cancelSession(asqSubmitBnplTabbyServiceRequest);
		} catch (Exception ex) {
			LOG.info("Error during createSession API");
		}
		return response;
	}

	/**
	 * This method return Technical Error
	 * 
	 * @param argModel
	 * @return Error
	 */

	private IOpResponse technicalErrorScreen(String message) {
		IFormattable[] args = new IFormattable[2];
		args[1] = _formattables.getSimpleFormattable(message);
		LOG.info("TABBY REDEEM API::::: " + message);
		return HELPER.getPromptResponse("ASQ_TABBY_TECHNICAL_ERROR", args);
	}

	/**
	 * This method handles the TABBY service errors
	 * 
	 * @param asqServiceResponse
	 * @return Error Prompts
	 */

	public IOpResponse handleServiceError(AsqSubmitBnplTabbyServiceResponse asqServiceResponse) {
		IFormattable[] args = new IFormattable[2];
		AsqBnplTabbyErrorDesc error = asqServiceResponse.getError();
		args[0] = _formattables.getSimpleFormattable(error.getErrorType());
		args[1] = _formattables.getSimpleFormattable(error.getError());
		String errorConstant = asqStcHelper.mapTabbyErrors(asqServiceResponse.getStatus());
		LOG.info("Error From TABBY API::::: " + args[0] + " - " + args[1]);
		LOG.info("Error Message Generated By Xstore based on TABBY API Response::::: " + error.getErrorType());
		return HELPER.getPromptResponse(errorConstant, args);
	}
}
