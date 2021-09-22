/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot.view;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import plot.data.DataSeries;
import plot.data.LegendData;
import plot.data.LineData;
import plot.data.TitleData;

/**
 *
 * @author tobak11
 */
public class Legend extends Group {
    private ArrayList<LegendData> legendData;
    
    public Legend(ArrayList<DataSeries> dsList) {
        legendData = new ArrayList<>();
        
        for(int i=0;i<dsList.size();i++){
            LegendData tempLegendData = new LegendData(dsList.get(i).getLineData(), new TitleData(dsList.get(i).getName()), dsList.get(i).getName());
            legendData.add(tempLegendData);
            
            Line tempLine = new Line(0,i*30, 30, i*30);
            tempLine.setStroke(tempLegendData.getLineData().getColor());
            tempLine.setStrokeWidth(tempLegendData.getLineData().getWidth()); 
            if(tempLegendData.getLineData().getLineType().equals(LineData.Type.DASH)){
                tempLine.getStrokeDashArray().addAll(4.0,8.0);
            }
            
            Text tempText = new Text(tempLegendData.getTitleData().getValue());
            tempText.setX(40);
            tempText.setY(i*30);
            
            this.getChildren().addAll(tempLine, tempText);
        }
    } 
}
