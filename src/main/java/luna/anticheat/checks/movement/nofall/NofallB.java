package luna.anticheat.checks.movement.nofall;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;
import luna.anticheat.utils.MathUtils;

@CheckInfo(name = "NoFall (B)", punishVL = 20)
public class NofallB extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                double deltaY = data.deltaY;
                double lastDeltaY = data.lastDeltaY;
                double predictedDelta = (lastDeltaY - 0.08D) * 0.9800000190734863D;
                boolean offGroundEnough = data.getTicksSinceClaimedOnGround() >= 5;
                boolean isCollidingWithLiquid = data.isCollidingWithLiquid();
                boolean isNotFlying = data.getTicksSinceFlying() >= 2;
                boolean reachedPeak = lastDeltaY + deltaY < lastDeltaY;
                boolean notInWater = data.getTicksSinceInWater() > 5;
                boolean collidedCeiling = (offGroundEnough && lastDeltaY - deltaY > 0.12);
                if (offGroundEnough && !isCollidingWithLiquid && Math.abs(predictedDelta) >= 0.005D && !reachedPeak && data.getTicksSinceVelocity() > 20 && notInWater) {
                    if (!MathUtils.isRoughlyEqual(deltaY, predictedDelta) && isNotFlying && !collidedCeiling && data.getOnClimbableTicks() == 0 && data.getInWaterTicks() == 0) {
                        flag(data, "NoFall NoGround");
                    } else {
                        reward(data);
                    }
                }
            }
        }
    }
}
