package converters;

public class FahrenheitConverter extends Converter implements CelsiusConverted {
    public FahrenheitConverter() {
        super("Градус Фаренгейта", "°F");
    }

    @Override
    public double fromCelsius(double t) {
        return t * 9 / 5 + 32;
    }
}
