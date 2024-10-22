package asq.retail.xstore.countrypack.common.taxfree.fintrax.op;

import java.util.List;

import dtv.pos.common.ConfigurationMgr;
import dtv.pos.framework.form.BasicEditModel;
import dtv.pos.framework.form.CodeEnumValueWrapper;
import dtv.pos.framework.form.EditModelField;
import dtv.pos.framework.form.ValueWrapperFactory;
import dtv.pos.framework.form.config.FieldDependencyConfig;
import dtv.pos.iframework.form.ICardinality;
import dtv.pos.iframework.form.IListFieldElementDescr;
import dtv.pos.iframework.form.IValueWrapperFactory;
import dtv.pos.iframework.security.SecuredObjectID;
import dtv.xst.dao.com.CodeLocator;
import dtv.xst.dao.com.ICodeValue;

public class AsqPlanetVatClaimEditModel extends BasicEditModel {
	
	 private static final IValueWrapperFactory codeWrapperFactory_ = ValueWrapperFactory.makeWrapperFactory(CodeEnumValueWrapper.class);
	 
	public static final String ASQ_MOBILE_NUMBER_FIELD = "custMobileNumber";
    public static final String ASQ_GENDER = "gender";
	private static final String ASQ_CUSTOMER_FIRST_NAME = "firstName";
	private static final String ASQ_CUSTOMER_LAST_NAME = "lastName";
	private static final String ASQ_NATIONAL = "asqNationality";
	private static final String ASQ_NATIONAL_RES = "asqNationalityResidence";
	private static final String ASQ_ADDRESS = "address1";
	private static final String ASQ_TAX_DOC = "asqTaxDocument";
	private static final String ASQ_TAX_DOC_NBR = "asqTaxDocumentNumber";
	private static final String ASQ_TAX_ISSUE = "asqTaxIssuedBy";
	private static final String ASQ_EXP_DATE = "asqExpirationDate";
	private static final String ASQ_BIRTH_DATE = "asqBirthDate";

	private String custMobileNumber;
	private String gender;
	private String firstName;
	private String lastName;
	private String asqNationality;
	private String asqNationalityResidence;
	private String address1;

	private String asqTaxDocument;
	private String asqTaxDocumentNumber;
	private String asqTaxIssuedBy;
	private String asqExpirationDate;
	private String asqBirthDate;
	
	String category="";

	public String getAsqTaxDocument() {
		return asqTaxDocument;
	}

	public void setAsqTaxDocument(String asqTaxDocument) {
		this.asqTaxDocument = asqTaxDocument;
	}

	public String getAsqTaxDocumentNumber() {
		return asqTaxDocumentNumber;
	}

	public void setAsqTaxDocumentNumber(String asqTaxDocumentNumber) {
		this.asqTaxDocumentNumber = asqTaxDocumentNumber;
	}

	public String getAsqTaxIssuedBy() {
		return asqTaxIssuedBy;
	}

	public void setAsqTaxIssuedBy(String asqTaxIssuedBy) {
		this.asqTaxIssuedBy = asqTaxIssuedBy;
	}

	public String getAsqExpirationDate() {
		return asqExpirationDate;
	}

	public void setAsqExpirationDate(String asqExpirationDate) {
		this.asqExpirationDate = asqExpirationDate;
	}

	public String getCustMobileNumber() {
		return custMobileNumber;
	}

	public void setCustMobileNumber(String custMobileNumber) {
		this.custMobileNumber = custMobileNumber;
	}

	@SuppressWarnings("unchecked")
	public AsqPlanetVatClaimEditModel() {
		super(FF.getTranslatable("_asqCaptureCustMobileNumber&EmailAddressTitle"),
				FF.getTranslatable("_asqCaptureCustMobileNumber&EmailAddressDescription"));
		addField(ASQ_MOBILE_NUMBER_FIELD, String.class);
		//addField(ASQ_CUSTOMER_EMAIL_FIELD, String.class);
		addField(ASQ_CUSTOMER_FIRST_NAME, String.class);
		addField(ASQ_CUSTOMER_LAST_NAME, String.class);
		addField(ASQ_GENDER, String.class);
	
		/*
		 * List<? extends ICodeValue> types =
		 * CodeLocator.getCodeValues(ConfigurationMgr.getOrganizationId(), category);
		 * 
		 * this.addField(EditModelField.makeFieldDefUnsafe(this,gender,String.class,2,
		 * null,ICardinality.OPTIONAL, types , null, codeWrapperFactory_, null ) );
		 */
		addField(ASQ_ADDRESS, String.class);
		addField(ASQ_NATIONAL, String.class);
		addField(ASQ_NATIONAL_RES, String.class);
		addField(ASQ_TAX_DOC, String.class);
		addField(ASQ_TAX_DOC_NBR, String.class);
		addField(ASQ_TAX_ISSUE, String.class);
		addField(ASQ_EXP_DATE, String.class);
		addField(ASQ_BIRTH_DATE, String.class);
		initializeFieldState();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAsqNationality() {
		return asqNationality;
	}

	public void setAsqNationality(String asqNationality) {
		this.asqNationality = asqNationality;
	}

	public String getAsqNationalityResidence() {
		return asqNationalityResidence;
	}

	public void setAsqNationalityResidence(String asqNationalityResidence) {
		this.asqNationalityResidence = asqNationalityResidence;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAsqBirthDate() {
		return asqBirthDate;
	}

	public void setAsqBirthDate(String asqBirthDate) {
		this.asqBirthDate = asqBirthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
