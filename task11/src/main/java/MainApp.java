public class MainApp {
    public static void main(String[] args) throws InterruptedException {
        //testFixedThreadPool();
        testScalableThreadPool();
    }

    public static void testFixedThreadPool() throws InterruptedException {
        System.out.println("FixedThreadPool");
        ThreadPool threadPool = new FixedThreadPool(3);
        threadPool.start();
        for (int i = 1; i <= 20; i++) {
            final int finalI = i;
            threadPool.execute(new Runnable() {
                public void run() {
                    System.out.println("Start job " + finalI + " in " + Thread.currentThread());
                    try {
                        Thread.sleep(finalI * 250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Finish job " + finalI + " in " + Thread.currentThread());
                }
            });
            Thread.sleep(100);
        }
    }

    public static void testScalableThreadPool() throws InterruptedException {
        System.out.println("ScalableThreadPool");
        ThreadPool threadPool = new ScalableThreadPool(3, 8);
        threadPool.start();
        for (int i = 1; i <= 20; i++) {
            final int finalI = i;
            threadPool.execute(new Runnable() {
                public void run() {
                    System.out.println("Start job " + finalI + " in " + Thread.currentThread());
                    try {
                        Thread.sleep(finalI * 250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Finish job " + finalI + " in " + Thread.currentThread());
                }
            });
            Thread.sleep(100);
        }
    }
}
