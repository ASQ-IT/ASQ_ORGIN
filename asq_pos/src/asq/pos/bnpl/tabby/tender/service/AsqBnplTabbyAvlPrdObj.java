package asq.pos.bnpl.tabby.tender.service;

import java.util.ArrayList;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqBnplTabbyAvlPrdObj {
	
	private ArrayList<AsqBnplTabbyInstallmentsObj> installments;

	public ArrayList<AsqBnplTabbyInstallmentsObj> getInstallments() {
		return installments;
	}

	public void setInstallments(ArrayList<AsqBnplTabbyInstallmentsObj> installments) {
		this.installments = installments;
	}

}
