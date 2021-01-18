import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class PerfomanceInvocationHandler implements InvocationHandler {
    private final Object delegate;

    public PerfomanceInvocationHandler(Object delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.isAnnotationPresent(Metric.class)) {
            return method.invoke(delegate, args);
        }
        long beginTime = new Date().getTime();
        Object result = method.invoke(delegate, args);
        long endTime = new Date().getTime();
        System.out.println("Метод " + method.getName() + " отработал за " + (endTime - beginTime) + " мс");
        return result;
    }
}
