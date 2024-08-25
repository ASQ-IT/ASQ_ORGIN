package asq.pos.loyalty.neqaty.tender;

import javax.xml.ws.Holder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.loyalty.neqaty.gen.NeqatyWSAPIPortType;
import dtv.servicex.ServiceType;
import dtv.servicex.impl.AbstractJaxWsHandler;

public class AsqNeqatyServiceHandler extends AbstractJaxWsHandler<NeqatyWSAPIPortType, IAsqNeqatyServiceRequest, AsqNeqatyServiceResponse> {

	private static final Logger LOG = LogManager.getLogger(AsqNeqatyServiceHandler.class);

	@Override
	public AsqNeqatyServiceResponse handleService(IAsqNeqatyServiceRequest argServiceRequest, ServiceType<IAsqNeqatyServiceRequest, AsqNeqatyServiceResponse> paramServiceType) {
		AsqNeqatyServiceResponse response = null;
		try {
			NeqatyWSAPIPortType servicePort = getServicePort();

			// check what need to set
			// response =

			Holder<String> resukltDesc = new Holder<String>();
			// servicePort.authorize(argServiceRequest,resultCode,resukltDesc,token,transactionReference,Holder<NeqatyWSAPIOptionsData>,
			// optionsData));
			resukltDesc.getClass();

		} catch (Exception ex) {
			LOG.error(ex);
		}
		checkForExceptions(response);
		return response;
	}

}
