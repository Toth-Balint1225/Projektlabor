import java.util.function;

public class DataSeries {
    private String name;
    private double startValue;
    private double endValue;
    private double frequency;
    private Function<double,double> fun;

    public DataSeries() {
        name = "default";
        startValue = 0.f;
        endValue = 0.f;
        frequency = 0.f;
        fun = null;
    }

    public DataSeries withName(String name) {
        this.name = name;
        return this;
    }



    public static void main(String[] args) {
        System.out.println("Hello");

        DataSeries ds = new DataSeries()
            .name("Jenő");
    }
}