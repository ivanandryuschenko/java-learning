import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class EncryptedClassLoader extends ClassLoader {
    private final String key;
    private final File dir;

    public EncryptedClassLoader(String key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            File f = new File(dir.getPath() + File.separatorChar + name.replace('.', File.separatorChar) + ".class.crypt");
            InputStream in = new BufferedInputStream(new FileInputStream(f));
            byte[] crypted = new byte[in.available()];
            in.read(crypted);
            byte[] decrypted = SimpleEncryptor.decrypt(crypted, key);
            Class c = defineClass(name, decrypted, 0, decrypted.length);
            in.close();
            return c;
        } catch (Exception e) {
            throw new ClassNotFoundException();
        }
    }
}
