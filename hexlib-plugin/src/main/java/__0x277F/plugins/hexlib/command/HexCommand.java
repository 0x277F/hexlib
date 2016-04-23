package __0x277F.plugins.hexlib.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HexCommand extends Command {
    private ExecutorFunction executor;
    private List<HexCommand> children;

    protected HexCommand(String name, ExecutorFunction executor) {
        super(name);
        this.executor = executor;
        this.children = new ArrayList<>();
    }

    protected HexCommand(String name, String description, String usageMessage, List<String> aliases, ExecutorFunction executor) {
        super(name, description, usageMessage, aliases);
        this.executor = executor;
        this.children = new ArrayList<>();
    }

    public boolean execute(CommandSender sender, String commandLabel, List<String> args) {
        return this.execute(sender, commandLabel, (String[]) args.toArray());
    }

    public List<HexCommand> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public void registerChild(HexCommand child) {
        children.add(child);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        return executor.execute(sender, this, commandLabel, Arrays.asList(args));
    }
}
