/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot.data;

import javafx.scene.paint.Color;

/**
 *
 * @author tobak11
 */
public class LineData {

    public static enum Type {
        SOLID, DASH, DOT_LINE
    };

    private double width;   
    private Color color;
    private Type lineType;

    public LineData(Color color, double width, Type lineType) {
        this.color = color;
        this.width = width;
        this.lineType = lineType;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Type getLineType() {
        return lineType;
    }    
}
