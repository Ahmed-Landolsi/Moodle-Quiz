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
public class NumericalQuestion extends Question{
    @XmlTransient
    private final String type = "numerical";
    public static void main(String[] args){}

    public NumericalQuestion() {
        this.setType(type);
        this.answersnum = new ArrayList<>();
        this.answers = new ArrayList<>();
        
    }
    @Override
    @XmlTransient
    public ArrayList<Answer> getAnswers() {
        return super.answers;
    }
    @Override
    @XmlElement(name="answer")
    public ArrayList<AnswerNumerical> getAnswersnum() {
        return super.answersnum;
    }
    @Override
    @XmlElementWrapper(name="units")
    @XmlElement(name="unit")
    public ArrayList<Unit> getUnits (){
        return super.getUnits();
    }
    @Override
    @XmlElement(name="unitpenalty")
    public float getUnitpenalty (){
        return super.getUnitpenalty();
    }
    @Override
    @XmlElement(name="unitgradingtype")
    public int getUnitgradingtype (){
        return super.getUnitgradingtype();
    }
    @Override
    @XmlElement(name="showunits")
    public int getShowunits (){
        return super.getShowunits();
    }
    @Override
    @XmlElement(name="unitsleft")
    public int getUnitsleft (){
        return super.getUnitsleft();
    }

}
