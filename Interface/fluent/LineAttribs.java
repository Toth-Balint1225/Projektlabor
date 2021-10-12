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

    // with the static import, the create method can change to lineAttribs
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

    public LineAttribs style(String style) {
        this.style = style;
        return this;
    }

}