package luna.anticheat.commands;

import luna.anticheat.listeners.CheckManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertsArgument extends Argument {

    public AlertsArgument() {
        super("alerts", "", "Turns anti-cheat alerts on or off.");
    }

    @Override
    public boolean process(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            if(CheckManager.playersToAlert.contains(((Player) sender).getPlayer())) {
                CheckManager.removeFromAlerts((Player) sender);
                sender.sendMessage(Argument.responsePrefix + ChatColor.RED + "Turned your alerts off.");
            } else if(!CheckManager.playersToAlert.contains(((Player) sender).getPlayer())) {
                CheckManager.addToAlerts((Player) sender);
                sender.sendMessage(Argument.responsePrefix + ChatColor.GREEN + "Turned your alerts on.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return false;
        }
        return true;
    }
}
