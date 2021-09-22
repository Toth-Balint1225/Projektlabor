/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plotproject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author juhasz
 */
public class BasicLineChart extends Pane {

    private Map<String, DataSeries> series;
    private Map<String, Color> colours;
    private double xFactor = 4;
    private double yFactor = 25;
    private double xOffset = 0;
    private double yOffset = 100;

    /**
     * canvas to display the channel data as a line plot
     */
    private Canvas signalCanvas;
    private double samplingFreq;
    private int samplesPerWindow;
    private double[] values;
    private int secondsPerPage;
    private double signalPitch;
    private double labelPanelWidth;
    private double magnitude;
    private double lineWidth;
    private int preStimulus;
    private int padding = 20;

    public BasicLineChart() {
        // init drawing parts
        setCacheHint(CacheHint.QUALITY);
        signalCanvas = new Canvas(600+2*padding, 400+2*padding);
        // make sure we resize properly
        signalCanvas.widthProperty().bind(widthProperty());
        signalCanvas.heightProperty().bind(heightProperty());
        
        getChildren().add(signalCanvas);

        magnitude = 1.0;
        
        // init data structures
        series = new HashMap<>();
        colours = new HashMap<>();

        ChangeListener<Number> listener = (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            draw(); // redraw chart
        };
        widthProperty().addListener(listener);
        heightProperty().addListener(listener);
    }

