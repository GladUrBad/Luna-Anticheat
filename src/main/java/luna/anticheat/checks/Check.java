package luna.anticheat.checks;

import io.github.retrooper.packetevents.event.PacketEvent;
import luna.anticheat.core.Luna;
import luna.anticheat.listeners.CheckManager;
import luna.anticheat.playerdata.PlayerData;
import luna.anticheat.utils.ViolationHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public abstract class Check {
    private final DecimalFormat df = new DecimalFormat("0.0");

    public Map<UUID, Double> violations = new WeakHashMap<>();
    public Map<UUID, Integer> flagStreaks = new WeakHashMap<>();
    public Map<UUID, Location> locationsLastNotFlagged = new WeakHashMap<>();

    public void flag(PlayerData data, String information) {
        double violations = this.violations.getOrDefault(data.getPlayer().getUniqueId(), 0D) + 1;
        this.violations.put(data.getPlayer().getUniqueId(), violations);
        int flagStreak = this.flagStreaks.getOrDefault(data.getUUID(), 0) + 1;
        this.flagStreaks.put(data.getUUID(), flagStreak);
        for(Player staff : Bukkit.getOnlinePlayers()) {
            if(CheckManager.playersToAlert.contains(staff)) {
                if(staff.isOnline()) {
                    ViolationHandler.sendAlert(data.getPlayer(), staff, information, this);
                }
            }
        }
        if(violations >= getPunishVL() && !this.isInDevelopment()) {
            //ViolationHandler.kickCheater(data.getPlayer(), this);
        }
    }

    public void removeViolations(Player player) {
        this.violations.put(player.getUniqueId(), 0D);
    }

    public void reward(PlayerData data) {
        double violations = this.violations.getOrDefault(data.getPlayer().getUniqueId(), 0D) * getRewardMultiplier();
        this.violations.put(data.getPlayer().getUniqueId(), violations);
        this.locationsLastNotFlagged.put(data.getUUID(), data.getPlayer().getLocation());
        this.flagStreaks.put(data.getUUID(), 0);
    }

    public double getViolationLevelOfPlayer(Player player) {
        return this.violations.get(player.getUniqueId());
    }

    public abstract void runCheck(PlayerData data, PacketEvent event);

    public String getCheckName() {
        return CheckManager.getCheckInfo(this).name();
    }

    public boolean isInDevelopment() {
        return CheckManager.getCheckInfo(this).dev();
    }

    public double getRewardMultiplier() {
        return 0.999;
    }

    public DecimalFormat getDecimalFormat() {
        return df;
    }

    public double getPunishVL() {
        return CheckManager.getCheckInfo(this).punishVL();
    }

    public ChatColor getVLColor(double vl) {
        if(vl >= (getPunishVL() / 3) && (vl <= (getPunishVL() /2))) {
            return ChatColor.YELLOW;
        } else if(vl >= (getPunishVL() / 2)) {
            return ChatColor.RED;
        }
        return ChatColor.GREEN;
    }
}
