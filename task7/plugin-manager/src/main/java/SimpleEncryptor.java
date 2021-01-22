import java.util.Arrays;

public class SimpleEncryptor {
    public static byte[] crypt(byte[] data, String key) {
        byte[] b = new byte[data.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte)(data[i] + (byte)key.charAt( key.charAt( i % key.length() ) % key.length() ));
        }
        return b;
    }

    public static byte[] decrypt(byte[] data, String key) {
        byte[] b = new byte[data.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte)(data[i] - (byte)key.charAt( key.charAt( i % key.length() ) % key.length() ));
        }
        return b;
    }
}
