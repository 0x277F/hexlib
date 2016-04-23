package __0x277F.plugins.hexlib.test;

import __0x277F.plugins.hexlib.HexLibPlugin;
import __0x277F.plugins.hexlib.command.CommandHolder;
import __0x277F.plugins.hexlib.command.Subcommand;
import org.bukkit.command.CommandSender;

@CommandHolder("hexlib")
public class TestCommands {
    private HexLibPlugin plugin;

    public TestCommands(HexLibPlugin plugin) {
        this.plugin = plugin;
    }

    @Subcommand("hello")
    public void helloTest(CommandSender sender) {
        sender.sendMessage("Hello!");
    }
}
