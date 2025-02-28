package asq.retail.xstore.countrypack.common.taxfree.fintrax.op;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import asq.pos.common.AsqConfigurationMgr;
import asq.pos.planet.vat.claim.service.AsqPlanetVatClaimOrderObj;
import asq.pos.planet.vat.claim.service.AsqPlanetVatClaimServiceRequest;
import asq.pos.planet.vat.claim.service.AsqPlanetVatClaimServiceResponse;
import asq.pos.planet.vat.claim.service.AsqPlanetVatClaimShopperIdDocObj;
import asq.pos.planet.vat.claim.service.AsqPlanetVatClaimShopperObj;
import asq.pos.planet.vat.claim.service.IAsqPlanetVatClaimServiceRequest;
import asq.pos.planet.vat.claim.service.IAsqPlanetVatClaimServices;
import asq.pos.zatca.AsqZatcaConstant;
import dtv.i18n.IFormattable;
import dtv.pos.common.CommonHelper;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.framework.op.OpState;
import dtv.pos.iframework.action.IXstActionKey;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.op.IOpState;
import dtv.pos.iframework.validation.IValidationResultList;
import dtv.xst.dao.trl.IRetailPriceModifier;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import dtv.xst.dao.trl.ISaleTaxModifier;
import dtv.xst.dao.trn.IPosTransaction;

public class AsqPlanetIssueFormAuthorizeOp extends AbstractFormOp<AsqPlanetVatClaimEditModel> {
	private static final Logger LOG = LogManager.getLogger(AsqPlanetIssueFormAuthorizeOp.class);
	
	protected final IOpState SHOWING_ERROR_PROMPT = new OpState("SHOWING_ERROR_PROMPT");
	public static final String ASQ_GENDER = "gender";
	@Inject
	protected Provider<IAsqPlanetVatClaimServices> planetService;

	@Inject
	private AsqConfigurationMgr _sysConfig;
	
	 @Inject
	   private CommonHelper _commonHelper;

	@Override
	protected AsqPlanetVatClaimEditModel createModel() {
		return new AsqPlanetVatClaimEditModel();
	}

	@Override
	protected String getFormKey() {
		return "ASQ_PLANET_TAX_DETAILS";
	}

