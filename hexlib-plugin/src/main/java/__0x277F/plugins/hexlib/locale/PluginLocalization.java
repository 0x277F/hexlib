package __0x277F.plugins.hexlib.locale;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PluginLocalization {
    private Plugin plugin;
    private FileConfiguration langFile;
    private String locale;

    public PluginLocalization(Plugin plugin, File file, String locale) {
        this.plugin = plugin;
        try {
            langFile.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Default location for lang files is inside a folder called 'lang
     * in the plugin data folder.
     *
     * @param plugin Plugin for which the locale is to be stored
     * @param locale The name of the locale (e.g. en_US)
     */
    public PluginLocalization(Plugin plugin, String locale) {
        this(plugin, new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "lang", locale + ".lang"), locale);
    }

    // According to default values.
    public static Map<String, PluginLocalization> loadAll(Plugin plugin) {
        Map<String, PluginLocalization> map = new HashMap<>();
        File langFolder = new File(plugin.getDataFolder(), "lang");
        if (langFolder.exists() && langFolder.isDirectory()) {
            Arrays.stream(langFolder.listFiles((file, name) -> name.endsWith(".lang")))
                    .forEach(file -> map.put(file.getName().replace(".lang", ""),
                            new PluginLocalization(plugin, file.getName().replace(".lang", ""))));
        }
        return map;
    }

    public String getLocalized(String key) {
        return langFile.getString(key);
    }

    public String getLocale() {
        return locale;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
