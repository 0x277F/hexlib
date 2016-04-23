package __0x277F.plugins.hexlib.locale;

import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class HexLocalization {
    private static Map<Plugin, Function<String, String>> factories = new HashMap<>();
    static Map<Plugin, Map<String, PluginLocalization>> localizationMap;

    public static void setLocalizationFactory(Plugin plugin, Function<String, String> factory) {
        factories.put(plugin, factory);
    }

    public static String getLocalized(Plugin plugin, String locale, String text) {
        return factories.get(plugin).apply(text);
    }
}
