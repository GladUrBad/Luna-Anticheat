package luna.anticheat.checks.movement.nofall;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "NoFall (A)", punishVL = 20)
public class NofallA extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if (PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                boolean onGround = data.isOnGround();
                boolean onGroundClientTick = data.getClaimedOnGroundTicks() > 0;
                if (onGroundClientTick && !onGround && data.getTicksSinceVelocity() > 20) {
                    if (++data.nofallApreVL > 2) {
                        flag(data, "Spoofed ground.");
                    }
                } else {
                   data.nofallApreVL = 0;
                }
            }
        }
        reward(data);
    }
}
