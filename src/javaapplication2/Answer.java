/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */
package javaapplication2;
import javax.xml.bind.annotation.*;
//import java.util.ArrayList;

/**
 *
 * @author foufou
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Answer {
    private int fraction = 0;
    private final Feedback feedback = new Feedback();
    private String text;
    private String format = "moodle_auto_format";

    
    public static void main(String[] args){}
    

    /**
     * @return the fraction
     */
    @XmlAttribute

    public int getFraction() {
        return fraction;
    }

    /**
     * @param fraction the fraction to set
     */
    
    public void setFraction(int fraction) {
        this.fraction = fraction;
    }

    /**
     * @return the feedback
     */
    @XmlElement
    public Feedback getFeedback() {
        return feedback;
    }

    /**
     * @param feedback the feedback to set
     */
    public void setFeedback(String feedback) {
        this.feedback.setText(feedback);
    }

    /**
     * @return the text
     */
    @XmlElement
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the format
     */
    @XmlAttribute
    public String getFormat() {
        return format;
    }

    /**
     * @param format the format to set
     */
    public void setFormat(String format) {
        this.format = format;
        this.feedback.setFormat(format);
    }
}
