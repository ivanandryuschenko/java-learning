import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Streams<T> {
    private List<T> list;

    public Streams(Collection<? extends T> collection) {
        list = new ArrayList<>(collection);
    }

    public static <T> Streams<T> of(Collection<? extends T> collection) {
        return new Streams<T>(collection);
    }

    public Streams<T> filter(Predicate<? super T> predicate) {
        list.removeIf(t -> !predicate.test(t));
        return this;
    }

     public <R> Streams<R> transform(Function<? super T, ? extends R> function) {
        List<R> rList = new ArrayList<>();
        for (T t : list) {
            rList.add(function.apply(t));
        }
        return of(rList);
    }

    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper,
                                  Function<? super T, ? extends V> valueMapper) {
        Map<K, V> map = new HashMap<>();
        for (T t : list) {
            map.put(keyMapper.apply(t), valueMapper.apply(t));
        }
        return map;
    }
}
