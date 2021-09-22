/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plotscenegraph;

import java.util.ArrayList;
import javafx.scene.CacheHint;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author tobak11
 */
public class LineChart extends Pane {
    //Nodes to create graph
    private Line xAxis;
    private Line yAxis;
    private Line xAxisTop;
    private Line yAxisTop;
    
    //Data Series
    private ArrayList<DataSeries> dsList;
    
    //Polyline Container from data
    private ArrayList<Polyline> polyList;
    
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
    
    private double maxTime;
    
    private double topPix = 100;
    private double botPix = 350;
    private double leftPix = 100;
    private double rightPix = 700;
    
    private double wantedStepX = 0.5;
    private double wantedStepY = 0.5;
    
    public LineChart(ArrayList<DataSeries> list){
        this.setCacheHint(CacheHint.QUALITY);
        
        //Initialize containers
        dsList = new ArrayList<>();
        polyList = new ArrayList<>();
        xTickMarks = new ArrayList<>();
        yTickMarks = new ArrayList<>();
        xLabels = new ArrayList<>();
        yLabels = new ArrayList<>();
        seriesLabels = new ArrayList<>();
        
        //Adding dataSerieses
        this.dsList=list;
        
        //Initialize data for the graph
        maxValue = this.dsList.get(0).getMaxValue();
        minValue = this.dsList.get(0).getMinValue();
        
        maxTime = this.dsList.get(0).getTimeWindow();
        
        for(int i=0;i<dsList.size();i++){
            if(dsList.get(i).getMaxValue()>maxValue){
                maxValue=dsList.get(i).getMaxValue();
            }
            if(dsList.get(i).getMinValue()<minValue){
                minValue=dsList.get(i).getMinValue();
            }
            if(dsList.get(i).getTimeWindow()>maxTime){
                maxTime=dsList.get(i).getTimeWindow();
            }
        }
        
        xAxis = new Line(leftPix,botPix,rightPix,botPix);
        yAxis = new Line(leftPix,topPix,leftPix,botPix);
        xAxisTop = new Line(leftPix,topPix,rightPix,topPix);
        yAxisTop = new Line(rightPix,topPix,rightPix,botPix);
        
        this.getChildren().addAll(xAxis,yAxis,xAxisTop,yAxisTop);
        
        for(int i=0;i<dsList.size();i++){
            this.getChildren().add(seriesGen(dsList.get(i)));
        }
        
        tickMarkGen(); this.getChildren().addAll(xTickMarks); this.getChildren().addAll(yTickMarks);
        
        labelGen(); this.getChildren().addAll(xLabels); this.getChildren().addAll(yLabels);
        
        seriesLabelGen(); this.getChildren().addAll(seriesLabels);
        
        setGraphLabels("angle", "sin(x) and cos(x)", "Trigonometric Functions"); this.getChildren().addAll(xAxisLabel, yAxisLabel, graphLabel);
    }
    
    public Polyline seriesGen(DataSeries ds){
        int dataLength = ds.getData().length;
        double[] data = ds.getData().clone();
        
        double pixIncrementerValue = (rightPix-leftPix)/dataLength;
        double[] points = new double[dataLength*2];
        
        double incrementer = 0.0;
        
        double yScaling = scalingIntervalGen(minValue,maxValue);
        
        
        for(int i=0;i<dataLength;i++){
            points[2*i] = leftPix + incrementer;
            points[2*i+1] = botPix - (((data[i]+Math.abs(minValue))/yScaling)*(botPix-topPix));
                    
            incrementer+=pixIncrementerValue;
        }
        
        Polyline poly = new Polyline(points);
        poly.setStroke(ds.getColor());
        poly.setStrokeWidth(ds.getLineWidth());
        
        if(ds.isDashed()){
            poly.getStrokeDashArray().addAll(4.0,8.0);
        }
        
        polyList.add(poly);
        
        return poly;
    }
    
    public void tickMarkGen(){
        double xTickCounter = maxTime/wantedStepX;
        double yTickCounter = scalingIntervalGen(minValue,maxValue)/wantedStepY;
        
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
        double yTickCounter = scalingIntervalGen(minValue,maxValue)/wantedStepY;
        
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
                Text temp = new Text(String.valueOf(minValue+i*wantedStepY));
                temp.setLayoutX(leftPix-36);
                temp.setLayoutY(botPix-i*yIncrementer);
                yLabels.add(temp);
            }            
        }
    }
    
    public void seriesLabelGen(){
        for(int i=0;i<dsList.size();i++){
            Text temp = new Text(dsList.get(i).getName());
            temp.setLayoutX(36);
            temp.setLayoutY(36+i*20);
            temp.setFont(Font.font(16));
            temp.setFill(dsList.get(i).getColor());
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
    
    public double scalingIntervalGen(double min, double max){
        if(max>=0 && min>=0){
            return max+min;
        }else if(max>=0 && min<0){
            return max+Math.abs(min);
        }else{
            return Math.abs(min)+Math.abs(max);
        }
    }
}