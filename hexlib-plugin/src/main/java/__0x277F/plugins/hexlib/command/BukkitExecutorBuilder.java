package __0x277F.plugins.hexlib.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class BukkitExecutorBuilder {
    private String mainCommandName = null;
    private int minArgs = 0;
    private int maxArgs = Integer.MAX_VALUE;
    private Predicate<CommandSender> senderFilter = null;
    private ExecutorFunction executorFunction = null;
    private String usage = null;

    public BukkitExecutorBuilder withMainCommand(String name) {
        mainCommandName = name.toLowerCase();
        return this;
    }

    public BukkitExecutorBuilder withMinArgs(int min) {
        minArgs = min;
        return this;
    }

    public BukkitExecutorBuilder withMaxArgs(int max) {
        maxArgs = max;
        return this;
    }

    public BukkitExecutorBuilder withSenderType(Predicate<CommandSender> filter) {
        senderFilter = filter;
        return this;
    }

    public BukkitExecutorBuilder withUsage(String usage) {
        this.usage = usage;
        return this;
    }

    public BukkitExecutorBuilder withCustomHandler(ExecutorFunction handler) {
        executorFunction = handler;
        return this;
    }

    public CommandExecutor build() {
        return (commandSender, command, s, strings) -> {
            if (senderFilter != null && senderFilter.test(commandSender)) {
                if (minArgs <= strings.length && strings.length < maxArgs) {
                    if (mainCommandName != null && command.getName().equalsIgnoreCase(mainCommandName)) {
                        List<String> args = Arrays.asList(strings);
                        if (executorFunction != null) {
                            if (!executorFunction.execute(commandSender, command, s, args)) {
                                if (usage != null) {
                                    commandSender.sendMessage(usage);
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return true;
                            }
                        }
                    }
                }
            }
            return true;
        };
    }
}
