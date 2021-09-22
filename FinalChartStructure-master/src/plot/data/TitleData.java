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
public class TitleData {
    private String value;
    private Font font;
    
    public TitleData(String value, Font font){
        this.value=value;
        this.font=font;
    }
    
    public TitleData(String value){
        this.value=value;
        this.font=Font.getDefault();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
