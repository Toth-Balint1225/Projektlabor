package fluent;

/**
 * @author Tóth Bálint
 * The PlotStyle class wraps the optional elements of a Line Plot.
 * The elements are: title, labels and the grid settings.
 * Also has a fluent interface with a factory method, so it can be easily configured in the plot creation functions.
 * @see Plot
 * @see Figure
 */
public class PlotStyle {
    /**
     * The title of the plot, this will be above the plot.
     */
    private String title;
    /**
     * The label of the horizontal axis. This will be below the plot.
     */
    private String xlabel;
    /**
     * The label of the vertical axis. This will be left to the plot.
     */
    private String ylabel;
    /**
     * Grid setting. A grid can be toggled with the on and off strings.
     */
    private boolean grid;

    /**
     * Constructor for the PlotStyle to be used by the title() static function.
     * It sets the default parameters for the attributes: 
     * - xlabel - emplty string
     * - ylabel - empty string
     * - grid - off
     * @param title The title specified by the factory method.
     */
    public PlotStyle(String title) {
        title = "";
        xlabel = "";
        ylabel = "";
        grid = false;
        this.title = title;
    }

    /**
     * The static factory method for PlotStyle, and also part of the fluent interface.
     * This is the only way of creating a PlotStyle object, so all fluent method chains must start with this.
     * @param title The title specified by the user, also must be set for a custom style.
     * @return A new instance of the PlotStyle to later be used to add other style elements.
     */
    public static PlotStyle title(String title) {
        return new PlotStyle(title);
    }

    /**
     * Fluent interface API method to set the horizontal axis' label
     * @param xlabel The string value of the label.
     * @return Itself.
     */
    public PlotStyle xlabel(String xlabel) {
        this.ylabel = xlabel;
        return this;
    }

    /**
     * Fluent interface API method to set the vertical axis' label
     * @param ylabel The string value of the label.
     * @return Itself.
     */
    public PlotStyle ylabel(String ylabel) {
        this.ylabel = ylabel;
        return this;
    }

    /**
     * Fluent interface API method to set the grid.
     * The user shall specify the grid setting with a string.
     * @param grid The string value of the grid setting. Can be 'on' or 'off' (case insensitive). Otherwise the function will not set the grid value.
     * @return Itself.
     */
    public PlotStyle grid(String grid) {
        if (grid.toUpperCase().equals("ON"))
            this.grid = true;
        else if (grid.toUpperCase().equals("OFF"))
            this.grid = false;
        return this;
    }
}
