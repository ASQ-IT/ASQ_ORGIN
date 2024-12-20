package asq.pos.register.sale;

import java.math.BigDecimal;
import java.util.Date;

import dtv.data2.access.AbstractQueryResult;
import dtv.data2.access.IObjectId;

public class AsqRecievingQueryResult extends AbstractQueryResult {

	private static final long serialVersionUID = 1L;

	private long INVCTL_DOCUMENT_ID;
	private Date CREATE_DATE;
	private BigDecimal EXPECTED_COUNT;
	private BigDecimal POSTED_COUNT;

	public long getINVCTL_DOCUMENT_ID() {
		return INVCTL_DOCUMENT_ID;
	}

	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}

	public BigDecimal getEXPECTED_COUNT() {
		return EXPECTED_COUNT;
	}

	public BigDecimal getPOSTED_COUNT() {
		return POSTED_COUNT;
	}

	public void setINVCTL_DOCUMENT_ID(long iNVCTL_DOCUMENT_ID) {
		INVCTL_DOCUMENT_ID = iNVCTL_DOCUMENT_ID;
	}

	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}

	public void setEXPECTED_COUNT(BigDecimal eXPECTED_COUNT) {
		EXPECTED_COUNT = eXPECTED_COUNT;
	}

	public void setPOSTED_COUNT(BigDecimal pOSTED_COUNT) {
		POSTED_COUNT = pOSTED_COUNT;
	}

	@Override
	protected IObjectId getObjectIdImpl() {
		return super.getObjectId();
	}
}
