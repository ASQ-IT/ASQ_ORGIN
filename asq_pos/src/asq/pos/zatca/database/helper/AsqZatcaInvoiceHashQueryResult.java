/**
 * 
 */
package asq.pos.zatca.database.helper;

import java.util.Date;

import dtv.data2.access.AbstractQueryResult;
import dtv.data2.access.IObjectId;

/**
 * @author RA20221457
 *
 */
public class AsqZatcaInvoiceHashQueryResult extends AbstractQueryResult {

	private static final long serialVersionUID = 1L;

	private Long icv;
	private String invoice_Id;
	private Date invoice_Date;
	private Long organization_Id;
	private String invoice_Hash;

	public Long getIcv() {
		return icv;
	}

	public void setIcv(Long icv) {
		this.icv = icv;
	}

	public String getInvoice_Id() {
		return invoice_Id;
	}

	public void setInvoice_Id(String invoice_Id) {
		this.invoice_Id = invoice_Id;
	}

	public Date getInvoice_Date() {
		return invoice_Date;
	}

	public void setInvoice_Date(Date invoice_Date) {
		this.invoice_Date = invoice_Date;
	}

	public Long getOrganization_Id() {
		return organization_Id;
	}

	public void setOrganization_Id(Long organization_Id) {
		this.organization_Id = organization_Id;
	}

	public String getInvoice_Hash() {
		return invoice_Hash;
	}

	public void setInvoice_Hash(String invoice_Hash) {
		this.invoice_Hash = invoice_Hash;
	}

	@Override
	public String toString() {
		return "AsqZatcaInvoiceHashQueryResult [icv=" + icv + ", invoice_Id=" + invoice_Id + ", invoice_Date=" + invoice_Date + ", organization_Id=" + organization_Id + ", invoice_Hash="
				+ invoice_Hash + "]";
	}

	@Override
	protected IObjectId getObjectIdImpl() {
		return null;
	}

}
