package __0x277F.plugins.hexlib.render;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Represents a bounded line of particles.
 * It contains a direction (to show an actual drawing animation)
 * It will also contain a speed and a density to customize
 * the effect of this animation and of the particles.
 * The drawOnce() method <b>MUST</b> be run <b>asynchronously</b>
 */
public class Render1DLineAgent extends ParticleRenderAgent implements Render1D {
    private Vector vector;
    private Location start;

    public Render1DLineAgent(ParticleColor color, Vector vector, Location start, int speed, int density) {
        super(color, speed, density);
        this.vector = vector.clone();
        this.start = start;
    }

    @Override
    public void drawOnce() {
        int segnum = 1;
        try {
            for (int seg = 0; seg < vector.length(); seg += density) {
                Bukkit.broadcastMessage(String.valueOf(effect == null));
                effect.displayParticles(color, start.add(vector.multiply(1 / density).multiply(segnum)));
                Thread.sleep(speed);
                segnum++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Vector getVector() {
        return vector;
    }

    public void resize(Vector newVector) {
        this.vector = newVector.clone();
    }

    public Location getStart() {
        return start;
    }

    public void setStart(Location start) {
        this.start = start;
    }
}
