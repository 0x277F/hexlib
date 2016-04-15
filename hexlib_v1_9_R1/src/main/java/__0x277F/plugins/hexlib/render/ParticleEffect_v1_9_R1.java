package __0x277F.plugins.hexlib.render;

import __0x277F.plugins.hexlib.pvc.PolyVersionImpl;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@PolyVersionImpl
public class ParticleEffect_v1_9_R1 extends ParticleEffect {
    @Override
    public void displayParticles(ParticleColor color, Location l) {
        displayParticles(color.r, color.g, color.b, l);
    }

    @Override
    public void displayParticles(float r, float g, float b, Location l) {
        for (Player p : l.getWorld().getPlayers()) {
            displayParticlesTo(r, g, b, l, p);
        }
    }

    @Override
    public void displayParticlesTo(ParticleColor color, Location l, Player p) {
        displayParticlesTo(color.r, color.g, color.b, l, p);
    }

    @Override
    public void displayParticlesTo(float r, float g, float b, Location l, Player p) {
        net.minecraft.server.v1_9_R1.PacketPlayOutWorldParticles packet = new net.minecraft.server.v1_9_R1.PacketPlayOutWorldParticles(
                net.minecraft.server.v1_9_R1.EnumParticle.REDSTONE, true,
                (float) l.getX(), (float) l.getY(), (float) l.getZ(),
                r, g, b,
                1, 0);
        ((org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }
}

