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
 * Delegating render agent to draw a rectangle ABCD.
 * One vertex is given and two vectors representing height and width are given.
 */
public class Render2DRectangleAgent extends ParticleRenderAgent implements Render2D, DelegatingRenderer<Render1D> {
    private List<Location> vertices;
    private Vector height, width;
    private Render1DLineAgent ab, bc, cd, da; //Assume vertex given is 'a'

    public Render2DRectangleAgent(ParticleColor color, Location vertex, Vector height, Vector width, int speed, int density) {
        super(color, speed, density);
        this.vertices = new ArrayList<>();
        this.height = height;
        this.width = width;
        Location cursor = vertex.clone();
        vertices.add(cursor);
        vertices.add(cursor = vertex.add(height));
        vertices.add(cursor = cursor.add(width));
        vertices.add(cursor.add(reverseVector(height)));
        ab = new Render1DLineAgent(color, height, vertices.get(0), speed, density);
        bc = new Render1DLineAgent(color, width, vertices.get(1), speed, density);
        cd = new Render1DLineAgent(color, reverseVector(height), vertices.get(2), speed, density);
        da = new Render1DLineAgent(color, reverseVector(width), vertices.get(3), speed, density);
    }

    @Override
    public void drawOnce() {
        ab.drawOnce();
        bc.drawOnce();
        cd.drawOnce();
        da.drawOnce();
    }

    @Override
    public Collection<Render1D> getComponents() {
        return Arrays.<Render1D>asList(ab, bc, cd, da);
    }

    @Override
    public Vector getLength() {
        return height;
    }

    @Override
    public Vector getWidth() {
        return width;
    }

    @Override
    public void resize(Vector height, Vector width) {
        this.height = height.clone();
        this.width = width.clone();
        ab.resize(height);
        bc.resize(width);
        cd.resize(height.clone().multiply(-1.0D));
        da.resize(width.clone().multiply(-1.0D));
    }

    @Override
    public Set<Location> getVertices() {
        return vertices.stream().collect(Collectors.toSet());
    }
}
