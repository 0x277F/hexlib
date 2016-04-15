package __0x277F.plugins.hexlib.pvc;

import __0x277F.plugins.hexlib.HexLibPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PluginLoadListener implements Listener {
    private HexLibPlugin plugin;
    private Set<JavaPlugin> validated;

    public PluginLoadListener(HexLibPlugin plugin) {
        this.plugin = plugin;
        this.validated = new HashSet<>();
    }

    /**
     * Validates a plugin's multi-version implementation.
     */
    @SuppressWarnings("unchecked")
    public void validatePlugin(JavaPlugin p) {
        Class<? extends JavaPlugin> pluginClass = p.getClass();
        if (pluginClass.isAnnotationPresent(MultiVersionPlugin.class)) {
            if (validated.contains(p)) {
                plugin.getLogger().warning("Plugin " + p.getName() + " has already been validated, ignoring.");
                return;
            }
            MultiVersionPlugin data = pluginClass.getAnnotation(MultiVersionPlugin.class);
            plugin.getLogger().info("Found plugin " + p.getName() + " supporting multiple versions.");
            Collection<Class<?>> absclasses = new HashSet<>();
            Arrays.stream(data.value()).forEach(name -> {
                try {
                    absclasses.add(Class.forName(name, false, pluginClass.getClassLoader()));
                } catch (ClassNotFoundException e) {
                    //Class does not exist
                    plugin.getLogger().warning("Could not locate class " + name + " specified as multi-version for plugin " + p.getName());
                }
            });
            absclasses.stream().forEach(absClass -> {
                if (absClass.isAnnotationPresent(PolyVersionCompat.class)) {
                    PolyVersionCompat compat = absClass.getAnnotation(PolyVersionCompat.class);
                    plugin.getLogger().info("Found multi-version-compatible class " + absClass.getName() + ": versions " + Arrays.toString(compat.value()));
                    Optional<MCVersionImpl> compatible = Arrays.stream(compat.value()).filter(v -> v.value().equals(plugin.getNmsVersion()) || v.value().equals(MinecraftVersion.ANY_CRAFTBUKKIT) || (v.value().equals(MinecraftVersion.ANY_SPIGOT) && plugin.isSpigot())).findFirst();
                    if (compatible.isPresent()) {
                        Class<?> impl = null;
                        MCVersionImpl v = compatible.get();
                        try {
                            plugin.getLogger().info("Finding class: " + absClass.getName() + "_" + v.value().name()); //DEBUG
                            impl = Class.forName(absClass.getName() + "_" + v.value().name(), false, pluginClass.getClassLoader());
                        } catch (ClassNotFoundException e) {
                            plugin.getLogger().info("Failed to find class."); //DEBUG
                            try {
                                impl = Class.forName(v.name(), false, pluginClass.getClassLoader());
                            } catch (ClassNotFoundException e1) {
                                plugin.getLogger().warning("Plugin " + p.getName() + " has improperly formatted version implementation info for poly-version-compatible " + absClass.getName());
                                if (!compat.lenient()) p.getPluginLoader().disablePlugin(p);
                            }
                        }
                        if (impl != null) {
                            if (absClass.isAssignableFrom(impl)) {
                                plugin.getLogger().info("Plugin " + p.getName() + " has valid version implementation " + impl.getName() + " for version " + v.value());
                                if (compat.autoInit()) {
                                    plugin.getLogger().info("Attempting to auto-initialize for " + absClass.getSimpleName());
                                    try {
                                        Field f = Arrays.stream(absClass.getDeclaredFields()).filter(i -> i.isAnnotationPresent(VersionSpecific.class))
                                                .findFirst().orElseGet(() -> {
                                                    try {
                                                        return absClass.getDeclaredField("clazz");
                                                    } catch (NoSuchFieldException e) {
                                                        plugin.getLogger().warning("Unable to auto-initialize " + absClass.getName());
                                                    }
                                                    return null;
                                                });
                                        boolean flag = f.isAccessible();
                                        f.setAccessible(true);
                                        f.set(null, impl);
                                        plugin.getLogger().info("Successfully auto-initialized " + absClass.getSimpleName() + " for version " + v.value());
                                        f.setAccessible(flag);
                                    } catch (IllegalAccessException e) {
                                        plugin.getLogger().warning("Unable to auto-initialize " + absClass.getName());
                                    }
                                }
                            }
                        }
                    } else {
                        plugin.getLogger().warning("Plugin " + p.getName() + " is incompatible with minecraft version " + plugin.getNmsVersion() + ", disabling...");
                        if (!compat.lenient()) p.getPluginLoader().disablePlugin(p);
                    }
                } else {
                    plugin.getLogger().warning("Class " + pluginClass.getName() + " declares non-annotated poly-version class " + absClass.getName());
                }
            });
        }

        if (p.isEnabled()) {
            validated.add(p);
        }
    }


    @EventHandler
    public void onPluginLoad(PluginEnableEvent event) {
        this.validatePlugin((JavaPlugin) event.getPlugin());
    }

    public void validatePlugin(Plugin plugin) {
        this.validatePlugin((JavaPlugin) plugin); //To make function reference happy
    }
}
