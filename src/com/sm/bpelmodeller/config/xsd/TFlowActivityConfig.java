//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.12.05 at 04:42:20 PM GMT+04:00 
//


package com.sm.bpelmodeller.config.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TFlowActivityConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TFlowActivityConfig">
 *   &lt;complexContent>
 *     &lt;extension base="{http://sm.com/bpel/stomodel-config}TActivityConfig">
 *       &lt;sequence>
 *         &lt;element name="countOfWaitedResults" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TFlowActivityConfig", propOrder = {
    "countOfWaitedResults"
})
public class TFlowActivityConfig
    extends TActivityConfig
{

    @XmlSchemaType(name = "unsignedInt")
    protected Long countOfWaitedResults;

    /**
     * Gets the value of the countOfWaitedResults property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCountOfWaitedResults() {
        return countOfWaitedResults;
    }

    /**
     * Sets the value of the countOfWaitedResults property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCountOfWaitedResults(Long value) {
        this.countOfWaitedResults = value;
    }

}