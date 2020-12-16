package p2.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.*;

public class MainApp {
    // Подсчет количества различных слов в файле
    public static int getUniqueWordsCount(List<String> strList) {
        Set<String> set = new HashSet<>(strList);
        return set.size();
    }

    // Список различных свло в файле, отсортированных по возростанию их длины
    public static void printUniqueSortedWords(List<String> strList) {
        Set<String> set = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                int dl = s.length() - t1.length();
                return (dl != 0) ? dl : s.compareTo(t1);
            }
        });
        set.addAll(strList);
        set.stream().forEach(System.out::println);
    }

    // Подсчет и вывод на экран сколько раз каждое слово встречается в файле
    public static void printUniqueWordsCount(List<String> strList) {
        Map<String, Integer> map = new HashMap<>();
        for (String s : strList) {
            map.put(s, map.getOrDefault(s, 0) + 1);
        }
        map.entrySet().stream().forEach(System.out::println);
    }

    // Вывод на экран всех строк в обратном порядке
    public static void printReversOrder(List<String> strList) {
        Deque<String> deq = new ArrayDeque<>();
        strList.stream().forEach(deq::push);
        deq.stream().forEach(System.out::println);
    }

    // Реализация своего Iterator для обхода списка в обратном порядке
    public static void printReversOrderIter(List<String> strList) {
        ReversIterator<String> it = new ReversIterator<>(strList);
        while(it.hasNext()) {
            System.out.println(it.next());
        }
    }

    // Вывод на экра заданной пользователем строки
    public static void printRequestLine(List<String> strList) {
        List<String> list = new ArrayList<>(strList);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int line = 1;
        while (line > 0) {
            try {
                System.out.println("Введите номер строки или 0 для выхода");
                System.out.print("> ");
                line = Integer.parseInt(reader.readLine());
                if (line > 0) {
                    System.out.println(list.get(line - 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Введенный текст не являеся номером");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("В файле нет строки с таким номером");
            } catch (IOException e) {
                System.out.println("Ошибка ввода");
            }
        }
    }

    public static void main(String[] args) {
        List<String> strList = new ArrayList<>(Arrays.asList("Aaa", "Aa", "Bbb", "Bc", "Bb", "Cc", "CccC", "Aa"));

        System.out.println("Количество различных слов в файле: " + getUniqueWordsCount(strList));

        System.out.println("\nСписок различных слов файла: ");
        printUniqueSortedWords(strList);

        System.out.println("\nСлова встречается в файле: ");
        printUniqueWordsCount(strList);

        System.out.println("Строки файла в обратном порядке: ");
        printReversOrder(strList);

        System.out.println("Строки файла в обратном порядке через свой итератор: ");
        printReversOrderIter(strList);

        System.out.println("Вывод строк заданных пользователем: ");
        printRequestLine(strList);
    }
}
