/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot.data;

/**
 *
 * @author tobak11
 */

public class DataSeries {
    private final String name; 
    private final double samplingFreq;
    private final double[] data;
    private final double timeWindow;
    private final double startTime;
    private final LineData lineData;
    
    private final double maxValue;
    private final double minValue;

    public DataSeries(String name, LineData linedata, double samplingFreq, float[] data, double timeWindow, double startTime){
        this.name=name;
        this.lineData = linedata; 
        this.samplingFreq=samplingFreq;
        this.timeWindow=timeWindow;
        this.startTime=startTime;
        
        this.data=new double[data.length];
        //Copying and converting data && finding maxValue
        this.data[0]=data[0];
        
        double tempMax = data[0];
        double tempMin = data[0];
        
        
        for(int i=1;i<data.length;i++){
            this.data[i]=data[i];
            
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
    
    public boolean isDashed() {
        if (lineData.getLineType().equals(LineData.Type.DASH))
            return true;
        return false;
    }
    
    public double getMinValue() {
        return minValue;
    }

    public LineData getLineData() {
        return lineData;
    }   
}
