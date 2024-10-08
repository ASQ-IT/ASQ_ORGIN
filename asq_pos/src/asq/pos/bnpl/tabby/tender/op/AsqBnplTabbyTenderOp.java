package asq.pos.bnpl.tabby.tender.op;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import asq.pos.bnpl.tabby.tender.op.AsqBnplTabbyEditModel;
import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyDetailsObj;
import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyErrorDesc;
import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyPaymentObj;
import asq.pos.bnpl.tabby.tender.service.AsqSubmitBnplTabbyServiceRequest;
import asq.pos.bnpl.tabby.tender.service.AsqSubmitBnplTabbyServiceResponse;
import asq.pos.bnpl.tabby.tender.service.IAsqBnplTabbyServices;
import asq.pos.bnpl.tabby.tender.service.IAsqSubmitBnplTabbyServiceRequest;
import asq.pos.bnpl.tamara.tender.op.AsqBnplTamaraEditModel;
import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraAmountObj;
import asq.pos.bnpl.tamara.tender.service.AsqBnplTamaraItemObj;
import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceRequest;
import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceResponse;
import asq.pos.bnpl.tamara.tender.service.AsqTamaraErrorDesc;
import asq.pos.bnpl.tamara.tender.service.IAsqSubmitBnplTamraServiceRequest;
import asq.pos.common.AsqValueKeys;
import asq.pos.loyalty.stc.tender.AsqStcHelper;
import asq.pos.zatca.AsqZatcaConstant;
import dtv.i18n.IFormattable;
import dtv.pos.common.OpChainKey;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.iframework.action.IXstActionKey;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.crm.IParty;
import dtv.xst.dao.trl.IRetailTransaction;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import dtv.xst.dao.trn.IPosTransaction;

public class AsqBnplTabbyTenderOp extends AbstractFormOp<AsqBnplTabbyEditModel>{
	private static final Logger LOG = LogManager.getLogger(AsqBnplTabbyTenderOp.class);

	@Inject
	protected Provider<IAsqBnplTabbyServices> tabbyService;
	
	@Inject
	AsqStcHelper asqStcHelper;
	
	@Override
	protected AsqBnplTabbyEditModel createModel() {
		return new AsqBnplTabbyEditModel();
	}

	@Override
	protected String getFormKey() {
		return "ASQ_TABBY_DETAILS";
	}
	
	private String custMobileNumber = "";
	
	/**
	 * This method checks whether customer is linked to transaction or not
	 * 
	 * @param
	 * @return
	 */

