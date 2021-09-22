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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author tobak11
 */
public class BoxChart extends Pane {
   
    private Line xAxis;
    private Line yAxis;
    
    //Containers
    private ArrayList<Line> yTickMarks;
    private ArrayList<Text> yTickLabels;
    
    private ArrayList<DataBar> dataBars;
    private ArrayList<Rectangle> dataBarRects;
    private ArrayList<Text> dataBarLabels;
    
    private ArrayList<Line> extraLines;
    
    //Axis labels and name of the graph
    private Text xAxisLabel;
    private Text yAxisLabel;
    private Text graphLabel;
    
    //Data for the graph
    private double maxValue;

    private double topPix = 100;
    private double botPix = 350;
    private double leftPix = 100;
    private double rightPix = 700;
    
    private double wantedStepY = 10;
    
    //Constants for the bar positioning
    private final double barWidthConst = 50;
    private final double barGapConst = 10;

    public BoxChart(ArrayList<DataBar> dataBars){
        this.setCacheHint(CacheHint.QUALITY);
        
        this.dataBars=dataBars;
        
        double tempMax = dataBars.get(0).getValue();      
        for(int i=0;i<dataBars.size();i++){
            if(dataBars.get(i).getValue()>tempMax){
                tempMax=dataBars.get(i).getValue();
            }
        }
        maxValue=tempMax;
        
        //Initialize containers
        yTickMarks = new ArrayList<>();
        yTickLabels = new ArrayList<>();
        
        dataBarLabels = new ArrayList<>();
        dataBarRects = new ArrayList<>();
        
        extraLines = new ArrayList<>();
        
        xAxis = new Line(leftPix,topPix,leftPix,botPix);
        yAxis = new Line(leftPix,botPix,rightPix,botPix);
        
        this.getChildren().addAll(xAxis,yAxis);
        
        tickMarkGen(); this.getChildren().addAll(yTickMarks);
        dataBarRectGen(); this.getChildren().addAll(dataBarRects);
        dataBarLabelGen(); this.getChildren().addAll(dataBarLabels);
        tickLabelGen(); this.getChildren().addAll(yTickLabels);
        setGraphLabels("Vehicles", "Quantity", "Vehicles owned by the company"); this.getChildren().addAll(xAxisLabel, yAxisLabel, graphLabel);
        extraLineGen(); this.getChildren().addAll(extraLines);
    }
    
    public void tickMarkGen(){
        double yTickCounter = Math.ceil(maxValue/wantedStepY);
        
        double yIncrementer = (botPix-topPix)/yTickCounter;
        
        for(int i=0;i<yTickCounter+1;i++){
            Line temp = new Line(leftPix,botPix-i*yIncrementer,leftPix-5,botPix-i*yIncrementer);
            yTickMarks.add(temp);
        }
    }
    
    public void tickLabelGen(){
        double yTickCounter = Math.ceil(maxValue/wantedStepY);
        
        double yIncrementer = (botPix-topPix)/yTickCounter;
        
        for(int i=0;i<yTickCounter+1;i++){
            Text temp = new Text(String.valueOf((int)(i*wantedStepY)));
            temp.setX(leftPix-30);
            temp.setY(botPix-i*yIncrementer+6);
            yTickLabels.add(temp);
        }
    }
    
    public void dataBarRectGen(){
        double tempX = leftPix+barGapConst;
        double tempY = botPix-(((botPix-topPix)*dataBars.get(0).getValue()*0.75)/maxValue);
        double tempBarWidth = barWidthConst*dataBars.get(0).getScaling();
        
        //Adding the first bar
        Rectangle tempRect = new Rectangle(tempX, tempY,tempBarWidth,(botPix-(tempY*(4/3)))/2);
        tempRect.setFill(dataBars.get(0).getColor());
        dataBarRects.add(tempRect);
        
        double prevX = tempX+tempBarWidth;
        
        for(int i=1;i<dataBars.size();i++){
            tempX = prevX+barGapConst;
            tempY = botPix-(((botPix-topPix)*dataBars.get(i).getValue()*0.75)/maxValue);
            tempBarWidth = barWidthConst*dataBars.get(i).getScaling();
            tempRect = new Rectangle(tempX, tempY,tempBarWidth,(botPix-(tempY*(4/3)))/1.5);
            tempRect.setFill(dataBars.get(i).getColor());
            prevX = tempX+tempBarWidth;
            dataBarRects.add(tempRect);
        }
    }
    
