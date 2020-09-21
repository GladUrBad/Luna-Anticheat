package luna.anticheat.checks.movement.fly;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import io.github.retrooper.packetevents.packetwrappers.in.flying.WrappedPacketInFlying;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;


@CheckInfo(name = "Fly (B)", punishVL = 40)
public class FlyB extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                if(data.getTicksSinceOnClimbable() > 5 && data.getTicksSinceOnStairs() > 40) {
                    if (data.getInAirTicks() >= 20 && data.getNegativeDeltaYTicks() < 12 && data.getJumpBoostLevel() <= 0 &&
                            data.getTicksSinceSlime() > 100 && data.getTicksSinceInWater() > 10 && data.getTicksSinceVelocity() > 20 && data.getOnGroundTicks() == 0 && data.getTicksSinceFlying() > 100 && !data.isInWater()) {
                        flag(data, "Off ground limit failed (A). Stair ticks: " + data.getTicksSinceOnStairs());
                    } else {
                        reward(data);
                    }
                    if (data.getInAirTicks() >= 75 && data.getTicksSinceInWater() > 10 && data.getJumpBoostLevel() <= 0 && data.getTicksSinceSlime() > 100 && data.getOnGroundTicks() == 0 && data.getTicksSinceFlying() > 100 && !data.isInWater()) {
                        flag(data, "Off ground limit failed (B). Stair ticks: " + data.getTicksSinceOnStairs());
                    } else {
                        reward(data);
                    }
                }
            }
        }
    }
}
