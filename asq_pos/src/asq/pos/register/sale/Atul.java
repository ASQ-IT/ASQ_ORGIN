package asq.pos.register.sale;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Atul {

	public static void main(String[] args) {

		// ######## This is for discount case start
		// calculation for price amount (unit price without vat)
		BigDecimal priceAmount = new BigDecimal(350);

		priceAmount = priceAmount.divide((new BigDecimal(.15).add(new BigDecimal(1))), 2, RoundingMode.HALF_UP);

		System.out.println("priceAmount = " + priceAmount);

		// discount amount
		BigDecimal discountAmount = new BigDecimal(17.50);
		discountAmount = discountAmount.divide(new BigDecimal(1.15), 2, RoundingMode.HALF_UP);

		System.out.println("discountAmount = " + discountAmount);

		// LineExtensionAmount amount
		BigDecimal LineQty = new BigDecimal(2);
		BigDecimal LineExtensionAmount = priceAmount;
		LineExtensionAmount = (LineExtensionAmount.subtract(discountAmount)).multiply(LineQty);

		System.out.println("LineExtensionAmount = " + LineExtensionAmount);
		// ######## This is for discount case ends

	}

}
