package converters;

public abstract class Converter {
    private String unit;
    private String symbol;

    public Converter(String unis, String symbol) {
        this.unit = unis;
        this.symbol = symbol;
    }

    public String getUnit() {
        return unit;
    }

    public String getSymbol() {
        return symbol;
    }
}
