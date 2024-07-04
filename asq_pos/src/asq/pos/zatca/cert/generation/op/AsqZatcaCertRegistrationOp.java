package asq.pos.zatca.cert.generation.op;

import dtv.pos.framework.op.AbstractFormOp;
import dtv.pos.iframework.action.IXstDataAction;
import dtv.pos.iframework.op.IOpResponse;
import dtv.service.ServiceException;
import dtv.service.req.IServiceResponse;
import oracle.retail.xstore.inv.exceptions.NotSupportedInTrainingModeException;

import javax.inject.Inject;
import javax.inject.Provider;

import asq.pos.zatca.cert.generation.service.AsqSubmitZatcaCertServiceRequest;
import asq.pos.zatca.cert.generation.service.IAsqZatcaCertRegistrationServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AsqZatcaCertRegistrationOp extends AbstractFormOp<AsqZatcaCertRegistrationEditModel> {
	
	private static final Logger LOG = LogManager.getLogger(AsqZatcaCertRegistrationOp.class);
	
	@Inject
	protected Provider<IAsqZatcaCertRegistrationServices> _zatcaCertRegistrationServices;

	@Override
	protected AsqZatcaCertRegistrationEditModel createModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getFormKey() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected IOpResponse handleDataAction(IXstDataAction argAction) {
		try {
			AsqSubmitZatcaCertServiceRequest serviceTempStoreRequest = new AsqSubmitZatcaCertServiceRequest();
			
			IServiceResponse response = _zatcaCertRegistrationServices.get().submitCertForRegistration(serviceTempStoreRequest);
		} catch (NotSupportedInTrainingModeException ex) {
			LOG.info(ex.getMessage());
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
		return null;
	}
}
