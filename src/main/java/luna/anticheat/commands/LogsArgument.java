package luna.anticheat.commands;

import luna.anticheat.checks.Check;
import luna.anticheat.listeners.CheckManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LogsArgument extends Argument {
    LogsArgument() {
        super("logs", "<view|clear> <player>", "Gets violation info for the defined player.");
    }

    @Override
    public boolean process(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            if(args.length < 3) return false;
            String arg = args[1];
            Player target = Bukkit.getPlayer(args[2]);
            if(target == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9Luna&8] &cUnknown player."));
                return true;
            }
            if(arg.equals("view")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9Luna&8]&f Logs for &a" + target.getName() + "&f:"));
                for (Check check : CheckManager.getChecks()) {
                    if (check.violations.containsKey(target.getUniqueId())) {
                        if (check.getViolationLevelOfPlayer(target) > 0D) {
                            sender.sendMessage(check.getCheckName() + ": " + check.getVLColor(check.getViolationLevelOfPlayer(target)) + Math.round(check.getViolationLevelOfPlayer(target)) + "VL");
                        }
                    }
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9Luna&8]&9 --------------------------------------------"));
                return true;
            } else if(arg.equals("clear")) {
                for(Check check : CheckManager.getChecks()) {
                    check.removeViolations(target);
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9Luna&8]&f Cleared logs for &a" + target.getName()));
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
