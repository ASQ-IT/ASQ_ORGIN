package asq.pos.zatca.invoice.models;

public class OnboardingData {
			
	private String otp; 
	private String sourceSystem;
	private String storeID;
	private String registerID;
	private String registerHostName;
	private String csr;
	private String type;
	
	

	public OnboardingData() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "OnboardingData [otp=" + otp + ",sourceSystem=" + sourceSystem + ", storeID=" + storeID
				+ ", registerID=" + registerID + ", registerHostName=" + registerHostName + ", type=" + type + ", csr=" + csr + "]";
	}

	public OnboardingData(boolean isCSIDGenerated, String otp, String sourceSystem, String storeID, String registerID, String registerHostName,
			String type, String csr) {
		super();
		this.otp = otp;
		this.sourceSystem = sourceSystem;
		this.storeID = storeID;
		this.registerID = registerID;
		this.registerHostName = registerHostName;
		this.type = type;
		this.csr = csr;

	}

	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
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
	public String getCsr() {
		return csr;
	}
	public void setCsr(String csr) {
		this.csr = csr;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
