package fluent;

/**
 * @author Tóth Bálint
 * The Line class wraps the attributes of a line in a Line Plot, as well as the style of the line.
 * It is used as input for Plot, and part of the Fluent Interface for the API.
 * @see LineStyle
 * @see Plot
 * @see Figure
 */
public class Line {

    private double[] xs;
    private double[] ys;

    LineStyle style;

    /**
     * Creates a Line object and sets the values
     * @param xs The raw x coordinates of the data points
     * @param ys The raw y coordinates of the data points
     * @param style The style of the line wrapped in a LineStyle object
     */
    public Line(double[] xs, double[] ys, LineStyle style) {
        this.xs = xs;
        this.ys = ys;
        this.style = style;
    }

    /**
     * Static factory method to create a new instance without the constructor and the new keyword.
     * This is the first variant to create a line with default properties.
     * @param xs x data points
     * @param ys y data points
     * @return A new instance of the Line, created by the Line constructor
     */
    public static Line line(double[] xs, double[] ys) {
        LineStyle style = LineStyle.style("-").color("red").width(1);
        return new Line(xs,ys,style);
    }

    /**
     * Static factory method overload to create a new instance without the constructor.
     * This is the second variant to create a line with specified style with the LineStyle class.
     * @param xs x data points
     * @param ys y data points
     * @param style A LineStyle object created with it's own fluent interface
     * @return A new instance of Line
     */
    public static Line line(double[] xs, double[] ys, LineStyle style) {
        return new Line(xs,ys,style);
    }

    /**
     * Static factory method overload to create a new instance of Line.
     * This version uses String argument list with "key;value" pairs that later will be parsed by the function
     * @param xs
     * @param ys
     * @param args
     * @return A new instance of Line
     */
    public static Line line(double[] xs, double[] ys, String ... args) {
        LineStyle attribs = parseAttribs(args);
        return new Line(xs,ys,attribs);
    }

    private static LineStyle parseAttribs(String[] args) {
        LineStyle toReturn = new LineStyle();
        return toReturn;
    }

    /**
     * Normalizes the x coordinates of the data points with respect to the global minimum and maximum.
     * @param min The global minimum of all the data points in the whole Plot.
     * @param max The global maximum of all the data points in the whole Plot.
     * @return The array of normalized x data points.
     */
    public double[] getNormalX(double min, double max) {
        double[] normalDomain = new double[xs.length];
        double span = max - min;
        for (int i=0;i<xs.length;i++) {
            normalDomain[i] = xs[i] - min;
            normalDomain[i] /= span;
        }
        return normalDomain;
    }

    /**
     * Normalizes the y coordinates of the data points with respect to the global minimum and maximum.
     * @param min The global minimum of all the data points in the whole Plot.
     * @param max The global maximum of all the data points in the whole Plot.
     * @return The array of normalized y data points.
     */
    public double[] getNormalY(double min, double max) {
        double[] normalData = new double[ys.length];
        double span = max - min;
        for (int i=0;i<ys.length;i++) {
            normalData[i] = ys[i] - min;
            normalData[i] /= span;
        }
        return normalData;
    }

    // getters for later to be used for axis generation

    public double[] getX() {
        return xs;
    }

    public double[] getY() {
        return ys;
    }

    public double getMinX() {
        return getMin(xs);
    }

    public double getMaxX() {
        return getMax(xs);
    }

    public double getMinY() {
        return getMin(ys);
    }

    public double getMaxY() {
        return getMax(ys);
    }

    // private util functions
    // TODO: replace with standard API algorithms
    private double getMin(double[] arr) {
        double min = arr[0];
        for (int i=1;i<arr.length;i++) {
            if (arr[i] < min)
                min = arr[i];
        }
        return min;
    }

    private double getMax(double[] arr) {
        double max = arr[0];
        for (int i=1;i<arr.length;i++) {
            if (arr[i] > max )
                max = arr[i];
        }
        return max;
    }
}
