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

@XmlAccessorType(XmlAccessType.PROPERTY)
public class Quiz {
  
    private final Category category = new Category();
    ArrayList<Question> questions;

    public static void main(String[] args){
    
    }

    public  Quiz() {
        this.questions = new ArrayList<>();
    }
    /**
     * @return the category
     */
   
    @XmlElement(name="question")
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

    
    @XmlElements({
    @XmlElement(name="question", type=TrueFalseQuestion.class),
    @XmlElement(name="question", type=ShortAnswerQuestion.class),
    @XmlElement(name="question", type=MultiChoiceQuestion.class),
    @XmlElement(name="question", type=NumericalQuestion.class)
    })
    /**
     * @return the questions
     */
    //@XmlElement(name="question")
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * @param question the questions to set
     */
    public void setQuestions(Question question) {
        Question mcq = question;
        this.questions.add(mcq);
    }

}
