public interface Calculator {
    @Metric
    @Cache
    int calc (int number);
}
