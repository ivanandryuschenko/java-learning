package algorithms;

import java.util.Arrays;

public class Sorting {
    // Перестановка двух элементов в массиве
    private static void swap(int[] a, int i1, int i2) {
        if (a[i1] != a[i2]) {
            int tmp = a[i1];
            a[i1] = a[i2];
            a[i2] = tmp;
        }
    }

    // Сортировка пузырьком
    public static void bubbleSort(int[] a) {
        for (int l = a.length; l > 0; l--) {
            boolean swaped = false;

            for(int i = 1; i < l; i++) {
                if (a[i-1] > a[i]) {
                    swap(a, i-1, i);
                    swaped = true;
                }
            }

            if (swaped == false)
                break;
        }
    }

    // Сортировка вставкой
    public static void insertionSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int k = a[i];
            if (a[i - 1] > k) {
                int j = i;
                while (j > 0 && a[j - 1] > k) {
                    a[j] = a[j - 1];
                    j--;
                }
                a[j] = k;
            }
        }
    }

    // Сортировка выбором
    public static void selectionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            swap(a, i, min);
        }
    }

    // Быстрая сортировка
    public static void quickSort(int[] a) {
        quickRec(a, 0, a.length - 1);
    }

    // Рекурсивный метод быстрой сортировки
    private static void quickRec(int[] a, int l, int r) {
        int size = r - l + 1;
        if (size <= 3) {
            quickManual(a, l, r);
        } else {
            int median = quickMedian(a, l, r);
            int part = quickPart(a, l, r, median); // Разбиение массива на 2 группы
            quickRec(a, l, part - 1); // Сортировка левой части
            quickRec(a, part + 1, r); // Сортировка правой части
        }
    }

    // Определение медианы по трем точкам для определения опорного значения
    private static int quickMedian(int[] a, int l, int r) {
        int c = (l + r)  / 2;

        if (a[l] > a[c])
            swap(a, l, c);

        if (a[l] > a[r])
            swap(a, l, r);

        if (a[c] > a[r])
            swap(a, c, r);

        swap(a, c, r-1);

        return a[r-1];
    }

    // Разбиение на две группы: левую (с меньшими значениями) и правую (с большими)
    private static int quickPart(int[] a, int l, int r, int p) {
        int lp = l;
        int rp = r - 1;

        while (true) {
            while (a[++lp] < p)
                ;
            while (a[--rp] > p)
                ;
            if (lp >= rp)
                break;
            else
                swap(a, lp, rp);
        }
        swap(a, lp, r-1);
        return lp;
    }

    // Ручная сортировка для группы не превышающей 3 элемента
    private static void quickManual(int[] a, int l, int r) {
        int size = r - l + 1;
        if (size <= 1)
            return;
        if (size == 2) {
            if (a[l] > a[r])
                swap(a, l, r);
        } else {
            if (a[l] > a[r-1])
                swap(a, l, r-1);
            if (a[l] > a[r])
                swap(a, l, r);
            if (a[r-1] > a[r])
                swap(a, r-1, r);
        }
    }
}
