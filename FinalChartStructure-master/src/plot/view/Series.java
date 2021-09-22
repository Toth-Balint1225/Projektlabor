/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot.view;

import plot.data.DataSeries;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.shape.Polyline;
import plot.data.LineData;

/**
 *
 * @author tobak11
 */
public class Series extends Group {
    private DoubleProperty widthProperty;
    private DoubleProperty heightProperty;
    
    private List<Polyline> lines;
    private double seriesLayoutY = 0;
    
    public Series(ArrayList<DataSeries> dsList, DoubleProperty widthProperty, DoubleProperty heightProperty){
        this.widthProperty = widthProperty;
        this.heightProperty = heightProperty;
        
        lines = new ArrayList<>();
        
        // find absolute minimum and maximum values of all series
        double minValue = dsList.get(0).getMinValue();
        double maxValue = dsList.get(0).getMaxValue();
        double yRange;
        
        for (DataSeries s : dsList) {
            if (s.getMinValue()<minValue)
                minValue = s.getMinValue();
            
            if (s.getMaxValue()>maxValue)
                maxValue = s.getMaxValue();
        }        
        yRange = Math.abs(minValue)+Math.abs(maxValue);
        
        // init polylines objects with points 
        double dataLength = dsList.get(0).getData().length;
        double pixIncrementerValue = widthProperty.doubleValue()/dataLength;
        
        for(int i=0;i<dsList.size();i++){
            double[] data = dsList.get(i).getData();
           
            double[] points = new double[(int)dataLength*2];
            
            for(int j=0;j<dataLength;j++){
                points[2*j] = j*pixIncrementerValue;
                points[2*j+1] = heightProperty.doubleValue() -(((data[j]+Math.abs(minValue))/yRange) * heightProperty.doubleValue());
                
                if(points[2*j+1]>seriesLayoutY){
                    seriesLayoutY=points[2*j+1];
                }
            }

            Polyline poly = new Polyline(points);
            lines.add(poly);
            poly.setStroke(dsList.get(i).getLineData().getColor());
            poly.setStrokeWidth(dsList.get(i).getLineData().getWidth());

            if(dsList.get(i).getLineData().getLineType().equals(LineData.Type.DASH)){
                poly.getStrokeDashArray().addAll(4.0,8.0);
            }

            this.getChildren().add(poly);
        }        
        
        // resize listeners
        this.widthProperty.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // polyline update
                //change X coordinates only
                for (Polyline line : lines) {
                    ObservableList<Double> points = line.getPoints();
                    for(int j=0; j<points.size()/2;j++){
                        points.set(2*j, j * widthProperty.subtract(80).doubleValue()/dataLength+48);
                    }
                }
            }
        });        

        final double finalMinValue = minValue;
        
        this.heightProperty.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // polyline update
                //change Y coordinates only
                for (int i = 0; i < dsList.size(); i++) {
                    DataSeries ds = dsList.get(i);
                    final double[] data = ds.getData();
                    
                    Polyline line = lines.get(i);
                    ObservableList<Double> points = line.getPoints();
                    for(int j=0; j<points.size()/2;j++){
                        points.set(2*j+1, heightProperty.subtract(32).doubleValue() -
                                ((data[j]+Math.abs(finalMinValue))/yRange) * heightProperty.subtract(32).doubleValue());
                        
                        if (points.get(2*j+1) > seriesLayoutY){
                            seriesLayoutY = points.get(2*j+1);
                        }
                    }
                }
            }
        });        

    }

    public Series(ArrayList<DataSeries> dsList, DoubleProperty widthProperty, DoubleProperty heightProperty, Axis x, Axis y){
        this.widthProperty = widthProperty;
        this.heightProperty = heightProperty;

        lines = new ArrayList<>();

        // find absolute minimum and maximum values of all series
        double minValue = dsList.get(0).getMinValue();
        double maxValue = dsList.get(0).getMaxValue();
        double yRange;

        for (DataSeries s : dsList) {
            if (s.getMinValue()<minValue)
                minValue = s.getMinValue();

            if (s.getMaxValue()>maxValue)
                maxValue = s.getMaxValue();
        }
        yRange = Math.abs(minValue)+Math.abs(maxValue);

        // init polylines objects with points
        double dataLength = dsList.get(0).getData().length;
        double pixIncrementerValue = widthProperty.doubleValue()/dataLength;

        for(int i=0;i<dsList.size();i++){
            double[] data = dsList.get(i).getData();

            double[] points = new double[(int)dataLength*2];

            for(int j=0;j<dataLength;j++){
                points[2*j] = j*pixIncrementerValue;
                points[2*j+1] = heightProperty.doubleValue() -(Math.log10(((data[j]+Math.abs(minValue))/yRange) * heightProperty.doubleValue()));

                if(points[2*j+1]>seriesLayoutY){
                    seriesLayoutY=points[2*j+1];
                }
            }

            Polyline poly = new Polyline(points);
            lines.add(poly);
            poly.setStroke(dsList.get(i).getLineData().getColor());
            poly.setStrokeWidth(dsList.get(i).getLineData().getWidth());

            if(dsList.get(i).getLineData().getLineType().equals(LineData.Type.DASH)){
                poly.getStrokeDashArray().addAll(4.0,8.0);
            }

            this.getChildren().add(poly);
        }

        // resize listeners
        this.widthProperty.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // polyline update
                //change X coordinates only
                for (Polyline line : lines) {
                    ObservableList<Double> points = line.getPoints();
                    for(int j=0; j<points.size()/2;j++){
                        points.set(2*j, (j * widthProperty.subtract(80).doubleValue()/dataLength+48));
                        System.out.println(j * widthProperty.subtract(80).doubleValue()/dataLength+48);
                    }
                }
            }
        });

        final double finalMinValue = minValue;

        this.heightProperty.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // polyline update
                //change Y coordinates only
                for (int i = 0; i < dsList.size(); i++) {
                    DataSeries ds = dsList.get(i);
                    final double[] data = ds.getData();

                    Polyline line = lines.get(i);
                    ObservableList<Double> points = line.getPoints();
                    for(int j=0; j<points.size()/2;j++){
                        points.set(2*j+1, heightProperty.subtract(32).doubleValue() -
                                ((data[j]+Math.abs(finalMinValue))/yRange) * heightProperty.subtract(32).doubleValue());

                        if (points.get(2*j+1) > seriesLayoutY){
                            seriesLayoutY = points.get(2*j+1);
                        }
                    }
                }
            }
        });

    }
    

    public double getSeriesLayoutY() {
        return seriesLayoutY;
    }
}
