package generics;

import java.util.HashMap;
import java.util.Map;

public class CountMapImpl<E> implements CountMap<E> {
    Map<E, Integer> map;

    CountMapImpl() {
        map = new HashMap<>();
    }

    public void add(E e) {
        map.put(e, map.getOrDefault(e, 0) + 1);
    }

    public int getCount(E e) {
        return map.getOrDefault(e, 0);
    }

    public int remove(E e) {
        Integer c;
        return ((c = map.remove(e)) == null) ? 0 : c;
    }

    public int size() {
        return map.size();
    }

    public void addAll(CountMap<E> source) {
        for (E key : source.toMap().keySet()) {
            map.put(key, map.getOrDefault(key, 0) + source.getCount(key));
        }
    }

    public Map<E, Integer> toMap() {
        return map;
    }

    public void toMap(Map<E, Integer> destination) {
        destination.putAll(map);
    }
}
