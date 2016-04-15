package __0x277F.plugins.hexlib.render;

import java.util.Collection;

/**
 * A render agent that delegates its drawOnce() calls to another agent.
 *
 * @param <T> Render type to delegate to. e.g. Render1D
 */
public interface DelegatingRenderer<T> {
    Collection<T> getComponents();
}
