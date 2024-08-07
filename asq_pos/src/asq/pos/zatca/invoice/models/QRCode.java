package asq.pos.zatca.invoice.models;

import java.nio.charset.StandardCharsets;

import dtv.util.Base64;
import oracle.dss.util.xdo.common.util.Hex;

public class QRCode {

	private String sellerName;
	private String vatNumber;
	private String invoiceTimeStamp;
	private String invoiceTotal;
	private String vatTotal;
	private String hashOfXML;
	private String signatureECDA;
	private byte[] publicKeyECDA;
	private byte[] signatureCSID;
	private String invoiceDate;

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public QRCode(String sellerName, String vatNumber, String invoiceTimeStamp, String invoiceDate, String invoiceTotal, String vatTotal,
			String hashOfXML, String signatureECDA2, byte[] publicKeyECDA2, byte[] signatureCSID2) {
		super();
		this.sellerName = sellerName;
		this.vatNumber = vatNumber;
		this.invoiceTimeStamp = invoiceTimeStamp;
		this.invoiceDate = invoiceDate;
		this.invoiceTotal = invoiceTotal;
		this.vatTotal = vatTotal;
		this.hashOfXML = hashOfXML;
		this.signatureECDA = signatureECDA2;
		this.publicKeyECDA = publicKeyECDA2;
		this.signatureCSID = signatureCSID2;
	}

	@Override
	public String toString() {
		return "QRCode [sellerName=" + sellerName + ", vatNumber=" + vatNumber + ", invoiceTimeStamp="
				+ invoiceTimeStamp + ", invoiceTotal=" + invoiceTotal + ", vatTotal=" + vatTotal + ", hashOfXML="
				+ hashOfXML + ", signatureECDA=" + signatureECDA + ", publicKeyECDA=" + publicKeyECDA
				+ ", signatureCSID=" + signatureCSID + "]";
	}

	public String getHexString(QRCode qrCode)  {

		String tag1 = getHexString(1, qrCode.getSellerName());
		String tag2 = getHexString(2, qrCode.getVatNumber());
//		String tag3 = getHexString(3, qrCode.getInvoiceDate()+"T"+qrCode.getInvoiceTimeStamp()+"Z");
		String tag3 = getHexString(3, qrCode.getInvoiceDate()+"T"+qrCode.getInvoiceTimeStamp());
		String tag4 = getHexString(4, qrCode.getInvoiceTotal());
		String tag5 = getHexString(5, qrCode.getVatTotal());
		String tag6 = getHexString(6, qrCode.getHashOfXML());
		String tag7 = getHexString(7, qrCode.getSignatureECDA());
		String tag8 = getHexBytes(8, qrCode.getPublicKeyECDA());//tag  8 byte and public parameter byte 
		String tag9 = getHexBytes(9, qrCode.getSignatureCSID());// tag 9 byte and suganbature parameter byte  

		String finalHexString = tag1 + tag2 + tag3 + tag4 + tag5 + tag6 + tag7 + tag8+ tag9;
		return finalHexString;
		
	}
	
	
	public QRCode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public byte[] decodeHex(String qrCodeDetailsString) {
		byte[] decodedHex = Hex.parse(qrCodeDetailsString);
		return decodedHex;
	}

	public String encodeBase64String (byte[] decodedHex) {
		String encodedBase64String = Base64.byteArrayToBase64(decodedHex);
		return encodedBase64String;
	}
	
	public String getHexString(int tagNo, String tagValue) {
		String tagNumLengthHexString = Integer.toHexString(tagNo);

		byte[] tagValueBytes = tagValue.getBytes(StandardCharsets.UTF_8);
//		int tagValueLength = tagValue.length();
		int tagValueLength=tagValueBytes.length;
		String tagValueLengthHexString = Integer.toHexString(tagValueLength);
		 if(tagValueLengthHexString.length()==1) {
			 tagValueLengthHexString = "0"+tagValueLengthHexString;
		 }
		String tagValueHexString = Hex.dump(tagValueBytes);
       // System.out.println("Tag : "+tagNo+" - "+(0 + tagNumLengthHexString) + ( tagValueLengthHexString) + tagValueHexString);
		return (0 + tagNumLengthHexString) + ( tagValueLengthHexString) + tagValueHexString;
		
	}
	
	public String getHexBytes(int tagNo, byte[] tagValue) {
		String tagNumLengthHexString = Integer.toHexString(tagNo);

		int tagValueLength = tagValue.length;
		String tagValueLengthHexString = Integer.toHexString(tagValueLength);
		if(tagValueLengthHexString.length()==1) {
			  tagValueLengthHexString = "0"+tagValueLengthHexString;
		  }
//		byte[] tagValueBytes = tagValue.getBytes(StandardCharsets.UTF_8);
		String tagValueHexString = Hex.dump(tagValue);
		return ((0 + tagNumLengthHexString) + (tagValueLengthHexString) + tagValueHexString);
	}

	private String bytesArrayToString(byte[] tagValue) {
		String byteToString="";
		for(byte t:tagValue) {
			byteToString=byteToString+t;
		}
		return byteToString;
	}
	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	public String getInvoiceTimeStamp() {
		return invoiceTimeStamp;
	}

	public void setInvoiceTimeStamp(String invoiceTimeStamp) {
		this.invoiceTimeStamp = invoiceTimeStamp;
	}

	public String getInvoiceTotal() {
		return invoiceTotal;
	}

	public void setInvoiceTotal(String invoiceTotal) {
		this.invoiceTotal = invoiceTotal;
	}

	public String getVatTotal() {
		return vatTotal;
	}

	public void setVatTotal(String vatTotal) {
		this.vatTotal = vatTotal;
	}

	public String getHashOfXML() {
		return hashOfXML;
	}

	public void setHashOfXML(String hashOfXML) {
		this.hashOfXML = hashOfXML;
	}

	public String getSignatureECDA() {
		return signatureECDA;
	}

	public void setSignatureECDA(String signatureECDA) {
		this.signatureECDA = signatureECDA;
	}

	public byte[] getPublicKeyECDA() {
		return publicKeyECDA;
	}

	public void setPublicKeyECDA(byte[] publicKeyECDA) {
		this.publicKeyECDA = publicKeyECDA;
	}

	public byte[] getSignatureCSID() {
		return signatureCSID;
	}

	public void setSignatureCSID(byte[] signatureCSID) {
		this.signatureCSID = signatureCSID;
	}

	

	

}
