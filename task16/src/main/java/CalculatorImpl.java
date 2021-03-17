import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalculatorImpl implements Calculator {
    public CalculatorImpl() {

    }

    @Override
    public List<Integer> fibonachi(int n) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Stream.iterate(new int[] {0, 1}, a -> new int[] {a[1], a[0] + a[1]})
                .limit(n)
                .map(a -> a[0])
                .collect(Collectors.toList());
    }
}
