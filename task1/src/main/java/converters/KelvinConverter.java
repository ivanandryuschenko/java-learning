package converters;

public class KelvinConverter extends Converter implements CelsiusConverted {
    public KelvinConverter() {
        super("Кельвин", "K");
    }

    @Override
    public double fromCelsius(double t) {
        return t + 273.15;
    }
}
