/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot.view;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import static javafx.scene.layout.BorderPane.setAlignment;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import plot.data.AxisData;
import plot.data.DataSeries;
import plot.data.GridLineData;
import plot.data.LabelData;
import plot.data.LineData;
import plot.data.TitleData;

/**
 *
 * @author kiand
 */
public class BarChart extends BorderPane{

    public enum Orientation{HORIZONTAL, VERTICAL};
 
    //Containers
    private FigureContainer plotContainer;
    private PlotGroup plotGroup;
    
    //Possible nodes
    private Axis xAxis;
    private Axis yAxis;
    private BarSeries series;
    private Legend legend;
    
    //-----Node DATA-----
    //xAxis Data
    private AxisData xAxisData;
    private Axis.AxisType xAxisType;
    private double xWidth;
    
    //yAxis Data
    private AxisData yAxisData;
    private Axis.AxisType yAxisType;
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

    private double strokeWidthVertical;
    private double strokeWidthHorizontal;

    private Color colorVertical = Color.LIGHTGRAY;
    private Color colorHorizontal = Color.LIGHTGRAY;

    private GridLineData.Type typeVertical;
    private GridLineData.Type typeHorizontal;

    //spacing
    private double space;
    private double minimalBarWidth;

    //horizontal
    private Orientation barOrientation;


    public BarChart() {
        plotContainer = new FigureContainer();       
        setCenter(plotContainer);   

        plotGroup = new PlotGroup();
        plotContainer.addPlot(plotGroup);
        
        legendOn = false;

        space = 0;
        minimalBarWidth = 0;

        strokeWidthHorizontal = 0.5;
        strokeWidthVertical = 0.5;

        typeVertical = GridLineData.Type.SOLID;
        typeHorizontal = GridLineData.Type.SOLID;

        barOrientation = Orientation.VERTICAL;
    }

    public BarChart xAxis(){
        xAxisData = new AxisData(
                new TitleData("xAxis"), new LabelData(), 
                1, 0.5, new LineData(Color.BLACK, 1, LineData.Type.SOLID));
        xAxisType = Axis.AxisType.HORIZONTAL;
        xWidth = 500;
        
        return this;
    }
    
    public BarChart yAxis(){
        yAxisData = new AxisData(
                new TitleData("yAxis"), new LabelData(), 
                1, 0.5, new LineData(Color.BLACK, 1, LineData.Type.SOLID));
        yAxisType = Axis.AxisType.VERTICAL;
        yHeight = 300;
        
        return this;
    }
    
    
    public BarChart xAxis(AxisData axisData){
        xAxisData = axisData;
        xAxisType = Axis.AxisType.HORIZONTAL;
        xWidth = 500;      
        
        return this;
    }
    
    public BarChart yAxis(AxisData axisData){
        yAxisData = axisData;
        yAxisType = Axis.AxisType.VERTICAL;
        yHeight = 300;
                
        return this;
    }
    
    public BarChart series(ArrayList<DataSeries> dsList){
        this.dsList=dsList;
        
        return this;
    }

    public BarChart title(TitleData titleData) {
        this.titleData = titleData;

        return this;
    }
    
    public BarChart legend(){
        this.legendOn = true;
        
        return this;
    }

    //sajat
    public BarChart gridVertical(){
        this.gridVerticalOn = true;
        System.out.println(strokeWidthVertical);

        return this;
    }

    public BarChart gridHorizontal(){
        this.gridHorizontalOn = true;
        System.out.println(strokeWidthHorizontal);

        return this;
    }

    public BarChart spacing(double space, double minimalBarWidth){
        this.space = space;
        this.minimalBarWidth = minimalBarWidth;

        return this;
    }

    public BarChart spacing(double space){
        this.space = space;
        this.minimalBarWidth = 20;

        return this;
    }

    public BarChart spacing(){
        this.space = 10;
        this.minimalBarWidth = 20;

        return this;
    }

    public BarChart gridVertical(Color color){
        this.gridVerticalOn = true;
        colorVertical = color;

        return this;
    }

    public BarChart gridHorizontal(Color color){
        this.gridHorizontalOn = true;
        colorHorizontal = color;

        return this;
    }

    public BarChart gridVertical(double strokeWidth){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;

        return this;
    }

    public BarChart gridHorizontal(double strokeWidth){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;

        return this;
    }

    public BarChart gridVertical(GridLineData.Type type){
        this.gridVerticalOn = true;
        typeVertical = type;

        return this;
    }

    public BarChart gridHorizontal(GridLineData.Type type){
        this.gridHorizontalOn = true;
        typeHorizontal = type;

        return this;
    }

    public BarChart gridVertical(Color color, double strokeWidth){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        colorVertical = color;

        return this;
    }

    public BarChart gridHorizontal(Color color, double strokeWidth){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        colorHorizontal = color;

        return this;
    }

    public BarChart gridVertical(Color color, GridLineData.Type type){
        this.gridVerticalOn = true;
        colorVertical = color;
        typeVertical = type;

        return this;
    }

    public BarChart gridHorizontal(Color color, GridLineData.Type type){
        this.gridHorizontalOn = true;
        colorHorizontal = color;
        typeHorizontal = type;

        return this;
    }

    public BarChart gridVertical(double strokeWidth, Color color){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        colorVertical = color;

        return this;
    }

    public BarChart gridHorizontal(double strokeWidth, Color color){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        colorHorizontal = color;

        return this;
    }

    public BarChart gridVertical(double strokeWidth, GridLineData.Type type){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        typeVertical = type;

        return this;
    }

    public BarChart gridHorizontal(double strokeWidth, GridLineData.Type type){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        typeHorizontal = type;

        return this;
    }

