package fluent;

import javafx.geometry.Insets;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.LinkedList;



public class Plot extends BorderPane {
    LinkedList<Line> lines;
    PlotStyle style;

    public Plot() {
        this.lines = new LinkedList<>();
    }

    public Plot(Line[] lines) {
        this.lines = new LinkedList<Line>(Arrays.asList(lines));
    }

    public Plot(Line[] lines, PlotStyle style) {
        this.lines = new LinkedList<Line>(Arrays.asList(lines));
        this.style = style;
    }

    public Plot display() {
        LinePlotPane lp = new LinePlotPane(lines,minX(),maxX(),minY(),maxY());
        this.setPadding(new Insets(10, 10, 10, 10));
        lp.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setPrefSize(400, 300);
        this.setCenter(lp);
        return this;
    }

    private double maxX() {
        double max = lines.getFirst().getMaxX();
        for (Line line : lines) {
            if (max < line.getMaxX())
                max  = line.getMaxX();
        }
        return max;
    }

    private double minX() {
        double min = lines.getFirst().getMinX();
        for (Line line : lines) {
            if (min > line.getMinX())
                min = line.getMinX();
        }
        return min;
    }

    private double maxY() {
        double max = lines.getFirst().getMaxY();
        for (Line line : lines) {
            if (max < line.getMaxY())
                max  = line.getMaxY();
        }
        return max;
    }

    private double minY() {
        double min = lines.getFirst().getMinY();
        for (Line line : lines) {
            if (min > line.getMinY())
                min = line.getMinY();
        }
        return min;
    }

}