package luna.anticheat.commands;

import luna.anticheat.core.Luna;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LunaCommands implements CommandExecutor {

    private final List<Argument> arguments;

    private final Luna luna;

    public LunaCommands(Luna luna) {
        this.luna = luna;
        arguments = new ArrayList<>();
        arguments.add(new AlertsArgument());
        arguments.add(new VersionArgument());
        arguments.add(new ExemptArgument());
        arguments.add(new LogsArgument());

        Collections.sort(arguments);

        Argument.luna = luna;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        boolean foundCommand = false;
        if(args.length > 0) {
            for(Argument arg : arguments) {
                String argName = arg.getName();
                if(argName.equals(args[0])) {
                    foundCommand = true;
                    if (!arg.process(commandSender, command, s, args)) {
                        commandSender.sendMessage(ChatColor.RED + "Usage: /luna " + arg.getUsage());
                    }
                    return true;
                }
            }
            return false;
        } else {
            if(commandSender instanceof Player) {
                commandSender.sendMessage(ChatColor.BLUE+ "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                for(Argument arg : arguments) {
                    commandSender.sendMessage(ChatColor.WHITE + "/luna " + arg.getName() + " : " + arg.getDescription());
                }
                commandSender.sendMessage(ChatColor.BLUE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            }
        }
        return true;
    }
}
