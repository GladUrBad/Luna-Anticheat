package luna.anticheat.checks.movement.speed;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;
import org.bukkit.entity.Player;

@CheckInfo(name = "Speed (A)", punishVL = 35)
public class SpeedA extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if (PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                final Player p = data.getPlayer();
                double deltaXZ = data.currDeltaXz;
                double lastDeltaXZ = data.lastDeltaXz;
                boolean offGround = data.getInAirTicks() > 1;
                boolean onClimbable = data.getOnClimbableTicks() > 0;
                boolean offStairs = data.getTicksSinceOnStairs() > 25;


                float friction = 0.91F;
                double shiftedLastDelta = lastDeltaXZ * friction;
                double equalness = deltaXZ - shiftedLastDelta;

                if(offGround && !onClimbable && offStairs && !data.isSneaking && data.getTicksSinceVelocity() > 20) {
                    if(equalness > 0.027 && !p.isFlying()) {
                        flag(data, "Invalid friction: expectedDeltaXZ: " + shiftedLastDelta + " actualDeltaXZ: " + deltaXZ);
                    } else {
                        reward(data);
                    }
                }
            }
        }
        reward(data);
    }
}
