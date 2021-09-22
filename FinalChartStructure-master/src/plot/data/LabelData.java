/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot.data;

import javafx.scene.text.Font;

/**
 *
 * @author tobak11
 */
public class LabelData {
    private Font font;
    private int decimals;
    
    public LabelData(){
        this.font = Font.getDefault();
        this.decimals = 1;
    }

    public LabelData(Font font, int decimals) {
        this.font = font;
        this.decimals = decimals;
    } 
}
