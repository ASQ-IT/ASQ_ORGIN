package asq.pos.bnpl.tabby.tender.op;

import java.util.ArrayList;

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
import asq.pos.bnpl.tamara.tender.service.AsqSubmitBnplTamraServiceResponse;
import asq.pos.bnpl.tamara.tender.service.AsqTamaraErrorDesc;
import asq.pos.bnpl.tamara.tender.service.IAsqSubmitBnplTamraServiceRequest;
import asq.pos.loyalty.stc.tender.AsqStcHelper;
import dtv.i18n.IFormattable;
import dtv.pos.common.OpChainKey;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.iframework.action.IXstActionKey;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.trl.IRetailTransaction;

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

	@Override
	protected IOpResponse handleDataAction(IXstDataAction argAction) {
		AsqSubmitBnplTabbyServiceResponse asqSubmitBnplTabbyServiceResponse;
		
		try {
			IXstActionKey actionKey = argAction.getActionKey();
			if (actionKey == XstDataActionKey.ACCEPT) {
				LOG.debug("Process of registering the till to Tabby Starts");
				AsqBnplTabbyEditModel model = getModel();
				IAsqSubmitBnplTabbyServiceRequest asqSubmitBnplTabbyServiceRequest = new AsqSubmitBnplTabbyServiceRequest();
				AsqBnplTabbyPaymentObj payment = new AsqBnplTabbyPaymentObj();
				AsqBnplTabbyDetailsObj asqBnplTabbyDetailsObj = new AsqBnplTabbyDetailsObj();
				asqBnplTabbyDetailsObj.setPhone("534274516");
				asqBnplTabbyDetailsObj.setReference_id("07266102056720240227");
				payment.setAmount("100.00");
				payment.setCurrency("SAR");
				payment.setBuyer(asqBnplTabbyDetailsObj);
				payment.setOrder(asqBnplTabbyDetailsObj);
				asqSubmitBnplTabbyServiceRequest.setPayment(payment);
				asqSubmitBnplTabbyServiceRequest.setMerchant_code("ASQAAB1");
			}
		} catch (Exception ex) {
			LOG.error("Recieve error in the generating Tabby response", ex);
			return this.HELPER.getPromptResponse("ASQ_ZATCA_REGISTOR_ERROR");
		}
		return HELPER.completeResponse();
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
