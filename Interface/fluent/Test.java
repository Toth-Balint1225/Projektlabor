package fluent;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

// static imports so the factory methods can be used without the scope
import static fluent.Figure.*;
import static fluent.Line.*;
import static fluent.LineStyle.*;
import static fluent.PlotStyle.*;


public class Test extends Application {

    // TODO
    // JavaFX interoperability
    // embedding FX into a swing application
    @Override
    public void start(Stage primaryStage) throws Exception {
        // some example lines to demonstrate the plot scaling
        double[] xs1 = new double[] {1,2};
        double[] ys1 = new double[] {1,2};
        double[] xs2 = new double[] {2,3};
        double[] ys2 = new double[] {3,2};

        // Figure creation with the fluent interface
        Figure fig = figure()                                                       // create the figure window
            .title("Demo figure").position(300,400)                                 // set the figure attributes
            .plot(                                                                  // start the plot
                title("First").ylabel("data points").xlabel("measurment points")    // plot attributes with the PlotStyle class
                , line(xs1,ys1,style("-").color("green").width(2))                  // line creation with the LineStyle fluent interface
                , line(xs2,ys2,"Style;:","Color;red","Width;1"));                   // line creation with string key-value pairs

        // test code for the figure, this might change in future versions and be hidden from the end user.
        primaryStage.setScene(new Scene(fig.display()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}