/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.util.ArrayList;
import javax.xml.bind.annotation.*;


@XmlRootElement(name="testcase")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CR_Testcase {
    private double mark = 1.0000;
    private int hiderestiffail = 0;
    private int useasexample = 0;
    private int testtype = 0;
    private CR_Stdin stdin = new CR_Stdin();
    private CR_Extra extra = new CR_Extra();
    private CR_Display display = new CR_Display();
    private CR_Testcode testcode = new CR_Testcode();
    private CR_Expected expected = new CR_Expected();    
    
    public static void main(String[] args){}
    
    public CR_Testcase(){
        
    }

    /**
     * @return the mark
     */
    @XmlAttribute
    public double getMark() {
        return mark;
    }

    /**
     * @param mark the mark to set
     */
    public void setMark(double mark) {
        this.mark = mark;
    }

    /**
     * @return the hiderestiffail
     */
    @XmlAttribute
    public int getHiderestiffail() {
        return hiderestiffail;
    }

    /**
     * @param hiderestiffail the hiderestiffail to set
     */
    public void setHiderestiffail(int hiderestiffail) {
        this.hiderestiffail = hiderestiffail;
    }

    /**
     * @return the useasexample
     */
    @XmlAttribute
    public int getUseasexample() {
        return useasexample;
    }

    /**
     * @param useasexample the useasexample to set
     */
    public void setUseasexample(int useasexample) {
        this.useasexample = useasexample;
    }

    /**
     * @return the testtype
     */
    @XmlAttribute
    public int getTesttype() {
        return testtype;
    }

    /**
     * @param testtype the testtype to set
     */
    public void setTesttype(int testtype) {
        this.testtype = testtype;
    }

    /**
     * @return the stdin
     */
    @XmlElement
    public CR_Stdin getStdin() {
        return stdin;
    }

    /**
     * @param stdin the stdin to set
     */
    public void setStdin(CR_Stdin stdin) {
        this.stdin = stdin;
    }

    /**
     * @return the extra
     */
    @XmlElement
    public CR_Extra getExtra() {
        return extra;
    }

    /**
     * @param extra the extra to set
     */
    public void setExtra(CR_Extra extra) {
        this.extra = extra;
    }

    /**
     * @return the display
     */
    @XmlElement
    public CR_Display getDisplay() {
        return display;
    }

    /**
     * @param display the display to set
     */
    public void setDisplay(CR_Display display) {
        this.display = display;
    }

    /**
     * @return the testcode
     */
    @XmlElement
    public CR_Testcode getTestcode() {
        return testcode;
    }

    /**
     * @param testcode the testcode to set
     */
    public void setTestcode(CR_Testcode testcode) {
        this.testcode = testcode;
    }

    /**
     * @return the expected
     */
    @XmlElement
    public CR_Expected getExpected() {
        return expected;
    }

    /**
     * @param expected the expected to set
     */
    public void setExpected(CR_Expected expected) {
        this.expected = expected;
    }
    
}
