import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Задание 1");
        testPluginManager();
        System.out.println("\nЗадание 2");
        testEncryptedClassLoader();
    }

    public static void testPluginManager() {
        System.out.println(Help.about());
        PluginManager pm = new PluginManager("task7/plugins");
        Plugin[] plugins = new Plugin[] {
                pm.load("plugin1", "MyPlugin1"),
                pm.load("plugin2", "MyPlugin2")
        };
        for (Plugin p : plugins) {
            if (p != null)
                p.doUsefull();
        }
    }

    public static void testEncryptedClassLoader() {
        // Создание зашифрованного класса
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(new File("task7/plugins/plugin2/MyPlugin2.class")));
            OutputStream out = new BufferedOutputStream(new FileOutputStream(new File("task7/plugins/plugin2/MyPlugin2.class.crypt")));
            out.write(SimpleEncryptor.crypt(in.readAllBytes(), "testpass"));
            in.close();
            out.close();
        } catch (IOException e ) {
            System.out.println("Не удалось зашифровать файл");
        }
        // Загрузка зашифрованного класса
        ClassLoader cryptedClassLoader = new EncryptedClassLoader("testpass", new File("task7/plugins/plugin2"), ClassLoader.getSystemClassLoader());
        try {
            Class<?> clazz = cryptedClassLoader.loadClass("MyPlugin2");
            Plugin plugin = (Plugin)(clazz.getConstructor(null).newInstance());
            plugin.doUsefull();
        } catch (Exception | Error e) {
            System.out.println("Не удалось загрузить зашифрованный класс");
        }
    }
}
