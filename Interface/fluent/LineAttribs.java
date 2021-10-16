package fluent;

public class LineAttribs {
    private String color;
    private int width;
    private String style;


    public LineAttribs() {
        color = "red";
        width = 1;
        style = "-";
    }

    public LineAttribs(String style) {
        this.style = style;
        color = "red";
        width = 1;
    }

    // with the static import, the create method can change to lineAttribs
    @Deprecated
    static public LineAttribs lineAttribs() {
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

    // new static factory method inherited from the removed
    // LineAttribs2 implementation
    public static LineAttribs style(String style) {
        return new LineAttribs(style);
    }

}