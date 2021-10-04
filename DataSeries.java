import java.util.function.Function;

public class DataSeries {
    private String name;
    private double startValue;
    private double endValue;
    private int frequency;
    private Function<Double,Double> fun;

    private double[] rawData;

    public DataSeries() {
        name = "default";
        startValue = 0.f;
        endValue = 0.f;
        frequency = 0;
        fun = null;
    }

    public DataSeries withName(String name) {
        this.name = name;
        return this;
    }

    public DataSeries withStartValue(double startValue) {
        this.startValue = startValue;
        return this;
    }

    public DataSeries withEndValue(double endValue) {
        this.endValue = endValue;
        return this;
    }

    
    public DataSeries withFrequency(int frequency) {
        this.frequency = frequency;
        return this;
    }

    public DataSeries withFunction(Function<Double,Double> fun) {
        this.fun = fun;
        return this;
    }

    public DataSeries makeSeries() {
        // tömb
        if (!(this.startValue != this.endValue && this.frequency != 0)) {
            return null;
        } else {
            // hány elem kell?
            double span = this.endValue - this.startValue;
            this.rawData = new double[frequency];

            generateData(span);
        }
        // adatok
        return this;
    }

    private void generateData(double span) {
        double actual;
        for (int i=0;i<frequency;i++) {
            actual = span/frequency*i;
            rawData[i] = fun.apply(actual);
            System.out.print(actual);
            System.out.print(" ");
            System.out.println(rawData[i]);
        }
    }

    public static void main(String[] args) {
        DataSeries ds = new DataSeries()
            .withName("Jenő")
            .withStartValue(0.d)
            .withEndValue(3.14d)
            .withFrequency(64)
            .withFunction((x) -> Math.sin(x))
            .makeSeries();
    }
} 