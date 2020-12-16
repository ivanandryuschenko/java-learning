package p2.strings;

import java.util.Iterator;
import java.util.List;

public class ReversIterator<E> implements Iterator<E> {
    private List<E> list;
    int index;

    public ReversIterator(List<E> list) {
        this.list = list;
        index = list.size();
    }

    @Override
    public boolean hasNext() {
        return index > 0;
    }

    @Override
    public E next() {
        return list.get(--index);
    }
}
