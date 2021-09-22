/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plotscenegraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.CacheHint;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

/**
 *
 * @author tobak11
 */
public class ResizeableBoundedLineChart extends Pane {
    //Nodes to create graph
    private Line xAxis;
    private Line yAxis;
    private Line xAxisTop;
    private Line yAxisTop;
    
    //Data Series
    private ArrayList<BoundedDataSeries> bdsList;
    
    //Polyline Container and data
    private HashMap <Integer,ArrayList<Double>> polylineData;
    private ArrayList<Polyline> polylineList;
    private HashMap <Integer,ArrayList<Double>> polygonData;
    private ArrayList<Polygon> polygonList;
    
    //Tickmark containers
    private ArrayList<Line> xTickMarks;
    private ArrayList<Line> yTickMarks;
    
    //Label containers
    private ArrayList<Text> xLabels;
    private ArrayList<Text> yLabels;
    private ArrayList<Text> seriesLabels;
    
    //Axis labels and name of the graph
    private Text xAxisLabel;
    private Text yAxisLabel;
    private Text graphLabel;
    
    //Data for the graph
    private double maxValue;
    private double minValue;
    private double maxLowerBound;
    private double maxUpperBound;
    
    private double maxTime;
    
    private double topPix = 100;
    private double botPix = 350;
    private double leftPix = 100;
    private double rightPix = 700;
    
    private double wantedStepX = 32;
    private double wantedStepY = 8;
    
    public ResizeableBoundedLineChart(ArrayList<BoundedDataSeries> list){
        this.setCacheHint(CacheHint.QUALITY);
        
        //Initialize containers
        bdsList = new ArrayList<>();
        polylineData = new HashMap<>();
        polylineList = new ArrayList<>();
        polygonData = new HashMap<>();
        polygonList = new ArrayList<>();
        xTickMarks = new ArrayList<>();
        yTickMarks = new ArrayList<>();
        xLabels = new ArrayList<>();
        yLabels = new ArrayList<>();
        seriesLabels = new ArrayList<>();
        
        //Adding dataSerieses
        this.bdsList=list;
        
        //Initialize data for the graph
        maxValue = this.bdsList.get(0).getMaxValue();
        minValue = this.bdsList.get(0).getMinValue();
        
        maxLowerBound = +this.bdsList.get(0).getLowerBound();
        maxUpperBound = +this.bdsList.get(0).getUpperBound();
        
        maxTime = this.bdsList.get(0).getTimeWindow();
        
        for(int i=0;i<bdsList.size();i++){
            if(this.bdsList.get(i).getMaxValue()>maxValue){
                maxValue=this.bdsList.get(i).getMaxValue();
            }
            if(this.bdsList.get(i).getMinValue()<minValue){
                minValue=this.bdsList.get(i).getMinValue();
            }
            if(bdsList.get(i).getTimeWindow()>maxTime){
                maxTime=bdsList.get(i).getTimeWindow();
            }
            
            if(this.bdsList.get(i).getLowerBound()>maxLowerBound){
                maxLowerBound=this.bdsList.get(i).getLowerBound();
            }
            if(this.bdsList.get(i).getUpperBound()>maxUpperBound){
                minValue=this.bdsList.get(i).getUpperBound();
            }
        }
        
        xAxis = new Line(leftPix,botPix,rightPix,botPix);
        yAxis = new Line(leftPix,topPix,leftPix,botPix);
        xAxisTop = new Line(leftPix,topPix,rightPix,topPix);
        yAxisTop = new Line(rightPix,topPix,rightPix,botPix);
        
        this.getChildren().addAll(xAxis,yAxis,xAxisTop,yAxisTop);
        
        for(int i=0;i<bdsList.size();i++){
            seriesGen(bdsList.get(i));
        }
        
        this.getChildren().addAll(polylineList); this.getChildren().addAll(polygonList);
        
        tickMarkGen(); this.getChildren().addAll(xTickMarks); this.getChildren().addAll(yTickMarks);
        
        labelGen(); this.getChildren().addAll(xLabels); this.getChildren().addAll(yLabels);
        
        seriesLabelGen(); this.getChildren().addAll(seriesLabels);
        
        setGraphLabels("N.o.D.", "Values", "Random Data"); this.getChildren().addAll(xAxisLabel, yAxisLabel, graphLabel); 
    }
    
