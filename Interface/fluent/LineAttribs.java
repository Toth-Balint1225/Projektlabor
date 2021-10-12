package fluent;

public class LineAttribs {
    private String color;
    private int width;
    private String style;

    public enum LineStyle {
        STRAIGHT,
        DOT,
        DASH,
        DASH_DOT
    }

    public enum MarkerType {
        SQUARE,
        CROSS,
        CIRCLE,
    }

    public LineAttribs() {
        color = "red";
        width = 1;
        style = "-";
    }

    static public LineAttribs create() {
        return new LineAttribs();
    }

    public LineAttribs color(String color) {
        this.color = color;
        return this;
    }

    public LineAttribs width(int width) {
        this.width = width;
        return this;
    }

    public LineAttribs style(String style) {
        this.style = style;
        return this;
    }

    public LineAttribs style(LineStyle style) {
        return this;
    }
}