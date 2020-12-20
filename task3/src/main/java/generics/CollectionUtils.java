package generics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionUtils {
    public static<E> void addAll(List<? extends E> source, List<? super E> destination) {
        destination.addAll(source);
    }

    public static<E> List<E> newArrayList() {
        return new ArrayList<>();
    }

    public static<E> int indexOf(List<? extends E> source, E e) {
        return source.indexOf(e);
    }

    public static<E> List<E> limit(List<? extends E> source, int size) {
        return List.copyOf(source.subList(0, size));
    }

    public static<E> void add(List<? super E> source, E e) {
        source.add(e);
    }

    public static<E> void removeAll(List<? super E> removeFrom, List<? extends E> c2) {
        removeFrom.removeAll(c2);
    }

    public static<E> boolean containsAll(List<? extends E> c1, List<? extends E> c2) {
        return c1.containsAll(c2);
    }

    public static<E> boolean containsAny(List<? extends E> c1, List<? extends E> c2) {
        for (E e : c2) {
            if (c1.contains(e))
                return true;
        }
        return false;
    }

    public static<E extends Comparable<? super E>> List<E> range(List<? extends E> list, E min, E max) {
        List<E> newList = new ArrayList<>();
        for (E e : list) {
            if (e.compareTo(min) >= 0 && e.compareTo(max) <= 0) {
                newList.add(e);
            }
        }
        Collections.sort(newList);
        return newList;
    }

    public static<E> List<E> range(List<? extends E> list, E min, E max, Comparator<? super E> comparator) {
        List<E> newList = new ArrayList<>();
        for (E e : list) {
            if (comparator.compare(e, min) >= 0 && comparator.compare(e, max) <= 0) {
                newList.add(e);
            }
        }
        Collections.sort(newList, comparator);
        return newList;
    }

}
