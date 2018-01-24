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
public class TrueFalseQuestion extends Question{
    @XmlTransient
    private final String type = "truefalse";
    public static void main(String[] args){}
    
    public TrueFalseQuestion() {
        this.setType(type);
        this.answers = new ArrayList<>();
    }
}
