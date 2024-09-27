package asq.pos.loyalty.neqaty.tender.service;

import javax.xml.ws.Holder;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.loyalty.neqaty.gen.AbortRequest;
import asq.pos.loyalty.neqaty.gen.AuthorizeRequest;
import asq.pos.loyalty.neqaty.gen.ConfirmRequest;
import asq.pos.loyalty.neqaty.gen.NeqatyWSAPIOptionsData;
import asq.pos.loyalty.neqaty.gen.NeqatyWSAPIPortType;
import asq.pos.loyalty.neqaty.gen.ObjectFactory;
import dtv.servicex.ServiceType;
import dtv.servicex.impl.AbstractJaxWsHandler;

public class AsqNeqatyServiceHandler
		extends AbstractJaxWsHandler<NeqatyWSAPIPortType, IAsqNeqatyServiceRequest, AsqNeqatyServiceResponse> {

	private static final Logger LOG = LogManager.getLogger(AsqNeqatyServiceHandler.class);

	@Override
	public AsqNeqatyServiceResponse handleService(IAsqNeqatyServiceRequest argServiceRequest,
			ServiceType<IAsqNeqatyServiceRequest, AsqNeqatyServiceResponse> paramServiceType) {

		AsqNeqatyServiceResponse response = null;
		try {
			NeqatyWSAPIPortType servicePort = getServicePort();

			Holder<Integer> resultCode = new Holder<Integer>();
			Holder<String> resultDescription = new Holder<String>();
			Holder<Integer> token = new Holder<Integer>();
			Holder<String> transactionReference = new Holder<String>();
			Holder<NeqatyWSAPIOptionsData> optionsData = new Holder<NeqatyWSAPIOptionsData>();

			switch (argServiceRequest.getMethod()) {
			case AUTHORIZE:
				servicePort.authorize(getAuthorizeRequest(argServiceRequest), resultCode, resultDescription, token,
						transactionReference, optionsData);
				break;
			case CONFIRM:
				servicePort.confirm(getConfirmRequest(argServiceRequest), resultCode, resultDescription, optionsData);
				break;
			case ABORT:
				servicePort.abort(getAbortRequest(argServiceRequest), resultCode, resultDescription);
				break;
			}

			// servicePort.authorize(getAuthorizeRequest(argServiceRequest), resultCode,
			// resultDescription, token,
			// transactionReference, optionsData);
			response = mapToNeqatyResponse(resultCode, resultDescription, token, transactionReference, optionsData);
			System.out.print("");
		} catch (Exception ex) {
			for (StackTraceElement stack : ex.getStackTrace()) {
				System.out.println(stack.toString());
			}
			LOG.error("Exception In Neqaty ", ex);
		}
		return response;
	}

	private AuthorizeRequest getAuthorizeRequest(IAsqNeqatyServiceRequest argServiceRequest) {
		ObjectFactory factory = new ObjectFactory();
		AuthorizeRequest requestPayload = factory.createAuthorizeRequest();
		requestPayload.setAuthenticationKey(
				factory.createAuthorizeRequestAuthenticationKey(argServiceRequest.getAuthenticationKey()));
		requestPayload.setMsisdn(factory.createAuthorizeRequestMsisdn(argServiceRequest.getMsisdn()));
		requestPayload
				.setOperationType(factory.createAuthorizeRequestOperationType(argServiceRequest.getOperationType()));

		requestPayload.setTid(factory.createAuthorizeRequestTid(argServiceRequest.getTid()));

		// Authorize request = factory.createAuthorize();
		// request.setReq(factory.createAuthorizeReq(requestPayload));
		return requestPayload;
	}

	private ConfirmRequest getConfirmRequest(IAsqNeqatyServiceRequest argServiceRequest) {
		ObjectFactory factory = new ObjectFactory();
		ConfirmRequest request = factory.createConfirmRequest();
		request.setAuthenticationKey(
				factory.createConfirmRequestAuthenticationKey(argServiceRequest.getAuthenticationKey()));
		request.setMsisdn(factory.createConfirmRequestMsisdn(argServiceRequest.getMsisdn()));

		if (!StringUtils.isBlank(argServiceRequest.getOtp())) {
			request.setOneTimePassword(factory.createConfirmRequestOneTimePassword(argServiceRequest.getOtp()));
		}
		request.setTransactionReference(
				factory.createConfirmRequestTransactionReference(argServiceRequest.getTransactionReference()));

		request.setTid(factory.createConfirmRequestTid(argServiceRequest.getTid()));
		return request;
	}

	private AbortRequest getAbortRequest(IAsqNeqatyServiceRequest argServiceRequest) {
		ObjectFactory factory = new ObjectFactory();
		AbortRequest request = factory.createAbortRequest();
		request.setAuthenticationKey(
				factory.createAbortRequestAuthenticationKey(argServiceRequest.getAuthenticationKey()));
		request.setMsisdn(factory.createAbortRequestMsisdn(argServiceRequest.getMsisdn()));

		request.setTransactionReference(
				factory.createAbortRequestTransactionReference(argServiceRequest.getTransactionReference()));

		request.setTid(factory.createAbortRequestTid(argServiceRequest.getTid()));
		return request;
	}

	private AsqNeqatyServiceResponse mapToNeqatyResponse(Holder<Integer> resultCode, Holder<String> resultDescription,
			Holder<Integer> token, Holder<String> transactionReference, Holder<NeqatyWSAPIOptionsData> optionsData) {

		AsqNeqatyServiceResponse responseNeqaty = new AsqNeqatyServiceResponse();
		responseNeqaty.setResultCode(resultCode.value);
		responseNeqaty.setResultDescription(resultDescription.value);
		responseNeqaty.setToken(token.value);
		responseNeqaty.setOptionsData(optionsData.value);
		responseNeqaty.setTransactionReference(transactionReference.value);
		return responseNeqaty;
	}

}