	@Override
	protected IOpResponse handleDataAction(IXstDataAction argAction) {
		AsqPlanetVatClaimServiceResponse asqPlanetVatClaimServiceResponse;
		AsqPlanetVatClaimEditModel model = getModel();
		IValidationResultList results = validateForm(model);
		try {
			IXstActionKey actionKey = argAction.getActionKey();
			if (actionKey == XstDataActionKey.ACCEPT) {
				LOG.debug("Process of registering the Tax Details");
				
				if (getOpState() == SHOWING_ERROR_PROMPT) {

				//	model.revertChanges();

					setOpState(null);
					return null;
				}

				if (!results.isValid()) {
					return super.handleDataAction(argAction);
				}

				IAsqPlanetVatClaimServiceRequest asqPlanetVatClaimServiceRequest = new AsqPlanetVatClaimServiceRequest();

				// Transaction Details
				IPosTransaction argTrans = _transactionScope.getTransaction();
				AsqPlanetVatClaimOrderObj asqPlanetVatClaimOrderObj = new AsqPlanetVatClaimOrderObj();
				asqPlanetVatClaimOrderObj.setTotalBeforeVAT(argTrans.getTotal().subtract(argTrans.getTaxAmount()));
				asqPlanetVatClaimOrderObj.setVatIncl(argTrans.getTaxAmount());
				asqPlanetVatClaimOrderObj.setTotal(argTrans.getTotal());

				// Line Item details
				List<ISaleReturnLineItem> argSaleLines = argTrans.getLineItems(ISaleReturnLineItem.class);

				asqPlanetVatClaimOrderObj.setItems(planetLineItem(argSaleLines));

				AsqPlanetVatClaimShopperObj asqPlanetVatClaimShopperObj = new AsqPlanetVatClaimShopperObj();
				asqPlanetVatClaimShopperObj.setFirstName(model.getFirstName());
				asqPlanetVatClaimShopperObj.setLastName(model.getFirstName());

				if(model.getGender() != null) {
				if (model.getGender().equalsIgnoreCase("M")) {
					asqPlanetVatClaimShopperObj.setGender("MALE");
				} else if (model.getGender().equalsIgnoreCase("F")) {
					asqPlanetVatClaimShopperObj.setGender("FEMALE");
				}
				}

				asqPlanetVatClaimShopperObj.setNationality(model.getAsqNationality());
				asqPlanetVatClaimShopperObj.setCountryOfResidence(model.getAsqNationalityResidence());
				asqPlanetVatClaimShopperObj.setPhoneNumber(model.getCustMobileNumber());

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

				AsqPlanetVatClaimShopperIdDocObj asqPlanetVatClaimShopperIdDocObj = new AsqPlanetVatClaimShopperIdDocObj();
				asqPlanetVatClaimShopperIdDocObj.setNumber(model.getAsqTaxDocumentNumber());
				asqPlanetVatClaimShopperIdDocObj.setType(model.getAsqTaxDocument());
				asqPlanetVatClaimShopperIdDocObj.setIssuedBy(model.getAsqTaxIssuedBy());
				AsqPlanetVatClaimShopperIdDocObj asqPlanetVatClaimShopperIdDocObj2 = new AsqPlanetVatClaimShopperIdDocObj();
				asqPlanetVatClaimShopperIdDocObj.setExpirationDate(model.getAsqExpirationDate());
				if (model.getAsqExpirationDate() != null) {
					asqPlanetVatClaimShopperIdDocObj.setExpirationDate(formatter.format(model.getAsqExpirationDate()));
				}
				
				if (model.getAsqBirthDate() != null) {
					asqPlanetVatClaimShopperIdDocObj2.setDate(formatter.format(model.getAsqBirthDate()));
				}

				asqPlanetVatClaimShopperObj.setBirth(asqPlanetVatClaimShopperIdDocObj2);
				asqPlanetVatClaimShopperObj.setShopperIdentityDocument(asqPlanetVatClaimShopperIdDocObj);

				asqPlanetVatClaimServiceRequest.setIssueTaxRefundTag(true);

				asqPlanetVatClaimServiceRequest.setDate(formatter.format(argTrans.getCreateDate()));
				String rcptData = String.valueOf(argTrans.getRetailLocationId())
						.concat(String.valueOf(argTrans.getWorkstationId())
								.concat(String.valueOf(argTrans.getTransactionSequence())));
				asqPlanetVatClaimServiceRequest.setReceiptNumber(rcptData);
				asqPlanetVatClaimServiceRequest.setMerchantId(_sysConfig.getPlanetMerchantIdNumber());
				asqPlanetVatClaimServiceRequest.setTerminal(_sysConfig.getPlanetTerminalNumber());
				asqPlanetVatClaimServiceRequest.setType(_sysConfig.getPlanetType());
				asqPlanetVatClaimServiceRequest.setOrder(asqPlanetVatClaimOrderObj);
				asqPlanetVatClaimServiceRequest.setShopper(asqPlanetVatClaimShopperObj);

				asqPlanetVatClaimServiceResponse = vatRegistration(asqPlanetVatClaimServiceRequest);
				// asqPlanetVatClaimServiceResponse.getTaxRefundResponse().getTaxRefundTagNumber();
				
				return validateResponseAndStoreDataInDB(argTrans, asqPlanetVatClaimServiceResponse);
			}
		} catch (Exception ex) {
			LOG.error("Recieve error in the generating Planet response", ex);
			return this.HELPER.getPromptResponse("ASQ_PLANET_REGISTER_ERROR");
		}
		return HELPER.completeResponse();
	}

