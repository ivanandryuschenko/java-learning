package hierarchy;

import java.util.Formatter;

public class Rect extends Shape {
    protected double width;
    protected double height;

    public Rect(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);
    }

    @Override
    public String toString() {
        return "Прямоугольник (" + new Formatter().format("%.1f x %.1f", width, height) + ")";
    }
}
