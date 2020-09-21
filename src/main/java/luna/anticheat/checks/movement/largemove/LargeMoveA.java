package luna.anticheat.checks.movement.largemove;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "LargeMove (A)", punishVL = 5)
public class LargeMoveA extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                if(data.deltaY > 8 && data.getTicksSinceVelocity() > 20 && data.getTicksSinceRespawn() > 20) {
                    flag(data, "moved " + data.deltaY + " vertically!");
                } else {
                    reward(data);
                }
            }
        }
    }
}
