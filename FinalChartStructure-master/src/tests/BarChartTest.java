/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import plot.data.AxisData;
import plot.data.DataSeries;
import plot.data.LineData;
import plot.data.TitleData;
import plot.utils.SignalUtil;
import plot.view.BarChart;

/**
 *
 * @author kiand
 */
public class BarChartTest extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // generate input data
        double timeWindow = 1.0;
        double samplingFreq = 32;

        float[] cosSignal = SignalUtil.generateCosine(1.0, 2.0, samplingFreq, timeWindow);
        float[] sinSignal = SignalUtil.generateSine(1.0, 2.0, samplingFreq, timeWindow);

        float[] sample = {0, 1, 2, 3, 4, 5};

        // generate data series from input data
        DataSeries cosSeries = new DataSeries("Cos(x)", 
                new LineData(Color.GREEN, 1, LineData.Type.SOLID),
                samplingFreq, cosSignal, timeWindow, 0.0);
        DataSeries sampleSeries = new DataSeries("Sample",
                new LineData(Color.GREEN, 1, LineData.Type.SOLID),
                samplingFreq, sample, timeWindow, 0.0);
        DataSeries sinSeries = new DataSeries("Sin(x)",
                new LineData(Color.BLUE, 1, LineData.Type.DASH),
                samplingFreq, sinSignal, timeWindow, 0.0);

        ArrayList<DataSeries> ds = new ArrayList<>(Arrays.asList(cosSeries, sinSeries));

        BarChart chart = new BarChart()
                .series(ds)
                .title(new TitleData("Trigonometric functions", Font.font(24)))
                .xAxis(new AxisData(new TitleData("Time", Font.font(16)), null, 1, 0.25, new LineData(Color.BLACK, 1.0, LineData.Type.SOLID)))
                .yAxis(new AxisData(new TitleData("Values", Font.font(16)), null, 1, 0.10, new LineData(Color.BLACK, 1.0, LineData.Type.SOLID)))
                .legend()
                .gridHorizontal()
                //.spacing(20.0, 10.0)
                .barOrientation(BarChart.Orientation.HORIZONTAL)
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