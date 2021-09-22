/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plotproject;

import java.util.Arrays;
import java.util.function.IntToDoubleFunction;
import javafx.scene.paint.Color;

/**
 *
 * @author juhasz
 */
class DataSeries {

    private final double[] data;
    private final double samplingFrequency;
    private final Color color;
    private final double lineWidth;

    DataSeries(double[] data, double samplingFreq, Color color) {
        this.data = data;
        this.samplingFrequency = samplingFreq;
        this.color = color;
        this.lineWidth = 1;
    }

    DataSeries(float[] floatData, double samplingFreq, Color color) {
        this.data = new double[floatData.length];
        Arrays.parallelSetAll(this.data, (int idx) -> floatData[idx]);
        this.samplingFrequency = samplingFreq;
        this.color = color;
        this.lineWidth = 1;
    }

    DataSeries(float[] floatData, int samplingFreq, Color color, double lineWidth) {
        this.data = new double[floatData.length];
        Arrays.parallelSetAll(this.data, (int idx) -> floatData[idx]);
        this.samplingFrequency = samplingFreq;
        this.color = color;
        this.lineWidth = lineWidth;
    }

    public double[] getData() {
        return data;
    }

    public double getSamplingFrequency() {
        return samplingFrequency;
    }

    public Color getColor() {
        return color;
    }

    double getLineWidth() {
        return lineWidth;
    }
    
    
    
}
