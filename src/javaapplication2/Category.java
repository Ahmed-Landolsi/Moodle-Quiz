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
@XmlRootElement(name="question")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Category {
    private Text text = new Text();
    private String type = "category";;

    //private String question = "question";
    /**
     * @return the text
     */
//    public void Category(){
//            this.text = new Text();
//            this.text.setText("$course$/Standard für mich");
//            this.type = "category";
//    }
    @XmlElement (name="category")
    public Text getText() {
        return text;
    }

    /**
     * @param text the text to set
     */ 
    public void setText(String text) {
        this.text.setText(text);
    }

    /**
     * @return the type
     */
    @XmlAttribute (name="type")
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