    public void seriesGen(BoundedDataSeries bds){
        int dataLength = bds.getData().length;
        double incrementer = 0.0;
        double pixIncrementerValue = (rightPix-leftPix)/(dataLength-1);
        double yScaling = scalingIntervalGen(minValue,maxValue,maxLowerBound,maxUpperBound);
        
        double[] data = bds.getData().clone();
        
        double[] points = new double[dataLength*2];  
        double[] boundPoints = new double[dataLength*4];
        
        double tempUpperBound = bds.getUpperBound();
        double tempLowerBound = bds.getLowerBound();

        for(int i=0;i<dataLength;i++){
            points[2*i] = leftPix + incrementer;
            points[2*i+1] = botPix-(((data[i]+Math.abs(minValue-maxLowerBound))/yScaling)*(botPix-topPix));
            boundPoints[2*i] = leftPix + incrementer;
            boundPoints[2*i+1] = botPix-(((data[i]+Math.abs(minValue-maxLowerBound)+tempUpperBound)/yScaling)*(botPix-topPix));
            boundPoints[4*dataLength-2*i-2] = leftPix + incrementer;
            boundPoints[4*dataLength-2*i-1] = botPix-(((data[i]+Math.abs(minValue-maxLowerBound)-tempLowerBound)/yScaling)*(botPix-topPix));          
            incrementer+=pixIncrementerValue;
        }
        
        int actPolyLine = polylineList.size();
        int actPolyGon = polygonList.size();
        
        polylineData.put(actPolyLine, new ArrayList<>());
        polygonData.put(actPolyGon, new ArrayList<>());
        
        for(int i=0;i<boundPoints.length;i++){
            if(i<points.length){
                polylineData.get(actPolyLine).add(points[i]);
            }
            polygonData.get(actPolyGon).add(boundPoints[i]);
        }
        
        Polyline polyLine = new Polyline(points);
        polyLine.setStroke(bds.getColor());
        polyLine.setStrokeWidth(bds.getLineWidth());
        
        Polygon polyGon = new Polygon(boundPoints);
        polyGon.setFill(bds.getColor());
        polyGon.setOpacity(0.6);
        
        if(bds.isIsDotted()){
            polyLine.getStrokeDashArray().addAll(4.0,8.0);
        }
        
        polylineList.add(polyLine);
        polygonList.add(polyGon);
    }
    
    public void tickMarkGen(){
        double xTickCounter = maxTime/wantedStepX;
        double yTickCounter = scalingIntervalGen(minValue,maxValue,maxLowerBound,maxUpperBound)/wantedStepY;
        
        double xIncrementer = (rightPix-leftPix)/xTickCounter;
        
        for(int i=0;i<xTickCounter+1;i++){
            Line temp = new Line(leftPix+i*xIncrementer,botPix,leftPix+i*xIncrementer,botPix+5);
            xTickMarks.add(temp);
        }
        
        double yIncrementer = (botPix-topPix)/yTickCounter;
        
        for(int i=0;i<yTickCounter+1;i++){
            if(botPix-i*yIncrementer>=topPix){
                Line temp = new Line(leftPix,botPix-i*yIncrementer,leftPix-5,botPix-i*yIncrementer);
                yTickMarks.add(temp);
            }
        }
    }
    
    public void labelGen(){
        double xTickCounter = maxTime/wantedStepX;
        double yTickCounter = scalingIntervalGen(minValue,maxValue,maxLowerBound,maxUpperBound)/wantedStepY;
        
        double xIncrementer = (rightPix-leftPix)/xTickCounter;
        
        for(int i=0;i<xTickCounter+1;i++){
            Text temp = new Text(String.valueOf(i*wantedStepX));
            temp.setX(leftPix+i*xIncrementer);
            temp.setY(botPix+24);
            xLabels.add(temp);
        }
        
        double yIncrementer = (botPix-topPix)/yTickCounter;
        
        for(int i=0;i<yTickCounter+1;i++){
            if(botPix-i*yIncrementer>=topPix){
                Text temp = new Text(String.valueOf((int)(Math.floor(minValue-maxLowerBound)+i*wantedStepY)));
                temp.setX(leftPix-36);
                temp.setY(botPix-i*yIncrementer);
                yLabels.add(temp);
            }            
        }
    }
    
