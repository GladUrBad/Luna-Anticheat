package luna.anticheat.checks.packets.badpackets;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;
import org.bukkit.Difficulty;

@CheckInfo(name = "BadPackets (F)", punishVL = 20)
public class BadPacketsF extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                if(data.isSprinting &&data.getPlayer().getFoodLevel() < 6 &&data.getPlayer().getLocation().getWorld().getDifficulty() != Difficulty.PEACEFUL) {
                    if(++data.bpFVl > 2) {
                        flag(data, "sprinting while food level is too low");
                    } else {
                        reward(data);
                    }
                } else {
                   data.bpFVl = 0;
                }
            }
        }
    }
}
