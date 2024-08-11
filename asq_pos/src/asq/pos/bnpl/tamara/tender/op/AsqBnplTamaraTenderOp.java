package asq.pos.bnpl.tamara.tender.op;

import java.util.ArrayList;
import java.util.Currency;

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
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.iframework.action.IXstActionKey;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;
import dtv.xst.dao.tnd.ITender;
import dtv.xst.dao.trl.IRetailTransaction;

public class AsqBnplTamaraTenderOp extends AbstractFormOp<AsqBnplTamaraEditModel> {

	private static final Logger LOG = LogManager.getLogger(AsqBnplTamaraTenderOp.class);

	@Inject
	protected Provider<IAsqBnplTamaraServices> tamaraService;
	
	@Override
	protected AsqBnplTamaraEditModel createModel() {
		return new AsqBnplTamaraEditModel();
	}

	@Override
	protected String getFormKey() {
		return "ASQ_CAP_CUST_MOBILE_NO";
	}
	
	private String custMobileNumber = "";
	private int i = 0;

	@Override
	protected IOpResponse handleDataAction(IXstDataAction argAction) {
		AsqSubmitBnplTamraServiceResponse asqSubmitBnplTamraServiceResponse;
		
		try {
			IRetailTransaction trans = (IRetailTransaction) this._transactionScope.getTransaction();
			ITender tender = (ITender) getScopedValue(ValueKeys.CURRENT_TENDER);
			Currency currency = Currency.getInstance(tender.getCurrencyId());
			IXstActionKey actionKey = argAction.getActionKey();
			if (actionKey == XstDataActionKey.ACCEPT && trans!=null) {
				LOG.debug("Process of registering the till to Tamara Starts Here");
				AsqBnplTamaraEditModel model = getModel();
				
				IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest = new AsqSubmitBnplTamraServiceRequest();
				ArrayList<AsqBnplTamaraItemObj> itemList = new ArrayList<AsqBnplTamaraItemObj>();
				
				AsqBnplTamaraAmountObj asqBnplTamaraAmountObj = new AsqBnplTamaraAmountObj();
				asqBnplTamaraAmountObj.setAmount(trans.getAmountTendered());
				asqBnplTamaraAmountObj.setCurrency(currency);
				AsqBnplTamaraItemObj asqBnplTamaraItemObj = new AsqBnplTamaraItemObj();
				asqBnplTamaraItemObj.setReference_id(trans.getTransactionSequence());
				asqBnplTamaraItemObj.setType("InStore");
			//	asqBnplTamaraItemObj.setItemDescription(trans.);
				asqBnplTamaraItemObj.setSku("214187383");
				asqBnplTamaraItemObj.setQuantity(1);
				itemList.add(asqBnplTamaraItemObj);
				
				asqSubmitBnplTamraServiceRequest.setTotal_amount(asqBnplTamaraAmountObj);
				asqSubmitBnplTamraServiceRequest.setItems(itemList);
				asqSubmitBnplTamraServiceRequest.setPhone_number("9715123423423");
				asqSubmitBnplTamraServiceRequest.setOrder_reference_id("08502102205120231011");
				
				// calling create session service method
				asqSubmitBnplTamraServiceResponse = createInStoreCheckoutSession(asqSubmitBnplTamraServiceRequest);
						
				
				//calling order detail service method
//				asqSubmitBnplTamraServiceResponse = getOrderDetails(asqSubmitBnplTamraServiceRequest);
				
				//calling refund service method
//				asqSubmitBnplTamraServiceResponse = simplifiedRefunds(asqSubmitBnplTamraServiceRequest);
				
				//calling cancel session service method
//				asqSubmitBnplTamraServiceResponse = cancelSession(asqSubmitBnplTamraServiceRequest);
			}
		} catch (Exception ex) {
			LOG.error("Recieve error in the generating Tamara response", ex);
			return this.HELPER.getPromptResponse("ASQ_ZATCA_REGISTOR_ERROR");
		}
		return HELPER.completeResponse();
	}
	
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
				setScopedValue(AsqValueKeys.ASQ_STC_MOBILE, editModel.getCustMobileNumber());
				return super.handleInitialState();
			}
			else {
				
			}
		} catch (Exception ex) {
		}
		return super.handleInitialState();
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
	
	public AsqSubmitBnplTamraServiceResponse simplifiedRefunds(IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest) {
		AsqSubmitBnplTamraServiceResponse response = new AsqSubmitBnplTamraServiceResponse();
		try {
			 response = (AsqSubmitBnplTamraServiceResponse) tamaraService.get().simplifiedRefunds(asqSubmitBnplTamraServiceRequest);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}
	
	public AsqSubmitBnplTamraServiceResponse cancelSession(IAsqSubmitBnplTamraServiceRequest asqSubmitBnplTamraServiceRequest) {
		AsqSubmitBnplTamraServiceResponse response = new AsqSubmitBnplTamraServiceResponse();
		try {
			 response = (AsqSubmitBnplTamraServiceResponse) tamaraService.get().cancelSession(asqSubmitBnplTamraServiceRequest);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}
}
