package asq.retail.xstore.countrypack.common.taxfree.fintrax.op;

import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import asq.pos.bnpl.tabby.tender.service.AsqBnplTabbyErrorDesc;
import asq.pos.bnpl.tabby.tender.service.AsqSubmitBnplTabbyServiceResponse;
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
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.iframework.action.IXstActionKey;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;
import dtv.pos.iframework.validation.IValidationResultList;
import dtv.xst.dao.trl.ISaleReturnLineItem;
import dtv.xst.dao.trl.ISaleTaxModifier;
import dtv.xst.dao.trn.IPosTransaction;

public class AsqPlanetIssueFormAuthorizeOp extends AbstractFormOp<AsqPlanetVatClaimEditModel> {
	private static final Logger LOG = LogManager.getLogger(AsqPlanetIssueFormAuthorizeOp.class);

	@Inject
	protected Provider<IAsqPlanetVatClaimServices> planetService;
	
	   @Inject
	   private AsqConfigurationMgr _sysConfig;
	
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

				ArrayList<AsqPlanetLineItemData> asqPlanetLineItemData = new ArrayList<>();
				for (ISaleReturnLineItem argSaleLine : argSaleLines) {
					AsqPlanetLineItemData line = new AsqPlanetLineItemData();
					line.setCode(argSaleLine.getItem().getItemId());
					line.setDescription(argSaleLine.getItemDescription());
					line.setGrossAmount(argSaleLine.getGrossAmount());
					line.setMerchandiseGroup(argSaleLine.getItem().getMerchLevel1Id());
					line.setNetAmount(argSaleLine.getNetAmount());
					line.setQuantity(argSaleLine.getQuantity());
					line.setUnitPrice(argSaleLine.getUnitPrice());

					line.setTaxRefundEligible(false);
					List<ISaleTaxModifier> vatRates = argSaleLine.getTaxModifiers();

					for (ISaleTaxModifier vatRate : vatRates) {
						line.setTaxRefundEligible(true); // fasle for no vat
						line.setVatAmount(vatRate.getTaxAmount().round(new MathContext(3, RoundingMode.HALF_UP)));
						line.setVatCode(vatRate.getTaxGroupId());
						line.setVatRate(null); // Need to check
					}

					if (argSaleLine.getItem().getSerializedItem()) {
						line.setSerialNumber(argSaleLine.getSerialNumber());
					}

					asqPlanetLineItemData.add(line);
				}

				asqPlanetVatClaimOrderObj.setItems(asqPlanetLineItemData);

				AsqPlanetVatClaimShopperObj asqPlanetVatClaimShopperObj = new AsqPlanetVatClaimShopperObj();
				asqPlanetVatClaimShopperObj.setFirstName(model.getFirstName());
				asqPlanetVatClaimShopperObj.setLastName(model.getFirstName());
				asqPlanetVatClaimShopperObj.setGender(model.getGender());
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

				asqPlanetVatClaimShopperIdDocObj2.setDate(model.getAsqBirthDate());
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

	private IOpResponse validateResponseAndStoreDataInDB(IPosTransaction originalPosTrx,
			AsqPlanetVatClaimServiceResponse response) {

		if (null == response) {
			return technicalErrorScreen("Planet API::::: Service has null response");
		}
		originalPosTrx.setStringProperty(AsqZatcaConstant.ASQ_PLANET_TAX_ID,
				response.getTaxRefundResponse().getTaxRefundTagNumber());
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

//		String errorConstant = asqStcHelper.mapError(error.getErrorType());
		LOG.info("Error From Planet API::::: " + args[0] + " - " + args[1]);
		LOG.info("Error Message Generated By Xstore based on Planet API Response::::: " + error.getErrorType());
		return HELPER.getPromptResponse(error.getErrorType(), args);
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

	public AsqPlanetVatClaimServiceResponse cancelVatClaim(
			IAsqPlanetVatClaimServiceRequest asqPlanetVatClaimServiceRequest) {
		AsqPlanetVatClaimServiceResponse response = new AsqPlanetVatClaimServiceResponse();
		try {
			response = (AsqPlanetVatClaimServiceResponse) planetService.get()
					.cancelVatClaim(asqPlanetVatClaimServiceRequest);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

}
