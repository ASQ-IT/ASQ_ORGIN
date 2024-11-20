package asq.pos.register.sale;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;

public class ASQTEST {

	public static void main(String[] args) {

		// ######## This is for discount case startR
		// calculation for price amount (unit price without vat)
		BigDecimal priceAmount = new BigDecimal(315);

		priceAmount = priceAmount.divide((new BigDecimal(.15).add(new BigDecimal(1))), 2, RoundingMode.HALF_UP);

		System.out.println("priceAmount = " + priceAmount);

		// discount amount
		BigDecimal discountAmount = new BigDecimal(35);
		discountAmount = discountAmount.divide(new BigDecimal(1.15), MathContext.DECIMAL64);
		System.out.println("discountAmount before scale = " + discountAmount);

		// discountAmount = Math.round(discountAmount.doubleValue());
		System.out.println("discountAmount after scale = " + discountAmount.floatValue());
		System.out.println("discountAmount after scale = " + Math.round(discountAmount.floatValue()));

		// LineExtensionAmount amount
		BigDecimal LineQty = new BigDecimal(2);
		BigDecimal LineExtensionAmount = priceAmount;
		LineExtensionAmount = (LineExtensionAmount.subtract(discountAmount)).multiply(LineQty);

		System.out.println("LineExtensionAmount = " + LineExtensionAmount);
		// ######## This is for discount case ends

		BigDecimal priceAmount2 = new BigDecimal(304.35);

		// 10 perc
		BigDecimal discountPercAmount = new BigDecimal(0.1);
		discountPercAmount = priceAmount2.multiply(discountPercAmount);
		discountPercAmount = discountPercAmount.setScale(2, RoundingMode.HALF_UP);
		System.out.println(" discountPercAmount = " + discountPercAmount);

		System.out.println("##############");
		priceAmount = new BigDecimal(30.43478260869565);

		System.out.println(" priceAmount HALF_UP = " + priceAmount.setScale(2, RoundingMode.HALF_UP));
		System.out.println(" priceAmount HALF_DOWN = " + priceAmount.setScale(2, RoundingMode.HALF_DOWN));
		System.out.println(" priceAmount HALF_EVEN = " + priceAmount.setScale(2, RoundingMode.HALF_EVEN));
		System.out.println(" priceAmount FLOOR = " + priceAmount.setScale(2, RoundingMode.FLOOR));

		System.out.println("##############");
		System.out.println(" priceAmount UP = " + priceAmount.setScale(2, RoundingMode.UP));
		System.out.println(" priceAmount CEILING = " + priceAmount.setScale(2, RoundingMode.CEILING));

		System.out.println(dtv.util.Base64.byteArrayToBase64("anku2693@Gogu".getBytes()));

		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.CEILING);
		for (Number n : Arrays.asList(12, 123.12345, 0.23, 0.1, 2341234.212431324)) {
			Double d = n.doubleValue();
			System.out.println(df.format(d));
		}

		System.out.println("##############");
		double value = 30.43478260869565;
		String result = String.format("%.2f", value);
		System.out.print(result);

	}

}
