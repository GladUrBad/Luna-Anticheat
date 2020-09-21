package luna.anticheat.checks.packets.badpackets;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.event.impl.PacketSendEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "BadPackets (B)", punishVL = 20)
public class BadPacketsB extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if (PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                long now = System.currentTimeMillis();
                    if(data.timerLastFlyingTime != null) {
                        data.tickSpeedBalance += 50;
                        data.tickSpeedBalance -= now - data.timerLastFlyingTime;
                        if(data.tickSpeedBalance > 0) {
                            flag(data, "tick speed balance : " + data.tickSpeedBalance);
                            data.tickSpeedBalance = 0;
                        } else {
                            reward(data);
                        }
                    }
                    data.timerLastFlyingTime = now;
            }
        } else if(event instanceof PacketSendEvent) {
            PacketSendEvent e = (PacketSendEvent) event;
            if(e.getPacketId() == PacketType.Server.POSITION) {
               data.tickSpeedBalance -= 50;
            }
        }
    }
}
