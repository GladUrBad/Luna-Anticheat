package luna.anticheat.checks.movement.fly;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;
import luna.anticheat.utils.MathUtils;
import org.bukkit.entity.Player;

@CheckInfo(name = "Fly (A)", punishVL = 20)
public class FlyA extends Check {


    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if (PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                final Player p =data.getPlayer();
                double deltaY =data.deltaY;
                double lastDeltaY =data.lastDeltaY;
                double predictedDelta = (lastDeltaY - 0.08D) * 0.9800000190734863D;
                boolean offGroundEnough = data.getInAirTicks() >= 5;
                boolean offStairs = data.getTicksSinceOnStairs() > 40;
                boolean isCollidingWithLiquid =data.isCollidingWithLiquid();
                boolean isNotFlying =data.getTicksSinceFlying() >= 2;
                boolean isOffClimbable = data.getTicksSinceOnClimbable() > 5;
                boolean reachedPeak = lastDeltaY + deltaY < lastDeltaY;
                boolean collidedCeiling = (offGroundEnough && lastDeltaY - deltaY > 0.12);
                if (offGroundEnough && offStairs && !isCollidingWithLiquid && Math.abs(predictedDelta) >= 0.005D && !reachedPeak && data.getTicksSinceVelocity() > 20 && isOffClimbable) {
                    if (!MathUtils.isRoughlyEqual(deltaY, predictedDelta) && isNotFlying && !collidedCeiling) {
                        flag(data, "expected Y: " + predictedDelta + " actual Y: " + deltaY);
                    } else {
                        reward(data);
                    }
                }
            }
        }
    }
}
