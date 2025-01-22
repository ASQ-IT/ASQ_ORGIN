package asq.pos.bnpl.tamara.tender.op;

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

import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraAddDataObj;
import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraAmountObj;
import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraDisAmtObj;
import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraItemObj;
import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceRequest;
import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceResponse;
import asq.pos.bnpl.tamara.tender.service.AsqTamaraErrorDesc;
import asq.pos.bnpl.tamara.tender.service.IAsqBnplTamaraServices;
import asq.pos.bnpl.tamara.tender.service.IAsqSubmitBnplTamraServiceRequest;
import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.stc.tender.AsqStcHelper;
import dtv.data2.access.DataFactory;
import dtv.i18n.FormattableFactory;
import dtv.i18n.IFormattable;
import dtv.i18n.TranslationHelper;
import dtv.pos.common.OpChainKey;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.security.StationState;
import dtv.pos.iframework.validation.IValidationResultList;
import dtv.xst.dao.crm.IParty;
import dtv.xst.dao.itm.MerchandiseHierarchyId;
import dtv.xst.dao.itm.impl.MerchandiseHierarchyModel;
import dtv.xst.dao.trl.IRetailPriceModifier;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import dtv.xst.dao.trn.IPosTransaction;

public class AsqBnplTamaraTenderOp extends AbstractFormOp<AsqBnplTamaraEditModel> {

	private static final Logger LOG = LogManager.getLogger(AsqBnplTamaraTenderOp.class);

	/**
	 * This class extends the Xstore Standard form class to handle all actions
	 * related to Mobile number field & TAMARA API calls
	 */

	@Inject
	protected Provider<IAsqBnplTamaraServices> tamaraService;

	@Inject
	AsqStcHelper asqStcHelper;
	private String custMobileNumber = "";

	@Inject
	protected StationState _stationState;

	@Inject
	protected FormattableFactory FF;

	@Override
	protected AsqBnplTamaraEditModel createModel() {
		return new AsqBnplTamaraEditModel();
	}

