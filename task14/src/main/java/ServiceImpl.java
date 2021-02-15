import java.util.ArrayList;
import java.util.List;

public class ServiceImpl implements Service {
    @Override
    public double doHardWork1(String item, int val1, int val2) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return val1 * val2;
    }

    @Override
    public List<String> doHardWork2(String item) {
        List<String> list = new ArrayList<>(10000);
        for (int i = 0; i < 10000; i++) {
            list.add(item + i);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Double> doHardWork3(int id) {
        List<Double> list = new ArrayList<>(10000);
        for (int i = 0; i < 10000; i++) {
            list.add(id * Math.sqrt(i));
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }
}
