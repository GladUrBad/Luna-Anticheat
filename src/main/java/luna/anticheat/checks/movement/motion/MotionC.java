package luna.anticheat.checks.movement.motion;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "Motion (C)", dev = true, punishVL = 20)
public class MotionC extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                boolean jumped = !data.location.isOnGround() && data.lastLocation.isOnGround() && data.deltaY > 0;
                boolean isValidForChecking = data.lastLocation.getY() % 0.015625 == 0 && data.getTicksSinceUnderBlock() > 10;
                if(jumped && isValidForChecking) {
                    if(data.deltaY != 0.42F) {
                        flag(data, "invalid jumping acceleration.");
                    }
                }
            }
        }
    }
}
