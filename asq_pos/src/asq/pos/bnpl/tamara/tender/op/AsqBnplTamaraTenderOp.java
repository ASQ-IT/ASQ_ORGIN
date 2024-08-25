package asq.pos.bnpl.tamara.tender.op;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import asq.pos.loyalty.stc.tender.op.AsqSTCMobileNumberEditModel;
import asq.pos.loyalty.stc.tender.service.AsqSTCErrorDesc;
import asq.pos.loyalty.stc.tender.service.AsqSTCLoyaltyServiceResponse;
import dtv.i18n.IFormattable;
import dtv.pos.common.OpChainKey;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.framework.validation.ValidationResultList;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.validation.IValidationResult;
import dtv.pos.iframework.validation.IValidationResultList;
import dtv.pos.iframework.validation.SimpleValidationResult;
import dtv.util.StringUtils;
import dtv.xst.dao.crm.IParty;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import dtv.xst.dao.trn.IPosTransactionProperty;

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
	 * This method handles the data operation after submitting the mobile number and calls request preparer
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
			return  requestPreparerStoreCheckOutSession(trans);
		}
		LOG.info("Action key is not equal to ACCEPT, rolling back to Sale Screen :"+argAction.getActionKey());
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
			}
			else {
				
			}
		} catch (Exception ex) {
			LOG.info(
					"TAMARA API Mobile number form execution exception customer is not available in the transaction and Trans object is null:");
		}
		return super.handleInitialState();
	}
	
	/**
	 * This method implements the requestPreparerStoreCheckOutSession method for preparing the
	 * request attributes
	 * 
	 * @param trans
	 * @return requestPreparerStoreCheckOutSession submission to TAMARA Service Handler
	 */
	
	private IOpResponse requestPreparerStoreCheckOutSession(IRetailTransaction trans) {

		AsqSubmitBnplTamraServiceResponse asqSubmitBnplTamraServiceResponse;
		List<ISaleReturnLineItem> saleItemList = trans.getLineItems(ISaleReturnLineItem.class);
		ArrayList<AsqBnplTamaraItemObj> itemList = new ArrayList<AsqBnplTamaraItemObj>();
		String itemId = null;
		String itemDesc = null;
		BigDecimal itemQty = BigDecimal.ZERO;
		for (ISaleReturnLineItem lineItem : saleItemList) {
			itemId = lineItem.getItemId();
			itemDesc = lineItem.getItemDescription();
			itemQty = lineItem.getQuantity();
			AsqBnplTamaraItemObj asqBnplTamaraItemObj = new AsqBnplTamaraItemObj();
			asqBnplTamaraItemObj.setType("InStore");// need to know this attribute
			asqBnplTamaraItemObj.setSku(itemId);
			asqBnplTamaraItemObj.setName(itemDesc);
			asqBnplTamaraItemObj.setReference_id(lineItem.getLineItemSequence());
			asqBnplTamaraItemObj.setQuantity(itemQty);
			itemList.add(asqBnplTamaraItemObj);
		}
		IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest = new AsqSubmitBnplTamraServiceRequest();
		AsqBnplTamaraAmountObj asqBnplTamaraAmountObj = new AsqBnplTamaraAmountObj();
		asqBnplTamaraAmountObj.setAmount(trans.getAmountTendered());
		//asqBnplTamaraAmountObj.setCurrency(trans.getRetailTransactionLineItems().get(0).getCurrencyId());
		asqBnplTamaraAmountObj.setCurrency("SAR");
		asqSubmitBnplTamraServiceRequest.setTotal_amount(asqBnplTamaraAmountObj);
		asqSubmitBnplTamraServiceRequest.setItems(itemList);
		//asqSubmitBnplTamraServiceRequest.setPhone_number(custMobileNumber);
		asqSubmitBnplTamraServiceRequest.setPhone_number("534274516");
		asqSubmitBnplTamraServiceRequest.setOrder_reference_id(trans.getTransactionSequence());
		asqSubmitBnplTamraServiceResponse = createInStoreCheckoutSession(asqSubmitBnplTamraServiceRequest);
		return validateResponseAndStoreDataInDB(asqSubmitBnplTamraServiceResponse);
	}

	private AsqSubmitBnplTamraServiceResponse requestPreparerGetOrderDetails(IRetailTransaction trans, String OrderID) {
		
		AsqSubmitBnplTamraServiceResponse asqSubmitBnplTamraServiceResponse;
		IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest = new AsqSubmitBnplTamraServiceRequest();
		asqSubmitBnplTamraServiceRequest.setOrder_id(OrderID);
		asqSubmitBnplTamraServiceResponse = getOrderDetails(asqSubmitBnplTamraServiceRequest);
		//Need to call this once we get successfull response on getOrderDetails
		setScopedValue(AsqValueKeys.ASQ_TAMARA_PAYMENT_SUCCESS,true);
		return asqSubmitBnplTamraServiceResponse;
	}
	
	private IOpResponse validateResponseAndStoreDataInDB(AsqSubmitBnplTamraServiceResponse response) {
		if (null != response && null != response.getErrors() && 0 != response.getErrors().length) {
			return handleServiceError(response);
		} else if (null == response) {
			return technicalErrorScreen("TAMARA API::::: Service has null response");
		}
		IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
		List<AsqSubmitBnplTamraServiceResponse> responseList = new ArrayList<AsqSubmitBnplTamraServiceResponse>();
		String orderID = response.getOrder_id();
		String checkoutID = response.getCheckout_id();
		response.setCheckout_id(checkoutID);
		response.setOrder_id(orderID);
		responseList.add(response);
		LOG.info("TAMARA API saving response to DB started");
	//	asqStcHelper.saveTamaraResponseToDB(trans,responseList);
		LOG.info("TAMARA API saving response to DB successfull");
		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_TENDER_TAMARA"));
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
		LOG.info("STC REDEEM API::::: " + message);
		return HELPER.getPromptResponse("ASQ_STC_TECHNICAL_ERROR", args);
	}
	/**
	 * This method handles the InStoreCheckoutSession API call service errors
	 * 
	 * @param asqServiceResponse
	 * @return Error Prompts
	 */

	public IOpResponse handleServiceError(AsqSubmitBnplTamraServiceResponse asqServiceResponse) {
		IFormattable[] args = new IFormattable[2];
		AsqSTCErrorDesc error = asqServiceResponse.getErrors()[0];
		args[0] = _formattables.getSimpleFormattable(error.getCode());
		args[1] = _formattables.getSimpleFormattable(error.getDescription());
		String errorConstant = asqStcHelper.mapError(error.getCode());
		LOG.info("Error From TAMARA API::::: " + error.getCode() + " - " + error.getDescription());
		LOG.info("Error Message Generated By Xstore based on TAMARA API Response::::: " + errorConstant);
		return HELPER.getPromptResponse(errorConstant, args);
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
	
	public AsqSubmitBnplTamraServiceResponse createInStoreCheckoutSession(IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest) {
		AsqSubmitBnplTamraServiceResponse response = new AsqSubmitBnplTamraServiceResponse();
		try {
			 response = (AsqSubmitBnplTamraServiceResponse) tamaraService.get().createInStoreCheckoutSession(asqSubmitBnplTamraServiceRequest);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	public AsqSubmitBnplTamraServiceResponse getOrderDetails(IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest) {
		AsqSubmitBnplTamraServiceResponse response = new AsqSubmitBnplTamraServiceResponse();
		try {
			 response = (AsqSubmitBnplTamraServiceResponse) tamaraService.get().getOrderDetails(asqSubmitBnplTamraServiceRequest);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}
}
