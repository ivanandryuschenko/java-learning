public class MainApp {

    public static void main(String[] args) {
        ConcurrentCacheProxy cacheProxy = new ConcurrentCacheProxy("task14\\cache");
        Service service = cacheProxy.cache(new ServiceImpl());

        Thread[] threads = new Thread[20];
        for (int i = 0; i < threads.length; i++) {
            final int fi = i;
            threads[i] = new Thread(() -> {
                service.doHardWork1("work" + fi % 3 + 1, 10 * (fi % 2 + 1), 10);
            });
        }

        for (Thread t : threads) {
            t.start();
        }
    }
}
