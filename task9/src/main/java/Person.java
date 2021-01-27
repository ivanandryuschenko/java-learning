import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Jon", 24));
        personList.add(new Person("Bob", 23));
        personList.add(new Person("Jim", 35));
        personList.add(new Person("Tom", 45));
        personList.add(new Person("Ron", 25));
        personList.add(new Person("Dan", 63));
        personList.add(new Person("Mark", 53));

        Map map = Streams.of(personList)
                .filter(p -> p.getAge() > 30)
                .transform(p -> new Person(p.getName(), p.getAge() + 10))
                .toMap(p -> p.getName(), p -> p);

        map.forEach((k, v) -> System.out.println(k + ":" + v));
    }
}
