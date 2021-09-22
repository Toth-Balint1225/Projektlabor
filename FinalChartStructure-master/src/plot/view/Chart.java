/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot.view;

import plot.data.*;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import plot.view.Axis.AxisType;

/**
 *
 * @author tobak11
 */
public class Chart extends BorderPane {

    //Containers
    private FigureContainer plotContainer;
    private PlotGroup plotGroup;
    
    //Possible nodes
    private Axis xAxis;
    private Axis yAxis;
    private Series series;
    private Legend legend;
    
    //-----Node DATA-----
    //xAxis Data
    private AxisData xAxisData;
    private AxisType xAxisType;
    private double xWidth;
    
    //yAxis Data
    private AxisData yAxisData;
    private AxisType yAxisType;
    private double yHeight;
    
    //Series data
    private ArrayList<DataSeries> dsList;
    
    //Title data
    private TitleData titleData;
    
    //Legend data
    private boolean legendOn;

    //sajat
    private boolean gridVerticalOn;
    private boolean gridHorizontalOn;
    private Grid gridVertical;
    private Grid gridHorizontal;

    private static double strokeWidthVertical;
    private static double strokeWidthHorizontal;

    private static Color colorVertical;
    private static Color colorHorizontal;

    private static GridLineData.Type typeVertical;
    private static GridLineData.Type typeHorizontal;

    private boolean isLogAxisX = false;
    private boolean isLogAxisy = false;


    public Chart() {
        plotContainer = new FigureContainer();       
        setCenter(plotContainer);   

        plotGroup = new PlotGroup();
        plotContainer.addPlot(plotGroup);
        
        legendOn = false;

        strokeWidthVertical = 0.5;
        strokeWidthHorizontal = 0.5;

        colorVertical = Color.LIGHTGRAY;
        colorHorizontal = Color.LIGHTGRAY;

        typeVertical = GridLineData.Type.SOLID;
        typeHorizontal = GridLineData.Type.SOLID;
    }

    public Chart xAxis(){
        xAxisData = new AxisData(
                new TitleData("xAxis"), new LabelData(), 
                1, 0.5, new LineData(Color.BLACK, 1, LineData.Type.SOLID));
        xAxisType = AxisType.HORIZONTAL;
        xWidth = 500;
        
        return this;
    }
    
    public Chart yAxis(){
        yAxisData = new AxisData(
                new TitleData("yAxis"), new LabelData(), 
                1, 0.5, new LineData(Color.BLACK, 1, LineData.Type.SOLID));
        yAxisType = AxisType.VERTICAL;
        yHeight = 300;
        
        return this;
    }
    
    
    public Chart xAxis(AxisData axisData){
        xAxisData = axisData;
        xAxisType = AxisType.HORIZONTAL;
        xWidth = 500;      
        
        return this;
    }
    
    public Chart yAxis(AxisData axisData){
        yAxisData = axisData;
        yAxisType = AxisType.VERTICAL;
        yHeight = 300;
                
        return this;
    }
    public Chart xLogAxis(){
        isLogAxisX = true;
        xAxisData = new AxisData(AxisData.LogType.TEN,
                new TitleData("xAxis"), new LabelData(),
                1, 0.5, new LineData(Color.BLACK, 1, LineData.Type.SOLID));
        xAxisType = AxisType.HORIZONTAL;
        xWidth = 500;

        return this;
    }

    public Chart yLogAxis(){
        isLogAxisy = true;
        yAxisData = new AxisData(AxisData.LogType.TEN,
                new TitleData("yAxis"), new LabelData(),
                1, 0.5, new LineData(Color.BLACK, 1, LineData.Type.SOLID));
        yAxisType = AxisType.VERTICAL;
        yHeight = 300;

        return this;
    }


    public Chart xLogAxis(AxisData axisData){
        isLogAxisX = true;
        xAxisData = axisData;
        xAxisType = AxisType.HORIZONTAL;
        xWidth = 500;

        return this;
    }

