package plot.view;

import javafx.scene.Group;
import plot.data.GridLineData;

import java.util.ArrayList;
import java.util.List;

public class Grid extends Group  {
    public List<GridLine> gridLines;


    public Grid(Axis x, Axis y, GridLineData gridLineData) {
        gridLines = new ArrayList<>();

        if(gridLineData.direction == GridLineData.Direction.VERTICAL) {
            for (int i = 0; i < x.minorTickLines.size(); i++) {
                GridLine gridLine = new GridLine(y, x.minorTickLines.get(i), gridLineData);
                gridLines.add(gridLine);
            }
        }
        if(gridLineData.direction == GridLineData.Direction.HORIZONTAL) {
            for (int i = 0; i < y.minorTickLines.size(); i++) {
                GridLine gridLine = new GridLine(x, y.minorTickLines.get(i), gridLineData);
                gridLines.add(gridLine);
            }
        }
    }

    public List<GridLine> getGridLines() {
        return gridLines;
    }
}
