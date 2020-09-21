package luna.anticheat.checks.packets.badpackets;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import io.github.retrooper.packetevents.packetwrappers.in.abilities.WrappedPacketInAbilities;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "BadPackets (G)", dev = true)
public class BadPacketsG extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(e.getPacketId() == PacketType.Client.ABILITIES) {
                WrappedPacketInAbilities packet = new WrappedPacketInAbilities(e.getNMSPacket());
                e.getPlayer().sendMessage(packet.isFlightAllowed() + " " + e.getPlayer().getAllowFlight());
            }
        }
    }
}
