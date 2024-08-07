package asq.pos.zatca.invoice.models;

public enum Catalog {
	  CSR_FILE_NOTFOUND(1001, "CSR file not found in specified directory."),
	  CSID_FILE_NOTFOUND(1002, "CSID file not found in specified directory."),
	  CSID_FILE_FOUND(1003, "CSID file found in specified directory."),
	  CSID_FILE_GENERATED(1004, "CSID file has been generated in the specified directory."),
	  CSID_FILE_NOT_GENERATED(1005, "CSID file has not been generated in the specified directory."),
	  CERT_FILE_NOT_GENERATED(1006, "CSID and CSR both files are not found in the specified directory. "
	  		+ "Invoice signing process will not working, please contact application support"),
	  CSID_FILE_NOTFOUND_FLAG_TRUE(1007, "CSID file not found, but property file having flag as true");
	
	  private final int code;
	  private final String description;

	  private Catalog(int code, String description) {
	    this.code = code;
	    this.description = description;
	  }

	  public String getDescription() {
	     return description;
	  }

	  public int getCode() {
	     return code;
	  }

	  @Override
	  public String toString() {
	    return code + ": " + description;
	  }
	}
