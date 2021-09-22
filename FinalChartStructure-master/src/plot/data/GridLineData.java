/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot.data;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tobak11
 */
public class GridLineData {
    public static enum Direction {HORIZONTAL, VERTICAL};

    public static enum Type {SOLID, DASH, DOT_LINE};

    public  double strokeWidth;

    public Color color;

    public Direction direction;

    public Type type;
    
    public GridLineData(Direction direction){
        this.direction = direction;
    }



//    public GridLineData(Direction direction, double strokeWidth){
//        this.direction = direction;
//        this.strokeWidth = strokeWidth;
//    }
//
//    public GridLineData(Direction direction, Color color){
//        this.direction = direction;
//        this.color = color;
//    }

    public GridLineData(Direction direction, double strokeWidth, Color color, Type type){
        this.direction = direction;
        this.strokeWidth = strokeWidth;
        this.color = color;
        this.type = type;
    }
}
