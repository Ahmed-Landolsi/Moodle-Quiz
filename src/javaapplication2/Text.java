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
public class Text {
    private String text = "$course$/test";

    /**
     * @return the text
     */
    @XmlElement(name="text")
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
    
}
