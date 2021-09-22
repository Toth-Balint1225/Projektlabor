/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot.view;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author BRAIN-1
 */
class FigureContainer extends BorderPane {
    
    private Pane plotPane;

    public FigureContainer() {
        setPadding(new Insets(20, 20, 20, 20));       
    }
    
    public void addPlot(PlotGroup plotGroup){
        plotPane = new Pane();
        plotGroup.heightProperty().bind(plotPane.heightProperty());
        plotGroup.widthProperty().bind(plotPane.widthProperty());

        plotPane.getChildren().add(plotGroup);
        setCenter(plotPane); 
    } 
}
