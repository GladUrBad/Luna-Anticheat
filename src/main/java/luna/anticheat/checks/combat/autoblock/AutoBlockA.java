package luna.anticheat.checks.combat.autoblock;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import io.github.retrooper.packetevents.packetwrappers.in.blockplace.WrappedPacketInBlockPlace;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "AutoBlock (A)", dev = true)
public class AutoBlockA extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(e.getPacketId() == PacketType .Client.BLOCK_PLACE) {
                WrappedPacketInBlockPlace packet = new WrappedPacketInBlockPlace(e.getPlayer(), e.getNMSPacket());
                if(packet.getItemStack().getType().toString().contains("SWORD")) {
                    long diff = data.lastSwingTime -data.lastBlockingTime;
                    if(diff <= 0L) {
                        if(Math.abs(diff) == 0L) {
                            if(++data.autoblockAPreVL > 5) {
                                flag(data, "swing/block packet consistency: " + diff);
                            } else {
                                reward(data);
                            }
                        }
                    } else {
                       data.autoblockAPreVL *= 0.75;
                    }
                }
            }
        }
    }
}
