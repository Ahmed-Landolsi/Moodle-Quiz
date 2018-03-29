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
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MatchingQuestion extends Question{
    @XmlTransient
    private final String type = "matching";
    public static void main(String[] args){}

    public MatchingQuestion() {
        this.setType(type);
        this.answersnum = new ArrayList<>();
        this.answers = new ArrayList<>();
        this.subquestions = new ArrayList<>();
    }
    @Override
    @XmlTransient
    public ArrayList<Answer> getAnswers() {
        return super.answers;
    }
    @Override
    @XmlElement(name="subquestion")
    public ArrayList<Subquestion> getSubquestions() {
        return super.subquestions;
    }
    
    @Override
    @XmlElement(name="shuffleanswers")
    public boolean isShuffleanswers (){
        return super.isShuffleanswers();
    }
    

}
