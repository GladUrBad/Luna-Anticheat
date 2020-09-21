package luna.anticheat.core;

import io.github.retrooper.packetevents.PacketEvents;
import luna.anticheat.commands.LunaCommands;
import luna.anticheat.listeners.CheckManager;
import luna.anticheat.listeners.JoinQuitListener;
import luna.anticheat.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;


public class Luna extends JavaPlugin {

    private final LunaCommands lunaCommands = new LunaCommands(this);
    private static Plugin INSTANCE;
    public static final HashMap<UUID, PlayerData> PLAYER_DATA = new HashMap<>();
    public static long enableTime;

    @Override
    public void onLoad() {
        PacketEvents.load();
    }

    @Override
    public void onEnable() {
        setupPluginInstance();
        registerBukkitEvents();
        registerCommands();
        registerPacketEvents();
        registerSpecialCheckData();
        registerPlayers();
    }

    @Override
    public void onDisable() {
        PacketEvents.stop();
    }

    public void setupPluginInstance() {
        INSTANCE = this;
    }

    public void registerCommands() {
        Objects.requireNonNull(getCommand("luna")).setExecutor(lunaCommands);
    }

    public void registerBukkitEvents() {
        PluginManager luna = Bukkit.getPluginManager();
        luna.registerEvents(new JoinQuitListener(), this);
    }


    public void registerPacketEvents() {
        PacketEvents.getSettings().setIdentifier("luna_anticheat");
        PacketEvents.start(this);
        PacketEvents.getAPI().getEventManager().registerListener(new CheckManager());
        PacketEvents.getAPI().getEventManager().registerListener(new JoinQuitListener());
    }

    public void registerPlayers() {
        for(final Player p : Bukkit.getOnlinePlayers()) {
            UUID playerUUID = p.getUniqueId();
            PacketEvents.getAPI().getPlayerUtilities().injectPlayer(p);
            if(!Luna.PLAYER_DATA.containsKey(playerUUID)) {
                Luna.PLAYER_DATA.put(playerUUID, new PlayerData(p.getUniqueId()));
            }
        }
    }

    public void registerSpecialCheckData() {
        enableTime = System.currentTimeMillis();
    }

    public static PlayerData getUser(Player player) {
        for(PlayerData data : Luna.PLAYER_DATA.values()) {
            if(data.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                return data;
            }
        }
        return null;
    }

    //Gets the instance of Luna.
    public static Plugin getInstance() {
        return INSTANCE;
    }
}