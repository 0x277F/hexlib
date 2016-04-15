package __0x277F.plugins.hexlib.render;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Set;

public interface Render3D {
    Vector getLength();

    Vector getWidth();

    Vector getDepth();

    void resize(Vector length, Vector width, Vector depth);

    Set<Location> getVertices();
}
