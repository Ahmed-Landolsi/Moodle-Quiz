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
@XmlRootElement(name="quiz")

@XmlAccessorType(XmlAccessType.PROPERTY)
public class CR_Quiz {
  
    private final Category category = new Category();
    CR_Question question;

    public static void main(String[] args){
    
    }

    public  CR_Quiz() {
        this.question = new CR_Question();
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
        @XmlElement(name="question", type=CR_Question.class)
    })
    /**
     * @return the questions
     */
    //@XmlElement(name="question")
    public CR_Question getQuestion() {
        return question;
    }

    /**
     * @param question the questions to set
     */
    public void setQuestion(CR_Question question) {
        CR_Question q = question;
        this.question = q;
    }

}