	@Override
	protected String getFormKey() {
		return "ASQ_CAP_CUST_MOBILE_NO";
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
			AsqBnplTamaraEditModel editModel = this.getModel();
			IValidationResultList results = validateForm(editModel);
			if (!results.isValid()) {
				return super.handleDataAction(argAction);
			}
			if (editModel.getCustMobileNumber() != null) {
				custMobileNumber = editModel.getCustMobileNumber();
				if (custMobileNumber.length() <= 14 && custMobileNumber.length() >= 9) {
					_transactionScope.setValue(AsqValueKeys.ASQ_MOBILE_NUMBER, custMobileNumber);
					LOG.debug("TAMARA API Mobile number captured :" + custMobileNumber);
				} else {
					return HELPER.getPromptResponse("ASQ_TAMARA_MOBILE_NUMBER_ERROR");
				}
			}
			if (custMobileNumber != null && !custMobileNumber.equals("")) {
				LOG.info("Process of TAMARA tender starts here");
				if (null != trans.getCustomerParty() && !(this._transactionScope
						.getValue(AsqValueKeys.ASQ_MOBILE_NUMBER).equals(custMobileNumber))) {
					IParty info = trans.getCustomerParty();
					info.setTelephone3(custMobileNumber);
					_transactionScope.setValue(AsqValueKeys.ASQ_MOBILE_NUMBER, custMobileNumber);
					LOG.info(
							"TAMARA API setting updated customer mobile number to transaction, this will be udpated once the transaciton is completed");
				}
			} else if (custMobileNumber == null || custMobileNumber.equals("")) {
				LOG.debug("TAMARA API customer mobile number field is null :");
				return super.handleDataAction(argAction);
			}
			LOG.info("TAMARA API request preparer for service call starts here: ");
			return requestPreparerStoreCheckOutSession(trans);
		}
		LOG.info("Action key is not equal to ACCEPT, rolling back to Sale Screen :" + argAction.getActionKey());
		return this.HELPER.getOpChainRollBackRequest();
	}

	/**
	 * This method checks whether customer is linked to transaction or not
	 *
	 * @param
	 * @return
	 */

	@Override
	protected IOpResponse handleInitialState() {
		AsqBnplTamaraEditModel editModel = getModel();
		try {
			IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
			if (trans != null && trans.getCustomerParty() != null
					&& trans.getTransactionTypeCode().equalsIgnoreCase("RETAIL_SALE")) {
				LOG.info("TAMARA API Mobile number form execution Customer is Linked to Transaction:");
				IParty argParty = trans.getCustomerParty();
				custMobileNumber = argParty.getTelephone3();
				editModel.setCustMobileNumber(custMobileNumber);
				_transactionScope.setValue(AsqValueKeys.ASQ_MOBILE_NUMBER, custMobileNumber);
				return super.handleInitialState();
			}
		} catch (Exception ex) {
			LOG.info(
					"TAMARA API Mobile number form execution exception customer is not available in the transaction and Trans object is null:");
		}
		return super.handleInitialState();
	}

	/**
	 * This method implements the requestPreparerStoreCheckOutSession method for
	 * preparing the request attributes
	 *
	 * @param trans
	 * @return requestPreparerStoreCheckOutSession submission to TAMARA Service
	 *         Handler
	 */

	private IOpResponse requestPreparerStoreCheckOutSession(IRetailTransaction trans) {

		AsqSubmitBnplTamraServiceResponse asqSubmitBnplTamraServiceResponse;
		IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest = new AsqSubmitBnplTamraServiceRequest();
		AsqBnplTamaraAmountObj asqBnplTamaraAmountObj = new AsqBnplTamaraAmountObj();
		asqBnplTamaraAmountObj.setAmount(trans.getTotal());
		//asqBnplTamaraAmountObj.setCurrency("SAR");
		asqBnplTamaraAmountObj.setCurrency(trans.getRetailTransactionLineItems().get(0).getCurrencyId());
		List<ISaleReturnLineItem> saleItemList = trans.getLineItems(ISaleReturnLineItem.class);
		ArrayList<AsqBnplTamaraItemObj> itemList = new ArrayList<>();
		for (ISaleReturnLineItem lineItem : saleItemList) {
			AsqBnplTamaraItemObj asqBnplTamaraItemObj = new AsqBnplTamaraItemObj();
			AsqBnplTamaraAmountObj itemTotalAmnt = new AsqBnplTamaraAmountObj();
			AsqBnplTamaraDisAmtObj itemDiscAmnt = new AsqBnplTamaraDisAmtObj();
			// Change to MerchITemType code for Category.
			asqBnplTamaraItemObj.setType(lineItem.getItem().getItemTypeCode());
			if (lineItem.getMerchLevel1Id() != null) {
				MerchandiseHierarchyId id = new MerchandiseHierarchyId();
				id.setHierarchyId(lineItem.getMerchLevel1Id());
				MerchandiseHierarchyModel result = DataFactory.getObjectByIdNoThrow(id);
				if (result != null) {
					String desc = TranslationHelper.translate(Locale.getDefault(), result.getDescription());
					asqBnplTamaraItemObj.setType(desc);
				}
			}
			asqBnplTamaraItemObj.setSku(lineItem.getItemId());
			asqBnplTamaraItemObj.setName(lineItem.getItemDescription());
			asqBnplTamaraItemObj.setReference_id(lineItem.getLineItemSequence());
			asqBnplTamaraItemObj.setQuantity(lineItem.getQuantity());
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
			asqBnplTamaraItemObj.setDiscount_amount(itemDiscAmnt);
			itemTotalAmnt.setAmount(lineItem.getGrossAmount());
			itemTotalAmnt.setCurrency(trans.getRetailTransactionLineItems().get(0).getCurrencyId());
			asqBnplTamaraItemObj.setTotal_amount(itemTotalAmnt);
			itemList.add(asqBnplTamaraItemObj);
		}
		asqSubmitBnplTamraServiceRequest.setTotal_amount(asqBnplTamaraAmountObj);
		asqSubmitBnplTamraServiceRequest.setItems(itemList);
		asqSubmitBnplTamraServiceRequest.setPhone_number(custMobileNumber);
		LocalTime time = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		String timeString = time.format(formatter);
		asqSubmitBnplTamraServiceRequest.setOrder_reference_id(Long.toString(trans.getRetailLocationId()).concat("-")
				.concat(Long.toString(trans.getTransactionSequence())).concat("-").concat(timeString));
		AsqBnplTamaraAddDataObj storeData = new AsqBnplTamaraAddDataObj();
		storeData.setStore_code(Long.toString(trans.getRetailLocationId()));
		asqSubmitBnplTamraServiceRequest.setAdditional_data(storeData);
		asqSubmitBnplTamraServiceRequest.setLocale(System.getProperty("asq.bnpl.tender.locale"));
		asqSubmitBnplTamraServiceResponse = createInStoreCheckoutSession(asqSubmitBnplTamraServiceRequest);
		System.out.println("CheckoutID: " + asqSubmitBnplTamraServiceResponse.getCheckout_id());
		System.out.println("OrderID: " + asqSubmitBnplTamraServiceResponse.getOrder_id());
		System.out.println("CheckoutLink: " + asqSubmitBnplTamraServiceResponse.getCheckout_deeplink());
		LOG.info("TAMARA API response PaymentLink: " + asqSubmitBnplTamraServiceResponse.getCheckout_deeplink());
		LOG.info("TAMARA API response CheckoutID: " + asqSubmitBnplTamraServiceResponse.getCheckout_id());
		LOG.info("TAMARA API response OrderID: " + asqSubmitBnplTamraServiceResponse.getOrder_id());
		return validateCheckoutSessionResponseAndStoreDataInDB(asqSubmitBnplTamraServiceResponse);
	}

	/**
	 * This method return Technical Error
	 *
	 * @param argModel
	 * @return Error
	 */

	private IOpResponse validateCheckoutSessionResponseAndStoreDataInDB(AsqSubmitBnplTamraServiceResponse response) {
		if (null != response && null != response.getErrors()) {
			return handleServiceError(response);
		} else if (null == response) {
			return technicalErrorScreen("TAMARA API::::: Service has null response");
		}
		IPosTransaction trans = this._transactionScope.getTransaction();
		HashMap<String, String> responseList = new HashMap<String, String>();
		String orderID = response.getOrder_id();
		responseList.put("checkoutID", response.getCheckout_id());
		responseList.put("orderID", orderID);
		LOG.info("TAMARA API saving response to DB started");
		saveTamaraResponseToDB(trans, responseList);
		LOG.info("TAMARA API saving response to DB successfull");
		_transactionScope.setValue(AsqValueKeys.TAMARA_RESPONSE, orderID);
		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_TAMARA_PAYMENT"));
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
		LOG.info("TAMARA REDEEM API::::: " + message);
		return HELPER.getPromptResponse("ASQ_TAMARA_TECHNICAL_ERROR", args);
	}

	/**
	 * This method handles the TAMARA service errors
	 *
	 * @param asqServiceResponse
	 * @return Error Prompts
	 */

	public IOpResponse handleServiceError(AsqSubmitBnplTamraServiceResponse asqServiceResponse) {
		IFormattable[] args = new IFormattable[1];
		AsqTamaraErrorDesc error = asqServiceResponse.getErrors();
		args[0] = _formattables.getSimpleFormattable(error.getError());
		String errorConstant = asqStcHelper.mapTamaraErrors(asqServiceResponse.getStatus());
		LOG.info("Error From TAMARA API::::: " + args[0]);
		LOG.info("Error Message Generated By Xstore based on TAMARA API Response::::: " + error.getMessage());
		return HELPER.getPromptResponse(errorConstant, args);
	}

	/**
	 * This method saves the response of TAMARA API like checkoutID and orderID to
	 * TRN_TRNS_P table for other TAMARA API calls
	 *
	 * @throws Database persistent Exception
	 * @param IRetailTransaction originalPosTrx, String checkoutID, String orderID
	 * @return
	 */

	public void saveTamaraResponseToDB(IPosTransaction originalPosTrx, HashMap<String, String> responseList) {

		IPosTransaction trans = this._transactionScope.getTransaction();
		if (responseList != null && trans != null) {
			trans.setStringProperty("ASQ_TAMARA_ORDERID", responseList.get("orderID"));
			trans.setStringProperty("ASQ_TAMARA_CHECKOUTID", responseList.get("checkoutID"));
			_transactionScope.setValue(AsqValueKeys.ASQ_TAMARA_ORDERID, responseList.get("orderID"));
			_transactionScope.setValue(AsqValueKeys.ASQ_TAMARA_CHECKOUTID, responseList.get("checkoutID"));
			LOG.info("Successfully updated Transaction properties with Response values");
		}
	}

	public AsqSubmitBnplTamraServiceResponse createInStoreCheckoutSession(
			IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest) {
		AsqSubmitBnplTamraServiceResponse response = new AsqSubmitBnplTamraServiceResponse();
		try {
			LOG.info("Calling CreateInStoreCheckoutSession API");
			response = (AsqSubmitBnplTamraServiceResponse) tamaraService.get()
					.createInStoreCheckoutSession(asqSubmitBnplTamraServiceRequest);
			LOG.info("Returned from CreateInStoreCheckoutSession API");
		} catch (Exception ex) {
			LOG.debug("Exception during calling createInStoreCheckoutSession to start payment");
		}
		return response;
	}
}
