/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;


/**
 *
 * @author foufou
 */
@XmlRootElement
//@XmlType(name="foo")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Quiz {
  
    private final Category category = new Category();
    ArrayList<MultiChoiceQuestion> questions;

//    @XmlAttribute
//    private String type = "category";
    public static void main(String[] args){}

    public Quiz() {
        this.questions = new ArrayList<>();
    }
    /**
     * @return the category
     */
//    @XmlElementWrapper (
//    name = "##default", 
//    namespace = "##default", 
//    nillable = false
//    )
    //@XmlAttachmentRef(name="type")
   
    @XmlElement(name="question")
    //@XmlAnyElement(value = TextDomHandler.class)
    public Category getCategory() {
        
        return category;
    }
    //

    /**
     * @param text the category to set
     */
    
    public void setCategory(String text) {
        this.category.setText(text);
    }

    /**
     * @return the questions
     */
    @XmlElement(name="question")
    public List<MultiChoiceQuestion> getQuestions() {
        return questions;
    }

    /**
     * @param question the questions to set
     */
    public void setQuestions(MultiChoiceQuestion question) {
        MultiChoiceQuestion mcq = question;
        this.questions.add(mcq);
    }

}
