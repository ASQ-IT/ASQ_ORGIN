/**
 * 
 */
package asq.pos.bnpl.tabby.tender.service;

import java.util.ArrayList;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author RA20221457
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AsqBnplTabbyOrderDetailsObj {
	
	 private String reference_id;
	 ArrayList<AsqBnplTabbyItemsObj> items ;

	public ArrayList<AsqBnplTabbyItemsObj> getItems() {
		return items;
	}

	public void setItems(ArrayList<AsqBnplTabbyItemsObj> items) {
		this.items = items;
	}

	public String getReference_id() {
		return reference_id;
	}

	public void setReference_id(String reference_id) {
		this.reference_id = reference_id;
	}
}
