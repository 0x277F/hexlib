package __0x277F.plugins.hexlib.render;

import __0x277F.plugins.hexlib.HexLibPlugin;
import __0x277F.plugins.hexlib.UnsupportedMinecraftVersionException;
import __0x277F.plugins.hexlib.pvc.MCVersionImpl;
import __0x277F.plugins.hexlib.pvc.MinecraftVersion;
import __0x277F.plugins.hexlib.pvc.PolyVersionCompat;
import __0x277F.plugins.hexlib.pvc.VersionSpecific;
import org.bukkit.entity.Player;

/**
 * Provides a poly-version-compatible way to show redstone
 * particles of any given color.
 */
@PolyVersionCompat(value = {
        @MCVersionImpl(value = MinecraftVersion.v1_8_R3, completeness = MCVersionImpl.Completeness.FULL),
        @MCVersionImpl(value = MinecraftVersion.v1_9_R1, completeness = MCVersionImpl.Completeness.FULL),
        //@MCVersionImpl(value = MinecraftVersion.ANY_SPIGOT, completeness = MCVersionImpl.Completeness.PARTIAL)
}, lenient = true)
public abstract class ParticleEffect {
    @VersionSpecific
    private static Class<? extends ParticleEffect> clazz = null;

    public static ParticleEffect newParticleEffect() {
        System.out.println(clazz);
        if (clazz != null) {
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
                throw new UnsupportedMinecraftVersionException("Cannot instantiate particle effect for version " + HexLibPlugin.getInstance().getNmsVersion());
            }
        } else {
            throw new UnsupportedMinecraftVersionException("Cannot instantiate particle effect for version " + HexLibPlugin.getInstance().getNmsVersion());
        }
    }

    public abstract void displayParticles(ParticleColor color, org.bukkit.Location l);

    public abstract void displayParticles(float r, float g, float b, org.bukkit.Location l);

    public abstract void displayParticlesTo(ParticleColor color, org.bukkit.Location l, Player p);

    public abstract void displayParticlesTo(float r, float g, float b, org.bukkit.Location l, Player p);
}
