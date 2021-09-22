/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot.data;

/**
 *
 * @author tobak11
 */
public class LegendData {
    private LineData lineData;
    
    private TitleData titleData;
    
    public LegendData(LineData lineData, TitleData titleData, String text) {
        this.lineData = lineData;
        this.titleData = titleData;
    }

    public LineData getLineData() {
        return lineData;
    }

    public TitleData getTitleData() {
        return titleData;
    }
}
