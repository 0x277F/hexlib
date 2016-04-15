package __0x277F.plugins.hexlib.render;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Delegating renderer for a rectangular prism ABCDEFGH.
 */
public class Render3DCuboidAgent extends ParticleRenderAgent implements Render3D, DelegatingRenderer<Render2D> {
    private List<Location> vertices;
    private Vector length, width, depth;
    private Render2DRectangleAgent abcd, efgh, abfe, bcgf, cdhg;

    public Render3DCuboidAgent(ParticleColor color, Location vertex, Vector length, Vector width, Vector depth, int speed, int density) {
        super(color, speed, density);
        this.vertices = new ArrayList<>();
        this.length = length;
        this.width = width;
        this.depth = depth;
        Location cursor = vertex.clone();
        vertices.add(cursor);
        vertices.add(cursor = cursor.add(length));
        vertices.add(cursor = cursor.add(width));
        vertices.add(cursor = cursor.add(reverseVector(length)));
        vertices.add(cursor = cursor.add(reverseVector(width)));
        vertices.add(cursor = cursor.add(depth));
        vertices.add(cursor = cursor.add(width));
        vertices.add(cursor = cursor.add(length));
        vertices.add(cursor = cursor.add(reverseVector(width)));
        vertices.add(cursor.add(reverseVector(length)));
        abcd = new Render2DRectangleAgent(color, vertices.get(0), length, width, speed, density);
        efgh = new Render2DRectangleAgent(color, vertices.get(4), length, width, speed, density);
        abfe = new Render2DRectangleAgent(color, vertices.get(0), length, depth, speed, density);
        bcgf = new Render2DRectangleAgent(color, vertices.get(0), width, depth, speed, density);
        cdhg = new Render2DRectangleAgent(color, vertices.get(2), reverseVector(length), depth, speed, density);
    }

    @Override
    public Collection<Render2D> getComponents() {
        return Arrays.<Render2D>asList(abcd, efgh, abfe, bcgf, cdhg);
    }

    @Override
    public void drawOnce() {
        abcd.drawOnce();
        efgh.drawOnce();
        abfe.drawOnce();
        bcgf.drawOnce();
        cdhg.drawOnce();
    }

    @Override
    public Vector getLength() {
        return length;
    }

    @Override
    public Vector getWidth() {
        return width;
    }

    @Override
    public Vector getDepth() {
        return depth;
    }

    @Override
    public void resize(Vector length, Vector width, Vector depth) {
        this.length = length.clone();
        this.width = width.clone();
        this.depth = depth.clone();
        abcd.resize(length, width);
        efgh.resize(length, width);
        abfe.resize(length, depth);
        bcgf.resize(width, depth);
        cdhg.resize(reverseVector(length), depth);
    }

    @Override
    public Set<Location> getVertices() {
        return vertices.stream().collect(Collectors.toSet());
    }
}
