import java.io.*;

public class MainApp {
    private static class Calc {
        public static double factorial(double n) {
            if (n <= 0) {
                return 1.0;
            } else {
                return n * factorial(n - 1.0);
            }
        }
    }

    public static void main(String[] args) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("task10/input.txt"))));
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось открыть файл для чтения");
            return;
        }

        while (true) {
            try {
                final int n = Integer.parseInt(reader.readLine());
                new Thread(() -> System.out.println(n + "! = " + Calc.factorial(n))).start();
            } catch (IOException | NumberFormatException e) {
                break;
            }
        }
    }
}
