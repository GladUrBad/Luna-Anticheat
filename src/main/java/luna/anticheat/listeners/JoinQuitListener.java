package luna.anticheat.listeners;

import io.github.retrooper.packetevents.event.PacketListener;
import luna.anticheat.core.Luna;
import luna.anticheat.playerdata.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class JoinQuitListener implements PacketListener, Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        final PlayerData data = new PlayerData(player.getUniqueId());
        UUID playerUUID = player.getUniqueId();
        data.joinTime = System.currentTimeMillis();

        if(!Luna.PLAYER_DATA.containsKey(playerUUID)) {
            Luna.PLAYER_DATA.put(playerUUID,data);
        }

        e.getPlayer().sendMessage(ChatColor.AQUA + "Welcome to the Luna Test Server! Do /luna to see commands.");
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        UUID playerUUID = player.getUniqueId();
        Luna.PLAYER_DATA.remove(playerUUID);

    }
}