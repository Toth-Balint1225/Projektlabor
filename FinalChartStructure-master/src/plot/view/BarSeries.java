package plot.view;

import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import plot.data.DataSeries;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import plot.data.LineData;
import plot.view.BarChart.Orientation;

public class BarSeries extends Group {
    private DoubleProperty widthProperty;
    private DoubleProperty heightProperty;

    private List<List<Rectangle>> rectangleSetList;
    private double seriesLayoutY = 0;

    private double space;
    private double minimalWidth;

    private Orientation orient;

    public BarSeries(ArrayList<DataSeries> dsList, DoubleProperty widthProperty, DoubleProperty heightProperty, double space, double minimalWidth, Orientation orientation){
        this.widthProperty = widthProperty;
        this.heightProperty = heightProperty;

        this.space = space;
        this.minimalWidth = minimalWidth;

        this.orient = orientation;

        rectangleSetList = new ArrayList<>();

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

            List<Rectangle> rectangleSet = new ArrayList<>();

            for(int r = 0; r < points.length; r += 2){
                Rectangle rect = new Rectangle(points[r], points[r+1]);
                rectangleSet.add(rect);

                this.getChildren().add(rect);
            }

            for(int in = 0; in < rectangleSet.size(); in++){
                rectangleSet.get(in).setStroke(dsList.get(i).getLineData().getColor());
                rectangleSet.get(in).setFill(dsList.get(i).getLineData().getColor());
                rectangleSet.get(in).setStrokeWidth(dsList.get(i).getLineData().getWidth());


                if(dsList.get(i).getLineData().getLineType().equals(LineData.Type.DASH)){
                    rectangleSet.get(in).getStrokeDashArray().addAll(4.0,8.0);
                    LinearGradient gradient = new LinearGradient(0, 0, 10, 10, false, CycleMethod.REPEAT,
                            new Stop(0, dsList.get(i).getLineData().getColor()),
                            new Stop(0.5, new Color(0, 0, 0, 0))
                    );

                    rectangleSet.get(in).setFill(gradient);
                }
            }

            rectangleSetList.add(rectangleSet);
        }
        final double finalMinValue = minValue;

        // resize listeners
        this.widthProperty.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // polyline update
                //change X coordinates only
                if(orient == Orientation.VERTICAL) {

                    for(List<Rectangle> list : rectangleSetList){
                        for(int x = 0; x < list.size(); x++){


                            list.get(x).setX(x * widthProperty.subtract(80).doubleValue() / dataLength + 48);

                            double width = widthProperty.subtract(80).doubleValue() / dataLength;

                            if (width - space < minimalWidth) {
                                if (width <= minimalWidth) {
                                    list.get(x).setWidth(width);
                                } else {
                                    list.get(x).setWidth(minimalWidth);
                                }
                            } else {
                                list.get(x).setWidth(width - space);
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < dsList.size(); i++) {
                        DataSeries ds = dsList.get(i);
                        final double[] data = ds.getData();

                        List<Rectangle> list = rectangleSetList.get(i);

                        for(int y = 0; y < list.size(); y++){
                            double pos = 48.0;

                            list.get(y).setX(pos);

                            list.get(y).setWidth(((data[y] + Math.abs(finalMinValue)) / yRange) * (widthProperty.doubleValue() - pos - 32));
                        }
                    }
                }
            }
        });



        this.heightProperty.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // polyline update
                //change Y coordinates only
                if(orient == Orientation.VERTICAL) {
                    for (int i = 0; i < dsList.size(); i++) {
                        DataSeries ds = dsList.get(i);
                        final double[] data = ds.getData();

                        List<Rectangle> list = rectangleSetList.get(i);

                        for (int y = 0; y < list.size(); y++) {
                            double pos = heightProperty.subtract(32).doubleValue() -
                                    ((data[y] + Math.abs(finalMinValue)) / yRange) * heightProperty.subtract(32).doubleValue();

                            list.get(y).setY(pos);

                            if (list.get(y).getY() > seriesLayoutY) {
                                seriesLayoutY = list.get(y).getY();
                            }

                            list.get(y).setHeight(heightProperty.subtract(32).doubleValue() - pos);
                        }
                    }
                } else {
                    for(List<Rectangle> list : rectangleSetList){
                        for(int x = 0; x < list.size(); x++){


                            list.get(x).setY(x * heightProperty.subtract(32).doubleValue() / dataLength);

                            double width = heightProperty.subtract(32).doubleValue() / dataLength;

                            if (width - space < minimalWidth) {
                                if (width <= minimalWidth) {
                                    list.get(x).setHeight(width);
                                } else {
                                    list.get(x).setHeight(minimalWidth);
                                }
                            } else {
                                list.get(x).setHeight(width - space);
                            }
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
