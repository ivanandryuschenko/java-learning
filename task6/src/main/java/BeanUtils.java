import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class BeanUtils {

    private static List key(String methodName, Object arg) {
        List<Object> key = new ArrayList<>();
        key.add(methodName);
        key.add(arg);
        return key;
    }

    private static Map<List, Method> getSetters(Object obj) {
        Map<List, Method> setters = new HashMap<>();
        Method[] methods = obj.getClass().getMethods();
        for (Method m : methods) {
            if (m.getName().startsWith("set") && m.getParameterCount() == 1) {
                setters.put(key(m.getName().substring(3), m.getParameterTypes()[0]), m);
            }
        }
        return setters;
    }

    private static Map<List, Method> getGetters(Object obj) {
        Map<List, Method> getterst = new HashMap<>();
        Method[] methods = obj.getClass().getMethods();
        for (Method m : methods) {
            if (m.getName().startsWith("get") && m.getParameterCount() == 0) {
                getterst.put(key(m.getName().substring(3), m.getReturnType()), m);
            }
        }
        return getterst;
    }

    /**
     * Scans object "from" for all getters. If object "to"
     * contains correspondent setter, it will invoke it
     * to set property value for "to" which equals to the property
     * of "from".
     * <p/>
     * The type in setter should be compatible to the value returned
     * by getter (if not, no invocation performed).
     * Compatible means that parameter type in setter should
     * be the same or be superclass of the return type of the getter.
     * <p/>
     * The method takes care only about public methods.
     *
     * @param to   Object which properties will be set.
     * @param from Object which properties will be used to get values.
     */
    public static void assign(Object to, Object from) {
        Map<List, Method> setters = getSetters(to);
        Map<List, Method> getters = getGetters(from);

        for (Map.Entry<List, Method> entry : setters.entrySet()) {
            if (getters.containsKey(entry.getKey())) {
                try {
                    entry.getValue().invoke(to, getters.get(entry.getKey()).invoke(from));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
