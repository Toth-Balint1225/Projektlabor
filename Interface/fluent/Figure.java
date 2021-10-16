package fluent;

public class Figure {
    private String title;
    private Plot plot;

    public Figure title(String title) {
        this.title = title;
        return this;
    }

    @Deprecated
    public Figure plot(double[] x, double[] y, LineAttribs attr) {
        this.plot = new Plot();
        return this;
    }

    @Deprecated
    public Figure plot(double[] x, double[] y, String ... args) {
        LineAttribs attribs = new LineAttribs();
        // for each pair -> parse attribs
        // what exactly is parse?
        // we make an intermediate object (see above)
        // we pass the intermediate object and  the key-value pair
        // the parser calls the appropriate setter on the intermediate object
        // the parser returns the extended object and has fun with it


        return this;
    }

    // some redesign is needed for the plot class: 
    // instead of taking two arrays and the formatting strings, it will take
    // an unspecified number of "line" objects, so we can display multiple lines in one plot
    // line would have a static factory method, so the whole thing should look like
    /*
        figure()
            .plot(line(x,y,"Style:-")
                , line(x2,y2,"Color:red","Style:--")
                , line(x3,y3,style("-").color("red")));
    */
    public Figure plot(Line ... lines) {
        return this;
    }

    // another static import with the static factory
    static public Figure figure() {
        return new Figure();
    }

}