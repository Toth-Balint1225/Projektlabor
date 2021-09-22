/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plotproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author BRAIN-1
 */
public class PlotProject extends Application {
    
    @Override
    public void start(Stage primaryStage) {

        BasicLineChart chart = new BasicLineChart();
        double samplingFreq = 64;
        float[] signal = SignalUtil.generateCosine(1.0, 2.0, samplingFreq, 2.0);
        chart.setData(signal, samplingFreq);
        getBasicPlotWindow("Sample plot", chart).show();        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    public static Stage getBasicPlotWindow(String title, Pane chart){
        AnchorPane root = new AnchorPane();
        root.getChildren().add(chart);
        
        AnchorPane.setTopAnchor(chart, 0.0);
        AnchorPane.setBottomAnchor(chart, 0.0);
        AnchorPane.setLeftAnchor(chart, 0.0);
        AnchorPane.setRightAnchor(chart, 0.0);
        
        Scene scene = new Scene(root, 600, 400);  
        Stage primaryStage = new Stage();
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
         
//        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                int value = 0;
//                System.out.println("key event" + event);
//                switch (event.getCode()) {
//                    case S: // take snapshot
//                            takeSnapshot(scene);
//                        break;
//                }
//            }
//        });

        return primaryStage;
    }
}
