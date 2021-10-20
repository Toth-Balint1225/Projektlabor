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


/**
 * @author Tóth Bálint
 * The Plot class is a JavaFX node extension that holds Line objects and displays them.
 * The Plot does not have its own fluent interface, it is meant to be created with the Figure class.
 * In this case the Figure acts as a Factory for Plot. The plot's attributes are stored as a PlotStyle object.
 * @see Figure
 * @see PlotStyle
 */
public class Plot extends BorderPane {
    /**
     * The lines of the plot stored in a list for easier processing.
     */
    LinkedList<Line> lines;

    /**
     * The style of the plot, stored in a PlotStyle object.
     */
    PlotStyle style;

    /**
     * Default constructor for the list initialization.
     */
    public Plot() {
        this.lines = new LinkedList<>();
    }

    /**
     * Constructor that initializes the list from a List array
     * @param lines An array of lists, so it is compatible with the Figure.plot() function's List ... parameter
     */
    public Plot(Line[] lines) {
        this.lines = new LinkedList<Line>(Arrays.asList(lines));
    }

    /**
     * Constructor that initializes every components of the Plot.
     * @param lines An array of lists
     * @param style The style of the plot created with some fluent.
     */
    public Plot(Line[] lines, PlotStyle style) {
        this.lines = new LinkedList<Line>(Arrays.asList(lines));
        this.style = style;
    }

    /**
     * Handles the display of the lines generating a LinePlotPane object and emplaces it to the center of the BorderPane
     * @return Itself, for the Figure.display() function.
     */
    public Plot display() {
        // creates the graphical components
        // this is where the private functions come in handy
        LinePlotPane lp = new LinePlotPane(lines,minX(),maxX(),minY(),maxY());
        this.setPadding(new Insets(10, 10, 10, 10));
        lp.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setPrefSize(400, 300);
        this.setCenter(lp);
        return this;
    }

    /*
        Private methods to calculate the global domain and image end points, that the LinePlotPane and Line classes can use to scale the line images.
        Not the best solutions, bunch of copy-pasta, but it works.
        TODO: refactor these
    */

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