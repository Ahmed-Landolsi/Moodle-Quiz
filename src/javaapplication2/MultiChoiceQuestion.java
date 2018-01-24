/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author foufou
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder={"name", "questiontext", "generalfeedback", "defaultgrade", "penalty", "hidden", "single", "shuffleanswers", "answernumbering", "correctfeedback", "partiallycorrectfeedback", "incorrectfeedback", "answers"})

public class MultiChoiceQuestion extends Question {
    @XmlTransient
    private final String type = "multichoice";
    
    public static void main(String[] args){
    }
    public MultiChoiceQuestion() {
        this.setType(type);
        this.answers = new ArrayList<>();
    }
   
    @Override
    @XmlElement(name="answernumbering")
    public String getAnswernumbering (){
        return super.getAnswernumbering();
    }
    @Override
    @XmlElement(name="single")
    public boolean isSingle (){
        return super.isSingle();
    }
    @Override
    @XmlElement(name="shuffleanswers")
    public boolean isShuffleanswers (){
        return super.isShuffleanswers();
    }
}