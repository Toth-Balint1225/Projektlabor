/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plotproject;

/**
 *
 * @author juhasz
 */
public class SignalUtil {
    
    public static float[] createSineSignal(double frequency, double samplingFreq, int sampleCount) {
        float[] signal = new float[sampleCount];
        for (int i = 0; i < signal.length; i++) {
            signal[i] = (float) Math.sin(2 * Math.PI * frequency * i / samplingFreq);
        }
        return signal;
    }    

    public static float[] generateSine(double amplitude, double frequency, double samplingFreq, double timeWindow){
        float[] signal = new float[(int)(timeWindow*samplingFreq)];
            // add cosine wave
        //double freq = 2; // Hz

        for (int i = 0; i < timeWindow*samplingFreq; i++) {
            double time = (double)i/(double)samplingFreq;
            double noise = 0.3 * Math.random();
            signal[i] = (float) (amplitude * Math.sin(2*Math.PI*frequency*time) /*+ noise*/);        
        }    
        return signal;
    }   
    
    public static float[] generateSine(double amplitude, double frequency, double phase, double samplingFreq, double timeWindow){
        float[] signal = new float[(int)(timeWindow*samplingFreq)];
            // add cosine wave
        //double freq = 2; // Hz

        for (int i = 0; i < timeWindow*samplingFreq; i++) {
            double time = (double)i/(double)samplingFreq;
            double noise = 0.3 * Math.random();
            signal[i] = (float) (amplitude * Math.sin(2*Math.PI*frequency*(time+phase)) /*+ noise*/);        
        }    
        return signal;
    }
    
    public static float[] generateCosine(double amplitude, double frequency, double samplingFreq, double timeWindow){
        float[] signal = new float[(int)(timeWindow*samplingFreq)];
            // add cosine wave
        //double freq = 2; // Hz

        for (int i = 0; i < timeWindow*samplingFreq; i++) {
            double time = (double)i/(double)samplingFreq;
            double noise = 0.3 * Math.random();
            signal[i] = (float) (amplitude * Math.cos(2*Math.PI*frequency*time) /*+ noise*/);        
        }    
        return signal;
    }

    public static float[] generateGaussian(double magnitude, double mean, double stdev, double samplingFreq, int seconds){
//        % make a Gaussian
        float[] signal = new float[(int)(seconds * samplingFreq)];
        for (int i = 0; i < signal.length; i++) {
            double time = i/samplingFreq;
            signal[i] = (float) (magnitude * Math.exp(-(time-mean) * (time-mean) / (2*stdev*stdev) ));        
        }
        return signal;
    }
    
    public static float[] generateGaussian(double frequency, double samplingFreq, double timeWindow, double offset){
//        % make a Gaussian
//        s=4/(2*pi*f);
//        gaussian_win = exp(-time.^2./(2*s^2));
//        double freq = 2; // Hz
        float[] signal = new float[(int)(timeWindow*samplingFreq)];
        for (int i = 0; i < timeWindow * samplingFreq; i++) {
            double time = i/samplingFreq;
            double s = 6.0 / (2*Math.PI*frequency);
            signal[i] = (float) Math.exp(-(time-offset) * (time-offset) / (2*s*s) );        
        }
        return signal;
    }
    
    public static double[][] generate2DGaussian(double frequency, double samplingFreq, double timeWindow, double offset){
        double[][] signal = new double[(int)(timeWindow*samplingFreq)][(int)(timeWindow*samplingFreq)];
        double sx = 9.0 / (2*Math.PI*frequency);
        double sy = 9.0 / (2*Math.PI*frequency);
        for (int i = 0; i < timeWindow * samplingFreq; i++) {
            double y = i/samplingFreq;
            for (int j = 0; j < timeWindow * samplingFreq; j++) {
                double x = j/samplingFreq;
                signal[i][j] = Math.exp(-((x-offset) * (x-offset) / (2*sx*sx) + (y-offset) * (y-offset) / (2*sy*sy)) );
            }
        }
        return signal;
    }
    
    
    public static float[] generateMorletWavelet(double frequency, double samplingFreq, double timeWindow, double offset){
        float[] cosine = generateCosine(1.0, frequency, samplingFreq, timeWindow);
        float[] gaussian = generateGaussian(frequency, samplingFreq, timeWindow, offset);
        float[] signal = new float[gaussian.length];
        // make wavelet
        for (int i = 0; i < timeWindow * samplingFreq; i++) {
//            double s = 3 / (2*Math.PI*freq);
            signal[i] = cosine[i] * gaussian[i];
        }
        return signal;
    }

    public static float[] generateLinearChirp(double startFrequency, double endFrequency, double initPhase, double samplingFreq, double timeWindow){
        float[] signal = new float[(int)(timeWindow*samplingFreq)];
        double k = (endFrequency - startFrequency)/timeWindow;

        for (int i = 0; i < timeWindow*samplingFreq; i++) {
            double time = (double)i/(double)samplingFreq;
            double noise = 0.3 * Math.random();
            signal[i] = (float) Math.sin(initPhase + 2*Math.PI*(startFrequency*time + 0.5*k*time*time));        
        }    
        return signal;
    }
    
    public static double[] generateExponentialChirp(double startFrequency, double endFrequency, double initPhase, double samplingFreq, double timeWindow){
        double[] signal = new double[(int)(timeWindow*samplingFreq)];
        double k = Math.pow((endFrequency/startFrequency), 1.0/timeWindow); // (f1/f0)^1/T

        for (int i = 0; i < timeWindow*samplingFreq; i++) {
            double time = (double)i/(double)samplingFreq;
            double noise = 0.3 * Math.random();
            // f(x)=sin(theta + 2*PI*f0*(k^t - 1)/ln(k))
            signal[i] = Math.sin(initPhase + 2*Math.PI*((Math.pow(k, time)-1.0)/Math.log1p(k)));        
        }    
        return signal;
    }    
    
}
