package __0x277F.plugins.hexlib;

import __0x277F.plugins.hexlib.command.HexCommandHandler;
import __0x277F.plugins.hexlib.pvc.MinecraftVersion;
import __0x277F.plugins.hexlib.pvc.MultiVersionPlugin;
import __0x277F.plugins.hexlib.pvc.PluginLoadListener;
import __0x277F.plugins.hexlib.render.ParticleEffect;
import __0x277F.plugins.hexlib.render.ParticleRenderAgent;
import __0x277F.plugins.hexlib.test.TestCommands;
import com.torchmind.minecraft.annotation.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Plugin(name = "HexLib", description = "Small library for various utilities.", author = "__0x277F", version = "${describe}")
@MultiVersionPlugin(ParticleEffect.class)
public class HexLibPlugin extends JavaPlugin {
    private static MinecraftVersion NMS_VERSION;
    private static boolean isSpigot;
    private static HexLibPlugin instance = null;
    private PluginLoadListener pluginLoadListener;
    private HexCommandHandler handler;

    public static HexLibPlugin getInstance() {
        if (instance != null) {
            return instance;
        }
        throw new IllegalStateException("HexLib is not enabled!");
    }

    @Override
    public void onEnable() {
        HexLibPlugin.instance = this;
        NMS_VERSION = MinecraftVersion.valueOf(Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3]);
        getLogger().info("MC version is " + NMS_VERSION);
        try {
            Class.forName("org.spigotmc.AsyncCatcher", false, Bukkit.class.getClassLoader()); //Random spigot-only class.
            isSpigot = true;
        } catch (ClassNotFoundException e) {
            isSpigot = false;
        }

        handler = new HexCommandHandler();
        this.pluginLoadListener = new PluginLoadListener(this);
        this.pluginLoadListener.validatePlugin(this);
        this.getServer().getPluginManager().registerEvents(pluginLoadListener, this);
        ParticleRenderAgent.initAll(ParticleEffect.newParticleEffect());
        handler.registerCommands(this, new TestCommands(this));

    }

    @Override
    public void onDisable() {
        HexLibPlugin.instance = null;
    }

    public MinecraftVersion getNmsVersion() {
        return NMS_VERSION;
    }

    public boolean isSpigot() {
        return isSpigot;
    }
}
