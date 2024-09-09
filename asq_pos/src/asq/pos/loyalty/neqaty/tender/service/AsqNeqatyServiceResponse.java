package asq.pos.loyalty.neqaty.tender.service;

import asq.pos.loyalty.neqaty.gen.NeqatyWSAPIOptionsData;
import dtv.service.req.IServiceResponse;
import dtv.servicex.impl.req.ServiceRequest;

public class AsqNeqatyServiceResponse extends ServiceRequest implements IServiceResponse {

	/**
	 * This class has all response attributes POJO's
	 */

	private Integer resultCode;
	private String resultDescription;
	private int token;
	private String transactionReference;
	private NeqatyWSAPIOptionsData optionsData;
	
	public Integer getResultCode() {
		return resultCode;
	}
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDescription() {
		return resultDescription;
	}
	public void setResultDescription(String resultDescription) {
		this.resultDescription = resultDescription;
	}
	public int getToken() {
		return token;
	}
	public void setToken(int token) {
		this.token = token;
	}
	public String getTransactionReference() {
		return transactionReference;
	}
	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}
	public NeqatyWSAPIOptionsData getOptionsData() {
		return optionsData;
	}
	public void setOptionsData(NeqatyWSAPIOptionsData optionsData) {
		this.optionsData = optionsData;
	}
	
	
}
