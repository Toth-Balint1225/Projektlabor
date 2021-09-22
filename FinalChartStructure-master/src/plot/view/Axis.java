/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot.view;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import plot.data.AxisData;
import plot.data.DataSeries;
import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.Text;
import plot.utils.VisualUtility;
/**
 *
 * @author tobak11
 */
public class Axis extends Group {

    private AxisType axisType;

    public static enum AxisType {
        HORIZONTAL, VERTICAL
    };

    protected Line axisLine;
    protected ArrayList<Double> majorTickCoordinates;
    protected ArrayList<Line> majorTickLines;
    protected ArrayList<Text> majorTickLabels;

    protected ArrayList<Double> minorTickCoordinates;
    public ArrayList<Line> minorTickLines;
       
    public Axis(AxisData axisData, AxisType axisType, ArrayList<DataSeries> ds, double xWidth, double yHeight) {
        this.axisType = axisType;
        
        if (axisType.equals(AxisType.HORIZONTAL)) {
            //AXIS
            axisLine = new Line(0, 0, xWidth, 0);
            
            axisLine.setStrokeWidth(axisData.getLineData().getWidth());
            axisLine.setFill(axisData.getLineData().getColor());
            axisLine.setStroke(axisData.getLineData().getColor());

            this.getChildren().add(axisLine);
            
            double tempMax = ds.get(0).getMaxValue();
            double tempMin = ds.get(0).getMinValue();
            double minStartTime = ds.get(0).getStartTime();
            
            for(int i=1;i<ds.size();i++){
                if(ds.get(i).getMaxValue()>tempMax){
                    tempMax = ds.get(i).getMaxValue();
                }
                if(ds.get(i).getMinValue()<tempMin){
                    tempMin = ds.get(i).getMinValue();
                }
                if(ds.get(i).getStartTime()<minStartTime){
                    minStartTime = ds.get(i).getStartTime();
                }
            }
            
            //MAJOR TICK LINES
            majorTickCoordinates = VisualUtility.xTickCoordinateGenerator(/*Math.abs(tempMax)+Math.abs(tempMin)*/6, axisData.getMajorStep(), 8, xWidth, yHeight);
            majorTickLines = new ArrayList<>();

            for (int i = 0; i < majorTickCoordinates.size()-4; i += 4) {
                Line tempLine = new Line(majorTickCoordinates.get(i+4), majorTickCoordinates.get(i + 5), majorTickCoordinates.get(i + 6), majorTickCoordinates.get(i + 7));
                majorTickLines.add(tempLine);
            }

            this.getChildren().addAll(majorTickLines);
           
            //MINOR TICK LINES
            minorTickCoordinates = VisualUtility.xTickCoordinateGenerator(Math.abs(tempMax)+Math.abs(tempMin), axisData.getMinorStep(), 4, xWidth, yHeight);
            minorTickLines = new ArrayList<>();

            for (int i = 0; i < minorTickCoordinates.size()-4; i += 4) {
                boolean majorLine = false;
                for(int j=0;j<majorTickCoordinates.size()-4;j+=4){
                    if(minorTickCoordinates.get(i+4).equals(majorTickCoordinates.get(j+4))){
                        majorLine = true;
                    }
                }
                
                if(majorLine){
                    Line tempLine = new Line(minorTickCoordinates.get(i+4), minorTickCoordinates.get(i + 5), minorTickCoordinates.get(i + 6), minorTickCoordinates.get(i + 7));
                    tempLine.setOpacity(0);
                    minorTickLines.add(tempLine);
                }else{
                    Line tempLine = new Line(minorTickCoordinates.get(i+4), minorTickCoordinates.get(i + 5), minorTickCoordinates.get(i + 6), minorTickCoordinates.get(i + 7));
                    minorTickLines.add(tempLine);
                }
            }

            this.getChildren().addAll(minorTickLines);
            
            //MAJOR TICK LABELS
            majorTickLabels = new ArrayList<>();

            double start = minStartTime;
            double step = axisData.getMajorStep();

            for (int i = 0; i < majorTickCoordinates.size(); i += 4) {
                Text tempTickLabel = new Text(majorTickCoordinates.get(i), majorTickCoordinates.get(i+1)+16, String.valueOf((start + (i / 4) * step)));
                majorTickLabels.add(tempTickLabel);
            }

            this.getChildren().addAll(majorTickLabels);

            //LISTENER(S)
            this.axisLine.endXProperty().addListener(new ChangeListener<Number>() {
                
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    for(int i=0;i<majorTickLines.size();i++){
                        majorTickLines.get(i).setStartX((majorTickCoordinates.get(4*i+4)-48)*(axisLine.endXProperty().subtract(48).doubleValue()/xWidth)+48);
                        majorTickLines.get(i).setEndX((majorTickCoordinates.get(4*i+6)-48)*(axisLine.endXProperty().subtract(48).doubleValue()/xWidth)+48);
                    }
                    
                    for(int i=0;i<minorTickLines.size();i++){
                        minorTickLines.get(i).setStartX((minorTickCoordinates.get(4*i+4)-48)*(axisLine.endXProperty().subtract(48).doubleValue()/xWidth)+48);
                        minorTickLines.get(i).setEndX((minorTickCoordinates.get(4*i+6)-48)*(axisLine.endXProperty().subtract(48).doubleValue()/xWidth)+48);
                    }
                    
                    for(int i=0;i<majorTickLabels.size();i++){
                        majorTickLabels.get(i).setX((majorTickCoordinates.get(4*i)-48)*(axisLine.endXProperty().subtract(48).doubleValue()/xWidth)+48);
                    }
                }
            });
            
            this.axisLine.endYProperty().addListener(new ChangeListener<Number>() {
                
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    for(int i=0;i<majorTickLines.size();i++){
                        majorTickLines.get(i).setStartY(axisLine.endYProperty().doubleValue()+majorTickCoordinates.get(4*i+5));
                        majorTickLines.get(i).setEndY(axisLine.endYProperty().doubleValue()+majorTickCoordinates.get(4*i+7));
                    }
                    
                    for(int i=0;i<minorTickLines.size();i++){
                        minorTickLines.get(i).setStartY(axisLine.endYProperty().doubleValue()+minorTickCoordinates.get(4*i+5));
                        minorTickLines.get(i).setEndY(axisLine.endYProperty().doubleValue()+minorTickCoordinates.get(4*i+7));
                    }
                    
                    for(int i=0;i<majorTickLabels.size();i++){
                        majorTickLabels.get(i).setY(axisLine.endYProperty().doubleValue()+majorTickCoordinates.get(4*i+1)+16);
                    }
                }
            });

        } else {
            //AXIS LINE
            axisLine = new Line(0, 0, 0, yHeight);
            
            axisLine.setStrokeWidth(axisData.getLineData().getWidth());
            axisLine.setFill(axisData.getLineData().getColor());
            axisLine.setStroke(axisData.getLineData().getColor());

            this.getChildren().add(axisLine);

            double tempMax = ds.get(0).getMaxValue();
            double tempMin = ds.get(0).getMinValue();
            double minStartTime = ds.get(0).getStartTime();
            
            for(int i=1;i<ds.size();i++){
                if(ds.get(i).getMaxValue()>tempMax){
                    tempMax = ds.get(i).getMaxValue();
                }
                if(ds.get(i).getMinValue()<tempMin){
                    tempMin = ds.get(i).getMinValue();
                }
                if(ds.get(i).getStartTime()<minStartTime){
                    minStartTime = ds.get(i).getStartTime();
                }
            }

            //MAJOR TICK LINES
            majorTickCoordinates = VisualUtility.yTickCoordinateGenerator(Math.abs(tempMax)+Math.abs(tempMin), axisData.getMajorStep(), 8, xWidth, yHeight);
            majorTickLines = new ArrayList<>();

            for (int i = 0; i < majorTickCoordinates.size()-4; i += 4) {
                Line tempLine = new Line(majorTickCoordinates.get(i+4), majorTickCoordinates.get(i + 5), majorTickCoordinates.get(i + 6), majorTickCoordinates.get(i + 7));
                majorTickLines.add(tempLine);
            }

            this.getChildren().addAll(majorTickLines);
            
            //MINOR TICK LINES
            minorTickCoordinates = VisualUtility.yTickCoordinateGenerator(Math.abs(tempMax)+Math.abs(tempMin), axisData.getMinorStep(), 4, xWidth, yHeight);
            minorTickLines = new ArrayList<>();

            for (int i = 0; i < minorTickCoordinates.size()-4; i += 4) {
                boolean majorLine = false;
                for(int j=0;j<majorTickCoordinates.size()-4;j+=4){
                    if(minorTickCoordinates.get(i+2).equals(majorTickCoordinates.get(j+2))){
                        majorLine = true;
                    }
                }
                
                if(majorLine){
                    Line tempLine = new Line(minorTickCoordinates.get(i+4), minorTickCoordinates.get(i + 5), minorTickCoordinates.get(i + 6), minorTickCoordinates.get(i + 7));
                    tempLine.setOpacity(0);
                    minorTickLines.add(tempLine);
                }else{
                    Line tempLine = new Line(minorTickCoordinates.get(i+4), minorTickCoordinates.get(i + 5), minorTickCoordinates.get(i + 6), minorTickCoordinates.get(i + 7));
                    minorTickLines.add(tempLine);
                }
            }

            this.getChildren().addAll(minorTickLines);

            //MAJOR TICK LABELS
            majorTickLabels = new ArrayList<>();

            double start = tempMax;
            double step = axisData.getMajorStep();

            for (int i = 0; i < majorTickCoordinates.size(); i += 4) {
                Text tempTickLabel = new Text(majorTickCoordinates.get(i) - 32, majorTickCoordinates.get(i + 1), String.valueOf((start - (i / 4) * step)));
                majorTickLabels.add(tempTickLabel);
            }

            this.getChildren().addAll(majorTickLabels);
            
            //LISTENER(S)
            this.axisLine.endYProperty().addListener(new ChangeListener<Number>() {
                
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    for(int i=0;i<majorTickLines.size();i++){
                        majorTickLines.get(i).setStartY(majorTickCoordinates.get(4*i+1)*(axisLine.endYProperty().divide(yHeight).doubleValue()));
                        majorTickLines.get(i).setEndY(majorTickCoordinates.get(4*i+3)*(axisLine.endYProperty().divide(yHeight).doubleValue()));
                    }
                    
                    for(int i=0;i<minorTickLines.size();i++){
                        minorTickLines.get(i).setStartY(minorTickCoordinates.get(4*i+1)*(axisLine.endYProperty().divide(yHeight).doubleValue()));
                        minorTickLines.get(i).setEndY(minorTickCoordinates.get(4*i+3)*(axisLine.endYProperty().divide(yHeight).doubleValue()));
                    }
                    
                    for(int i=0;i<majorTickLabels.size();i++){
                        majorTickLabels.get(i).setY(majorTickCoordinates.get(4*i+1)*(axisLine.endYProperty().divide(yHeight).doubleValue())); 
                    }
                }
            });
        }
    }

    public AxisType getAxisType() {
        return axisType;
    }

    public Line getAxisLine() {
        return axisLine;
    }

    void bind(DoubleProperty widthProperty, DoubleProperty heightProperty) {
        switch (axisType){
            case HORIZONTAL:
                axisLine.setStartX(48);
                axisLine.startYProperty().bind(heightProperty.subtract(32));        
                axisLine.endXProperty().bind(widthProperty.subtract(32));
                axisLine.endYProperty().bind(heightProperty.subtract(32));        
                break;
            case VERTICAL:
                axisLine.setStartX(48);
                axisLine.setStartY(0);   
                axisLine.setEndX(48);
                axisLine.endYProperty().bind(heightProperty.subtract(32));        
                break;
        }
    } 
}