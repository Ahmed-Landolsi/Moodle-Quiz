/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import javax.xml.bind.annotation.*;
/**
 *
 * @author foufou
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Image {
    private String name;
    private String path = "/" ;
    private String encoding = "base64";
    private String encodedstring;

    
    /**
     * @return the encodedstring
     */
    @XmlValue
    public String getEncodedstring() {
        return encodedstring;
    }

    /**
     * @param encodedstring the encodedstring to set
     */
    public void setEncodedstring(String encodedstring) {
        this.encodedstring = encodedstring;
    }
    /**
     * @return the name
     */
    @XmlAttribute(name="name")
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the path
     */
    @XmlAttribute(name="path")
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the encoding
     */
    @XmlAttribute(name="encoding")
    public String getEncoding() {
        return encoding;
    }

    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    
    
}
