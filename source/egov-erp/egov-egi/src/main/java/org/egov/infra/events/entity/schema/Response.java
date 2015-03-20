//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2011.12.20 at 11:49:41 AM IST
//

package org.egov.infra.events.entity.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="callback" type="{}CallBackType" minOccurs="0"/>
 *         &lt;element name="email" type="{}EmailType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sms" type="{}SMSType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "callback", "email", "sms" })
@XmlRootElement(name = "response")
public class Response {

    protected CallBackType callback;
    protected List<EmailType> email;
    protected List<SMSType> sms;

    /**
     * Gets the value of the callback property.
     *
     * @return possible object is {@link CallBackType }
     */
    public CallBackType getCallback() {
        return callback;
    }

    /**
     * Sets the value of the callback property.
     *
     * @param value
     *            allowed object is {@link CallBackType }
     */
    public void setCallback(final CallBackType value) {
        this.callback = value;
    }
   

    /**
     * Gets the value of the email property.
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the email property.
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getEmail().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EmailType }
     */
    public List<EmailType> getEmail() {
        if (email == null)
            email = new ArrayList<EmailType>();
        return this.email;
    }

    /**
     * Gets the value of the sms property.
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the sms property.
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getSms().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link SMSType }
     */
    public List<SMSType> getSms() {
        if (sms == null)
            sms = new ArrayList<SMSType>();
        return this.sms;
    }

}
