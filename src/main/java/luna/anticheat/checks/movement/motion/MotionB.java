package luna.anticheat.checks.movement.motion;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "Motion (B)", punishVL = 20)
public class MotionB extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                if(Math.abs(data.deltaY) == Math.abs(data.lastDeltaY) && !data.getPlayer().isFlying() && data.deltaY != 0 && data.getOnClimbableTicks() == 0 && data.getInWaterTicks() == 0 && data.getTicksSinceOnStairs() >= 5) {
                    if(++data.motionBVerbose > 5) {
                        flag(data, "consistent Y change for " + data.motionCVerbose);
                    } else {
                        reward(data);
                    }
                } else {
                    data.motionCVerbose = 0;
                }
            }
        }
    }
}
