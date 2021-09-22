/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plotscenegraph;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.paint.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author tobak11
 */
public class PlotSceneGraph extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
//        //!!!!!!!!!!!!!!!!!!
//        //!! 1. LINECHART !!
//        //!!!!!!!!!!!!!!!!!!
//        
        double timeWindow = 2.0;
        double samplingFreq = 64;
        
        float[] cosSignal = SignalUtil.generateCosine(1.0, 2.0, samplingFreq, timeWindow);    
        float[] sinSignal = SignalUtil.generateSine(1.0, 2.0, samplingFreq, timeWindow);
        
        DataSeries cosSeries = new DataSeries("Cos(x)", Color.RED, 2, samplingFreq, cosSignal, timeWindow, 0.0, false);
        DataSeries sinSeries = new DataSeries("Sin(x)", Color.GREEN, 4, samplingFreq, sinSignal, timeWindow, 0.0, true);
        
        ArrayList<DataSeries> ds = new ArrayList<>(Arrays.asList(cosSeries,sinSeries));
        LineChart lineChart = new LineChart(ds);
       
        getBasicPlotWindow("Plot Scene Graph", lineChart, primaryStage).show();
//        
//        //!!!!!!!!!!!!!!!!!!
//        //!! 2. HISTOGRAM !!
//        //!!!!!!!!!!!!!!!!!!
//        
        Stage secondaryStage = new Stage();
        
        DataBar cars2 = new DataBar("Cars", Color.BLUE, 1, 24, true);
        DataBar ships2 = new DataBar("Ships", Color.GREEN, 1, 73, false);
        DataBar trains2 = new DataBar("Trains", Color.RED, 1, 9, true);
        DataBar buses2 = new DataBar("Buses", Color.BROWN, 1, 53, true);
        
        ArrayList<DataBar> vehicleContainer = new ArrayList<>(Arrays.asList(cars2, ships2, trains2, buses2));
        
        Histogram histChart = new Histogram(vehicleContainer);
        
        getBasicPlotWindow("Plot Scene Graph", histChart, secondaryStage).show();
//        
//        //!!!!!!!!!!!!!!!!!!!
//        //!! 3. AREA CHART !!
//        //!!!!!!!!!!!!!!!!!!!
//        
        Stage tertiaryStage = new Stage();
        
        RandomDataUtil rdu = new RandomDataUtil();
        float[] rndData13 = rdu.getRandomData(128, 32, -16, 30);
        float[] rndData23 = rdu.getRandomData(128, 32, -16, 30);
        
        DataSeries rndSeries13 = new DataSeries("Random 1", Color.RED, 2, 64, rndData13, 2.0, 0.0, false);
        DataSeries rndSeries23 = new DataSeries("Random 2", Color.GREEN, 2, 64, rndData23, 2.0, 0.0, false);
        
        ArrayList<DataSeries> ds3 = new ArrayList<>(Arrays.asList(rndSeries13, rndSeries23));
        AreaChart areaChart = new AreaChart(ds3);
       
        getBasicPlotWindow("Plot Scene Graph", areaChart, tertiaryStage).show();
//        
//        //!!!!!!!!!!!!!!!!!!!!!
//        //!! 4. MARKER CHART !!
//        //!!!!!!!!!!!!!!!!!!!!!
//        
        Stage quaternaryStage = new Stage();
        
        RandomDataUtil rdu4 = new RandomDataUtil();
        float[] rndData14 = rdu4.getRandomData(128, 32, -16, 30);
        float[] rndData24 = rdu4.getRandomData(128, 32, -16, 30);
        
        DataSeries rndSeries14 = new DataSeries("Random 1", Color.RED, 2, 64, rndData14, 2.0, 0.0, false);
        DataSeries rndSeries24 = new DataSeries("Random 2", Color.GREEN, 2, 64, rndData24, 2.0, 0.0, false);
        
        ArrayList<DataSeries> ds4 = new ArrayList<>(Arrays.asList(rndSeries14, rndSeries24));
        MarkerChart markerChart = new MarkerChart(ds4);
       
        getBasicPlotWindow("Plot Scene Graph", markerChart, quaternaryStage).show();
        
