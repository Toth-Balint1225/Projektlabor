/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot.data;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 *
 * @author tobak11
 */
class MarkerData {
    private double size;
    private Shape shape;
    private Color color;

    public MarkerData(double size, Shape shape, Color color) {
        this.size = size;
        this.shape = shape;
        this.color = color;
    } 
}
