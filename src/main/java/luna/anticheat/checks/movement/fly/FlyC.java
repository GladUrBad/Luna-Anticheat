package luna.anticheat.checks.movement.fly;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;
@CheckInfo(name = "Fly (C)", dev = true)
public class FlyC extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                if(!data.location.isOnGround() && !data.lastLocation.isOnGround()) {
                    if(data.deltaY > 0.8F) {
                        flag(data, "Highjump: " + data.deltaY);
                    }
                }
            }
        }
    }
}
