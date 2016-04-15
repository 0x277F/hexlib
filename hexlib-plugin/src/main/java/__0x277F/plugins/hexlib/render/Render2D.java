package __0x277F.plugins.hexlib.render;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Set;

public interface Render2D {
    Vector getLength();

    Vector getWidth();

    void resize(Vector length, Vector width);

    Set<Location> getVertices();
}
