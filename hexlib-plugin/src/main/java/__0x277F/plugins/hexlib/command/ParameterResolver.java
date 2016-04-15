package __0x277F.plugins.hexlib.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ParameterResolver {
    private static Map<Class<?>, Resolver<?>> resolverMap;

    static {
        resolverMap = new HashMap<>();

        resolverMap.put(Player.class, wrapSimple(Bukkit::getPlayer));
        resolverMap.put(World.class, wrapSimple(Bukkit::getWorld));
        resolverMap.put(Location.class, ctx -> {
            // Note: This accepts basic integer coordinates.
            // Implement a smarter system for floating point
            // coordinates if you feel like it as I'm bad at regex.
            if (ctx.getInput().matches("-?[0-9]+,-?[0-9]+,-?[0-9]+")) {
                int[] coords = Arrays.stream(ctx.getInput().split(",")).mapToInt(Integer::parseInt).toArray();
                return new Location(((Player) ctx.getSender()).getWorld(), coords[0], coords[1], coords[2]);
            }
            return null;
        });
        resolverMap.put(int.class, wrapSimple(Integer::parseInt));
        resolverMap.put(double.class, wrapSimple(Double::parseDouble));
        resolverMap.put(float.class, wrapSimple(Float::parseFloat));
        resolverMap.put(byte.class, wrapSimple(Byte::parseByte));
        resolverMap.put(short.class, wrapSimple(Short::parseShort));
        resolverMap.put(long.class, wrapSimple(Long::parseLong));
        resolverMap.put(boolean.class, wrapSimple(Boolean::parseBoolean));
    }

    public static boolean isResolvable(Class<?> clazz) {
        return resolverMap.containsKey(clazz);
    }

    public static void addResolver(Class<?> clazz, Resolver<?> resolver) {
        resolverMap.put(clazz, resolver);
    }

    public static <T> Resolver<T> wrapSimple(Function<String, T> function) {
        return ctx -> function.apply(ctx.getInput());
    }

    @SuppressWarnings("unchecked")
    public static <T> T resolve(Class<?> clazz, ResolvingContext context) {
        return (T) resolverMap.get(clazz).resolve(context);
    }
}
