package __0x277F.plugins.hexlib.test;

import org.bukkit.command.CommandSender;

public interface Subcommand {
    boolean execute(CommandSender sender, String label, String[] args);

    boolean isValid(CommandSender sender);
}
