package luna.anticheat.utils;

import io.github.retrooper.packetevents.PacketEvents;
import luna.anticheat.checks.Check;
import luna.anticheat.core.Luna;
import luna.anticheat.listeners.CheckManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class ViolationHandler {

    public static void kickCheater(Player cheater, Check check) {
        Bukkit.getScheduler().runTaskLater(Luna.getInstance(), new Runnable() {
            @Override
            public void run() {
                cheater.getWorld().strikeLightningEffect(cheater.getPlayer().getLocation());
                cheater.kickPlayer("Cheating: " + check.getCheckName());
                CheckManager.removeViolations(cheater);
            }
        }, 0L);
    }
    public static void sendAlert(Player flagger, Player staff, String information, Check check) {
        //Setting up constants for alerts
        String FAILED_CHECK = (ChatColor.WHITE + flagger.getName() + ChatColor.GRAY + " failed " + ChatColor.WHITE + check.getCheckName());
        String VL = ChatColor.translateAlternateColorCodes('&',
                " &8(" + check.getVLColor(check.violations.get(flagger.getUniqueId())) + check.getDecimalFormat().format(check.violations.get(flagger.getUniqueId())) +
                        "&8)");

        //Creating TextComponent from Bukkit to have Hoverinfo for checks
        TextComponent alertsComponent = new TextComponent();
        if(check.isInDevelopment()) {
            alertsComponent.setText(ChatColor.translateAlternateColorCodes('&',
                    "&8[&4&lDev&8] &8[&9Luna&8] " + FAILED_CHECK + VL));
        } else {
            alertsComponent.setText(ChatColor.translateAlternateColorCodes('&',
                    "&8[&9Luna&8] " + FAILED_CHECK + VL));
        }
        alertsComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(
                "Info:\n" + "Check info: " + information + "\n" + flagger.getName() + "'s Ping: " + PacketEvents.getAPI().getPlayerUtilities().getPing(flagger) +
                        "\nServer TPS: " + new DecimalFormat("##.##").format(PacketEvents.getAPI().getServerUtilities().getCurrentServerTPS()) + "\n\n " +
                        "(Click " +
                        "to " +
                        "teleport to " + flagger.getName() + ")").color(net.md_5.bungee.api.ChatColor.AQUA).create()));
        alertsComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + flagger.getName()));

        //Sending alert
        staff.spigot().sendMessage(alertsComponent);
    }
}