    public void seriesLabelGen(){
        for(int i=0;i<bdsList.size();i++){
            Text temp = new Text(bdsList.get(i).getName());
            temp.setX(36);
            temp.setY(36+i*20);
            temp.setFont(Font.font(16));
            temp.setFill(bdsList.get(i).getColor());
            seriesLabels.add(temp);
        }
    }
    
    public void setGraphLabels(String xAx, String yAx, String graph){
        Text tempXAx = new Text(xAx);
        Text tempYAx = new Text(yAx);
        Text tempGraphName = new Text(graph);
        
        tempXAx.setFont(Font.font(16));
        tempXAx.setX((leftPix+rightPix)/2-tempXAx.getLayoutBounds().getWidth()/2);
        tempXAx.setY(botPix+48);
        
        tempYAx.setRotate(-90);
        tempYAx.setFont(Font.font(16));
        tempYAx.setX(leftPix-(tempYAx.getLayoutBounds().getWidth()/2+60));
        tempYAx.setY((topPix+botPix)/2);
        
        tempGraphName.setFont(Font.font(24));
        tempGraphName.setX((leftPix+rightPix)/2-tempGraphName.getLayoutBounds().getWidth()/2);
        tempGraphName.setY(topPix-60);
        
        this.xAxisLabel=tempXAx;
        this.yAxisLabel=tempYAx;
        this.graphLabel=tempGraphName;
    }
    
    public double scalingIntervalGen(double minT, double maxT, double mlb, double mub){
        double min = minT-mlb;
        double max = maxT+mub;
        
        if(max>=0 && min>=0){
            return max+min;
        }else if(max>=0 && min<0){
            return max+Math.abs(min);
        }else{
            return Math.abs(min)+Math.abs(max);
        }
    }
    
