/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plotscenegraph;

import java.util.ArrayList;
import javafx.scene.CacheHint;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author tobak11
 */
public class MultiBoundedLineChart extends Pane {
    //Nodes to create graph
    private Line xAxis;
    private Line yAxis;
    private Line xAxisTop;
    private Line yAxisTop;
    
    //Data Series
    private ArrayList<BoundedDataSeries> bdsList;
    
    //Polyline Container from data
    private ArrayList<Polyline> polylineList;
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
    
    private double topPix = 30;
    private double botPix = 105;
    private double leftPix = 30;
    private double rightPix = 210;
    
    private double wantedStepX = 0.5;
    private double wantedStepY = 4;
    
    public MultiBoundedLineChart(ArrayList<BoundedDataSeries> list){
        this.setCacheHint(CacheHint.QUALITY);
        
        //Initialize containers
        bdsList = new ArrayList<>();
        polylineList = new ArrayList<>();
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
        
        //labelGen(); this.getChildren().addAll(xLabels); this.getChildren().addAll(yLabels);
        
        //seriesLabelGen(); this.getChildren().addAll(seriesLabels);
        
        //setGraphLabels("Data", "Values", "Random Data"); this.getChildren().addAll(xAxisLabel, yAxisLabel, graphLabel);
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
            
            System.out.println(points[2*i+1]);
        }
        
        System.out.println("MAX: " + maxValue);
        System.out.println("MIN: " + minValue);
        
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
            temp.setLayoutX(leftPix+i*xIncrementer-10);
            temp.setLayoutY(botPix+24);
            xLabels.add(temp);
        }
        
        double yIncrementer = (botPix-topPix)/yTickCounter;
        
        for(int i=0;i<yTickCounter+1;i++){
            if(botPix-i*yIncrementer>=topPix){
                Text temp = new Text(String.valueOf((int)(Math.floor(minValue-maxLowerBound)+i*wantedStepY)));
                temp.setLayoutX(leftPix-36);
                temp.setLayoutY(botPix-i*yIncrementer);
                yLabels.add(temp);
            }            
        }
    }
    
    public void seriesLabelGen(){
        for(int i=0;i<bdsList.size();i++){
            Text temp = new Text(bdsList.get(i).getName());
            temp.setLayoutX(36);
            temp.setLayoutY(36+i*20);
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
        tempXAx.setLayoutX((leftPix+rightPix)/2-tempXAx.getLayoutBounds().getWidth()/2);
        tempXAx.setLayoutY(botPix+48);
        System.out.println((leftPix+rightPix)/2-tempXAx.getLayoutBounds().getWidth()/2 + " " + botPix+48);
        
        tempYAx.setRotate(-90);
        tempYAx.setFont(Font.font(16));
        tempYAx.setLayoutX(leftPix-(tempYAx.getLayoutBounds().getWidth()/2+60));
        tempYAx.setLayoutY((topPix+botPix)/2);
        
        tempGraphName.setFont(Font.font(24));
        tempGraphName.setLayoutX((leftPix+rightPix)/2-tempGraphName.getLayoutBounds().getWidth()/2);
        tempGraphName.setLayoutY(topPix-60);
        
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
}
