package __0x277F.plugins.hexlib.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

@FunctionalInterface
public interface ExecutorFunction {
    boolean execute(CommandSender sender, Command command, String label, List<String> args);
}
