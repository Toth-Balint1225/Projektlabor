/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot.data;

import javafx.scene.paint.Color;

/**
 *
 * @author tobak11
 */
public class AxisData {
    private TitleData title;
    private LabelData label;
    
    private double majorStep;
    private double minorStep;
    
    private LineData lineData;

    private LogType logType = LogType.TEN;

    public static enum LogType {
        TWO, TEN, SIXTEEN
    };

    public AxisData(String title) {
        this.title = new TitleData(title);
        this.lineData = new LineData(Color.BLACK, 1, LineData.Type.SOLID);
    }  
        
        
    public AxisData(TitleData title, LabelData label, double majorStep, double minorStep, LineData line) {
        this.title = title;
        this.label = label;
        this.majorStep = majorStep;
        this.minorStep = minorStep;
        this.lineData = line;
    }

    public AxisData(LogType logType, TitleData title, LabelData label, double majorStep, double minorStep, LineData line) {
        this.logType = logType;
        this.title = title;
        this.label = label;
        this.majorStep = majorStep;
        this.minorStep = minorStep;
        this.lineData = line;
    }
    
    public AxisData(LineData line) {
        this.lineData = line;
    }    

    public TitleData getTitle() {
        return title;
    }

    public void setTitle(TitleData title) {
        this.title = title;
    }

    public LabelData getLabel() {
        return label;
    }

    public void setLabel(LabelData label) {
        this.label = label;
    }

    public double getMajorStep() {
        return majorStep;
    }

    public void setMajorStep(double majorStep) {
        this.majorStep = majorStep;
    }

    public double getMinorStep() {
        return minorStep;
    }

    public void setMinorStep(double minorStep) {
        this.minorStep = minorStep;
    }

    public LineData getLineData() {
        return lineData;
    }

    public void setLine(LineData line) {
        this.lineData = line;
    }

    public LogType getLogType() {
        return logType;
    }
}
