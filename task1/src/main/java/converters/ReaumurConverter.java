package converters;

public class ReaumurConverter extends Converter implements CelsiusConverted {
    public ReaumurConverter() {
        super("Градус Реомюра", "°Re");
    }
    @Override
    public double fromCelsius(double t) {
        return t * 0.8;
    }
}
