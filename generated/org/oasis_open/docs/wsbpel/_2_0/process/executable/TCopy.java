//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.26 at 04:44:27 PM GMT+04:00 
//


package org.oasis_open.docs.wsbpel._2_0.process.executable;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for tCopy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tCopy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}from"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}to"/>
 *       &lt;/sequence>
 *       &lt;attribute name="keepSrcElementName" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tBoolean" default="no" />
 *       &lt;attribute name="ignoreMissingFromData" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tBoolean" default="no" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCopy", propOrder = {
    "from",
    "to"
})
public class TCopy
    extends TExtensibleElements
{

    @XmlElement(required = true)
    protected TFrom from;
    @XmlElement(required = true)
    protected TTo to;
    @XmlAttribute
    protected TBoolean keepSrcElementName;
    @XmlAttribute
    protected TBoolean ignoreMissingFromData;

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link TFrom }
     *     
     */
    public TFrom getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link TFrom }
     *     
     */
    public void setFrom(TFrom value) {
        this.from = value;
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link TTo }
     *     
     */
    public TTo getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link TTo }
     *     
     */
    public void setTo(TTo value) {
        this.to = value;
    }

    /**
     * Gets the value of the keepSrcElementName property.
     * 
     * @return
     *     possible object is
     *     {@link TBoolean }
     *     
     */
    public TBoolean getKeepSrcElementName() {
        if (keepSrcElementName == null) {
            return TBoolean.NO;
        } else {
            return keepSrcElementName;
        }
    }

    /**
     * Sets the value of the keepSrcElementName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TBoolean }
     *     
     */
    public void setKeepSrcElementName(TBoolean value) {
        this.keepSrcElementName = value;
    }

    /**
     * Gets the value of the ignoreMissingFromData property.
     * 
     * @return
     *     possible object is
     *     {@link TBoolean }
     *     
     */
    public TBoolean getIgnoreMissingFromData() {
        if (ignoreMissingFromData == null) {
            return TBoolean.NO;
        } else {
            return ignoreMissingFromData;
        }
    }

    /**
     * Sets the value of the ignoreMissingFromData property.
     * 
     * @param value
     *     allowed object is
     *     {@link TBoolean }
     *     
     */
    public void setIgnoreMissingFromData(TBoolean value) {
        this.ignoreMissingFromData = value;
    }

}
