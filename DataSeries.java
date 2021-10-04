import java.util.function.Function;

/*
    néhány gondolat a mintavételezésről és a fogalmak tisztázása
    def: Mintavételezésnek nevzzük a függvénybe behelyettesítést az értelmezési tartomány egy pontján.    (D1)
    def: Frekvenciának nevezzük a mintavételek számát; jele: freq                                         (D2)
    def: Értelmezési tartomány hossza: span, minimum értéke minValue, maximum értéke maxValue             
         span = maxValue - minValue                                                                       (D3)
    def: az i-ik mintavételi pontot a(i)-vel jelöljük.                                                    (D4)
    feltétel: az értelmezési tartomány kezdeti és végpontja mindig mintavételi pont kell hogy legyen.     (F1)
    feltétel: mindig frekvencia számú mintavétel kell, hogy történjen                                     (F2)
    állítás: (F1)-ből következik, hogy a mintavételek száma, tehát a frekvencia legalább 2.               (T1)
    állítás: a(i) = minValue + (i*span) / (freq-1), minden 2<=freq, 0<=i<freq-re                          (T2)
    bizonyítás:
        freq=2   =>  a(i) = minValue + (i*span) / (2-1)
                     a(i) = minValue + i*span
        => a(0) = minValue, mert minValue + (0*span) / (2-1) = minValue + 0
        => a(1) = maxValue, mert minVAlue + (1*span) / (2-1) = minValue + span = maxValue (D3)
        => (F1), (F2) teljesül, alapeset kész
        
        freq=n   =>  a(i) = minValue + (i*span) / (n-1)
        => a(0)   = minValue
        => a(n-1) = minVAlue + ((n-1)*span) / (n-1) = minValue + span = maxValue (D3)
        => (F1), (F2) teljesül, általános eset kész

        freq=n+1 => a(i) = minValue + (i*span) / ((n+1)-1)
                    a(i) = minValue + (i*span) / n
        => a(0) = minValue + (0*span) / n = minValue
        => a(n) = minValue + (n*span) / n = minValue + span = maxValue (D3)
        => (F1), (F2) teljesül 
        Q.E.D.
*/

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
        // foglalja is egyből a tömböt
        this.rawData = new double[frequency];
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
            generateData(span);
        }
        // adatok
        return this;
    }

    private void generateData(double span) {
        // az előző verzió kalap szar, de a célnak megfelelt
        // itt jön a tuti dolog
        double actual = 0.d;
        for (int i=0;i<this.frequency;i++) {
            actual = this.startValue + (i*span) / (this.frequency-1);
            rawData[i] = fun.apply(actual);
            // demo funkcionalitás
            System.out.println(i+"\tx="+actual+"\ty="+rawData[i]);
        }
    }

    public static void main(String[] args) {
        DataSeries ds = new DataSeries()
            .withName("Sine")
            .withStartValue(0.d)
            .withEndValue(0.5d)
            .withFrequency(10)
            .withFunction((x) -> Math.sin(x))
            .makeSeries();
    }
} 