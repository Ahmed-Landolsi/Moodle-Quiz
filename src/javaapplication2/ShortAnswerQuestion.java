 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.util.ArrayList;
import javax.xml.bind.annotation.*;

/**
 *
 * @author foufou
 */
//
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder={"name", "questiontext", "generalfeedback", "defaultgrade", "penalty", "hidden", "single", "shuffleanswers", "answernumbering", "correctfeedback", "partiallycorrectfeedback", "incorrectfeedback", "usecase", "answers"})

public class ShortAnswerQuestion extends Question{
    @XmlTransient
    private final String type = "shortanswer";
    //private int usecase;

   
    public static void main(String[] args){}
    
    public ShortAnswerQuestion() {
        this.setType(type);
        this.answers = new ArrayList<>();
    }
    @Override
    @XmlElement(name="usecase")
    public int getUsecase (){
        return super.getUsecase();
    }

}
