
package asq.pos.loyalty.neqaty.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resultCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="resultDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="transactionReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="optionsData" type="{urn:margento}NeqatyWSAPI-optionsData" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "resultCode",
    "resultDescription",
    "token",
    "transactionReference",
    "optionsData"
})
@XmlRootElement(name = "authorizeResponse")
public class AuthorizeResponse {

    protected int resultCode;
    @XmlElementRef(name = "resultDescription", type = JAXBElement.class, required = false)
    protected JAXBElement<String> resultDescription;
    protected int token;
    @XmlElementRef(name = "transactionReference", type = JAXBElement.class, required = false)
    protected JAXBElement<String> transactionReference;
    @XmlElementRef(name = "optionsData", type = JAXBElement.class, required = false)
    protected JAXBElement<NeqatyWSAPIOptionsData> optionsData;

    /**
     * Gets the value of the resultCode property.
     * 
     */
    public int getResultCode() {
        return resultCode;
    }

    /**
     * Sets the value of the resultCode property.
     * 
     */
    public void setResultCode(int value) {
        this.resultCode = value;
    }

    /**
     * Gets the value of the resultDescription property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getResultDescription() {
        return resultDescription;
    }

    /**
     * Sets the value of the resultDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setResultDescription(JAXBElement<String> value) {
        this.resultDescription = value;
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

    /**
     * Gets the value of the optionsData property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link NeqatyWSAPIOptionsData }{@code >}
     *     
     */
    public JAXBElement<NeqatyWSAPIOptionsData> getOptionsData() {
        return optionsData;
    }

    /**
     * Sets the value of the optionsData property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link NeqatyWSAPIOptionsData }{@code >}
     *     
     */
    public void setOptionsData(JAXBElement<NeqatyWSAPIOptionsData> value) {
        this.optionsData = value;
    }

}
