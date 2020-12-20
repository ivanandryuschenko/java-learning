package generics;

import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;

public class MainApp {

    public static void main(String[] args) {
        CountMap<Integer> map = new CountMapImpl<>();
        map.add(10);
        map.add(10);
        map.add(5);
        map.add(6);
        map.add(5);
        map.add(10);
        System.out.println(map.getCount(5));
        System.out.println(map.getCount(10));


        List<Integer> l1 = CollectionUtils.newArrayList();
        l1.add(10);
        l1.add(30);
        l1.add(45);
        l1.add(5);
        List<Double> l2 = CollectionUtils.newArrayList();
        l2.add(10.0);
        l2.add(34.2);
        l2.add(1.0);
        List<Number> ln = CollectionUtils.newArrayList();
        CollectionUtils.addAll(l1, ln);
        CollectionUtils.addAll(l2, ln);
        CollectionUtils.add(ln, 3.0);
        System.out.println(ln);
        CollectionUtils.removeAll(ln, l2);
        System.out.println(ln);
        System.out.println(CollectionUtils.containsAll(ln, l1));
        System.out.println(CollectionUtils.containsAll(ln, l2));
        System.out.println(CollectionUtils.containsAny(l1, ln));
        System.out.println(CollectionUtils.limit(l2, 3));
        System.out.println(CollectionUtils.indexOf(ln, 3.0));
        System.out.println(CollectionUtils.range(l1, 1, 40));
        System.out.println(CollectionUtils.range(l1, 1, 40, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        }));

    }
}
