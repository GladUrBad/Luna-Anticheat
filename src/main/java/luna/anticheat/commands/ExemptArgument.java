package luna.anticheat.commands;

import luna.anticheat.listeners.CheckManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExemptArgument extends Argument {

    ExemptArgument() {
        super("exempt", "<player>", "Exempts/Un-exempts specified player from Luna.");
    }

    @Override
    public boolean process(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            if(args.length < 2) return false;
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9Luna&8] &cUnknown player."));
                return true;
            }
            if(!CheckManager.isExempted(target)) {
                CheckManager.exemptPlayer(target);
                sender.sendMessage(ChatColor.translateAlternateColorCodes(
                        '&', "&8[&9Luna&8]&f " + target.getName() + " &ais now exempted."));
                return true;
            } else if(CheckManager.isExempted(target)){
                CheckManager.unExemptPlayer(target);
                sender.sendMessage(ChatColor.translateAlternateColorCodes(
                        '&', "&8[&9Luna&8]&f " + target.getName() + " &cis no longer exempted."));
                return true;
            }
        }
        return false;
    }
}
