package asq.pos.bnpl.tamara.tender.op;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javax.inject.Inject;
import javax.inject.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraAmountObj;
import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraItemObj;
import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceRequest;
import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceResponse;
import asq.pos.bnpl.tamara.tender.service.IAsqBnplTamaraServices;
import asq.pos.bnpl.tamara.tender.service.IAsqSubmitBnplTamraServiceRequest;
import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.stc.tender.AsqStcHelper;
import asq.pos.loyalty.stc.tender.service.AsqSTCErrorDesc;
import asq.pos.zatca.AsqZatcaConstant;
import dtv.data2.access.DataFactory;
import dtv.data2.access.exception.PersistenceException;
import dtv.i18n.IFormattable;
import dtv.pos.common.OpChainKey;
import dtv.pos.common.OpExecutionException;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.framework.validation.ValidationResultList;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.security.StationState;
import dtv.pos.iframework.ui.model.IStationModel;
import dtv.pos.iframework.validation.IValidationResult;
import dtv.pos.iframework.validation.IValidationResultList;
import dtv.pos.iframework.validation.SimpleValidationResult;
import dtv.util.StringUtils;
import dtv.xst.dao.crm.IParty;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import dtv.xst.dao.trn.IPosTransaction;
import dtv.xst.dao.trn.IPosTransactionProperty;
import dtv.xst.dao.trn.PosTransactionPropertyId;

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

	@Inject
	protected StationState _stationState;

	@Override
	protected AsqBnplTamaraEditModel createModel() {
		return new AsqBnplTamaraEditModel();
	}

	@Override
	protected String getFormKey() {
		return "ASQ_CAP_CUST_MOBILE_NO";
	}

	private String custMobileNumber = "";

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
			custMobileNumber = editModel.getCustMobileNumber();
			LOG.debug("TAMARA API Mobile number captured :" + custMobileNumber);
			if (custMobileNumber != null && !custMobileNumber.equals("")) {
				LOG.info("Process of TAMARA tender starts here");
				if (null != trans.getCustomerParty()
						&& !(this.getScopedValue(AsqValueKeys.ASQ_MOBILE_NUMBER).equals(custMobileNumber))) {
					IParty info = trans.getCustomerParty();
					info.setTelephone1(custMobileNumber);
					setScopedValue(AsqValueKeys.ASQ_MOBILE_NUMBER, custMobileNumber);
					LOG.info(
							"TAMARA API setting updated customer mobile number to transaction, this will be udpated once the transaciton is completed");
				}
			} else if (custMobileNumber == null) {
				LOG.debug("TAMARA API customer mobile number field is null :");
				return super.handleDataAction(argAction);
			}
			LOG.info("TAMARA API request preparer for service call starts here: ");
			System.out.println();
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

	protected IOpResponse handleInitialState() {

		String custMobileNumber = "";
		int i = 0;
		AsqBnplTamaraEditModel editModel = getModel();
		try {
			IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
			if (trans != null && trans.getCustomerParty() != null)// Transactions Typecode condition to be included
			{
				custMobileNumber = trans.getCustomerParty().getTelephoneInformation().get(i).getTelephoneNumber();
				editModel.setCustMobileNumber(custMobileNumber);
				setScopedValue(AsqValueKeys.ASQ_MOBILE_NUMBER, editModel.getCustMobileNumber());
				return super.handleInitialState();
			} else {

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
		// asqBnplTamaraAmountObj.setCurrency(trans.getRetailTransactionLineItems().get(0).getCurrencyId());
		asqBnplTamaraAmountObj.setCurrency("SAR");
		List<ISaleReturnLineItem> saleItemList = trans.getLineItems(ISaleReturnLineItem.class);
		ArrayList<AsqBnplTamaraItemObj> itemList = new ArrayList<AsqBnplTamaraItemObj>();
		for (ISaleReturnLineItem lineItem : saleItemList) {
			AsqBnplTamaraItemObj asqBnplTamaraItemObj = new AsqBnplTamaraItemObj();
			AsqBnplTamaraAmountObj itemTotalAmnt = new AsqBnplTamaraAmountObj();
			asqBnplTamaraItemObj.setType(AsqZatcaConstant.ASQ_TAMARA_STORE_TYPE_DEFAULT);
			asqBnplTamaraItemObj.setSku(lineItem.getItemId());
			asqBnplTamaraItemObj.setName(lineItem.getItemDescription());
			asqBnplTamaraItemObj.setReference_id(lineItem.getLineItemSequence());
			asqBnplTamaraItemObj.setQuantity(lineItem.getQuantity());
			itemTotalAmnt.setAmount(lineItem.getBaseUnitPrice());
			itemTotalAmnt.setCurrency("SAR");
			asqBnplTamaraItemObj.setTotal_amount(itemTotalAmnt);
			itemList.add(asqBnplTamaraItemObj);
		}
		asqSubmitBnplTamraServiceRequest.setTotal_amount(asqBnplTamaraAmountObj);
		asqSubmitBnplTamraServiceRequest.setItems(itemList);
		asqSubmitBnplTamraServiceRequest.setPhone_number(custMobileNumber);
		asqSubmitBnplTamraServiceRequest.setOrder_reference_id(Long.toString(trans.getTransactionSequence()));
		asqSubmitBnplTamraServiceResponse = createInStoreCheckoutSession(asqSubmitBnplTamraServiceRequest);
		System.out.println("CheckoutID: " + asqSubmitBnplTamraServiceResponse.getCheckout_id());
		System.out.println("OrderID: " + asqSubmitBnplTamraServiceResponse.getOrder_id());
		System.out.println("CheckoutLink: " +asqSubmitBnplTamraServiceResponse.getCheckout_link());
		return validateCheckoutSessionResponseAndStoreDataInDB(asqSubmitBnplTamraServiceResponse);
	}

	private IOpResponse validateCheckoutSessionResponseAndStoreDataInDB(AsqSubmitBnplTamraServiceResponse response) {
		if (null != response && null != response.getErrors() && 0 != response.getErrors().length) {
			return handleServiceError(response);
		} else if (null == response) {
			return technicalErrorScreen("TAMARA API::::: Service has null response");
		}
		IPosTransaction trans = (IPosTransaction) this._transactionScope.getTransaction();
		HashMap<String, String> responseList = new HashMap<String, String>();
		String orderID = response.getOrder_id();
		responseList.put("checkoutID", response.getCheckout_id());
		responseList.put("orderID", orderID);
		LOG.info("TAMARA API saving response to DB started");
		saveTamaraResponseToDB(trans, responseList);
		LOG.info("TAMARA API saving response to DB successfull");
		return (IOpResponse) requestPreparerGetOrderDetails(response, trans);
	}

	private IOpResponse requestPreparerGetOrderDetails(AsqSubmitBnplTamraServiceResponse response,
			IPosTransaction trans) {

		AsqSubmitBnplTamraServiceResponse asqSubmitBnplTamraServiceResponse = new AsqSubmitBnplTamraServiceResponse();
		IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest = new AsqSubmitBnplTamraServiceRequest();
		if (response.getErrors() == null) {
			asqSubmitBnplTamraServiceRequest.setOrder_id(response.getOrder_id());
			asqSubmitBnplTamraServiceResponse = getOrderDetails(asqSubmitBnplTamraServiceRequest);
		}
		return validateGetOrderDetailsResponse(asqSubmitBnplTamraServiceResponse);
	}

	private IOpResponse validateGetOrderDetailsResponse(AsqSubmitBnplTamraServiceResponse response) {
		if (null != response && null != response.getErrors() && 0 != response.getErrors().length) {
			return handleServiceError(response);
		} else if (null == response) {
			// return technicalErrorScreen("TAMARA API::::: Service has null response");
			setScopedValue(AsqValueKeys.ASQ_TAMARA_PAYMENT_SUCCESS, true);// need to correct this
			return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_TENDER_TAMARA"));// need to correct
																										// this
		} else if (response.getErrors() == null) {
			setScopedValue(AsqValueKeys.ASQ_TAMARA_PAYMENT_SUCCESS, true);
			return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_TENDER_TAMARA"));
		}
		return HELPER.completeCurrentChainResponse();
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
		IFormattable[] args = new IFormattable[2];
		// AsqTamaraErrorDesc error = asqServiceResponse.getErrors()[0];
		// args[0] = _formattables.getSimpleFormattable(error.getError_code());

		LOG.info("Error From TAMARA API::::: " + args[0]);
		LOG.info("Error Message Generated By Xstore based on TAMARA API Response::::: "
				+ asqServiceResponse.getMessage());
		return HELPER.getPromptResponse(asqServiceResponse.getMessage(), args);
	}

	/**
	 * This method handles form validation of mobile number field
	 * 
	 * @param argModel
	 * @return validationResultList
	 */

	protected IValidationResultList validateForm(AsqBnplTamaraEditModel argModel) {
		ValidationResultList validationResultList = new ValidationResultList();
		if (StringUtils.isEmpty(argModel.getCustMobileNumber()) && argModel.getCustMobileNumber() == null) {
			IValidationResult idResult = SimpleValidationResult.getFailed("_asqMobileNumberFieldExceptionMessage");
			validationResultList.add(idResult);
		}
		return (IValidationResultList) validationResultList;
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
		if (responseList != null) {
			trans.setStringProperty("ASQ_TAMARA_ORDERID", responseList.get("orderID"));
			trans.setStringProperty("ASQ_TAMARA_CHECKOUTID", responseList.get("checkoutID"));
			_transactionScope.setValue(AsqValueKeys.ASQ_TAMARA_ORDERID, responseList.get("orderID"));
			_transactionScope.setValue(AsqValueKeys.ASQ_TAMARA_CHECKOUTID, responseList.get("checkoutID"));
		}
	}

	public AsqSubmitBnplTamraServiceResponse createInStoreCheckoutSession(
			IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest) {
		AsqSubmitBnplTamraServiceResponse response = new AsqSubmitBnplTamraServiceResponse();
		try {
			response = (AsqSubmitBnplTamraServiceResponse) tamaraService.get()
					.createInStoreCheckoutSession(asqSubmitBnplTamraServiceRequest);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}


	public AsqSubmitBnplTamraServiceResponse getOrderDetails(
			IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest) {
		boolean success = false;
		AsqSubmitBnplTamraServiceResponse response = new AsqSubmitBnplTamraServiceResponse();
		while (!success) {
			try {
				response = (AsqSubmitBnplTamraServiceResponse) tamaraService.get()
						.getOrderDetails(asqSubmitBnplTamraServiceRequest);
				if (response != null && response.getStatus() != null) {
					if (response.getStatus().equalsIgnoreCase("fully_captured")
							|| response.getStatus().equalsIgnoreCase("expired")) {
						success = true;
					} else {
						Thread.sleep(1000);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return response;

	}
}
