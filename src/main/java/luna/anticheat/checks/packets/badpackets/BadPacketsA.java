package luna.anticheat.checks.packets.badpackets;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import io.github.retrooper.packetevents.packetwrappers.in.flying.WrappedPacketInFlying;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "BadPackets (A)", punishVL = 1)
public class BadPacketsA extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                WrappedPacketInFlying packet = new WrappedPacketInFlying(e.getNMSPacket());
                double pitch = packet.getPitch();
                if(Math.abs(pitch) > 90) {
                    flag(data, "invalid pitch: " + pitch);
                } else {
                    reward(data);
                }
            }
        }
    }
}
