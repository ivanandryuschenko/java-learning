package p1.cars;
import java.util.*;

public class MainApp {
    private static class Car {
        private String model;
        private String type;
        public Car(String model, String type) {
            this.model = model;
            this.type = type;
        }
        @Override
        public String toString() {
            return model;
        }
    }

    // Разбиение списка машин Car на списки сгруппированные по type
    public static void main(String[] args) {
        List<Car> cars = new ArrayList<Car>(Arrays.asList(
                new Car("Лада", "Седан"),
                new Car("Лада", "Хэтчбек"),
                new Car("Мерседес", "Седан"),
                new Car("БМВ", "Кроссовер"),
                new Car("Форд", "Хэтчбек"),
                new Car("Пежо", "Кроссовер"),
                new Car("Тойота", "Седан")
        ));

        Map<String, List<Car>> map = new HashMap<>();
        for (Car c : cars) {
            if (map.containsKey(c.type) == false) {
                map.put(c.type, new ArrayList<>());
            }
            map.get(c.type).add(c);
        }

        map.entrySet().stream().forEach(System.out::println);
    }
}