    public void dataBarLabelGen(){
        for(int i=0;i<dataBarRects.size();i++){
            double labX = dataBarRects.get(i).getX()+(dataBarRects.get(i).getWidth()/2);
            double labY = botPix-12-((botPix-topPix)*dataBars.get(i).getValue())/maxValue;
            Text tempText = new Text(dataBars.get(i).getName());
            tempText.setLayoutX(labX-tempText.getLayoutBounds().getWidth()/2);
            tempText.setLayoutY(labY);
            tempText.setFill(dataBars.get(i).getColor());
            
            dataBarLabels.add(tempText);
        }
    }
    
    public void setGraphLabels(String xAx, String yAx, String graph){
        Text tempXAx = new Text(xAx);
        Text tempYAx = new Text(yAx);
        Text tempGraphName = new Text(graph);
        
        tempXAx.setFont(Font.font(16));
        tempXAx.setLayoutX((leftPix+rightPix)/2-tempXAx.getLayoutBounds().getWidth()/2);
        tempXAx.setLayoutY(botPix+48);
        
        tempYAx.setRotate(-90);
        tempYAx.setFont(Font.font(16));
        tempYAx.setLayoutX(leftPix-96);
        tempYAx.setLayoutY((topPix+botPix)/2-tempYAx.getLayoutBounds().getWidth()/2+40);
        
        tempGraphName.setFont(Font.font(24));
        tempGraphName.setLayoutX((leftPix+rightPix)/2-tempGraphName.getLayoutBounds().getWidth()/2);
        tempGraphName.setLayoutY(topPix-60);
        
        this.xAxisLabel=tempXAx;
        this.yAxisLabel=tempYAx;
        this.graphLabel=tempGraphName;
    }
    
    public void extraLineGen(){
        for(int i=0;i<dataBarRects.size();i++){
            double dbX = dataBarRects.get(i).getX()+dataBarRects.get(i).getWidth()/2;
            double topY = dataBarRects.get(i).getY();
            double botY = dataBarRects.get(i).getY()+dataBarRects.get(i).getHeight();
            
            Line topMax = new Line(dbX-dataBarRects.get(i).getWidth()/8,topY-((botY-topY)*0.5),dbX+dataBarRects.get(i).getWidth()/8,topY-((botY-topY)*0.5));
            Line top95 = new Line(dbX-dataBarRects.get(i).getWidth()/4,topY-((botY-topY)*0.475),dbX+dataBarRects.get(i).getWidth()/4,topY-((botY-topY)*0.475));
            Line topVert = new Line(dbX,topY-((botY-topY)*0.475),dbX,topY);
            Line botVert = new Line(dbX,botY,dbX,botY+((botY-topY)*0.475));
            Line bot95 = new Line(dbX-dataBarRects.get(i).getWidth()/4,botY+((botY-topY)*0.475),dbX+dataBarRects.get(i).getWidth()/4,botY+((botY-topY)*0.475));
            Line botMax = new Line(dbX-dataBarRects.get(i).getWidth()/8,botY+((botY-topY)*0.5),dbX+dataBarRects.get(i).getWidth()/8,botY+((botY-topY)*0.5));
            
            extraLines.add(topMax);
            extraLines.add(top95);
            extraLines.add(topVert);
            extraLines.add(botVert);
            extraLines.add(bot95);
            extraLines.add(botMax);
        }
    }
}