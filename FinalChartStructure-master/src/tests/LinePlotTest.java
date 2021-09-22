/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Polyline;
import plot.data.*;

import java.io.IOException;
import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import plot.utils.SignalUtil;
import plot.view.Chart;

/**
 *
 * @author BRAIN-1
 */
public class LinePlotTest extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // generate input data
        double timeWindow = .5d;
        double samplingFreq = 64;

        float[] cosSignal = SignalUtil.generateCosine(1d, 2.0, samplingFreq, timeWindow);
        float[] sinSignal = SignalUtil.generateSine(1d, 2.0, samplingFreq, timeWindow);

        // generate data series from input data
        DataSeries cosSeries = new DataSeries("Cos(x)", 
                new LineData(new Color(0,0,0,0), 4, LineData.Type.SOLID),
                samplingFreq, cosSignal, timeWindow, 0.0);
        DataSeries sinSeries = new DataSeries("Sin(x)", 
                new LineData(new Color(0,0,0,0), 4, LineData.Type.DASH),
                samplingFreq, sinSignal, timeWindow, 0.0);

        ArrayList<DataSeries> ds = new ArrayList<>(Arrays.asList(cosSeries, sinSeries));

        Chart chart = new Chart()
                .series(ds)
                .xAxis(new AxisData(new TitleData("", Font.font(16)), null, 1, 1,
                        new LineData(Color.BLACK, 1.0, LineData.Type.SOLID)))
                .yAxis(new AxisData(new TitleData("values", Font.font(16)), null, .5, .5,
                        new LineData(Color.BLACK, 1.0, LineData.Type.SOLID)))
                .title(new TitleData("Trigonometric functions", Font.font(24)))
            .build();

        Scene scene = new Scene(chart, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
