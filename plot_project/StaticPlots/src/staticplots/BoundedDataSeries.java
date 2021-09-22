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
public class BoundedDataSeries {
    private final String name; 
    private final Color color;
    private final double lineWidth;
    private final double samplingFreq;
    private final double[] data;
    private final double timeWindow;
    private final double startTime;
    private boolean isDotted;
    
    private double maxValue;
    private double minValue;
    
    private final double upperBound;
    private final double lowerBound;

    public BoundedDataSeries(String name, Color color, double lineWidth, double samplingFreq, float[] data, double timeWindow, 
            double startTime, boolean isDotted, double lowerBound, double upperBound){
        
        this.name=name;
        this.color=color;
        this.lineWidth=lineWidth;
        this.samplingFreq=samplingFreq;
        this.timeWindow=timeWindow;
        this.startTime=startTime;
        this.isDotted=isDotted;
        this.lowerBound=lowerBound;
        this.upperBound=upperBound;
        
        this.data=new double[data.length];
        //Copying and converting data && finding maxValue
        this.data[0]=data[0];
        
        double tempMax = data[0];
        double tempMin = data[0];
        
        for(int i=1;i<data.length;i++){
            this.data[i] = data[i];
            
            if(data[i]>tempMax){
                tempMax = data[i];
            }
            
            if(data[i]<tempMin){
                tempMin = data[i];
            }
        }
        
        this.maxValue=tempMax;
        this.minValue=tempMin;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public double getSamplingFreq() {
        return samplingFreq;
    }

    public double[] getData() {
        return data;
    }

    public double getTimeWindow() {
        return timeWindow;
    }

    public double getStartTime() {
        return startTime;
    }
    
    public double getMaxValue() {
        return maxValue;
    }
    
    public boolean isIsDotted() {
        return isDotted;
    }
    
    public double getMinValue() {
        return minValue;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public double getLowerBound() {
        return lowerBound;
    }
}
