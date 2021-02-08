import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutionManagerImpl implements ExecutionManager {

    private class ExecutionContext implements Context {
        private List<Future<String>> futureList;

        public ExecutionContext(List<Future<String>> futureList) {
            this.futureList = futureList;
        }

        @Override
        public int getCompletedTaskCount() {
            return (int)futureList.stream().filter(Future::isDone).filter(f -> {
                try {
                    f.get();
                    return true;
                } catch (Exception e) {}
                return false;
            }).count();
        }

        @Override
        public int getFailedTaskCount() {
            return (int)futureList.stream().filter(Future::isDone).filter(f -> {
                try {
                    f.get();
                } catch (ExecutionException e) {
                    return true;
                } catch (Exception e) {}
                return false;
            }).count();
        }

        @Override
        public int getInterruptedTaskCount() {
            return (int)futureList.stream().filter(Future::isCancelled).count();
        }

        @Override
        public void interrupt() {
            futureList.stream().filter(f -> !f.isDone()).forEach(f -> f.cancel(false));
        }

        @Override
        public boolean isFinished() {
            return futureList.stream().filter(Future::isDone).count() == futureList.size();
        }
    }

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        List<Future<String>> futureList = new ArrayList<>();
        for (Runnable runnable : tasks) {
            futureList.add(service.submit(runnable, "Ok"));
        }
        Context context = new ExecutionContext(futureList);

        new Thread(() -> {
            while (true) {
                if (context.isFinished()) {
                    callback.run();
                    service.shutdown();
                    break;
                }
            }
        }).start();

        return context;
    }
}
