/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot.utils;

import plot.data.AxisData;

import java.util.ArrayList;

/**
 *
 * @author tobak11
 */
public class VisualUtility {
    public static ArrayList<Double> xTickCoordinateGenerator(double interval, double stepX, int length, double xWidth, double yHeight){
        ArrayList<Double> coordinates = new ArrayList<>();
        
        int numOfTicks = (int)Math.floor(interval/stepX);
        double pixIncrement = xWidth/numOfTicks;
        
        for(int i=0;i<numOfTicks+1;i++){
            coordinates.add(i*pixIncrement+48);
            coordinates.add(0.0);
            coordinates.add(i*pixIncrement+48);
            coordinates.add((-1)*(double)length);
        }
        
        return coordinates;
    }
    
    public static ArrayList<Double> yTickCoordinateGenerator(double interval, double stepY, int length, double xWidth, double yHeight){
        ArrayList<Double> coordinates = new ArrayList<>();
        
        int numOfTicks = (int)Math.floor(interval/stepY);
        double pixIncrement = yHeight/numOfTicks;
        
        for(int i=0;i<numOfTicks+1;i++){
            coordinates.add(48d);
            coordinates.add(i*pixIncrement);
            coordinates.add(48+(double)length);
            coordinates.add(i*pixIncrement);
        }
        
        return coordinates;
    }

    //sajat
    public static ArrayList<Double> xTickCoordinateGeneratorLog(double interval, int length, double xWidth, double yHeight, AxisData axisData){
        ArrayList<Double> coordinates = new ArrayList<>();
        if(axisData.getLogType() == AxisData.LogType.TWO) {
            for (int j = 0; j < (int) interval; j++) {
                for (int i = 0; i < 3; i++) {
                    coordinates.add((j * xWidth / (int) interval) + (log2(i) * (xWidth / (int) interval) + 48));
                    coordinates.add(0.0);
                    coordinates.add((j * xWidth / (int) interval) + (log2(i) * (xWidth / (int) interval) + 48));
                    coordinates.add((-1) * (double) length);
                }
            }
        }else if(axisData.getLogType() == AxisData.LogType.SIXTEEN) {
            for (int j = 0; j < (int) interval; j++) {
                for (int i = 0; i < 17; i++) {
                    coordinates.add((j * xWidth / (int) interval) + (log16(i) * (xWidth / (int) interval) + 48));
                    coordinates.add(0.0);
                    coordinates.add((j * xWidth / (int) interval) + (log16(i) * (xWidth / (int) interval) + 48));
                    coordinates.add((-1) * (double) length);
                }
            }
        }else{
            for (int j = 0; j < (int) interval; j++) {
                for (int i = 0; i < 11; i++) {
                    coordinates.add((j * xWidth / (int) interval) + (Math.log10(i) * (xWidth / (int) interval) + 48));
                    coordinates.add(0.0);
                    coordinates.add((j * xWidth / (int) interval) + (Math.log10(i) * (xWidth / (int) interval) + 48));
                    coordinates.add((-1) * (double) length);
                }
            }
        }
        return coordinates;
    }

    public static ArrayList<Double> yTickCoordinateGeneratorLog(double interval, int length, double xWidth, double yHeight, AxisData axisData){
        ArrayList<Double> coordinates = new ArrayList<>();

        if(axisData.getLogType() == AxisData.LogType.SIXTEEN){
            for (int j = 0; j < (int) interval; j++) {
                for (int i = 0; i < 17; i++) {
                    coordinates.add(48d);
                    coordinates.add(yHeight - ((j * yHeight / (int) interval) + (log16(i) * (yHeight / (int) interval))));
                    coordinates.add(48 + (double) length);
                    coordinates.add(yHeight - ((j * yHeight / (int) interval) + (log16(i) * (yHeight / (int) interval))));
                }
            }
        }else if(axisData.getLogType() == AxisData.LogType.TWO){
            for (int j = 0; j < (int) interval; j++) {
                for (int i = 0; i < 3; i++) {
                    coordinates.add(48d);
                    coordinates.add(yHeight - ((j * yHeight / (int) interval) + (log2(i) * (yHeight / (int) interval))));
                    coordinates.add(48 + (double) length);
                    coordinates.add(yHeight - ((j * yHeight / (int) interval) + (log2(i) * (yHeight / (int) interval))));
                }
            }
        }else {
            for (int j = 0; j < (int) interval; j++) {
                for (int i = 0; i < 11; i++) {
                    coordinates.add(48d);
                    coordinates.add(yHeight - ((j * yHeight / (int) interval) + (Math.log10(i) * (yHeight / (int) interval))));
                    coordinates.add(48 + (double) length);
                    coordinates.add(yHeight - ((j * yHeight / (int) interval) + (Math.log10(i) * (yHeight / (int) interval))));
                }
            }
        }

        return coordinates;
    }

    private static double log2(int N)
    {
        return Math.log(N) / Math.log(2);
    }

    private static double log16(int N)
    {
        return Math.log(N) / Math.log(16);
    }
}