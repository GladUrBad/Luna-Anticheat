package luna.anticheat.checks.packets.badpackets;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import io.github.retrooper.packetevents.packetwrappers.in.entityaction.WrappedPacketInEntityAction;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "BadPackets (C)", punishVL = 50)
public class BadPacketsC extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if (e.getPacketId() == PacketType .Client.ENTITY_ACTION) {
                if (data.isSneaking && data.isSprinting) {
                    if(++data.bpCVl > 1) {
                        flag(data, "sneaking while sprinting");
                    } else {
                        reward(data);
                    }
                } else {
                   data.bpCVl = 0;
                }
            }
        }
    }
}
