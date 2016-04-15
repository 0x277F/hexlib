package __0x277F.plugins.hexlib.render;

import __0x277F.plugins.hexlib.HexLibPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public abstract class ParticleRenderAgent {
    protected static ParticleEffect effect;
    protected ParticleColor color;
    protected int speed;
    protected int density;
    protected BukkitTask repeatingTask;

    protected ParticleRenderAgent(ParticleColor color, int speed, int density) {
        this.color = color;
        this.speed = speed;
        this.density = density;
    }

    public static Vector reverseVector(Vector v) {
        Vector v1 = v.clone();
        return v.multiply(-1.0D).add(v1);
    }

    public static void initAll(ParticleEffect effect) {
        ParticleRenderAgent.effect = effect;
    }

    public abstract void drawOnce();

    public void drawRepeatedly(long repetition) {
        if (this.repeatingTask == null)
            this.repeatingTask = Bukkit.getServer().getScheduler().runTaskTimer(HexLibPlugin.getInstance(), this::drawOnce, 1L, repetition);
    }

    public void stopDrawing() {
        repeatingTask.cancel();
        repeatingTask = null;
    }

    public ParticleColor getColor() {
        return color;
    }

    public void setColor(ParticleColor color) {
        this.color = color;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
    }

}
