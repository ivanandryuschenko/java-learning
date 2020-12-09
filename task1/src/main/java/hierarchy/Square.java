package hierarchy;

import java.util.Formatter;

public class Square extends Rect {
    public Square(double width) {
        super(width, width);
    }

    @Override
    public String toString() {
        return "Квадрат (" + new Formatter().format("%.1f x %.1f", width, height) + ")";
    }
}
