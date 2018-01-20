/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */
package javaapplication2;

import java.util.ArrayList;
//import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author foufou
 */
@XmlRootElement(name="questiontext")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder={"text","images"})
public class QuestionText {
    private String text = "";
    private String format = "moodle_auto_format";
    private ArrayList<Image> images;
    
    public QuestionText(){
       this.images = new ArrayList<>();
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
     * @return the format
     */
    @XmlAttribute(name="format")
    public String getFormat() {
        return format;
    }

    /**
     * @param format the format to set
     */ 
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @return the images
     */
    @XmlElement(name="file") 
    public ArrayList<Image> getImages() {
        return images;
    }

    /**
     * @param image the images to set
     */
    public void setImages(Image image) {
        Image img = image;
        this.images.add(img);
    }


}
