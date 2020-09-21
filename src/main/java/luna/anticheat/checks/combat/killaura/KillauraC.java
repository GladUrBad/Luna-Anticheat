package luna.anticheat.checks.combat.killaura;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "Killaura (C)", dev = true, punishVL = 50)
public class KillauraC extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if (e.getPacketId() == PacketType.Client.USE_ENTITY) {
                long diff = System.currentTimeMillis() -data.killauraCLastFlying;
                ++data.killauraCUseEntitySinceFlying;
                int useEntitySinceFlying = data.killauraCUseEntitySinceFlying;

                if (useEntitySinceFlying < 2 && diff < 35L) {
                    if (++data.killauraCPreVl > 3) {
                        flag(data, "Post-flying packet: " + diff);
                    } else {
                        reward(data);
                    }
                } else {
                    data.killauraCPreVl = 0;
                }
            } else if (PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
               data.killauraCLastFlying = System.currentTimeMillis();
               data.killauraCUseEntitySinceFlying = 0;
            }
        }
    }
}
