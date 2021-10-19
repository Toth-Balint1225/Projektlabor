package fluent;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tóth Bálint
 * @see Plot
 * The LinePlotPane class handles all the Plot classes displays. It is capable of displaying multiple lines.
 */
public class LinePlotPane extends Pane {

    /**
     * The PolyContainer class stores polylines and normalized coordinates as well as the point buffer.
     * It is a convinience wrapper for easier storage of the Polylines, because the listeners require access to the
     * normalized coordinates in order to resize the plot.
     */
    private class PolyContainer {
        /**
         * A polyline object which is the line plot itself.
         */
        public Polyline poly;
        /**
         * The x coordinates of the normalized data points.
         */
        public double[] xs;
        /**
         * The y coordinates of the normalized data points.
         */
        public double[] ys;

        /**
         * Constructs a PolyContainer wrapper for a line objects, using the line's normalized coordinates.
         * @param line The Line object to be displayed
         * @param minX Precalculated minnimum of the domain (x axis), used by Line.getNormal()
         * @param maxX Maximum of the domain
         * @param minY Minimum of the data points
         * @param maxY Maximum of the data points
         */
        public PolyContainer(Line line, double minX, double maxX, double minY, double maxY) {
            xs = line.getNormalX(minX, maxX);
            ys = line.getNormalY(minY, maxY);
            double[] points = new double[2*xs.length];
            for (int i=0;i<xs.length;i++) {
                points[2*i] = xs[i];
                points[2*i+1] = (1.d-ys[i]);
            }
            poly = new Polyline(points);
        }
    }

    /**
     * The Polylines the Pane displays, wrapped in a PolyContainer, to always have access to the normalized x and y coordinates.
     */
    private LinkedList<PolyContainer> polys;

    /**
     * Constructs a Pane that contains all the polylines based on the supplied Line objects, and adds the resize Listeners.
     * The Listeners are used to recalculate the screen-space coordinates of the polylines representing the plot.
     * The minimums and maximums should be the extremums of the whole domain and image of all the lines specified in the parameters, because
     * these will be the scaling factors the Line uses to normalize its components.
     * @param lines The lines to be drawn.
     * @param minX Precalculated minimum of the domain supplied by the Plot that requests the drawing
     * @param maxX Maximum of the domain
     * @param minY Minimum of the data points (y coordinates)
     * @param maxY Maximum of the data points
     */
    public LinePlotPane(List<Line> lines, double minX, double maxX, double minY, double maxY) {
        // init
        polys = new LinkedList<>();

        // construct a PolyContainer from every line 
        for (Line line : lines) {
            PolyContainer actual = new PolyContainer(line, minX, maxX, minY, maxY);
            this.getChildren().add(actual.poly);
            polys.add(actual);
        }

        /* Listeners
            Every rescale does the following:
            o get all the polylines from the PolyContainer list
            o get the data points of the polyline
            o recalculate the screen-space coordinates of the line
              with the use of the normalized x and y coordinates stored in the Container
        */
        this.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // for each polylines
                for (PolyContainer actual : polys) {
                    // the points are represented as a list of double values
                    ObservableList<Double> polyPoints = actual.poly.getPoints();
                    // the points stores the x and y coordinates continously, so in this case we only need the 2*i-th element
                    for (int i=0;i<actual.xs.length;i++) {
                        // screen-space transformaton: take the normal coordinate and multiply by the width of the Pane
                        polyPoints.set(2*i,actual.xs[i] * widthProperty().doubleValue());
                    }
                }
            }
        });

        this.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                for (PolyContainer actual : polys) {
                    ObservableList<Double> polyPoints = actual.poly.getPoints();
                    for (int i=0;i<actual.ys.length;i++) {
                        // take the normal coordinate and multiply by the height of the Pane
                        // important: the screen's (0,0) is at the top left, so to filp the picture, we need to subtract it from 1
                        polyPoints.set(2*i+1,(1.d - actual.ys[i]) * heightProperty().doubleValue());
                    }   
                }
            }
        });
    }
}