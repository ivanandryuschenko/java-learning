public class CalculatorImpl implements Calculator {
    @Override
    public int calc(int number) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (number <= 1) {
            return 1;
        } else {
            return number * calc(number - 1);
        }
    }
}
