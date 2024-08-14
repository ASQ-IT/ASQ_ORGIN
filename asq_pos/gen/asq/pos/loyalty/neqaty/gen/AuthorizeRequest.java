
package asq.pos.loyalty.neqaty.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for authorizeRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="authorizeRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authenticationKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tid" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="operationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="msisdn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="redeemPoints" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="redeemCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="transactionReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authorizeRequest", propOrder = {
    "authenticationKey",
    "tid",
    "token",
    "operationType",
    "msisdn",
    "amount",
    "redeemPoints",
    "redeemCode",
    "transactionReference"
})
public class AuthorizeRequest {

    @XmlElementRef(name = "authenticationKey", type = JAXBElement.class, required = false)
    protected JAXBElement<String> authenticationKey;
    @XmlElementRef(name = "tid", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> tid;
    protected int token;
    @XmlElementRef(name = "operationType", type = JAXBElement.class, required = false)
    protected JAXBElement<String> operationType;
    @XmlElementRef(name = "msisdn", type = JAXBElement.class, required = false)
    protected JAXBElement<String> msisdn;
    protected double amount;
    protected double redeemPoints;
    protected int redeemCode;
    @XmlElementRef(name = "transactionReference", type = JAXBElement.class, required = false)
    protected JAXBElement<String> transactionReference;

    /**
     * Gets the value of the authenticationKey property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAuthenticationKey() {
        return authenticationKey;
    }

    /**
     * Sets the value of the authenticationKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAuthenticationKey(JAXBElement<String> value) {
        this.authenticationKey = value;
    }

    /**
     * Gets the value of the tid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getTid() {
        return tid;
    }

    /**
     * Sets the value of the tid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setTid(JAXBElement<Integer> value) {
        this.tid = value;
    }

    /**
     * Gets the value of the token property.
     * 
     */
    public int getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     */
    public void setToken(int value) {
        this.token = value;
    }

    /**
     * Gets the value of the operationType property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOperationType() {
        return operationType;
    }

    /**
     * Sets the value of the operationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOperationType(JAXBElement<String> value) {
        this.operationType = value;
    }

    /**
     * Gets the value of the msisdn property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMsisdn() {
        return msisdn;
    }

    /**
     * Sets the value of the msisdn property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMsisdn(JAXBElement<String> value) {
        this.msisdn = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     */
    public void setAmount(double value) {
        this.amount = value;
    }

    /**
     * Gets the value of the redeemPoints property.
     * 
     */
    public double getRedeemPoints() {
        return redeemPoints;
    }

    /**
     * Sets the value of the redeemPoints property.
     * 
     */
    public void setRedeemPoints(double value) {
        this.redeemPoints = value;
    }

    /**
     * Gets the value of the redeemCode property.
     * 
     */
    public int getRedeemCode() {
        return redeemCode;
    }

    /**
     * Sets the value of the redeemCode property.
     * 
     */
    public void setRedeemCode(int value) {
        this.redeemCode = value;
    }

    /**
     * Gets the value of the transactionReference property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTransactionReference() {
        return transactionReference;
    }

    /**
     * Sets the value of the transactionReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTransactionReference(JAXBElement<String> value) {
        this.transactionReference = value;
    }

}
