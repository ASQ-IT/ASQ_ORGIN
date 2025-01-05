/**
 * 
 */
package asq.pos.bnpl.tamara.tender.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author RA20221457
 *
 */

@JsonIgnoreProperties(ignoreUnknown =true)
public class AsqBnplTamaraTraDisAmtObj {
	
		private String name;
		
		private AsqBnplTamaraDisAmtObj amount;
	 
		

		public AsqBnplTamaraDisAmtObj getAmount() {
			return amount;
		}

		public void setAmount(AsqBnplTamaraDisAmtObj amount) {
			this.amount = amount;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	 

		
	}


