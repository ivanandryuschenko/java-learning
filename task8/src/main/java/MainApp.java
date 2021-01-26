public class MainApp {

    public static void main(String[] args) {
        CacheProxy cacheProxy = new CacheProxy("task8/cache");
        Service service = cacheProxy.cache(new ServiceImpl());

        System.out.println(service.doHardWork1("work1", 10, 10));
        System.out.println(service.doHardWork1("work2", 10, 10));
        System.out.println(service.doHardWork1("work1", 20, 10));
        System.out.println(service.doHardWork1("work2", 20, 10));

        System.out.println(service.doHardWork2("Test1-"));
        System.out.println(service.doHardWork2("Test2-"));
        System.out.println(service.doHardWork2("Test1-"));
        System.out.println(service.doHardWork2("Test2-"));

        System.out.println(service.doHardWork3(1));
        System.out.println(service.doHardWork3(2));
        System.out.println(service.doHardWork3(1));
        System.out.println(service.doHardWork3(2));
    }
}
