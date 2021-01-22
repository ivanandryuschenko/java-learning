import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class PluginManager {
    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load(String pluginName, String pluginClassName) {
        PluginClassLoader loader = new PluginClassLoader(pluginRootDirectory + File.separator + pluginName);
        Plugin plugin = null;
        try {
            plugin = (Plugin)(loader.loadClass(pluginClassName).getConstructor(null).newInstance());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            System.out.println(e);
        }
        return plugin;
    }
}