	private ArrayList<AsqPlanetLineItemData> planetLineItem(List<ISaleReturnLineItem> argSaleLines) {
		ArrayList<AsqPlanetLineItemData> asqPlanetLineItemData = new ArrayList<>();
		for (ISaleReturnLineItem argSaleLine : argSaleLines) {
			AsqPlanetLineItemData line = new AsqPlanetLineItemData();

			if (!argSaleLine.getVoid()) {
				line.setCode(argSaleLine.getItem().getItemId());
				line.setDescription(argSaleLine.getItemDescription());
				line.setGrossAmount(argSaleLine.getGrossAmount());
				List<IRetailPriceModifier> discountRates = argSaleLine.getRetailPriceModifiers();
				BigDecimal discamount=BigDecimal.ZERO;
				for (IRetailPriceModifier discountRate : discountRates) {
					if(!discountRate.getVoid() && discountRate.getExtendedAmount()!=null) {
					discamount=discamount.add(discountRate.getExtendedAmount());
					}
				
				}
				if(discamount.compareTo(BigDecimal.ZERO)>0) {
				//line.setDiscountAmount(discamount);
				line.setGrossAmount(argSaleLine.getGrossAmount().subtract(discamount));
				}
				 //line.setMerchandiseGroup(argSaleLine.getItem().getMerchLevel1Id());
				line.setMerchandiseGroup(_sysConfig.getPlanetMerchantIdItem());
				line.setNetAmount(argSaleLine.getNetAmount());
				line.setQuantity(argSaleLine.getQuantity());
				line.setUnitPrice(argSaleLine.getUnitPrice());

				line.setTaxRefundEligible(false);
				List<ISaleTaxModifier> vatRates = argSaleLine.getTaxModifiers();

				for (ISaleTaxModifier vatRate : vatRates) {
					line.setTaxRefundEligible(true); // fasle for no vat
					line.setVatAmount(_commonHelper.roundCurrency(vatRate.getTaxAmount()));
					
					line.setVatCode("5");
					line.setVatRate(null); // Need to check
				}

				if (argSaleLine.getItem().getSerializedItem()) {
					line.setSerialNumber(argSaleLine.getSerialNumber());
				}

				asqPlanetLineItemData.add(line);
			}
		}
		return asqPlanetLineItemData;
	}

	private IOpResponse validateResponseAndStoreDataInDB(IPosTransaction originalPosTrx,
			AsqPlanetVatClaimServiceResponse response) {

		if (null == response) {
			return technicalErrorScreen("Planet API::::: Service has null response");
		} 
	
		else if (response.getTaxRefundResponse() != null) {
			originalPosTrx.setStringProperty(AsqZatcaConstant.ASQ_PLANET_TAX_ID,
					response.getTaxRefundResponse().getTaxRefundTagNumber());
			return HELPER.getPromptResponse("ASQ_PLANET_SUCCESS");
		}
		else if (response.getError()!= null){
			setOpState(SHOWING_ERROR_PROMPT);
			IFormattable[] args = new IFormattable[2];
			String message = response.getError().getMessage();
			args[0] = _formattables.getSimpleFormattable(message);
			LOG.info("Planet REDEEM API::::: " + message);
			
			return HELPER.getPromptResponse("ASQ_PLANET_RESPONSE_ERROR", args);
		}
		if ( null == response.getStatus()) {
			return technicalErrorScreen("Planet API::::: Service has null response");
		} 
		return HELPER.completeResponse();
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
		LOG.info("Planet REDEEM API::::: " + message);
		return HELPER.getPromptResponse("ASQ_PLANET_TECHNICAL_ERROR");
	}

	public AsqPlanetVatClaimServiceResponse vatRegistration(
			IAsqPlanetVatClaimServiceRequest asqPlanetVatClaimServiceRequest) {
		AsqPlanetVatClaimServiceResponse response = new AsqPlanetVatClaimServiceResponse();
		try {
			response = (AsqPlanetVatClaimServiceResponse) planetService.get()
					.vatRegistration(asqPlanetVatClaimServiceRequest);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

}
