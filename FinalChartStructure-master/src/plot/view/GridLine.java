package plot.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import plot.data.GridLineData;

public class GridLine extends Group {
    Line gridLine;

    public GridLine(Axis axis, Line tickLine, GridLineData gridLineData) {
        gridLine = new Line(0,0,0,1);
        if(axis.getAxisType() == Axis.AxisType.VERTICAL) {
            gridLine.startXProperty().bind(tickLine.startXProperty());
            gridLine.endXProperty().bind(tickLine.endXProperty());
            gridLine.startYProperty().bind(axis.getAxisLine().startYProperty());
            gridLine.endYProperty().bind(axis.getAxisLine().endYProperty());
        }else if(axis.getAxisType() == Axis.AxisType.HORIZONTAL){
            gridLine.startYProperty().bind(tickLine.startYProperty());
            gridLine.endYProperty().bind(tickLine.endYProperty());
            gridLine.startXProperty().bind(axis.getAxisLine().startXProperty());
            gridLine.endXProperty().bind(axis.getAxisLine().endXProperty());
        }
        gridLine.setStrokeWidth(gridLineData.strokeWidth);
        gridLine.setFill(gridLineData.color);
        gridLine.setStroke(gridLineData.color);
        if(gridLineData.type.equals(GridLineData.Type.DASH)) {
            gridLine.getStrokeDashArray().addAll(6.0, 8.0);
        }else if(gridLineData.type.equals(GridLineData.Type.DOT_LINE)) {
            gridLine.getStrokeDashArray().addAll(1.0, 7.0);
        }
        this.getChildren().add(gridLine);
    }

}