    public void createBindings(){
        //Binding axes          
        xAxis.startXProperty().bind(this.widthProperty().multiply(0.125));
        xAxis.endXProperty().bind(this.widthProperty().multiply(0.875));
        xAxis.startYProperty().bind(this.heightProperty().divide(450).multiply(350));
        xAxis.endYProperty().bind(this.heightProperty().divide(450).multiply(350));
        
        xAxisTop.startXProperty().bind(this.widthProperty().multiply(0.125));
        xAxisTop.endXProperty().bind(this.widthProperty().multiply(0.875));
        xAxisTop.startYProperty().bind(this.heightProperty().divide(450).multiply(100));
        xAxisTop.endYProperty().bind(this.heightProperty().divide(450).multiply(100));
        
        yAxis.startXProperty().bind(this.widthProperty().multiply(0.125));
        yAxis.endXProperty().bind(this.widthProperty().multiply(0.125));
        yAxis.startYProperty().bind(this.heightProperty().divide(450).multiply(100));
        yAxis.endYProperty().bind(this.heightProperty().divide(450).multiply(350));
        
        yAxisTop.startXProperty().bind(this.widthProperty().multiply(0.875));
        yAxisTop.endXProperty().bind(this.widthProperty().multiply(0.875));
        yAxisTop.startYProperty().bind(this.heightProperty().divide(450).multiply(100));
        yAxisTop.endYProperty().bind(this.heightProperty().divide(450).multiply(350));
        
        for(int i=0;i<xTickMarks.size();i++){
            xTickMarks.get(i).startXProperty().bind(this.widthProperty().multiply(xTickMarks.get(i).getStartX()).divide(800));
            xTickMarks.get(i).endXProperty().bind(this.widthProperty().multiply(xTickMarks.get(i).getEndX()).divide(800));
            xTickMarks.get(i).startYProperty().bind(this.heightProperty().multiply(xTickMarks.get(i).getStartY()).divide(450));
            xTickMarks.get(i).endYProperty().bind(this.heightProperty().multiply(xTickMarks.get(i).getEndY()).divide(450));
            
            xLabels.get(i).xProperty().bind(xTickMarks.get(i).endXProperty().subtract(xLabels.get(i).getLayoutBounds().getWidth()/2));
            xLabels.get(i).yProperty().bind(xTickMarks.get(i).endYProperty().add(16));
        }
        
        for(int i=0;i<yTickMarks.size();i++){
            yTickMarks.get(i).startXProperty().bind(this.widthProperty().multiply(yTickMarks.get(i).getStartX()).divide(800));
            yTickMarks.get(i).endXProperty().bind(this.widthProperty().multiply(yTickMarks.get(i).getEndX()).divide(800));
            yTickMarks.get(i).startYProperty().bind(this.heightProperty().multiply(yTickMarks.get(i).getStartY()).divide(450));
            yTickMarks.get(i).endYProperty().bind(this.heightProperty().multiply(yTickMarks.get(i).getEndY()).divide(450));
            
            yLabels.get(i).xProperty().bind(yTickMarks.get(i).endXProperty().subtract(24));
            yLabels.get(i).yProperty().bind(yTickMarks.get(i).endYProperty().add(yLabels.get(i).getLayoutBounds().getHeight()/4));
        }
        
        for(int i=0;i<seriesLabels.size();i++){
            seriesLabels.get(i).xProperty().bind(this.widthProperty().multiply(seriesLabels.get(i).getX()).divide(800));
            seriesLabels.get(i).yProperty().bind(this.heightProperty().multiply(seriesLabels.get(i).getY()).divide(450));
        }
        
        xAxisLabel.xProperty().bind(this.widthProperty().multiply(xAxisLabel.getX()).divide(800));
        xAxisLabel.yProperty().bind(this.heightProperty().multiply(xAxisLabel.getY()).divide(450));
        
        yAxisLabel.xProperty().bind(this.widthProperty().multiply(yAxisLabel.getX()).divide(800));
        yAxisLabel.yProperty().bind(this.heightProperty().multiply(yAxisLabel.getY()).divide(450));
        
        graphLabel.xProperty().bind(this.widthProperty().multiply(graphLabel.getX()).divide(800));
        graphLabel.yProperty().bind(this.heightProperty().multiply(graphLabel.getY()).divide(450));
    }
    
    public void createListeners(){
        this.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                refreshNonBindableNodes();
            }
        });
        
        this.heightProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                refreshNonBindableNodes();
            }
        });
    }
    
    public void refreshNonBindableNodes(){
        
        //Refresh polylines
        for(int i=0;i<polylineList.size();i++){
            ArrayList<Double> tempData = new ArrayList<>();
            this.getChildren().remove(polylineList.get(i));
            for(int j=0;j<polylineData.get(i).size();j+=2){
                tempData.add(polylineData.get(i).get(j)*this.widthProperty().divide(800).doubleValue());
                tempData.add(polylineData.get(i).get(j+1)*this.heightProperty().divide(450).doubleValue());
            }
            polylineList.get(i).getPoints().clear();
            polylineList.get(i).getPoints().addAll(tempData);
            this.getChildren().add(polylineList.get(i));
        }
        
        //Refresh polygons
        for(int i=0;i<polygonList.size();i++){
            ArrayList<Double> tempData = new ArrayList<>();
            this.getChildren().remove(polygonList.get(i));
            for(int j=0;j<polygonData.get(i).size();j+=2){
                tempData.add(polygonData.get(i).get(j)*this.widthProperty().divide(800).doubleValue());
                tempData.add(polygonData.get(i).get(j+1)*this.heightProperty().divide(450).doubleValue());
            }
            polygonList.get(i).getPoints().clear();
            polygonList.get(i).getPoints().addAll(tempData);
            this.getChildren().add(polygonList.get(i));
        }
        
    }
}