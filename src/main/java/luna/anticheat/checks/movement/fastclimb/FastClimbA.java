package luna.anticheat.checks.movement.fastclimb;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;
import org.bukkit.entity.Player;

@CheckInfo(name = "FastClimb (A)", punishVL = 10)
public class FastClimbA extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if (PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                final Player p =data.getPlayer();
                double deltaY =data.deltaY;
                double lastDeltaY =data.lastDeltaY;
                boolean onClimbable =data.getOnClimbableTicks() > 2;
                boolean jumpedOnLadder = (Math.abs(deltaY - lastDeltaY) > 0.0001);

                if (onClimbable && deltaY > 0.1177 && !jumpedOnLadder) {
                    flag(data, "moved too fast on climbable blocks: " + deltaY);
                } else {
                    reward(data);
                }
            }
        }
    }
}
