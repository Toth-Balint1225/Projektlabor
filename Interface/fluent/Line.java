package fluent;

public class Line {

    // line components
    private double[] xs;
    private double[] ys;

    // line attributes
    LineAttribs2 attribs;

    // just an empty constructor for now
    public Line(double[] xs, double[] ys, LineAttribs2 attribs) {
        this.xs = xs;
        this.ys = ys;
        this.attribs = attribs;
    }

    // the static factory method
    public static Line line(double[] xs, double[] ys, LineAttribs2 attribs) {
        return new Line(xs,ys,attribs);
    }

    // another static factory method with the string arguments and the parsing
    public static Line line(double[] xs, double[] ys, String ... args) {
        LineAttribs2 attribs = parseAttribs(args);
        return new Line(xs,ys,attribs);
    }

    // the parser method for the string array
    private static LineAttribs2 parseAttribs(String[] args) {
        LineAttribs2 toReturn = new LineAttribs2("-");
        // foreach arg parse and append to toReturn
        return toReturn;
    }
}
