package fluent;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import static fluent.Figure.*;
import static fluent.Line.*;
import static fluent.LineStyle.*;
import static fluent.PlotStyle.*;


public class Test extends Application {

    // JavaFX interoperability
    // embedding FX into a swing application
    @Override
    public void start(Stage primaryStage) throws Exception {
        double[] xs1 = new double[] {1,2};
        double[] ys1 = new double[] {1,2};
        double[] xs2 = new double[] {2,3};
        double[] ys2 = new double[] {3,2};

        Figure fig = figure()
            .title("Demo figure").position(300,400)
            .plot(
                title("First").ylabel("data points").xlabel("measurment points")
                , line(xs1,ys1,style("-").color("green").width(2)) 
                , line(xs2,ys2,"Style;:","Color;red","Width;1"))
            .display();

        primaryStage.setScene(new Scene(fig));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}