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
@XmlRootElement(name="unit")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Unit {
    private String unit_name ;
    private int multiplier;

    /**
     * @return the unit_name
     */
    @XmlElement(name="unit_name")
    public String getUnit_name() {
        return unit_name;
    }

    /**
     * @param unit_name the unit_name to set
     */
    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    /**
     * @return the multiplier
     */
    @XmlElement(name="multiplier")
    public int getMultiplier() {
        return multiplier;
    }

    /**
     * @param multiplier the multiplier to set
     */
    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}
