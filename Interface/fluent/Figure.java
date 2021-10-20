package fluent;

import java.util.LinkedList;
import javafx.scene.layout.BorderPane;

/**
 * @author Tóth Bálint
 * The Figure class represents a window to hold plots. A figure is the highest level of the graphing API and its 
 * fluent interface. The fluent interface follows the specificateion of the MATLAB programming language.
 * At the level of a figure, the end user can invoke the Figure.figure() static factory method to create a window
 * for the plots to later be positioned in. The user can also specify the title and the position of the window on screen,
 * via the title() and position() fluent interface methods. The Figure.plot() and its overloads can be used to add a new plot to 
 * the figure window. It acts as a factory method for the Plot class.
 * @see Plot
 * @see Line
 */
public class Figure extends BorderPane {
    /**
     * The title to be displayed on the window.
     */
    private String title;
    /**
     * The position of the window on screen.
     */
    private int xpos;
    private int ypos;
    /**
     * The plots of the window stored in a list. They will be added in a grid like layout.
     */
    private LinkedList<Plot> plots;

    /**
     * Default constructs a Figure and initializes the plot container list.
     * Defaults:
     * - title - Figure
     * - xpos - 100
     * - ypos - 100
     */
    public Figure() {
        this.plots = new LinkedList<Plot>();
        title = "Figure";
        xpos = 100;
        ypos = 100;
    }
    
    /**
     * Static factory API method to create the new figure to later be configured with plots.
     * @return A new instance of the Figure class with the default constructor.
     */
    static public Figure figure() {
        return new Figure();
    }

    /**
     * Fluent interface API method to modify the title of the Figure window.
     * @param title The string to be used as the title.
     * @return Itself, as a fluent interface method.
     */
    public Figure title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Fluent interface API method to modify the position of the window on screen.
     * @param xpos The horizontal distance of the window's edge from the screen's edge.
     * @param ypos The vertical distance of the window's edge from the screen's edge.
     * @return Itself, as a fluent interface method.
     */
    public Figure position(int xpos, int ypos) {
        this.xpos = xpos;
        this.ypos = ypos;
        return this;
    }

    /**
     * Fluent interface API method to add a plot to the window with default parameters.
     * It acts as a factory method for the Plot class.
     * The function also adds the created plot to the plot container list.
     * @param lines The list of Line objects to be displayed in the plot.
     * @return Itself.
     */
    public Figure plot(Line ... lines) {
        Plot p = new Plot(lines);
        this.plots.add(p);
        return this;
    }
    
    /**
     * Fluent interface API method to add a plot to the window with specified plot style.
     * The plot style can also be created with fluent interface methods.
     * @see PlotStyle
     * @param style The Plot's optional parameters, like title and labels. 
     * @param lines The list of line objects to be displayed in the plot.
     * @return Itself.
     */
    public Figure plot(PlotStyle style, Line ... lines) {
        Plot p = new Plot(lines,style);
        this.plots.add(p);
        return this;
    }


    /**
     * Initializes the components of the figure. After this function the figure can be 
     * used to create a JavaFX scene. Pronbably will be hidden from the end user.
     * @return Itself, as a part of the fluent interface.
     */
    public Figure display() {
        for (Plot p : plots) {
            this.setCenter(p.display());
        }
        return this;
    }

}