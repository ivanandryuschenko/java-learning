import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ScalableThreadPool implements ThreadPool {
    private final int minPoolSize;
    private final int maxPoolSize;

    private List<JobExecutor> jobExecutors = new ArrayList<>();
    private AtomicInteger jobInWork = new AtomicInteger();
    private Queue<Runnable> jobQueue = new ConcurrentLinkedQueue<>();

    private class JobExecutor extends Thread {
        @Override
        public void run() {
            while (true) {
                Runnable job = jobQueue.poll();
                if (job != null) { // Получена задача из очереди
                    jobInWork.incrementAndGet();
                    job.run();
                    jobInWork.decrementAndGet();
                } else { // Если задач нет
                    synchronized (jobExecutors) {
                        if (jobExecutors.size() > minPoolSize) {
                            // Удаление потока
                            System.out.println("Remove JobExecutor " + this);
                            jobExecutors.remove(this);
                            break;
                        } else {
                            // Ожидание поступления новой задачи
                            try {
                                jobExecutors.wait();
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                }
            }
        }
    }

    public ScalableThreadPool(int minPoolSize, int maxPooSize) {
        this.minPoolSize = minPoolSize;
        this.maxPoolSize = maxPooSize;

        for (int i = 0; i < minPoolSize; i++) {
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
            if (jobInWork.get() + jobQueue.size() > jobExecutors.size() // Запущенных потоков недостаточно чтобы обработать все задачи
                    && jobExecutors.size() < maxPoolSize) { // Возможно расширение количества потоков
                JobExecutor j = new JobExecutor();
                System.out.println("Add JobExecutor " + j);
                jobExecutors.add(j);
                j.start();
            }
            jobExecutors.notifyAll();
        }
    }
}
