package __0x277F.plugins.hexlib;

import __0x277F.plugins.hexlib.pvc.MinecraftVersion;
import __0x277F.plugins.hexlib.pvc.MultiVersionPlugin;
import __0x277F.plugins.hexlib.pvc.PluginLoadListener;
import __0x277F.plugins.hexlib.render.ParticleEffect;
import __0x277F.plugins.hexlib.render.ParticleRenderAgent;
import __0x277F.plugins.hexlib.test.HexLibCommandExecutor;
import com.torchmind.minecraft.annotation.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Plugin(name = HexLibPluginInfo.PLUGIN_ID, version = HexLibPluginInfo.PLUGIN_VERSION, author = HexLibPluginInfo.PLUGIN_AUTHOR, description = HexLibPluginInfo.PLUGIN_DESC)
@MultiVersionPlugin("__0x277F.plugins.hexlib.render.ParticleEffect")
public class HexLibPlugin extends JavaPlugin {
    private static MinecraftVersion NMS_VERSION;
    private static boolean isSpigot;
    private static HexLibPlugin instance = null;
    private PluginLoadListener pluginLoadListener;

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
        try {
            Class.forName("org.spigotmc.AsyncCatcher", false, Bukkit.class.getClassLoader()); //Random spigot-only class.
            isSpigot = true;
        } catch (ClassNotFoundException e) {
            isSpigot = false;
        }

        this.pluginLoadListener = new PluginLoadListener(this);
        this.getServer().getPluginManager().registerEvents(pluginLoadListener, this);
        this.getCommand("hexlib").setExecutor(new HexLibCommandExecutor());
        ParticleRenderAgent.initAll(ParticleEffect.newParticleEffect());
        System.out.println(ParticleEffect.newParticleEffect());
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
