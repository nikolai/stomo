//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.26 at 04:44:27 PM GMT+04:00 
//


package org.oasis_open.docs.wsbpel._2_0.process.executable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * 				XSD Authors: The child element correlation needs to be a Local Element Declaration, 
 * 				because there is another correlation element defined for the non-invoke activities.
 * 			
 * 
 * <p>Java class for tCorrelationsWithPattern complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tCorrelationsWithPattern">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;element name="correlation" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tCorrelationWithPattern" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCorrelationsWithPattern", propOrder = {
    "correlation"
})
public class TCorrelationsWithPattern
    extends TExtensibleElements
{

    @XmlElement(required = true)
    protected List<TCorrelationWithPattern> correlation;

    /**
     * Gets the value of the correlation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the correlation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCorrelation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TCorrelationWithPattern }
     * 
     * 
     */
    public List<TCorrelationWithPattern> getCorrelation() {
        if (correlation == null) {
            correlation = new ArrayList<TCorrelationWithPattern>();
        }
        return this.correlation;
    }

}
