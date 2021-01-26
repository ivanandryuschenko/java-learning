import java.util.List;

public interface Service {
    @Cache(cacheType = CacheType.IN_MEMORY)
    double doHardWork1(String item, @Ignore int val1, int val2);

    @Cache(cacheType = CacheType.FILE, fileNamePrefix = "data", listLimit = 10)
    List<String> doHardWork2(String item);

    @Cache(cacheType = CacheType.FILE, zip = true, listLimit = 10)
    List<Double> doHardWork3(int id);
}
