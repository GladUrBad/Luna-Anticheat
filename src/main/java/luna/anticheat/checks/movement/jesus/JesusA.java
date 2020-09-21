package luna.anticheat.checks.movement.jesus;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "Jesus (A)", punishVL = 10)
public class JesusA extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if (PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                if(!data.isInWater() && data.isWaterBelow() && data.getOnSolidGroundTicks() == 0) {
                    if(++data.liquidWalkCounterA >= 10) {
                        flag(data, "walking on water for " + data.liquidWalkCounterA + " ticks!");
                    } else {
                        reward(data);
                    }
                } else {
                    data.liquidWalkCounterA = 0;
                }
            }
        }
    }
}
