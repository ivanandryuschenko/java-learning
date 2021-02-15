import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ConcurrentCacheProxy {
    private final String rootPath;

    public ConcurrentCacheProxy(String rootPath) {
        this.rootPath = rootPath;
        new File(this.rootPath).mkdirs();
    }

    public<T> T cache(T delegate) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                delegate.getClass().getInterfaces(),
                new CacheInvocationHandler(delegate));
    }

    public class CacheInvocationHandler implements InvocationHandler {
        private final Map<Object, Object> cacheMap = new ConcurrentHashMap<>();
        private final Map<Object, Lock> lockMap = new ConcurrentHashMap<>();
        private final Object delegate;

        public CacheInvocationHandler(Object delegate) {
            this.delegate = delegate;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isAnnotationPresent(Cache.class)) {
                switch (method.getAnnotation(Cache.class).cacheType()) {
                    case IN_MEMORY:
                        return invokeMemoryCache(method, args);
                    case FILE:
                        return invokeFileCache(method, args);
                }
            }

            return method.invoke(delegate, args);
        }

        private Object invokeFileCache(Method method, Object[] args) throws Throwable {
            String filename = getFileName(method, args);

            synchronized (this) {
                if (!lockMap.containsKey(filename)) {
                    lockMap.put(filename, new ReentrantLock());
                }
            }

            Lock lock = lockMap.get(filename);

            lock.lock();
            try {
                // Попытка загрузить данные из кэша
                try {
                    if (method.getAnnotation(Cache.class).zip()) {
                        return loadFromZip(filename + ".zip");
                    } else {
                        return loadFromFile(filename);
                    }
                } catch (IOException | ClassNotFoundException e) {}

                // Если загрузить из кэша не удалось, вызов метода
                Object o = method.invoke(delegate, args);

                // Ограничение рамера списка
                if (o instanceof List) {
                    o = ((List<?>) o).stream()
                            .limit(method.getAnnotation(Cache.class).listLimit())
                            .collect(Collectors.toList());
                }

                // Сохранение вычисленного значения в кэш
                try {
                    if (method.getAnnotation(Cache.class).zip()) {
                        saveToZip(filename + ".zip", o);
                        System.out.println("Calc and save in zip cache by name: " + filename + " value: " + o);
                    } else {
                        saveToFile(filename, o);
                        System.out.println("Calc and save in file cache by name: " + filename + " value: " + o);
                    }
                } catch (IOException e) {
                    System.out.println("Не удалось сохранить резульат вычисления метода " + method.getName()
                            + ". Проверте является ли класс " + o.getClass().getSimpleName() + " сериализуемым.");
                }

                return o;
            } finally {
                lock.unlock();
            }
        }

        private Object invokeMemoryCache(Method method, Object[] args) throws Throwable {
            Object key = getKey(method, args);

            synchronized (this) {
                if (!lockMap.containsKey(key)) {
                    lockMap.put(key, new ReentrantLock());
                }
            }

            Lock lock = lockMap.get(key);

            lock.lock();
            try {
                Object o;

                // Попытка загрузить данные из кэша
                if (cacheMap.containsKey(key)) {
                    o = cacheMap.get(key);
                    System.out.println("Get from memory cache by key: " + key + " value: " + o);
                    return o;
                }

                // Если загрузить из кэша не удалось, вызов метода
                o = method.invoke(delegate, args);

                // Ограничение рамера списка
                if (o instanceof List) {
                    o = ((List<?>) o).stream()
                            .limit(method.getAnnotation(Cache.class).listLimit())
                            .collect(Collectors.toList());
                }

                // Сохранение вычисленного значения в кэш
                cacheMap.put(key, o);

                System.out.println("Calc and save in memory cache by key: " + key + " value: " + o);

                return o;
            } finally {
                lock.unlock();
            }
        }

        private Object loadFromFile(String filename) throws IOException, ClassNotFoundException {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filename)));
            Object o = ois.readObject();
            ois.close();
            System.out.println("Get from file cache by name: " + filename + " value: " + o);
            return o;
        }

        private void saveToFile(String filename, Object o) throws IOException {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename)));
            oos.writeObject(o);
            oos.close();
        }

        private Object loadFromZip(String filename) throws IOException, ClassNotFoundException {
            ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream(new File(filename))));
            Object o = ois.readObject();
            ois.close();
            System.out.println("Get from zip cache by name: " + filename + " value: " + o);
            return o;
        }

        private void saveToZip(String filename, Object o) throws IOException {
            ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(new File(filename))));
            oos.writeObject(o);
            oos.close();
        }

        private String getFileName(Method method, Object[] args) {
            String filename = getKey(method, args).toString().replace(", ", "-");
            filename = filename.substring(1, filename.length() - 1);
            if (rootPath != null && rootPath.length() > 0) {
                filename = rootPath + File.separatorChar + filename;
            }
            return filename;
        }

        private Object getKey(Method method, Object[] args) {
            List<Object> key = new ArrayList<>();
            Cache cache = method.getAnnotation(Cache.class);
            // Добавление к ключу имени заданного в аннотации либо имени вызываемого метода
            key.add((cache.fileNamePrefix().length() > 0) ? cache.fileNamePrefix() : method.getName());
            // Добавление к ключу учитываемых аргументов метода
            for (int i = 0; i < args.length; i++) {
                if (!method.getParameters()[i].isAnnotationPresent(Ignore.class)) {
                    key.add(args[i]);
                }
            }
            return key;
        }
    }
}
