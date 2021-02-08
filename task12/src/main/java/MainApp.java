public class MainApp {
    public static void main(String[] args) {
        //part1();
        part2();
    }

    public static void part1() {
        Task<String> task = new Task<>(() -> {
            Thread.sleep(5000);
            return "OK";
        });

        for (int i = 0; i < 3; i++) {
            int fI = i;
            new Thread(() -> {
                System.out.println("Thread " + fI + " get task result");
                System.out.println("Thread " + fI + ": " + task.get());
            }).start();
        }
    }

    public static void part2() {
        ExecutionManager manager = new ExecutionManagerImpl();
        Runnable[] tasks = new Runnable[10];
        for (int i = 0; i < tasks.length; i++) {
            final int fI = i + 1;
            tasks[i] = () -> {
                System.out.println("Task " + fI + " start");
                if (fI == 5) {
                    throw new TaskException();
                }
                try {
                    Thread.sleep(500 * fI);
                } catch (InterruptedException e) {
                }
                System.out.println("Task " + fI + " finish");
            };
        }

        Context context = manager.execute(() -> System.out.println("All Task finish"), tasks);

        while (!context.isFinished()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //context.interrupt();
        }

        System.out.println("Успешно выполенных задач: " + context.getCompletedTaskCount());
        System.out.println("Аварийно завершенных задач: " + context.getFailedTaskCount());
        System.out.println("Отмененных задач: " + context.getInterruptedTaskCount());
    }
}