    public Chart yLogAxis(AxisData axisData){
        isLogAxisy = true;
        yAxisData = axisData;
        yAxisType = AxisType.VERTICAL;
        yHeight = 300;

        return this;
    }
    
    public Chart series(ArrayList<DataSeries> dsList){
        this.dsList=dsList;
        
        return this;
    }

    public Chart title(TitleData titleData) {
        this.titleData = titleData;

        return this;
    }
    
    public Chart legend(){
        this.legendOn = true;
        
        return this;
    }

    //sajat

    public Chart gridVertical(){
        this.gridVerticalOn = true;

        return this;
    }

    public Chart gridHorizontal(){
        this.gridHorizontalOn = true;

        return this;
    }

    public Chart gridVertical(Color color){
        this.gridVerticalOn = true;
        colorVertical = color;

        return this;
    }

    public Chart gridHorizontal(Color color){
        this.gridHorizontalOn = true;
        colorHorizontal = color;

        return this;
    }

    public Chart gridVertical(double strokeWidth){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;

        return this;
    }

    public Chart gridHorizontal(double strokeWidth){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;

        return this;
    }

    public Chart gridVertical(GridLineData.Type type){
        this.gridVerticalOn = true;
        typeVertical = type;

        return this;
    }

    public Chart gridHorizontal(GridLineData.Type type){
        this.gridHorizontalOn = true;
        typeHorizontal = type;

        return this;
    }

    public Chart gridVertical(Color color, double strokeWidth){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        colorVertical = color;

        return this;
    }

    public Chart gridHorizontal(Color color, double strokeWidth){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        colorHorizontal = color;

        return this;
    }

    public Chart gridVertical(Color color, GridLineData.Type type){
        this.gridVerticalOn = true;
        colorVertical = color;
        typeVertical = type;

        return this;
    }

    public Chart gridHorizontal(Color color, GridLineData.Type type){
        this.gridHorizontalOn = true;
        colorHorizontal = color;
        typeHorizontal = type;

        return this;
    }

    public Chart gridVertical(double strokeWidth, Color color){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        colorVertical = color;

        return this;
    }

    public Chart gridHorizontal(double strokeWidth, Color color){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        colorHorizontal = color;

        return this;
    }

    public Chart gridVertical(double strokeWidth, GridLineData.Type type){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        typeVertical = type;

        return this;
    }

    public Chart gridHorizontal(double strokeWidth, GridLineData.Type type){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        typeHorizontal = type;

        return this;
    }

    public Chart gridVertical(GridLineData.Type type, Color color){
        this.gridVerticalOn = true;
        colorVertical = color;
        typeVertical = type;

        return this;
    }

    public Chart gridHorizontal(GridLineData.Type type, Color color){
        this.gridHorizontalOn = true;
        colorHorizontal = color;
        typeHorizontal = type;

        return this;
    }

    public Chart gridVertical(GridLineData.Type type, double strokeWidth){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        typeVertical = type;

        return this;
    }

    public Chart gridHorizontal(GridLineData.Type type, double strokeWidth){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        typeHorizontal = type;

        return this;
    }

    public Chart gridVertical(Color color, double strokeWidth, GridLineData.Type type){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        colorVertical = color;
        typeVertical = type;

        return this;
    }

    public Chart gridHorizontal(Color color, double strokeWidth, GridLineData.Type type){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        colorHorizontal = color;
        typeHorizontal = type;

        return this;
    }

    public Chart gridVertical(Color color, GridLineData.Type type, double strokeWidth){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        colorVertical = color;
        typeVertical = type;

        return this;
    }

    public Chart gridHorizontal(Color color, GridLineData.Type type, double strokeWidth){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        colorHorizontal = color;
        typeHorizontal = type;

        return this;
    }

    public Chart gridVertical(double strokeWidth, Color color, GridLineData.Type type){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        colorVertical = color;
        typeVertical = type;

        return this;
    }

