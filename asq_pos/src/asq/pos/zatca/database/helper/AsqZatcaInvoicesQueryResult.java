package asq.pos.zatca.database.helper;

import java.util.Date;

import dtv.data2.access.AbstractQueryResult;
import dtv.data2.access.IObjectId;

public class AsqZatcaInvoicesQueryResult extends AbstractQueryResult {

	private static final long serialVersionUID = 3539305736841576125L;

	private long ORGANIZATION_ID;
	private String INVOICE_ID;
	private Date BUSINESS_DATE;
	private String TRANS_SEQ;
	private String WKSTN_ID;
	private String STATUS;
	private byte[] INVOICE_QRCODE;
	private byte[] INVOICE_XML;
	private Date CREATE_DATE;
	private String CREATE_USER_ID;
	private String UPDATE_USER_ID;
	private Date UPDATE_DATE;
	private String INVOICE_UUID;
	private String INVOICE_HASH;

	public String getINVOICE_UUID() {
		return INVOICE_UUID;
	}

	public void setINVOICE_UUID(String iNVOICE_UUID) {
		INVOICE_UUID = iNVOICE_UUID;
	}

	public String getINVOICE_HASH() {
		return INVOICE_HASH;
	}

	public void setINVOICE_HASH(String iNVOICE_HASH) {
		INVOICE_HASH = iNVOICE_HASH;
	}

	@Override
	protected IObjectId getObjectIdImpl() {
		return super.getObjectId();
	}

	public long getORGANIZATION_ID() {
		return ORGANIZATION_ID;
	}

	public void setORGANIZATION_ID(long oRGANIZATION_ID) {
		ORGANIZATION_ID = oRGANIZATION_ID;
	}

	public String getINVOICE_ID() {
		return INVOICE_ID;
	}

	public void setINVOICE_ID(String iNVOICE_ID) {
		INVOICE_ID = iNVOICE_ID;
	}

	public Date getBUSINESS_DATE() {
		return BUSINESS_DATE;
	}

	public void setBUSINESS_DATE(Date bUSINESS_DATE) {
		BUSINESS_DATE = bUSINESS_DATE;
	}

	public String getTRANS_SEQ() {
		return TRANS_SEQ;
	}

	public void setTRANS_SEQ(String tRANS_SEQ) {
		TRANS_SEQ = tRANS_SEQ;
	}

	public String getWKSTN_ID() {
		return WKSTN_ID;
	}

	public void setWKSTN_ID(String wKSTN_ID) {
		WKSTN_ID = wKSTN_ID;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public byte[] getINVOICE_QRCODE() {
		return INVOICE_QRCODE;
	}

	public void setINVOICE_QRCODE(byte[] iNVOICE_QRCODE) {
		INVOICE_QRCODE = iNVOICE_QRCODE;
	}

	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}

	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}

	public String getCREATE_USER_ID() {
		return CREATE_USER_ID;
	}

	public void setCREATE_USER_ID(String cREATE_USER_ID) {
		CREATE_USER_ID = cREATE_USER_ID;
	}

	public String getUPDATE_USER_ID() {
		return UPDATE_USER_ID;
	}

	public void setUPDATE_USER_ID(String uPDATE_USER_ID) {
		UPDATE_USER_ID = uPDATE_USER_ID;
	}

	public Date getUPDATE_DATE() {
		return UPDATE_DATE;
	}

	public void setUPDATE_DATE(Date uPDATE_DATE) {
		UPDATE_DATE = uPDATE_DATE;
	}

	public byte[] getINVOICE_XML() {
		return INVOICE_XML;
	}

	public void setINVOICE_XML(byte[] iNVOICE_XML) {
		INVOICE_XML = iNVOICE_XML;
	}
}