	protected IOpResponse handleInitialState() {

		String custMobileNumber = "";
		int i = 0;
		AsqBnplTabbyEditModel editModel = getModel();
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
			custMobileNumber = editModel.getCustMobileNumber();
			LOG.debug("TABBY API Mobile number captured :" + custMobileNumber);
			if (custMobileNumber != null && !custMobileNumber.equals("")) {
				LOG.info("Process of TABBY tender starts here");
				if (null != trans.getCustomerParty()
						&& !(this.getScopedValue(AsqValueKeys.ASQ_MOBILE_NUMBER).equals(custMobileNumber))) {
					IParty info = trans.getCustomerParty();
					info.setTelephone1(custMobileNumber);
					setScopedValue(AsqValueKeys.ASQ_MOBILE_NUMBER, custMobileNumber);
					LOG.info(
							"TABBY API setting updated customer mobile number to transaction, this will be udpated once the transaciton is completed");
				}
			} else if (custMobileNumber == null) {
				LOG.debug("TABBY API customer mobile number field is null :");
				return super.handleDataAction(argAction);
			}
			LOG.info("TABBY API request preparer for service call starts here: ");
			return requestPreparerStoreSession(trans);
		}
		LOG.info("Action key is not equal to ACCEPT, rolling back to Sale Screen :" + argAction.getActionKey());
		return this.HELPER.getOpChainRollBackRequest();
	}
	
	/**
	 * This method implements the requestPreparerStoreSession method for
	 * preparing the request attributes
	 * 
	 * @param trans
	 * @return requestPreparerStoreCheckOutSession submission to TAMARA Service
	 *         Handler
	 */

	private IOpResponse requestPreparerStoreSession(IRetailTransaction trans) {
		
		AsqSubmitBnplTabbyServiceResponse asqSubmitBnplTabbyServiceResponse;
		IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest = new AsqSubmitBnplTabbyServiceRequest();
		AsqBnplTabbyPaymentObj payment = new AsqBnplTabbyPaymentObj();
		AsqBnplTabbyDetailsObj asqBnplTabbyDetailsObj = new AsqBnplTabbyDetailsObj();
		asqBnplTabbyDetailsObj.setPhone(custMobileNumber);
		asqBnplTabbyDetailsObj.setReference_id(Long.toString(trans.getTransactionSequence()));
		payment.setAmount(trans.getTotal().toString());
		//payment.setCurrency(trans.getRetailTransactionLineItems().get(0).getCurrencyId());
		payment.setCurrency("SAR");
		payment.setBuyer(asqBnplTabbyDetailsObj);
		payment.setOrder(asqBnplTabbyDetailsObj);
		asqSubmitBnplTabbyServiceRequest.setPayment(payment);
		asqSubmitBnplTabbyServiceRequest.setMerchant_code(AsqZatcaConstant.ASQ_TABBY_MERCHANT_CODE_DEFAULT);
		asqSubmitBnplTabbyServiceResponse = createSession(asqSubmitBnplTabbyServiceRequest);
		return validateSessionResponseAndStoreDataInDB(asqSubmitBnplTabbyServiceResponse);
	}

	private IOpResponse validateSessionResponseAndStoreDataInDB(AsqSubmitBnplTabbyServiceResponse response) {
		
		  if (null != response && null != response.getError()) { 
			  return handleServiceError(response); 
			  } 
		  else if(null == response) 
		  { 
			  return technicalErrorScreen("TABBY API::::: Service has null response"); 
			  }
		  IPosTransaction trans = (IPosTransaction)this._transactionScope.getTransaction(); 
		  HashMap<String, String> responseList = new HashMap<String, String>(); //need to work on this
		  responseList.put("sessionID",  response.getId());
		  responseList.put("paymentID", response.getPayment().getId());
		  responseList.put("paymentCreationDate",response.getPayment().getCreated_at());
		  responseList.put("paymentExpiryDate", response.getPayment().getExpires_at());
		  responseList.put("paymentStatus",response.getPayment().getStatus());
		  LOG.info("TABBY API saving response to DB started");
		  saveTabbyResponseToDB(trans, responseList);
		  LOG.info("TABBY API saving response to DB successfull");
		  return (IOpResponse)requestPreparerGetNotificationService(response, trans);
	}
	
	private IOpResponse requestPreparerGetNotificationService(AsqSubmitBnplTabbyServiceResponse response,
			IPosTransaction trans) {

		AsqSubmitBnplTabbyServiceResponse asqSubmitBnplTabbyServiceResponse = new AsqSubmitBnplTabbyServiceResponse();
		IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest = new AsqSubmitBnplTabbyServiceRequest();
		if (response.getError() == null) {
			asqSubmitBnplTabbyServiceRequest.setId(response.getId());
			asqSubmitBnplTabbyServiceResponse = notificationRequest(asqSubmitBnplTabbyServiceRequest);
		}
		return validateNotificationServiceResponse(asqSubmitBnplTabbyServiceResponse, trans);
	}

	@SuppressWarnings("null")
	private IOpResponse validateNotificationServiceResponse(AsqSubmitBnplTabbyServiceResponse response, IPosTransaction trans) {
		if (null != response && null != response.getError()) {
			return handleServiceError(response);
		} else if (response.getStatus().equalsIgnoreCase("ok")) {
			return (IOpResponse) requestPreparerRetrievePayment(response, trans);
			
			//return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_TENDER_TABBY"));
		} /*
			 * else if (response.getError() == null) {
			 * setScopedValue(AsqValueKeys.ASQ_TABBY_PAYMENT_SUCCESS, true); //return
			 * this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf(
			 * "ASQ_TENDER_TABBY")); return (IOpResponse)
			 * requestPreparerRetrievePayment(response); }
			 */
		return HELPER.completeCurrentChainResponse();
	}
	
	private IOpResponse requestPreparerRetrievePayment(AsqSubmitBnplTabbyServiceResponse response, IPosTransaction trans) {

		AsqSubmitBnplTabbyServiceResponse asqSubmitBnplTabbyServiceResponse = new AsqSubmitBnplTabbyServiceResponse();
		IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest = new AsqSubmitBnplTabbyServiceRequest();
		AsqBnplTabbyPaymentObj payment = new AsqBnplTabbyPaymentObj();
		if (response.getError() == null) {
			payment.setId(this._transactionScope.getValue(AsqValueKeys.ASQ_TABBY_PMNT_ID));
			asqSubmitBnplTabbyServiceRequest.setPayment(payment);
			asqSubmitBnplTabbyServiceResponse = retrievePayment(asqSubmitBnplTabbyServiceRequest);
		}
		return validateRetrievePaymentResponse(asqSubmitBnplTabbyServiceResponse);
	}
	
	private IOpResponse validateRetrievePaymentResponse(AsqSubmitBnplTabbyServiceResponse response) {
		if (null != response && null != response.getError()) {
			return handleServiceError(response);
		} else if (response.getStatus().equalsIgnoreCase("CREATED")) { // NEED TO CHECK ALL 5 STATUS CODES
			setScopedValue(AsqValueKeys.ASQ_TABBY_PAYMENT_SUCCESS, true);// need to correct this
			return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_TENDER_TABBY"));// need to correct
																										// this
		} /*
			 * else if (response.getError() == null) {
			 * setScopedValue(AsqValueKeys.ASQ_TABBY_PAYMENT_SUCCESS, true); return
			 * this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf(
			 * "ASQ_TENDER_TABBY")); }
			 */
		return HELPER.completeCurrentChainResponse();
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
			_transactionScope.setValue(AsqValueKeys.ASQ_TABBY_SESSION_ID,responseList.get("sessionID"));
			_transactionScope.setValue(AsqValueKeys.ASQ_TABBY_PMNT_STATUS, responseList.get("paymentStatus"));
		}
	}
	public AsqSubmitBnplTabbyServiceResponse createSession(IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest) {
		AsqSubmitBnplTabbyServiceResponse response = new AsqSubmitBnplTabbyServiceResponse();
		try {
			 response = (AsqSubmitBnplTabbyServiceResponse) tabbyService.get().createSession(asqSubmitBnplTabbyServiceRequest);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}
	
	public AsqSubmitBnplTabbyServiceResponse notificationRequest(IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest) {
		AsqSubmitBnplTabbyServiceResponse response = new AsqSubmitBnplTabbyServiceResponse();
		try {
			 response = (AsqSubmitBnplTabbyServiceResponse) tabbyService.get().notificationRequest(asqSubmitBnplTabbyServiceRequest);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	public AsqSubmitBnplTabbyServiceResponse retrievePayment(IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest) {
		AsqSubmitBnplTabbyServiceResponse response = new AsqSubmitBnplTabbyServiceResponse();
		try {
			 response = (AsqSubmitBnplTabbyServiceResponse) tabbyService.get().retrievePayment(asqSubmitBnplTabbyServiceRequest);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}
	
	public AsqSubmitBnplTabbyServiceResponse refundPayment(IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest) {
		AsqSubmitBnplTabbyServiceResponse response = new AsqSubmitBnplTabbyServiceResponse();
		try {
			 response = (AsqSubmitBnplTabbyServiceResponse) tabbyService.get().refundPayment(asqSubmitBnplTabbyServiceRequest);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	public AsqSubmitBnplTabbyServiceResponse cancelSession(IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest) {
		AsqSubmitBnplTabbyServiceResponse response = new AsqSubmitBnplTabbyServiceResponse();
		try {
			 response = (AsqSubmitBnplTabbyServiceResponse) tabbyService.get().cancelSession(asqSubmitBnplTabbyServiceRequest);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}
	
	private IOpResponse validateResponseAndStoreDataInDB(IRetailTransaction originalPosTrx, AsqSubmitBnplTabbyServiceResponse response) {
		if (null != response && null != response.getError()) {
			return handleServiceError(response);
		} else if (null == response) {
			return technicalErrorScreen("TABBY API::::: Service has null response");
		}
		IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
		LOG.info("TABBY API saving response to DB started");
//		asqStcHelper.saveTamaraResponseToDB(originalPosTrx, response);
		LOG.info("TABBY API saving response to DB successfull");
		return this.HELPER.getCompleteStackChainResponse(OpChainKey.valueOf("ASQ_TENDER_TABBY"));
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
		LOG.info("Error From TABBY API::::: " +  args[0] + " - " + args[1]);
		LOG.info("Error Message Generated By Xstore based on TABBY API Response::::: " + error.getErrorType());
		return HELPER.getPromptResponse(error.getErrorType(), args);
	}


}
