package luna.anticheat.checks.combat.killaura;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.listeners.CheckManager;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "Killaura (B)", punishVL = 50)
public class KillauraB extends Check {


    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if (PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
               data.killauraBEntitiesHit = 0;
            } else if (e.getPacketId() == PacketType.Client.USE_ENTITY) {
                WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getNMSPacket());
                int currentEntityID = packet.getEntityId();

                int lastEntityID =data.killauraBLastEntityID;
               data.killauraBLastEntityID = currentEntityID;

                if (currentEntityID != lastEntityID) {
                   data.killauraBEntitiesHit++;
                }

                if (data.killauraBEntitiesHit > 1) {
                    flag(data, "hit " + data.killauraBEntitiesHit + " entities at once!");
                } else {
                    reward(data);
                }
            }
        }
    }
}
