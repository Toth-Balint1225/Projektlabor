/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plotscenegraph;

import javafx.scene.paint.Color;

/**
 *
 * @author tobak11
 */
public class DataBar {
    private final String name;
    private final Color color;
    private final double scaling;
    private final double value;
    private final boolean isShaded;
    
    public DataBar(String name, Color color, double scaling, double value, boolean isShaded){
        this.name=name;
        this.color=color;
        this.scaling=scaling;
        this.value=value;
        this.isShaded=isShaded;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public double getScaling() {
        return scaling;
    }

    public double getValue() {
        return value;
    }

    public boolean isIsShaded() {
        return isShaded;
    }
}
