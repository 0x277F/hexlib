package __0x277F.plugins.hexlib.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class HexCommand extends Command {
    private ExecutorFunction executor;

    protected HexCommand(String name, ExecutorFunction executor) {
        super(name);
        this.executor = executor;
    }

    protected HexCommand(String name, String description, String usageMessage, List<String> aliases, ExecutorFunction executor) {
        super(name, description, usageMessage, aliases);
        this.executor = executor;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        return executor.execute(sender, this, commandLabel, Arrays.asList(args));
    }
}
