/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;

/**
 *
 * @author BRAIN-1
 */
public class PlotGroup extends Group {

    private DoubleProperty widthProperty;
    private DoubleProperty heightProperty;
    
    public PlotGroup() {
        widthProperty = new SimpleDoubleProperty();
        heightProperty = new SimpleDoubleProperty();
    }    
    
    public DoubleProperty widthProperty() {
        return widthProperty;
    }

    public DoubleProperty heightProperty() {
        return heightProperty;
    }
}
