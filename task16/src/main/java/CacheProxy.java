import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CacheProxy {

    public<T> T newInstance(T delegate) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                delegate.getClass().getInterfaces(),
                new CacheInvocationHandler(delegate));
    }

    public class CacheInvocationHandler implements InvocationHandler {
        private final Object delegate;

        public CacheInvocationHandler(Object delegate) {
            this.delegate = delegate;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isAnnotationPresent(Cachable.class)) {
                try {
                    // Получение объекта для взаимодействия с базой данных
                    DataBase db = method.getAnnotation(Cachable.class).value().getConstructor().newInstance();
                    CacheDao dao = new CacheDaoImpl(db);

                    Object key = getKey(method, args);

                    // Попытка загрузить данные из кэша
                    try {
                        return dao.load(key);
                    } catch (SQLException e) {}

                    // Если загрузить из кэша не удалось, вызов метода
                    Object object = method.invoke(delegate, args);

                    // Сохранение вычисленного значения в кэш
                    try {
                        dao.save(key, object);
                    } catch (SQLException e) {}

                    return object;
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {}
            }
            return method.invoke(delegate, args);
        }

        private Object getKey(Method method, Object[] args) {
            List<Object> key = new ArrayList<>();

            // Добавление к ключу имени заданного в аннотации либо имени вызываемого метода
            key.add(method.getName());
            // Добавление к ключу учитываемых аргументов метода
            for (int i = 0; i < args.length; i++) {
                key.add(args[i]);
            }
            return key;
        }
    }
}
