package fluent;


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


        Figure figure = Figure.create()
            .plot(x,y,LineAttribs.create()
                .color("r")
                .style("-")
                .width(2))
            .plot(y,x,"Color:red","Width:3")
        ;


        System.out.println(figure);
    }
}