    public void addKeyListener(Pane root) {
        
        root.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int value = 0;
                System.out.println("key event" + event);
                switch (event.getCode()) {
/*                    case UP:
                        if (event.isShiftDown()) {
//                            StackPane dlg = new StackPane();
//                            Rectangle rectangle = new Rectangle(300, 100, BACKGROUND_COLOR);                            
//                            
////                            rectangle.setEffect(new Glow(10));
//                            Text message = new Text("Signal Amplitude: 100 uV");
//                            dlg.getChildren().addAll(rectangle, message);
//                            signalPane.getChildren().add(dlg);
//                            dlg.setAlignment(Pos.CENTER);
////                            dlg.setPrefSize(300, windowHeight);
//                            dlg.setEffect(new InnerShadow(5, Color.GRAY));
                            // increment
                            value = -1;
                            amplitudeIndex += value;
                            if (amplitudeIndex < 0) {
                                amplitudeIndex = 0;
                            }
                            if (amplitudeIndex >= amplitude.length) {
                                amplitudeIndex = amplitude.length - 1;
                            }
                        }else if (event.isAltDown()){
                            // increase slowdown factor
                            slowdownFactor *= 10;
                            if (slowdownFactor > 1000)
                                slowdownFactor = 1000;
                        }
                        break;
                    case DOWN:
                        if (event.isShiftDown()) {
                            // decrement
                            value = 1;
                            amplitudeIndex += value;
                            if (amplitudeIndex < 0) {
                                amplitudeIndex = 0;
                            }
                            if (amplitudeIndex >= amplitude.length) {
                                amplitudeIndex = amplitude.length - 1;
                            }
                        }else if (event.isAltDown()){
                            // decrease slowdown factor
                            slowdownFactor /= 10;
                            if (slowdownFactor < 1)
                                slowdownFactor = 1;
                        }
                        break;
                    case LEFT:
                        if (event.isControlDown()) {
                            dataStartPosition -= samplesPerWindow;
                        } else if (event.isShiftDown()) {
                            value = 1;
                            secondsPerPage += value;
                            if (secondsPerPage > 60) {
                                secondsPerPage = 60;
                            }
                        } else if (event.isAltDown()) {
                            markerPosition--;
                            if (markerPosition < 0) {
                                markerPosition = 1;
                            }
                        }else {
                            dataStartPosition -= samplesPerWindow / secondsPerPage;
                        }
                        if (dataStartPosition < 0) {
                            dataStartPosition = 0;
                        }
                        break;
                    case RIGHT:
                        if (event.isControlDown()) {
                            dataStartPosition += samplesPerWindow;
                        } else if (event.isShiftDown()) {
                            value = -1;
                            secondsPerPage += value;
                            if (secondsPerPage < 1) {
                                secondsPerPage = 1;
                            }
                        } else if (event.isAltDown()) {
                            markerPosition++;
                            //TODO marker should not move beyond the sample count!
//                            if (markerPosition > samplesPerWindow) {
//                                markerPosition = 1;
//                            }
                        }else {
                            dataStartPosition += samplesPerWindow / secondsPerPage;
                        }
                        break;
                    case I:
                        AnchorPane root4 = new AnchorPane();
                        Scene icaScene = new Scene(root4, 900, 600, true, SceneAntialiasing.BALANCED);
                        EEGSignalChart icaPane = new EEGSignalChart(root4, test, EEGSignalChart.DrawMode.ICA);
                        icaPane.setDataSource(test);
//        signalPane4.setLabels(labels);
                        BorderPane content4 = new BorderPane();
                        content4.setCenter(icaPane);
                        root4.getChildren().addAll(content4);
                        
                        Stage primaryStage = new Stage();
                        primaryStage.setTitle("ICA View");
                        primaryStage.setScene(icaScene);
                        primaryStage.setX(0);
                        primaryStage.setY(0);
                        primaryStage.setWidth(1100);
                        primaryStage.setHeight(600);
                        primaryStage.setMaximized(false);
                        primaryStage.setFullScreen(false);
                        primaryStage.show();
                        break;
                    case H: // show help screen
                        Alert dialog = new Alert(AlertType.INFORMATION);
                        dialog.setTitle("Program command help");
                        dialog.setHeaderText(null);
                        dialog.setGraphic(null);
                        dialog.setContentText(
                                "H -- this Help window\n" +
                                "-------------------------------------------------\n" +         
                                "UP -- \n" +
                                "DOWN -- \n" +
                                "LEFT -- scroll samples by 1 second\n" +
                                "RIGHT -- scroll samples by 1 second\n" +
                                "<SHIFT> + UP -- increase signal amplitude\n" +
                                "<SHIFT> + DOWN -- decrease signal amplitude\n" +
                                "<SHIFT> + LEFT -- increase seconds per page\n" +
                                "<SHIFT> + RIGHT -- decrease seconds per page\n" +
                                "<CTRL> + LEFT -- jump to previous page\n" +
                                "<CTRL> + RIGHT -- jumpt to next page\n" +
                                "P -- start Playback from marker position\n" +
                                "S -- Stop playback\n" +
                                "SPACE -- start/stop playback\n" +
                                "<ALT> + UP -- slow down playback \n" +
                                "<ALT> + DOWN -- speed up playback \n" +
                                "<ALT> + LEFT -- step marker backwards by 1 sample\n" +
                                "<ALT> + RIGHT -- step marker forward by 1 sample\n" +
                                "-------------------------------------------------\n" +         
                                //"C -- show Control dialog\n" +
                                "O -- show file-Open dialog\n" +
                                "L -- toggle Live data display\n" +         
                                "B -- Butterfly plot\n" +         
                                "N -- Normal signal plot\n" +                                        
                                "C -- Jet LUT-coloured signal plot\n" +         
                                "-------------------------------------------------\n" +         
                                "TAB -- move focus to next pane"
                        );
                        dialog.setResizable(true);
                        
//                        dialog.setOnAction(new EventHandler<DialogEvent>() {
//                            @Override
//                            public void handle(DialogEvent event) {
//                                dialog.close();
//                            }
//                        });
                        dialog.showAndWait();
                        break;
                    case B: // display butterfly plot
                        drawMode = DrawMode.LINE_BUTTERFLY_PLOT;
                        draw();
                        break;                        
                    case N: // display normal signal plot
                        drawMode = DrawMode.LINE_PLOT;
                        draw();
                        break;                        
                    case C: // display colour band signal plot
                        drawMode = DrawMode.COLOUR_LANE_PLOT;
                        draw();
                        break;                        
                    case P: // play marker animation
                        drawTimer.start();
//                        Executors.newFixedThreadPool(2).submit(markerPlayTask);      
                        if (! isPlaying){
                            markerPlayTask = new MarkerThread();
                            markerPlayTask.setDaemon(true);
                            markerPlayTask.start();
                            isPlaying = true;
                        }
                        break;
                    case S: // stop marker animation
                        if (isPlaying){
                            markerPlayTask.interrupt();
                            drawTimer.stop();
                            isPlaying = false;
                        }
                        break;
                    case SPACE:
                        if (isPlaying){
                            markerPlayTask.interrupt();
                            drawTimer.stop();
                            isPlaying = false;
                        }else{
                            drawTimer.start();
//                            Executors.newFixedThreadPool(2).submit(markerPlayTask);                            
                            markerPlayTask = new MarkerThread();
                            markerPlayTask.setDaemon(true);
                            markerPlayTask.start();
                            isPlaying = true;
                        }
//                    case C:     // show control panel to set amplitude, etc
//                        break;
                    case O:     // open file dialog for EEG file
                        break;
*/                  case S:     // take snapshot
                        takeSnapshot();
                        break;
                    case TAB:   // switch focus
                        break;
                }
//                if (event.isShiftDown()) {
////                    System.err.println("SHIFT down");
//                    if (event.getCode().equals(KeyCode.UP)) {
//                        
//                    } else if (event.getCode().equals(KeyCode.DOWN)) {
//                        
//                    } else if (event.getCode().equals(KeyCode.LEFT)) {
//                        
//                    } else if (event.getCode().equals(KeyCode.RIGHT)) {
//                        
//                    }
//                } else if (event.getCode().equals(KeyCode.LEFT)) {
//                    
//                } else if (event.getCode().equals(KeyCode.RIGHT)) {
//                    
////                        if (pageOffset + samplesPerWindow > last sample)
////                            pageOffset = last sample / samplesPerWindow;                        
//                }
            
                event.consume();
//                System.err.println("draw: "+ secondsPerPage + " - " + amplitude[amplitudeIndex]);
////                secondsPerPage += value;
//                draw();
//                drawSelectionWindow();
//                drawMarker(markerPosition);
//                System.out.println("************");
            }        
        });   
    }
    
    private void takeSnapshot(){
        System.out.println("taking a snapshot...");
    }
    
    public void draw() {
        GraphicsContext gc = signalCanvas.getGraphicsContext2D();
        
        drawChartBackground(gc);        
        drawLabels(gc);
        drawTickmarks(gc);
        
        // draw zero signal reference line
//        gc.setLineWidth(1.0);
//        gc.setStroke(Color.BLACK);
//        gc.strokeLine(0, signalCanvas.getHeight()/2, signalCanvas.getWidth(), signalCanvas.getHeight()/2);
        
        // draw epoch preStimulus marker
        gc.setLineWidth(0.5);
        for (int i = 0; i < 21; i++) {
            double x = padding + i*(signalCanvas.getWidth()-2*padding)/20.0;
            gc.setStroke(Color.LIGHTGRAY);
            if ((i % 5) == 0)
                gc.setStroke(Color.DARKGREY);
            if ((i % 10) == 0)
                gc.setStroke(Color.BLUE);
            gc.strokeLine(x, padding, x, signalCanvas.getHeight()-padding);
        }        
        
        // draw signals
        int seriesIdx = 0;
        gc.setLineWidth(1.0);
        gc.setFont(Font.getDefault());
        gc.setTextAlign(TextAlignment.LEFT);
        for (String name : series.keySet()) {
            DataSeries s = series.get(name);
            Paint oldStroke = gc.getStroke();
            gc.setStroke(s.getColor());
            gc.setFill(s.getColor());
            gc.setLineWidth(s.getLineWidth());
            // draw legend
            gc.fillText(name, 70, 50 + seriesIdx * 20);
            gc.setStroke(oldStroke);
            drawChannel(name, s);
            seriesIdx++;            
        }

        // draw epoch stimulus marker
        gc.setStroke(Color.RED);
        double offset = 2048 * 0.001 * preStimulus * ((signalCanvas.getWidth()-2*padding)/ samplesPerWindow);
        gc.strokeLine(padding + offset, padding, padding + offset, signalCanvas.getHeight()-padding);
    }

    private void drawChannel(String name, DataSeries s) {
        if ((s == null) || (s.getData() == null)) {
            return;
        }
        // now draw
        GraphicsContext gc = signalCanvas.getGraphicsContext2D();
        
        Polyline signal = new Polyline();
        double[] data = s.getData();
        long noSamples = data.length;

        int numOfChannels = 1;
//        samplesPerWindow = (int) (s.getData().length);
        signalPitch = signalCanvas.getHeight()-2*padding;

        double signalScale = (double) (0.5 * (signalCanvas.getHeight()-2*padding) );
        double pixelIncrement = (signalCanvas.getWidth()-2*padding) / data.length;

        double[] xValues = new double[data.length];
        double[] yValues = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            xValues[i] = padding + (double) (i * pixelIncrement);
            yValues[i] = padding -(double) data[i] * signalScale / magnitude + (0.5) * signalPitch;
        }
        Paint oldStroke = gc.getStroke();
        gc.setStroke(s.getColor());
        gc.setLineWidth(s.getLineWidth());
        // draw signal
        gc.strokePolyline(xValues, yValues, data.length);
        gc.setStroke(oldStroke);
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
        draw();
    }

    public void setData(float[] values, double samplingFreq) {        
        series.put("Series 1", new DataSeries(values, samplingFreq, Color.NAVY));
        draw();
    }
    
    public void addSeries(String name, double[] data, double samplingFreq, Color color){
        
        series.put(name, new DataSeries(data, samplingFreq, color));
        draw();
    }

    public void addSeries(String name, float[] data, double samplingFreq, Color color){
        System.out.println("Series data length: " + data.length);
        series.put(name, new DataSeries(data, samplingFreq, color));
        draw();
    }

    public void removeSeries(String name) {
        series.remove(name);
        draw();
    }

    public void clear() {
        series.clear();
    }

    private void drawChartBackground(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, signalCanvas.getWidth(), signalCanvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.strokeRect(padding, padding, signalCanvas.getWidth()-2*padding, 
                signalCanvas.getHeight()-2*padding);
    }

    private void drawLabels(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setFont(Font.getDefault());
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.BOTTOM);
        
        int divisions = 2 * samplesPerWindow/2048 ;
        for (int i = 0; i <= divisions; i++) {
            double x = padding + i*(signalCanvas.getWidth()-2*padding)/divisions;            
            gc.fillText(String.valueOf(i*500-preStimulus), x, signalCanvas.getHeight());
        }