    public BarChart gridVertical(GridLineData.Type type, Color color){
        this.gridVerticalOn = true;
        colorVertical = color;
        typeVertical = type;

        return this;
    }

    public BarChart gridHorizontal(GridLineData.Type type, Color color){
        this.gridHorizontalOn = true;
        colorHorizontal = color;
        typeHorizontal = type;

        return this;
    }

    public BarChart gridVertical(GridLineData.Type type, double strokeWidth){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        typeVertical = type;

        return this;
    }

    public BarChart gridHorizontal(GridLineData.Type type, double strokeWidth){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        typeHorizontal = type;

        return this;
    }

    public BarChart gridVertical(Color color, double strokeWidth, GridLineData.Type type){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        colorVertical = color;
        typeVertical = type;

        return this;
    }

    public BarChart gridHorizontal(Color color, double strokeWidth, GridLineData.Type type){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        colorHorizontal = color;
        typeHorizontal = type;

        return this;
    }

    public BarChart gridVertical(Color color, GridLineData.Type type, double strokeWidth){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        colorVertical = color;
        typeVertical = type;

        return this;
    }

    public BarChart gridHorizontal(Color color, GridLineData.Type type, double strokeWidth){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        colorHorizontal = color;
        typeHorizontal = type;

        return this;
    }

    public BarChart gridVertical(double strokeWidth, Color color, GridLineData.Type type){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        colorVertical = color;
        typeVertical = type;

        return this;
    }

    public BarChart gridHorizontal(double strokeWidth, Color color, GridLineData.Type type){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        colorHorizontal = color;
        typeHorizontal = type;

        return this;
    }

    public BarChart gridVertical(double strokeWidth, GridLineData.Type type, Color color){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        colorVertical = color;
        typeVertical = type;

        return this;
    }

    public BarChart gridHorizontal(double strokeWidth, GridLineData.Type type, Color color){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        colorHorizontal = color;
        typeHorizontal = type;

        return this;
    }

    public BarChart gridVertical(GridLineData.Type type, Color color, double strokeWidth){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        colorVertical = color;
        typeVertical = type;

        return this;
    }

    public BarChart gridHorizontal(GridLineData.Type type, Color color, double strokeWidth){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        colorHorizontal = color;
        typeHorizontal = type;

        return this;
    }

    public BarChart gridVertical(GridLineData.Type type, double strokeWidth, Color color){
        this.gridVerticalOn = true;
        strokeWidthVertical = strokeWidth;
        colorVertical = color;
        typeVertical = type;

        return this;
    }

    public BarChart gridHorizontal(GridLineData.Type type, double strokeWidth, Color color){
        this.gridHorizontalOn = true;
        strokeWidthHorizontal = strokeWidth;
        colorHorizontal = color;
        typeHorizontal = type;

        return this;
    }

    //horizontal
    public BarChart barOrientation(Orientation orientation){
        this.barOrientation = orientation;

        return this;
    }


    public BarChart build(){
        if(dsList != null){
            series = new BarSeries(dsList, plotGroup.widthProperty(), plotGroup.heightProperty(), space, minimalBarWidth, barOrientation);
            plotGroup.getChildren().add(series);

            if(barOrientation == Orientation.VERTICAL) {
                if (xAxisData != null) {
                    xAxis = new Axis(xAxisData, xAxisType, dsList, xWidth, yHeight);
                    xAxis.bind(plotGroup.widthProperty(), plotGroup.heightProperty());

                    plotGroup.getChildren().add(xAxis);

                    Text xAxisTitle = new Text(xAxisData.getTitle().getValue());
                    xAxisTitle.setFont(xAxisData.getTitle().getFont());
                    BorderPane.setAlignment(xAxisTitle, Pos.CENTER);

                    plotContainer.setBottom(xAxisTitle);
                }
                if (yAxisData != null) {
                    yAxis = new Axis(yAxisData, yAxisType, dsList, xWidth, yHeight);
                    yAxis.bind(plotGroup.widthProperty(), plotGroup.heightProperty());

                    plotGroup.getChildren().add(yAxis);

                    Text yAxisTitle = new Text(yAxisData.getTitle().getValue());
                    yAxisTitle.setFont(yAxisData.getTitle().getFont());
                    yAxisTitle.setRotate(-90);
                    BorderPane.setAlignment(yAxisTitle, Pos.CENTER);

                    plotContainer.setLeft(yAxisTitle);
                }
            } else {
                if (xAxisData != null) {
                    xAxis = new Axis(xAxisData, xAxisType, dsList, xWidth, yHeight);
                    xAxis.bind(plotGroup.widthProperty(), plotGroup.heightProperty());

                    plotGroup.getChildren().add(xAxis);

                    Text xAxisTitle = new Text(yAxisData.getTitle().getValue());
                    xAxisTitle.setFont(yAxisData.getTitle().getFont());
                    BorderPane.setAlignment(xAxisTitle, Pos.CENTER);

                    plotContainer.setBottom(xAxisTitle);
                }
                if (yAxisData != null) {
                    yAxis = new Axis(yAxisData, yAxisType, dsList, xWidth, yHeight);
                    yAxis.bind(plotGroup.widthProperty(), plotGroup.heightProperty());

                    plotGroup.getChildren().add(yAxis);

                    Text yAxisTitle = new Text(xAxisData.getTitle().getValue());
                    yAxisTitle.setFont(xAxisData.getTitle().getFont());
                    yAxisTitle.setRotate(-90);
                    BorderPane.setAlignment(yAxisTitle, Pos.CENTER);

                    plotContainer.setLeft(yAxisTitle);
                }
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
        }else{
            throw new NullPointerException("No data provided");
        }
        
        return this;
    }
}
