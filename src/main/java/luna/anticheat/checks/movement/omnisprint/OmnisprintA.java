package luna.anticheat.checks.movement.omnisprint;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;
import luna.anticheat.utils.MathUtils;

@CheckInfo(name = "Omnisprint (A)", dev = true)
public class OmnisprintA extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                float yaw = MathUtils.yawTo180F(data.location.getYaw());
                if(data.isSprinting) {
                    if ((yaw > 0F && yaw < 90F && data.deltaX > 0 && data.deltaZ < 0) ||
                            (yaw > 90F && yaw < 180F && data.deltaX > 0 && data.deltaZ > 0) ||
                            (yaw > -180F && yaw < -90F && data.deltaX < 0 && data.deltaZ > 0) ||
                            (yaw > -90F && yaw < -0F && data.deltaX < 0 && data.deltaZ < 0)) {
                        flag(data, "Sprinting in a wrong direction. Yaw: " + yaw + " X: " + data.deltaX + " Z: " + data.deltaZ);
                    }
                }
            }
        }
    }
}
