import java.util.concurrent.Callable;

public class Task <T> {
    private final Callable<? extends T> callable;
    private volatile T results;
    private volatile TaskException exception;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() throws TaskException {
        if (results == null && exception == null) {
            synchronized (this) {
                if (results == null && exception == null) {
                    try {
                        results = callable.call();
                    } catch (Exception e) {
                        exception = new TaskException(e);
                    }
                }
            }
        }

        if (exception != null) {
            throw exception;
        }

        return results;
    }
}
