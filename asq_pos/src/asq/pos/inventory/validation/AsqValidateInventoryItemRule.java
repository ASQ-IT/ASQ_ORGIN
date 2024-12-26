/**
 * 
 */
package asq.pos.inventory.validation;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import asq.pos.common.AsqConfigurationMgr;
import dtv.i18n.IFormattable;
import dtv.pos.common.ConfigurationMgr;
import dtv.pos.common.SysConfigAccessor;
import dtv.pos.common.ValueKeys;
import dtv.pos.framework.validation.AbstractSecuredValidationRule;
import dtv.pos.framework.validation.ValidationResult;
import dtv.pos.iframework.validation.IValidationResult;
import dtv.pos.iframework.validation.SimpleValidationResult;
import dtv.pos.inventory.InventoryHelper;
import dtv.pos.inventory.lookup.InventoryLookupHelper;
import dtv.pos.inventory.type.InvDocType;
import dtv.pos.inventory.validation.ValidateInventoryItemRule;
import dtv.service.ServiceException;
import dtv.service.ServiceUnavailableException;
import dtv.util.NumberUtils;
import dtv.util.StringUtils;
import dtv.xst.dao.inv.IDocumentInventoryLocationModifier;
import dtv.xst.dao.inv.IInventoryDocument;
import dtv.xst.dao.inv.IInventoryDocumentLineItem;
import dtv.xst.dao.itm.IItem;
import dtv.xst.dao.itm.INonPhysicalItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author SA20547171
 *
 */
public class AsqValidateInventoryItemRule extends AbstractSecuredValidationRule {
	private static final String failedMessageKey_ = "_inventoryQuantityVerificationLessDetailMsg";
	private static final String ALLOW_ACTION = "ALLOW";
	private static final String PROHIBIT_ACTION = "PROHIBIT";
	private static final String OVERRIDE_ACTION = "OVERRIDE";
	private static final Logger _logger = LogManager.getLogger(ValidateInventoryItemRule.class);
	@Inject
	private InventoryLookupHelper _invLookupHelper;

	@Inject
	private InventoryHelper _invHelper;

	@Inject
	private SysConfigAccessor _sysConfig;

	@Inject
	private AsqConfigurationMgr _asqConfig;

	public String getBucketId() {
		IInventoryDocument doc = getScopedValue(ValueKeys.CURRENT_INV_DOC);
		InvDocType docType = _invHelper.getInvDocType(doc.getDocumentTypeCode(), doc.getDocumentSubtypeCode());
		return !StringUtils.isEmpty(docType.getInventoryBucket()) ? docType.getInventoryBucket()
				: ConfigurationMgr.getOnHandInventoryBucketId();
	}

	public IValidationResult validate() {
		if (_sysConfig.checkItemQuantity()) {
			String unavailableAction = "ALLOW";
			if (unavailableAction.equals("ALLOW")) {
				return IValidationResult.SUCCESS;
			}

			IItem item = null;
			BigDecimal quantity = BigDecimal.ZERO;
			if (getScopedValue(ValueKeys.CURRENT_ITEM) != null) {
				item = getScopedValue(ValueKeys.CURRENT_ITEM);
				quantity = getScopedValue(ValueKeys.ENTERED_ITEM_QUANTITY) != null
						? (BigDecimal) getScopedValue(ValueKeys.ENTERED_ITEM_QUANTITY)
						: BigDecimal.ZERO;
			} else if (getScopedValue(ValueKeys.CURRENT_INV_DOC_LINE) != null) {
				item = getScopedValue(ValueKeys.CURRENT_INV_DOC_LINE).getItem();
				quantity = getScopedValue(ValueKeys.ENTERED_ITEM_QUANTITY);
			}

			if (item == null || item instanceof INonPhysicalItem || item.getNotInventoried()) {
				return IValidationResult.SUCCESS;
			}

			try {
				BigDecimal itemInventory = getItemAvailableQuantity(item);
				IInventoryDocument doc = getScopedValue(ValueKeys.CURRENT_INV_DOC);
				List<IInventoryDocumentLineItem> invDocLineItems = doc.getInventoryDocumentLineItems();
				BigDecimal transPlusNewLine = BigDecimal.ZERO;
				if (quantity != BigDecimal.ZERO) {
					transPlusNewLine = NumberUtils.add(BigDecimal.ZERO, quantity);
				} else {
					for (IInventoryDocumentLineItem invDocLineItem : invDocLineItems) {
						for (IDocumentInventoryLocationModifier invDocLineItemMod : invDocLineItem
								.getDocumentInventoryLocationModifiers()) {
							if (invDocLineItemMod.getItemId().equalsIgnoreCase(item.getItemId())) {
								quantity = quantity.add(invDocLineItemMod.getQuantity());
							}
						}
					}

					transPlusNewLine = NumberUtils.add(BigDecimal.ONE, quantity);
				}

				if (NumberUtils.isGreaterThan(transPlusNewLine, itemInventory)) {
					if (unavailableAction.equals("PROHIBIT")) {
						return SimpleValidationResult.getFailed(FF.getTranslatable(getErrorMsgKey(),
								new IFormattable[] { FF.getLiteral(item.getItemId()),
										FF.getSimpleFormattable(item.getDescription()) }));
					}

					if (unavailableAction.equals("OVERRIDE")) {
						IFormattable[] args = new IFormattable[] { FF.getLiteral(item.getItemId()),
								FF.getSimpleFormattable(item.getDescription()) };
						IFormattable msg = FF.getTranslatable(getErrorMsgKey(), args);
						return ValidationResult.getOverridable(msg, getPrivilege());
					}
				}
			} catch (ServiceUnavailableException var12) {
				_logger.error("Service unavailable exception caught retrieving item [" + item.getItemId() + "].",
						var12);
				return ValidationResult.getOverridable(FF.getTranslatable("_invServiceQtyVerifyOffline"),
						getPrivilege());
			} catch (ServiceException var13) {
				_logger.error("Service exception caught retrieving item [" + item.getItemId() + "].", var13);
				return SimpleValidationResult.getFailed(FF.getTranslatable("_invServiceFailedMsg"));
			}
		}

		return IValidationResult.SUCCESS;
	}

	protected String getErrorMsgKey() {
		return "_inventoryQuantityVerificationLessDetailMsg";
	}

	protected BigDecimal getItemAvailableQuantity(IItem argItem) {
		return _invLookupHelper.getAvailableQuantity(argItem, _stationState.getRetailLocationId(), getBucketId());
	}
}
