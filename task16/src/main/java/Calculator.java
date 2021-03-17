import java.util.List;

public interface Calculator {
    @Cachable(SQLiteDB.class)
    List<Integer> fibonachi(int n);
}
