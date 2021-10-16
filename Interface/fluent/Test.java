package fluent;

// this is experimental and subject to change
// with this, we won't need the LineAttribs.lineAttribs(), but just lineAttribs
// btw, this is the static factory method of the lineAttribs class
import static fluent.LineAttribs.lineAttribs;

// figure's static import
import static fluent.Figure.figure;

// the other lineAttribs' static import
import static fluent.LineAttribs2.style;

// static import for the line object
import static fluent.Line.*;

/*

the goal is:
Figure
    .scatter(x,y,LineAttribs
        .color("r")
        .style("-")
        .width(2))
    .line(x,y,"Color:red","LineStyle:-")
    .xlabel("X")
    .ylabel("Y")
    .title("Just a test");
or something similar
    plot(x,y,String ... lineAttribs)
*/

public class Test {

    public static void main(String[] args) {
        double[] x = new double[]{1,2,3};
        double[] y = new double[]{4,5,6};


        Figure figure = figure()
            .plot(x,y,lineAttribs()
                .color("r")
                .style("-")
                .width(2))
            .plot(y,x,"Color:red","Width:3")
        ;

        // another solution would be:... .plot(x,y,style("-").color("r").width(2)), where style is the obligatory static factory method
        // let's call the example subject plot2

        Figure other = figure()
            .plot(x,y,style("-").color("r").width(3));

        // inputting multiple lines to a plot function
        Figure unlimited = figure()
            .plot(line(x,y,style("-").width(2))
                , line(y,x,"Style:-","Color:green")
                , line(x,y,"Style:--","Color:blue")
            )
        ;

        System.out.println(figure);
        System.out.println(other);
        System.out.println(unlimited);
    }
}