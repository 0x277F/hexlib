package __0x277F.plugins.hexlib.command;

import org.bukkit.command.CommandSender;

public class ResolvingContext {
    private String input;
    private String rootCommand;
    private int index;
    private CommandSender sender;

    public ResolvingContext(String input, String rootCommand, int index, CommandSender sender) {
        this.input = input;
        this.rootCommand = rootCommand;
        this.index = index;
        this.sender = sender;
    }

    public ResolvingContext() {

    }

    public String getInput() {
        return input;
    }

    public ResolvingContext setInput(String input) {
        this.input = input;
        return this;
    }

    public String getRootCommand() {
        return rootCommand;
    }

    public ResolvingContext setRootCommand(String rootCommand) {
        this.rootCommand = rootCommand;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public ResolvingContext setIndex(int index) {
        this.index = index;
        return this;
    }

    public CommandSender getSender() {
        return sender;
    }

    public ResolvingContext setSender(CommandSender sender) {
        this.sender = sender;
        return this;
    }
}
