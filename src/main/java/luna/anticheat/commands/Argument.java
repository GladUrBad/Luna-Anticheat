package luna.anticheat.commands;

import luna.anticheat.core.Luna;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

//Thanks, Hawk.

public abstract class Argument implements Comparable<Argument> {
    private final String name;
    private final String description;
    private final String syntax;
    static Luna luna;
    static final String responsePrefix = ChatColor.translateAlternateColorCodes('&', "&8[&9Luna&8] ");

    Argument(String name, String syntax, String description) {
        this.name = name;
        this.syntax = syntax;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return name + (syntax.length() == 0 ? "" : " " + syntax);
    }

    public abstract boolean process(CommandSender sender, Command cmd, String label, String[] args);

    @Override
    public int compareTo(Argument other) {
        return name.compareTo(other.name);
    }
}