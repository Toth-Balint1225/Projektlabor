package fluent;

/**
 * @author Tóth Bálint
 * The LineStyle class wraps the attributes of a line:
 * - color
 * - width
 * - style
 * The LineStyle is also part of the Figure's fluent interface, and has it's own static factory method.
 * @see Line
 * @see Figure
 */
public class LineStyle {
    private String color;
    private int width;
    private String style;


    /**
     * Default constructs a LineStyle object.
     * Default values: color - red, width - 1, style - straight
     */
    public LineStyle() {
        color = "red";
        width = 1;
        style = "-";
    }

    /**
     * Constucts a new object with the style specified. It is used by the style() satic factory method.
     * @param style The style supplied by the static factory method.
     */
    public LineStyle(String style) {
        this.style = style;
        color = "red";
        width = 1;
    }

    /**
     * Static factory method to create a new Instance without the constructor.
     * It is part of a fluent interface to create and configure LineStyle objects.
     * All the style configuration has to start with this function.
     * @param style The style string specified by MATLAB.
     * @return A new instance of LineStyle with already set style.
     */
    public static LineStyle style(String style) {
        return new LineStyle(style);
    }

    /**
     * Fluent interface method to configure the colour as a String specified by MATLAB
     * @param color The string name of a color.
     * @return Itself, so it can be chained.
     */
    public LineStyle color(String color) {
        this.color = color;
        return this;
    }

    /**
     * Fluent interface meghod to configure the width of a line.
     * @param width Width as an integer number.
     * @return Itself, so it can be chained to other functions.
     */
    public LineStyle width(int width) {
        this.width = width;
        return this;
    }


}