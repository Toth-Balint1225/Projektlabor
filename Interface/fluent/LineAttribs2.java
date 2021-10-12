package fluent;

// this will be the example implementation for plot(x,y,style("-").width(2).color("r"))
public class LineAttribs2 {

    private String style;
    private int width;
    private String color;

    // 1. the static factory method will be style(string)
    
    public static LineAttribs2 style(String style) {
        return new LineAttribs2(style);
    }

    public LineAttribs2(String style) {
        this.style = style;
    }

    // setter methods
    public LineAttribs2 width(int width) {
        this.width = width;
        return this;
    }

    public LineAttribs2 color(String color) {
        this.color = color;
        return this;
    }
}
