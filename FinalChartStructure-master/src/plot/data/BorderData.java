/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot.data;

import java.util.List;

/**
 *
 * @author tobak11
 */
public class BorderData {
    public static enum EWNS {LEFT, TOP, RIGHT, BOTTOM};
    
    private List<EWNS> ewnsData;
    private LineData line;
    
    public BorderData(List<EWNS> ewnsData, LineData line){
        this.ewnsData = ewnsData;
        this.line = line;
    }
}
