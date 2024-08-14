
package asq.pos.loyalty.neqaty.gen;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NeqatyWSAPI-optionsData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NeqatyWSAPI-optionsData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pointsBalance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="tier" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="redeemOptions" type="{urn:margento}NeqatyWSAPI-redeemOption" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NeqatyWSAPI-optionsData", propOrder = {
    "pointsBalance",
    "tier",
    "redeemOptions"
})
public class NeqatyWSAPIOptionsData {

    protected double pointsBalance;
    protected int tier;
    protected List<NeqatyWSAPIRedeemOption> redeemOptions;

    /**
     * Gets the value of the pointsBalance property.
     * 
     */
    public double getPointsBalance() {
        return pointsBalance;
    }

    /**
     * Sets the value of the pointsBalance property.
     * 
     */
    public void setPointsBalance(double value) {
        this.pointsBalance = value;
    }

    /**
     * Gets the value of the tier property.
     * 
     */
    public int getTier() {
        return tier;
    }

    /**
     * Sets the value of the tier property.
     * 
     */
    public void setTier(int value) {
        this.tier = value;
    }

    /**
     * Gets the value of the redeemOptions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the redeemOptions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRedeemOptions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NeqatyWSAPIRedeemOption }
     * 
     * 
     */
    public List<NeqatyWSAPIRedeemOption> getRedeemOptions() {
        if (redeemOptions == null) {
            redeemOptions = new ArrayList<NeqatyWSAPIRedeemOption>();
        }
        return this.redeemOptions;
    }

}
