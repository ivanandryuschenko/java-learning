import converters.*;
import hierarchy.*;

import java.util.Arrays;
import java.util.Formatter;

public class MainApp {
    public static void main(String[] args) {
        // Сортировка пузырьком
        System.out.println("Сортировка пузырьком ===========================");
        int[] a = new int[20];
        for(int i = 0; i < a.length; i++)
            a[i] = (int)(Math.random() * 100);
        System.out.println("Массив до сортировки:");
        System.out.println(Arrays.toString(a));
        algorithms.Sorting.bubbleSort(a);
        System.out.println("Массив после сортировки:");
        System.out.println(Arrays.toString(a));

        // Иерархия объектов
        System.out.println("\n\nГеометрические фигуры ===========================");
        Shape[] shapes = new Shape[] {
                new Circle(3.0),
                new Rect(2.0, 4.0),
                new Square(2.0),
                new Triangle(2.0, 3.0, 2.0)
        };
        for (Shape s : shapes) {
            System.out.println(s.toString() + ": Площадь = " +
                    new Formatter().format("%.2f", s.area()) +
                    ", Периметр = " + new Formatter().format("%.2f", s.perimeter()));
        }

        // Конвертеры температуры
        System.out.println("\n\nКонвертеры температуры ==========================");
        Converter[] converters = new Converter[] {
                new CelsiusConverter(),
                new FahrenheitConverter(),
                new KelvinConverter(),
                new RankineConverter(),
                new ReaumurConverter()
        };

        for (Converter c : converters) {
            if (c instanceof CelsiusConverted) {
                System.out.printf("%20s: ", c.getUnit());
                for (int i = 0; i <= 250; i += 50)
                    System.out.printf("%6.2f  ", (((CelsiusConverted) c).fromCelsius(i)));
                System.out.println();
            }
        }
    }
}
