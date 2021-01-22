import java.io.*;

public class PluginClassLoader extends ClassLoader {
    private String rootPath;

    public PluginClassLoader(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> c = null;
        try {
            c = findClass(name);
            if (resolve) {
                resolveClass(c);
            }
        } catch (ClassNotFoundException e) {
            c = super.loadClass(name, resolve);
        }
        return c;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            File f = new File(rootPath + File.separatorChar + name.replace('.', File.separatorChar) + ".class");
            InputStream in = new BufferedInputStream(new FileInputStream(f));
            byte[] b = new byte[in.available()];
            in.read(b);
            Class c = defineClass(name, b, 0, b.length);
            in.close();
            return c;
        } catch (Exception e) {
            throw new ClassNotFoundException();
        }
    }
}
