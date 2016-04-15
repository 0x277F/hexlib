package __0x277F.plugins.hexlib.render;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public interface Render1D {
    void resize(Vector vector);

    Vector getVector();

    Location getStart();

    void setStart(Location location);
}
