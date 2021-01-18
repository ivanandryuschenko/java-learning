import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MainApp {
    public static <T> T newPerfomanceProxy(Object delegate) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                delegate.getClass().getInterfaces(),
                new PerfomanceInvocationHandler(delegate));
    }

    public static <T> T newCacheProxy(Object delegate) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                delegate.getClass().getInterfaces(),
                new CacheInvocationHandler(delegate));
    }

    public static void calculator() {
        Calculator calculator = newPerfomanceProxy(newCacheProxy(new CalculatorImpl()));
        System.out.println(calculator.calc(4));
        System.out.println(calculator.calc(8));
        System.out.println(calculator.calc(6));
        System.out.println(calculator.calc(10));
        System.out.println(calculator.calc(4));
        System.out.println(calculator.calc(10));
    }

    public static Method[] getAllMethodsInHierarchy(Class<?> objectClass) {
        Set<Method> allMethods = new HashSet<Method>();
        Method[] declaredMethods = objectClass.getDeclaredMethods();
        Method[] methods = objectClass.getMethods();
        if (objectClass.getSuperclass() != null) {
            Class<?> superClass = objectClass.getSuperclass();
            Method[] superClassMethods = getAllMethodsInHierarchy(superClass);
            allMethods.addAll(Arrays.asList(superClassMethods));
        }
        allMethods.addAll(Arrays.asList(declaredMethods));
        allMethods.addAll(Arrays.asList(methods));
        return allMethods.toArray(new Method[allMethods.size()]);
    }

    public static void printAllMethodsInHierarchy() {
        Method[] methods = getAllMethodsInHierarchy(ChildClass.class);
        for (Method m : methods) {
            System.out.println(m.getName());
        }
    }

    public static void printAllGetters() {
        Method[] methods = getAllMethodsInHierarchy(ChildClass.class);
        for (Method m : methods) {
            if (m.getName().startsWith("get"))
                System.out.println(m.getName());
        }
    }

    public static void checkStringConstants() {
        Field[] fields = ParentClass.class.getFields();
        for (Field f : fields) {
            try {
                if (f.getType().equals(String.class)
                        && Modifier.isFinal(f.getModifiers())
                        && Modifier.isStatic(f.getModifiers())
                        && f.getName().compareTo((String)f.get(null)) != 0) {
                    System.out.println("Значение константы " + f.getName() + " не равно ее имени");
                    return;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Все String константы имеют значение равное их имени");
    }

    public static void testBeanUtils() {
        ParentClass p1 = new ParentClass();
        p1.setIntField(5);
        p1.setStringField("String");
        ParentClass p2 = new ParentClass();
        BeanUtils.assign(p2, p1);
        System.out.println(p2.getIntField());
        System.out.println(p2.getStringField());
    }

    public static void main(String[] args) {
        System.out.println("\n\nЗадача 1, 5, 6");
        calculator();
        System.out.println("\n\nЗадача 2");
        printAllMethodsInHierarchy();
        System.out.println("\n\nЗадача 3");
        printAllGetters();
        System.out.println("\n\nЗадача 4");
        checkStringConstants();
        System.out.println("\n\nЗадача 7");
        testBeanUtils();
    }
}
