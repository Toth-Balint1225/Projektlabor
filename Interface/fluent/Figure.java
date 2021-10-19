package fluent;

import java.util.LinkedList;
import javafx.scene.layout.BorderPane;

public class Figure extends BorderPane {
    private String title;
    private int xpos;
    private int ypos;
    private LinkedList<Plot> plots;

    public Figure() {
        this.plots = new LinkedList<Plot>();
        title = "Figure";
        xpos = 100;
        ypos = 100;
    }
    
    static public Figure figure() {
        return new Figure();
    }

    public Figure title(String title) {
        this.title = title;
        return this;
    }

    public Figure position(int xpos, int ypos) {
        this.xpos = xpos;
        this.ypos = ypos;
        return this;
    }

    public Figure plot(Line ... lines) {
        Plot p = new Plot(lines);
        this.plots.add(p);
        return this;
    }
    
    public Figure plot(PlotStyle style, Line ... lines) {
        Plot p = new Plot(lines,style);
        this.plots.add(p);
        return this;
    }


    public Figure display() {
        for (Plot p : plots) {
            this.setCenter(p.display());
        }
        return this;
    }

}