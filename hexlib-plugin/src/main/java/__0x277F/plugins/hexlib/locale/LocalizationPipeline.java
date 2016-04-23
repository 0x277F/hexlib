package __0x277F.plugins.hexlib.locale;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class LocalizationPipeline {
    private Plugin plugin;
    private LinkedList<Function<String, String>> pipeline;
    private Map<String, PluginLocalization> localizationMap = HexLocalization.localizationMap.get(plugin);

    public LocalizationPipeline(Plugin plugin) {
        this.pipeline = new LinkedList<>();
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void addAdapter(Function<String, String> adapter) {
        pipeline.add(adapter);
    }

    public void addAdapter(int location, Function<String, String> adapter) {
        pipeline.add(location, adapter);
    }

    public void addMonitor(Consumer<String> monitor) {
        pipeline.add(data -> {
            monitor.accept(data);
            return data;
        });
    }

    public void localizeChat(Player to) {
        pipeline.add(key -> localizationMap.get(to.spigot().getLocale()).getLocalized(key));
    }
}
