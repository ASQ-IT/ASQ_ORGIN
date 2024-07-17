// Copyright (c) 2024, Oracle and/or its affiliates. All rights reserved.
// Generated using dtv.data2.access.impl.daogen.GenerateIds 2024-07-13T15:19:37
// CHECKSTYLE:OFF
package dtv.asq.dao.zatca;

/**
 * Auto generated Id Object for AsqZatcaInvoiceHash.
 *
 * @author DAOGen
 */
@SuppressWarnings("all")
public class AsqZatcaInvoiceHashId extends dtv.data2.access.AbstractObjectId {

	// Fix serialization compatibility based on the name of the DAO
	private static final long serialVersionUID = 699331087L;

	public static final String[] FIELDS = { "organizationId", "icv" };

	@Override
	public int getBaseVersion() {
		return 23;
	}

	public AsqZatcaInvoiceHashId() {
		super();
	}

	public AsqZatcaInvoiceHashId(String argObjectIdValue) {
		setValue(argObjectIdValue);
	}

	public AsqZatcaInvoiceHashId(dtv.asq.dao.zatca.impl.AsqZatcaInvoiceHashDAO argDao) {
		_organizationId = argDao.getOrganizationId();
		_icv = argDao.getIcv();
	}

	private Long _icv;

	@Override
	public String getDtxTypeName() {
		return "AsqZatcaInvoiceHash";
	}

	@Override
	public String[] getFieldNames() {
		return FIELDS;
	}

	/**
	 * Gets the value of the ICV field.
	 * 
	 * @return The value of the field.
	 */
	public Long getIcv() {
		return _icv;
	}

	/**
	 * Sets the value of the ICV field.
	 * 
	 * @param argIcv The new value of the field.
	 */
	public void setIcv(Long argIcv) {
		_icv = argIcv;
	}

	@Override
	public void setValue(String argObjectIdValue) {
		String str = argObjectIdValue;
		if (dtv.util.StringUtils.isEmpty(str)) {
			throw new dtv.data2.access.exception.DtxException("argument passed to setValue() is null or empty - a valid value must be passed");
		}
		try {
			String[] tokens = str.split("::");
			str = tokens[0];

			setOrganizationId(java.lang.Long.valueOf(str));
			str = tokens[1];

			setIcv(java.lang.Long.valueOf(str));
		} catch (Exception ee) {
			throw new dtv.data2.access.exception.DtxException("An exception occured while parsing object id string: " + argObjectIdValue, ee);
		}
	}

	@Override
	public boolean equals(Object ob) {
		if (this == ob) {
			return true;
		}
		if (!(ob instanceof AsqZatcaInvoiceHashId)) {
			return false;
		}
		AsqZatcaInvoiceHashId other = (AsqZatcaInvoiceHashId) ob;
		return ((_organizationId == null && other._organizationId == null) || (this._organizationId != null && this._organizationId.equals(other._organizationId)))
				&& ((_icv == null && other._icv == null) || (this._icv != null && this._icv.equals(other._icv)));
	}

	@Override
	public int hashCode() {
		return (((_organizationId == null) ? 0 : _organizationId.hashCode()) + ((_icv == null) ? 0 : _icv.hashCode()));
	}

	@Override
	public String toString() {
		StringBuilder buff = new StringBuilder(12 * 2);

		return buff.append(String.valueOf(_organizationId)).append("::").append(String.valueOf(_icv)).toString();
	}

	@Override
	public boolean validate() {
		if (_icv == null) {
			return false;
		}
		return true;
	}

}