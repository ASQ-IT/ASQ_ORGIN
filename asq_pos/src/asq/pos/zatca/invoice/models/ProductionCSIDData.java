package asq.pos.zatca.invoice.models;

public class ProductionCSIDData {
	 
	private String storeID;
	private String registerID;
	private String registerHostName;
	private String complianceRequestID;
	private String complianceSecret;
	private String sourceSystem;
	private String type;
	private String complianceBinarySecurityToken;
	
	public ProductionCSIDData() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "OnboardingData [storeID=" + storeID
				+ ", registerID=" + registerID + ", registerHostName=" + registerHostName + ", complianceRequestID=" + complianceRequestID + ", complianceSecret=" + complianceSecret + ", sourceSystem=" + sourceSystem + ", type=" + type + ",complianceBinarySecurityToken=" + complianceBinarySecurityToken + " ]";
	}

	public ProductionCSIDData( String storeID, String registerID, String registerHostName,
			String complianceRequestID, String complianceSecret, String sourceSystem, String type, String complianceBinarySecurityToken) {
		super();
		
		this.storeID = storeID;
		this.registerID = registerID;
		this.registerHostName = registerHostName;
		this.complianceRequestID = complianceRequestID;
		this.complianceSecret = complianceSecret;
		this.sourceSystem = sourceSystem;
		this.type = type;
		this.complianceBinarySecurityToken = complianceBinarySecurityToken;
		
	}
	public String getStoreID() {
		return storeID;
	}
	public void setStoreID(String storeID) {
		this.storeID = storeID;
	}
	public String getRegisterID() {
		return registerID;
	}
	public void setRegisterID(String registerID) {
		this.registerID = registerID;
	}
	public String getRegisterHostName() {
		return registerHostName;
	}
	public void setRegisterHostName(String registerHostName) {
		this.registerHostName = registerHostName;
	}
	public String getComplianceRequestID() {
		return complianceRequestID;
	}
	public void setComplianceRequestID(String complianceRequestID) {
		this.complianceRequestID = complianceRequestID;
	}
	public String getComplianceSecret() {
		return complianceSecret;
	}

	public void setComplianceSecret(String complianceSecret) {
		this.complianceSecret = complianceSecret;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComplianceBinarySecurityToken() {
		return complianceBinarySecurityToken;
	}

	public void setComplianceBinarySecurityToken(String complianceBinarySecurityToken) {
		this.complianceBinarySecurityToken = complianceBinarySecurityToken;
	}
	
}
