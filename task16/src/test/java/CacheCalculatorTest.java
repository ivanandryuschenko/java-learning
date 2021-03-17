import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CacheCalculatorTest {
    @Test(timeout = 2000)
    public void calcFibonachi() {
        Calculator calculator = new CacheProxy().newInstance(new CalculatorImpl());
        List<Integer> list1 = Arrays.asList(0, 1, 1, 2, 3, 5, 8, 13, 21, 34);

        // Первый вызов, если кеш не заполнен, продлится 1000 мс
        Assert.assertEquals(list1, calculator.fibonachi(10));
        // Для второго вызова кеш должен быть уже создан и общее время теста должно быть меньше 2000 мс
        Assert.assertEquals(list1, calculator.fibonachi(10));
    }
}