package luna.anticheat.checks.packets.badpackets;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "BadPackets (E)", punishVL = 1)
public class BadPacketsE extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(e.getPacketId() == PacketType.Client.USE_ENTITY) {
                WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getNMSPacket());
                if(packet.getEntity() == e.getPlayer()) {
                    flag(data, "attacked self!");
                } else {
                    reward(data);
                }
            }
        }
    }
}
