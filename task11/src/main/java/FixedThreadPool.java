import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FixedThreadPool implements ThreadPool {
    private List<JobExecutor> jobExecutors = new ArrayList<>();
    private Queue<Runnable> jobQueue = new ConcurrentLinkedQueue<>();

    private class JobExecutor extends Thread {
        @Override
        public void run() {
            while (true) {
                Runnable job = jobQueue.poll();
                if (job != null) {
                    // Выполнение очередной задачи
                    job.run();
                } else {
                    // Ожидание поступления новой задачи
                    synchronized (jobExecutors) {
                        try {
                            jobExecutors.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        }
    }

    public FixedThreadPool(int poolSize) {
        for (int i = 0; i < poolSize; i++) {
            jobExecutors.add(new JobExecutor());
        }
    }

    public void start() {
        for (JobExecutor e : jobExecutors) {
            e.start();
        }
    }

    public void execute(Runnable runnable) {
        jobQueue.offer(runnable);
        synchronized (jobExecutors) {
            jobExecutors.notifyAll();
        }
    }
}
