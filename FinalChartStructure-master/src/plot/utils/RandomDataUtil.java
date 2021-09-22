/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot.utils;

import java.util.Random;

/**
 *
 * @author tobak11
 */
public class RandomDataUtil {
    
    public float[] getRandomData(int numberOfData, int periodLength, float minValue, float maxValue){
        Random rnd = new Random();
        float[] data = new float[numberOfData];
        
        data[0] = rnd.nextFloat()*(maxValue-minValue)+minValue;
                
        int periodCounter = 1;
        boolean positivePeriod = true;
        
        for(int i=1;i<numberOfData;i++){
            if(positivePeriod){
                data[i]=data[i-1]+rnd.nextFloat()*(maxValue-data[i-1])/periodLength;
                periodCounter++;
            }else{
                data[i]=data[i-1]-rnd.nextFloat()*(data[i-1]-minValue)/periodLength;
                periodCounter++;
            }
            
            if(periodCounter==periodLength){
                positivePeriod=!positivePeriod;
                periodCounter=0;
            }
        }
        
        return data;
    }
}
