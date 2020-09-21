package luna.anticheat.checks.packets.badpackets;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "BadPackets (D)", dev = true)
public class BadPacketsD extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(e.getPacketId() == PacketType .Client.HELD_ITEM_SLOT) {
                if(data.bpDSlotChanges > 1) {
                    flag(data, "switched slot twice in same tick!");
                } else {
                    reward(data);
                }
            } else if(PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
               data.bpDSlotChanges = 0;
            }
        }
    }
}
