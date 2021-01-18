import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class CacheInvocationHandler implements InvocationHandler {
    private Map<Object, Object> cache = new HashMap<>();
    private final Object delegate;

    public CacheInvocationHandler(Object delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.isAnnotationPresent(Cache.class)) {
            return method.invoke(delegate, args);
        }

        if (!cache.containsKey(key(method, args))) {
            cache.put(key(method, args), method.invoke(delegate, args));
        }
        return cache.get(key(method, args));
    }

    private Object key(Method method, Object[] args) {
        List<Object> key = new ArrayList<>();
        key.add(method);
        key.addAll(Arrays.asList(args));
        return key;
    }
}
