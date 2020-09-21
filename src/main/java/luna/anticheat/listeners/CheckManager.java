package luna.anticheat.listeners;

import io.github.retrooper.packetevents.annotations.PacketHandler;
import io.github.retrooper.packetevents.event.PacketEvent;

import io.github.retrooper.packetevents.event.PacketListener;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.event.impl.PacketSendEvent;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.checks.combat.aim.AimA;
import luna.anticheat.checks.combat.aim.AimB;
import luna.anticheat.checks.combat.autoblock.AutoBlockA;
import luna.anticheat.checks.combat.killaura.KillauraA;
import luna.anticheat.checks.combat.killaura.KillauraB;
import luna.anticheat.checks.combat.killaura.KillauraC;
import luna.anticheat.checks.combat.reach.ReachA;
import luna.anticheat.checks.movement.fastclimb.FastClimbA;
import luna.anticheat.checks.movement.fly.FlyA;
import luna.anticheat.checks.movement.fly.FlyB;
import luna.anticheat.checks.movement.fly.FlyC;
import luna.anticheat.checks.movement.jesus.JesusA;
import luna.anticheat.checks.movement.jesus.JesusB;
import luna.anticheat.checks.movement.largemove.LargeMoveA;
import luna.anticheat.checks.movement.largemove.LargeMoveB;
import luna.anticheat.checks.movement.motion.MotionA;
import luna.anticheat.checks.movement.motion.MotionB;
import luna.anticheat.checks.movement.motion.MotionC;
import luna.anticheat.checks.movement.nofall.NofallA;
import luna.anticheat.checks.movement.nofall.NofallB;
import luna.anticheat.checks.movement.noslowdown.NoSlowA;
import luna.anticheat.checks.movement.omnisprint.OmnisprintA;
import luna.anticheat.checks.movement.speed.SpeedA;
import luna.anticheat.checks.movement.speed.SpeedB;
import luna.anticheat.checks.movement.speed.SpeedC;
import luna.anticheat.checks.movement.step.StepA;
import luna.anticheat.checks.packets.badpackets.*;
import luna.anticheat.core.Luna;
import luna.anticheat.playerdata.PlayerData;
import luna.anticheat.utils.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CheckManager implements Listener, PacketListener {

    private static ArrayList<Check> checks;
    public static ArrayList<UUID> vapePLAYER_DATAS;

    public static List<Player> playersToAlert;
    public static List<Player> exemptedPlayers;

    public CheckManager() {
        playersToAlert = new ArrayList<>();
        exemptedPlayers = new ArrayList<>();
        vapePLAYER_DATAS = new ArrayList<>();
        checks = new ArrayList<>();

        registerChecks(Arrays.asList(
                new AutoBlockA(),
                new ReachA(),
                new KillauraA(),
                new KillauraB(),
                new KillauraC(),
                new FastClimbA(),
                new FlyA(),
                new FlyB(),
                new FlyC(),
                new JesusA(),
                new JesusB(),
                new LargeMoveA(),
                new LargeMoveB(),
                new MotionA(),
                new MotionB(),
                new MotionC(),
                new NofallA(),
                new NofallB(),
                new NoSlowA(),
                new SpeedA(),
                new SpeedB(),
                new SpeedC(),
                new StepA(),
                new BadPacketsA(),
                new BadPacketsB(),
                new BadPacketsC(),
                new BadPacketsD(),
                new BadPacketsE(),
                new BadPacketsF(),
                new BadPacketsG(),
                new OmnisprintA(),
                new AimA(),
                new AimB()));
    }

    public static void registerCheck(Check check) {
        checks.add(check);
    }

    public static void registerChecks(List<Check> checks) {
        for(Check check : checks) {
            registerCheck(check);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b" + check.getCheckName() + " &a has been registered."));
        }
    }

    public static List<Check> getChecks() {
        return checks;
    }

    public static void addToAlerts(Player player) {
        CheckManager.playersToAlert.add(player);
    }

    public static void removeFromAlerts(Player player) {
        CheckManager.playersToAlert.remove(player);
    }

    public static void exemptPlayer(Player player) {
        CheckManager.exemptedPlayers.add(player);
    }

    public static void unExemptPlayer(Player player) {
        CheckManager.exemptedPlayers.remove(player);
    }

    public static boolean isExempted(Player player) {
        if(CheckManager.exemptedPlayers.contains(player)) {
            return true;
        } else {
            return false;
        }
    }

    public static void removeViolations(Player player) {
        for(Check check : checks) check.removeViolations(player);
    }

    public static CheckInfo getCheckInfo(Check check) {
        return check.getClass().getAnnotation(CheckInfo.class);
    }

    @PacketHandler
    public void onPacketReceive(PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            final Player player = e.getPlayer();
            //player.sendMessage(player.getName() + " checked!");
            if (player == null) return;
            if (player.isOnline()) {
                PlayerData data = Luna.getUser(e.getPlayer());
                if (data != null) {
                   PlayerDataManager.processData(e, data);
                    if (data.getPlayer().isOnline() && !isExempted(data.getPlayer()) && !data.hasPendingTeleport) {
                        for (Check check : checks) {
                            check.runCheck(data, e);
                        }
                    }
                }
            }
        } else if(event instanceof PacketSendEvent) {
            PacketSendEvent e = (PacketSendEvent) event;
            final Player player = e.getPlayer();
            if (player == null) return;
            if (player.isOnline()) {
                PlayerData data= Luna.getUser(e.getPlayer());
                if (data != null) {
                   PlayerDataManager.processData(e,data);
                    if (data.getPlayer().isOnline() && !isExempted(data.getPlayer())) {
                        for (Check check : checks) {
                            check.runCheck(data, e);
                        }
                    }
                }
            }
        }
    }


}