//        if (signalCanvas.getHeight() == 0)
//            return;
//        
//        Text label = new Text(0, signalCanvas.getHeight(), "0");
//        label.setTextAlignment(TextAlignment.CENTER);
//        label.setTextOrigin(VPos.TOP);        
//        getChildren().add(label);
    }

    private void drawTickmarks(GraphicsContext gc) {
        // x axis
        int divisions = 2 * samplesPerWindow/2048 ;
        for (int i = 0; i <= divisions; i++) {
            double x = padding + i*(signalCanvas.getWidth()-2*padding)/divisions;
            double y = signalCanvas.getHeight()-padding;
            gc.strokeLine(x, y, x, y-6);            
        }
        // y axis
        divisions = 4;
        for (int i = 0; i < divisions; i++) {
            double x = padding;
            double y = padding + i*(signalCanvas.getHeight()-2*padding)/divisions;
            gc.strokeLine(x, y, x+6, y);            
        }
    }

    public void addSeries(String name, float[] data, int samplingFreq, Color color, double lineWidth) {
        series.put(name, new DataSeries(data, samplingFreq, color, lineWidth));
    }

    public void setPreStimulusLength(int preStimulus) {
        this.preStimulus = preStimulus;
    }

    public void setInterval(int samplesCount) {
        samplesPerWindow = samplesCount;
    }
}
