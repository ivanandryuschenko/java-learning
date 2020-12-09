package converters;

public class CelsiusConverter extends Converter implements CelsiusConverted {
    public CelsiusConverter() {
        super("Градус Цельсия", "°C");
    }
    @Override
    public double fromCelsius(double t) {
        return t;
    }
}
