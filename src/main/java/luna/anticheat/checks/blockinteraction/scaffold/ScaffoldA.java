package luna.anticheat.checks.blockinteraction.scaffold;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import io.github.retrooper.packetevents.packetwrappers.in.blockplace.WrappedPacketInBlockPlace;
import luna.anticheat.checks.Check;
import luna.anticheat.playerdata.PlayerData;

public class ScaffoldA extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if (e.getPacketId() == PacketType .Client.BLOCK_PLACE) {
                WrappedPacketInBlockPlace packet = new WrappedPacketInBlockPlace(e.getPlayer(), e.getNMSPacket());
                if(packet.getItemStack().getType().isBlock()) {
                    long timeDiff = Math.abs(e.getTimestamp() - data.lastFlyingTime);
                    if (timeDiff < 5) {
                        if (++data.scaffoldACounter > 6) {
                            flag(data, "low flying time: " + timeDiff);
                        } else {
                            reward(data);
                        }
                    }
                }
            }
        }

    }
}
