package plot.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import plot.data.AxisData;
import plot.data.DataSeries;
import plot.utils.VisualUtility;

import java.util.ArrayList;

public class LogAxis extends Axis {

    public LogAxis(AxisData axisData, Axis.AxisType axisType, ArrayList<DataSeries> ds, double xWidth, double yHeight) {
        super(axisData, axisType, ds, xWidth, yHeight);
        super.getChildren().removeAll(super.minorTickLines);


        if (axisType.equals(AxisType.HORIZONTAL)) {
            double tempMax = ds.get(0).getMaxValue();
            double tempMin = ds.get(0).getMinValue();
            double minStartTime = ds.get(0).getStartTime();

            for (int i = 1; i < ds.size(); i++) {
                if (ds.get(i).getMaxValue() > tempMax) {
                    tempMax = ds.get(i).getMaxValue();
                }
                if (ds.get(i).getMinValue() < tempMin) {
                    tempMin = ds.get(i).getMinValue();
                }
                if (ds.get(i).getStartTime() < minStartTime) {
                    minStartTime = ds.get(i).getStartTime();
                }
            }

            minorTickCoordinates = VisualUtility.xTickCoordinateGeneratorLog(Math.abs(tempMax) + Math.abs(tempMin),4, xWidth, yHeight, axisData);
            minorTickLines = new ArrayList<>();

            for (int i = 0; i < minorTickCoordinates.size() - 4; i += 4) {
                boolean majorLine = false;
                for (int j = 0; j < majorTickCoordinates.size() - 4; j += 4) {
                    if (minorTickCoordinates.get(i + 4).equals(majorTickCoordinates.get(j + 4))) {
                        majorLine = true;
                    }
                }

                if (majorLine) {
                    Line tempLine = new Line(minorTickCoordinates.get(i + 4), minorTickCoordinates.get(i + 5), minorTickCoordinates.get(i + 6), minorTickCoordinates.get(i + 7));
                    tempLine.setOpacity(0);
                    minorTickLines.add(tempLine);
                } else {
                    Line tempLine = new Line(minorTickCoordinates.get(i + 4), minorTickCoordinates.get(i + 5), minorTickCoordinates.get(i + 6), minorTickCoordinates.get(i + 7));
                    minorTickLines.add(tempLine);
                }
            }

            this.getChildren().addAll(minorTickLines);
        }else{
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

            minorTickCoordinates = VisualUtility.yTickCoordinateGeneratorLog(Math.abs(tempMax)+Math.abs(tempMin), 4, xWidth, yHeight, axisData);
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
        }
    }
}
