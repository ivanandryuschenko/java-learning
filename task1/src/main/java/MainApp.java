import java.util.Arrays;

public class MainApp {
    public static void main(String[] args) {
        int[] a = {3, 15, 7, 6, 8, 9, 2, 4, 7, 8};
        algorithms.Sorting.quickSort(a);
        System.out.println(Arrays.toString(a));
    }
}
