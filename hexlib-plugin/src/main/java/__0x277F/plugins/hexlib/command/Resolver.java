package __0x277F.plugins.hexlib.command;

@FunctionalInterface
public interface Resolver<T> {
    T resolve(ResolvingContext context);
}
