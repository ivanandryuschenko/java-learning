package converters;

public class RankineConverter extends Converter implements CelsiusConverted {
    public RankineConverter() {
        super("Градус Ранкина", "°Ra");
    }
    @Override
    public double fromCelsius(double t) {
        return t * 9 / 5 + 32 + 459.67;
    }
}
