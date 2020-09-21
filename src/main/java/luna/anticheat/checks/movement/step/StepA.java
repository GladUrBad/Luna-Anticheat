package luna.anticheat.checks.movement.step;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "Step (A)", punishVL = 10)
public class StepA extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                if(data.location.isOnGround() && data.getTicksSinceTeleport() > 5 && data.getTicksSinceTeleport() > 5) {
                    if(data.lastLocation.isOnGround()) {
                        if(data.deltaY > 0.6F) {
                            flag(data, "stepped up " + data.deltaY + " tall structure.");
                        } else {
                            reward(data);
                        }
                    }
                }
            }
        }
    }
}
