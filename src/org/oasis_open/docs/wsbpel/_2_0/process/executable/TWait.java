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


/**
 * <p>Java class for tWait complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tWait">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tActivity">
 *       &lt;choice>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}for"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}until"/>
 *       &lt;/choice>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tWait", propOrder = {
    "_for",
    "until"
})
public class TWait
    extends TActivity
{

    @XmlElement(name = "for")
    protected TDurationExpr _for;
    protected TDeadlineExpr until;

    /**
     * Gets the value of the for property.
     * 
     * @return
     *     possible object is
     *     {@link TDurationExpr }
     *     
     */
    public TDurationExpr getFor() {
        return _for;
    }

    /**
     * Sets the value of the for property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDurationExpr }
     *     
     */
    public void setFor(TDurationExpr value) {
        this._for = value;
    }

    /**
     * Gets the value of the until property.
     * 
     * @return
     *     possible object is
     *     {@link TDeadlineExpr }
     *     
     */
    public TDeadlineExpr getUntil() {
        return until;
    }

    /**
     * Sets the value of the until property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDeadlineExpr }
     *     
     */
    public void setUntil(TDeadlineExpr value) {
        this.until = value;
    }

}