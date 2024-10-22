package asq.pos.planet.vat.claim.service;

import com.oracle.shaded.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsqPlanetVatClaimShopperObj {
	
		 private String firstName;
		 private String lastName;
		 private String gender;
		 private String nationality;
		 private String countryOfResidence;
		 private String phoneNumber;
		 AsqPlanetVatClaimShopperIdDocObj birth;
		 AsqPlanetVatClaimShopperIdDocObj shopperIdentityDocument;
		 
		 private String shopperCode;
		 private String shopperFullName;
		 private String shopperIdentityCardType;
		 private String shopperIdentityCardNumber;


		 // Getter Methods 

		 public String getFirstName() {
		  return firstName;
		 }

		 public String getLastName() {
		  return lastName;
		 }

		 public String getGender() {
		  return gender;
		 }

		 public String getNationality() {
		  return nationality;
		 }

		 public String getCountryOfResidence() {
		  return countryOfResidence;
		 }

		 public String getPhoneNumber() {
		  return phoneNumber;
		 }

		 public AsqPlanetVatClaimShopperIdDocObj getBirth() {
		  return birth;
		 }

		 public AsqPlanetVatClaimShopperIdDocObj getShopperIdentityDocument() {
		  return shopperIdentityDocument;
		 }

		 // Setter Methods 

		 public void setFirstName(String firstName) {
		  this.firstName = firstName;
		 }

		 public void setLastName(String lastName) {
		  this.lastName = lastName;
		 }

		 public void setGender(String gender) {
		  this.gender = gender;
		 }

		 public void setNationality(String nationality) {
		  this.nationality = nationality;
		 }

		 public void setCountryOfResidence(String countryOfResidence) {
		  this.countryOfResidence = countryOfResidence;
		 }

		 public void setPhoneNumber(String phoneNumber) {
		  this.phoneNumber = phoneNumber;
		 }

		 public void setBirth(AsqPlanetVatClaimShopperIdDocObj birth) {
		  this.birth = birth;
		 }

		 public void setShopperIdentityDocument(AsqPlanetVatClaimShopperIdDocObj shopperIdentityDocument) {
		  this.shopperIdentityDocument = shopperIdentityDocument;
		 }

		public String getShopperCode() {
			return shopperCode;
		}

		public void setShopperCode(String shopperCode) {
			this.shopperCode = shopperCode;
		}

		public String getShopperFullName() {
			return shopperFullName;
		}

		public void setShopperFullName(String shopperFullName) {
			this.shopperFullName = shopperFullName;
		}

		public String getShopperIdentityCardType() {
			return shopperIdentityCardType;
		}

		public void setShopperIdentityCardType(String shopperIdentityCardType) {
			this.shopperIdentityCardType = shopperIdentityCardType;
		}

		public String getShopperIdentityCardNumber() {
			return shopperIdentityCardNumber;
		}

		public void setShopperIdentityCardNumber(String shopperIdentityCardNumber) {
			this.shopperIdentityCardNumber = shopperIdentityCardNumber;
		}
		 
	}
