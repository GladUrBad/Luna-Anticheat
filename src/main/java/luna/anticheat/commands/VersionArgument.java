package luna.anticheat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VersionArgument extends Argument {

    public VersionArgument() {
        super("version", "", "Tells you the version of the anti-cheat");
    }

    @Override
    public boolean process(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&9Luna version &f1.0-PRE &9last updated &f07/11/2020"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&9Developed with passion by &fGladUrBad &c♥"));
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return false;
        }
    }
}
