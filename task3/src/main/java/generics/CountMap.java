package generics;
import java.util.List;
import java.util.Map;

public interface CountMap<E> {
    // Добавляет элемент в этот контейнер.
    void add(E e);

    // Возвращает количество добавлений данного элемента
    int getCount(E e);

    // Удаляет элемент из контейнера и возвращает количество его добавлений(до удаления)
    int remove(E e);

    // Количество разных элементов
    int size();

    // Добавить все элементы из source в текущий контейнер,
    // при совпадении ключей, суммировать значения
    void addAll(CountMap<E> source);

    // Вернуть java.util.Map. ключ - добавленный элемент,
    // значение - количество его добавлений
    Map<E, Integer> toMap();

    //Тот же самый контракт как и toMap(), только всю информацию записать в destination
    void toMap(Map<E, Integer> destination);
}
