package fluent;

public class PlotStyle {
    private String title;
    private String xlabel;
    private String ylabel;
    private boolean grid;

    public PlotStyle(String title) {
        title = "";
        xlabel = "";
        ylabel = "";
        grid = false;
        this.title = title;
    }

    public static PlotStyle title(String title) {
        return new PlotStyle(title);
    }

    public PlotStyle xlabel(String xlabel) {
        this.ylabel = xlabel;
        return this;
    }

    public PlotStyle ylabel(String ylabel) {
        this.ylabel = ylabel;
        return this;
    }

    public PlotStyle grid(String grid) {
        if (grid.toUpperCase().equals("ON"))
            this.grid = true;
        else if (grid.toUpperCase().equals("OFF"))
            this.grid = false;
        return this;
    }
}
