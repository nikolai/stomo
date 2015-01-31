//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.26 at 04:44:27 PM GMT+04:00 
//


package org.oasis_open.docs.wsbpel._2_0.process.executable;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for tActivity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tActivity">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}targets" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}sources" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="suppressJoinFailure" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tBoolean" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tActivity", propOrder = {
    "targets",
    "sources"
})
@XmlSeeAlso({
    TAssign.class,
    TWait.class,
    TFlow.class,
    TValidate.class,
    TEmpty.class,
    TRethrow.class,
    TCompensateScope.class,
    TForEach.class,
    TExit.class,
    TRepeatUntil.class,
    TCompensate.class,
    TScope.class,
    TWhile.class,
    TThrow.class,
    TIf.class,
    TReply.class,
    TPick.class,
    TReceive.class,
    TSequence.class,
    TInvoke.class
})
public class TActivity
    extends TExtensibleElements
{

    protected TTargets targets;
    protected TSources sources;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String name;
    @XmlAttribute
    protected TBoolean suppressJoinFailure;

    /**
     * Gets the value of the targets property.
     * 
     * @return
     *     possible object is
     *     {@link TTargets }
     *     
     */
    public TTargets getTargets() {
        return targets;
    }

    /**
     * Sets the value of the targets property.
     * 
     * @param value
     *     allowed object is
     *     {@link TTargets }
     *     
     */
    public void setTargets(TTargets value) {
        this.targets = value;
    }

    /**
     * Gets the value of the sources property.
     * 
     * @return
     *     possible object is
     *     {@link TSources }
     *     
     */
    public TSources getSources() {
        return sources;
    }

    /**
     * Sets the value of the sources property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSources }
     *     
     */
    public void setSources(TSources value) {
        this.sources = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the suppressJoinFailure property.
     * 
     * @return
     *     possible object is
     *     {@link TBoolean }
     *     
     */
    public TBoolean getSuppressJoinFailure() {
        return suppressJoinFailure;
    }

    /**
     * Sets the value of the suppressJoinFailure property.
     * 
     * @param value
     *     allowed object is
     *     {@link TBoolean }
     *     
     */
    public void setSuppressJoinFailure(TBoolean value) {
        this.suppressJoinFailure = value;
    }

}
