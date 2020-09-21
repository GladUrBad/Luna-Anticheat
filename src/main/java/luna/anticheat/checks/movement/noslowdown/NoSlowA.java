package luna.anticheat.checks.movement.noslowdown;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "NoSlow (A)", punishVL = 200)
public class NoSlowA extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                if(data.isSprinting && data.getPlayer().isBlocking()) {
                    if(++data.noslowAPreVL > 2) {
                        flag(data, "sprinting while blocking!");
                    } else {
                        reward(data);
                    }
                } else {
                    data.noslowAPreVL = 0;
                }
            }
        }
    }
}
