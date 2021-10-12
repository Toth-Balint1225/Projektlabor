package fluent;

public class Figure {
    private String title;
    private Plot plot;

    public Figure title(String title) {
        this.title = title;
        return this;
    }

    public Figure plot(double[] x, double[] y, LineAttribs attr) {
        this.plot = new Plot();
        return this;
    }

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

    static public Figure create() {
        return new Figure();
    }
}