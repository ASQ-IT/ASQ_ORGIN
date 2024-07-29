package asq.pos.bnpl.tamara.tender.op;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.bnpl.tamara.tender.service.IAsqBnplTamaraServices;
import dtv.pos.framework.action.type.XstDataActionKey;
import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.iframework.action.IXstActionKey;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;

public class AsqBnplTamaraTenderOp extends AbstractFormOp<AsqBnplTamaraEditModel> {

	private static final Logger LOG = LogManager.getLogger(AsqBnplTamaraTenderOp.class);

	@Inject
	protected Provider<IAsqBnplTamaraServices> zatcaService;

	@Override
	protected AsqBnplTamaraEditModel createModel() {
		return new AsqBnplTamaraEditModel();
	}

	@Override
	protected String getFormKey() {
		return "ASQ_CAP_CUST_MOBILE_NO";
	}

	@Override
	protected IOpResponse handleDataAction(IXstDataAction argAction) {
		try {
			IXstActionKey actionKey = argAction.getActionKey();
			if (actionKey == XstDataActionKey.ACCEPT) {
				LOG.debug("Process of registering the till to Zataca Starts");
				AsqBnplTamaraEditModel model = getModel();

				// calling the service method
				zatcaService.get().submitPaymentlinkToCustomer(null);
			}
		} catch (Exception ex) {
			LOG.error("Recieve error in the generating zatca certificate", ex);
			return this.HELPER.getPromptResponse("ASQ_ZATCA_REGISTOR_ERROR");
		}
		return HELPER.completeResponse();
	}

}
