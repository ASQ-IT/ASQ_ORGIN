
package asq.pos.loyalty.neqaty.gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NeqatyWSAPI-redeemOption complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NeqatyWSAPI-redeemOption">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="redeemPoints" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="redeemAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="redeemCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NeqatyWSAPI-redeemOption", propOrder = {
    "redeemPoints",
    "redeemAmount",
    "redeemCode"
})
public class NeqatyWSAPIRedeemOption {

    protected double redeemPoints;
    protected double redeemAmount;
    protected int redeemCode;

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
     * Gets the value of the redeemAmount property.
     * 
     */
    public double getRedeemAmount() {
        return redeemAmount;
    }

    /**
     * Sets the value of the redeemAmount property.
     * 
     */
    public void setRedeemAmount(double value) {
        this.redeemAmount = value;
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

}
