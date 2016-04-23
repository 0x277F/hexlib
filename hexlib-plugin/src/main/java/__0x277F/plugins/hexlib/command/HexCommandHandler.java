package __0x277F.plugins.hexlib.command;

import __0x277F.plugins.hexlib.HexLibPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HexCommandHandler {
    private static HexCommandHandler instance;
    private static Field commandMap;

    public HexCommandHandler() {
        if (HexCommandHandler.instance == null) {
            HexCommandHandler.instance = this;
        }
    }

    public static HexCommandHandler instance() {
        return instance;
    }

    static {
        try {
            commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMap.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private Map<HexCommand, Map<String, ExecutorFunction>> subcommandMap;

    public <T> void registerCommands(Plugin plugin, T holder) {
        HexLibPlugin hexlib = HexLibPlugin.getInstance(); // Makes sure plugin is enabled.
        @SuppressWarnings("unchecked")
        Class<T> holderClass = (Class<T>) holder.getClass();
        if (holderClass.isAnnotationPresent(CommandHolder.class)) {
            CommandHolder holderAnnotation = holderClass.getAnnotation(CommandHolder.class);
            String name = holderAnnotation.value();
            String[] aliases = holderAnnotation.aliases().length == 0 ? new String[]{name} : holderAnnotation.aliases();
            Optional<Method> rootHandlerOpt = Arrays.stream(holderClass.getMethods()).filter(method -> method.isAnnotationPresent(RootCommand.class)).findFirst();
            boolean usingCustomRoot = rootHandlerOpt.isPresent();
            Method rootHandler = rootHandlerOpt.orElseGet(() -> {
                try {
                    return HexCommandHandler.class.getDeclaredMethod("rootDefault", CommandSender.class, Command.class, String.class, List.class);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("wot?");
                }
            });
            HexCommand rootCommand = new HexCommand(name, ((sender, command1, label, args) -> {
                ((HexCommand) command1).getChildren().stream()
                        .filter(command -> command.getName().equalsIgnoreCase(args.get(0)) || command.getAliases().contains(args.get(0).toLowerCase()))
                        .forEach(command -> {
                            command.execute(sender, args.get(0), args);
                        });
                return true;
            }));
            Arrays.stream(holderClass.getDeclaredMethods()).filter(method -> method.isAnnotationPresent(Subcommand.class))
                    .forEach(method -> {
                        // TODO: Migrate nonperformant reflection
                        Subcommand subcommand = method.getDeclaredAnnotation(Subcommand.class);
                        String syntaxHint = method.isAnnotationPresent(Syntax.class) ? method.getAnnotation(Syntax.class).value() : "No such command.";
                        HexCommand command = new HexCommand(subcommand.value(), (sender, command1, label, args) -> {
                            try {
                                if (Arrays.equals(method.getParameterTypes(), new Object[]{CommandSender.class, Command.class, String.class, List.class})) {
                                    return (boolean) method.invoke(holder, sender, command1, label, args);
                                }
                                List<Object> passArgs = new ArrayList<>(args.size());
                                int argIndex = 0;
                                for (Parameter parameter : method.getParameters()) {
                                    String input = args.get(argIndex);
                                    if (parameter.getType() == CommandSender.class || parameter.isAnnotationPresent(Sender.class)) {
                                        if (CommandSender.class.isAssignableFrom(parameter.getType())) {
                                            passArgs.add(parameter.getType().cast(sender));
                                        } else {
                                            throw new IllegalArgumentException("Parameter annotated with @Sender isn't a CommandSender!");
                                        }
                                        continue; // Command sender parameters should never be optional
                                    }
                                    ResolvingContext ctx = new ResolvingContext()
                                            .setSender(sender)
                                            .setRootCommand(label)
                                            .setInput(input);
                                    Object o = ParameterResolver.resolve(parameter.getClass(), ctx);
                                    if (parameter.isAnnotationPresent(OptionalParameter.class)) {
                                        OptionalParameter optional = parameter.getAnnotation(OptionalParameter.class);
                                        if (!optional.value().isEmpty()) {
                                            if (o == null) {
                                                o = ctx.setInput(optional.value());
                                                passArgs.add(o);
                                                continue;
                                            }
                                        }
                                    }
                                    passArgs.add(o);
                                }
                                return (boolean) method.invoke(holder, passArgs.toArray());
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                                return true;
                            }
                        });
                        rootCommand.registerChild(command);
                    });
            register(plugin, rootCommand);
        }
    }

    public static void register(Plugin plugin, Command command) {
        try {
            ((SimpleCommandMap) commandMap.get(Bukkit.getServer())).register(plugin.getName(), command);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static boolean rootDefault(CommandSender sender, Command command, String label, List<String> args) {
        sender.sendMessage(ChatColor.RED + "Not enough arguments!");
        return false;
    }
}