//        //!!!!!!!!!!!!!!!!!!!!!!!!!!
//        //!! 5. BOUNDED LINECHART !!
//        //!!!!!!!!!!!!!!!!!!!!!!!!!!
        
        Stage quinaryStage = new Stage();
        
        RandomDataUtil rdu5 = new RandomDataUtil();
        float[] rndData15 = rdu5.getRandomData(128, 32, -16, 30);
        float[] rndData25 = rdu5.getRandomData(128, 32, -16, 30);
        
        BoundedDataSeries rndSeries15 = new BoundedDataSeries("Random 1", Color.RED, 1, 64, rndData15, 2.0, 0.0, false, 4.0, 7.0);
        BoundedDataSeries rndSeries25 = new BoundedDataSeries("Random 2", Color.BLUE, 6, 64, rndData25, 2.0, 0.0, false, 6.0, 2.0);
         
        ArrayList<BoundedDataSeries> bds5 = new ArrayList<>(Arrays.asList(rndSeries15, rndSeries25));
        BoundedLineChart bdlChart = new BoundedLineChart(bds5);
       
        getBasicPlotWindow("Plot Scene Graph", bdlChart, quinaryStage).show();
        
//        //!!!!!!!!!!!!!!!!!!
//        //!! 6. BOX CHART !!
//        //!!!!!!!!!!!!!!!!!!
        
        Stage senaryStage = new Stage();
        
        DataBar cars6 = new DataBar("Cars", Color.BLUE, 1, 24, true);
        DataBar ships6 = new DataBar("Ships", Color.GREEN, 1, 73, false);
        DataBar trains6 = new DataBar("Trains", Color.RED, 1, 9, true);
        DataBar buses6 = new DataBar("Buses", Color.BROWN, 1, 53, true);
        
        ArrayList<DataBar> vehicleContainer6 = new ArrayList<>(Arrays.asList(cars6, ships6, trains6, buses6));
        
        BoxChart boxChart = new BoxChart(vehicleContainer6);
        
        getBasicPlotWindow("Plot Scene Graph", boxChart, senaryStage).show();
        
//        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        //!! 7. MULTI BOUNDED LINECHART !!
//        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        
        Stage septenaryStage = new Stage();
        
        RandomDataUtil rdu7 = new RandomDataUtil();
        
        GridPane mbdlChartGrid = new GridPane();
        mbdlChartGrid.setPadding(new Insets(10,10,10,10));
        mbdlChartGrid.setVgap(10);
        mbdlChartGrid.setHgap(10);
        
        int rowCounter = -1;
        
        for(int i=0;i<9;i++){
            float[] tempRndData17 = rdu7.getRandomData(128, 32, -16, 30);
            float[] tempRndData27 = rdu7.getRandomData(128, 32, -16, 30);

            BoundedDataSeries rndSeries17 = new BoundedDataSeries("Random 1", Color.RED, 1, 64, tempRndData17, 2.0, 0.0, false, 4.0, 7.0);
            BoundedDataSeries rndSeries27 = new BoundedDataSeries("Random 2", Color.BLUE, 6, 64, tempRndData27, 2.0, 0.0, false, 6.0, 2.0);

            ArrayList<BoundedDataSeries> bds6 = new ArrayList<>(Arrays.asList(rndSeries17, rndSeries27));
            MultiBoundedLineChart mbdlChart = new MultiBoundedLineChart(bds6);
            
            if(i%3==0){
                rowCounter++;
            }
            
            mbdlChartGrid.add(mbdlChart, rowCounter, i%3); 
        }
 
        getBasicPlotWindow("Plot Scene Graph", mbdlChartGrid, septenaryStage).show();
        
        
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //!! 7. RESIZEABLE BOUNDED LINECHART !!
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        
        Stage octonaryStage = new Stage();
        
        RandomDataUtil rdu8 = new RandomDataUtil();
        float[] rndData18 = rdu8.getRandomData(128, 32, -16, 30);
        float[] rndData28 = rdu8.getRandomData(256, 16, 0, 90);
        
        BoundedDataSeries rndSeries18 = new BoundedDataSeries("Random 1", Color.RED, 1, 64, rndData18, 128.0, 0.0, false, 4.0, 7.0);
        BoundedDataSeries rndSeries28 = new BoundedDataSeries("Random 2", Color.BLUE, 6, 64, rndData28, 256.0, 0.0, false, 6.0, 2.0);
         
        ArrayList<BoundedDataSeries> bds8 = new ArrayList<>(Arrays.asList(rndSeries18, rndSeries28));
        ResizeableBoundedLineChart rbdlChart = new ResizeableBoundedLineChart(bds8); 
        
        getBasicPlotWindow("Plot Scene Graph", rbdlChart, octonaryStage).show();
        
        rbdlChart.createBindings();
        rbdlChart.createListeners();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static Stage getBasicPlotWindow(String title, Pane chart, Stage stage){
        Scene scene = new Scene(chart, 800, 450);  
        stage.setTitle(title);
        stage.setScene(scene);
         
        return stage;
    }
    
}
