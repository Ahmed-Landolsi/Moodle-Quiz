 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */
package javaapplication2;
import java.util.ArrayList;
import javax.xml.bind.annotation.*;
//import java.util.ArrayList;

/**
 *
 * @author foufou
 */
@XmlRootElement(name="subquestion")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Subquestion {
    @XmlAttribute(name="format")
    private String format;
    private String text;
    private AnswerMatching answer ;
    
    public static void main(String[] args){}
    
    public Subquestion(){
        
    }
    

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

    /**
     * @return the answer
     */
    @XmlElement(name="answer")
    public AnswerMatching getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(AnswerMatching answer) {
        this.answer = answer;
    }

    /**
     * @param format the format to set
     */
    public void setFormat(String format) {
        this.format = format;
    }

}