    public Chart gridHorizontal(double strokeWidth, Color color, GridLineData.Type type){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        colorHorizontal = color;
        typeHorizontal = type;

        return this;
    }

    public Chart gridVertical(double strokeWidth, GridLineData.Type type, Color color){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        colorVertical = color;
        typeVertical = type;

        return this;
    }

    public Chart gridHorizontal(double strokeWidth, GridLineData.Type type, Color color){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        colorHorizontal = color;
        typeHorizontal = type;

        return this;
    }

    public Chart gridVertical(GridLineData.Type type, Color color, double strokeWidth){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        colorVertical = color;
        typeVertical = type;

        return this;
    }

    public Chart gridHorizontal(GridLineData.Type type, Color color, double strokeWidth){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        colorHorizontal = color;
        typeHorizontal = type;

        return this;
    }

    public Chart gridVertical(GridLineData.Type type, double strokeWidth, Color color){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        colorVertical = color;
        typeVertical = type;

        return this;
    }

    public Chart gridHorizontal(GridLineData.Type type, double strokeWidth, Color color){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        colorHorizontal = color;
        typeHorizontal = type;

        return this;
    }

    
    public Chart build(){
        if(dsList != null){
            series = new Series(dsList, plotGroup.widthProperty(), plotGroup.heightProperty(), xAxis, yAxis);

            if(xAxisData != null){
                if(isLogAxisX) {
                    xAxis = new LogAxis(xAxisData, xAxisType, dsList, xWidth, yHeight);
                }else {
                    xAxis = new Axis(xAxisData, xAxisType, dsList, xWidth, yHeight);
                }
                xAxis.bind(plotGroup.widthProperty(), plotGroup.heightProperty());

                plotGroup.getChildren().add(xAxis);

                Text xAxisTitle = new Text(xAxisData.getTitle().getValue());
                xAxisTitle.setFont(xAxisData.getTitle().getFont());
                BorderPane.setAlignment(xAxisTitle, Pos.CENTER);

                plotContainer.setBottom(xAxisTitle);
            }
            if(yAxisData != null){
                if(isLogAxisy){
                    yAxis = new LogAxis(yAxisData, yAxisType, dsList, xWidth, yHeight);
                }else {
                    yAxis = new Axis(yAxisData, yAxisType, dsList, xWidth, yHeight);
                }
                yAxis.bind(plotGroup.widthProperty(), plotGroup.heightProperty());

                plotGroup.getChildren().add(yAxis);

                Text yAxisTitle = new Text(yAxisData.getTitle().getValue());
                yAxisTitle.setFont(yAxisData.getTitle().getFont());
                yAxisTitle.setRotate(-90);
                BorderPane.setAlignment(yAxisTitle, Pos.CENTER);

                plotContainer.setLeft(yAxisTitle);
            }
            if(titleData != null){
                Text title = new Text(titleData.getValue());
                title.setFont(titleData.getFont());
                setAlignment(title, Pos.CENTER);

                plotContainer.setTop(title);
            }
            if(legendOn){
                legend = new Legend(dsList);

                plotContainer.setRight(legend);
            }
            if(gridVerticalOn)
            {
                gridVertical = new Grid(xAxis, yAxis, new GridLineData(GridLineData.Direction.VERTICAL, strokeWidthVertical, colorVertical, typeVertical));
                for (GridLine gridLine : gridVertical.gridLines) {
                    plotGroup.getChildren().add(gridLine);
                }
            }
            if(gridHorizontalOn)
            {
                gridHorizontal = new Grid(xAxis, yAxis, new GridLineData(GridLineData.Direction.HORIZONTAL, strokeWidthHorizontal, colorHorizontal, typeHorizontal));
                for (GridLine gridLine : gridHorizontal.gridLines) {
                    plotGroup.getChildren().add(gridLine);
                }
            }
            plotGroup.getChildren().add(series);

        }else{
            throw new NullPointerException("No data provided");
        }
        
        return this;
    }
}
